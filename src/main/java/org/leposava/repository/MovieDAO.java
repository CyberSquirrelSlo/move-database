package org.leposava.repository;


import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import org.leposava.model.Actor;
import org.leposava.model.Movie;

import javax.naming.NamingException;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MovieDAO implements IMovieDAO {


    private static MovieDAO instance;

    protected MovieDAO() {
        // initialization
    }

    public static MovieDAO getInstance() {
        if (instance == null) {
            instance = new MovieDAO();
        }
        return instance;
    }

    private EntityManager entityManager;

    protected EntityManager getEntityManager() throws NamingException {
        if(this.entityManager == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                SQLServerDriver driver = new SQLServerDriver();


                EntityManagerFactory emf = Persistence.createEntityManagerFactory("movie");
                EntityManager em = emf.createEntityManager();
                em.setFlushMode(FlushModeType.AUTO);

                this.entityManager = em;
                return em;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return  this.entityManager;
    }



    @Override
    public void setEntityManager(EntityManager entityManager) {
        if( this.entityManager == null)
            try {
                this.entityManager = getEntityManager();
            }catch (NamingException e){
                e.printStackTrace();
            }
    }

    @Override
    public Movie saveMovie(Movie movie){
        Set<Actor> actorSet = new HashSet<>();
        for(Actor a : movie.getActors()){
            a = persist(a);
            actorSet.add(a);
        }
        movie.setActors(actorSet);
        movie = persist(movie);

        return movie;

    }


    @Override
    public Movie updateMovie(Movie movie, String movieID){
        Movie updatedMovie = find(Movie.class, movieID);
        updatedMovie.setTitle(movie.getTitle());
        updatedMovie.setDescription(movie.getDescription());
        updatedMovie.setPhoto(movie.getPhoto());
        updatedMovie.setYear(movie.getYear());

        Set<Actor> actorSet = new HashSet<>();
        for(Actor a : movie.getActors()){
            a = persist(a);
            actorSet.add(a);
        }
        movie.setActors(actorSet);
        updatedMovie.setActors(movie.getActors());

        updatedMovie = persist(updatedMovie);

        return updatedMovie;

    }

    @Override
    public Actor saveActor(Actor actor) {
        Set<Movie> movieSet = new HashSet<>();
        for(Movie m : actor.getMovies()){
            m = persist(m);
            movieSet.add(m);
        }
        actor.setMovies(movieSet);
        actor = persist(actor);

        return actor;
    }

    @Override
    public Actor updateActor(Actor actor, String actorID) {
        Actor updatedActor = find(Actor.class, actorID);
        updatedActor.setFirstName(actor.getFirstName());
        updatedActor.setLastName(actor.getLastName());
        updatedActor.setBornDate(actor.getBornDate());
        updatedActor.setPhoto(actor.getPhoto());
        Set<Movie> movieSet = new HashSet<>();
        for(Movie m : actor.getMovies()){
            m = persist(m);
            movieSet.add(m);
        }
        actor.setMovies(movieSet);
        updatedActor.setMovies(actor.getMovies());
        updatedActor = persist(updatedActor);

        return updatedActor;
    }

    @Override
    public List<Movie> findMovesByYear(List<String> years) {
        try {
            TypedQuery<Movie> query = getEntityManager().createQuery("SELECT m   FROM movie m where  m.year between :y1 and :y2"
                    , Movie.class);
            query.setParameter("y1", years.get(0));
            query.setParameter("y2",years.get(1));

            return query.getResultList();
        }catch (NamingException e){
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Long getMoviesSize() {
        try {
            Query query = getEntityManager().createNativeQuery("SELECT COUNT(*) FROM movie m ");
            long count = ((Number) query.getSingleResult()).intValue();
            return count;
        }catch (NamingException e){
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public List<Movie> getMoviesPages(int startingFrom, int pageSize) {
        int pageNumber = startingFrom;

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = criteriaBuilder
                .createQuery(Long.class);
        countQuery.select(criteriaBuilder
                .count(countQuery.from(Movie.class)));
        Long count = entityManager.createQuery(countQuery)
                .getSingleResult();

        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder
                .createQuery(Movie.class);
        Root<Movie> from = criteriaQuery.from(Movie.class);
        CriteriaQuery<Movie> select = criteriaQuery.select(from);

        TypedQuery<Movie> typedQuery = entityManager.createQuery(select);
        while (pageNumber < count.intValue()) {
            typedQuery.setFirstResult(pageNumber);
            typedQuery.setMaxResults(pageSize);
            System.out.println("Current page: " + typedQuery.getResultList());
            pageNumber += pageSize;
        }
       return typedQuery.getResultList();
    }


    @Override
    public <T> T persist(T entity) {
        try {
             getEntityManager().getTransaction().begin();
             T entity_r =  getEntityManager().merge(entity);
             getEntityManager().flush();
             getEntityManager().getTransaction().commit();
             return  entity_r;
        }catch (NamingException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T find(Class<? extends T> entityClass, Object primaryKey) {
        try{
            return getEntityManager().find(entityClass, primaryKey);

        }catch (NamingException e){
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public List<Actor> getAllActors(){
        try {
             TypedQuery<Actor> query = getEntityManager().createQuery("SELECT a  FROM actor a"
                     , Actor.class);

            return query.getResultList();
        }catch (NamingException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Movie> getAllMovies(){
        try {
            TypedQuery<Movie> query = getEntityManager().createQuery("SELECT m   FROM movie m "
                    , Movie.class);

            return query.getResultList();
        }catch (NamingException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Object entity) {
        try {
            getEntityManager().remove(getEntityManager().contains(entity) ? entity : getEntityManager().merge(entity));
        }catch (NamingException e){
            e.printStackTrace();
        }
    }

}