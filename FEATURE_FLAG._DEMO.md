# Demo of using feature fkags

This demo is to understand how feature flaggs and Spring Boot Properties can work in conjunction.

It is implemented using CommanLineRunner. The CommandLineRunner is a functional interface in Spring Boot that allows you to execute specific code after the Spring application context has been fully initialized and all beans have been loaded, but before the application is considered fully "started" (e.g., ready to serve requests for a web application).

In the demo, I used this because I want to show how competing implementations can be selected on startup. If you try to run both the CommanLineRuners at the same time, there will be an initialization issue with Unleash.

# The flags

We have two levels of feature toggles. The first one is the Properties Feature Toggles, defined in the `application.yml`.
This are used to toggle features that could controll how to start this, like if using Unleash direct or through Open Feature.

```yml
killers:
    ## Master feature toggle
    enabled: false
    ## Feature toggles for different profiles.
    ## This can also be done using Spring @Profiles
    profile: dexter
    ## Profile specific toggles
    dexter:
      ## Enabling the running loop.
      enabled: false
      ## Configurable "user name"
      name: Dexter
    pennywise:
      enabled: false
      name: Pennywise
```
In the Feature Flag Service we have the following flags:

```yml
dexter.enabled - toggle only 
pennywise.enabled - toggle only
kilersonly.enabled - context sensitive based on userId
detective.suspect.name - if enabled, returns a random suspect name
```

## Unleash settings

```yml
## Unleash configuration
unleash:
  enabled: true
  appName: killers-onboarding-java
  instanceId: killers-onboarding-instance
  apiUrl: https://unleash-aks01.aarotest.com/api/
  apiKey: default:development.77302b30bc579e3f2729920efdbfdf6c46c6b5ec2464096bb9f5df8d
```
They are stored in the UnleashConfiguration class.
This class exposes 2 @Beans; one for getting the Unleash client directly and one that wraps it in an Open Feature Client.

## The conditional selection of killer.

```java
@Component
@ConditionalOnExpression("${killers.enabled:false} and '${killers.profile:}' == 'dexter'")
public class DexterRunner implements CommandLineRunner {
    ...
---
@Component
@ConditionalOnExpression("${killers.enabled:false} and '${killers.profile:}' == 'pennywise'")
public class PennywiseRunner implements CommandLineRunner {
    ...
```

Our two runners both enables on the `killers.enabled` property.
Since this is evaluated during startup, we have not yet connected to the Feature flag service.

They have individual rules to be anabled by setting the `killers.profile` property.
This can also be achived using the @Profile annotation from Spring, but this is an example on how to toggle on "versions".

Now we know that only one of the killers will be running.

## Evaluation Context

```java
UnleashContext context = UnleashContext.builder()
                .userId(killerName)
                .build();
```
This creates a context object that should be used when requesting a flag. 

The client also sets application context, like the application name and the instance. In this example it is hard coded, but should be created based on a pattern.

Unleash has this built in context fields:
- environment
- userId
- appName
- currentTime
- sessionId



Open Feature does it like this:
```java
MutableContext context = new MutableContext();
        context.add("userId", properties.getName());
        client.setEvaluationContext(context);
```
Lets look at the different toggle types.

## Plain enabled/disabled

```java 
if (unleash.isEnabled("dexter.enabled")) {
```

Pretty straight forward.

## Context enabled

Here we send in the context. This should be the default.

```java
if(unleash.isEnabled("killersonly.enabled", context)) {
```

This feature flag uses the Constraints on the strategy so that the userId has to be Dexter or dexter.

If the same segment is to be used for several flaggs, use a named Segment in Unleash. https://docs.getunleash.io/reference/segments

## Get a variant

```java
var variant = unleash.getVariant("detective.suspect.name", context);
if (variant.isEnabled()) {
     logger.info("### The detective suspects {}:{} is the killer.", 
        variant.getName(), 
        variant.getPayload()
            );
     
```

This gets a Variant. The variant contains the toggle, same as before.

But is also contains a return value. For the Suspects, this is created as a random returned value of 4 different suspects. This is the way we can return experimental values for A/B testing. Supported variant formats are string, json, csv or number.

Note that this can be used as a way of giving an enabled component a json configuration object based on a segment instead of having to ask for several flags. But you should NOT use it to stor the whole configuration for a Customer or a Tenant.







