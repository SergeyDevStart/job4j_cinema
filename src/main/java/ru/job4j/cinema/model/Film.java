package ru.job4j.cinema.model;

import java.util.Objects;

public class Film {
    private Integer id;
    private String name;
    private String description;
    private int year;
    private Integer genreId;
    private int minimalAge;
    private int durationInMinutes;
    private Integer fileId;

    public static class FilmBuilder {
        private Integer id;
        private String name;
        private String description;
        private int year;
        private Integer genreId;
        private int minimalAge;
        private int durationInMinutes;
        private Integer fileId;

        public FilmBuilder buildId(Integer id) {
            this.id = id;
            return this;
        }

        public FilmBuilder buildName(String name) {
            this.name = name;
            return this;
        }

        public FilmBuilder buildDescription(String description) {
            this.description = description;
            return this;
        }

        public FilmBuilder buildYear(int year) {
            this.year = year;
            return this;
        }

        public FilmBuilder buildGenreId(Integer genreId) {
            this.genreId = genreId;
            return this;
        }

        public FilmBuilder buildMinimalAge(int minimalAge) {
            this.minimalAge = minimalAge;
            return this;
        }

        public FilmBuilder buildDurationInMinutes(int durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public FilmBuilder buildFileId(Integer fileId) {
            this.fileId = fileId;
            return this;
        }

        public Film build() {
            Film film = new Film();
            film.id = id;
            film.name = name;
            film.description = description;
            film.year = year;
            film.genreId = genreId;
            film.minimalAge = minimalAge;
            film.durationInMinutes = durationInMinutes;
            film.fileId = fileId;
            return film;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Film film = (Film) o;
        return Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
