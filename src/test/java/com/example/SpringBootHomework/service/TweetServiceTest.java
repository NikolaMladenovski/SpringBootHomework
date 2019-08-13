package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.repository.TweetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetServiceImpl tweetServiceImpl;


    @Test
    public void shouldGetTweetById() {
        Tweet tweet = new Tweet("tweet content");

        tweetRepository.save(tweet);
        Tweet foundTweet = tweetServiceImpl.getTweetById(tweet.getTweetId());
        assertEquals(tweet.getDateOfCreation(), foundTweet.getDateOfCreation());
        assertEquals(tweet.getContent(), tweet.getContent());
    }

    @Test
    public void saveTweet() {
    }

    @Test
    public void getUsersThatTweetedLastMonth() {
    }

    @Test
    public void updateTweetContent() {
    }

    @Test
    public void deleteTweetsForUser() {
    }
}