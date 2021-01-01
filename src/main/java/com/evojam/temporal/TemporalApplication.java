package com.evojam.temporal;

import lombok.Data;
import lombok.Value;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.SQLException;
import java.util.List;


@SpringBootApplication
public class TemporalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemporalApplication.class, args);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Port() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }
}


@RestController
@Value
class JokesController {

    JokeRepository repo;

    @PostMapping("/jokes")
    void postJokes(@RequestBody List<Joke> jokes) {
        jokes.forEach(repo::save);
    }

    @GetMapping("/jokes")
    Iterable<Joke> getJokes() {
        return repo.findAll();
    }
}


@Data
@Entity
class Joke {

    @Id
    @GeneratedValue
    Long id;

    String text;
}


@Repository
interface JokeRepository extends CrudRepository<Joke, Long> {
}



