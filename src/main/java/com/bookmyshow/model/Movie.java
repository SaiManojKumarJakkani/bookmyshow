package src.main.java.com.bookmyshow.model;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String movieID;
    private String movieName;
    private String genre;
    private String actor;
    private String actress;
    private String producer;
    private String director;
    private String language;
    private String rating;

    public Movie() {
        this.movieID= "";
        this.movieName="";
        this.genre="";
        this.actor="";
        this.actress="";
        this.producer="";
        this.director="";
        this.language="";
        this.rating="";

    }
    //Getter Setter for movieID
    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

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
    void addMovie(Movie movie){

    }
    @Override
    public String toString() {
        return "MovieID: "+movieID+
                "\n movieName: "+movieName+
                "\n genre: "+genre+
                "\n language: "+language;
    }
}
