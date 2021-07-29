package com.sg.dvdlibrary.dto;

public class Dvd {
    private String title;
    private String relDate;
    private String mpaaRating;
    private String director;
    private String studio;
    private String userEntry;

    public Dvd(String title) {
        this.title = title;
        this.relDate = "";
        this.mpaaRating = "";
        this.director = "";
        this.studio = "";
        this.userEntry = "";
    }

    public Dvd(String title, String relDate, String mpaaRating, String director, String studio, String userEntry) {
        this.title = title;
        this.relDate = relDate;
        this.mpaaRating = mpaaRating;
        this.director = director;
        this.studio = studio;
        this.userEntry = userEntry;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelDate() {
        return relDate;
    }

    public void setRelDate(String relDate) {
        this.relDate = relDate;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getUserEntry() {
        return userEntry;
    }

    public void setUserEntry(String userEntry) {
        this.userEntry = userEntry;
    }
}
