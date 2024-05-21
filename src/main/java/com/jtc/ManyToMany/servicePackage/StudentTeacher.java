package com.jtc.ManyToMany.servicePackage;

import com.jtc.ManyToMany.entity.Student;
import com.jtc.ManyToMany.entity.Teacher;
import com.jtc.ManyToMany.reposit.RepositStudent;
import com.jtc.ManyToMany.reposit.RepositTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class StudentTeacher {


    private final RepositTeacher repositTeacher;
    private final RepositStudent studentRepository;

    public Student savingStudent(Student student)
    {
        return null;
    }

    public List<Student> gatSavedStudent()
    {
            return studentRepository.findAll();
    }

}
