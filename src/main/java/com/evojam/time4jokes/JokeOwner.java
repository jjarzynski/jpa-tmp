package com.evojam.time4jokes;

// @Data
// @Entity
// @SequenceGenerator(name = "owner_id", sequenceName = "owner_seq", allocationSize = 1)
// class JokeOwner {
//
//     @Id
//     @GeneratedValue(generator = "owner_id")
//     Long id;
//
//     @ManyToOne
//     Joke joke;
//
//     @ManyToOne
//     Comedian comedian;
//
//     LocalDate since;
//
//     LocalDate until;
//
//     Stream<JokeOwner> reassign(Comedian candidate, LocalDate since) {
//         this.until = since;
//
//         JokeOwner next = new JokeOwner();
//         next.setSince(since);
//         this.joke.setOwner(candidate);
//         next.setJoke(this.joke);
//         next.setComedian(candidate);
//
//         return Stream.of(this, next);
//     }
// }
//
// interface OwnerRepository extends JpaRepository<JokeOwner, Long> {
//
//     JokeOwner findByJokeIdAndUntilIsNull(Long id);
// }
