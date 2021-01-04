package com.evojam.temporal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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
@AllArgsConstructor
class FunnyController {

    JokeRepository jokeRepository;
    ReactionRepository reactionRepository;
    ComedianRepository comedianRepository;

    @PostMapping("/joke/{id}/reaction")
    void reaction(@PathVariable Long id, @RequestBody ReactionDto request) {
        jokeRepository.findById(id).map(joke -> {
            Reaction reaction = new Reaction();
            reaction.setDate(request.getDate());
            reaction.setReaction(request.getReaction());
            reaction.setJoke(joke);
            return reaction;
        }).ifPresent(reactionRepository::save);
    }

    @GetMapping("/joke/{id}/reaction")
    List<ReactionDto> reactions(@PathVariable Long id) {
        return reactionRepository.findAllByJokeIdOrderByDate(id);
    }

    @DeleteMapping("/comedian/{id}")
    void retire(@PathVariable Long id) {
        // jokeRepository.findByOwnerId(id).forEach(joke -> {
        //     joke.setOwner(null);
        //     jokeRepository.save(joke);
        // });
        comedianRepository.findById(id)
                .map(Comedian::retire)
                // .ifPresent(comedianRepository::delete);
                .ifPresent(comedianRepository::save);
    }

    @PutMapping("/joke/{id}/owner")
    void assign(@PathVariable Long id, @RequestBody AssignmentDto request) {
        jokeRepository.findById(id)
                .flatMap(joke -> comedianRepository.findByName(request.getName())
                        .map(comedian -> joke.setOwner(comedian)))
                .ifPresent(jokeRepository::save);
    }

    @PatchMapping("/joke/{id}")
    void rephrase(@PathVariable Long id, @RequestBody RephraseDto request) {
        jokeRepository.findById(id)
                .map(joke -> joke.setQuestion(request.getQuestion()))
                .ifPresent(jokeRepository::save);
    }
}

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
class ReactionDto {

    String jokeOwnerName;
    LocalDate jokeOwnerRetired;
    String jokeQuestion;
    LocalDate date;
    Integer reaction;
}

@Value
class AssignmentDto {

    LocalDate since;
    String name;
}

@Value
class RephraseDto {

    LocalDate since;
    String question;
}

@Data
@Entity
class Comedian {

    @Id
    @GeneratedValue
    Long id;

    String name;

    LocalDate retired;

    Comedian retire() {
        this.retired = LocalDate.now();
        return this;
    }
}

@Data
@Entity
@Accessors(chain = true)
class Joke {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Comedian owner;

    String title;
    String question;
    String answer;
}

@Data
@Entity
@Accessors(chain = true)
class Reaction {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Joke joke;

    LocalDate date;

    Integer reaction;
}

interface ReactionRepository extends JpaRepository<Reaction, Long> {

    List<ReactionDto> findAllByJokeIdOrderByDate(Long id);
}

interface JokeRepository extends JpaRepository<Joke, Long> {
    List<Joke> findByOwnerId(Long id);
}

interface ComedianRepository extends JpaRepository<Comedian, Long> {

    Optional<Comedian> findByName(String name);
}

// using integers for hibernate IDs
