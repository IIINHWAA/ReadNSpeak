package com.readnspeak.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readnspeak.dto.UserDto;
import com.readnspeak.entity.User;
import com.readnspeak.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUser_Success() throws Exception {
        // given: Mock 객체의 반환 값 설정
        UserDto userDto = new UserDto("testuser", "test@example.com", "hashedpassword123");
        User mockUser = new User();
        mockUser.setUser_id(1);
        mockUser.setUsername("testuser");
        mockUser.setEmail("test@example.com");

        when(userService.registerUser(any(UserDto.class))).thenReturn(mockUser);

        // when: POST 요청 실행
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto))) // 요청 본문
                .andExpect(status().isOk()) // 상태 코드 검증
                .andExpect(jsonPath("$.username").value("testuser")) // username 필드 검증
                .andExpect(jsonPath("$.email").value("test@example.com")); // email 필드 검증
    }
}

