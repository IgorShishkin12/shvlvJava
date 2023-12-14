
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Использование: java TextFilter <файл_с_текстом> <файл_с_словами> <файл_результата>");
            System.exit(1);
        }

        String inputFile = args[0];
        String filterFile = args[1];
        String outputFile = args[2];

        try {
            Set<String> filterWords = loadFilterWords(filterFile);
            filterText(inputFile, outputFile, filterWords);
            System.out.println("Фильтрация завершена. Результат сохранен в " + outputFile);
        } catch (IOException e) {
            System.err.println("Ошибка при обработке файлов: " + e.getMessage());
        }
    }

    private static Set<String> loadFilterWords(String filterFile) throws IOException {
        Set<String> filterWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filterFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                filterWords.add(line.trim());
            }
        }
        return filterWords;
    }

    private static void filterText(String inputFile, String outputFile, Set<String> filterWords) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!containsFilterWord(line, filterWords)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }

    private static boolean containsFilterWord(String line, Set<String> filterWords) {
        for (String word : filterWords) {
            if (line.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
