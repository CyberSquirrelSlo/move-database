package org.leposava;

import org.leposava.model.Actor;
import org.leposava.model.Movie;
import org.leposava.repository.MovieDAO;
import org.leposava.util.UtilClass;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Startup
@Singleton
public class StartupClass {

    @PostConstruct
    public void init() {
        populateDB();
    }

    private void populateDB() {


        Movie movie = new Movie();
        movie.setTitle("Pred dozdot");
        movie.setDescription("Milčo Mančevski");
        movie.setYear("1995");

        byte[] photo = UtilClass.readPhoto("E:\\dev\\java-workspace\\move-database\\src\\main\\resources\\images\\flash.jpg");
        movie.setPhoto(photo);


        movie =  MovieDAO.getInstance().persist(movie);
        Actor actor = new Actor();
        actor.setFirstName("Tom");
        actor.setLastName("Cruise");
        String pattern = "yyyy-MM-dd";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse("1962-07-03");
        }catch (ParseException e){
            e.printStackTrace();
        }

        actor.setBornDate(date);
        Movie movie1 = new Movie();
        movie1.setDescription("Christopher McQuarrie");
        movie1.setTitle("Mission: Impossible - Fallout (2018) ");
        movie1.setYear("2018");

        byte[] photo1 = UtilClass.readPhoto("E:\\dev\\java-workspace\\move-database\\src\\main\\resources\\images\\missionimposible.jpg");
        movie1.setPhoto(photo1);


        Set<Movie> movies = new HashSet<>();

        Set<Actor> actors = new HashSet<Actor>();


        movie1 =  MovieDAO.getInstance().persist(movie1);
        movies.add(movie1);
        actor.setMovies(movies);
        actor  =  MovieDAO.getInstance().persist(actor);
        actors.add(actor);
        movie1.setActors(actors);
        movie1 =  MovieDAO.getInstance().persist(movie1);
        movie.setActors(actors);
        MovieDAO.getInstance().persist(movie);
        for(Movie m :  actor.getMovies()){
            Movie mm =   MovieDAO.getInstance().find(Movie.class, m.getId());
            // return mm.getDescription();
        }
        Actor newActor = new Actor();
        newActor.setFirstName("Lepa");
        newActor.setLastName("Knez");
        date = null;
        try {
            date = simpleDateFormat.parse("1978-06-20");
        }catch (ParseException e){
            e.printStackTrace();
        }

        newActor.setBornDate(date);
        Set<Movie> movies1 = new HashSet<>();
        movies1.add(movie);
        newActor.setMovies(movies1);
        newActor =  MovieDAO.getInstance().persist(newActor);
        actors.add(newActor);
        movie1.setActors(actors);
        MovieDAO.getInstance().persist(movie1);
    }



}
