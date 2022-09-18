package ru.simplegroup.wells.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import ru.simplegroup.wells.entity.Department;
import ru.simplegroup.wells.entity.Parameter;
import ru.simplegroup.wells.entity.Well;
import ru.simplegroup.wells.repository.*;

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
        List<Department> departments = getDataRepository(new Department(), pathData).findAll();
        return departments.stream().filter(department -> department.getName() != null && department.getX() != null
                && department.getY() != null && department.getRadius() != null).collect(Collectors.toList());
    }

    @Override
    public List<Well> getWells() {
        List<Well> wells = getDataRepository(new Well(), pathData).findAll();;
        return wells.stream().filter(well -> well.getId() != null && well.getName() != null && well.getX() != null
                && well.getY() != null).collect(Collectors.toList());
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = getDataRepository(new Parameter(), pathData).findAll();;
        return parameters.stream().filter(parameter -> parameter.getWellId() != null && parameter.getParameterName() != null &&
                parameter.getValue() != null).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private <T> DataRepository<T> getDataRepository(T t, String pathData) {
        DataRepository<T> result = null;
        if ("file".equalsIgnoreCase(pathData)) {
            if (t instanceof Well) result = (DataRepository<T>) wellReadFileRepository;
            else if (t instanceof Department) result = (DataRepository<T>) departmentReadFileRepository;
            else if (t instanceof Parameter) result = (DataRepository<T>) parameterReadFileRepository;
        } else if ("dataBase".equalsIgnoreCase(pathData)) {
            if (t instanceof Well) result = (DataRepository<T>) wellRepository;
            else if (t instanceof Department) result = (DataRepository<T>) departmentRepository;
            else if (t instanceof Parameter) result = (DataRepository<T>) parameterRepository;
        } else {
            throw new NoSuchElementException("the data path must be \"file\" or \"dataBase\"");
        }
        return result;
    }

}
