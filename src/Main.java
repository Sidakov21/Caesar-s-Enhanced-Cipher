//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.*;
import java.nio.file.Files;


public class Main {

    public static void CommonEncryption(File originalFile, File encryptedFile, int key) throws IOException {
        try (BufferedReader fin = new BufferedReader(new FileReader(originalFile));
             BufferedWriter fout = new BufferedWriter(new FileWriter(encryptedFile))) {

            int line;
            while ((line = fin.read()) != -1) {
                fout.write((char)line + key);
            }
            System.out.println("Encryption successful!");
        }
    }

    //Расшифровка текста с помощью ключа
    public static void CommonDecryption(File encryptedFile, File originalFile, int key) throws IOException {
        try (BufferedReader fin = new BufferedReader(new FileReader(encryptedFile));
             BufferedWriter fout = new BufferedWriter(new FileWriter(originalFile))) {

            int line;
            while ((line = fin.read()) != -1) {
                fout.write((char)line - key);
            }

            System.out.println("Decryption successful!");
        } // Потоки закроются автоматически благодаря try-with-resources
    }

    //Расшифровка текста с помощью brute force (перебор всех вариантов)
    public static void BruteForceDecryption(File encryptedFile, File outputDirectory) throws IOException {

        // Файл для сохранения всех вариантов
        File resultFile = new File(outputDirectory, "all_decrypted_variants.txt");

        String bestDecryption = ""; // Сохраним лучший текст
        int bestKey = -1;           // Сохраним лучший ключ
        int maxSpaceCount = 0;      // Максимальное количество пробелов в тексте

        try (BufferedReader fin = new BufferedReader(new FileReader(encryptedFile));
             BufferedWriter fout = new BufferedWriter(new FileWriter(resultFile))) {

            // Перебор всех возможных ключей
            for (int key = 0; key <= 255; key++) {
                fout.write("=== Key " + key + " ===\n");  // Добавляем заголовок с номером ключа

                fin.mark(500000); // Ставим метку для возврата к началу файла

                StringBuilder decryptedText = new StringBuilder();
                int spaceCount = 0;

                int line;
                while ((line = fin.read()) != -1) {
                    char decryptedChar = (char) (line - key);
                    decryptedText.append(decryptedChar);
                    fout.write(decryptedChar);

                    // Подсчёт пробелов для оценки осмысленности текста
                    if (decryptedChar == ' ') {
                        spaceCount++;
                    }
                }

                fout.write("\n\n"); // Добавляем отступ между вариантами
                fin.reset();        // Возвращаемся в начало файла для следующей итерации

                // Проверяем, является ли этот текст самым вероятным кандидатом
                if (spaceCount > maxSpaceCount) {
                    maxSpaceCount = spaceCount;
                    bestDecryption = decryptedText.toString();
                    bestKey = key;
                }
            }

            System.out.println("✅ Нужный ключ: Key " + bestKey);
            System.out.println("📝 Текст оригинальный:\n" + bestDecryption);
        }

    }

    public static void main(String[] args) {
          File originalFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\TestFile.txt");
//        File encryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\enctyptedFile.txt");

        File encryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\enctyptedFile.txt");
        File outputDirectory = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\BruteForse");

        int key = 7;

        try {
//            CommonEncryption(originalFile, encryptedFile, key);
//            CommonDecryption(encryptedFile, originalFile, key);
            BruteForceDecryption(encryptedFile, outputDirectory);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}