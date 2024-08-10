package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {
    private TicketService ticketService;
    private TicketController controller;

    @BeforeEach
    void init() {
        ticketService = mock(TicketService.class);
        controller = new TicketController(ticketService);
    }

    @Test
    void whenPostSavedTicketThenSuccessAndGetSuccessPageWithMessage() {
        var ticket = new Ticket(1, 1, 1, 1);
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);
        var expectedMessage = "Вы забронировали билет на 1 ряд 1 место.";
        when(ticketService.save(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = controller.saveTicket(ticket, model);
        var actualMessage = model.getAttribute("message");
        var actualTicket = ticketArgumentCaptor.getValue();

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat(actualTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    void whenPostSavedTicketIsNotSuccessThenGetErrorPageWithMessage() {
        var ticket = new Ticket(1, 1, 1, 1);
        var expectedMessage = """
                Не удалось приобрести билет на заданное место.
                Вероятно, оно уже занято.
                Перейдите на страницу бронирования билетов и попробуйте снова.
                """;
        when(ticketService.save(ticket)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = controller.saveTicket(ticket, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/409");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}