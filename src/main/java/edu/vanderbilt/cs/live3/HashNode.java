package edu.vanderbilt.cs.live3;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HashNode {
	// non leaf nodes have hashes with a character pointing to the next character 
	private HashMap<Character, HashNode> nonLeafHash;
	
	// leaf nodes have hashes with a character (the last character in a geohash string)
	// pointing to a list of entries associated with this geohash string
	private HashMap<Character, ArrayList<GeoHash>> leafHash; 
	 
	
	/* 
	 * Constructor accepts a leaf indicating whether node is a leaf
	 * (i.e. final character in a geohash string), the character to 
	 * use as a key for the hashmap
	 * 
	 */
	public HashNode(String geoHashString, GeoHash geoHashObject) {
		// use first character of the string argument as the key 
		// first character gets removed for each recursive call until empty 
		char key = geoHashString.charAt(0); 
		
		if (geoHashString.length() == 1) {
			// node is leaf if char is last in geohash string  
			
			// create array list with the geoHashObject as sole element
			ArrayList<GeoHash> objectsWithThisGeoHash = new ArrayList<GeoHash>(
					Arrays.asList(geoHashObject));
			// point key at this list in the leafHash 
			this.leafHash.put(key, objectsWithThisGeoHash);
		} else {
			// not a leaf; store key->HashNode map in nonLeafHash 
			// and recursively construct until last character is reached
			HashNode nextHashNode = new HashNode(geoHashString.substring(1), null); 
			nonLeafHash.put(key, nextHashNode); 
		}
	}
	
	
	public void putGeoHash(String geoHashString) {
		char key = geoHashString.charAt(0);
		
	}
	
	/*
	 * Given a key, get the value from a leaf node hash map
	 * @param char key 
	 * @return ArrayList<GeoHash>
	 */
	public ArrayList<GeoHash> getLeafNodeValue(char key) { 
		return leafHash.get(key);
	}
	
	/*
	 * Given a key, get the value from a non leaf node hash map
	 * @param char key
	 * @return HashNode
	 */
	public HashNode getLeafNodeValue(char key) { 
		return nonLeafHash.get(key);
	}
}
