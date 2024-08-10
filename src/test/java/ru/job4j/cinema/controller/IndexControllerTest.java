package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IndexControllerTest {
    @Test
    void whenRequestIndexThenGetIndexPage() {
        var controller = new IndexController();
        var view = controller.genIndexPage();
        assertThat(view).isEqualTo("index");
    }
}