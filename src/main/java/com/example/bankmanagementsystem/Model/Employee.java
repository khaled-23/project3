package com.example.bankmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "position can not be null")
    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String position;
    @Positive(message = "salary should be a positive number")
    @Column(columnDefinition = "INT NOT NULL CHECK(salary>0)")
    private Double salary;


    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;
}
