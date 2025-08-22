package com.example.demo.tpch;

import org.junit.jupiter.api.Test;

import com.example.demo.tpch.entities.LineItem;
import com.example.demo.tpch.entities.Order;
import com.example.demo.tpch.entities.Part;
import com.example.demo.tpch.entities.Supplier;
import com.example.demo.tpch.entities.TpchEntity;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

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
                "egular courts above the",
                null,
                null,
                null
        );
        LineItem actual = new LineItem().fromLine(line);
        assertEquals(expected, actual);
    }

    @Test
    void testFromLine_WithContext() {
        String line = "1|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|comment";

        Part part = new Part(155190L, "","","","",0,"", 0.0,"");
        Order order = new Order(1L, 1L, "O", 0.0, LocalDate.now(), "5-LOW", "Clerk#000000001", 0, "comment", null);
        Supplier supplier = new Supplier(7706, "Supplier#000007706", "address", 15, "25-989-741-2988", 0, "comment", null);


        Map<Long, Part> partMap = new HashMap<>();
        partMap.put(155190L, part);

        Map<Long, Order> orderMap = new HashMap<>();
        orderMap.put(1L, order);
        Map<Long, Supplier> supplierMap = new HashMap<>();
        supplierMap.put(7706L, supplier);

        Map<String, Map<Long, ? extends TpchEntity<?>>> context = new HashMap<>();
        context.put("parts", partMap);
        context.put("orders", orderMap);
        context.put("suppliers", supplierMap);

        LineItem lineItem = new LineItem().fromLine(line, context);
        assertNotNull(lineItem.order());
        assertEquals(1, lineItem.order().orderKey());
        assertNotNull(lineItem.supplier());
        assertEquals(7706, lineItem.supplier().suppKey());
    }

    @Test
    void testFromLine_InvalidLine_TooFewFields() {
        String line = "1|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK"; // 15 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new LineItem().fromLine(line));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidLine_TooManyFields() {
        String line = "1|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|comment|extra"; // 17 fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new LineItem().fromLine(line));
        assertEquals("Invalid line format", exception.getMessage());
    }

    @Test
    void testFromLine_InvalidNumberFormat() {
        String line = "not_a_number|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|comment";
        assertThrows(NumberFormatException.class, () -> new LineItem().fromLine(line));
    }

    @Test
    void testFromLine_InvalidDateFormat() {
        String line = "1|155190|7706|1|17.0|21168.23|0.04|0.02|N|O|not_a_date|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|comment";
        assertThrows(DateTimeParseException.class, () -> new LineItem().fromLine(line));
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
                "egular courts above the",
                null,
                null,
                null
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
                "Comment|With|Pipe",
                null,
                null,
                null
        );
        String expectedLine = "1|1|1|1|1.0|1.0|1.0|1.0|A|B|C|2023-01-01|2023-01-01|2023-01-01|Instruct\nHere|Mode|Comment|With|Pipe";
        assertEquals(expectedLine, lineItem.toLine());
    }
}