package com.jtc.ManyToMany.configClass;

import com.jtc.ManyToMany.entity.Users;
import com.jtc.ManyToMany.reposit.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserDetailClassService implements UserDetailsService {
    @Autowired
    private  UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> users = usersRepository.findByUsername(username);
        return users.map(UserDetailClass::new).orElseThrow();

    }
}
