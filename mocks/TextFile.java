package cp.mocks;

import java.io.IOException;
import java.util.Scanner;

import cp.utils.FilePath;
import cp.utils.FileUtils;

public class TextFile {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of characters: ");
        double n = scanner.nextDouble();
        String options = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int index = (int) (Math.random() * options.length());
            content.append(options.charAt(index));
        }
        String randomName = "cp/mocks/random.txt";
        FileUtils.writeFileInString(new FilePath(randomName), content.toString());
    }
}
