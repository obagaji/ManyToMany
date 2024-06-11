package com.jtc.ManyToMany.servicePackage;

import com.jtc.ManyToMany.dto.LoginDto;
import com.jtc.ManyToMany.entity.Student;
import com.jtc.ManyToMany.entity.Subject;
import com.jtc.ManyToMany.entity.Teacher;
import com.jtc.ManyToMany.entity.Users;
import com.jtc.ManyToMany.reposit.RepositStudent;
import com.jtc.ManyToMany.reposit.RepositTeacher;
import com.jtc.ManyToMany.reposit.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RepositStudent repositStudent;
    private final UsersRepository usersRepository;
    private final RepositTeacher repositTeacher;

    public Subject saveSubjects()
    {
        Subject subject = new Subject();

        subject.setSubjectId(011L);

        subject.setSubjectName("maths");

        return null;
    }
    public Users registerUser(Users users){

        users.setUserId(11L);

        users.setUsername("musa");

        users.setUserPassword("paasword");

        users.setRoles("Admin");

    return usersRepository.save(users);
    }

}
