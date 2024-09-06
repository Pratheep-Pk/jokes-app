package com.example.jokes.service;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.example.jokes.model.Joke;

@RegisterRestClient
@Path("/random_joke")
public interface JokeClient {
    @GET
    Uni<Joke> getRandomJoke();
}


