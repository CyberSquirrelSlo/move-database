package org.leposava.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.type.BinaryType;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@XmlRootElement
@Entity(name = "actor")
public class Actor  implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "actor_id", updatable = false, nullable = false)
    private String id;
    private String firstName;
    private String lastName;
    private Date bornDate;

    @Lob
    @Column(name = "photo", length = 100000)
    private byte[] photo;

    @ElementCollection(targetClass= org.hibernate.mapping.Set.class)
    @Column(length = 100000)
    private Set<Movie> movies = new HashSet<>();



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    @ManyToMany(cascade = { CascadeType.ALL },
            fetch = FetchType.EAGER

    )
    @JoinTable(name = "movies_actors",
            joinColumns = { @JoinColumn(name = "actor_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }



    @Override
    public  String toString(){
       return  getFirstName();
    }

}
