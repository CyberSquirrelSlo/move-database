package org.leposava.DTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "Actor")
public class ActorDTO {


    private String id;
    private String firstName;
    private String lastName;
    private Date bornDate;

    private String photoURL;

    private Set<String> moviesIds = new HashSet<>();

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

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Set<String> getMoviesIds() {
        return moviesIds;
    }

    public void setMoviesIds(Set<String> moviesIds) {
        this.moviesIds = moviesIds;
    }

    @Override
    public  String toString(){
        return  getFirstName();
    }

}
