package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public interface UserService {
    Set<Tweet> getUserTweets(Long userId);
    Set<Tweet> getUserTweetsForGivenDate(Long userId, LocalDate date);
    User saveUser(User user);
    void updateUserPassword(Long userId,User user);
    void deleteUser(Long userId);
}
