package red.lisgar.api.entidades;

import java.util.List;

public class Personajes {
    private String name;
    private String status;
    private String species;
    private String gender;
    private String nameorigin;
    private String urlorigin;
    private String namelocation;
    private String urllocation;
    private String image;
    /*private List episode;*/
    private String url;

    public Personajes(String name, String status, String species, String gender, String nameorigin, String urlorigin, String namelocation, String urllocation, String image, String url) {
        this.name = name;
        this.status = status;
        this.species = species;
        this.gender = gender;
        this.nameorigin = nameorigin;
        this.urlorigin = urlorigin;
        this.namelocation = namelocation;
        this.urllocation = urllocation;
        this.image = image;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getGender() {
        return gender;
    }

    public String getNameorigin() {
        return nameorigin;
    }

    public String getUrlorigin() {
        return urlorigin;
    }

    public String getNamelocation() {
        return namelocation;
    }

    public String getUrllocation() {
        return urllocation;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}