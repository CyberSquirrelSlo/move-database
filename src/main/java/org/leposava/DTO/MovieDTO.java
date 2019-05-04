package org.leposava.DTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;
@XmlRootElement(name = "Movie")
public class MovieDTO {

    private String id;
    private String title;
    private String year;
    private String description;
    private String photoURL;
    private Set<String> actorsIds = new HashSet<>();

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

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Set<String> getActorsIds() {
        return actorsIds;
    }

    public void setActorsIds(Set<String> actorsIds) {
        this.actorsIds = actorsIds;
    }
}
