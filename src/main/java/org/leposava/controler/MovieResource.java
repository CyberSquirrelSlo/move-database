package org.leposava.controler;

import org.leposava.DTO.MovieDTO;
import org.leposava.model.Movie;
import org.leposava.repository.MovieDAO;
import org.leposava.util.UtilClass;

import javax.faces.bean.RequestScoped;
import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


@Path("movies")
@RequestScoped
public class MovieResource {

    @Context
    UriInfo info;

    @DELETE
    @Path("movie/{movieID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response  deleteMovie(@PathParam("movieID") String movieID){

        Movie moveToDelete = MovieDAO.getInstance().find(Movie.class, movieID);
        if(moveToDelete == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        MovieDAO.getInstance().remove(moveToDelete);

        return Response.ok().build();

    }

    @PUT
    @Path("movie/{movieID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response  updateMovie(@PathParam("movieID") String movieID, Movie movie){
        movie.setId(movieID);
        movie = MovieDAO.getInstance().updateMovie(movie, movieID);
        MovieDTO movieDTO = UtilClass.setMoveDTO(movie);
        movieDTO.getPhotoURL();

        return Response.status(Response.Status.CREATED ).entity(movieDTO).build();

    }
    @POST
    @Path("movie")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response  createMovie(Movie movie){
        movie= MovieDAO.getInstance().saveMovie(movie);
        MovieDTO movieDTO = UtilClass.setMoveDTO(movie);
        String photoUrl = info.getBaseUri().toString() + movieDTO.getPhotoURL();
        movieDTO.setPhotoURL(photoUrl);
        return Response.status(Response.Status.CREATED ).entity(movieDTO).build();

    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllMovies(){

        List<Movie> movieList =  MovieDAO.getInstance().getAllMovies();
        List<MovieDTO> movieDtos = new LinkedList<>();
        for( Movie m : movieList){
            MovieDTO movieDTO = UtilClass.setMoveDTO(m);
            String photoUrl = info.getBaseUri().toString() + movieDTO.getPhotoURL();
            movieDTO.setPhotoURL(photoUrl);
            movieDtos.add(movieDTO);
        }
        if(movieDtos == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }


        return Response.ok(movieDtos.toArray(new MovieDTO[movieDtos.size()])).build();

       // return Response.ok(movieList.toArray(new Movie[movieList.size()])).build();
    }
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path( "{movieID}")
    public Response getMove(@PathParam("movieID") String movieID){
        Movie movie = MovieDAO.getInstance().find(Movie.class, movieID);
        if(movie == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        MovieDTO movieDTO = UtilClass.setMoveDTO(movie);
        String photoUrl = info.getBaseUri().toString() + movieDTO.getPhotoURL();
        movieDTO.setPhotoURL(photoUrl);

        return Response.status(Response.Status.FOUND).entity(movieDTO).build();

    }
    @GET
    @Produces("image/jpeg")
    @Path( "/{movieID}/image.jpg" )
    public Response  getImageRepresentation(@PathParam("movieID") String movieID) {
       Movie movie = MovieDAO.getInstance().find(Movie.class, movieID);

        ByteArrayInputStream bis = new ByteArrayInputStream(movie.getPhoto());
        try {
            BufferedImage bImage2 = ImageIO.read(bis);
            int height=  bImage2.getHeight();
            int width =  bImage2.getWidth();
            int new_height = 250;
            double new_width = new_height * (width*0.01*100 /height*0.01*100);
            int new_widthI = (int )new_width;
            bImage2 = UtilClass.resize(bImage2, new_widthI, new_height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( bImage2, "jpg", baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return  Response.ok(imageInByte).build();

        }catch (IOException e){
            e.printStackTrace();
        }

          return Response.ok(movie.getPhoto()).build();
    }
}
