package com.example.SpringBootHomework.controller;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/tweet")
public class TweetRestController {
    private final TweetService tweetService;

    @Autowired
    public TweetRestController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping(value = "/getTweetById")
    public ResponseEntity<?> getTweetById(@RequestParam(value = "tweetId") Long tweetId) {
        Tweet tweet = tweetService.getTweetById(tweetId);
        return new ResponseEntity<>(tweet, HttpStatus.OK);
    }

    @PostMapping(value = "/saveTweet")
    public ResponseEntity<?> saveTweet(@Valid @RequestBody Tweet tweet) {
        Tweet savedTweet = tweetService.saveTweet(tweet);
        return new ResponseEntity<>(savedTweet, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getUserTweetsFromLastMonth")
    public ResponseEntity<?> getUsersTweetedLastMonth() throws ParseException {
        Set<User> users = tweetService.getUsersThatTweetedLastMonth();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping(value = "/updateTweetContent")
    public ResponseEntity<?> updateTweetContent(@RequestParam(value = "tweetId") Long tweetId, @RequestParam(value = "newTweetContent") String newTweetContent) {
        tweetService.updateTweetContent(tweetId, newTweetContent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteUserTweets")
    public ResponseEntity<?> deleteTweetsForUser(@RequestBody User user) {
        tweetService.deleteTweetsForUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
