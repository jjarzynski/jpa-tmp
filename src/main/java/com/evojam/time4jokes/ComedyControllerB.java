package com.evojam.time4jokes;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
class ComedyControllerB {

    JokeRepository jokeRepository;
    ReactionRepository reactionRepository;
    ComedianRepository comedianRepository;
    // TODO
    JokeOwnerRepository ownerRepository;
    JokeVersionRepository versionRepository;

    @GetMapping("/v2/joke/{id}/reaction")
    List<ReactionDto> reactions(@PathVariable Long id) {
        // TODO
        return reactionRepository.findByJokeIdWithOwnerAtTheTime(id);
    }

    @DeleteMapping("/v2/comedian/{id}")
    void retire(@PathVariable Long id) {
        comedianRepository.findById(id)
                .map(Comedian::retire)
                .ifPresent(comedianRepository::save);
    }

    @PutMapping("/v2/joke/{id}/owner")
    void reassign(@PathVariable Long id, @RequestBody AssignmentDto request) {
        // TODO

        JokeOwner current = ownerRepository.findByJokeIdAndUntilIsNull(id);
        Comedian comedian = comedianRepository.findByName(request.getName());

        current.reassign(comedian, request.getSince())
                .forEach(ownerRepository::save);
    }

    @PatchMapping("/v2/joke/{id}")
    void rephrase(@PathVariable Long id, @RequestBody RephraseDto request) {
        jokeRepository.findById(id)
                .map(joke -> joke.setQuestion(request.getQuestion()))
                .ifPresent(jokeRepository::save);

        // TODO

        JokeVersion current = versionRepository.findByJokeIdAndUntilNull(id);
        current.rephrase(request.getQuestion(), request.getSince())
                .forEach(versionRepository::save);
    }
}
