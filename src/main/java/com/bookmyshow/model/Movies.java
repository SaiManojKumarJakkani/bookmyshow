package org.example;

import java.util.ArrayList;
import java.util.List;

public class Movies {

    private String movieName;
    private String genre;
    private String actor;
    private String actress;
    private String producer;
    private String director;
    private String language;
    private String rating;

    public Movies() {

        this.movieName="";
        this.genre="";
        this.actor="";
        this.actress="";
        this.producer="";
        this.director="";
        this.language="";
        this.rating="";

    }

    public Movies( String movieName, String genre, String actor, String actress, String producer, String director, String language, String rating) {
    this.movieName=movieName;
    this.genre=genre;
    this.actor=actor;
    this.actress= actress;
    this.producer=producer;
    this.director=director;
    this.language=language;
    this.rating=rating;
    }

    //Getter Setter for movieID

    //Getter Setter for movie Name
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    //Getter Setter for genre
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    //Getter Setter for actor
    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    //Getter Setter for actress
    public String getActress() {
        return actress;
    }
    public void setActress(String actress) {
        this.actress = actress;
    }
    //Getter Setter for producer
    public String getProducer() {
        return producer;
    }
    public void setProducer(String producer) {
        this.producer = producer;
    }
    //Getter Setter for director
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director= director;
    }
    //Getter Setter for language
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    //Getter Setter for rating
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    @Override
    public String toString() {
        return "\n movieName: "+movieName+
                "\n genre: "+genre+
                "\n actor: " +actor+
                "\n actress: " +actress+
                "\n producer: " + producer +
                "\n director: " + director +
                "\n language: "+ language +
                "\n rating: " +rating;


    }
}
