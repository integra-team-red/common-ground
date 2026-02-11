package cloudflight.integra.backend.hobbyGroup.model;

import java.util.UUID;

public class HobbyGroup {
    // Create a HobbyGroup POJO with fields: id (UUID), name, description, and radiusKm[cite: 41, 55].
    private UUID id;
    private String name;
    private String description;
    private double radiusKm;

    public HobbyGroup(UUID id, String name, String description, double radiusKm) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.radiusKm = radiusKm;
    }


    public UUID getId() {return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public double getRadiusKm() { return radiusKm; }

    public void setId(UUID id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {this.description = description; }

    public void setRadiusKm(double radiusKm) { this.radiusKm = radiusKm;  }
}
