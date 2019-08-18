package com.example.SpringBootHomework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tweets")
public class Tweet {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tweetId;
    @Expose
    @NotBlank(message = "You have to provide content")
    @Length(max = 120, message = "Max length is 120 characters")
    private String content;

    @Expose
    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;

    @JsonIgnoreProperties(value = "listOfTweets")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Tweet(String content) {
        this.content = content;
    }
}
