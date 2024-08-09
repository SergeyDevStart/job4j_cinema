package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/buy")
    public String saveTicket(@ModelAttribute Ticket ticket, Model model) {
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            model.addAttribute("message", """
                Не удалось приобрести билет на заданное место.
                Вероятно, оно уже занято.
                Перейдите на страницу бронирования билетов и попробуйте снова.
                """);
            return "errors/409";
        }
        model.addAttribute("message",
                String.format(
                        "Вы забронировали билет на %d ряд %d место.",
                        ticket.getRowNumber(),
                        ticket.getPlaceNumber()
                )
        );
        return "tickets/success";
    }
}
