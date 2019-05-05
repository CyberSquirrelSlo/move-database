package org.leposava.repository;

import org.leposava.model.Actor;
import org.leposava.model.Movie;
import org.primefaces.json.JSONArray;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public interface IMovieDAO extends Serializable {
    void setEntityManager(EntityManager entityManager);

    Movie saveMovie(Movie movie);

    Actor saveActor(Actor actor);

    <T> T persist(T entity);

    <T> T find(Class<? extends T> entityClass, Object primaryKey);

    List<Actor> getAllActors();

    List<Movie> getAllMovies();

    void remove(Object entity);

    Movie updateMovie(Movie movie, String movieID);

    Actor updateActor(Actor actor, String actorID);


    List<Movie> findMovesByYear(List<String> years);

    Long getMoviesSize();

    List<Movie> getMoviesPages(int startingFrom, int pageSize, String order);

    long getActorsSize();

    List<Actor> getActorsPages(int startingFrom, int pageSize, String order);

    List<Movie> findMovesByTitle(String titile);

    List<Movie> getMoviesPagesBetweenTwoYears(String year1, String year2, int startingFrom, int pageSize, String order);

    Long getMoviesBetweenSize(String year1, String year2);
}
