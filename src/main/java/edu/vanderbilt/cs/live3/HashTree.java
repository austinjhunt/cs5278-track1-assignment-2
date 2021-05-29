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
		LatLon coords = new LatLon(lat, lon); 
		root.insertGeoHash(geoHash, coords);
	}
	
	public boolean removeGeoHash(String geoHash, double lat, double lon) {
		LatLon coords = new LatLon(lat, lon); 
		return root.removeGeoHash(geoHash, coords);
	}
	
	public List<double[]> deleteAll(String geoHash, int bitsOfPrecision){
		return root.removeAllMatchingUpToNChars(
				geoHash, bitsOfPrecision);
		 
	}
	
	public boolean contains(String geoHash, double lat, double lon, int bitsOfPrecision) {
		LatLon coords = new LatLon(lat, lon);
		return root.containsUpToNChars(geoHash, coords, bitsOfPrecision);
	}
	
	public List<double[]> neighbors(String geoHash, int bitsOfPrecision) {
		return root.neighborsUpToNChars(geoHash, bitsOfPrecision);
	}
	
	public static void main(String[] args) { 
	}
	
	public String toString() {
		return root.toString();
	}
}
 
	
