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
        // System.out.println("Enter the file path to compress and the destination path: ");
        System.out.print("Path to file which need to be compressed: ");
        String inputPath = sc.nextLine();
        System.out.print("Path to store the decompressed data: ");
        String destinationPath = sc.nextLine();

        FilePath input = new FilePath(inputPath);
        // FilePath destination = new FilePath(destinationPath);

        System.out.println("Input Path: " + input);
        System.out.println("Destination Path: " + destinationPath);

        // Read the file content
        String content = FileUtils.getFileContent(input);
        System.out.println(content);

        byte[] contentInBytes = FileUtils.getFileBytes(input);

        // Get the frequency of each byte in the file
        HashMap<Byte, Integer> frequency = FileUtils.getFileByteFrequency(input);
        // for (Byte key : frequency.keySet()) {
        //     System.out.println((char)key.byteValue() + ": " + frequency.get(key));
        // }
        // System.out.println(frequency);
        
        Huffman huffman = new Huffman();
        Tree tree = huffman.buildTree(frequency);
        huffman.generateCodes(tree, "");
        
        // huffman.printCodes();
        // Huffman.printTree(tree, 0);
        // BTreePrinter.printNode(tree);
        
        byte[] compressed = huffman.compress(contentInBytes);
        // System.out.println(new String(compressed));
        
        // Write the compressed content to the destination file
        
        // create folder named {Input_file_name}_zip
        String destinationFolder = destinationPath + "_zip";
        FileUtils.createFolder(destinationFolder);
        
        String compressedFilePath = destinationFolder + "/" + input.fileName + "_compressed." + "huff";
        FileUtils.writeFile(new FilePath(compressedFilePath), compressed);
        
        // Read the compressed file
        byte[] compressedContent = FileUtils.getFileBytes(new FilePath(compressedFilePath));
        
        FileUtils.writeFileInString(new FilePath(
                destinationFolder + "/" + input.fileName + "_compressed_codes." + "txt"), frequency.toString());
        
        // Decompress the compressed file
        // byte[] decompressed = huffman.decompress(frequency, compressedContent);
        byte[] decompressed = huffman.decompress(compressedContent);

        String newPathName = destinationFolder + '/' + input.fileName + "_decomp." + input.extension;
        // Write the decompressed content to a new file
        FileUtils.writeFile(new FilePath(newPathName), decompressed);
    }
}
