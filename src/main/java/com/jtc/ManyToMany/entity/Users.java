package com.jtc.ManyToMany.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {


    @Id
    private long userId;
    private String userName;
    private String userPassword;
    private String roles;
}
