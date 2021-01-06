package com.evojam.time4jokes;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

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

interface JokeRepository extends JpaRepository<Joke, Long> {

    List<Joke> findByOwnerId(Long id);
}
