package cloudflight.integra.backend.databaseseed;

import cloudflight.integra.backend.event.EventRepository;
import cloudflight.integra.backend.event.model.Event;
import cloudflight.integra.backend.hobbyGroup.HobbyGroupRepository;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.location.LocationRepository;
import cloudflight.integra.backend.location.model.Location;
import cloudflight.integra.backend.systemFeedback.SystemFeedbackRepository;
import cloudflight.integra.backend.systemFeedback.model.SystemFeedback;
import cloudflight.integra.backend.tag.TagRepository;
import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.user.Role;
import cloudflight.integra.backend.user.UserRepository;
import cloudflight.integra.backend.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final Faker faker = new Faker(new Random(123));

    public DatabaseSeedService(
        UserRepository userRepository, LocationRepository locationRepository,
        TagRepository tagRepository, SystemFeedbackRepository systemFeedbackRepository,
        HobbyGroupRepository hobbyGroupRepository, EventRepository eventRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.tagRepository = tagRepository;
        this.systemFeedbackRepository = systemFeedbackRepository;
        this.hobbyGroupRepository = hobbyGroupRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (userRepository.count() > 0) {
            return;
        }
        String password = passwordEncoder.encode("Password12@");

        User admin = new User();
        admin.setUsername("Leo");
        admin.setEmail("leo@commonground.com");
        admin.setPassword(password);
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        int i = 3;
        while (i > 0) {
            User user = new User();
            user.setUsername(faker.funnyName().name());
            user.setEmail(faker.internet().safeEmailAddress());
            user.setPassword(password);
            user.setRole(Role.USER);
            userRepository.save(user);
            i--;
        }
    }

    private void seedLocationsTable() {
        if (locationRepository.count() > 0) {
            return;
        }
        int i = 10;
        while (i > 0) {
            Location location = new Location();
            location.setName(faker.lordOfTheRings().location());
            location.setLatitude(faker.number().randomDouble(4, 0, 90));
            location.setLongitude(faker.number().randomDouble(4, 0, 90));
            locationRepository.save(location);
            i--;
        }

    }

    private void seedSystemFeedbackTable() {
        if (systemFeedbackRepository.count() > 0) {
            return;
        }
        int i = 10;
        while (i > 0) {
            SystemFeedback systemFeedback = new SystemFeedback();
            systemFeedback.setEmail(faker.internet().emailAddress());
            systemFeedback.setMessage(faker.chuckNorris().fact());
            systemFeedback.setCreatedAt(LocalDateTime.of(2026, 4, faker.number().numberBetween(1, 30), 12, 0));
            systemFeedbackRepository.save(systemFeedback);
            i--;
        }


    }

    private void seedTagsTable() {
        if (tagRepository.count() > 0) {
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
            tag.setLabel(label);
            tag.setNormalizedLabel(label.toLowerCase());
            tagRepository.save(tag);
        }

    }

    private void seedHobbyGroupsTable() {
        if (hobbyGroupRepository.count() > 0) {
            return;
        }
        int i = 10;
        User user = userRepository.findByEmail("leo@commonground.com");
        while (i > 0) {
            HobbyGroup hobbyGroup = new HobbyGroup();
            hobbyGroup.setName(faker.space().nebula());
            hobbyGroup.setDescription(faker.yoda().quote());
            hobbyGroup.setRadiusKm(faker.number().numberBetween(0, 10));
            hobbyGroup.setOwner(user);
            hobbyGroupRepository.save(hobbyGroup);
            i--;
        }

    }

    private void seedEventsTable() {
        if (eventRepository.count() > 0) {
            return;
        }
        List<Location> locations = locationRepository.findAll();
        List<HobbyGroup> hobbyGroups = hobbyGroupRepository.findAll();
        if (locations.isEmpty() || hobbyGroups.isEmpty()) return;
        int i = 10;
        while (i > 0) {
            Event event = new Event();
            event.setTitle(faker.cat().name());
            LocalDateTime startTime = LocalDateTime.of(2026, 4, faker.number().numberBetween(1, 30), 12, 0);
            event.setStartTime(startTime);
            event.setEndTime(startTime.plusHours(2));
            event.setLocation(locations.get(faker.number().numberBetween(0, locations.size())));
            event.setHobbyGroup(hobbyGroups.get(faker.number().numberBetween(0, hobbyGroups.size())));
            eventRepository.save(event);
            i--;
        }
    }

}
