import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Использование: java CaesarCipher <входной_файл> <ключ_шифра> <выходной_файл>");
            System.exit(1);
        }

        String inputFile = args[0];
        int shiftKey = Integer.parseInt(args[1]);
        String outputFile = args[2];

        try {
            String inputText = readFile(inputFile);
            String encryptedText = encrypt(inputText, shiftKey);
            writeFile(outputFile, encryptedText);
            System.out.println("Шифрование завершено успешно.");
        } catch (IOException e) {
            System.err.println("Ошибка при обработке файлов: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String readFile(String fileName) throws IOException {
        try (FileReader fileReader = new FileReader(fileName)) {
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = fileReader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            return stringBuilder.toString();
        }
    }

    private static void writeFile(String fileName, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(content);
        }
    }

    private static String encrypt(String text, int shiftKey) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                result.append((char) ((character - base + shiftKey) % 26 + base));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
}
