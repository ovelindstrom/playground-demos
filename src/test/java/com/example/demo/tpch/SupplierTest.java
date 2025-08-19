package com.example.demo.tpch;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SupplierTest {

    @Test
    void testFromLine_ValidInput() {
        String line = "1|Supplier#000000001|Supplier#Address|17|27-918-335-1736|5755.94|Supplier#Comment|";
        Supplier supplier = new Supplier(0, "", "", 0, "", 0.0, "").fromLine(line);

        assertEquals(1, supplier.suppKey());
        assertEquals("Supplier#000000001", supplier.name());
        assertEquals("Supplier#Address", supplier.address());
        assertEquals(17, supplier.nationKey());
        assertEquals("27-918-335-1736", supplier.phone());
        assertEquals(5755.94, supplier.acctBal());
        assertEquals("Supplier#Comment", supplier.comment());
    }

    @Test
    void testToLine() {
        Supplier supplier = new Supplier(
                1,
                "Supplier#000000001",
                "Supplier#Address",
                17,
                "27-918-335-1736",
                5755.94,
                "Supplier#Comment|");
        String expected = "1|Supplier#000000001|Supplier#Address|17|27-918-335-1736|5755.94|Supplier#Comment|";
        assertEquals(expected, supplier.toLine());
    }

    @Test
    void testFromLine_InvalidInput() {
        String invalidLine = "1|Supplier#000000001|Supplier#Address|17|27-918-335-1736|5755.94";
        Supplier dummy = new Supplier(0, "", "", 0, "", 0.0, "");
        assertThrows(IllegalArgumentException.class, () -> dummy.fromLine(invalidLine));
    }

    @Test
    void testFromLine_NonNumericFields() {
        String invalidLine = "abc|Supplier#000000001|Supplier#Address|xyz|27-918-335-1736|notadouble|Supplier#Comment|";
        Supplier dummy = new Supplier(0, "", "", 0, "", 0.0, "");
        assertThrows(NumberFormatException.class, () -> dummy.fromLine(invalidLine));
    }
}
