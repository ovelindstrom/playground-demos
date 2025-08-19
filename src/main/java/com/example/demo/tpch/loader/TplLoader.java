package com.example.demo.tpch.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.tpch.TpchEntity;
import com.example.demo.tpch.TpchEntityFactory;

import java.util.function.Supplier;

public class TplLoader {

    public static <T extends TpchEntity<T>> List<T> load(String filePath, Supplier<T> supplier) {
        List<T> entities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                T entity = TpchEntityFactory.fromLine(line, supplier);
                entities.add(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }

}
