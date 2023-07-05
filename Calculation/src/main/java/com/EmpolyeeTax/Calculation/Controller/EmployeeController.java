package com.EmpolyeeTax.Calculation.Controller;


import com.EmpolyeeTax.Calculation.DTO.TaxDto;
import com.EmpolyeeTax.Calculation.Entity.Employee;
import com.EmpolyeeTax.Calculation.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;



    @PostMapping("/saveEmployee")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody   Employee employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Return validation errors
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        // Create the employee entity and save it
        Employee employee = new Employee(
                employeeDTO.getEmployeeId(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail(),
                employeeDTO.getPhoneNumbers(),
                employeeDTO.getDoj(),
                employeeDTO.getSalary()
        );
        employeeService.saveEmployee(employee);

        return ResponseEntity.ok("Employee created successfully");
    }


    @GetMapping("/tax-deductions")
    public List<TaxDto> getEmployeeTaxDeductions() {
        return employeeService.calculateTaxDeductionsForCurrentFinancialYear();
    }
}
