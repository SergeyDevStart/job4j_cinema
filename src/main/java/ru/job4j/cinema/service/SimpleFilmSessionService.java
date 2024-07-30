package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository, FilmRepository filmRepository,
                                    HallRepository hallRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Optional<FilmSessionDto> getFilmSessionById(Integer id) {
        var filmSessionOptional = filmSessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            return Optional.empty();
        }
        var filmSession = filmSessionOptional.get();
        var film = filmRepository.findById(filmSession.getFilmId()).orElseThrow();
        var hall = hallRepository.findById(filmSession.getHallId()).orElseThrow();
        var filmSessionDto = new FilmSessionDto(filmSession, film, hall);
        return Optional.of(filmSessionDto);
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        return filmSessionRepository.findAll().stream().map(filmSession -> {
            var film = filmRepository.findById(filmSession.getFilmId()).orElseThrow();
            var hall = hallRepository.findById(filmSession.getHallId()).orElseThrow();
            return new FilmSessionDto(filmSession, film, hall);
        }).collect(Collectors.toList());
    }
}
