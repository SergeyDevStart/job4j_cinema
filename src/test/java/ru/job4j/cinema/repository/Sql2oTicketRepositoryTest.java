package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.ticket.Sql2oTicketRepository;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oTicketRepositoryTest {
    private static Sql2o sql2o;
    private static Sql2oTicketRepository ticketRepository;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var input = Sql2oTicketRepository.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);
        ticketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    void clearTickets() {
        try (var connection = sql2o.open()) {
            connection.createQuery("DELETE FROM tickets").executeUpdate();
        }
    }

    @Test
    void whenSaveTicketThenSuccess() {
        var ticket = new Ticket(1, 5, 15, 1);
        var savedTicket = ticketRepository.save(ticket).get();
        assertThat(savedTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    void whenSaveSameThenNotSave() {
        var ticket = new Ticket(1, 5, 15, 1);
        var ticket1 = new Ticket(1, 5, 15, 2);
        var savedTicket = ticketRepository.save(ticket);
        var savedTicket1 = ticketRepository.save(ticket1);
        assertThat(savedTicket).isPresent();
        assertThat(savedTicket1).isEmpty();
    }
}