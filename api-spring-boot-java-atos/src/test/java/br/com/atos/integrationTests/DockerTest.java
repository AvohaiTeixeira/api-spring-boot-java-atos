package br.com.atos.integrationTests;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class DockerTest {

    @Container
    static MySQLContainer<?> mySql =
            new MySQLContainer<>("mysql:8.0.36");

    @Test
    void test() {
        System.out.println(mySql.getJdbcUrl());
    }
}
