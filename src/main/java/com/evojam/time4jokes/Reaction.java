package com.evojam.time4jokes;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    // TODO

    @Query("select new com.evojam.time4jokes.ReactionDto(" +
            "   comedian.name," +
            "   comedian.retired," +
            "   version.question," +
            "   joke.answer," +
            "   reaction.date," +
            "   reaction.reaction" +
            ")" +

            " from Reaction reaction" +
            " join reaction.joke joke" +

            " join JokeOwner owner" +
            " on owner.joke = joke" +
            " and reaction.date >= owner.since" +
            " and (" +
            "   reaction.date < owner.until" +
            "   or owner.until is null" +
            ")" +

            " join JokeVersion version" +
            " on version.joke = joke" +
            " and reaction.date >= version.since" +
            " and (" +
            "   reaction.date < version.until" +
            "   or version.until is null" +
            ")" +

            "join owner.comedian comedian"
    )
    List<ReactionDto> findByJokeIdWithOwnerAtTheTime(Long id);
}
