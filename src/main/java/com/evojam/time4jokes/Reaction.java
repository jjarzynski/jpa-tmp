package com.evojam.time4jokes;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

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

    // 1-10
    Integer reaction;
}

interface ReactionRepository extends JpaRepository<Reaction, Long> {

    List<ReactionDto> findAllByJokeIdOrderByDate(Long id);
}
