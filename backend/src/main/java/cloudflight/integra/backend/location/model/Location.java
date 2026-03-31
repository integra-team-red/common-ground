package cloudflight.integra.backend.location.model;

import cloudflight.integra.backend.user.model.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Double latitude;
    private Double longitude;


    @ManyToOne
    @JoinColumn(name= "creator_id")
    private User creator;

    public Location() {
    }

    public Location(UUID id, String name, Double latitude, Double longitude, User creator) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creator = creator;
    }

    public UUID getId() {
        return id;
    }

    public Location setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Location setName(String name) {
        this.name = name;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Location setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Location setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public User getCreator() {
        return creator;
    }

    public Location setCreator(User creator) {
        this.creator = creator;
        return this;
    }
}
