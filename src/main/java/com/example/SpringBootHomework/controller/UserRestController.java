package com.example.SpringBootHomework.controller;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {
    private final UserService userService;


    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;

    }


    @GetMapping(value = "/getUserTweets", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserTweets(@RequestParam(value = "userId") Long userId) {
        Set<Tweet> tweets = userService.getUserTweets(userId);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUserTweetsForGivenDate(@RequestParam(value = "userId") Long userId, @RequestParam(value = "date") LocalDate date) {
        Set<Tweet> tweets = userService.getUserTweetsForGivenDate(userId, date);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestParam(value = "user") User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUserPassword(@RequestParam(value = "userId") Long userId, @RequestParam(value = "newUserPassword") String newUserPassword) {
        userService.updateUserPassword(userId, newUserPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
