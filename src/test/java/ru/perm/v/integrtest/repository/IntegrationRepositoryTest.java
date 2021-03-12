package ru.perm.v.integrtest.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Slf4j
public abstract class IntegrationRepositoryTest {

    @Container
    @SuppressWarnings("rawtypes")
    public static PostgreSQLContainer postgreSQLContainer;

    static {
        DockerImageName postgres = DockerImageName.parse("postgres:13.1");

        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(postgres)
                .withReuse(true);

        postgreSQLContainer.start();
    }

    @SuppressWarnings("unused")
    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        log.info("=================================url: {}, username: {}, pass: {}",
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }
}