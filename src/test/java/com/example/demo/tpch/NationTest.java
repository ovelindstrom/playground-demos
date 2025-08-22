package com.example.demo.tpch;

import org.junit.jupiter.api.Test;

import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.Region;
import com.example.demo.tpch.entities.TpchEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NationTest {

    @Test
    void testFromLine_ValidLine() {
        String line = "0|ALGERIA|0| haggle. carefully final deposits detect slyly agai|";
        Nation expected = new Nation(0, "ALGERIA", 0, null, " haggle. carefully final deposits detect slyly agai");
        Nation actual = new Nation().fromLine(line);
        assertEquals(expected, actual);
    }

    @Test
    void testFromLine_WithRegion() {
        String line = "0|ALGERIA|0|comment|";
        Region region = new Region(0, "AFRICA", "comment");
        Map<Long, Region> regionMap = new HashMap<>();
        regionMap.put(0L, region);
        Map<String, Map<Long, ? extends TpchEntity<?>>> context = new HashMap<>();
        context.put("region", regionMap);

        Nation nation = new Nation().fromLine(line, context);
        assertNotNull(nation.region());
        assertEquals("AFRICA", nation.region().name());
    }

    @Test
    void testFromLine_InvalidLine_TooFewFields() {
        String line = "0|ALGERIA|0"; // 3 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Nation().fromLine(line));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidLine_TooManyFields() {
        String line = "0|ALGERIA|0|comment|extra"; // 5 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Nation().fromLine(line));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidNumberFormat() {
        String line = "not_a_number|ALGERIA|0|comment";
        assertThrows(NumberFormatException.class, () -> new Nation().fromLine(line));
    }

    @Test
    void testToLine_ValidNation() {
        Nation nation = new Nation(0, "ALGERIA", 0, null, " haggle. carefully final deposits detect slyly agai");
        String expectedLine = "0|ALGERIA|0| haggle. carefully final deposits detect slyly agai";
        assertEquals(expectedLine, nation.toLine());
    }

    @Test
    void testToLine_WithSpecialCharactersInComment() {
        Nation nation = new Nation(1, "BRAZIL", 1, null, "comment|with|pipes");
        String expectedLine = "1|BRAZIL|1|comment|with|pipes";
        assertEquals(expectedLine, nation.toLine());
    }
}