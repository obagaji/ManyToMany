package com.jtc.ManyToMany.contPackage;

import com.jtc.ManyToMany.configClass.JwtTokenGenerator;
import com.jtc.ManyToMany.configClass.UserDetailClass;
import com.jtc.ManyToMany.configClass.UserDetailClassService;
import com.jtc.ManyToMany.dto.LoginDto;
import com.jtc.ManyToMany.dto.UsersDto;
import com.jtc.ManyToMany.entity.Student;
import com.jtc.ManyToMany.entity.Users;
import com.jtc.ManyToMany.mapperClasses.ManyToManyMapper;
import com.jtc.ManyToMany.reposit.UsersRepository;
import com.jtc.ManyToMany.servicePackage.AuthService;
import com.jtc.ManyToMany.servicePackage.StudentTeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AppController {


    private final StudentTeacher studentTeacher;

    @PostMapping("/token")
    public ResponseEntity<String>testToken(@PathVariable("token") String token )
    {
        return ResponseEntity.ok(token + "   token received");
    }


    @PostMapping("/student")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student)
    {
      Student saveStudents =  studentTeacher.savingStudent(student);

      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/student").build(saveStudents);

      HttpHeaders headers = new HttpHeaders();

      headers.setLocation(uri);

      return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(saveStudents);
    }

    @GetMapping("/student/all")
    public ResponseEntity<List<Student>> getStudent()
    {
        List<Student> studentArray = studentTeacher.gatSavedStudent();

        return new ResponseEntity<>(studentArray,HttpStatus.OK);
    }

    @DeleteMapping("/remove/{studentId}")
    public ResponseEntity<String> updateDatabase(@PathVariable("studentId") long studentId)
    {
        studentTeacher.removeStudent(studentId);

        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/remove/studentId").build(studentId);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setLocation(url);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).build();

    }

}
