package cp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import cp.utils.FilePath;
import cp.utils.FileUtils;
import cp.utils.Huffman;
import cp.utils.Tree;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        // Request user for the file path to compress
        System.out.println("Enter the file path to compress and the destination path: ");
        String inputPath = sc.nextLine();
        String destinationPath = sc.nextLine();

        FilePath input = new FilePath(inputPath);
        FilePath destination = new FilePath(destinationPath);

        System.out.println("Input Path: " + input);
        System.out.println("Destination Path: " + destination);

        // Read the file content
        String content = FileUtils.getFileContent(input);
        System.out.println(content);

        byte[] contentInBytes = FileUtils.getFileBytes(input);

        // Get the frequency of each byte in the file
        HashMap<Byte, Integer> frequency = FileUtils.getFileByteFrequency(input);
        System.out.println(frequency);
        FileUtils.writeFileInString(input, frequency.toString());

        Huffman huffman = new Huffman();
        Tree tree = huffman.buildTree(frequency);
        huffman.generateCodes(tree, "");
        
        // huffman.printCodes();

        byte[] compressed = huffman.compress(contentInBytes);
        // System.out.println(new String(compressed));

        // Write the compressed content to the destination file
        FileUtils.writeFile(destination, compressed);
    }
}