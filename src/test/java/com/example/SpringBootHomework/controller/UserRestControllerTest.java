package com.example.SpringBootHomework.controller;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.TweetRepository;
import com.example.SpringBootHomework.repository.UserRepository;
import com.example.SpringBootHomework.service.UserService;
import com.google.gson.Gson;
import jdk.net.SocketFlow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {
    private static final String URL_GET_USER_TWEETS = "/api/user/getUserTweets";
    private static final String URL_DELETE_USER = "api/user/deleteUser";

//    @Autowired
//    private UserService userService;

    private Gson gson;

    protected MockMvc mockMvc;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setUp() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getUserTweets() throws Exception {
        User nikola = new User("nikola12345", "pass", "nikola@yahoo.com");
        Tweet tweet1 = new Tweet("tweet1 content");
        Tweet tweet2 = new Tweet("tweet2 contetnt");
        nikola.getListOfTweets().add(tweet1);
        nikola.getListOfTweets().add(tweet2);
//        tweet1.setUser(nikola);
//        tweet2.setUser(nikola);
        userRepository.save(nikola);
        Set<Tweet> expectedTweets = Stream.of(tweet1, tweet2).collect(Collectors.toSet());
//        Mockito.when(userService.getUserTweets(nikola.getId())).thenReturn(expectedTweets);


        String jsonUserExpectedTweets = gson.toJson(expectedTweets);

        mockMvc.perform(get(URL_GET_USER_TWEETS)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .param("userId", nikola.getId().toString()))
                .andExpect(status().isOk())

                .andExpect(content().json(jsonUserExpectedTweets));
    }

    @Test
    public void getUserTweetsForGivenDate() {
    }

    @Test
    public void saveUser() {
    }

    @Test
    public void updateUserPassword() {
    }

    @Test
    public void deleteUser() {
    }
}