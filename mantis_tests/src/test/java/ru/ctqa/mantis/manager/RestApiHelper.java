package ru.ctqa.mantis.manager;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.UserApi;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.model.User;
import ru.ctqa.mantis.model.UserData;

public class RestApiHelper extends HelperBase {
    public RestApiHelper(ApplicationManager manager) {
        super(manager);
        ApiClient defClient = Configuration.getDefaultApiClient();
        ApiKeyAuth auth = (ApiKeyAuth) defClient.getAuthentication("Authorization");
        auth.setApiKey(manager.property("apiKey"));
    }

    public void createUser(UserData userData) {
        UserApi apiInstance = new UserApi();

        User user = new User();
        user.setUsername(userData.username());
        user.setPassword(userData.password());
        user.setRealName(userData.realname());
        user.setEmail(userData.email());

        try {
            apiInstance.userAdd(user);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#userAdd");
            e.printStackTrace();
        }
    }
}