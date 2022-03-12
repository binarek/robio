package binarek.robio;

import binarek.robio.auth.UserRepository;
import binarek.robio.auth.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Console;
import java.util.UUID;

@SpringBootApplication
public class RobioInitializerApplication implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    RobioInitializerApplication(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(RobioInitializerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        final var console = System.console();

        final var adminUsername = readAdminUsername(console);
        final var adminPassword = readAdminPassword(console);
        setupAdminUser(adminUsername, adminPassword, console);

        console.printf("Initializing Robio application finished with success\n");
    }

    private String readAdminUsername(Console console) {
        var username = console.readLine("Username for admin user (default 'admin'): ");
        while (invalidUsername(username)) {
            username = console.readLine("Invalid username, try again: ");
        }
        return username.isEmpty() ? "admin" : username;
    }

    private char[] readAdminPassword(Console console) {
        var password = console.readPassword("Password for admin user: ");
        while (invalidPassword(password)) {
            password = console.readPassword("Password does not meet security policy, try again: ");
        }
        return password;
    }

    private void setupAdminUser(String username, char[] password, Console console) {
        console.printf("Setting password for " + username + " user...\n");

        final var adminUsername = Username.of(username);
        final var adminUserId = userRepository.getByUsername(adminUsername)
                .map(User::getUserId)
                .orElseGet(() -> UserId.of(UUID.randomUUID()));
        final var adminPassword = HashedPassword.of(passwordEncoder.encode(String.valueOf(password)));

        final var adminUser = User.newUser(adminUserId, adminUsername, adminPassword, UserRole.ADMIN);

        userRepository.save(adminUser);
    }

    private boolean invalidUsername(String username) {
        return !username.isEmpty() && !Username.isValidUsername(username);
    }

    private boolean invalidPassword(char[] password) {
        if (password.length < 3) {
            return true;
        }
        for (char c : password) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }
}
