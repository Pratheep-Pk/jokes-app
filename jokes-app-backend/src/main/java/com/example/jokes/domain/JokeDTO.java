package com.example.jokes.domain;

import lombok.Data;

@Data
public class JokeDTO {
    public String id;
    public String question;
    public String answer;
}
