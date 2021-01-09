package com.evojam.time4jokes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
class ComedyControllerA {

    JokeRepository jokeRepository;
    ReactionRepository reactionRepository;
    ComedianRepository comedianRepository;

    @PostMapping("/v1/joke/{id}/reaction")
    void reaction(@PathVariable Long id, @RequestBody List<ReactionDto> request) {
        jokeRepository.findById(id).ifPresent(joke ->
        request.stream()
                .map(item -> new Reaction()
                        .setDate(item.getDate())
                        .setReaction(item.getReaction())
                        .setJoke(joke)
                )
                .forEach(reactionRepository::save));
    }

    @GetMapping("/v1/joke/{id}/reaction")
    List<ReactionDto> reactions(@PathVariable Long id) {
        return reactionRepository.findAllByJokeIdOrderByDate(id);
    }

    @DeleteMapping("/v1/comedian/{id}")
    void retire(@PathVariable Long id) {
        jokeRepository.findByOwnerId(id).forEach(joke -> {
            joke.setOwner(null);
            jokeRepository.save(joke);
        });
        comedianRepository.findById(id)
                .ifPresent(comedianRepository::delete);
    }

    @PutMapping("/v1/joke/{id}/owner")
    void assign(@PathVariable Long id, @RequestBody AssignmentDto request) {
        Comedian comedian = comedianRepository.findByName(request.getName());

        jokeRepository.findById(id)
                .map(joke -> {
                    joke.setOwner(comedian);
                    return joke;
                })
                .ifPresent(jokeRepository::save);
    }

    @PatchMapping("/v1/joke/{id}")
    void rephrase(@PathVariable Long id, @RequestBody RephraseDto request) {
        jokeRepository.findById(id)
                .map(joke -> joke.setQuestion(request.getQuestion()))
                .ifPresent(jokeRepository::save);
    }
}

@Value
class RephraseDto {

    LocalDate since;
    String question;
}

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
class ReactionDto {

    String jokeOwnerName;
    String jokeQuestion;
    String jokeAnswer;
    LocalDate date;
    Integer reaction;
}

@Value
class AssignmentDto {

    LocalDate since;
    String name;
}
