package Project;

import java.util.*;

public class TrainingDiaryApp {
    private UserManager userManager;
    private User currentUser;
    private Scanner scanner;
    private TrainingTypeManager typeManager;

    public TrainingDiaryApp() {
        this.userManager = new UserManager();
        this.currentUser = null;
        this.scanner = new Scanner(System.in);
        this.typeManager = new TrainingTypeManager();
        // Добавляем начальные типы тренировок
        typeManager.addTrainingType("кардио", "Кардио-тренировки");
        typeManager.addTrainingType("силовая", "Силовые тренировки");
        typeManager.addTrainingType("йога", "Йога");
        // Добавьте другие типы тренировок по необходимости
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Некорректный ввод");
            }
        }
    }

    private void registerUser(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.print("Является ли пользователь администратором? (true/false): ");
        boolean isAdmin = scanner.nextBoolean();
        scanner.nextLine();
        userManager.register(username, password, isAdmin);
        System.out.println("Пользователь зарегистрирован успешно.");
    }


    private void loginUser(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        currentUser = userManager.login(username, password);
        if (currentUser != null) {
            System.out.println("Вход выполнен успешно.");
            userMenu(scanner);
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }

    private void userMenu(Scanner scanner) {
        boolean loggedIn = true;
        while (loggedIn) {
            // Выводим заголовок меню
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║              Меню пользователя             ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. Добавить тренировку                     ║");
            System.out.println("║ 2. Просмотреть тренировки                  ║");
            System.out.println("║ 3. Добавить пользовательский тип тренировки║");
            System.out.println("║ 4. Отображение всех возможных тренировок   ║"); // Новый пункт меню
            System.out.println("║ 5. Редактировать тренировку                ║");
            System.out.println("║ 6. Удалить тренировку                      ║");
            System.out.println("║ 7. Получить статистику                     ║");
            System.out.println("║ 8. Выход                                   ║");
            System.out.println("╚════════════════════════════════════════════╝");

            // Запрашиваем у пользователя действие
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            switch (choice) {
                case 1:
                    addTraining(scanner);
                    break;
                case 2:
                    viewTrainings();
                    break;
                case 3:
                    addCustomTrainingType(scanner);
                    break;
                case 4:
                    displayAllTrainingTypes(); // Новый метод для отображения всех типов тренировок
                    break;
                case 5:
                    editTraining(scanner);
                    break;
                case 6:
                    deleteTraining(scanner);
                    break;
                case 7:
                    showStatistics();
                    break;
                case 8:
                    loggedIn = false;
                    currentUser = null;
                    System.out.println("Выход выполнен успешно.");
                    break;
                default:
                    System.out.println("Некорректный ввод");
            }
        }
    }

    private String inputDate(Scanner scanner) {
        String date = "";
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Введите дату тренировки (гггг-мм-дд): ");
            date = scanner.nextLine();
            if (isValidDate(date)) {
                validDate = true;
            } else {
                System.out.println("Некорректная дата. Пожалуйста, введите дату в формате гггг-мм-дд и убедитесь, что она корректна.");
            }
        }
        return date;
    }

    private boolean isValidDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        if (month < 1 || month > 12) {
            return false;
        }
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day < 1 || day > 31)) {
            return false;
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && (day < 1 || day > 30)) {
            return false;
        }
        if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0 && year % 100 == 0)) {
                return day >= 1 && day <= 29;
            } else {
                return day >= 1 && day <= 28;
            }
        }
        return true;
    }

    private int readIntFromUser(Scanner scanner, String message) {
        int value;
        while (true) {
            try {
                System.out.print(message);
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите целое число.");
            }
        }
        return value;
    }

    private void addTraining(Scanner scanner) {
        String date;
        while (true) {
            try {
                System.out.print("Введите дату тренировки (гггг-мм-дд): ");
                date = scanner.nextLine();
                if (!isValidDate(date)) {
                    throw new IllegalArgumentException("Некорректный формат даты. Используйте гггг-мм-дд.");
                }
                break; // Если дата валидна, выходим из цикла
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); // Выводим сообщение об ошибке
            }
        }

        String typeName;
        while (true) {
            System.out.print("Введите тип тренировки: ");
            typeName = scanner.nextLine();
            if (!typeManager.getAllTypes().contains(typeName.toLowerCase())) {
                System.out.println("Некорректный тип тренировки. Пожалуйста, выберите тип из списка:");
                displayAllTrainingTypes();
                continue; // Продолжаем цикл, чтобы запросить у пользователя правильное название тренировки
            }
            // Проверка наличия тренировки данного типа на указанную дату
            if (currentUser.hasTrainingOnDate(date, typeName)) {
                System.out.println("Тренировка данного типа на указанную дату уже существует.");
                continue; // Продолжаем цикл, чтобы запросить у пользователя другое название тренировки
            }
            break; // Если тип тренировки введен корректно, выходим из цикла
        }

        int duration;
        while (true) {
            try {
                System.out.print("Введите длительность тренировки (в минутах): ");
                duration = Integer.parseInt(scanner.nextLine());
                if (duration <= 0) {
                    throw new IllegalArgumentException("Длительность тренировки должна быть положительным числом.");
                }
                break; // Если длительность введена корректно, выходим из цикла
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректный формат длительности тренировки. Введите целое положительное число.");
            }
        }

        int calories;
        while (true) {
            try {
                System.out.print("Введите количество потраченных калорий: ");
                calories = Integer.parseInt(scanner.nextLine());
                if (calories < 0) {
                    throw new IllegalArgumentException("Количество потраченных калорий не может быть отрицательным числом.");
                }
                break; // Если количество калорий введено корректно, выходим из цикла
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректный формат количества потраченных калорий. Введите целое неотрицательное число.");
            }
        }

        System.out.print("Введите дополнительную информацию: ");
        String additionalInfo = scanner.nextLine();

        currentUser.addTraining(new Training(date, typeManager.getTrainingType(typeName), duration, calories, additionalInfo, currentUser));

        System.out.println("Тренировка добавлена успешно.");
    }



    private void viewTrainings() {
        List<Training> trainings;
        if (currentUser.isAdmin()) {
            // Администратор может видеть тренировки всех пользователей
            trainings = userManager.getAllTrainings();
        } else {
            // Пользователь может видеть только свои тренировки
            trainings = currentUser.getTrainings();
        }

        if (trainings.isEmpty()) {
            System.out.println("У вас еще нет тренировок.");
        } else {
            Collections.sort(trainings, Comparator.comparing(Training::getDate));
            for (Training training : trainings) {
                String ownerInfo = currentUser.isAdmin() ? " (пользователь: " + training.getOwner().getUsername() + ")" : "";
                System.out.println(training + ownerInfo);
            }
        }
    }

    private void editTraining(Scanner scanner) {
        System.out.print("Введите дату тренировки (гггг-мм-дд): ");
        String date = scanner.nextLine();

        List<Training> trainingsOnDate = currentUser.getTrainingsByDate(date);

        if (trainingsOnDate.isEmpty()) {
            System.out.println("На указанную дату нет тренировок.");
        } else {
            displayTrainings(trainingsOnDate);
            int index = readIntFromUser(scanner, "Введите номер тренировки для редактирования: ") - 1;
            if (index >= 0 && index < trainingsOnDate.size()) {
                System.out.println("Введите 'cancel', чтобы отменить редактирование.");
                editSingleTraining(trainingsOnDate.get(index), scanner);
            } else {
                System.out.println("Некорректный номер тренировки.");
            }
        }
    }

    private void editSingleTraining(Training training, Scanner scanner) {
        System.out.println("Выберите параметр для редактирования:");
        System.out.println("1. Дата тренировки");
        System.out.println("2. Тип тренировки");
        System.out.println("3. Длительность тренировки");
        System.out.println("4. Количество потраченных калорий");
        System.out.println("5. Дополнительная информация");
        System.out.print("Введите номер параметра: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                editTrainingDate(training, scanner);
                break;
            case 2:
                editTrainingType(training, scanner);
                break;
            case 3:
                editTrainingDuration(training, scanner);
                break;
            case 4:
                editTrainingCalories(training, scanner);
                break;
            case 5:
                editTrainingAdditionalInfo(training, scanner);
                break;
            default:
                System.out.println("Некорректный ввод.");
        }
    }

    private void editTrainingDate(Training training, Scanner scanner) {
        System.out.print("Введите новую дату тренировки (гггг-мм-дд): ");
        String newDate = scanner.nextLine();
        if (newDate.equalsIgnoreCase("cancel")) {
            System.out.println("Отменено.");
            return;
        }
        training.setDate(newDate);
        System.out.println("Дата тренировки успешно изменена.");
    }

    private void editTrainingType(Training training, Scanner scanner) {
        String typeName;
        while (true) {
            System.out.print("Введите новый тип тренировки: ");
            typeName = scanner.nextLine();
            if (typeName.equalsIgnoreCase("cancel")) {
                System.out.println("Отменено.");
                return;
            }
            if (!typeManager.getAllTypes().contains(typeName.toLowerCase())) {
                System.out.println("Некорректный тип тренировки. Пожалуйста, выберите тип из списка:");
                displayAllTrainingTypes();
                continue;
            }
            if (currentUser.hasTrainingOnDate(training.getDate(), typeName)) {
                System.out.println("Тренировка данного типа на указанную дату уже существует.");
                continue;
            }
            break;
        }
        training.setType(typeManager.getTrainingType(typeName));
        System.out.println("Тип тренировки успешно изменен.");
    }

    private void editTrainingDuration(Training training, Scanner scanner) {
        System.out.print("Введите новую длительность тренировки (в минутах): ");
        String durationString = scanner.nextLine();
        if (durationString.equalsIgnoreCase("cancel")) {
            System.out.println("Отменено.");
            return;
        }
        int newDuration;
        try {
            newDuration = Integer.parseInt(durationString);
            if (newDuration <= 0) {
                throw new IllegalArgumentException("Длительность тренировки должна быть положительным числом.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат длительности тренировки. Введите целое положительное число.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        training.setDurationMinutes(newDuration);
        System.out.println("Длительность тренировки успешно изменена.");
    }

    private void editTrainingCalories(Training training, Scanner scanner) {
        System.out.print("Введите новое количество потраченных калорий: ");
        String caloriesString = scanner.nextLine();
        if (caloriesString.equalsIgnoreCase("cancel")) {
            System.out.println("Отменено.");
            return;
        }
        int newCalories;
        try {
            newCalories = Integer.parseInt(caloriesString);
            if (newCalories < 0) {
                throw new IllegalArgumentException("Количество потраченных калорий не может быть отрицательным числом.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат количества потраченных калорий. Введите целое неотрицательное число.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        training.setCaloriesBurned(newCalories);
        System.out.println("Количество потраченных калорий успешно изменено.");
    }

    private void editTrainingAdditionalInfo(Training training, Scanner scanner) {
        System.out.print("Введите новую дополнительную информацию: ");
        String newInfo = scanner.nextLine();
        if (newInfo.equalsIgnoreCase("cancel")) {
            System.out.println("Отменено.");
            return;
        }
        training.setAdditionalInfo(newInfo);
        System.out.println("Дополнительная информация успешно изменена.");
    }

    private void deleteTraining(Scanner scanner) {
        System.out.print("Введите дату тренировки (гггг-мм-дд): ");
        String date = scanner.nextLine();

        List<Training> trainingsOnDate = currentUser.getTrainingsByDate(date);

        if (trainingsOnDate.isEmpty()) {
            System.out.println("На указанную дату нет тренировок.");
        } else {
            displayTrainings(trainingsOnDate);
            int index = readIntFromUser(scanner, "Введите номер тренировки для удаления: ") - 1;
            if (index >= 0 && index < trainingsOnDate.size()) {
                System.out.println("Введите 'cancel', чтобы отменить удаление. Нажмите Enter, чтобы подтвердить удаление.");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("cancel")) {
                    System.out.println("Отменено.");
                    return;
                }
                currentUser.deleteTraining(trainingsOnDate.get(index));
                System.out.println("Тренировка успешно удалена.");
            } else {
                System.out.println("Некорректный номер тренировки.");
            }
        }
    }

    private void showStatistics() {
        List<Training> trainings = currentUser.getTrainings();
        int totalDuration = 0;
        int totalCaloriesBurned = 0;
        int averageDuration = 0;
        double averageCaloriesBurned = 0;

        if (!trainings.isEmpty()) {
            for (Training training : trainings) {
                totalDuration += training.getDurationMinutes();
                totalCaloriesBurned += training.getCaloriesBurned();
            }
            averageDuration = totalDuration / trainings.size();
            averageCaloriesBurned = (double) totalCaloriesBurned / trainings.size();

            System.out.println("Статистика по тренировкам:");
            System.out.println("Всего тренировок: " + trainings.size());
            System.out.println("Общая продолжительность тренировок: " + totalDuration + " минут");
            System.out.println("Средняя продолжительность тренировок: " + averageDuration + " минут");
            System.out.println("Общее количество потраченных калорий: " + totalCaloriesBurned);
            System.out.println("Среднее количество потраченных калорий: " + averageCaloriesBurned);
        } else {
            System.out.println("У вас еще нет тренировок для отображения статистики.");
        }
    }

    private void addCustomTrainingType(Scanner scanner) {
        System.out.print("Введите название нового типа тренировки: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание нового типа тренировки: ");
        String description = scanner.nextLine();
        typeManager.addTrainingType(name, description);
        System.out.println("Новый тип тренировки добавлен успешно.");
    }

    private void displayAllTrainingTypes() {
        System.out.println("Все доступные типы тренировок:");
        Set<String> allTrainingTypes = typeManager.getAllTypes();
        for (String typeName : allTrainingTypes) {
            String description = typeManager.getDescription(typeName);
            System.out.println(typeName + ": " + description);
        }
    }

    private void displayTrainings(List<Training> trainings) {
        System.out.println("Тренировки на указанную дату:");
        for (int i = 0; i < trainings.size(); i++) {
            System.out.println((i + 1) + ". " + trainings.get(i));
        }
    }
}

