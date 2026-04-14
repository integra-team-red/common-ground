package cloudflight.integra.backend.databaseseed;

import cloudflight.integra.backend.event.EventRepository;
import cloudflight.integra.backend.event.EventService;
import cloudflight.integra.backend.event.model.Event;
import cloudflight.integra.backend.hobbyGroup.HobbyGroupRepository;
import cloudflight.integra.backend.hobbyGroup.HobbyGroupService;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.location.LocationRepository;
import cloudflight.integra.backend.location.LocationService;
import cloudflight.integra.backend.location.model.Location;
import cloudflight.integra.backend.security.RegisterRequest;
import cloudflight.integra.backend.systemFeedback.SystemFeedbackRepository;
import cloudflight.integra.backend.systemFeedback.SystemFeedbackService;
import cloudflight.integra.backend.systemFeedback.model.SystemFeedback;
import cloudflight.integra.backend.tag.TagRepository;
import cloudflight.integra.backend.tag.TagService;
import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.user.Role;
import cloudflight.integra.backend.user.UserRepository;
import cloudflight.integra.backend.user.UserService;
import cloudflight.integra.backend.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class DatabaseSeedService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final TagRepository tagRepository;
    private final SystemFeedbackRepository systemFeedbackRepository;
    private final HobbyGroupRepository hobbyGroupRepository;
    private final EventRepository eventRepository;
    private final UserService userService;
    private final LocationService locationService;
    private final TagService tagService;
    private final SystemFeedbackService systemFeedbackService;
    private final HobbyGroupService hobbyGroupService;
    private final EventService eventService;
    private final Faker faker = new Faker(new Random(123));

    public DatabaseSeedService(
        UserRepository userRepository,
        LocationRepository locationRepository,
        TagRepository tagRepository,
        SystemFeedbackRepository systemFeedbackRepository,
        HobbyGroupRepository hobbyGroupRepository,
        EventRepository eventRepository,

        UserService userService,
        LocationService locationService,
        TagService tagService,
        SystemFeedbackService systemFeedbackService,
        HobbyGroupService hobbyGroupService,
        EventService eventService
    ) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.tagRepository = tagRepository;
        this.systemFeedbackRepository = systemFeedbackRepository;
        this.hobbyGroupRepository = hobbyGroupRepository;
        this.eventRepository = eventRepository;

        this.eventService = eventService;
        this.userService = userService;
        this.locationService = locationService;
        this.tagService = tagService;
        this.systemFeedbackService = systemFeedbackService;
        this.hobbyGroupService = hobbyGroupService;
    }

    public void seedDatabase() {
        seedUsersTable();
        seedLocationsTable();
        seedTagsTable();
        seedSystemFeedbackTable();
        seedHobbyGroupsTable();
        seedEventsTable();
    }

    public void clearDatabase() {
        eventRepository.deleteAll();
        hobbyGroupRepository.deleteAll();
        systemFeedbackRepository.deleteAll();
        tagRepository.deleteAll();
        locationRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void seedUsersTable() {
        if (userService.getByUsername("Leo") != null) {
            return;
        }

        RegisterRequest adminReq = new RegisterRequest(
            "Leo",
            "leo@commonground.com",
            "Password12@"
        );
        User admin = userService.register(adminReq);
        admin.setRole(Role.ADMIN);
        admin.setJoinedDate(LocalDateTime.of(2026, 4, 1, 12, 0));

        int i = 3;
        while (i > 0) {
            RegisterRequest userReq = new RegisterRequest(
                faker.funnyName().name(),
                faker.internet().safeEmailAddress(),
                "Password12@"
            );
            User user = userService.register(userReq);
            user.setJoinedDate(LocalDateTime.of(
                2026, 4, faker.number().numberBetween(1, 30), 12, 0));
            i--;
        }
    }

    private void seedLocationsTable() {
        if (!locationService.getAll(Pageable.unpaged()).isEmpty()) {
            return;
        }
        int i = 10;
        while (i > 0) {
            Location location = new Location();
            location.setName(truncate(faker.lordOfTheRings().location()));
            location.setLatitude(faker.number().randomDouble(4, 0, 90));
            location.setLongitude(faker.number().randomDouble(4, 0, 90));
            locationService.create(location);
            i--;
        }

    }

    private void seedSystemFeedbackTable() {
        if (!systemFeedbackService.getAll().isEmpty()) {
            return;
        }
        int i = 10;
        while (i > 0) {
            SystemFeedback systemFeedback = new SystemFeedback();
            systemFeedback.setEmail(faker.internet().emailAddress());
            systemFeedback.setMessage(truncate(faker.chuckNorris().fact()));
            systemFeedback.setCreatedAt(LocalDateTime.of(
                2026, 4, faker.number().numberBetween(1, 30), 12, 0));
            systemFeedbackService.create(systemFeedback);
            i--;
        }


    }

    private void seedTagsTable() {
        if (!tagService.getAll(Pageable.unpaged()).isEmpty()) {
            return;
        }
        String[] labels = {
            faker.harryPotter().spell(),
            faker.lordOfTheRings().location(),
            faker.gameOfThrones().house(),
            faker.pokemon().name(),
            faker.superhero().power()
        };

        for (String label : labels) {
            Tag tag = new Tag();
            tag.setLabel(truncate(label));
            tag.setNormalizedLabel(truncate(label).toLowerCase());
            tagService.create(tag);
        }

    }

    private void seedHobbyGroupsTable() {
        if (!hobbyGroupService.getAll(Pageable.unpaged()).isEmpty()) {
            return;
        }
        int i = 10;
        User user = userService.findByEmail("leo@commonground.com");
        while (i > 0) {
            HobbyGroup hobbyGroup = new HobbyGroup();
            hobbyGroup.setName(truncate(faker.space().nebula()));
            hobbyGroup.setDescription(truncate(faker.yoda().quote()));
            hobbyGroup.setRadiusKm(faker.number().numberBetween(0, 10));
            hobbyGroup.setOwner(user);
            hobbyGroupService.create(hobbyGroup);
            i--;
        }

    }

    private void seedEventsTable() {
        if (!eventService.getAll(Pageable.unpaged()).isEmpty()) {
            return;
        }
        List<Location> locations = locationService.getAllList();
        List<HobbyGroup> hobbyGroups = hobbyGroupService.getAllList();
        if (locations.isEmpty() || hobbyGroups.isEmpty()) return;
        int i = 10;
        while (i > 0) {
            Event event = new Event();
            event.setTitle(truncate(faker.cat().name()));
            LocalDateTime startTime = LocalDateTime.of(
                2026, 4, faker.number().numberBetween(1, 30), 12, 0);
            event.setStartTime(startTime);
            event.setEndTime(startTime.plusHours(2));
            event.setLocation(locations.get(faker.number().numberBetween(0, locations.size())));
            event.setHobbyGroup(hobbyGroups.get(faker.number().numberBetween(0, hobbyGroups.size())));
            eventService.create(event);
            i--;
        }
    }

    private String truncate(String text) {
        if (text == null) return null;
        return text.length() > 100 ? text.substring(0, 90) + "..." : text;
    }

}
