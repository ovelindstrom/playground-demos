package com.example.demo.tpch;

import org.junit.jupiter.api.Test;

import com.example.demo.tpch.entities.Customer;
import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.TpchEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testFromLine_ValidLine() {
        String line = "1|Customer#000000001|IVhzIApeRb ot,c,E|15|25-989-741-2988|711.56|BUILDING|to the even, regular platelets. final, regular accounts deposit silently ironic, pending requests. ironic, unusual assumptions handle final, pending pinto beans. carefully unusual instructions nag attentively among the furiously unusual sentences. final accounts sleep furiously among the instructions. unusual, regular deposits haggle carefully across the furiously regular accounts. furiously final ideas believe slyly. final, ironic ideas sleep quickly furious packages wake deposits. pending, furiously regular requests sleep slyly across the furiously silent accounts. even accounts boost. slyly regular packages haggle. furiously express ideas nag. final, regular accounts are according to the slyly final packages? furiously final accounts wake. furiously regular requests sleep slyly among the regular accounts. final, regular ideas cajole carefully, regular ideas. final, ironic accounts sleep. even, final requests sleep slyly. regular instructions boost slyly. even, regular accounts are. slyly final dependencies wake. pending accounts haggle slyly. furiously express ideas nag furiously. even dependencies use. even, final accounts boost slyly at the pending accounts. pending, regular accounts haggle slyly near the regular accounts. regular, final packages cajole carefully at the furiously regular ideas. furiously unusual ideas are slyly. regular, final accounts boost carefully at the regular ideas. final, even ideas sleep slyly at the furiously regular dependencies. |";
        Customer expected = new Customer(
                1,
                "Customer#000000001",
                "IVhzIApeRb ot,c,E",
                15,
                "25-989-741-2988",
                71156,
                "BUILDING",
                "to the even, regular platelets. final, regular accounts deposit silently ironic, pending requests. ironic, unusual assumptions handle final, pending pinto beans. carefully unusual instructions nag attentively among the furiously unusual sentences. final accounts sleep furiously among the instructions. unusual, regular deposits haggle carefully across the furiously regular accounts. furiously final ideas believe slyly. final, ironic ideas sleep quickly furious packages wake deposits. pending, furiously regular requests sleep slyly across the furiously silent accounts. even accounts boost. slyly regular packages haggle. furiously express ideas nag. final, regular accounts are according to the slyly final packages? furiously final accounts wake. furiously regular requests sleep slyly among the regular accounts. final, regular ideas cajole carefully, regular ideas. final, ironic accounts sleep. even, final requests sleep slyly. regular instructions boost slyly. even, regular accounts are. slyly final dependencies wake. pending accounts haggle slyly. furiously express ideas nag furiously. even dependencies use. even, final accounts boost slyly at the pending accounts. pending, regular accounts haggle slyly near the regular accounts. regular, final packages cajole carefully at the furiously regular ideas. furiously unusual ideas are slyly. regular, final accounts boost carefully at the regular ideas. final, even ideas sleep slyly at the furiously regular dependencies. ",
                null);
        Customer actual = new Customer().fromLine(line);
        assertEquals(expected, actual);
    }

    @Test
    void testFromLine_WithNation() {
        String line = "1|Customer#000000001|IVhzIApeRb ot,c,E|15|25-989-741-2988|711.56|BUILDING|comment|";
        Nation nation = new Nation(15, "FRANCE", 3, null, "comment");
        Map<Long, Nation> nationMap = new HashMap<>();
        nationMap.put(15L, nation);
        Map<String, Map<Long, ? extends TpchEntity<?>>> context = new HashMap<>();
        context.put("nation", nationMap);

        Customer customer = new Customer().fromLine(line, context);
        assertNotNull(customer.nation());
        assertEquals("FRANCE", customer.nation().name());
    }

    @Test
    void testFromLine_InvalidLine_TooFewFields() {
        String line = "1|CustomerName|CustomerAddress|15|25-989-741-2988|71156|BUILDING";
        assertThrows(IllegalArgumentException.class, () -> new Customer().fromLine(line));
    }

    @Test
    void testFromLine_InvalidLine_TooManyFields() {
        String line = "1|CustomerName|CustomerAddress|15|25-989-741-2988|71156|BUILDING|comment|extra";
        assertThrows(IllegalArgumentException.class, () -> new Customer().fromLine(line));
    }

    @Test
    void testFromLine_InvalidNumberFormat() {
        String line = "not_a_number|CustomerName|CustomerAddress|15|25-989-741-2988|71156|BUILDING|comment";
        assertThrows(NumberFormatException.class, () -> new Customer().fromLine(line));
    }

    @Test
    void testToLine_ValidCustomer() {
        Customer customer = new Customer(
                1L,
                "CustomerName",
                "CustomerAddress",
                15L,
                "25-989-741-2988",
                71156L,
                "BUILDING",
                "comment comment",
                null);
        String expectedLine = "1|CustomerName|CustomerAddress|15|25-989-741-2988|71156|BUILDING|comment comment";
        assertEquals(expectedLine, customer.toLine());
    }

    @Test
    void testToLine_EmptyFields() {
        Customer customer = new Customer(
                0L,
                "",
                "",
                0L,
                "",
                0L,
                "",
                "",
                null);
        String expectedLine = "0|||0||0||";
        assertEquals(expectedLine, customer.toLine());
    }

    @Test
    void testToLine_SpecialCharacters() {
        Customer customer = new Customer(
                42L,
                "Name|With|Pipes",
                "Address\nWith\nNewlines",
                99L,
                "123-456-7890",
                100L,
                "SEGMENT",
                "Comment with | and \n",
                null);
        String expectedLine = "42|Name|With|Pipes|Address\nWith\nNewlines|99|123-456-7890|100|SEGMENT|Comment with | and \n";
        // Note: This test will fail because String.join does not escape delimiters or
        // newlines.
        // This test is to document current behavior.
        assertEquals(expectedLine, customer.toLine());
    }
}