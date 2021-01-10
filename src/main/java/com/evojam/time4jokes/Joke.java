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

interface JokeRepository extends JpaRepository<Joke, Long> {

    List<Joke> findByOwnerId(Long id);
}

// TODO
