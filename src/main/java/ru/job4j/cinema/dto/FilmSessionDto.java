package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;

import java.time.LocalDateTime;

public class FilmSessionDto {
    private Integer id;
    private String filmName;
    private Integer hallId;
    private String hallName;
    private int hallRowCount;
    private int hallPlaceCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;

    public FilmSessionDto(FilmSession filmSession, Film film, Hall hall) {
        this.id = filmSession.getId();
        this.filmName = film.getName();
        this.hallId = hall.getId();
        this.hallName = hall.getName();
        this.hallRowCount = hall.getRowCount();
        this.hallPlaceCount = hall.getPlaceCount();
        this.startTime = filmSession.getStartTime();
        this.endTime = filmSession.getEndTime();
        this.price = filmSession.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getHallRowCount() {
        return hallRowCount;
    }

    public void setHallRowCount(int hallRowCount) {
        this.hallRowCount = hallRowCount;
    }

    public int getHallPlaceCount() {
        return hallPlaceCount;
    }

    public void setHallPlaceCount(int hallPlaceCount) {
        this.hallPlaceCount = hallPlaceCount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
