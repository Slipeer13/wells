package ru.simplegroup.wells.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simplegroup.wells.entity.Department;
import ru.simplegroup.wells.entity.Well;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DepartmentsToWellsServiceImpl implements DepartmentsToWellsService{

    private DataService dataService;

    @Autowired
    public void setReadFileService(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public String getDepartmentsToWells() {
        Instant start = Instant.now();
        List<Department> departments = dataService.getDepartments();
        List<Well> wells = dataService.getWells();
        String result = wells.stream()
                .map(well -> departments.stream().filter(department -> isWellLocatedInDepartment(well, department))
                        .map(department -> String.format("%n%s принадлежит месторождению %s", well.getName(), department.getName()))
                        .collect(Collectors.joining())).collect(Collectors.joining());
        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        log.info("{} \nexecution time: {} ms", result, elapsed);
        return result;
    }

    private boolean isWellLocatedInDepartment(Well well, Department department) {
        return (Math.pow(well.getX() - department.getX(), 2)
                + Math.pow(well.getY() - department.getY(), 2)) <= Math.pow(department.getRadius(), 2);
    }

}
