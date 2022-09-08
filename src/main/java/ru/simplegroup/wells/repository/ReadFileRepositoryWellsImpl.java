package ru.simplegroup.wells.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;
import ru.simplegroup.wells.entity.Well;

import java.util.List;

@Repository
@RefreshScope
public class ReadFileRepositoryWellsImpl implements ReadFileRepository<Well> {

    @Value("${path.file.well}")
    private String pathFileWells;

    @Override
    public List<Well> findAll() {
        return readValue(pathFileWells, Well[].class);
    }

}
