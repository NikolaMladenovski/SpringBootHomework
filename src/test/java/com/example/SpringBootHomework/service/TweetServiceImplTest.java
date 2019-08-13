package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.TweetRepository;
import org.apache.el.stream.Stream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceImplTest {

    @InjectMocks
    private TweetServiceImpl tweetServiceImpl;

    @Mock
    private TweetRepository tweetRepository;


    @Before
    public void setUp() {
        tweetServiceImpl = new TweetServiceImpl(tweetRepository);
    }

    @Test
    public void shouldGetTweetById() {
        Tweet tweet = new Tweet("TestTweetId");
        Mockito.when(tweetRepository.findById(tweet.getTweetId())).thenReturn(java.util.Optional.of(tweet));
        Tweet foundTweet = tweetServiceImpl.getTweetById(tweet.getTweetId());
        assertEquals(tweet.getContent(), foundTweet.getContent());
        assertEquals(tweet.getTweetId(), foundTweet.getTweetId());
        assertEquals(tweet.getDateOfCreation(), foundTweet.getDateOfCreation());
    }

    @Test
    public void shouldSaveTweet() {
        //arrange
        Tweet tweet = new Tweet("TestTweetSave");
        Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);

        //act
        Tweet foundTweet = tweetServiceImpl.saveTweet(tweet);

        //assert
        assertEquals(tweet.getContent(), foundTweet.getContent());
        assertEquals(tweet.getTweetId(), foundTweet.getTweetId());
        assertEquals(tweet.getDateOfCreation(), foundTweet.getDateOfCreation());
    }

    @Test
    public void getUsersThatTweetedLastMonth() {
        User nikola = new User("nikola","pass","nikola@yahoo.com");
        User dimitar = new User("dimitar","pass","dimitar@yahoo.com");

        Tweet tweet1 = new Tweet("content1");
        Tweet tweet2 = new Tweet("content2");

        tweet1.setDateOfCreation(LocalDate.of(2019,7,15));
        tweet2.setDateOfCreation(LocalDate.of(2019,8,15));

        nikola.getListOfTweets().add(tweet1);
        tweet1.setUser(nikola);

        dimitar.getListOfTweets().add(tweet2);
        tweet2.setUser(dimitar);

        //Set<Tweet> tweetSet =
        //Mockito.when(tweetServiceImpl.getUsersThatTweetedLastMonth()).thenReturn()


    }


    @Test
    public void updateTweetContent() {

    }

    @Test
    public void deleteTweetsForUser() {
    }
}