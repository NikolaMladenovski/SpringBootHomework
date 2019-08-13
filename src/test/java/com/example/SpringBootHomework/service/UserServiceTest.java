package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceTest {


    @Before
    public void setUp(){

    }
    @Test
    public void getUserTweets() {
    }

    @Test
    public void getUserTweetsForGivenDate() {
    }

    @Test
    public void shouldSaveUser() {
        User user = new User("username1","password1234","nikola@yahoo.com");

    }

    @Test
    public void updateUserPassword() {
    }

    @Test
    public void deleteUser() {
    }
}