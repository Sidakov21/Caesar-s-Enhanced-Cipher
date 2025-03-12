//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.*;


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
        for (int key = 0; key <= 255; key++) { // Расширен диапазон ключей
            File possibleFile = new File(outputDirectory, "decrypted_" + key + ".txt");

            StringBuilder decryptedText = new StringBuilder();

            try (BufferedReader fin = new BufferedReader(new FileReader(encryptedFile));
                 BufferedWriter fout = new BufferedWriter(new FileWriter(possibleFile))) {

                int line;
                boolean validText = true;
                int spaceCount = 0;

                while ((line = fin.read()) != -1) {
                    char decryptedChar = (char) (line - key);
                    decryptedText.append(decryptedChar);

                    // Проверяем диапазон символов (ASCII + кириллица)
                    if ((decryptedChar < 32 || decryptedChar > 126) && (decryptedChar < 'А' || decryptedChar > 'я')) {
                        validText = false;
                    }

                    if (decryptedChar == ' ') {
                        spaceCount++;
                    }

                    fout.write(decryptedChar);
                }

                // Проверка на осмысленный текст
                if (validText && spaceCount > 5 && decryptedText.toString().matches(".*[A-Za-zА-Яа-я].*")) {
                    System.out.println("✅ Possible valid key found: " + key + " -> " + possibleFile.getName());
                } else {
                    System.out.println("❌ Key " + key + " does not seem valid.");
                }
            }
        }
    }


    public static void main(String[] args) {
//        File originalFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\TestFile.txt");
//        File encryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\enctyptedFile.txt");

        File encryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\enctyptedFile.txt");
        File outputDirectory = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\BruteForse");

        int key = 3;

        try {
//            CommonEncryption(originalFile, encryptedFile, key);
//            CommonDecryption(encryptedFile, originalFile, key);
            BruteForceDecryption(encryptedFile, outputDirectory);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}