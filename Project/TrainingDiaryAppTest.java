package Project;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TrainingDiaryAppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ByteArrayInputStream in;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testRegisterUser() {
        String input = "TestUser\nTestPassword\ntrue\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        TrainingDiaryApp app = new TrainingDiaryApp();
        app.registerUser(new Scanner(System.in));

        assertTrue(outContent.toString().contains("Пользователь зарегистрирован успешно."));
    }

    @Test
    public void testDeleteTraining() {
        TrainingDiaryApp app = new TrainingDiaryApp();

        // Создаем тренировки для пользователя
        User user = new User("TestUser", "TestPassword", false);
        user.addTraining(new Training("2024-04-10", app.typeManager.getTrainingType("кардио"), 30, 200, "Additional Info", user));

        // Вход пользователя
        app.currentUser = user;

        // Ввод даты тренировки для удаления
        String input = "2024-04-10\n1\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Вызываем метод удаления тренировки
        app.deleteTraining(new Scanner(System.in));

        assertTrue(outContent.toString().contains("Тренировка успешно удалена."));
        assertEquals(0, user.getTrainings().size());
    }
}

