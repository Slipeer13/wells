package ru.simplegroup.wells.service;

import ru.simplegroup.wells.entity.Department;
import ru.simplegroup.wells.entity.Parameter;
import ru.simplegroup.wells.entity.Well;

import java.util.List;

public interface DataService {

    List<Department> getDepartments();

    List<Well> getWells();

    List<Parameter> getParameters();
}
