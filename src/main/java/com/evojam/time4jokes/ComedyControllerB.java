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
    // OwnerRepository ownerRepository;
    // JokeVersionRepository versionRepository;

    @GetMapping("/v2/joke/{id}/reaction")
    List<ReactionDto> reactions(@PathVariable Long id) {
        // return reactionRepository.findByJokeIdWithOwnerAtTheTime(id);
        return Collections.emptyList(); // TODO
    }

    @DeleteMapping("/v2/comedian/{id}")
    void retire(@PathVariable Long id) {
        // comedianRepository.findById(id)
        //         .map(Comedian::retire)
        //         .ifPresent(comedianRepository::save);
        // TODO
    }

    @PutMapping("/v2/joke/{id}/owner")
    void reassign(@PathVariable Long id, @RequestBody AssignmentDto request) {

        // JokeOwner current = ownerRepository.findByJokeIdAndUntilIsNull(id);
        // Comedian comedian = comedianRepository.findByName(request.getName());
        //
        // current.reassign(comedian, request.getSince())
        //         .forEach(ownerRepository::save);
        // TODO
    }

    @PatchMapping("/v2/joke/{id}")
    void rephrase(@PathVariable Long id, @RequestBody RephraseDto request) {
        jokeRepository.findById(id)
                .map(joke -> joke.setQuestion(request.getQuestion()))
                .ifPresent(jokeRepository::save);

        // JokeVersion current = versionRepository.findByJokeIdAndUntilIsNull(id);
        // current.change(request.getQuestion(), request.getSince())
        //         .forEach(versionRepository::save);
        // TODO
    }
}
