/* 
 * HashTree
 * CS 5278 Track 1 Assignment 2
 * May 19, 2021
 * Austin Hunt
 */

package edu.vanderbilt.cs.live3;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BinaryHashTree { 
	
	private HashNode root;
	
	 
	public void insertGeoHash(String geoHash) {
		root.insertGeoHash(geoHash);
	}
	
	public BinaryHashTree() {
		root = new HashNode();
	}
	 
	public static void main(String[] args) {
		// 
	}
}
 
	
