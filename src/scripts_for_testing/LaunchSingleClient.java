package scripts_for_testing;

import chatapp.Client;
import chatapp.User;

import java.util.Random;

/**
 * Created by Yitzi on 2/26/2016.
 */
public class LaunchSingleClient {

    public static void main(String[] args) {
        Random random = new Random();
        int name = random.nextInt(899999) + 100000;
        User user = new User("Guest " + name);
        new Client(user);
    }

}
