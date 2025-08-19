package com.example.demo.tpch;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import static org.junit.jupiter.api.Assertions.*;





class LineItemTest {

    @Test
    void testFromLine_ValidLine() {
        String line = "1|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|egular courts above the";
        LineItem expected = new LineItem(
                1,
                155190,
                7706,
                1,
                17.0,
                21168.23,
                0.04,
                0.02,
                "N",
                "O",
                LocalDate.parse("1996-03-13"),
                LocalDate.parse("1996-02-12"),
                LocalDate.parse("1996-03-22"),
                "DELIVER IN PERSON",
                "TRUCK",
                "egular courts above the"
        );
        LineItem actual = TpchEntityFactory.fromLine(line, LineItem::new);
        assertEquals(expected, actual);
    }

    @Test
    void testFromLine_InvalidLine_TooFewFields() {
        String line = "1|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK"; // 15 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(line, LineItem::new));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidLine_TooManyFields() {
        String line = "1|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|comment|extra"; // 17 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TpchEntityFactory.fromLine(line, LineItem::new));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidNumberFormat() {
        String line = "not_a_number|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|comment";
        assertThrows(NumberFormatException.class, () -> TpchEntityFactory.fromLine(line, LineItem::new));
    }

    @Test
    void testFromLine_InvalidDateFormat() {
        String line = "1|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|not_a_date|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|comment";
        assertThrows(DateTimeParseException.class, () -> TpchEntityFactory.fromLine(line, LineItem::new));
    }

    @Test
    void testToLine_ValidLineItem() {
        LineItem lineItem = new LineItem(
                1,
                155190,
                7706,
                1,
                17.0,
                21168.23,
                0.04,
                0.02,
                "N",
                "O",
                LocalDate.parse("1996-03-13"),
                LocalDate.parse("1996-02-12"),
                LocalDate.parse("1996-03-22"),
                "DELIVER IN PERSON",
                "TRUCK",
                "egular courts above the"
        );
        String expectedLine = "1|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|egular courts above the";
        assertEquals(expectedLine, lineItem.toLine());
    }

    @Test
    void testToLine_WithSpecialCharacters() {
        LineItem lineItem = new LineItem(
                1,
                1,
                1,
                1,
                1.0,
                1.0,
                1.0,
                1.0,
                "A|B",
                "C",
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2023-01-01"),
                "Instruct\nHere",
                "Mode",
                "Comment|With|Pipe"
        );
        String expectedLine = "1|1|1|1|1.0|1.0|1.0|1.0|A|B|C|2023-01-01|2023-01-01|2023-01-01|Instruct\nHere|Mode|Comment|With|Pipe";
        assertEquals(expectedLine, lineItem.toLine());
    }
}