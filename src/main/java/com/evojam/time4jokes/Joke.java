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

// @Data
// @Entity
// @SequenceGenerator(
//         name = "joke_version_id",
//         sequenceName = "joke_version_seq",
//         allocationSize = 1
// )
// class JokeVersion {
//
//     @Id
//     @GeneratedValue(generator = "joke_version_id")
//     Long id;
//
//     @ManyToOne
//     Joke joke;
//
//     String question;
//
//     LocalDate since;
//     LocalDate until;
//
//     Stream<JokeVersion> change(String question, LocalDate since) {
//         this.setUntil(since);
//
//         JokeVersion next = new JokeVersion();
//         next.setJoke(this.joke);
//         next.setQuestion(question);
//         next.setSince(since);
//
//         return Stream.of(this, next);
//     }
// }

interface JokeRepository extends JpaRepository<Joke, Long> {

    List<Joke> findByOwnerId(Long id);
}

// interface JokeVersionRepository extends JpaRepository<JokeVersion, Long> {
//
//     JokeVersion findByJokeIdAndUntilIsNull(Long id);
// }
