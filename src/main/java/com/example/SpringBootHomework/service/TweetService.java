package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;

import java.text.ParseException;
import java.util.Set;

public interface TweetService {
    Tweet getTweetById(Long tweetId);

    Tweet saveTweet(Tweet tweet);

    Set<User> getUsersThatTweetedLastMonth() throws ParseException;

    void updateTweetContent(Long tweetId, String newTweetContent);

    void deleteTweetsForUser(User user);
}
