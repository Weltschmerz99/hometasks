package Project;

public class Training {
    private User owner; // Добавляем поле owner

    private String date;
    private TrainingType type;
    private int durationMinutes;
    private int caloriesBurned;
    private String additionalInfo;

    public Training(String date, TrainingType type, int durationMinutes, int caloriesBurned, String additionalInfo, User owner) {
        this.date = date;
        this.type = type;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
        this.additionalInfo = additionalInfo;
        this.owner = owner;
    }

    public String getDate() {
        return date;
    }

    public TrainingType getType() {
        return type;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public User getOwner() {
        return owner;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(TrainingType type) {
        this.type = type;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        String typeName = (type != null) ? type.getName() : "Неопределенный тип";
        return "Дата: " + date +
                ", Тип тренировки: " + typeName +
                ", Длительность: " + durationMinutes + " мин" +
                ", Потраченные калории: " + caloriesBurned +
                ", Дополнительная информация: " + additionalInfo;
    }
}