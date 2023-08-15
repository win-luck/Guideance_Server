package com.spring.guideance.service;

import com.spring.guideance.user.dto.request.CreateUserDto;
import com.spring.guideance.user.dto.request.UpdateUserDto;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.user.service.UserService;
import com.spring.guideance.util.exception.UserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입(){
        // given
        String name = "test";
        String email = "";

        // when
        userService.createUser(new CreateUserDto(name, email));

        // then
        assertEquals(userRepository.findByEmail(email).orElse(null).getName(), name);
    }

    @Test
    public void 중복이메일체크() throws UserException{
        // given
        String name = "test";
        String email = "1";
        userService.createUser(new CreateUserDto(name, email));

        // when
        // 예외가 발생해야 한다.
        assertThrows(UserException.class, () -> userService.createUser(new CreateUserDto(name, email)));
    }

    @Test
    public void 로그인(){
        // given
        String name = "test";
        String email = "123";
        userService.createUser(new CreateUserDto(name, email));

        // when
        ResponseUserDto userDto = userService.login(email);

        // then
        assertEquals(userDto.getName(), name);
    }

    @Test
    public void 회원정보수정(){
        // given
        String name = "test";
        String email = "1234";
        Long id = userService.createUser(new CreateUserDto(name, email));

        // when
        String newName = "newTest";
        userService.updateUser(new UpdateUserDto(id, newName));

        // then
        assertEquals(userRepository.findByEmail("1234").orElse(null).getName(), newName);
    }

    @Test
    public void 회원정보삭제(){
        // given
        String name = "test";
        String email = "12345";
        userService.createUser(new CreateUserDto(name, email));

        // when
        userService.deleteUser("12345");

        // then
        // 예외가 발생해야 한다.
        assertThrows(UserException.class, () -> userService.login(email));
    }
}
