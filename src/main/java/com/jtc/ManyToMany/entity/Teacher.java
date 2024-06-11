package com.jtc.ManyToMany.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
        private long teacherId;

    @Column(name = "teacher_class")
        private String teacherClass;

    @OneToMany
    @JoinColumn(name = "teacher_subject_id")
        private List<Subject> teacherSubject;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "teacher")
        private Set<Student> student;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_userId")
        private Users teacherUsers;
}
