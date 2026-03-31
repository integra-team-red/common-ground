package cloudflight.integra.backend.databaseseed;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
@RequestMapping("/api/seed")
public class DatabaseSeedController {
    private final DatabaseSeedService databaseSeedService;

    public DatabaseSeedController(DatabaseSeedService databaseSeedService) {
        this.databaseSeedService = databaseSeedService;
    }

    @PostMapping
    public ResponseEntity<String> seedDatabase() {
        databaseSeedService.seedDatabase();
        return ResponseEntity.ok("Database seeded successfully!");
    }

    @DeleteMapping
    public ResponseEntity<String> clearDatabase() {
        databaseSeedService.clearDatabase();
        return ResponseEntity.ok("Database cleared successfully!");
    }
}
