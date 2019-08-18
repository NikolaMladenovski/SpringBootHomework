package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.exception.UserNotFoundException;
import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.UserRepository;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

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
    public void shouldGetUserTweets() {
        User user = new User("nikola", "pass", "nikola@feit.com");
        Tweet tweet1 = new Tweet("tweet1");
        Tweet tweet2 = new Tweet("tweet2");
        user.setListOfTweets(Stream.of(tweet1, tweet2).collect(Collectors.toSet()));

        Set<Tweet> expectedTweets = (Stream.of(tweet1, tweet2)).collect(Collectors.toSet());

        Mockito.when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        Set<Tweet> foundTweets = userServiceImpl.getUserTweets(user.getId());

        assertEquals(expectedTweets, foundTweets);
    }

    @Test
    public void shouldGetUserTweetsForGivenDate() throws ParseException {
        User nikola = new User("nikola", "pass", "nikola@yahoo.com");


        Tweet tweet1 = new Tweet("content1");
        Tweet tweet2 = new Tweet("content2");
        Tweet tweet3 = new Tweet("content3");

        tweet1.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 7, 25)));
        tweet2.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 8, 15)));
        tweet3.setDateOfCreation(Date.valueOf(LocalDate.of(2019, 8, 15)));

        nikola.getListOfTweets().add(tweet1);
        nikola.getListOfTweets().add(tweet2);
        nikola.getListOfTweets().add(tweet3);

        Mockito.when(userRepository.findById(nikola.getId())).thenReturn(java.util.Optional.of(nikola));

        Set<Tweet> expectedSet = Stream.of(tweet2, tweet3).collect(Collectors.toSet());

        Set<Tweet> foundTweets = userServiceImpl.getUserTweetsForGivenDate(nikola.getId(), LocalDate.of(2019, 8, 15).toString());

        assertEquals(expectedSet, foundTweets);

    }

    @Test
    public void shouldSaveUser() {
        User user = new User("nikola", "pass", "nikola@yahoo.com ");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User foundUser = userServiceImpl.saveUser(user);

        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void shouldUpdateUserPassword() {
        User user = new User("tviteras", "pass", "nikola@yahoo.com");
        Mockito.when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        String newUserPassword = "newPass";
        userServiceImpl.updateUserPassword(user.getId(), newUserPassword);
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertEquals(newUserPassword, foundUser.get().getPassword());
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldDeleteUser() {
        User user = new User("tviteras", "pass", "nikola@yahoo.com");
        userRepository.save(user);
        userServiceImpl.deleteUser(user.getId());
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertEquals(null, foundUser);

    }
}