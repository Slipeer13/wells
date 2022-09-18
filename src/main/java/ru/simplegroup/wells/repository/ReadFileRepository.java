package ru.simplegroup.wells.repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ReadFileRepository<T> extends DataRepository<T>{

    default List<T> readValue(String fileName, Class<T[]> clazz) {
        List<T> result = new ArrayList<>();
        try(FileReader fileReader = new FileReader(fileName)) {
            result = Arrays.asList(new ObjectMapper().readValue(fileReader, clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
