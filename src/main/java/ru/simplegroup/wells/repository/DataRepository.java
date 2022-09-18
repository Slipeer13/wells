package ru.simplegroup.wells.repository;

import java.util.List;

public interface DataRepository<T> {
    List<T> findAll();
}
