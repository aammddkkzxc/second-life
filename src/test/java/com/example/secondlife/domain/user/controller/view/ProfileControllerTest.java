package com.example.secondlife.domain.user.controller.view;

import com.example.secondlife.domain.BasicCRUDTest;
import com.example.secondlife.domain.post.service.PostSearchService;
import com.example.secondlife.domain.user.service.UserSearchService;
import org.springframework.boot.test.mock.mockito.MockBean;

public class ProfileControllerTest extends BasicCRUDTest {

    @MockBean
    private UserSearchService userSearchService;

    @MockBean
    private PostSearchService postSearchService;


}