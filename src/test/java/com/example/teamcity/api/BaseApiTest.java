package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.models.AuthModules;
import com.example.teamcity.api.models.ServerAuthSettings;
import com.example.teamcity.api.requests.ServerAuthRequest;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;

public class BaseApiTest extends BaseTest {
    private final ServerAuthRequest serverAuthRequest = new ServerAuthRequest(Specifications.getSpec().superUserAuth());
    private AuthModules authModules;
    private boolean perProjectPermissioins;

    @BeforeSuite(alwaysRun = true)
    public void setUpServerAuthSettings() {
        // получаем текущие настройки PerProjectPermissions
        perProjectPermissioins = serverAuthRequest.read().getPerProjectPermissions();

        authModules = generate(AuthModules.class);

        // обновляем значение PerProjectPermissions
        serverAuthRequest.update(ServerAuthSettings.builder()
                .perProjectPermissions(true)
                .modules(authModules)
                .build());
    }

    @AfterSuite(alwaysRun = true)
    public void cleanUpServerAuthSettings() {
        // возвращаем настройки PerProjectPermissions в исходное значение
        serverAuthRequest.update(ServerAuthSettings.builder()
                .perProjectPermissions(perProjectPermissioins)
                .modules(authModules)
                .build());

    }
}
