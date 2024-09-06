package com.example.jokes.service;


import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.jokes.domain.JokeDTO;
import com.example.jokes.exceptions.JokeNotAvailableException;
import com.example.jokes.model.Joke;


@ApplicationScoped
public class JokeService {

    @Inject
    @RestClient
    JokeClient jokeClient;

    public Uni<List<JokeDTO>> getJokes(int count) {
        int numberOfBatches = (count + 9) / 10;
        List<Uni<Joke>> jokeRequests = IntStream.range(0, numberOfBatches * 10)
                .mapToObj(i -> fetchSingleJoke())
                .collect(Collectors.toList());

        return Uni.combine().all().unis(jokeRequests)
                .combinedWith(results -> {
                    List<Joke> jokes = results.stream()
                            .filter(result -> result instanceof Joke)
                            .map(result -> (Joke) result)
                            .filter(joke -> !joke.getSetup().equals("No Joke"))
                            .limit(count)
                            .collect(Collectors.toList());
                    if (jokes.isEmpty()) {
                        throw new JokeNotAvailableException("Jokes are not available from the external server.");
                    }
                    storeJokes(jokes).subscribe().with(
                        success -> System.out.println("Jokes successfully saved to DB"),
                        failure -> System.err.println("Failed to save jokes to DB: " + failure.getMessage())
                    );
                    return jokes.stream()
                            .map(this::mapToJokeDTO)
                            .collect(Collectors.toList());
                })
                .onFailure().recoverWithItem(new ArrayList<>());
    }

    private JokeDTO mapToJokeDTO(Joke joke) {
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setId(joke.getId().toString());
        jokeDTO.setQuestion(joke.getSetup());
        jokeDTO.setAnswer(joke.getPunchline());
        return jokeDTO;
    }

    private Uni<Joke> fetchSingleJoke() {
        return jokeClient.getRandomJoke().onFailure().recoverWithItem(new Joke("No Joke", "Server unavailable"));
    }

    @Transactional
    public Uni<Void> saveJoke(Joke joke) {
    	return joke.persist().replaceWithVoid();
    }


    @Transactional
    public Uni<Void> storeJokes(List<Joke> jokes) {
        List<Uni<Void>> saveJokes = jokes.stream()
                .map(this::saveJoke)
                .collect(Collectors.toList());
        return Uni.combine().all().unis(saveJokes)
                .combinedWith(result -> null);
    }

}

