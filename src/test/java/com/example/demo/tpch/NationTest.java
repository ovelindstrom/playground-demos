package com.example.demo.tpch;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




class NationTest {

      @Test
    void testFromLine_ValidLine() {
        String line = "0|ALGERIA|0| haggle. carefully final deposits detect slyly agai|";
        Nation expected = new Nation(0, "ALGERIA", 0, " haggle. carefully final deposits detect slyly agai");
        Nation actual = TpchEntityFactory.fromLine(line, Nation::new);
        assertEquals(expected, actual);
    }

    @Test
    void testFromLine_InvalidLine_TooFewFields() {
        String line = "0|ALGERIA|0"; // 3 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(line, Nation::new));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidLine_TooManyFields() {
        String line = "0|ALGERIA|0|comment|extra"; // 5 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(line, Nation::new));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidNumberFormat() {
        String line = "not_a_number|ALGERIA|0|comment";
        assertThrows(NumberFormatException.class, () -> TpchEntityFactory.fromLine(line, Nation::new));
    }

    @Test
    void testToLine_ValidNation() {
        Nation nation = new Nation(0, "ALGERIA", 0, " haggle. carefully final deposits detect slyly agai");
        String expectedLine = "0|ALGERIA|0| haggle. carefully final deposits detect slyly agai";
        assertEquals(expectedLine, nation.toLine());
    }

    @Test
    void testToLine_WithSpecialCharactersInComment() {
        Nation nation = new Nation(1, "BRAZIL", 1, "comment|with|pipes");
        String expectedLine = "1|BRAZIL|1|comment|with|pipes";
        assertEquals(expectedLine, nation.toLine());
    }
}