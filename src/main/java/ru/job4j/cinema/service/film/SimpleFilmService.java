package ru.job4j.cinema.service.film;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.repository.genre.GenreRepository;
import ru.job4j.cinema.service.film.FilmService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleFilmService implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository filmRepository, GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<FilmDto> getFilmById(Integer id) {
        var filmOptional = filmRepository.findById(id);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        Film film = filmOptional.get();
        String genreName = getGenreName(film.getGenreId());
        FilmDto filmDto = new FilmDto(film.getName(), film.getDescription(), film.getYear(),
                film.getMinimalAge(), film.getDurationInMinutes(), genreName, film.getFileId());
        filmDto.setId(film.getId());
        return Optional.of(filmDto);
    }

    @Override
    public Collection<FilmDto> findAll() {
        return filmRepository.findAll().stream().map(film -> {
            String genreName = getGenreName(film.getGenreId());
            FilmDto filmDto = new FilmDto(film.getName(), film.getDescription(), film.getYear(),
                    film.getMinimalAge(), film.getDurationInMinutes(), genreName, film.getFileId());
            filmDto.setId(film.getId());
            return filmDto;
        }).collect(Collectors.toList());
    }

    private String getGenreName(Integer id) {
        return genreRepository.findById(id).orElseThrow().getName();
    }
}
