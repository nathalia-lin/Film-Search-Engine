package project3;

/**
 * This class represents a Location which consists of a string location and possibly a fun fact about the location
 * @author Nathalia Lin
*/
public class Location{

	private String loc;
	private String fact;
	
	/**
	 * Constructs a new Location object with specified location and fun fact 
	 * @param loc location to be used for this Location, any non-empty string
	 * @param fact fun fact to be used for this Location
	 * @throws IllegalArgumentException if loc parameter is invalid (null or empty-string)
	 */
	Location(String loc, String fact) throws IllegalArgumentException{
		if (loc == null || loc.isEmpty()){
			throw new IllegalArgumentException("Invalid location: cannot be empty or null");
		}
		this.loc = loc;
		this.fact = fact;
	}

	/**
	 * Returns the string location representing this Location object
	 * @return the location of this Location object
	*/
	public String getLocation() {
		return loc;
	}

	/**
	 * Returns the string fun fact representing this Location object
	 * @return the fact of this Location object
	*/
	public String getFact() {
		return fact;
	}
}