package com.EmpolyeeTax.Calculation.Entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;





@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Employee ID is mandatory")
    @Column(nullable = false)
    private String employeeId;


    @NotBlank(message = "First Name is mandatory")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    @Column(nullable = false)
    private String lastName;

    @Email(message = "Invalid Email format")
    @NotBlank(message = "Email is mandatory")
    @Column(nullable = false)
    private String email;

    @NotEmpty(message = "Phone Numbers must not be empty")
    @ElementCollection
    private List<String> phoneNumbers;





    @NotNull(message = "DOJ is mandatory")
    @Column(nullable = false)
    private LocalDate doj;

    @Positive(message = "Salary must be a positive value")
    private double salary;





    public Employee(String employeeId, String firstName, String lastName, String email, List<String> phoneNumbers, LocalDate doj, double salary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumbers = phoneNumbers;
        this.doj = doj;
        this.salary = salary;
    }









}
