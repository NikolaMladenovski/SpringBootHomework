package com.example.SpringBootHomework;

import com.example.SpringBootHomework.repository.TweetRepository;
import com.example.SpringBootHomework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;


@Configuration
@SpringBootApplication
public class SpringBootHomeworkApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHomeworkApplication.class, args);

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        User user1 = new User("username12345", "password", "nikola@yahoo.com");
//        Tweet tweet1 = new Tweet("tweet Content432432");
//
//        user1.getListOfTweets().add(tweet1);
//        tweet1.setUser(user1);
//
//        userRepository.save(user1);
    }
}
