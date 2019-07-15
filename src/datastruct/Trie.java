package datastruct;

import java.util.ArrayList;
import java.util.HashMap;

// Sử dụng cấu trúc dữ liệu Trie để nạp Database

public class Trie {
	
	private TrieNode root  = new TrieNode(); // phần tử gốc
	

	public TrieNode getRoot() {
		return root;
	}

	public void setRoot(TrieNode root) {
		this.root = root;
	}

	public Trie () {};
	
	// nạp 1 từ vào cấu trúc 
	public void insert (String word) {
		TrieNode pointer = root;
		String tu ="";
		for (int i = 0 ; i < word.length(); i++) {
			char chu = word.charAt(i);
			tu = tu + chu +"";
			HashMap<Character, TrieNode> tem = pointer.getChildren();
			if (tem==null) {
				tem = new HashMap<Character,TrieNode>();
				tem.put(chu, new TrieNode(tu));
				pointer.setChildren(tem);
			}
			else if (!(tem.containsKey(chu))) {
				tem.put(chu, new TrieNode(tu));
				pointer.setChildren(tem);
			}
			pointer = pointer.getChildren().get(chu);
		}
		pointer.setisWord(true);
	}
	
//	 hàm tìm kiếm của Trie	
	public ArrayList<String> find (String word) {
		if(word==null) word="";
		TrieNode pointer = root;
		ArrayList<String> string = new ArrayList<String>();
		for (int i = 0 ; i < word.length(); i++) {
			char chu = word.charAt(i);
			if (pointer.getChildren() == null) {
				return string;
			}
			TrieNode node = pointer.getChildren().get(chu);
			if (node==null) {
				return string;
			}
			pointer = node;
		}
		if (pointer.getChildren()==null) {
			string.add(pointer.getWord());
			return string;
		}
		
		// Tạo hàm đợi , ứng dụng thuật toán tìm kiếm chiều rộng BFS
		int index =1;
		ArrayList<TrieNode> Queue = new ArrayList<TrieNode>();
		Queue.add(pointer);
		
		HashMap<Character, TrieNode> mapchildren = pointer.getChildren();
		if (mapchildren!=null) {
			Queue.addAll(mapchildren.values());
		}
		while (index < Queue.size()) {
			pointer = Queue.get(index++);
			mapchildren = pointer.getChildren();
			if (mapchildren!=null) {
				Queue.addAll(mapchildren.values());
			}
		}
		// chạy hàm đợi, node nào có đánh dấu isWord thì nạp vào arraylist
		for (int i = 0 ; i < Queue.size();i++) {
			pointer = Queue.get(i);
			if (pointer.isWord()) {
				string.add(pointer.getWord());
			}
		}
		return string;
	}
	
	// hàm xóa note của 1 từ 
	public void delete (String word) { 
		delete(root,word,0);
	}
	private boolean delete (TrieNode pointer, String word, int index) {
		if (index ==word.length()) {
			if (!pointer.isWord()) {
				return false;
			}
			pointer.setisWord(false);
			if (pointer.getChildren()==null) return true;
			return pointer.getChildren().isEmpty();
		}
		char chu = word.charAt(index);
		TrieNode node  = pointer.getChildren().get(chu);
		if (node == null) {
			return false;
		}
		boolean checkNode = delete(node, word, index + 1) && !node.isWord();
		if (checkNode) {
			pointer.getChildren().remove(chu);
			return pointer.getChildren().isEmpty(); 
		}
		return false;
	}
	
	// hàm cập nhập từ vào Trie

	public void update(String word_old, String word_new) {
		delete(word_old);
		insert(word_new);
	}
	
}
