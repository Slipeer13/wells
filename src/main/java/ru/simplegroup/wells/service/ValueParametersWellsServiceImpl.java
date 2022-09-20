package ru.simplegroup.wells.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simplegroup.wells.entity.Parameter;
import ru.simplegroup.wells.entity.Well;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
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
        AtomicReference<DoubleSummaryStatistics> doubleSummaryStatistics = new AtomicReference<>(new DoubleSummaryStatistics());
        Map<Long, List<Parameter>> parametersMap = parameters.stream().collect(Collectors.groupingBy(Parameter::getWellId));
        String result = wells.stream().map(well -> "\n" + well.getName() + //Наименование скважины
                        parametersMap.get(well.getId()).stream().collect(Collectors.groupingBy(Parameter::getParameterName)).entrySet().stream()//параметры скважины
                                .peek(p-> {
                                            doubleSummaryStatistics.set(new DoubleSummaryStatistics());
                                            p.getValue().forEach(p1-> doubleSummaryStatistics.get().accept(p1.getValue()));
                                        }
                                )
                                .map(parameter -> "\n" + parameter.getKey() //наименование параметра
                                        + "\n мин. значение : " + doubleSummaryStatistics.get().getMin()
                                        + "\n макс. значение : " + doubleSummaryStatistics.get().getMax()
                                        + "\n среднее значение : " + df.format(doubleSummaryStatistics.get().getAverage()))
                                .collect(Collectors.joining()) + "\n")
                .collect(Collectors.joining());
        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        log.info("{} \nexecution time: {} ms", result, elapsed);
        return result;
    }
}
