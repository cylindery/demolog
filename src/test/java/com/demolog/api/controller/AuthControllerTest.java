package com.demolog.api.controller;

import com.demolog.api.domain.User;
import com.demolog.api.repository.SessionRepository;
import com.demolog.api.repository.UserRepository;
import com.demolog.api.request.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void test1() throws Exception {
        // given
        userRepository.save(User.builder()
                .email("jxwxnjx@gmail.com")
                .name("주원주")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("jxwxnjx@gmail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 1개 생성")
    void test2() throws Exception {
        // given
        User user = userRepository.save(User.builder()
                .email("jxwxnjx@gmail.com")
                .name("주원주")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("jxwxnjx@gmail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(1L, user.getSessions().size());
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 하나의 세션 객체 응답")
    void test3() throws Exception {
        // given
        User user = userRepository.save(User.builder()
                .email("jxwxnjx@gmail.com")
                .name("주원주")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("jxwxnjx@gmail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andDo(print());
    }
}