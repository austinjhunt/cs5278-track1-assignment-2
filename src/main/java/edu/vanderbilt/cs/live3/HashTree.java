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

public class HashTree { 
	
	private HashNode root;
	
	public HashTree() {
		root = new HashNode(0); // level 0 for root
	}
	 
	public void insertGeoHash(String geoHash, double lat, double lon) {
		root.insertGeoHash(geoHash, lat, lon);
	}
	
	public boolean removeGeoHash(String geoHash, double lat, double lon) {
		return root.removeGeoHash(geoHash, lat, lon);
	}
	
	public static void main(String[] args) { 
	}
	
	public String toString() {
		return root.toString();
	}
}
 
	
