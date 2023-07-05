package com.EmpolyeeTax.Calculation.DTO;


import lombok.Data;

@Data
public class TaxDto {

    private String employeeId;
    private String firstName;

    private String lastName;

    private double yearlySalary;
    private double taxAmount;
    private double cessAmount;




}
