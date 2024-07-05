package com.nabil.seeder;

import com.nabil.models.Account;
import com.nabil.models.Role;
import com.nabil.models.Course;
import com.nabil.services.AccountService;
import com.nabil.services.RoleService;
import com.nabil.repositories.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** Synthetic local data only. Never activate this profile on a public server. */
@Component
@Profile("demo")
public class DemoSeeder implements CommandLineRunner {
    private final AccountService accounts;
    private final RoleService roles;
    private final CourseRepository courses;
    public DemoSeeder(AccountService accounts, RoleService roles, CourseRepository courses) {
        this.accounts = accounts; this.roles = roles; this.courses = courses;
    }
    public void run(String... args) {
        Role student = new Role("ROLE_USER");
        Role teacher = new Role("ROLE_ADMIN");
        roles.addRole(student); roles.addRole(teacher); roles.addRole(new Role("ROLE_SUPERADMIN"));
        Account learner = new Account("student@example.com", "LocalDemo123!", "Demo", "Student");
        learner.setRole(student); learner.setChips("100"); accounts.addAccount(learner);
        Account instructor = new Account("teacher@example.com", "LocalDemo123!", "Demo", "Teacher");
        instructor.setRole(teacher); instructor.setChips("100"); accounts.addAccount(instructor);
        courses.save(new Course("Demo Mathematics", "Mathematics", "Demo", "teacher@example.com",
            "Beginner", 0.0, "", "Synthetic course for exploring the local demo."));
    }
}
