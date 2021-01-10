package com.evojam.time4jokes;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Data
@Entity
@Accessors(chain = true)
class Joke {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Comedian owner;

    String question;
    String answer;
}

// TODO

@Data
@Entity
@SequenceGenerator(
        name = "joke_version_id",
        sequenceName = "joke_version_seq",
        allocationSize = 1
)
@Accessors(chain = true)
class JokeVersion {

    @Id
    @GeneratedValue(generator = "joke_version_id")
    Long id;

    @ManyToOne
    Joke joke;

    String question;

    LocalDate since;
    LocalDate until;

    Stream<JokeVersion> rephrase(String question, LocalDate since) {
        this.until = since;

        JokeVersion next = new JokeVersion().setJoke(this.joke).setQuestion(question).setSince(since);

        return Stream.of(this, next);
    }
}

interface JokeRepository extends JpaRepository<Joke, Long> {

    List<Joke> findByOwnerId(Long id);
}

// TODO

interface JokeVersionRepository extends JpaRepository<JokeVersion, Long> {

    JokeVersion findByJokeIdAndUntilNull(Long id);
}