package graphtutorial;

import java.util.Arrays;
import java.util.List;

import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.UserCollectionPage;

import java.io.IOException;
import java.util.Properties;

/**
 * Graph Tutorial
 *
 */
public class App {

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println("Java Graph Tutorial");
        System.out.println();

        // Load OAuth settings
        final Properties oAuthProperties = new Properties();
        try {
            oAuthProperties.load(App.class.getResourceAsStream("oAuth.properties"));
        } catch (IOException e) {
            System.out.println(
                    "Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
            return;
        }

        final String appId = oAuthProperties.getProperty("app.id");
        final String appSecret = oAuthProperties.getProperty("app.secret");
        final String tenant_guid = oAuthProperties.getProperty("app.tenant_guid");
        final List<String> appScopes = Arrays
                .asList(oAuthProperties.getProperty("app.scopes").split(","));

        // Initialize Graph with auth settings
        Graph.initializeGraphAuth(appId, appSecret, tenant_guid, appScopes);
        final String accessToken = Graph.getUserAccessToken();

        System.out.println("Access token: " + accessToken);

        // Greet the user
        UserCollectionPage usersList = Graph.getUsers();
        List<User> users = usersList.getCurrentPage();
        for (User user : users) {
            System.out.println("Welcome " + user.displayName);
            System.out.println("mail: " + user.userPrincipalName);
            System.out.println();
        }

    }
}