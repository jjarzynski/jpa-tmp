package com.evojam.temporal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Value;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.Collection;


@SpringBootApplication
public class TemporalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemporalApplication.class, args);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Port() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}


@RestController
@Value
class TemporalController {

    JokeRepository jokeRepo;
    ReactionRepository reactionRepo;

    @GetMapping("/jokes")
    Iterable<Joke> jokes() {
        return jokeRepo.findAll();
    }

    @PostMapping("/joke/{id}/reaction")
        // TODO: make more readable?
    void reactions(@PathVariable Long id, Reaction reaction) {
        jokeRepo.findById(id)
                .ifPresent(joke -> {
                    reaction.setJoke(joke);
                    reactionRepo.save(reaction);
                });
    }
}

@Data
@Entity
class Comedian {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    String name;

    @OneToMany(mappedBy = "ownerId")
    Collection<Joke> jokes;
}

@Data
@Entity
class Joke {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    @JsonIgnore
    Long ownerId;

    String title;

    String text;
}

@Data
@Entity
class Reaction {

    @Id
    @GeneratedValue
    @JsonIgnore
    Long id;

    @ManyToOne
    Joke joke;

    Integer oneToTen;
}

@Repository
interface JokeRepository extends JpaRepository<Joke, Long> {
}


@Repository
interface ReactionRepository extends JpaRepository<Reaction, Long> {
}


// TODO: D in CRUD - delete

// TODO: U in CRUD
//  - update joke
//  - reassign joke
