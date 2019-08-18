package com.example.SpringBootHomework.repository;

import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    void deleteAllByUser(User user);
    Set<Tweet> findAllByDateOfCreationBetween(Date afterDate, Date beforeDate);
    //void deleteAllByUserId(Long Id);
}
