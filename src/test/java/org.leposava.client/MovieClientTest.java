package org.leposava.client;

import org.junit.jupiter.api.Test;
import org.leposava.DTO.MovieDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieClientTest {

    @Test
    void getMovies() {
        MovieClient movieClient = new MovieClient();
        List<MovieDTO> movieDTOList =  movieClient.getMovies();
        assertNotNull(movieDTOList);
    }
}