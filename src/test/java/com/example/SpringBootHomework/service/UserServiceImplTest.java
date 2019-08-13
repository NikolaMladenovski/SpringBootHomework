package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.TweetRepository;
import com.example.SpringBootHomework.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;


    @Before
    public void setUp() {
        userServiceImpl = new UserServiceImpl(userRepository);
    }

    @Test
    public void getUserTweets() {
        User user = new User("nikola","pass","nikola@feit.com");
        Tweet tweet1 = new Tweet("tweet1");
        Tweet tweet2 = new Tweet("tweet2");
        user.setListOfTweets(Stream.of(tweet1,tweet2).collect(Collectors.toSet()));
        Set<Tweet> expectedTweets = (Stream.of(tweet1,tweet2)).collect(Collectors.toSet());
        Mockito.doReturn(expectedTweets).when(userRepository.findById(user.getId()));

        Set<Tweet> foundTweets = userServiceImpl.getUserTweets(user.getId());

        assertEquals(expectedTweets,foundTweets);
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