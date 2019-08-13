package com.example.SpringBootHomework.controller;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.UserRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserRestControllerTest {
    private static final String URL_GET_USER_TWEETS = "/getUserTweets";
    private static final String URL_DELETE_USER = "/deleteUser";

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
        User nikola = new User("nikola","pass","nikola@yahoo.com");
        Tweet tweet1 = new Tweet("tweet1 content");
        Tweet tweet2 = new Tweet("tweet2 contetnt");
        nikola.getListOfTweets().add(tweet1);
        nikola.getListOfTweets().add(tweet2);
        tweet1.setUser(nikola);
        tweet2.setUser(nikola);
        userRepository.save(nikola);
        String jsonUserStringOfUser = gson.toJson(nikola);

        mockMvc.perform(post(URL_GET_USER_TWEETS)
                .contentType(MediaType.APPLICATION_JSON_UTF8));

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