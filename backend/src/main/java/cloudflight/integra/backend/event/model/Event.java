package cloudflight.integra.backend.event.model;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name="hobby_group_id", nullable = false)
    private HobbyGroup hobbyGroup;


    public Event(UUID id, String title, LocalDateTime startTime, LocalDateTime endTime, HobbyGroup hobbyGroup) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hobbyGroup = hobbyGroup;
    }
    public Event(){}

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public HobbyGroup getHobbyGroup() {
        return hobbyGroup;
    }

    public void setHobbyGroup(HobbyGroup hobbyGroup) {
        this.hobbyGroup = hobbyGroup;
    }
}
