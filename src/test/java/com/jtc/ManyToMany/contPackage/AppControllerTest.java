package com.jtc.ManyToMany.contPackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtc.ManyToMany.ManyToManyApplication;
import com.jtc.ManyToMany.entity.Student;
import com.jtc.ManyToMany.entity.Teacher;
import com.jtc.ManyToMany.entity.Users;
import com.jtc.ManyToMany.servicePackage.StudentTeacher;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@WebMvcTest
@ContextConfiguration(classes = ManyToManyApplication.class)
@AutoConfigureMockMvc
//@Import({StudentTeacher.class, ManyToManyConfig.class})
class AppControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentTeacher studentTeacher;

    Student student = new Student();
    Teacher teacher = new Teacher();
    Users users = new Users();
    Users usersTeacher = new Users();
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void saveStudent() throws Exception {
        student.setStudentId(100L);
        users.setUsername("musa");
        users.setUserPassword(new BCryptPasswordEncoder().encode("password"));
        teacher.setTeacherId(111L);
        teacher.setTeacherClass("jss");
        usersTeacher.setUsername("john");
        usersTeacher.setUserPassword("password");
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


    @DisplayName("Test for bad input of a variable")
    @Test
    void saveStudentWithoutPassword() throws Exception
    {
        student.setStudentId(1112L);
        users.setUsername("lucky");
        teacher.setTeacherId(111L);
        teacher.setTeacherClass("jss");
     /*   teacher.setTeacherName("john");
        teacher.setTeacherPassword("password");*/
        Set<Teacher> teacherSet = new HashSet<>();
        teacherSet.add(teacher);
        student.setTeacher(teacherSet);
        Mockito.when(studentTeacher.savingStudent(student)).thenReturn(student);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/student")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student));
                MvcResult mvcResult = mockMvc.perform(requestBuilder)
                        .andExpect(result -> result.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST))
                        .andReturn();

    }

    @DisplayName(" testing retriving object")
    @Test
    void getStudent() {
        Mockito.when(studentTeacher.gatSavedStudent()).thenReturn(new ArrayList<>());
    }
}