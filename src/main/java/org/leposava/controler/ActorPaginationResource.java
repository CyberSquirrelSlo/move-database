package org.leposava.controler;

import org.leposava.DTO.ActorDTO;
import org.leposava.DTO.ActorPagingDTO;
import org.leposava.DTO.MovieDTO;
import org.leposava.DTO.MoviePagingDTO;
import org.leposava.model.Actor;
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
@Path("actors")
public class ActorPaginationResource {


    @Context
    UriInfo info;

    @GET
    @Path("{startingFrom}/{pageSize}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getMovies(@PathParam("startingFrom") int startingFrom,
                              @PathParam("pageSize") int pageSize) {

        try {
            long totalRecord = MovieDAO.getInstance().getActorsSize();
            List<Actor> actorList = MovieDAO.getInstance().getActorsPages(startingFrom, pageSize, "asc");
            if(actorList == null || actorList.size()<=0){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            List<ActorDTO> actorDTOS = new LinkedList<>();
            for (Actor actor : actorList) {
                ActorDTO actorDTO = UtilClass.setActorDTO(actor);
                actorDTOS.add(actorDTO);
            }

            ActorPagingDTO actorPagingDTO = new ActorPagingDTO();
            actorPagingDTO.setActorDTOList(actorDTOS);
            actorPagingDTO.setTotalRecord(totalRecord);
            actorPagingDTO.setPageSize(pageSize);

            double pages = (double) totalRecord / (double) pageSize;
            actorPagingDTO.setPages(pages);

            double pageOn = (double) (pageSize + startingFrom) / (double) pageSize;
            actorPagingDTO.setPageOn(pageOn);
            actorPagingDTO.setNoOfPages(Math.ceil(pages));
            actorPagingDTO.setPageRatio((int) Math.floor(pageOn) + "/" + (int) Math.ceil(pages));

            String baseUri = info.getBaseUri().toString();
            baseUri = baseUri + "movies/";
            if (totalRecord > (startingFrom + pageSize)) {
                actorPagingDTO.setNextPage(baseUri + (startingFrom + pageSize) + "/"
                        + pageSize);
            } else {
                actorPagingDTO.setNextPage(null);
            }
            actorPagingDTO.setCurrentPage(info.getAbsolutePath().toString());
            if (startingFrom <= 0) {
                actorPagingDTO.setPrevPage(null);
            } else if (startingFrom - pageSize <= 0) {
                actorPagingDTO.setPrevPage(baseUri + "0/" + pageSize);
            } else {
                actorPagingDTO.setPrevPage(baseUri + (startingFrom - pageSize) + "/"
                        + pageSize);
            }

            return Response.ok(actorPagingDTO).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
