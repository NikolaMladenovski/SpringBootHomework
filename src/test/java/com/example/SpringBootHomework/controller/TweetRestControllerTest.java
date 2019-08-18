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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TweetRestControllerTest {

    private static final String URL_GET_TWEET_BY_ID = "/api/tweet/getTweetById";
    private static final String URL_SAVE_TWEET = "/api/tweet/saveTweet";
    private static final String URL_DELETE_USER = "/api/tweet/deleteTweet";
    private static final String URL_DELETE_USER_TWEETS = "/api/tweet/deleteUserTweets";
    private static final String URL_UPDATE_TWEET_CONTENT = "/api/tweet/updateTweetContent";
    private static final String URL_GET_USERS_TWEETED_LAST_MONTH = "/api/tweet/getUserTweetsFromLastMonth";

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
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


    }

    @Test
    public void shouldGetTweetById() throws Exception {
        Tweet tweet1 = new Tweet("content1");
        //tweet1.setTweetId(5L);
        tweet1.setUser(null);
        tweetRepository.save(tweet1);
        MvcResult mvcResult = mockMvc.perform(get(URL_GET_TWEET_BY_ID)
                .param("tweetId", tweet1.getTweetId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String foundTweetJson = mvcResult.getResponse().getContentAsString();
        Tweet foundTweet = new ObjectMapper().readValue(foundTweetJson, Tweet.class);

        Assertions.assertThat(foundTweet.getContent()).isEqualTo(tweet1.getContent());
        Assertions.assertThat(foundTweet.getTweetId()).isEqualTo(tweet1.getTweetId());

    }

    @Test
    public void shouldSaveTweet() throws Exception {
        User nikola = new User("nikola12345", "pass", "nikola@yahoo.com");
        userRepository.save(nikola);
        Tweet tweet1 = new Tweet("content1");
        tweet1.setUser(nikola);

        String jsonTweet = gson.toJson(tweet1);

        MvcResult mvcResult = mockMvc.perform(post(URL_SAVE_TWEET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonTweet)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        String foundTweetJson = mvcResult.getResponse().getContentAsString();
        Tweet foundTweet = new ObjectMapper().readValue(foundTweetJson, Tweet.class);

        Assertions.assertThat(foundTweet.getContent()).isEqualTo(tweet1.getContent());


    }

    @Test
    public void shouldGetUsersTweetedLastMonth() throws Exception {
        User nikola = new User("nikola12234", "pass", "nikola@yahoo.com");
        User dimitar = new User("dimitar12344", "pass", "dimitar@yahoo.com");

        Tweet tweet1 = new Tweet("content1");
        Tweet tweet2 = new Tweet("content2");

        tweet1.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 7, 15)));
        tweet2.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 8, 15)));

        nikola.getListOfTweets().add(tweet1);
        tweet1.setUser(nikola);

        dimitar.getListOfTweets().add(tweet2);
        tweet2.setUser(dimitar);
        userRepository.save(nikola);
        userRepository.save(dimitar);
        tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);



        MvcResult mvcResult = mockMvc.perform(get(URL_GET_USERS_TWEETED_LAST_MONTH)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String foundUsersJson = mvcResult.getResponse().getContentAsString();
        User[] foundUsers = new ObjectMapper().readValue(foundUsersJson, User[].class);

        Assertions.assertThat(foundUsers.length).isEqualTo(1);

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
        User userNikola = new User("username2324", "password", "nikola@yahoo.com");

        userNikola.getListOfTweets().add(tweet);
        userRepository.save(userNikola);
        tweet.setUser(userNikola);

        tweetRepository.save(tweet);

        String userJsonString = gson.toJson(userNikola);

        mockMvc.perform(delete(URL_DELETE_USER_TWEETS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJsonString))
                .andExpect(status().isNoContent());
        Optional<Tweet> foundTweet = tweetRepository.findById(tweet.getTweetId());
        Assertions.assertThat(foundTweet).isEmpty();
    }
}