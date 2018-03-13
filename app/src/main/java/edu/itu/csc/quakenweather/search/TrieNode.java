package edu.itu.csc.quakenweather.search;

import java.util.HashMap;
import java.util.Set;

/**
 * TrieNode is the data structure that will maintain the city names in a trie.
 *
 * @author "Jigar Gosalia"
 */
public class TrieNode {

	public HashMap<Character, TrieNode> children;

	private String text;

	public boolean isCity;

    public TrieNode() { this(""); }

	public TrieNode(String text) {
        this.children = new HashMap<Character, TrieNode>();
		this.text = text;
		this.isCity = false;
	}

	/**
	 * Return the TrieNode that is the child when you follow the link from the given
	 * Character
	 * 
	 * @param c
	 *            The next character in the key
	 * @return The TrieNode that character links to, or null if that link is not in
	 *         the trie.
	 */
	public TrieNode getChild(Character c) {
		return this.children.get(c);
	}

	/**
	 * Inserts this character at this node. Returns the newly created node, if c
	 * wasn't already in the trie.
	 *
	 * If it was, it does not modify the trie and returns null.
	 * 
	 * @param c
	 *            The character that will link to the new node
	 * @return The newly created TrieNode, or null if the node is already in the
	 *         trie.
	 */
	public TrieNode insert(Character c) {
		if (this.children.containsKey(c)) {
			return null;
		}
		TrieNode next = new TrieNode(this.text + c.toString());
		this.children.put(c, next);
		return next;
	}

	/**
	 * Return the text string at this node
	 * @return
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Set whether or not this node ends a city in the trie.
	 * @param isCity
	 */
	public void setEndsWord(boolean isCity) { this.isCity = isCity;	}

	/**
	 * Return whether or not this node ends a city in the trie.
	 * @return
	 */
	public boolean endsWord() {
		return this.isCity;
	}

	/**
	 * Return the set of characters (city names) that have links from this node.
	 * @return
	 */
	public Set<Character> getValidNextCharacters() {
		return this.children.keySet();
	}

}