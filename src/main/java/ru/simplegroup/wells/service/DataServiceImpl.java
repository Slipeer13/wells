package ru.simplegroup.wells.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simplegroup.wells.entity.Department;
import ru.simplegroup.wells.entity.Parameter;
import ru.simplegroup.wells.entity.Well;
import ru.simplegroup.wells.repository.ReadFileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {

    /*@Autowired
    WellRepository wellRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    ParameterRepository parameterRepository;*/

    @Autowired
    ReadFileRepository<Well> wellRepository;
    @Autowired
    ReadFileRepository<Department> departmentRepository;
    @Autowired
    ReadFileRepository<Parameter> parameterRepository;

    @Override
    public List<Department> getDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().filter(department -> department.getX() != null
                && department.getY() != null && department.getRadius() != null).collect(Collectors.toList());
    }

    @Override
    public List<Well> getWells() {
        List<Well> wells = wellRepository.findAll();
        return wells.stream().filter(well -> well.getX() != null
                && well.getY() != null).collect(Collectors.toList());
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = parameterRepository.findAll();
        return parameters.stream().filter(parameter -> parameter.getValue() != null).collect(Collectors.toList());
    }
}
