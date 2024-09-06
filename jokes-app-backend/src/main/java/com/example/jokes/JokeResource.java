package com.example.jokes;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import com.example.jokes.exceptions.JokeNotAvailableException;
import com.example.jokes.service.JokeService;

@Path("/jokes")
public class JokeResource {

    @Inject
    JokeService jokeService;

    @GET
    public Uni<Response> getJokes(@QueryParam("count") int count) {
        if (count <= 0 || count > 100) {
            return Uni.createFrom().item(
                    Response.status(400).entity(new ErrorResponse("Request number of jokes is out of limits")).build());
        }
        return jokeService.getJokes(count)
                .onItem().transform(jokes -> Response.ok(jokes).build())
                .onFailure(JokeNotAvailableException.class)
                .recoverWithItem(ex -> Response.status(503).entity(new ErrorResponse(ex.getMessage())).build())
                .onFailure().recoverWithItem(Response.serverError().entity(new ErrorResponse("Jokes server is temporarily unavailable")).build());
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}