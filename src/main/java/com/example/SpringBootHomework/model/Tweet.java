package com.example.SpringBootHomework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tweets")
public class Tweet {
    @Id
    @GeneratedValue
    private Long tweetId;
    @NotBlank(message = "You have to provide content")
    @Length(max = 120, message = "Max length is 120 characters")
    private String content;

    private LocalDate dateOfCreation = LocalDate.now();

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Tweet(String content) {
        this.content = content;
    }
}
