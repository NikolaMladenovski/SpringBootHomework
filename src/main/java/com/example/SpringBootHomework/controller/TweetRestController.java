package com.example.SpringBootHomework.controller;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

public class TweetRestController {
    private final TweetService tweetService;

    @Autowired
    public TweetRestController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public ResponseEntity<?> getTweetById(@RequestParam(value = "tweetId") Long tweetId) {
        Tweet tweet = tweetService.getTweetById(tweetId);
        return new ResponseEntity<>(tweet, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveTweet(@RequestParam(value = "tweet") Tweet tweet) {
        Tweet savedTweet = tweetService.saveTweet(tweet);
        return new ResponseEntity<>(savedTweet, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUsersTweetedLastMonth() {
        Set<User> users = tweetService.getUsersThatTweetedLastMonth();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> updateTweetContent(@RequestParam(value = "tweetId") Long tweetId, @RequestParam(value = "newTweetContent") String newTweetContent) {
        tweetService.updateTweetContent(tweetId, newTweetContent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTweetsForUser(@RequestParam(value = "user") User user) {
        tweetService.deleteTweetsForUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
