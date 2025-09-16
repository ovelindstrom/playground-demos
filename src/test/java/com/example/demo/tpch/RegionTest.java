package com.example.demo.tpch;

import org.junit.jupiter.api.Test;

import com.example.demo.tpch.entities.Region;
import com.example.demo.tpch.entities.TpchEntityFactory;
import static com.example.demo.tpch.entities.TpchEntityFactory.EntityType;

import static org.junit.jupiter.api.Assertions.*;

public class RegionTest {


    @Test
    void testFromLineValid() {
        Region region = new Region(0, "", "");
        String input = "1|FINLAND|kyllä";
        Region parsed = (Region) TpchEntityFactory.fromLine(EntityType.REGION, input);
        assertEquals(1, parsed.regionKey());
        assertEquals("FINLAND", parsed.name());
        assertEquals("kyllä", parsed.comment());
    }

    @Test
    void testFromLineInvalidFormat() {
        Region region = new Region(0, "", "");
        String invalidInput = "1|FINLAND";
        assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(EntityType.REGION, invalidInput));
    }

    @Test
    void testFromLineWithExtraDelimiter() {
        Region region = new Region(0, "", "");
        String input = "1|FINLAND|kyllä|extra|";
        assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(EntityType.REGION, input));
    }
}
