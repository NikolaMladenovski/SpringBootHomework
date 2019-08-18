package com.example.SpringBootHomework.controller;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.TweetRepository;
import com.example.SpringBootHomework.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {
    private static final String URL_GET_USER_TWEETS = "/api/user/getUserTweets";
    private static final String URL_DELETE_USER = "/api/user/deleteUser";
    private static final String URL_SAVE_USER = "/api/user/saveUser";
    private static final String URL_UPDATE_USER_PASSWORD = "/api/user/updateUserPassword";
    private static final String URL_GET_USER_TWEETS_FOR_DATE = "/api/user/getUserTweetsForDate";

//    @Autowired
//    private UserService userService;

    private Gson gson;


    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Before
    public void setUp() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getUserTweets() throws Exception {
        User nikola = new User("nikola12345", "pass", "nikola@yahoo.com");
        nikola.setId(5L);
        userRepository.save(nikola);
        Tweet tweet1 = new Tweet("tweet1 content");

        Tweet tweet2 = new Tweet("tweet2 contetnt");

        nikola.getListOfTweets().add(tweet1);
        nikola.getListOfTweets().add(tweet2);
        tweet1.setUser(nikola);
        tweet2.setUser(nikola);
        tweet1.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 7, 25)));
        tweet2.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 8, 15)));
        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);

        Set<Tweet> expectedTweets = Stream.of(tweet1, tweet2).collect(Collectors.toSet());

        MvcResult mvcResult = mockMvc.perform(get(URL_GET_USER_TWEETS)
                .param("userId", nikola.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        String foundUserJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Tweet[] foundTweets = objectMapper.readValue(foundUserJson, Tweet[].class);
        System.out.println(foundUserJson);

        Assert.assertEquals(expectedTweets.size(), foundTweets.length);

    }


    @Test
    public void getUserTweetsForGivenDate() throws Exception {
        User nikola = new User("nikola123213", "pass", "nikola@yahoo.com");
       // nikola.setId(10L);
        Tweet tweet1 = new Tweet("content1");
        Tweet tweet2 = new Tweet("content2");
        Tweet tweet3 = new Tweet("content3");

        tweet1.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 7, 25)));
        tweet2.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 8, 15)));
        tweet3.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 8, 15)));



        nikola.getListOfTweets().add(tweet1);
        nikola.getListOfTweets().add(tweet2);
        nikola.getListOfTweets().add(tweet3);
        userRepository.save(nikola);
        tweet1.setUser(nikola);
        tweet2.setUser(nikola);
        tweet3.setUser(nikola);

        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);
        tweetRepository.save(tweet3);


        MvcResult mvcResult =  mockMvc.perform(get(URL_GET_USER_TWEETS_FOR_DATE)
                .param("userId", nikola.getId().toString())
                .param("date", Date.valueOf(LocalDate.of(2019, 8, 15)).toString()))
                .andExpect(status().isOk())
                .andReturn();

        String foundUserJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Tweet[] foundTweets = objectMapper.readValue(foundUserJson, Tweet[].class);
        System.out.println(foundUserJson);

        Assert.assertEquals(2, foundTweets.length);


    }

    @Test
    public void shouldSaveUser() throws Exception {
        User nikola = new User("nikola12345", "pass", "nikola@yahoo.com");
        nikola.setId(6L);
        String jsonUser = gson.toJson(nikola);

        MvcResult mvcResult = mockMvc.perform(post(URL_SAVE_USER)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonUser))
                .andExpect(status().isCreated()).andReturn();

        String foundUserJson = mvcResult.getResponse().getContentAsString();
        User foundUser = new ObjectMapper().readValue(foundUserJson, User.class);

        Assertions.assertThat(foundUser.getUsername()).isEqualTo(nikola.getUsername());
        Assertions.assertThat(foundUser.getPassword()).isEqualTo(nikola.getPassword());
        Assertions.assertThat(foundUser.getEmail()).isEqualTo(nikola.getEmail());
    }

    @Test
    public void shouldUpdateUserPassword() throws Exception {
        User nikola = new User("nikola12345", "pass", "nikola@yahoo.com");
        userRepository.save(nikola);
        String newUserPassword = "newPass";
        mockMvc.perform(put(URL_UPDATE_USER_PASSWORD)
                .param("userId", nikola.getId().toString())
                .param("newUserPassword", newUserPassword))
                .andExpect(status().isOk());

        Optional<User> foundUser = userRepository.findById(nikola.getId());

        Assertions.assertThat(foundUser.get().getPassword()).isEqualTo(newUserPassword);
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User nikola = new User("nikola12345", "pass", "nikola@yahoo.com");
        userRepository.save(nikola);
        mockMvc.perform(delete(URL_DELETE_USER)
                .param("userId", nikola.getId().toString()))
                .andExpect(status().isNoContent());
        Optional<User> foundUser = userRepository.findById(nikola.getId());
        Assertions.assertThat(foundUser).isEmpty();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenDeleteUser() throws Exception {
        mockMvc.perform(delete(URL_DELETE_USER)
                .param("userId", Long.toString(400L)))
                .andExpect(status().isNoContent());
    }
}