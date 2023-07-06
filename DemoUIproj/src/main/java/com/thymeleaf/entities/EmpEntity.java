package com.thymeleaf.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "emp_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private int id;

//    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "This isn't valid. It accepts only alphabets.")
    @Column(name = "emp_name")
    private String name;

    @Column(name = "emp_email", unique = true)
    private String email;

//    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",message = "password must be accepts 8 characters.")
    @Column(name = "emp_password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
