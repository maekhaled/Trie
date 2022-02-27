# Trie
Rutgers CS112: Assignment 2

This project implements a Trie structure. This application builds a tree structure called Trie for a dictionary of English words, and uses the Trie to generate completion lists for string searches. 

## Description

This application:
  1. Builds a trie for a given list of words that are stored in an array. The word list is input to the trie building algorithm.
  2. Given a search prefix, scans the trie efficiently, gathers and returns a list of words that begin with this search prefix (arraylist of TrieNodes that hold matching words).
  
### Background

  A Trie is a general tree, in that each node can have any number of children. It is used to store a dictionary (list) of words that can be searched on, in a manner that allows for effcient generation of completion lists.
  The word list is originally stored in an array, and the trie is built off of this array. Here is an example of a word list, the corresponding trie, followed by an explanation of the structure and its correspondence to the word list.

  ![SampleWordListsAndTries](https://user-images.githubusercontent.com/71520010/155866847-e2bd8b28-5e1a-48dd-948f-2303fc6bf91f.png)

  ### Data Structure
  
  Since the nodes in a trie have varying numbers of children, the structure is built using linked lists in which each node has three fields:
  - substring (which is a triplet of indexes)
  - first child, and
  - sibling, which is a pointer to the next sibling.

### Executing Program

The program may be executed using the supplied TrieApp driver. It first asks for the name of an input file of words, with which it builds a trie by calling the Trie.buildTree
method. After the trie is built, it asks for search prefixes for which it computes completion lists, calling the Trie.completionList method.

Some example text files are provided: words0.txt, words1.txt, words2.txt, words3.txt, words4.txt 

The first line of a word file is the number of the words, and the subsequent lines are the words, one per line.

### Sample Run

Here is a sample run of TrieApp.java:

![SampleTrieRun](https://user-images.githubusercontent.com/71520010/155867259-d7f42b61-c8df-42f0-a685-8861c8fb9350.PNG)


All use of this code must comply with the [Rutgers University Code of Student Conduct](http://eden.rutgers.edu/%7Epmj34/media/AcademicIntegrity.pdf).
