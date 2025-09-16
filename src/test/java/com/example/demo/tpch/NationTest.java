package com.example.demo.tpch;

import org.junit.jupiter.api.Test;

import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.TpchEntityFactory;
import static com.example.demo.tpch.entities.TpchEntityFactory.EntityType;


import static org.junit.jupiter.api.Assertions.*;

class NationTest {

    @Test
    void testFromLine_ValidLine() {
        String line = "0|ALGERIA|0| haggle. carefully final deposits detect slyly agai|";
        Nation expected = new Nation(0, "ALGERIA", 0, " haggle. carefully final deposits detect slyly agai");
        Nation actual = (Nation) TpchEntityFactory.fromLine(EntityType.NATION,line);
        assertEquals(expected, actual);
    }
    
    @Test
    void testFromLine_InvalidLine_TooFewFields() {
        String line = "0|ALGERIA|0"; // 3 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(EntityType.NATION,line));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidLine_TooManyFields() {
        String line = "0|ALGERIA|0|comment|extra"; // 5 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(EntityType.NATION,line));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidNumberFormat() {
        String line = "not_a_number|ALGERIA|0|comment";
        assertThrows(NumberFormatException.class, () -> TpchEntityFactory.fromLine(EntityType.NATION,line));
    }
}