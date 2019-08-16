package com.example.SpringBootHomework.service;

import com.example.SpringBootHomework.exception.UserNotFoundException;
import com.example.SpringBootHomework.model.Tweet;
import com.example.SpringBootHomework.model.User;
import com.example.SpringBootHomework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<Tweet> getUserTweets(Long userId) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(userId);
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException();
        }
        Set<Tweet> userTweets = foundUser.get().getListOfTweets();
        return userTweets;
    }

    @Override
    public Set<Tweet> getUserTweetsForGivenDate(Long userId, LocalDate date) throws UserNotFoundException {
            Optional<User> foundUser = userRepository.findById(userId);
            if (!foundUser.isPresent()) {
                throw new UserNotFoundException();
            }
            Set<Tweet> userTweetsForGivenDate = foundUser
                    .get()
                    .getListOfTweets()
                    .stream()
                    .filter(tweet -> tweet.getDateOfCreation().equals(date))
                    .collect(Collectors.toSet());
            return userTweetsForGivenDate;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUserPassword(Long userId, String newPassword) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(userId);
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException();
        }
        foundUser.get().setPassword(newPassword);
        userRepository.save(foundUser.get());
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(userId);
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(userId);
    }
}
