package Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public void register(String username, String password, boolean isAdmin) {
        users.put(username, new User(username, password, isAdmin));
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<Training> getAllTrainings() {
        List<Training> allTrainings = new ArrayList<>();
        Iterator<Map.Entry<String, User>> iterator = users.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, User> entry = iterator.next();
            User user = entry.getValue();
            allTrainings.addAll(user.getTrainings());
        }
        return allTrainings;
    }
}
