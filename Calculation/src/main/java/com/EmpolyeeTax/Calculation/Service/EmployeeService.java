package com.EmpolyeeTax.Calculation.Service;

import com.EmpolyeeTax.Calculation.DTO.TaxDto;
import com.EmpolyeeTax.Calculation.Entity.Employee;
import com.EmpolyeeTax.Calculation.Repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo repo;


    public void saveEmployee(Employee employee) {
        repo.save(employee);
    }

    public List<TaxDto> calculateTaxDeductionsForCurrentFinancialYear() {
        List<Employee> employees = repo.findAll();
        List<TaxDto> taxDeductions = new ArrayList<>();

        // Calculate tax deductions for each employee
        for (Employee employee : employees) {
            TaxDto taxDeductionDTO = new TaxDto();
            taxDeductionDTO.setEmployeeId(employee.getEmployeeId());
            taxDeductionDTO.setFirstName(employee.getFirstName());
            taxDeductionDTO.setLastName(employee.getLastName());
            taxDeductionDTO.setYearlySalary(calculateYearlySalary(employee));
            taxDeductionDTO.setTaxAmount(calculateTaxAmount(employee));
            taxDeductionDTO.setCessAmount(calculateCessAmount(employee));

            taxDeductions.add(taxDeductionDTO);
        }

        return taxDeductions;
    }


    private double calculateYearlySalary(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        LocalDate financialYearStart = currentDate.withMonth(4).withDayOfMonth(1);
        LocalDate financialYearEnd = financialYearStart.plusYears(1).minusDays(1);


        LocalDate doj = employee.getDoj();
        long numberOfYears = financialYearStart.getYear()-doj.getYear();
        LocalDate dojAfterFinancialYearStart = doj.plusYears(numberOfYears).isAfter(financialYearStart) ? doj.plusYears(numberOfYears) : financialYearStart;

        LocalDate lastDayOfFinancialYear = dojAfterFinancialYearStart.isBefore(financialYearEnd) ? financialYearEnd : dojAfterFinancialYearStart;
        long totalMonths = ChronoUnit.MONTHS.between(dojAfterFinancialYearStart, lastDayOfFinancialYear) + 1;
        int totalDaysInLastMonth = dojAfterFinancialYearStart.getDayOfMonth();

        double lengthOfMonth=dojAfterFinancialYearStart.lengthOfMonth();
        double salaryPerMonth = employee.getSalary() / 12;
        double totalSalary = salaryPerMonth * totalMonths;


        if (totalDaysInLastMonth > 1&&!dojAfterFinancialYearStart.isEqual(financialYearStart)) {
            totalSalary += salaryPerMonth * (totalDaysInLastMonth /lengthOfMonth );
        }

        return totalSalary;
    }

    private double calculateTaxAmount(Employee employee) {
        double yearlySalary = calculateYearlySalary(employee);

        double taxAmount = 0;

        if (yearlySalary <= 250000) {
            // No tax
        } else if (yearlySalary <= 500000) {
            taxAmount = (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            taxAmount = 12500 + (yearlySalary - 500000) * 0.1;
        } else {
            taxAmount = 12500 + 50000 + (yearlySalary - 1000000) * 0.2;
        }

        return taxAmount;
    }

    private double calculateCessAmount(Employee employee) {
        double yearlySalary = calculateYearlySalary(employee);
        double cessAmount = 0;

        if (yearlySalary > 2500000) {
            double taxableAmount = yearlySalary - 2500000;
            cessAmount = taxableAmount * 0.02;
        }

        return cessAmount;
    }
}
