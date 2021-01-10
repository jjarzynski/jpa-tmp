package com.evojam.time4jokes;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.stream.Stream;

// TODO

@Data
@Entity
@SequenceGenerator(
        name = "owner_id",
        sequenceName = "owner_seq",
        allocationSize = 1
)
@Accessors(chain = true)
class JokeOwner {

    @Id
    @GeneratedValue(generator = "owner_id")
    Long id;

    @ManyToOne
    Joke joke;

    @ManyToOne
    Comedian comedian;

    LocalDate since;
    LocalDate until;

    Stream<JokeOwner> reassign(Comedian target, LocalDate since) {
        this.until = since;

        JokeOwner next = new JokeOwner()
                .setJoke(this.joke)
                .setComedian(target)
                .setSince(since);

        return Stream.of(this, next);
    }
}

interface JokeOwnerRepository extends JpaRepository<JokeOwner, Long> {
    JokeOwner findByJokeIdAndUntilIsNull(Long id);
}