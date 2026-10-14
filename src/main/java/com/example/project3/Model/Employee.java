package com.example.project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(30) not null")
    private String position;
    @Column(columnDefinition = "varchar(30) not null")
    private BigDecimal salary;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    @PrimaryKeyJoinColumn
    private User user;
}
