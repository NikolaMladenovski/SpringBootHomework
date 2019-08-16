package com.example.SpringBootHomework.controller;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.TweetRepository;
import com.example.SpringBootHomework.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class TweetRestControllerTest {

    private static final String URL_GET_TWEET_BY_ID = "/api/tweet/getTweetById";
    private static final String URL_SAVE_TWEET = "/api/tweet/saveTweet";
    private static final String URL_DELETE_USER = "/api/tweet/deleteTweet";
    private static final String URL_DELETE_USER_TWEETS = "/api/tweet/deleteUserTweets";
    private static final String URL_UPDATE_TWEET_CONTENT = "/api/tweet/updateTweetContent";

    private Gson gson;


    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void shouldGetTweetById() throws Exception {
        User nikola = new User("nikola12345", "pass", "nikola@yahoo.com");
        nikola.setId(10L);
        Tweet tweet1 = new Tweet("content1");
        tweet1.setTweetId(5L);
        tweet1.setUser(null);
        tweetRepository.save(tweet1);
        MvcResult mvcResult = mockMvc.perform(post(URL_GET_TWEET_BY_ID)
                .param("tweetId",tweet1.getTweetId().toString()))
                .andExpect(status().isCreated()).andReturn();

        String foundTweetJson = mvcResult.getResponse().getContentAsString();
        Tweet foundTweet = new ObjectMapper().readValue(foundTweetJson, Tweet.class);

        Assertions.assertThat(foundTweet.getContent()).isEqualTo(tweet1.getContent());
        Assertions.assertThat(foundTweet.getTweetId()).isEqualTo(tweet1.getTweetId());

    }

    @Test
    public void shouldSaveTweet() throws Exception {
        User nikola = new User("nikola12345", "pass", "nikola@yahoo.com");
        nikola.setId(6L);
        Tweet tweet1 = new Tweet("content1");
        //tweet1.setTweetId(10L);
        //tweet1.setUser(nikola);

        String jsonTweet = gson.toJson(tweet1);

        MvcResult mvcResult = mockMvc.perform(post(URL_SAVE_TWEET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonTweet))
                .andExpect(status().isCreated()).andReturn();

        String foundTweetJson = mvcResult.getResponse().getContentAsString();
        Tweet foundTweet = new ObjectMapper().readValue(foundTweetJson, Tweet.class);

        Assertions.assertThat(foundTweet.getContent()).isEqualTo(tweet1.getContent());
        Assertions.assertThat(foundTweet.getTweetId()).isEqualTo(tweet1.getTweetId());

    }

    @Test
    public void shouldGetUsersTweetedLastMonth() {
    }

    @Test
    public void shouldUpdateTweetContent() throws Exception {
        Tweet tweet = new Tweet("nikolaTwets");
         tweetRepository.save(tweet);
        String newTweetContent = "newTweetContent";
        mockMvc.perform(put(URL_UPDATE_TWEET_CONTENT)
                .param("tweetId", tweet.getTweetId().toString())
                .param("newTweetContent", newTweetContent))
                .andExpect(status().isOk());

        Optional<Tweet> foundUser = tweetRepository.findById(tweet.getTweetId());

        Assertions.assertThat(foundUser.get().getContent()).isEqualTo(newTweetContent);
    }

    //todo
    @Test
    public void shouldDeleteTweetsForUser() throws Exception {
        Tweet tweet = new Tweet("TweetContent");
        User userNikola = new User("username2324","password","nikola@yahoo.com");
        userRepository.save(userNikola);
        userNikola.getListOfTweets().add(tweet);
        userNikola.setId(10L);
        tweet.setUser(userNikola);
        tweet.setTweetId(20L);

        tweetRepository.save(tweet);
        String userJsonString = gson.toJson(userNikola);
        mockMvc.perform(delete(URL_DELETE_USER)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJsonString))
                .andExpect(status().isNoContent());
        Optional<Tweet> foundTweet = tweetRepository.findById(tweet.getTweetId());
        Assertions.assertThat(foundTweet).isEmpty();
    }
}