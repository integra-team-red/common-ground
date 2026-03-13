package cloudflight.integra.backend.hobbyGroup.model;

import cloudflight.integra.backend.user.model.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class HobbyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private double radiusKm;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
        name = "group_members",
        joinColumns = @JoinColumn(name = "hobby_group_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> members = new HashSet<>();

    public HobbyGroup(UUID id, String name, String description, double radiusKm, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.radiusKm = radiusKm;
        this.owner = owner;
    }

    public HobbyGroup() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getRadiusKm() {
        return radiusKm;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRadiusKm(double radiusKm) {
        this.radiusKm = radiusKm;
    }

    public Set<User> getMembers() {
        return members;
    }

    public User getOwner() {
        return owner;
    }


}
