package org.leposava.client;

import org.leposava.DTO.MovieDTO;
import org.leposava.model.Movie;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class MovieClient {

    private Client client;

    public MovieClient(){
        client = ClientBuilder.newClient();
    }

    public List<MovieDTO> getMovies(){
        WebTarget target = client.target("localhost:8080/move-database/webapi/");
        List<MovieDTO> response = target.path("movies")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<MovieDTO>>() {});

        return response;

    }
}
