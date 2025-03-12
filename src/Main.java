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


    public static void main(String[] args) {
        File originalFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\TestFile.txt");
        File encryptedFile = new File("C:\\Users\\Амир\\IdeaProjects\\CesarCipher\\src\\enctyptedFile.txt");
        int key = 3;

        try {
            CommonEncryption(originalFile, encryptedFile, key);
            CommonDecryption(encryptedFile, originalFile, key);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}