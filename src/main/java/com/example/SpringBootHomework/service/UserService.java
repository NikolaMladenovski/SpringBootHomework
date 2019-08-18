package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;

import java.text.ParseException;
import java.util.Set;

public interface UserService {
    Set<Tweet> getUserTweets(Long userId);
    Set<Tweet> getUserTweetsForGivenDate(Long userId, String date) throws ParseException;
    User saveUser(User user);
    void updateUserPassword(Long userId,String newPassword);
    void deleteUser(Long userId);
}
