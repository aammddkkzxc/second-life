package com.example.secondlife;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.secondlife.domain.user.controller.api.UserController;
import com.example.secondlife.domain.user.service.UserSearchService;
import com.example.secondlife.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = UserController.class)
public class BasicCRUDTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected UserSearchService userSearchService;

    protected <T> ResultActions doPost(String url, T request) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );
    }

    protected ResultActions doGet(String url, Long id) throws Exception {
        return mockMvc.perform(get(url, id)
                .contentType(MediaType.APPLICATION_JSON)
        );
    }

    protected <T> ResultActions doPatch(String url, Long id, T request) throws Exception {
        return mockMvc.perform(patch(url, id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );
    }

    protected ResultActions doDelete(String url, Long id) throws Exception {
        return mockMvc.perform(delete(url, id)
                .contentType(MediaType.APPLICATION_JSON)
        );
    }
}