package cloudflight.integra.backend.hobbyGroup.model;

import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.user.model.User;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class HobbyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private double radiusKm;

    @ManyToMany
    @JoinTable(
        name = "Group_Tags",
        joinColumns = {@JoinColumn(name = "hobby_group_id")},
        inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
        name = "group_members",
        joinColumns = @JoinColumn(name = "hobby_group_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final List<User> members = new LinkedList<>();


    public HobbyGroup(
        UUID id,
        String name,
        String description,
        double radiusKm,
        List<Tag> tags,
        User owner
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.radiusKm = radiusKm;
        this.tags = tags;
        this.owner = owner;
        this.members.add(owner);
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

    public List<Tag> getTags() {
        return tags;
    }

    public User getOwner() {
        return owner;
    }

    public List<User> getMembers() {
        return members;
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

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
