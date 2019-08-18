package com.example.SpringBootHomework.controller;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Set;


@RestController
@RequestMapping(value = "/api/user")
public class UserRestController {
    private  UserService userService;


    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping(value = "/getUserTweets")
    public ResponseEntity<?> getUserTweets(@RequestParam(value = "userId") Long userId) {
        Set<Tweet> tweets = userService.getUserTweets(userId);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @GetMapping(value = "/getUserTweetsForDate")
    public ResponseEntity<?> getUserTweetsForGivenDate(@RequestParam(value = "userId") Long userId, @RequestParam(value = "date") String date) throws ParseException {

        Set<Tweet> tweets = userService.getUserTweetsForGivenDate(userId, date);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @PostMapping(value = "/saveUser")
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateUserPassword")
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
