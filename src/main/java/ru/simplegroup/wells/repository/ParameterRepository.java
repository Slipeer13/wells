package ru.simplegroup.wells.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simplegroup.wells.entity.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {

}
