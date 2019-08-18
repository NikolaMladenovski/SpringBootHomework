package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.exception.TweetNotFoundException;
import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.TweetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

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
    public void shouldGetUsersThatTweetedLastMonth() {
        User nikola = new User("nikola", "pass", "nikola@yahoo.com");
        User dimitar = new User("dimitar", "pass", "dimitar@yahoo.com");

        Tweet tweet1 = new Tweet("content1");
        Tweet tweet2 = new Tweet("content2");

        tweet1.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 7, 15)));
        tweet2.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 8, 15)));

        nikola.getListOfTweets().add(tweet1);
        tweet1.setUser(nikola);

        dimitar.getListOfTweets().add(tweet2);
        tweet2.setUser(dimitar);

        Set<Tweet> allTweetsSet = (Stream.of(tweet1, tweet2).collect(Collectors.toSet()));
        Mockito.when(tweetRepository.findAll()).thenReturn(allTweetsSet);
        Set<User> foundUsersThatTweetedLastMonth = new HashSet<>();
        try {
            foundUsersThatTweetedLastMonth = tweetServiceImpl.getUsersThatTweetedLastMonth();
        }catch (ParseException exception){
            System.out.println("Parse Exception......");
        }
        Set<User> expectedUsersThatTweetedLastMonth = (Stream.of(nikola)).collect(Collectors.toSet());

        assertEquals(expectedUsersThatTweetedLastMonth, foundUsersThatTweetedLastMonth);


    }

    @Test
    public void shouldUpdateTweetContent() {
        Tweet tweet = new Tweet("TweetContent");
        Mockito.when(tweetRepository.findById(tweet.getTweetId())).thenReturn(java.util.Optional.of(tweet));
        String newTweetContent = "new content";
        tweetServiceImpl.updateTweetContent(tweet.getTweetId(), newTweetContent);
        Optional<Tweet> foundTweet = tweetRepository.findById(tweet.getTweetId());
        assertEquals(newTweetContent, foundTweet.get().getContent());
    }

    @Test(expected = TweetNotFoundException.class)
    public void shouldDeleteTweetsForUser() {
        Tweet tweet = new Tweet("tweetContent");
        User user = new User("nikola", "pass", "nikola@yahoo.com");
        user.getListOfTweets().add(tweet);
        tweet.setUser(user);
        tweetRepository.save(tweet);
        tweetServiceImpl.deleteTweetsForUser(user);

        Tweet foundTweet = tweetServiceImpl.getTweetById(tweet.getTweetId());
        assertEquals(null, foundTweet);
    }
}