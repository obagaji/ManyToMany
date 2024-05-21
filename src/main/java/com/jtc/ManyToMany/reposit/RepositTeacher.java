package com.jtc.ManyToMany.reposit;

import com.jtc.ManyToMany.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositTeacher extends JpaRepository<Teacher,Long> {
}
