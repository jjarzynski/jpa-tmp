package com.evojam.time4jokes;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
class ComedyControllerB {

    JokeRepository jokeRepository;
    ReactionRepository reactionRepository;
    ComedianRepository comedianRepository;

    @GetMapping("/v2/joke/{id}/reaction")
    List<ReactionDto> reactions(@PathVariable Long id) {
        return Collections.emptyList(); // TODO
    }

    @DeleteMapping("/v2/comedian/{id}")
    void retire(@PathVariable Long id) {
        // TODO
    }

    @PutMapping("/v2/joke/{id}/owner")
    void reassign(@PathVariable Long id, @RequestBody AssignmentDto request) {
        // TODO
    }

    @PatchMapping("/v2/joke/{id}")
    void rephrase(@PathVariable Long id, @RequestBody RephraseDto request) {
        jokeRepository.findById(id)
                .map(joke -> joke.setQuestion(request.getQuestion()))
                .ifPresent(jokeRepository::save);
        // TODO
    }
}
