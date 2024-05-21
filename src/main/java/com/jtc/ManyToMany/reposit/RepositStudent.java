package com.jtc.ManyToMany.reposit;

import com.jtc.ManyToMany.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositStudent extends JpaRepository<Student,Long> {
}
