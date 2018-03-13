package edu.itu.csc.quakenweather.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An trie data structure that implements Recommend interface.
 *
 * @author Jigar Gosalia
 *
 */
public class RecommendTrie {

	private TrieNode root;

    private int size;

	public RecommendTrie() {
        root = new TrieNode();
	}

	/**
	 * Insert a city name by creating and linking necessary trie nodes into
	 * the trie.
	 *
	 * @return
	 */
	public boolean addWord(String cityName) {
		String cityNameL = cityName.toLowerCase();
		TrieNode node = root;
		for (char character : cityNameL.toCharArray()) {
			if (node.getValidNextCharacters().contains(character)) {
				node = node.getChild(character);
			} else {
				node = node.insert(character);
			}
		}
		if (!node.endsWord()) {
			node.setEndsWord(true);
			size++;
			return true;
		}
		return false;
	}

	/**
	 * Return maximum count of recommended city names list.
	 *
	 * If prefix is not in the trie, it returns an empty list.
	 * 
	 * @param prefix
	 * @param count
	 * @return
	 */
	public List<String> recommend(String prefix, int count) {
		List<String> recommendations = new LinkedList<String>();
		TrieNode node = root;
		for (char c : prefix.toCharArray()) {
			if (node.getValidNextCharacters().contains(c)) {
				node = node.getChild(c);
			} else {
				return recommendations;
			}
		}
		if (node.endsWord()) {
			recommendations.add(node.getText());
		}

		Queue<TrieNode> nodeQueue = new LinkedList<TrieNode>();
		List<Character> children = new LinkedList<Character>(node.getValidNextCharacters());

		for (int i = 0; i < children.size(); i++) {
			char c = children.get(i);
			nodeQueue.add(node.getChild(c));
		}
		while (!nodeQueue.isEmpty() && recommendations.size() < count) {
			TrieNode firstNode = nodeQueue.poll();
			if (firstNode.endsWord()) {
				recommendations.add(firstNode.getText());
			}

			List<Character> childNodes = new LinkedList<Character>(firstNode.getValidNextCharacters());
			for (int i = 0; i < childNodes.size(); i++) {
				char c = childNodes.get(i);
				nodeQueue.add(firstNode.getChild(c));
			}
		}
		return recommendations;
	}

}