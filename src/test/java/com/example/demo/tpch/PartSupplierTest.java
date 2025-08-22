package com.example.demo.tpch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.demo.tpch.entities.PartSupplier;
import com.example.demo.tpch.entities.TpchEntityFactory;

public class PartSupplierTest {
    @Test
    void testFromLine() {
        String line = "1|2|3325|771.64|PartSupplier#Comment|";
        PartSupplier partSupplier = TpchEntityFactory.fromLine(line, PartSupplier::new);

        assertEquals(1, partSupplier.partKey());
        assertEquals(2, partSupplier.suppKey());
        assertEquals(3325, partSupplier.availQty());
        assertEquals(771.64, partSupplier.supplyCost());
        assertEquals("PartSupplier#Comment", partSupplier.comment());
    }

    @Test
    void testToLine() {
        PartSupplier partSupplier = new PartSupplier(1, 2, 3325, 771.64, "PartSupplier#Comment",null, null);
        String expected = "1|2|3325|771.64|PartSupplier#Comment";
        assertEquals(expected, partSupplier.toLine());
    }

    @Test
    void testFromLineWithInvalidData() {
        String line = "1|2|3325|771.64|";
        assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(line, PartSupplier::new));
    }

}
