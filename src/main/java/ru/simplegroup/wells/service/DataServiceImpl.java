package ru.simplegroup.wells.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import ru.simplegroup.wells.entity.Department;
import ru.simplegroup.wells.entity.Parameter;
import ru.simplegroup.wells.entity.Well;
import ru.simplegroup.wells.repository.DepartmentRepository;
import ru.simplegroup.wells.repository.ParameterRepository;
import ru.simplegroup.wells.repository.ReadFileRepository;
import ru.simplegroup.wells.repository.WellRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RefreshScope
public class DataServiceImpl implements DataService {

    @Value("${path.data}")
    private String pathData;

    @Autowired
    WellRepository wellRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    ReadFileRepository<Well> wellReadFileRepository;
    @Autowired
    ReadFileRepository<Department> departmentReadFileRepository;
    @Autowired
    ReadFileRepository<Parameter> parameterReadFileRepository;

    @Override
    public List<Department> getDepartments() {
        List<Department> departments;
        if ("file".equalsIgnoreCase(pathData)) {
            departments = departmentReadFileRepository.findAll();
        } else if ("dataBase".equalsIgnoreCase(pathData)) {
            departments = departmentRepository.findAll();
        } else {
            throw new NoSuchElementException("the data path must be \"file\" or \"dataBase\"");
        }
        return departments.stream().filter(department -> department.getName() != null && department.getX() != null
                && department.getY() != null && department.getRadius() != null).collect(Collectors.toList());
    }

    @Override
    public List<Well> getWells() {
        List<Well> wells;
        if ("file".equalsIgnoreCase(pathData)) {
            wells = wellReadFileRepository.findAll();
        } else if ("dataBase".equalsIgnoreCase(pathData)) {
            wells = wellRepository.findAll();
        } else {
            throw new NoSuchElementException("the data path must be \"file\" or \"dataBase\"");
        }
        return wells.stream().filter(well -> well.getId() != null && well.getName() != null && well.getX() != null
                && well.getY() != null).collect(Collectors.toList());
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters;
        if ("file".equalsIgnoreCase(pathData)) {
            parameters = parameterReadFileRepository.findAll();
        } else if ("dataBase".equalsIgnoreCase(pathData)) {
            parameters = parameterRepository.findAll();
        } else {
            throw new NoSuchElementException("the data path must be \"file\" or \"dataBase\"");
        }
        return parameters.stream().filter(parameter -> parameter.getWellId() != null && parameter.getParameterName() != null &&
                parameter.getValue() != null).collect(Collectors.toList());
    }

}
