package cp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {
    PriorityQueue<Tree> queue = new PriorityQueue<Tree>((a, b) -> a.data.frequency - b.data.frequency);
    public HashMap<Byte, String> codes = new HashMap<Byte, String>();
    public Tree huffmanTree = null;
    int extraBits = 0;


    public Tree buildTree(HashMap<Byte, Integer> frequency) {
        for (Byte key : frequency.keySet()) {
            queue.add(new Tree(new Data(key, frequency.get(key))));
        }

        while (queue.size() > 1) {
            Tree left = queue.poll();
            Tree right = queue.poll();
            Tree parent = new Tree(new Data(null, left.data.frequency + right.data.frequency));
            parent.left = left;
            parent.right = right;
            queue.add(parent);
        }
        huffmanTree = queue.peek();
        return queue.poll();
    }

    public void generateCodes(Tree root, String code) {
        if (root.data.data != null) {
            codes.put(root.data.data, code);
            return;
        }

        generateCodes(root.left, code + "0");
        generateCodes(root.right, code + "1");
    }

    public void printCodes() {
        for (Byte key : codes.keySet()) {
            System.out.println((char)key.byteValue() + ": " + codes.get(key));
        }
    }

    public byte[] compress(byte[] bytes) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(codes.get(b));
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BitOutputStream bitOutput = new BitOutputStream(output);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '0') {
                bitOutput.writeBit(0);
            } else {
                bitOutput.writeBit(1);
            }
        }
        bitOutput.flush();
        return output.toByteArray();
    }


    public byte[] decompress (HashMap<Byte, Integer> frequency, byte[] compressed) {
        // Tree root = buildTree(frequency);
        Tree root = huffmanTree;
        StringBuilder strBuilder = new StringBuilder();
        Tree current = root;

        // Total number of bits to process
        int totalBits = compressed.length * 8 - extraBits;

        int bitCount = 0; // Counter to keep track of the number of bits processed
        for (byte b : compressed) {
            for (int i = 7; i >= 0; i--) {
                if (bitCount == totalBits) {
                    break; // Stop if we have processed all valid bits
                }

                int bit = (b >> i) & 1;
                if (bit == 0)
                    current = current.left;
                else
                    current = current.right;

                if (current.data != null && current.data.data != null) {
                    strBuilder.append((char) (current.data.data.intValue()));
                    current = root; // Reset to start of tree for next character
                }
                bitCount++;
            }
        }

        System.out.println(strBuilder.toString());
        return strBuilder.toString().getBytes();
    }

    public static void printTree(Tree node, int level) {
        if (node == null) {
            return;
        }

        printTree(node.right, level + 1);
        for (int i = 0; i < level; i++) {
            System.out.print(" ");
        }
        System.out.println(node);
        printTree(node.left, level + 1);
    }
}
