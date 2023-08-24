package com.spring.guideance.service;

import com.spring.guideance.user.dto.request.CreateUserDto;
import com.spring.guideance.user.dto.request.UpdateUserDto;
import com.spring.guideance.user.dto.response.ResponseUserDto;
import com.spring.guideance.user.repository.UserRepository;
import com.spring.guideance.user.service.UserService;
import com.spring.guideance.util.exception.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init(){
        userRepository.deleteAll();
        String name = "test";
        String keyCode = "a";
        userService.createUser(new CreateUserDto(name, keyCode, null));
    }

    @Test
    public void 회원가입(){
        assertEquals( 1, userRepository.count());
    }

    @Test
    public void 중복키코드체크() throws UserException{
        String name = "test";
        String keyCode = "a";
        assertThrows(UserException.class, () -> userService.createUser(new CreateUserDto(name, keyCode, null)));
    }

    @Test
    public void 로그인(){
        ResponseUserDto userDto = userService.login("a");
        assertEquals("test", userDto.getName());
    }

    @Test
    public void 회원정보수정(){
        Long id = userRepository.findAll().get(0).getId();
        userService.updateUser(new UpdateUserDto(id, "newName"), null);
        assertEquals(userRepository.findById(id).get().getName(), "newName");
    }

    @Test
    public void 회원정보삭제(){
        userService.deleteUser(userRepository.findAll().get(0).getId());
        assertEquals(0, userRepository.count());
    }
}
