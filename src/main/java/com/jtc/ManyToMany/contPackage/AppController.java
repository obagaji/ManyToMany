package com.jtc.ManyToMany.contPackage;

import com.jtc.ManyToMany.entity.Student;
import com.jtc.ManyToMany.servicePackage.StudentTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/student").build(saveStudents);
      HttpHeaders headers = new HttpHeaders();
      headers.setLocation(uri);
      return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(saveStudents);
    }
    @GetMapping("/find")
    public ResponseEntity<List<Student>> getStudent()
    {
        List<Student> studentArray = studentTeacher.gatSavedStudent();
        return new ResponseEntity<>(studentArray,HttpStatus.OK);
    }
    @GetMapping("/update/{studentId}")
    public ResponseEntity<String> updateDatabase(@PathVariable("studentId") long studentId)
    {
        studentTeacher.removeStudent(studentId);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/update/studentId").build(studentId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(url);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).build();

    }

}
