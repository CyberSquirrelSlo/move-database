package org.leposava.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.GUIDGenerator;
import org.hibernate.type.BinaryType;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@Entity(name="movie")
public class Movie  implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "movie_id",unique = true,updatable = false,length = 36)
    private String id;
    private String title;
    private String year;
    private String description;
    @Column(name = "photo", length = 100000)
    private byte[] photo;
    @ElementCollection(targetClass= org.hibernate.mapping.Set.class)
    @Column(length = 100000)
    private Set<Actor> actors = new HashSet<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


    @ManyToMany(mappedBy = "movies", cascade = CascadeType.ALL)
    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }


}
