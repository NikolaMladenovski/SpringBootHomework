package com.example.SpringBootHomework.repository;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    void deleteAllByUser(User user);
}
