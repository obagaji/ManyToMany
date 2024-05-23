package com.jtc.ManyToMany.reposit;

import com.jtc.ManyToMany.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositStudent extends JpaRepository<Student,Long> {
}
