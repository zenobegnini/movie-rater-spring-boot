package it.intesys.movierater.app;

import javax.persistence.*;

@Entity
public class Movie {
    @Id
    @Column(name="id")
    private Integer id;

    @Column(name = "title")
    private String title;
    @Column(name = "Year")
    private Integer Year;
    @Column(name = "Genre")
    private String Genre;
    @Column(name = "Director")
    private String Director;
    @Column(name = "Actors")
    private String Actors;
    @Column(name = "Country")
    private String Country;
    @Column(name = "votes")
    private int votes;

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}


