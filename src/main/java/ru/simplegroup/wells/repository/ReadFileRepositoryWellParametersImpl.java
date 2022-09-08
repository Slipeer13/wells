package ru.simplegroup.wells.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;
import ru.simplegroup.wells.entity.Parameter;

import java.util.List;

@Repository
@RefreshScope
public class ReadFileRepositoryWellParametersImpl implements ReadFileRepository<Parameter> {

    @Value("${path.file.well.parameters}")
    private String pathFileParameters;

    @Override
    public List<Parameter> findAll() {
        return readValue(pathFileParameters, Parameter[].class);
    }

}
