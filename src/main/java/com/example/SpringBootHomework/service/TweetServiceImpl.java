package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.exception.TweetNotFoundException;
import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TweetServiceImpl implements TweetService {

    private TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Tweet getTweetById(Long tweetId) throws TweetNotFoundException {
        Optional<Tweet> foundTweet = tweetRepository.findById(tweetId);
        if (!foundTweet.isPresent()) {
            throw new TweetNotFoundException();
        }
        return foundTweet.get();
    }

    @Override
    public Tweet saveTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Set<User> getUsersThatTweetedLastMonth() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusMonths(1);
        start=start.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(1);
        //List<Tweet> allTweets = (Set<Tweet>) tweetRepository.findAll();
        Set<Tweet> allTweetsFromLastMonth = tweetRepository.findAllByDateOfCreationBetween(simpleDateFormat.parse(start.toString()),simpleDateFormat.parse(end.toString()));
//                .stream()
//                .filter(tweet -> new java.sql.Date(tweet.getDateOfCreation().getTime())
//                        .toLocalDate().equals(LocalDate.now().getMonth().minus(1)))
//                .collect(Collectors.toSet());
        Set<User> allUsersThatTweetedLastMonth = allTweetsFromLastMonth.stream().map(Tweet::getUser).collect(Collectors.toSet());
        return allUsersThatTweetedLastMonth;

    }

    @Override
    public void updateTweetContent(Long tweetId, String newTweetContent) throws TweetNotFoundException {
        Optional<Tweet> foundTweet = tweetRepository.findById(tweetId);
        if (!foundTweet.isPresent()) {
            throw new TweetNotFoundException();
        }
        foundTweet.get().setContent(newTweetContent);
        tweetRepository.save(foundTweet.get());
    }

    @Override
        public void deleteTweetsForUser(User user) {

        tweetRepository.deleteAllByUser(user);
    }
}
