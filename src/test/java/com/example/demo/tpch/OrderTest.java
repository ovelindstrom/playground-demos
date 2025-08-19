package com.example.demo.tpch;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

  
  @Test
    void testFromLine_ValidLine() {
        String line = "1|3691|O|173665.47|1996-01-02|5-LOW|Clerk#000000951|0|nstructions sleep furiously among ";
        Order expected = new Order(
            1,
            3691,
            "O",
            173665.47,
            LocalDate.parse("1996-01-02"),
            "5-LOW",
            "Clerk#000000951",
            0,
            "nstructions sleep furiously among "
        );
        Order actual = TpchEntityFactory.fromLine(line, Order::new);
        assertEquals(expected, actual);
    }

    @Test
    void testFromLine_InvalidLine_TooFewFields() {
        String line = "1|3691|O|173665.47|1996-01-02|5-LOW|Clerk#000000951|0"; // 8 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(line, Order::new));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidLine_TooManyFields() {
        String line = "1|3691|O|173665.47|1996-01-02|5-LOW|Clerk#000000951|0|comment|extra"; // 10 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(line, Order::new));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidNumberFormat() {
        String line = "not_a_number|3691|O|173665.47|1996-01-02|5-LOW|Clerk#000000951|0|comment";
        assertThrows(NumberFormatException.class, () -> TpchEntityFactory.fromLine(line, Order::new));
    }

    @Test
    void testToLine_ValidOrder() {
        Order order = new Order(
            1,
            3691,
            "O",
            173665.47,
            LocalDate.parse("1996-01-02"),
            "5-LOW",
            "Clerk#000000951",
            0,
            "nstructions sleep furiously among "
        );
        String expectedLine = "1|3691|O|173665.47|1996-01-02|5-LOW|Clerk#000000951|0|nstructions sleep furiously among ";
        assertEquals(expectedLine, order.toLine());
    }
}
