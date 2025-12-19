package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import com.github.viclovsky.swagger.coverage.FileSystemOutputWriter;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.nio.file.Paths;
import java.util.List;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;

public class Specifications {
    private static Specifications spec;

    private Specifications() {
    }

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder reqBuilder() {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri("http://" + Config.getProperty("host")).build();
        reqBuilder.setContentType(ContentType.JSON);
        reqBuilder.setAccept(ContentType.JSON);
        reqBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        reqBuilder.addFilter(new SwaggerCoverageRestAssured(
                new FileSystemOutputWriter(Paths.get("target/" + OUTPUT_DIRECTORY))
        ));

        return reqBuilder;
    } // Какие endpoint’ы из Swagger реально были вызваны тестами, а какие — нет?
    // target/ — это стандартная папка Maven, используется для артефактов сборки и отчётов

    // Swagger API Coverage integration:
// Rest Assured filter intercepts all outgoing HTTP requests,
// compares them with Swagger specification,
// marks executed endpoints as covered,
// and stores coverage data on the filesystem (target/swagger-coverage)
// for further report generation and CI integration.

    public RequestSpecification unauthSpec() {
        return reqBuilder().build();
    }

    public RequestSpecification authSpec(User username) {
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        basicAuthScheme.setUserName(username.getUsername());
        basicAuthScheme.setPassword(username.getPassword());
        return reqBuilder()
                .setAuth(basicAuthScheme)
                .build();

    }

    public RequestSpecification superUserAuth() {
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        basicAuthScheme.setUserName(""); // empty username
        basicAuthScheme.setPassword(Config.getProperty("superUserToken"));
        return reqBuilder()
                .setAuth(basicAuthScheme)
                .build();
    }

    public RequestSpecification mockSpec() {
        return reqBuilder()
                .setBaseUri("http://localhost:8081")
                .build();
    }
}
