package com.GreetingApp.GreetingApp.dto;

public class Greeting {
    private Long id ;
    private String greeting;

    public Greeting(Long id, String greeting) {
        this.id = id;
        this.greeting = greeting;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", greeting='" + greeting + '\'' +
                '}';
    }
}
