package com.jtc.ManyToMany.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teacherId;
    @Column(name = "teacher_name",nullable = true)
    private String teacherName;
    @Column(name = "teacher_class")
    private String teacherClass;
    @Column(nullable = false)
    private String teacherPassword;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_userId")
    private Users teacherUsers;
    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "teacher")
    private Set<Student> student;
}
