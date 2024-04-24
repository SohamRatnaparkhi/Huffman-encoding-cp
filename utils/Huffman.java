package cp.utils;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {
    PriorityQueue<Tree> queue = new PriorityQueue<Tree>((a, b) -> a.data.frequency - b.data.frequency);
    public HashMap<Byte, String> codes = new HashMap<Byte, String>();

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
            System.out.println(key + ": " + codes.get(key));
        }
    }

    public byte[] compress(byte[] bytes) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte b : bytes)
            strBuilder.append(codes.get(b));

        int length = (strBuilder.length() + 7) / 8;
        byte[] huffCodeBytes = new byte[length];
        int idx = 0;
        for (int i = 0; i < strBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 > strBuilder.length())
                strByte = strBuilder.substring(i);
            else
                strByte = strBuilder.substring(i, i + 8);
            huffCodeBytes[idx] = (byte) Integer.parseInt(strByte, 2);
            idx++;
        }
        return huffCodeBytes;
    }
}
