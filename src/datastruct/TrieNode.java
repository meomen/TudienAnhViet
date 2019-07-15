package datastruct;

import java.util.HashMap;

// đối tượng phần tử của Trie
public class TrieNode {
	private String word;
//	private String html;
	private boolean isWord;
	private HashMap<Character,TrieNode> children;
	public TrieNode () {
		this.word = "";	
		this.isWord = false;
		this.children = new HashMap<Character,TrieNode>() ;
//		this.children = null ;

	}
	public TrieNode(String word) {
//		this.html = "";
		this.word = word;
		this.isWord = false;
		this.children = null;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public boolean isWord() {
		return isWord;
	}
	public void setisWord(boolean isWord) {
		this.isWord = isWord;
	}
	public HashMap<Character, TrieNode> getChildren() {
		return children;
	}
	public void setChildren(HashMap<Character, TrieNode> children) {
		this.children = children;
	}
	@Override
	public String toString () {
		return this.word;
	}
	
}
