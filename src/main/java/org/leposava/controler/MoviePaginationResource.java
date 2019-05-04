package org.leposava.controler;

import org.leposava.DTO.MovieDTO;
import org.leposava.DTO.MoviePagingDTO;
import org.leposava.model.Movie;
import org.leposava.repository.MovieDAO;
import org.leposava.util.UtilClass;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    @Path("{startingFrom}/{pageSize}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCustomers(@PathParam("startingFrom") int startingFrom,
                                 @PathParam("pageSize") int pageSize) {

      try {
          long totalRecord = MovieDAO.getInstance().getMoviesSize();
          List<Movie> movieList = MovieDAO.getInstance().getMoviesPages(startingFrom, pageSize);
          List<MovieDTO> movieDTOS = new LinkedList<>();
          for (Movie movie : movieList) {
              MovieDTO movieDTO = UtilClass.setMoveDTO(movie);
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
