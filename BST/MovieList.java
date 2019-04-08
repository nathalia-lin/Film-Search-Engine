package project3;

import java.util.*;

/**
 * MovieList class is used to store a collection of Movie objects
 * This class inherits all of its properties from an ArrayList<Movie>. It 
 * adds Movie-specific functions that allow search by Movie titles and actors
 * based on the input keyword
 *
 * This class sorts the Movie object based on the compareTo function in Movie
 * @author Nathalia Lin
 */
public class MovieList extends LinkedList<Movie>{

	/**
	 * Constructs a new MovieList object by inheriting from Movie
	*/
	public MovieList(){
		super();
	}

	/**
	 * Search through the list of titles for an object matching 
	 * the given keyword. 
	 * @param keyword the string for which to search 
	 * @return a list of all the matching titles found
	 * @return null if keyword is empty or null and if there is no matching titles
	 */
	public MovieList getMatchingTitles (String keyword){
		MovieList matchTitle = new MovieList();	

		if (keyword == null || keyword.isEmpty()){
			return null;
		}

		// loop through the movies in this MovieList object
		for (Movie m : this){
			// check if title contains the keyword
			if (m.getTitle().toLowerCase().contains(keyword.toLowerCase())){
				matchTitle.add(m);
			}
		}

		// if list is not empty, then sort and return it
		if (matchTitle.size() > 0){
			matchTitle.sort();
			return matchTitle;
		}
		return null;
	}

	/**
	 * Search through the list of actors for an object matching 
	 * the given keyword. 
	 * @param keyword the string for which to search 
	 * @return a list of all the matching actors found
	 * @return null if keyword is empty or null and if there is no matching actor
	 */
	public MovieList getMatchingActor (String keyword){
		MovieList matchActor = new MovieList();	

		if (keyword == null || keyword.isEmpty()){
			return null;
		}

		// loop through the movies in this MovieList object
		for (Movie m : this){
			// check if valid actor contain the keyword
			if (m.getActor1() != null && m.getActor1().getName().toLowerCase().contains(keyword.toLowerCase())){
				matchActor.add(m);
			} else if (m.getActor2() != null && m.getActor2().getName().toLowerCase().contains(keyword.toLowerCase())){
				matchActor.add(m);
			} else if (m.getActor3() != null && m.getActor3().getName().toLowerCase().contains(keyword.toLowerCase())){
				matchActor.add(m);
			}
		}

		// if list is not empty, then sort and return it
		if (matchActor.size() > 0){
			matchActor.sort();
			System.out.println(matchActor);
			return matchActor;
		}
		return null;
	}

	/**
	 * Returns the string representation of this MovieList.
	 * @return the string representation of this MovieList object 
	*/
	@Override
	public String toString(){
		String movieTitle = "";
		int size = this.size() - 1;

		// print all the movie titles except last one
		for (int i = 0; i < this.size() - 1; i++){
			if (!movieTitle.toLowerCase().contains(this.get(i).getTitle().toLowerCase())){
				movieTitle += this.get(i).getTitle() + "; ";
			}
		}

		// prints last movie title without final colon
		if (!movieTitle.toLowerCase().contains(this.get(size).getTitle().toLowerCase())){
			movieTitle += this.get(size).getTitle();
		}

		return movieTitle;
	}
}