package com.example.SpringBootHomework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Expose
    @Id
    @GeneratedValue
    private Long Id;
    @NotBlank(message = "You have to provide UserName")
    @Length(min = 10, max = 25, message = "Username must be between 10 and 25 characters")
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    @Email(message = "You have to provide valid email address")
    private String email;

    @OneToMany(mappedBy = "user",fetch =FetchType.LAZY)
    @JsonIgnoreProperties(value = {"user"})
    private Set<Tweet> listOfTweets = new HashSet<>();

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
