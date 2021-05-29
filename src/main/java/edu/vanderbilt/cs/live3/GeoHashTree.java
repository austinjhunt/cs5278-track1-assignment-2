/* 
 * GeoHashTree
 * CS 5278 Track 1 Assignment 2
 * May 19, 2021
 * Austin Hunt
 */

package edu.vanderbilt.cs.live3;
import java.util.List;
import java.util.Arrays;

public class GeoHashTree { 

	/* 
	 * This is a class to represent a binary tree data structure 
	 * to be used by the GeoDB class for efficient searching of locations
	 * 
	 */
	public GeoHashTree() {
		
	}
	// every tree (non-empty) has a root 
	TreeNode root; 

	
	private String toStringHelper(TreeNode current) {
		String output = ""; 
		if (current != null) {
			output += toStringHelper(current.getLeftChild());
			output += toStringHelper(current.getRightChild());
			output += " (" + current.getValue() + "), ";
		}
		return output;
	}
	public String toString() {
		// print tree out starting at root
		return toStringHelper(root);
	}
	
	/**
    *
    * Recursive helper function to insert a node into a binary tree 
    * @param current
    * @param newNode 
    * 
    */
	private void insertNodeHelper(TreeNode current, TreeNode newNode) {
		if (newNode.getValue() < current.getValue()) {
			// belongs in left subtree
			if (current.getLeftChild() != null) {
				// current has a left child, traverse it 
				insertNodeHelper(current.getLeftChild(), newNode);
			} else {
				// set current's left child to newNode!
				current.setLeftChild(newNode);
			}
		} else { 
			if (current.getRightChild() != null) {
				// current has a right child, traverse it
				insertNodeHelper(current.getRightChild(), newNode);
			} else {
				// current has no right child, set to new node 
				current.setRightChild(newNode);
			}
		}
	} 
	
	/**
    *
    * Inserts a new node into the GeoHashTree
    * @param val 
    * 
    */
	public void insertNode(char val) {
		// method to insert a new node in the tree 
		
		// create a new node with the value passed as argument 
		TreeNode newNode = new TreeNode(val); 
		
		// if a root has not been added yet add it
		if (root == null) {
			root = new TreeNode(null);
		} else { 
			// need to traverse the tree to determine where to insert, 
			// starting at root (current)
			TreeNode current = root; 
			insertNodeHelper(current, newNode);
		}
	}
	
	
	/*
	 * Helper for node deletion; get the minimum value in the right subtree 
	 * of a given node 
	 * @param TreeNode current 
	 * @param boolean firstCall
	 * @return char min
	 */
	private char getMinVal(TreeNode current) {
		char min = current.getValue();
		while (current.getLeftChild() != null) {
			min = current.getLeftChild().getValue();
			current = current.getLeftChild();
		}
		return min;
	}
	/**
    *
    * Helper for node deletion from the GeoHashTree
    * @param char val 
    * @return TreeNode current
    * 
    */
	private TreeNode deleteNodeHelper(TreeNode current, char val) {
		TreeNode deletingThis;
		if (current == null) {
			// base case
			return null;
		}
		if (Character.compare(val, current.getValue()) < 0) {// (val < current.getValue()) { 
			current.setLeftChild(deleteNodeHelper(current.getLeftChild(), val));
		} else if (Character.compare(val, current.getValue()) > 0){ //(val > current.getValue()) {
			current.setRightChild(deleteNodeHelper(current.getRightChild(), val));
		} else {
			// value equals current node value 
			if (current.isLeaf()) {
				// simply delete leaf nodes (tell parent to kill this link)
				return null; 
			} else if (current.getLeftChild() == null){
				// has only RIGHT child (tell parent to replace current with current's right)
				return current.getRightChild();
			} else if (current.getRightChild() == null) {
				// has only LEFT child (tell parent to replace current with current's left)
				return current.getLeftChild(); 
			} else {
				// has both children ; set value to minimum in right subtree
				char rightSubtreeMinVal = getMinVal(current.getRightChild());
				current.setValue(rightSubtreeMinVal);
				// now delete that duplicate min value node from right subtree 
				current.setRightChild(deleteNodeHelper(current.getRightChild(), rightSubtreeMinVal));
				// has at least one child; return left if present, otherwise right 
				// return current.getLeftChild() == null ? current.getRightChild() : current.getLeftChild();
			}
		}
		// return the current node 
		return current;
	}
	
	/**
    *
    * Deletes node from the GeoHashTree
    * @param char val 
    * 
    */
	public void deleteNode(char val) {
		TreeNode deleted = deleteNodeHelper(root, val);
	}
	
	

	/**
    *
    * Recursive helper for findNeighbors
    * 
    * @param geoHash
    * @param bitsOfPrecision
    * @param current 
    * 
    */
	private List<double[]> findNeighborsHelper(String geoHash, int bitsOfPrecision, TreeNode current) {
		 if (current == null) {
			 return null;
		 } 
		 // check if first n characters match 
		 return null;
		 
	}
	
	/**
    *
    * Finds all neighbors of a given hash (all nodes 
    * whose geoHash value match provided geoHash up to 
    * bitsOfPrecision characters starting from left) 
    * 
    * @param geoHash
    * @param bitsOfPrecision
    * 
    */
	public List<double[]> findNeighbors(String geoHash, int bitsOfPrecision ) {
		// start from root
		return findNeighborsHelper(geoHash, bitsOfPrecision, root); 
	}
	
	

	
	/* 
	 * Recursive helper for searching binary tree
	 * @param val
	 */
	public TreeNode searchHelper(TreeNode current, int val) {
		if (current.getValue() == val || current == null) {
			return current;
		} else {
			if (val < current.getValue()) {
				return searchHelper(current.getLeftChild(), val);
			} else {
				return searchHelper(current.getRightChild(), val); 
			}
		} 
	}
	
	/* 
	 * Searches binary tree for a value 
	 * @param val
	 */
	public TreeNode search(int val) {
		// start at root, recursively search 
		TreeNode current = root; 
		return searchHelper(current, val); 
		
	}
	
	public static void main(String[] args) {
		// 
	}
}

class TreeNode {
	/* 
	 * This is a helper class to be used by the GHTree 
	 * class to represent nodes in the binary tree. 
	 */
	private char value;
	// a node may have a left or right child, which are also Nodes
	private TreeNode leftChild;
	private TreeNode rightChild;
	
	public TreeNode(char val){
		this.value = val;
	}
	
	/*
	 * Public access method for private value
	 */
	public char getValue() {
		return this.value;
	}
	/*
	 * Public setter method for private value 
	 * @param char value
	 */
	public void setValue(char value) {
		this.value = value;
	}
	
	/* 
	 * public method to determine if node is a leaf
	 */
	public boolean isLeaf() {
		return this.getLeftChild() == null && this.getRightChild() == null;
	}
	/*
	 * Public access method for left child
	 */
	public TreeNode getLeftChild() {
		return this.getLeftChild();
	}
	/*
	 * Public access method for right child
	 */
	public TreeNode getRightChild() {
		return this.getRightChild();
	}
	/*
	 * Public setter method for left child
	 * @param TreeNode newLeftChild
	 */
	public void setLeftChild(TreeNode newLeftChild) {
		this.leftChild = newLeftChild;
	}
	/*
	 * Public setter method for right child
	 * @param TreeNode newRightChild
	 */
	public void setRightChild(TreeNode newLeftChild) {
		this.rightChild = newLeftChild;
	}
}
