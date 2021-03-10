package ru.perm.v.integrtest.repository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles({"test"})
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
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }
}