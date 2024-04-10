package Project;

import java.util.*;

public class TrainingTypeManager {
    private Map<String, String> trainingTypes;

    public TrainingTypeManager() {
        this.trainingTypes = new HashMap<>();
    }

    public void addTrainingType(String type, String description) {
        trainingTypes.put(type.toLowerCase(), description);
    }

    public String getDescription(String type) {
        return trainingTypes.get(type.toLowerCase());
    }

    public Set<String> getAllTypes() {
        return trainingTypes.keySet();
    }

    public TrainingType getTrainingType(String typeName) {
        String typeDescription = trainingTypes.get(typeName.toLowerCase());
        if (typeDescription != null) {
            return new TrainingType(typeName, typeDescription);
        }
        return null;
    }
}
