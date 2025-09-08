package com.openclassrooms.mddapi.config;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataSeeder {

        @Bean
        public CommandLineRunner commandLineRunner(
                        ThemeRepository themeRepository,
                        UserRepository userRepository,
                        ArticleRepository articleRepository,
                        PasswordEncoder passwordEncoder) {
                return args -> {
                        // --- Seed User (Improved Logic) ---
                        // Try to find the default user. If it doesn't exist, create it.
                        User defaultUser = userRepository.findByEmail("testuser@example.com")
                                        .orElseGet(() -> {
                                                System.out.println("Default user not found. Creating default user...");
                                                User newUser = User.builder()
                                                                .email("testuser@example.com")
                                                                .username("testuser")
                                                                .password(passwordEncoder.encode("password"))
                                                                .build();
                                                return userRepository.save(newUser);
                                        });
                        System.out.println("Default user is available.");

                        // --- Seed Themes ---
                        List<Theme> themes = new ArrayList<>();
                        if (themeRepository.count() == 0) {
                                System.out.println("Seeding database with initial themes...");
                                Theme theme1 = Theme.builder().title("Java")
                                                .description("Discussions about Java, Spring, and the JVM ecosystem.")
                                                .build();
                                Theme theme2 = Theme.builder().title("JavaScript")
                                                .description("All things JavaScript: React, Angular, Vue, Node.js, and more.")
                                                .build();
                                Theme theme3 = Theme.builder().title("Databases")
                                                .description("Topics related to SQL, NoSQL, performance, and data modeling.")
                                                .build();
                                Theme theme4 = Theme.builder().title("DevOps")
                                                .description("Continuous integration, delivery, Docker, Kubernetes, and cloud infrastructure.")
                                                .build();
                                themes = List.of(theme1, theme2, theme3, theme4);
                                themeRepository.saveAll(themes);
                                System.out.println("Themes seeding complete.");
                        } else {
                                themes = themeRepository.findAll();
                                System.out.println("Themes already exist. Skipping theme seeding.");
                        }

                        // --- Seed Articles ---
                        if (articleRepository.count() == 0) {
                                System.out.println("Seeding database with initial articles...");
                                List<Article> articles = new ArrayList<>();

                                // Java Articles
                                Theme javaTheme = themes.stream().filter(t -> t.getTitle().equals("Java")).findFirst()
                                                .get();
                                articles.add(Article.builder().title("Exploring Spring Boot 3 Features").content(
                                                "Spring Boot 3 brings a new baseline of Java 17, support for GraalVM native images, and observability with Micrometer...")
                                                .theme(javaTheme).user(defaultUser).build());
                                articles.add(Article.builder().title("Understanding Java's Virtual Threads").content(
                                                "Project Loom introduces virtual threads to the JVM, a lightweight concurrency model that promises to simplify writing high-throughput applications...")
                                                .theme(javaTheme).user(defaultUser).build());

                                // JavaScript Articles
                                Theme jsTheme = themes.stream().filter(t -> t.getTitle().equals("JavaScript"))
                                                .findFirst().get();
                                articles.add(Article.builder().title("React Server Components vs. Client Components")
                                                .content(
                                                                "A deep dive into React's new architecture. Server Components run on the server and are not interactive, reducing the client-side bundle size...")
                                                .theme(jsTheme).user(defaultUser).build());
                                articles.add(Article.builder().title("The Rise of TypeScript").content(
                                                "Why has TypeScript become the industry standard for large-scale JavaScript projects? We explore static typing, improved tooling, and scalability...")
                                                .theme(jsTheme).user(defaultUser).build());

                                // Databases Articles
                                Theme dbTheme = themes.stream().filter(t -> t.getTitle().equals("Databases"))
                                                .findFirst().get();
                                articles.add(Article.builder().title("SQL vs. NoSQL: Which to Choose?").content(
                                                "The eternal debate. We break down the use cases for relational databases like PostgreSQL versus NoSQL databases like MongoDB...")
                                                .theme(dbTheme).user(defaultUser).build());
                                articles.add(Article.builder().title("The Importance of Database Indexing").content(
                                                "Slow queries? The first place to look is your indexing strategy. An index is a data structure that improves the speed of data retrieval operations...")
                                                .theme(dbTheme).user(defaultUser).build());

                                // DevOps Articles
                                Theme devopsTheme = themes.stream().filter(t -> t.getTitle().equals("DevOps"))
                                                .findFirst().get();
                                articles.add(Article.builder().title("Getting Started with Kubernetes").content(
                                                "Kubernetes, or K8s, is an open-source system for automating deployment, scaling, and management of containerized applications.")
                                                .theme(devopsTheme).user(defaultUser).build());
                                articles.add(Article.builder().title("Infrastructure as Code with Terraform").content(
                                                "Terraform allows you to build, change, and version infrastructure safely and efficiently. This post covers the basics of HCL and provider configuration.")
                                                .theme(devopsTheme).user(defaultUser).build());

                                articleRepository.saveAll(articles);
                                System.out.println("Articles seeding complete.");
                        } else {
                                System.out.println("Articles already exist. Skipping article seeding.");
                        }
                        System.out.println("\n \n" +
                                        "Access the swagger at the following url : http://localhost:8080/swagger-ui/index.html#/\n"
                                        +
                                        "The test user should have been created with:\n" +
                                        "Email: testuser@example.com\n" +
                                        "Username: testuser\n" +
                                        "Password: password" + "\n\n");

                };
        }
}