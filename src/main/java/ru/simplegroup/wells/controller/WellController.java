package ru.simplegroup.wells.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simplegroup.wells.service.DepartmentsToWellsService;
import ru.simplegroup.wells.service.ParametersWellsService;
import ru.simplegroup.wells.service.ValueParametersWellsService;

import javax.validation.constraints.Min;

@RestController
@Validated
public class WellController {

    private final ParametersWellsService parametersWellsService;
    private final DepartmentsToWellsService departmentsToWellsService;
    private final ValueParametersWellsService valueParametersWellsService;

    @Autowired
    public WellController(ParametersWellsService parametersWellsService,
                          DepartmentsToWellsService departmentsToWellsService,
                          ValueParametersWellsService valueParametersWellsService) {
        this.parametersWellsService = parametersWellsService;
        this.departmentsToWellsService = departmentsToWellsService;
        this.valueParametersWellsService = valueParametersWellsService;
    }

    @GetMapping("/parameters")
    public ResponseEntity<String> getParametersWells() {
        return ResponseEntity.ok(parametersWellsService.getParametersWells());
    }

    @GetMapping("/values")
    public ResponseEntity<String> getValueParametersWells(@RequestParam(name = "idFrom", defaultValue = "10") @Min(1) int idFrom,
                                        @RequestParam(name = "idTo", defaultValue = "30") @Min(1) int idTo) {
        return ResponseEntity.ok(valueParametersWellsService.getValueParametersWells(idFrom, idTo));
    }

    @GetMapping("/departments")
    public ResponseEntity<String> getDepartmentsToWells() {
        return ResponseEntity.ok(departmentsToWellsService.getDepartmentsToWells());

    }

}
