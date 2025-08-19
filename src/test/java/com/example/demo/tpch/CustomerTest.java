package com.example.demo.tpch;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testFromLine_ValidLine() {
        String line = "1|CustomerName|CustomerAddress|15|25-989-741-2988|71156|BUILDING|comment comment|";
        Customer expected = new Customer(
                1L,
                "CustomerName",
                "CustomerAddress",
                15L,
                "25-989-741-2988",
                71156L,
                "BUILDING",
                "comment comment");
        Customer actual = TpchEntityFactory.fromLine(line, Customer::new);
        assertEquals(expected, actual);
    }

    @Test
    void testFromLine_InvalidLine_TooFewFields() {
        String line = "1|CustomerName|CustomerAddress|15|25-989-741-2988|71156|BUILDING";
        assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(line, Customer::new));
    }

    @Test
    void testFromLine_InvalidLine_TooManyFields() {
        String line = "1|CustomerName|CustomerAddress|15|25-989-741-2988|71156|BUILDING|comment|extra";
        assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(line, Customer::new));
    }

    @Test
    void testFromLine_InvalidNumberFormat() {
        String line = "not_a_number|CustomerName|CustomerAddress|15|25-989-741-2988|71156|BUILDING|comment";
        assertThrows(NumberFormatException.class, () -> TpchEntityFactory.fromLine(line, Customer::new));
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
                "comment comment|");
        String expectedLine = "1|CustomerName|CustomerAddress|15|25-989-741-2988|71156|BUILDING|comment comment|";
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
                "");
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
                "Comment with | and \n");
        String expectedLine = "42|Name|With|Pipes|Address\nWith\nNewlines|99|123-456-7890|100|SEGMENT|Comment with | and \n";
        // Note: This test will fail because String.join does not escape delimiters or
        // newlines.
        // This test is to document current behavior.
        assertEquals(expectedLine, customer.toLine());
    }
}