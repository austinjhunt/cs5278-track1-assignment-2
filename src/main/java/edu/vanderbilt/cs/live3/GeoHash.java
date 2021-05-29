/* 
 * GeoHash - CS 5278 Track 1 Assignment 2
 * May 26, 2021
 * Austin Hunt
 */

package edu.vanderbilt.cs.live3;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils; // To merge two lists with addAll()

public class GeoHash implements GeoDB {

	public static final double[] LATITUDE_RANGE = { -90, 90 };
	public static final double[] LONGITUDE_RANGE = { -180, 180 };
	
	private HashTree hashy = new HashTree();
	
	// store bits of precision as instance variable 
	int bitsOfPrecision; 
	 
	/* Begin GeoDB implementation */ 
	
	/* Constructor with number of bits of precision as a constructor parameter */ 
	public GeoHash (int bitsOfPrecision) {
		this.bitsOfPrecision = bitsOfPrecision;
	}
	/**
    *
    * Your GeoDB implementation should take the maximum
    * number of bits of precision as a constructor parameter.
    * When you call this insert method, it should use the
    * maximum bits of precision when calculating the geohash
    * for the inserted data.
    *
    * Inserts a location into the database. No
    * duplicates are stored. If the position is already
    * present, it should be overwritten.
    *
    * @param lat
    * @param lon
    */
   public void insert(double lat, double lon) { 
	   String geoHash = toHashString(geohash(lat, lon, bitsOfPrecision));
	   System.out.println("GeoHash = " + geoHash);
	   hashy.insertGeoHash(geoHash, lat, lon);
   }

   /**
    * Deletes the specified location from the database.
    *
    * Your GeoDB implementation should take the maximum
    * number of bits of precision as a constructor parameter.
    * When you call this insert method, it should use the
    * maximum bits of precision when calculating the geohash
    * to search for to delete the associated location(s).
    *
    * Returns true if an item was deleted.
    *
    *
    * @param lat
    * @param lon
    */
   public boolean delete(double lat, double lon) {
	   // only delete if saved ; check is O(1)
	   String geoHash = toHashString(geohash(lat, lon, bitsOfPrecision));
	   /* 
	    * Since multiple coord pairs can map to same geoHash, 
	    * don't delete the geoHash itself but delete the lat lon pair 
	    * stored in the leaf/last character pointer of the geohash. 
	    */
	   return hashy.removeGeoHash(geoHash, lat, lon);  
   }

   /**
    * Deletes all locations from the database that
    * match the provided latitude and longitude
    * up to the specified number of bits of precision
    * in their geohashes.
    *
    * For example, if you are using 3 bits of precision,
    * then the following two geohashes match:
    *
    * 0100001 => 010
    * 0101111 => 010
    *
    * With 4 bits of precision, they don't match:
    *
    * 0100001 => 0100
    * 0101111 => 0101
    *
    * Returns the list of deleted locations.
    *
    * If bitsOfPrecision == 0, then this method should delete everything.
    *
    * @param lat
    * @param lon
    */
   public List<double[]> deleteAll(double lat, double lon, int bitsOfPrecision){
	   List<double[]> deleted = new ArrayList<double[]>();
	   return deleted;
   }

   /**
    * Returns true if the database contains at least one location that
    * matches the provided latitude and longitude
    * up to the specified number of bits of precision
    * in its geohash.
    *
    * For example, if you are using 3 bits of precision,
    * then the following two geohashes match:
    *
    * 0100001 => 010
    * 0101111 => 010
    *
    * With 4 bits of precision, they don't match:
    *
    * 0100001 => 0100
    * 0101111 => 0101
    *
    * If bitsOfPrecision == 0, then this method should always return true.
    *
    * @param lat
    * @param lon
    */
   public boolean contains(double lat, double lon, int bitsOfPrecision) {
	   return false;
   }

   /**
    * Returns all locations in the database that
    * match the provided latitude and longitude
    * up to the specified number of bits of precision
    * in their geohashes.
    *
    * For example, if you are using 3 bits of precision,
    * then the following two geohashes match:
    *
    * 0100001 => 010
    * 0101111 => 010
    *
    * With 4 bits of precision, they don't match:
    *
    * 0100001 => 0100
    * 0101111 => 0101
    *
    * If bitsOfPrecision == 0, then this method should always return everything
    * in the database.
    *
    * @param lat
    * @param lon
    */
   public List<double[]> nearby(double lat, double lon, int bitsOfPrecision){
	   List<double[]> neighbors = new ArrayList<double[]>();
	   return neighbors;
   }
	/* End GeoDB implementation */ 
	 
	
	public static double[] copyDoubleArray(double [] arrToCopy) {
		// Copy an array of doubles to a new array 
		double [] b = new double[arrToCopy.length];
		for (int i = 0 ; i < arrToCopy.length; i++){
			b[i] = arrToCopy[i];
		}
        return b; 
	}

