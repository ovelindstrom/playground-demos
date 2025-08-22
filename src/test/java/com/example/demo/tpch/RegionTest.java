package com.example.demo.tpch;

import org.junit.jupiter.api.Test;

import com.example.demo.tpch.entities.Region;

import static org.junit.jupiter.api.Assertions.*;

public class RegionTest {

    @Test
    void testToLine() {
        Region region = new Region(1, "FINLAND", "kyllä");
        String expected = "1|FINLAND|kyllä";
        assertEquals(expected, region.toLine());
    }

    @Test
    void testFromLineValid() {
        Region region = new Region(0, "", "");
        String input = "1|FINLAND|kyllä";
        Region parsed = region.fromLine(input);
        assertEquals(1, parsed.regionKey());
        assertEquals("FINLAND", parsed.name());
        assertEquals("kyllä", parsed.comment());
    }

    @Test
    void testFromLineInvalidFormat() {
        Region region = new Region(0, "", "");
        String invalidInput = "1|FINLAND";
        assertThrows(IllegalArgumentException.class, () -> region.fromLine(invalidInput));
    }

    @Test
    void testFromLineWithExtraDelimiter() {
        Region region = new Region(0, "", "");
        String input = "1|FINLAND|kyllä|extra|";
        assertThrows(IllegalArgumentException.class, () -> region.fromLine(input));
    }
}
