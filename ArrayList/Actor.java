package project2;

/**
 * This class represents an Actor which consists of an actors name
 * @author Nathalia Lin
*/
public class Actor{

	private String name;

	/**
	 * Constructs a new Actor object with specific name value.
	 * @param actor name to be used for this Actor, should be any non-empty string
	 * @throws IllegalArgumentException if name parameter is invalid (null or empty-string)
	*/
	public Actor(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty() ){
			throw new IllegalArgumentException("Invalid actor: cannot be empty or null");
		}
		this.name = name;
	}

	/**
	 * Returns the actor name representing this Actor object
	 * @return the name of this Actor object
	*/
	public String getName(){
		return name;
	}
}