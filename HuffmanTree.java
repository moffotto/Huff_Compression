/*  Samuel Otto
    12/08/2017
    CSE 143, AH
    TA: Gavin Cai
    Assignment #8

    This class allows a client to compress an input file by encoding it into a 
    Huffman format. It also allows a client to decode the Huffman encoded file
    back into the original text file. 
 */

import java.util.*;
import java.io.*;

public class HuffmanTree {

    private HuffmanNode overallRoot; //Contains reference to the Huffman Tree

    //Pre: Takes an integer array as paramter. 
    //Post: Constructs initial Huffman Tree based on the given count of each 
    //character. 
    public HuffmanTree(int[] count) {
        Queue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>();

        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                queue.add(new HuffmanNode(count[i], i));
            }
        }

        queue.add(new HuffmanNode(1, count.length));

        while (queue.size() > 1) {
            HuffmanNode left = queue.remove();
            HuffmanNode right = queue.remove();
            queue.add(new HuffmanNode(left.frequency + right.frequency,
                    left, right));
        }

        overallRoot = queue.remove();

    }

    //Pre: Takes in a a client's output file location as a parameter
    //Post: Writes the tree to the given output stream in standard format
    public void write(PrintStream output) {
        write(overallRoot, output, "");
        
    }

    //Pre: Takes in the current Huffman node, an output file location, and a 
    //string binary code for the Huffman node as parameters
    //Post: Prints the integer values for the characters in the binary tree to 
    //a line & the correlating bit code to another
    private void write(HuffmanNode root, PrintStream output, String code) {
        if (root != null) {
            if (root.right == null && root.left == null) {
                output.println(root.data);
                output.println(code);
            } else {
                write(root.left, output, code + 0);
                write(root.right, output, code + 1);
            }
        }

    }

    //Pre: Scanner input as parameter, must be in the standard Huffman format
    //Post: Constructs a new tree from an input file.
    public HuffmanTree(Scanner input) {
        while (input.hasNextLine()) {
            int data = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            overallRoot = createTree(overallRoot, code, data);
        }

    }

    //Pre: Takes in the current Huffman node, a string binary code for the 
    //Huffman node, and an integer for a character as parameters.
    //Post: Returns a tree using the desired character to determine where to 
    //add nodes.
    private HuffmanNode createTree(HuffmanNode root, String code, int data) {
        if (code.isEmpty()) {
            return new HuffmanNode(-1, data);
        } else {
            if (root == null) {
                root = new HuffmanNode(-1);
            }
            if (code.charAt(0) != '0') {
                root.right = createTree(root.right, code.substring(1), data);
            } else {
                root.left = createTree(root.left, code.substring(1), data);
            }

            return root;
        }

    }

    //Pre: Takes in an encoded/compressed file, an ouput file, and an integer
    //for the end-of-file character.
    //Post: Creates a text file using the user provided compressed file
    //containing the characters. Prints out each decoded character into the
    //given file.
    public void decode(BitInputStream input, PrintStream output, int eof) {
        int current = decode(overallRoot, input);

        while (current != eof) {
            output.write(current);
            current = decode(overallRoot, input);
        }

    }

    //Pre: Takes in am encoded compression file from the user, along with the 
    //current Huffman node.
    //Post: Traverses the given tree until it reaches a character, then returns 
    //the integer value of the character.
    private int decode(HuffmanNode root, BitInputStream input) {
        if (root.right != null && root.left != null) {
            if (input.readBit() != 0) {
                return decode(root.right, input);
            } else {
                return decode(root.left, input);
            }
        }

        return root.data;

    }

}
