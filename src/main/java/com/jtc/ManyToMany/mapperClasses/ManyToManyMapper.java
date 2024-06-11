package com.jtc.ManyToMany.mapperClasses;


import com.jtc.ManyToMany.dto.LoginDto;
import com.jtc.ManyToMany.dto.UsersDto;
import com.jtc.ManyToMany.entity.Teacher;
import com.jtc.ManyToMany.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ManyToManyMapper {

    ManyToManyMapper INSTANCE = Mappers.getMapper(ManyToManyMapper.class);

    Users loginDtoToUsers(LoginDto loginDto);

    LoginDto modelUsersToLonginDto(Users users);

    UsersDto userDto (Users users);

    Users users (UsersDto usersDto);

    Teacher teacherLogin(LoginDto loginDto );

    LoginDto loginDto(Teacher teacher);
}
