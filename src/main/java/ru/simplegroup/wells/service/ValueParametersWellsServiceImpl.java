package ru.simplegroup.wells.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simplegroup.wells.entity.Parameter;
import ru.simplegroup.wells.entity.Well;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ValueParametersWellsServiceImpl implements ValueParametersWellsService{

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private DataService dataService;

    @Autowired
    public void setReadFileService(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public String getValueParametersWells(int idWellFrom, int idWellTo) {
        Instant start = Instant.now();
        List<Parameter> parameters = dataService.getParameters();
        List<Well> wells = dataService.getWells();
        wells = wells.stream().filter(well -> well.getId() >= idWellFrom && well.getId() <= idWellTo).collect(Collectors.toList());
        Map<Long, List<Parameter>> parametersMap = parameters.stream().collect(Collectors.groupingBy(Parameter::getWellId));
        String result = wells.stream().map(well -> "\n" + well.getName() + //Наименование скважины
                        parametersMap.get(well.getId()).stream().collect(Collectors.groupingBy(Parameter::getParameterName)).entrySet().stream()//параметры скважины
                                .map(parameter -> "\n" + parameter.getKey() //наименование параметра
                                        + "\n мин. значение : " + parameter.getValue().stream().min((o1, o2) -> (int) (o1.getValue() - o2.getValue())).get().getValue() //минимальное значение
                                        + "\n макс. значение : " + parameter.getValue().stream().max((o1, o2) -> (int) (o1.getValue() - o2.getValue())).get().getValue() //максимальное значение
                                        + "\n среднее значение : " + df.format(parameter.getValue().stream().mapToDouble(Parameter::getValue).sum() / parameter.getValue().stream().map(Parameter::getValue).count())) //среднее значение
                                .collect(Collectors.joining()) + "\n")
                .collect(Collectors.joining());
        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        log.info("{} \nexecution time: {} ms", result, elapsed);
        return result;
    }
}
