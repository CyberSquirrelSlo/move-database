package org.leposava.controler;

import org.leposava.DTO.MovieDTO;
import org.leposava.DTO.MoviePagingDTO;
import org.leposava.model.Movie;
import org.leposava.repository.MovieDAO;
import org.leposava.util.UtilClass;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;
import java.util.List;
@Stateless
@Path("movies")
public class MoviePaginationResource {

    @Context
    UriInfo info;

    @GET
    @Path("{startingFrom}/{pageSize}/search")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getMoviesBetweenYears(@PathParam("startingFrom") int startingFrom,
                                          @PathParam("pageSize") int pageSize,
                                          @QueryParam("year") List<String> years) {

        if( years == null || years.size() < 2 || years.get(0)=="" || years.get(1)==""
                || Integer.parseInt(years.get(0)) > Integer.parseInt(years.get(1))){
            return   Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            long totalRecord = MovieDAO.getInstance().getMoviesBetweenSize(years.get(0),years.get(1));
            List<Movie> movieList = MovieDAO.getInstance().getMoviesPagesBetweenTwoYears(years.get(0),years.get(1),startingFrom, pageSize, "asc");
            if(movieList == null || movieList.size()<=0){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            List<MovieDTO> movieDTOS = new LinkedList<>();
            for (Movie movie : movieList) {
                MovieDTO movieDTO = UtilClass.setMoveDTO(movie);
                String photoUrl = info.getBaseUri().toString() + movieDTO.getPhotoURL();
                movieDTO.setPhotoURL(photoUrl);
                movieDTOS.add(movieDTO);
            }

            MoviePagingDTO moviePagingDTO = new MoviePagingDTO();
            moviePagingDTO.setMovieDTOList(movieDTOS);
            moviePagingDTO.setTotalRecord(totalRecord);
            moviePagingDTO.setPageSize(pageSize);

            double pages = (double) totalRecord / (double) pageSize;
            moviePagingDTO.setPages(pages);

            double pageOn = (double) (pageSize + startingFrom) / (double) pageSize;
            moviePagingDTO.setPageOn(pageOn);
            moviePagingDTO.setNoOfPages(Math.ceil(pages));
            moviePagingDTO.setPageRatio((int) Math.floor(pageOn) + "/" + (int) Math.ceil(pages));

            String baseUri = info.getBaseUri().toString();
            baseUri = baseUri + "movies/";
            if (totalRecord > (startingFrom + pageSize - 1)) {
                moviePagingDTO.setNextPage(baseUri + (startingFrom + pageSize) + "/"
                        + pageSize);
            } else {
                moviePagingDTO.setNextPage(null);
            }
            moviePagingDTO.setCurrentPage(info.getAbsolutePath().toString());
            if (startingFrom <= 1) {
                moviePagingDTO.setPrevPage(null);
            } else if (startingFrom - pageSize <= 0) {
                moviePagingDTO.setPrevPage(baseUri + "1/" + pageSize);
            } else {
                moviePagingDTO.setPrevPage(baseUri + (startingFrom - pageSize) + "/"
                        + pageSize);
            }

            return Response.ok(moviePagingDTO).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



    @GET
    @Path("{startingFrom}/{pageSize}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getMovies(@PathParam("startingFrom") int startingFrom,
                                 @PathParam("pageSize") int pageSize) {

      try {
          long totalRecord = MovieDAO.getInstance().getMoviesSize();
          List<Movie> movieList = MovieDAO.getInstance().getMoviesPages(startingFrom, pageSize, "asc");
          if(movieList == null || movieList.size()<=0){
              return Response.status(Response.Status.NOT_FOUND).build();
          }
          List<MovieDTO> movieDTOS = new LinkedList<>();
          for (Movie movie : movieList) {
              MovieDTO movieDTO = UtilClass.setMoveDTO(movie);
              String photoUrl = info.getBaseUri().toString() + movieDTO.getPhotoURL();
              movieDTO.setPhotoURL(photoUrl);
              movieDTOS.add(movieDTO);
          }

          MoviePagingDTO moviePagingDTO = new MoviePagingDTO();
          moviePagingDTO.setMovieDTOList(movieDTOS);
          moviePagingDTO.setTotalRecord(totalRecord);
          moviePagingDTO.setPageSize(pageSize);

          double pages = (double) totalRecord / (double) pageSize;
          moviePagingDTO.setPages(pages);

          double pageOn = (double) (pageSize + startingFrom) / (double) pageSize;
          moviePagingDTO.setPageOn(pageOn);
          moviePagingDTO.setNoOfPages(Math.ceil(pages));
          moviePagingDTO.setPageRatio((int) Math.floor(pageOn) + "/" + (int) Math.ceil(pages));

          String baseUri = info.getBaseUri().toString();
          baseUri = baseUri + "movies/";
          if (totalRecord > (startingFrom + pageSize - 1)) {
              moviePagingDTO.setNextPage(baseUri + (startingFrom + pageSize) + "/"
                      + pageSize);
          } else {
              moviePagingDTO.setNextPage(null);
          }
          moviePagingDTO.setCurrentPage(info.getAbsolutePath().toString());
          if (startingFrom <= 1) {
              moviePagingDTO.setPrevPage(null);
          } else if (startingFrom - pageSize <= 0) {
              moviePagingDTO.setPrevPage(baseUri + "1/" + pageSize);
          } else {
              moviePagingDTO.setPrevPage(baseUri + (startingFrom - pageSize) + "/"
                      + pageSize);
          }

          return Response.ok(moviePagingDTO).build();
      }catch (Exception e){
          e.printStackTrace();
      }
      return Response.status(Response.Status.BAD_REQUEST).build();
    }


}
