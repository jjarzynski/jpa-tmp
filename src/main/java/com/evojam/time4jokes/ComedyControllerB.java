package com.evojam.time4jokes;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/b")
@AllArgsConstructor
class ComedyControllerB {

    JokeRepository jokeRepository;
    ReactionRepository reactionRepository;
    ComedianRepository comedianRepository;
    OwnerRepository ownerRepository;

    @GetMapping("/joke/{id}/reaction")
    List<ReactionDto> reactions(@PathVariable Long id) {

        return reactionRepository.findByJokeIdWithOwnerAtTheTime(id);
    }

    @DeleteMapping("/comedian/{id}")
    void retire(@PathVariable Long id) {

        // comedianRepository.findById(id)
        //         .map(Comedian::retire)
        //         .ifPresent(comedianRepository::save);
        // TODO
    }

    @PutMapping("/joke/{id}/owner")
    void assign(@PathVariable Long id, @RequestBody AssignmentDto request) {

        JokeOwner current = ownerRepository.findByJokeIdAndUntilIsNull(id);
        Comedian comedian = comedianRepository.findByName(request.getName());

        // current.reassign(comedian, request.getSince())
        //         .forEach(ownerRepository::save);
        // TODO
    }

    @PatchMapping("/joke/{id}")
    void rephrase(@PathVariable Long id, @RequestBody RephraseDto request) {
        // jokeRepository.findById(id)
        //         .map(joke -> joke.setQuestion(request.getQuestion()))
        //         .ifPresent(jokeRepository::save);
        // TODO
    }
}
