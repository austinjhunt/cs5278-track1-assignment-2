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
	private HashMap<Character, ArrayList<LatLon>> leafHash;  // change arraylist to hashmap
	//FIXME:
	private HashMap<Character, HashMap<String, LatLon>> leafHashMoreEfficient;
	
	private boolean leaf;
	
	private int level; // level in the tree
	
	public HashNode(int level) {
		this.nonLeafHash = new HashMap<Character, HashNode>();
		this.leafHash = new HashMap<Character, ArrayList<LatLon>>(); 
		this.level = level;
	}
	
	public HashNode(String geoHashString, LatLon coords, int level) {
		this.nonLeafHash = new HashMap<Character, HashNode>();
		this.leafHash = new HashMap<Character, ArrayList<LatLon>>();
		this.level = level;
		// use first character of the string argument as the key 
		// first character gets removed for each recursive call until empty
		char key = geoHashString.charAt(0); 
		this.leaf = geoHashString.length() == 1;
		if (this.leaf) {
			// node is leaf if char is last in geohash string   
			ArrayList<LatLon> coordPairsWithThisGeoHash;
			if (this.leafHash.containsKey(key)) {
				coordPairsWithThisGeoHash = this.leafHash.get(key);
				if (!coordPairsWithThisGeoHash.contains(coords)) {
					coordPairsWithThisGeoHash.add(coords);
				}
			} else {
				// create & put new array list with the geoHashObject 
				// as sole element
				coordPairsWithThisGeoHash = new ArrayList<LatLon>(
						Arrays.asList(coords)); 
				this.leafHash.put(key, coordPairsWithThisGeoHash);
			}
			
			
		} else {
			// System.out.println("SUBSTRING = " + geoHashString.substring(1) );
			HashNode nextHashNode = new HashNode(
					geoHashString.substring(1), coords, this.level + 1); 
			this.nonLeafHash.put(key, nextHashNode); 
		}
	} 
	
	/*
	 * Insert method to insert new geohash strings into the tree.
	 * Method defined on the node because it recursively defines a path 
	 * of pointers from the root node to the leaf node (last character in hash)
	 * Always first invoked on root as root.insertGeoHash(String geoHash);
	 * @param geoHashString
	 */
	
	public void insertGeoHash(String geoHashString, double lat, double lon) {
		if (geoHashString.trim().length() > 0){
			// only handle if not empty 
			char key = geoHashString.charAt(0);
			// FIXME: don't create this on each recursive call. 
			LatLon coords = new LatLon(lat, lon);
			// Starts recursive build of path to leaf 
			if (!this.leaf && nonLeafHash.containsKey(key)){
				// System.out.println("non leaf hash at level " + this.level + 
					//	" already contains key " + key);
				
				// take off the first character and call insertGeoHash on 
				// the node this existing key points at 
				HashNode nextHashNode = nonLeafHash.get(key);
				nextHashNode.insertGeoHash(geoHashString.substring(1), lat, lon);
			} else if (!this.leaf) {
				// not stored yet, create new node
				HashNode nextHashNode = new HashNode(
						geoHashString.substring(1), coords, this.level + 1);
				nonLeafHash.put(key, nextHashNode);
			} else {
				// leaf 
				if (leafHash.containsKey(key)) {
					ArrayList<LatLon> list = leafHash.get(key);
					if (!list.contains(coords)) {
						list.add(coords);
					}
				} else {
					ArrayList<LatLon> list = new ArrayList<LatLon>(
							Arrays.asList(coords)); 
					leafHash.put(key, list);
				}
			}
		}
		else {
			System.out.println("Should never reach this.");
		}
		
	}

	public boolean removeGeoHash(String geoHashString, double lat, double lon) {
		if (geoHashString.trim().length() > 0){ 
			
		}
		return false;
	}
	
	/*
	 * Given a key, get the value from a leaf node hash map
	 * @param char key 
	 * @return ArrayList<GeoHash>
	 */
	public ArrayList<LatLon> getLeafNodeValue(char key) { 
		return leafHash.get(key);
	}
	
	/*
	 * Given a key, get the value from a non leaf node hash map
	 * @param char key
	 * @return HashNode
	 */
	public HashNode getNonLeafNodeValue(char key) { 
		return nonLeafHash.get(key);
	}
	
	public String latLonListToString(ArrayList<LatLon> list) {
		final List<String> output = new ArrayList<String>();
		for (LatLon item : list) {
			output.add(item.toString() + " ");
		}
		String out = "";
		for (String s : output) {
			out += s;
		}
		return out;
	}
	
	public String toString() {
		final List<String> output = new ArrayList<String>();
		output.add("(Leaf ? " + leaf + ", Level-> " + this.level + ") ");
		if (!this.leaf) { 
			nonLeafHash.forEach((key, node) -> {
				output.add("Key: " + key + "->Node: \n" + node.toString());
			}); 
		} else {  
			leafHash.forEach((key, list) -> {
				output.add("Key: " + key + "->List: " + latLonListToString(list));
			}); 
		}
		String out = ""; 
		for (String s : output) {
			out += s;
		}
		return out;
		
	}
	
	public static void main(String[] args) {
		
	}
}
