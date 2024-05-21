package com.jtc.ManyToMany.contPackage;

import com.jtc.ManyToMany.entity.Student;
import com.jtc.ManyToMany.servicePackage.StudentTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AppController {


    private final StudentTeacher studentTeacher;

    @PostMapping("/student")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student)
    {
      Student saveStudents =  studentTeacher.savingStudent(student);

      return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @GetMapping("/find")
    public ResponseEntity<List<Student>> getStudent()
    {
        List<Student> studentArray = studentTeacher.gatSavedStudent();
        return new ResponseEntity<>(studentArray,HttpStatus.OK);
    }

}
