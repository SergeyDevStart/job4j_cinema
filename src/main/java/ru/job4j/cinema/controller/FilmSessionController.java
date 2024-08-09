package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.FilmSessionService;

@Controller
@RequestMapping("/sessions")
public class FilmSessionController {
    private final FilmSessionService filmSessionService;

    public FilmSessionController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        return "sessions/list";
    }

    @GetMapping("/{id}")
    public String getFilmSessionById(Model model, @PathVariable("id") Integer id) {
        var filmSessionDtoOptional = filmSessionService.getFilmSessionById(id);
        if (filmSessionDtoOptional.isEmpty()) {
            model.addAttribute("message", "Выбранный сеанс не найден");
            return "errors/404";
        }
        model.addAttribute("filmSession", filmSessionDtoOptional.get());
        return "tickets/buy";
    }
}
