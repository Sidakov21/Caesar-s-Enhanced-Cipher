package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Шифр Цезаря");

        // Поля ввода
        TextField inputField = new TextField();
        inputField.setPromptText("Введите путь к файлу");

        TextField keyField = new TextField();
        keyField.setPromptText("Введите ключ (число)");

        // Кнопки действий
        Button encryptButton = new Button("Зашифровать");
        Button decryptButton = new Button("Расшифровать");
        Button bruteForceButton = new Button("Brute Force");
        Button statisticalButton = new Button("Статистический анализ");

        // Поле вывода результата
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        // Обработчики событий
        //Шифровка текста
        encryptButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(keyField.getText());
                File originalFile = new File(inputField.getText());
                File encryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\src\\File examples\\encryptedFile.txt");
                Main.CommonEncryption(originalFile, encryptedFile, key);
                resultArea.setText("Файл успешно зашифрован!");
            } catch (NumberFormatException | IOException ex) {
                resultArea.setText("Ошибка: " + ex.getMessage());
            }
        });

        //Расшифровка текста с помощью ключа
        decryptButton.setOnAction(e -> {
            try {
                int key = Integer.parseInt(keyField.getText());
                File encryptedFile = new File(inputField.getText());
                File decryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\File examples\\decryptedFile.txt");
                Main.CommonDecryption(encryptedFile, decryptedFile, key);
                resultArea.setText("Файл успешно расшифрован!");
            } catch (NumberFormatException | IOException ex) {
                resultArea.setText("Ошибка: " + ex.getMessage());
            }
        });

        //Расшифровка текста с помощью brute force (перебор всех вариантов)
        bruteForceButton.setOnAction(e -> {
            try {
                File encryptedFile = new File(inputField.getText());
                File outputDirectory = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\File examples\\BruteForse");
                Main.BruteForceDecryption(encryptedFile, outputDirectory);
                resultArea.setText("Brute Force завершен. Проверьте файл результатов.");
            } catch (IOException ex) {
                resultArea.setText("Ошибка: " + ex.getMessage());
            }
        });

        // Статистическая расшифровка
        statisticalButton.setOnAction(e -> {
            try {
                File encryptedFile = new File(inputField.getText());
                File statisticalFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\File examples\\StatisticalDecrypt\\statistical_decrypt.txt");
                Main.StatisticalDecryption(encryptedFile, statisticalFile);
                resultArea.setText("Статистический анализ завершен. Проверьте файл результатов.");
            } catch (IOException ex) {
                resultArea.setText("Ошибка: " + ex.getMessage());
            }
        });

        // Компоновка элементов
        VBox layout = new VBox(10, inputField, keyField, encryptButton, decryptButton,
                bruteForceButton, statisticalButton, resultArea);
        layout.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(layout, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}