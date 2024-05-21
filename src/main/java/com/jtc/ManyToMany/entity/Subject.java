package com.jtc.ManyToMany.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subject {
    @Id
    private Long subjectId;

    private String subjectName;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Set<Student> subjectStudent;
    @OneToOne
    @JoinTable(name = "subject_teacher", joinColumns = @JoinColumn(
            name =  "subject_id"),inverseJoinColumns = @JoinColumn(name ="teacher_id"))
    private Teacher teacher;
}
