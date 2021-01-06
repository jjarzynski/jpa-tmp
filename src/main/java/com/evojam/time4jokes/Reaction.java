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

    Integer reaction;
}

interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Query("SELECT new com.evojam.time4jokes.ReactionDto(" +
            "   owner.comedian.name," +
            "   owner.comedian.retired," +
            "   reaction.joke.question," +
            "   reaction.joke.answer," +
            "   reaction.date," +
            "   reaction.reaction" +
            ")" +

            " FROM Reaction reaction" +
            " JOIN JokeOwner owner" +
            " ON reaction.joke.id = owner.joke.id" +
            " AND reaction.date >= owner.since" +
            " AND (" +
            "   owner.until IS NULL" +
            "   OR reaction.date < owner.until" +
            " )" +

            " WHERE owner.joke.id = :id" +
            " ORDER BY reaction.date")
    List<ReactionDto> findByJokeIdWithOwnerAtTheTime(Long id);

    List<ReactionDto> findAllByJokeIdOrderByDate(Long id);
}