	public static boolean[] geohash1D(double valueToHash, double[] valueRange, int bitsOfPrecision) {

		// @ToDo:
		//
		// Implement GeoHashing here for a single value (NOT! latitude, longitude pair).
		//
		// For those seeking an added challenge, use a recursive algorithm that uses a
		// base case and does not pass around a result array.
		//
		// For now, this method only needs to "geohash" either latitude or longitude
		// separately.
		//
		// You will be passed a valueToHash and a valueRange (e.g., the range of
		// longitudes or latitudes).
		//
		// The bits of precision is the number of bits that should be in your output
		// hash.
		//
		// We are approximating "bits" with a boolean array to make things simpler.
		
		if ( bitsOfPrecision == 0 ){
			// base case. 
			return null;
		}
		else {
			// keep recursing and decrementing bitsOfPrecision
			// Copy the valueRange array to prevent changes to valueRange array used for testing
			// (the constant was modified without copying; this fixes that)
			double[] valueRangeCopy = copyDoubleArray(valueRange);

			// get range midpoint
			double midpoint = ( valueRangeCopy[0] + valueRangeCopy[1] ) / 2;
			
			// determine which half the <long/lat> value belongs to based on midpoint comparison
			// if larger half, bit is 1, if lower half, bit is 0; initialize to false
			boolean bit = false;
			if ( valueToHash >= midpoint ){
				// if greater, set bit to 1 and set new lower limit to be midpoint
				bit = true;
				valueRangeCopy[0] = midpoint;
			}
			else {
				// if less, set bit to 0 and set new upper limit to be midpoint
				// bit already false
				valueRangeCopy[1] = midpoint;
			}
			// decrement bitsOfPrecision for next recursive call (closer to base case)
			bitsOfPrecision -= 1;
			
			// return one array combining this bit and the next recursive list of bits, i.e. 
			// [<bit from this call>, <list of bits from next recursive call>] 
			return ArrayUtils.addAll(new boolean[] {bit}, geohash1D(valueToHash, valueRangeCopy, bitsOfPrecision));
		}
	}

	public static boolean[] geohash2D(double v1, double[] v1range, double v2, double[] v2range, int bitsOfPrecision) {

		// @ToDo:
		//
		// Separately compute indvidual 1D geohashes for v1 and v2 and then interleave them together
		// into a final combined geohash.
		//
		// The resulting geohash should have the number of bits specified by bitsOfPrecision.
		
		// Each of the individual lat / long hashes should be half the size of combined
		// account for odd length (where length=bitsOfPrecision) with + (bitsOfPrecision % 2) 
		boolean[] latHash = geohash1D(v1, v1range, (bitsOfPrecision / 2) + (bitsOfPrecision % 2)); 
		boolean[] longHash = geohash1D(v2, v2range, (bitsOfPrecision / 2) + (bitsOfPrecision % 2));
		
		// Create the combined bit array
		boolean[] combined = new boolean[bitsOfPrecision];  
		int i = 0; 
		while (i < bitsOfPrecision) {
			// interleave -> { latHash[0], longHash[0], latHash[1], longHash[1], ... latHash[(bitsOfPrecision-1)/2], longHash[(bitsOfPrecision-1)/2]}
			// dividing by 2 in the indexing truncates the decimal, e.g. 0/2,1/2,3/2,4/2,5/2 -> 0,0,1,1,2,2,..
			if (i % 2 == 0) {
				combined[i] = latHash[i/2]; 
			}
			else {
				combined[i] = longHash[i/2];
			}
			i ++; 
		} 
		return combined; 
	}

	public static boolean[] geohash(double lat, double lon, int bitsOfPrecision) {
		return geohash2D(lat, LATITUDE_RANGE, lon, LONGITUDE_RANGE, bitsOfPrecision);
	}

	// This is a helper method that will make printing out
	// geohashes easier
	public static String toHashString(boolean[] geohash) {
		String hashString = "";
		for (boolean b : geohash) {
			hashString += (b ? "1" : "0");
		}
		return hashString;
	}

	// This is a convenience method to make it easy to get a string of 1s and 0s for a
	// geohash
	public static String geohashString(double valueToHash, double[] valueRange, int bitsOfPrecision) {
		return toHashString(geohash1D(valueToHash,valueRange,bitsOfPrecision));
	}

	// Faux testing for now
	public static void assertEquals(String v1, String v2) {
		if(!v1.contentEquals(v2)) {
			throw new RuntimeException(v1 + " != " + v2);
		}
	}
	
	public HashTree getTree() {
		return this.hashy;
	}
	
	public static void main(String[] args) {
		GeoHash gh1 = new GeoHash(11);
		System.out.println("Inserting 34.5809, 85.3221");
		gh1.insert(34.5809, 85.3221);
		
		System.out.println("Inserting 34.5809, 85.3222");
		gh1.insert(34.5809, 85.3222);
		
		System.out.println("Inserting 78.5221, 32.2354");
		gh1.insert(78.5221, 32.2354);
		
		System.out.println("Tree");
		System.out.println(gh1.getTree());
		
	}
}
