package ru.simplegroup.wells.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simplegroup.wells.entity.Parameter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ParametersWellsServiceImpl implements ParametersWellsService {

    private DataService dataService;

    @Autowired
    public void setReadFileService(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public String getParametersWells() {
        Instant start = Instant.now();
        List<Parameter> parameters = dataService.getParameters();
        String result = parameters.stream().map(Parameter::getParameterName)
                .distinct().sorted().map(e -> "\n" + e).collect(Collectors.joining());
        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        log.info("{} \nexecution time: {} ms", result, elapsed);
        return result;
    }
}
