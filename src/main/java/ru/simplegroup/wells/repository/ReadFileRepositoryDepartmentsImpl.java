package ru.simplegroup.wells.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;
import ru.simplegroup.wells.entity.Department;

import java.util.List;

@Repository
@RefreshScope
public class ReadFileRepositoryDepartmentsImpl implements ReadFileRepository<Department> {

    @Value("${path.file.department}")
    private String pathFileDepartment;

    @Override
    public List<Department> findAll() {
        return readValue(pathFileDepartment, Department[].class);
    }

}
