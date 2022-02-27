package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		
		TrieNode root = new TrieNode(null,null,null);
		
		if (allWords==null) {return root;}
		
		for(int i=0; i < allWords.length; i++) {
		
			Indexes newIndex = new Indexes(i,(short) 0, (short)(allWords[i].length()-1));
			TrieNode newNode = new TrieNode(newIndex, null, null); 
			
			if(root.firstChild == null) {
				root.firstChild=newNode;
				continue;
			}
			
			TrieNode prev = root;
			TrieNode ptr = root.firstChild;
			
			while(ptr != null) {
				
				int common=prefixEnd(newNode,ptr,allWords);
				
				if(common>newNode.substr.startIndex-1) {
					
					//internal node
					if(ptr.firstChild != null) {
						
						if((short)common>=ptr.substr.endIndex) {
						newNode.substr.startIndex = (short)(ptr.substr.endIndex+1);
						prev=ptr;
						ptr=ptr.firstChild;
						continue;
						}
						else {
							Indexes sp = new Indexes(ptr.substr.wordIndex,ptr.substr.startIndex,(short)common);
							TrieNode smallerpref= new TrieNode(sp,ptr,ptr.sibling);
							if(prev.firstChild == ptr) {
								prev.firstChild=smallerpref;
							}
							else {
								prev.sibling=smallerpref;
							}
							ptr.substr.startIndex=(short)(smallerpref.substr.endIndex + 1);
							newNode.substr.startIndex=(short)(smallerpref.substr.endIndex+1);
							ptr.sibling=newNode;
							break;
							}
						}
					//leaf node
					else {
						
						newNode.substr.startIndex = (short)(common+1);
						ptr.substr.endIndex = (short)common;
						Indexes ac = new Indexes(ptr.substr.wordIndex,(short)(ptr.substr.endIndex+1),(short)(allWords[ptr.substr.wordIndex].length()-1));
						TrieNode addedChild = new TrieNode(ac,null,newNode);
						ptr.firstChild = addedChild;
						break;
					}
				}
				else{
					prev=ptr;
					ptr=ptr.sibling;
					}
				}
				if(ptr == null) {
				prev.sibling=newNode;
				}
			}
		return root;
		}

	
	
	//get the end index of prefix
	private static int prefixEnd(TrieNode newNode, TrieNode current, String[] allWords) {
		int count = -1;
		String newWord = allWords[newNode.substr.wordIndex];
		String word = allWords[current.substr.wordIndex];
		
		if(newWord.length()>word.length()) {
			for(int i=0; i<word.length();i++) {
				if(newWord.charAt(i) == word.charAt(i)) {
					count++;
					continue;
				}
				else {
					break;
				}
			}
		}
		else{
			for(int i=0; i<newWord.length();i++) {
				if(newWord.charAt(i) == word.charAt(i)) {
					count++;
					continue;
				}
				else {
					break;
				}
			}
		}
		
		return count;		
	}
	
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		
		ArrayList<TrieNode> matches = new ArrayList<TrieNode>();
		
		if(root.firstChild == null || prefix.length()==0 ) {
			return matches;
		}
		
		TrieNode ptr = root.firstChild;
		
		while(ptr != null) {
			
			int subStart = ptr.substr.startIndex;
			int subEnd = ptr.substr.endIndex;
			
			if (prefix.charAt(subStart) == allWords[ptr.substr.wordIndex].charAt(subStart)) {
				
				int index = ptr.substr.startIndex +1;
				boolean check = true;
				
				while(index <= subEnd && index<prefix.length()) {
					if(prefix.charAt(index) == allWords[ptr.substr.wordIndex].charAt(index)) {
						index++;
					}
					else {
						check = false;
						break;
					}
				}
				
				//no words with common prefix
				if (check == false) {
					return null;
				}
				
				//ptr holds entire prefix 
				if(index == prefix.length()) {
					
					//if ptr is leaf node then its the only common word
					if (ptr.firstChild == null) {
						matches.add(ptr);
						return matches;
					}
					//if ptr is internal node get all leaves of its subtree
					else {
					matches = getLeaves(ptr.firstChild, matches);
					return matches;
					}
				}
				//ptr holds part of prefix
				else {
					ptr = ptr.firstChild;
					continue;
				}	
			}
			else {
				ptr = ptr.sibling;
			}	
		}
		return null;
	}
	
	private static ArrayList<TrieNode> getLeaves(TrieNode node, ArrayList<TrieNode> matches){
		
		TrieNode ptr = node;
		
		while(ptr != null) {
			System.out.print(ptr.substr);
			if(ptr.firstChild == null) {
				matches.add(ptr);
			}
			else {
				matches = getLeaves(ptr.firstChild,matches);
			}
			ptr = ptr.sibling;		
		}

		return matches;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
