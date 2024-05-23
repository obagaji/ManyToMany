package com.jtc.ManyToMany.contPackage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtc.ManyToMany.ManyToManyApplication;
import com.jtc.ManyToMany.configClass.ManyToManyConfig;
import com.jtc.ManyToMany.entity.Student;
import com.jtc.ManyToMany.entity.Teacher;
import com.jtc.ManyToMany.reposit.RepositStudent;
import com.jtc.ManyToMany.reposit.RepositTeacher;
import com.jtc.ManyToMany.servicePackage.StudentTeacher;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest
@ContextConfiguration(classes = ManyToManyApplication.class)
@AutoConfigureMockMvc
//@Import({StudentTeacher.class, ManyToManyConfig.class})
class AppControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RepositStudent repositStudent;
    @MockBean
    RepositTeacher repositTeacher;

    @MockBean
    StudentTeacher studentTeacher;

    Student student = new Student();
    Teacher teacher = new Teacher();
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();



    @Test
    void saveStudent() throws Exception {
        student.setStudentId(100L);
        student.setStudentName("musa");
        student.setStudentPassword(new BCryptPasswordEncoder().encode("password"));
        teacher.setTeacherId(111L);
        teacher.setTeacherClass("jss");
        teacher.setTeacherName("john");
        teacher.setTeacherPassword("password");
        Set<Teacher> teacherSet = new HashSet<>();
        teacherSet.add(teacher);
        student.setTeacher(teacherSet);
        Mockito.when(studentTeacher.savingStudent(student)).thenReturn(student);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/student")
                .content(objectMapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(result -> {result.getResponse().setStatus(HttpServletResponse.SC_ACCEPTED);})
                .andReturn();

    }

    @Test
    void getStudent() {
    }
}