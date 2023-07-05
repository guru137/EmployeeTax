package com.EmpolyeeTax.Calculation.Repo;

import com.EmpolyeeTax.Calculation.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
}
