package com.example.bankmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "account number should not be empty")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$")
    @Column(columnDefinition = "VARCHAR(19) NOT NULL UNIQUE")
    private String accountNumber;
    @PositiveOrZero(message = "balance should be positive")
    @Column(columnDefinition = "INT NOT NULL CHECK(balance>=0)")
    private Double balance;
//    @AssertFalse(message = "account should be disabled by default")
    @Column(columnDefinition = "BOOLEAN NOT NULL")
    private Boolean isActive;

    @ManyToOne
//    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;

}
