package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.user.Sql2oUserRepository;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oUserRepositoryTest {
    private static Sql2oUserRepository userRepository;
    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();
        var dataSource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(dataSource);
        userRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        try (var connection = sql2o.open()) {
            connection.createQuery("DELETE FROM users").executeUpdate();
        }
    }

    @Test
    void whenSaveUserThenSuccess() {
        var user = new User("Name", "test@mail", "password");
        user.setId(1);
        var savedUser = userRepository.save(user).get();
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    void whenSaveUserAndFindByIdThenGetSame() {
        var user = new User("Name", "test@mail", "password");
        user.setId(1);
        userRepository.save(user);
        var savedUser = userRepository.findByEmailAndPassword("test@mail", "password").get();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenSaveTwoUsersThenGetSame() {
        var user = new User("Name", "test@mail", "password");
        user.setId(1);
        var user1 = new User("Name1", "test1@mail", "password");
        user.setId(2);
        userRepository.save(user);
        userRepository.save(user1);
        var savedUser = userRepository.findByEmailAndPassword("test@mail", "password").get();
        var savedUser1 = userRepository.findByEmailAndPassword("test1@mail", "password").get();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
        assertThat(savedUser1).usingRecursiveComparison().isEqualTo(user1);
    }

    @Test
    void whenSaveSameEmailThenNothingSave() {
        var user = new User("Name", "test@mail", "password");
        user.setId(1);
        var user1 = new User("Name1", "test@mail", "password");
        user.setId(2);
        userRepository.save(user);
        userRepository.save(user1);
        var savedUser = userRepository.findByEmailAndPassword("test@mail", "password");
        var savedUser1 = userRepository.findByEmailAndPassword("test1@mail", "password");
        assertThat(savedUser).isPresent();
        assertThat(savedUser1).isEmpty();
    }
}