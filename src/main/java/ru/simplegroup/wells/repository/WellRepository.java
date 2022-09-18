package ru.simplegroup.wells.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simplegroup.wells.entity.Well;

public interface WellRepository extends JpaRepository<Well, Long>, DataRepository<Well> {

}
