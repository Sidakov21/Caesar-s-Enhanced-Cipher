package org.example;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;


public class Main {

    //Шифровка текста. Работа с русскими словами.
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
            for (int key = 0; key <= 33; key++) {
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



    // Статистическая расшифровка
    public static void StatisticalDecryption(File encryptedFile, File decryptedFile) throws IOException {
        String encryptedText = new String(Files.readAllBytes(encryptedFile.toPath()));
        Map<Character, Double> expectedFreq = getExpectedFrequencies(); // Эталонные частоты

        int bestKey = 0;
        double minScore = Double.MAX_VALUE;

        // Перебор всех возможных ключей (0-33)
        for (int key = 0; key <= 33; key++) {
            String decrypted = decrypt(encryptedText, key); // Расшифровка текста
            Map<Character, Double> observedFreq = calculateFrequencies(decrypted); // Частоты в тексте

            double score = calculateDeviation(expectedFreq, observedFreq); // Отклонение
            if (score < minScore) {
                minScore = score;
                bestKey = key;
            }
        }

        // Сохранение результата
        String result = decrypt(encryptedText, bestKey);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(decryptedFile))) {
            writer.write(result);
        }
        System.out.println("Best key: " + bestKey);
    }

    private static Map<Character, Double> getExpectedFrequencies() {
        Map<Character, Double> freq = new HashMap<>();
        // Частоты для русского языка (примерные значения)
        freq.put('о', 0.1097);
        freq.put('е', 0.0845);
        freq.put('а', 0.0801);
        freq.put('и', 0.0735);
        freq.put('н', 0.0670);
        freq.put('т', 0.0626);

        return freq;
    }

    private static String decrypt(String text, int key) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append((char) (c - key));
        }
        return sb.toString();
    }

    // Подсчёт количества каждого символа
    private static Map<Character, Double> calculateFrequencies(String text) {
        Map<Character, Integer> counts = new HashMap<>();
        int totalLetters = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char lowerC = Character.toLowerCase(c);
                counts.put(lowerC, counts.getOrDefault(lowerC, 0) + 1);
                totalLetters++;
            }
        }

        // Вычисление частот
        Map<Character, Double> frequencies = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            frequencies.put(entry.getKey(), entry.getValue() / (double) totalLetters);
        }
        return frequencies;
    }

    private static double calculateDeviation(Map<Character, Double> expected, Map<Character, Double> observed) {
        double score = 0;
        for (Character c : expected.keySet()) {
            double exp = expected.getOrDefault(c, 0.0);
            double obs = observed.getOrDefault(c, 0.0);
            score += Math.pow(exp - obs, 2); // Сумма квадратов отклонений
        }
        return score;
    }



//    public static void main(String[] args) {
////          File originalFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\File examples\\TestFile.txt");
////          File encryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\File examples\\encryptedFile.txt");
//
////        File encryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\File examples\\encryptedFile.txt");
////        File outputDirectory = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\File examples\\BruteForse");
//
////        File encryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\File examples\\encryptedFile.txt");
////        File statisticalFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\untitled\\File examples\\StatisticalDecrypt\\statistical_decrypt.txt");
//
////        int key = 3;
//
////        try {
//////            CommonEncryption(originalFile, encryptedFile, key);
//////            CommonDecryption(encryptedFile, originalFile, key);
//////            BruteForceDecryption(encryptedFile, outputDirectory);
//////            StatisticalDecryption(encryptedFile, statisticalFile);
////        } catch (IOException e) {
////            System.err.println("Ошибка при чтении файла: " + e.getMessage());
////        }
//    }
}