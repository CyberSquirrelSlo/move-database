package org.leposava.util;

import org.leposava.DTO.ActorDTO;
import org.leposava.DTO.MovieDTO;
import org.leposava.model.Actor;
import org.leposava.model.Movie;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public final class UtilClass {


    public  static  MovieDTO setMoveDTO(Movie movie){
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setYear(movie.getYear());
        if(movie.getPhoto()!=null) {
            movieDTO.setPhotoURL("movies/"+movie.getId()+"/image.jpg");
        }
        Set<String> actorIds = new HashSet<>();
        for(Actor a : movie.getActors()){
            actorIds.add(a.getId());
        }
        movieDTO.setActorsIds(actorIds);

        return movieDTO;
    }

    public static ActorDTO setActorDTO(Actor actor) {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getId());
        actorDTO.setFirstName(actor.getFirstName());
        actorDTO.setLastName(actor.getLastName());
        actorDTO.setBornDate(actor.getBornDate());
        if(actor.getPhoto()!=null) {
            actorDTO.setPhotoURL("actors/"+actor.getId()+"/image.jpg");
        }
        Set<String> moviesIds = new HashSet<>();
        for(Movie m : actor.getMovies()){
            moviesIds.add(m.getId());
        }
        actorDTO.setMoviesIds(moviesIds);
        return actorDTO;
    }

    public  static  byte[] readPhoto(String path){
        try {
            BufferedImage image = ImageIO.read(new File(path));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            byte[] bi = new byte[os.size()];
            int i = 0;
            for (byte b : os.toByteArray()) {
                bi[i] = b;
                i++;
            }
            return  bi;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}
