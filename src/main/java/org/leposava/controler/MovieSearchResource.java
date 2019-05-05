package org.leposava.controler;

import org.leposava.DTO.MovieDTO;
import org.leposava.model.Movie;
import org.leposava.repository.MovieDAO;
import org.leposava.util.UtilClass;

import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;
import java.util.List;

@Path("search/movies")
@RequestScoped
public class MovieSearchResource {

    @Context
    UriInfo info;

    @GET
    @Path("bytitle")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response searchForMoviesByTitle(@QueryParam(value = "title") String title) {

        if(title == null ||  title==""){
            return   Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Movie> movies = MovieDAO.getInstance().findMovesByTitle(title);
        if(movies == null ||  movies.size() <= 0){
            return   Response.status(Response.Status.NOT_FOUND).build();
        }
        List<MovieDTO> movieDTOS = new LinkedList<>();
        for(Movie movie : movies) {
            MovieDTO movieDTO= UtilClass.setMoveDTO(movie);
            String photoUrl = info.getBaseUri().toString() + movieDTO.getPhotoURL();
            movieDTO.setPhotoURL(photoUrl);
            movieDTOS.add(movieDTO);
        }

        return Response.ok(movieDTOS.toArray(new MovieDTO[movieDTOS.size()])).build();

    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response searchForMoviesBetweenTwoYears(@QueryParam(value = "year") List<String> years) {

        if(years == null || years.size() < 2 || years.get(0)=="" || years.get(1)==""
        || Integer.parseInt(years.get(0)) > Integer.parseInt(years.get(1))){
          return   Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Movie> movies = MovieDAO.getInstance().findMovesByYear(years);
        List<MovieDTO> movieDTOS = new LinkedList<>();
        for(Movie movie : movies) {
            MovieDTO movieDTO= UtilClass.setMoveDTO(movie);
            String photoUrl = info.getBaseUri().toString() + movieDTO.getPhotoURL();
            movieDTO.setPhotoURL(photoUrl);
            movieDTOS.add(movieDTO);
        }

        return Response.ok(movieDTOS.toArray(new MovieDTO[movieDTOS.size()])).build();

    }
}

