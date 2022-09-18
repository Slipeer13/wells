package ru.simplegroup.wells.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simplegroup.wells.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String>, DataRepository<Department> {

}
