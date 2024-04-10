package Project;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Training> trainings;
    private boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.trainings = new ArrayList<>();
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public void addTraining(Training training) {
        trainings.add(training);
    }

    public List<Training> getTrainings() {
        return trainings;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean deleteTraining(Training training) {
        return trainings.remove(training);
    }

    public List<Training> getTrainingsByDate(String date) {
        List<Training> trainingsOnDate = new ArrayList<>();
        for (Training training : trainings) {
            if (training.getDate().equals(date)) {
                trainingsOnDate.add(training);
            }
        }
        return trainingsOnDate;
    }

    public boolean hasTrainingOnDate(String date, String typeName) {
        for (Training training : trainings) {
            if (training.getDate().equals(date) && training.getType().getName().equalsIgnoreCase(typeName)) {
                return true;
            }
        }
        return false;
    }
}
