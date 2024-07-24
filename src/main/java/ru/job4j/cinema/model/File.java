package ru.job4j.cinema.model;

import java.util.Objects;

public class File {
    private Integer id;
    private String name;
    private String path;

    public File(Integer id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(path, file.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path);
    }
}
