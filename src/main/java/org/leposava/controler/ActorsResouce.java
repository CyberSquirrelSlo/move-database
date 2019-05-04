package org.leposava.controler;

import org.leposava.DTO.ActorDTO;
import org.leposava.model.Actor;
import org.leposava.repository.MovieDAO;
import org.leposava.util.UtilClass;

import javax.faces.bean.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Path("actors")
@RequestScoped
public class ActorsResouce  {


    @POST
    @Path("actor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response  createActor(Actor actor){
        actor= MovieDAO.getInstance().saveActor(actor);
        ActorDTO actorDTO = UtilClass.setActorDTO(actor);

        return Response.status(Response.Status.CREATED ).entity(actorDTO).build();

    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllActors(){

       List<Actor> actorList =  MovieDAO.getInstance().getAllActors();
       List<ActorDTO> actorDTOS = new LinkedList<>();
       Set<Actor> actorSet = new HashSet<>();
       for( Actor a : actorList){
           actorSet.add(a);
           ActorDTO actorDTO = UtilClass.setActorDTO(a);
           actorDTOS.add(actorDTO);
       }
        if(actorDTOS == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok( actorDTOS.toArray(new ActorDTO[actorDTOS.size()])).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{actorID}")
    public Response getMove(@PathParam("actorID") String actorID){
        Actor actor = MovieDAO.getInstance().find(Actor.class, actorID);
        if(actor == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ActorDTO actorDTO = UtilClass.setActorDTO(actor);

        return Response.ok(actorDTO).build();

    }

    @GET
    @Produces("image/jpeg")
    @Path( "/{actorID}/image.jpg" )
    public Response getImageRepresentation(@PathParam("actorID") String actorID) {
        Actor actor = MovieDAO.getInstance().find(Actor.class, actorID);
        return Response.ok(actor.getPhoto()).build();
    }



    @PUT
    @Path("actor/{actorID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response  updateMovie(@PathParam("actorID") String actorID, Actor actor){
        actor.setId(actorID);
        actor = MovieDAO.getInstance().updateActor(actor, actorID);
        ActorDTO movieDTO = UtilClass.setActorDTO(actor);

        return Response.status(Response.Status.CREATED ).entity(movieDTO).build();

    }

    @DELETE
    @Path("actor/{actorID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response  deleteMovie(@PathParam("actorID") String actorID){

        Actor actorToDelete = MovieDAO.getInstance().find(Actor.class, actorID);
        if(actorToDelete == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        MovieDAO.getInstance().remove(actorToDelete);

        return Response.ok().build();

    }




}
