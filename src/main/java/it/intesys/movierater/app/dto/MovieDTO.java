package it.intesys.movierater.app.dto;

public class MovieDTO {


    private Long id;
    private String title;
    private String director;
    private int votes;

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String director) {
        this.id = id;
        this.title = title;
        this.director = director;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}

