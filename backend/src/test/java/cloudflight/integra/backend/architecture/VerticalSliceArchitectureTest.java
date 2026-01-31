package cloudflight.integra.backend.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Tag("architecture")
class VerticalSliceArchitectureTest {

    private static final String BASE_PACKAGE = "cloudflight.integra.backend";

    @Test
    void controllersShouldOnlyDependOnServices() {
        JavaClasses imported = new ClassFileImporter().importPackages(BASE_PACKAGE);

        ArchRuleDefinition.noClasses()
            .that().areAnnotatedWith(RestController.class)
            .should().dependOnClassesThat()
            .areAnnotatedWith(Repository.class)
            .check(imported);
    }

    @Test
    void servicesShouldNotDependOnControllersOrDtos() {
        JavaClasses imported = new ClassFileImporter().importPackages(BASE_PACKAGE);

        ArchRuleDefinition.noClasses()
            .that().areAnnotatedWith(Service.class)
            .should().dependOnClassesThat()
            .areAnnotatedWith(RestController.class)
            .orShould().dependOnClassesThat()
            .haveSimpleNameEndingWith("Dto")
            .check(imported);
    }

    @Test
    void repositoriesShouldNotDependOnServicesOrControllersOrDtos() {
        JavaClasses imported = new ClassFileImporter().importPackages(BASE_PACKAGE);

        ArchRuleDefinition.noClasses()
            .that().areAnnotatedWith(Repository.class)
            .should().dependOnClassesThat()
            .areAnnotatedWith(Service.class)
            .orShould().dependOnClassesThat()
            .areAnnotatedWith(RestController.class)
            .orShould().dependOnClassesThat()
            .haveSimpleNameEndingWith("Dto")
            .check(imported);
    }
}
