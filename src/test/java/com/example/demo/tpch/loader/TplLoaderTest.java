package com.example.demo.tpch.loader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.TpchEntityFactory.EntityType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TplLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testLoad_ValidFile_ReturnsListOfNations() throws IOException {
        String nationTblContent = "0|ALGERIA|0| haggle. carefully final deposits detect slyly agai|\n" +
                "1|ARGENTINA|1|al foxes promise slyly according to the regular accounts. bold requests alon|\n" +
                "2|BRAZIL|1|y alongside of the pending deposits. carefully special packages are about the ironic forges. slyly special |\n" +
                "3|CANADA|1|eas hang ironic, silent packages. slyly regular packages are furiously over the tithes. fluffily bold|\n" +
                "4|EGYPT|4|y above the carefully unusual theodolites. final dugouts are quickly across the furiously regular d|\n" +
                "5|ETHIOPIA|0|ven packages wake quickly. regu|\n" +
                "6|FRANCE|3|refully final requests. regular, ironi|\n" +
                "7|GERMANY|3|l platelets. regular accounts x-ray: unusual, regular acco|\n" +
                "8|INDIA|2|ss excuses cajole slyly across the packages. deposits print aroun|\n" +
                "9|INDONESIA|2| slyly express asymptotes. regular deposits haggle slyly. carefully ironic hockey players sleep blithely. carefull|\n" +
                "10|IRAN|4|efully alongside of the slyly final dependencies. |\n" +
                "11|IRAQ|4|nic deposits boost atop the quickly final requests? quickly regula|\n" +
                "12|JAPAN|2|ously. final, express gifts cajole a|\n" +
                "13|JORDAN|4|ic deposits are blithely about the carefully regular pa|\n" +
                "14|KENYA|0| pending excuses haggle furiously deposits. pending, express pinto beans wake fluffily past t|\n" +
                "15|MOROCCO|0|rns. blithely bold courts among the closely regular packages use furiously bold platelets?|\n" +
                "16|MOZAMBIQUE|0|s. ironic, unusual asymptotes wake blithely r|\n" +
                "17|PERU|1|platelets. blithely pending dependencies use fluffily across the even pinto beans. carefully silent accoun|\n" +
                "18|CHINA|2|c dependencies. furiously express notornis sleep slyly regular accounts. ideas sleep. depos|\n" +
                "19|ROMANIA|3|ular asymptotes are about the furious multipliers. express dependencies nag above the ironically ironic account|\n" +
                "20|SAUDI ARABIA|4|ts. silent requests haggle. closely express packages sleep across the blithely|\n" +
                "21|VIETNAM|2|hely enticingly express accounts. even, final |\n" +
                "22|RUSSIA|3| requests against the platelets use never according to the quickly regular pint|\n" +
                "23|UNITED KINGDOM|3|eans boost carefully special requests. accounts are. carefull|\n" +
                "24|UNITED STATES|1|y final packages. slow foxes cajole quickly. quickly silent platelets breach ironic accounts. unusual pinto be|";
        Path nationFile = tempDir.resolve("nation.tbl");
        Files.writeString(nationFile, nationTblContent);

        List<Nation> nations = TplLoader.load(nationFile.toString(), EntityType.NATION);

        assertNotNull(nations);
        assertEquals(25, nations.size());

        Nation firstNation = nations.get(0);
        assertEquals(0, firstNation.nationKey());
        assertEquals("ALGERIA", firstNation.name());
        assertEquals(0, firstNation.regionKey());
        assertEquals(" haggle. carefully final deposits detect slyly agai", firstNation.comment());

        Nation lastNation = nations.get(24);
        assertEquals(24, lastNation.nationKey());
        assertEquals("UNITED STATES", lastNation.name());
        assertEquals(1, lastNation.regionKey());
        assertEquals("y final packages. slow foxes cajole quickly. quickly silent platelets breach ironic accounts. unusual pinto be", lastNation.comment());
    }

    @Test
    void testLoad_NonExistentFile_ReturnsEmptyList() {
        List<Nation> nations = TplLoader.load("non_existent_file.tbl", EntityType.NATION);
        assertTrue(nations.isEmpty());
    }

    @Test
    void testLoad_EmptyFile_ReturnsEmptyList() throws IOException {
        Path emptyFile = tempDir.resolve("empty.tbl");
        Files.createFile(emptyFile);
        List<Nation> nations = TplLoader.load(emptyFile.toString(), EntityType.NATION);
        assertTrue(nations.isEmpty());
    }
}
