package com.nowcoder.answer.service;

import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private int age;
    private String name;
    public AnswerService(){

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage(int userId){
        return "hello message:"+userId;
    }
}
