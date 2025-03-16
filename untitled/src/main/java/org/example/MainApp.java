package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CesarApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Поле для ввода ключа
        TextField keyField = new TextField();
        keyField.setPromptText("Введите ключ");

        // Поле для ввода текста
        TextArea inputText = new TextArea();
        inputText.setPromptText("Введите текст");

        // Поле для вывода результата
        TextArea outputText = new TextArea();
        outputText.setPromptText("Результат");
        outputText.setEditable(false);

        // Кнопка для шифрования
        Button encryptBtn = new Button("Зашифровать");
        encryptBtn.setOnAction(e -> {
            int key = Integer.parseInt(keyField.getText());
            String text = inputText.getText();
            String encrypted = Main.encrypt(text, key); // Используем метод из Main
            outputText.setText(encrypted);
        });

        // Кнопка для дешифрования
        Button decryptBtn = new Button("Расшифровать");
        decryptBtn.setOnAction(e -> {
            int key = Integer.parseInt(keyField.getText());
            String text = inputText.getText();
            String decrypted = Main.decrypt(text, key); // Используем метод из Main
            outputText.setText(decrypted);
        });

        // Компоновка элементов
        VBox root = new VBox(10, keyField, inputText, outputText, encryptBtn, decryptBtn);
        Scene scene = new Scene(root, 400, 300);

        // Настройка окна
        primaryStage.setTitle("Шифр Цезаря");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void Main(String[] args) {
        launch(args);
    }
}