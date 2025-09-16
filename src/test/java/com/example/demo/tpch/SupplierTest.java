package com.example.demo.tpch;

import org.junit.jupiter.api.Test;

import com.example.demo.tpch.entities.Supplier;
import com.example.demo.tpch.entities.TpchEntityFactory;
import static com.example.demo.tpch.entities.TpchEntityFactory.EntityType;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierTest {

    @Test
    void testFromLine_ValidInput() {
        String line = "1|Supplier#000000001|Supplier#Address|17|27-918-335-1736|5755.94|Supplier#Comment|";
        Supplier supplier = (Supplier) TpchEntityFactory.fromLine(EntityType.SUPPLIER, line);
        assertEquals(1, supplier.suppKey());
        assertEquals("Supplier#000000001", supplier.name());
        assertEquals("Supplier#Address", supplier.address());
        assertEquals(17, supplier.nationKey());
        assertEquals("27-918-335-1736", supplier.phone());
        assertEquals(5755.94, supplier.acctBal());
        assertEquals("Supplier#Comment", supplier.comment());
    }
}
