/*  Samuel Otto
    12/08/2017
    CSE 143, AH
    TA: Gavin Cai
    Assignment #8

    This creates a node class for constructing a Huffman Tree. Allows clients to
    compare nodes based on their frequency.
 */

public class HuffmanNode implements Comparable<HuffmanNode> {

    public int frequency; //The frequency of occurances
    public int data; //Int value for allocated letter
    public HuffmanNode left; //The left branch
    public HuffmanNode right; //The right branch

    //Pre: Takes an integer of frequency, a left node, and a right node as 
    //parameters.
    //Post: Constructs a Huffman Node with the given frequency and nodes.
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;

    }

    //Pre: Takes in an integer of frequency and an integer for data.
    //Post: Constructs a node with the given frequency and letter value.
    public HuffmanNode(int frequency, int data) {
        this.frequency = frequency;
        this.data = data;

    }

    //Pre: Takes in an integer of frequency as a parameter.
    //Post: Constructs a node with the given frequency.
    public HuffmanNode(int frequency) {
        this(frequency, null, null);
        
    }

    //Pre: Takes in another Huffman node as a parameter.
    //Post: Compares the frequency of two Huffman nodes: the current one and 
    //another one. A positive returned value means this frequency is larger than 
    //the other. A negative returned value means this is smaller. Zero means
    //that the nodes are equal.
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;

    }

}
