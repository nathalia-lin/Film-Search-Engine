package project3;

import java.util.*;
/** 
 * This class represents a movie.
 * It uses two equivalent constructor: 
 * - Movie (title, year) in which the parameters must be valid
 * - Movie (title, year, director, writer, actor1, actor2, actor3) in which some of the parameters have to be valid
 * creates movie objects of SF locations 
 * creates an Location Object arrayList and a method that adds Location Objects
 * prints out a specific format for each movie
 * @author Nathalia Lin
*/
public class Movie implements Comparable<Movie>{

	private String title;
	private int year;
	private ArrayList<Location> locObj;
	private String director;
	private String writer;
	private Actor actor1;
	private Actor actor2;
	private Actor actor3;

	/**
	 * Constructs a new Movie object with specifiec title and year
	 * @param title component of this Movie object, should not be null or empty string
	 * @param year component of this Movie object, should be between years 1900 and 2020
	 * @throws IllegalArgumentException if title or year is invalid
	*/
	public Movie(String title, int year) throws IllegalArgumentException {
		if (title == null || title.isEmpty()){
			System.out.println("Title");
			throw new IllegalArgumentException("Invalid title: cannot be empty or null");
		}
		if (year < 1900 || year > 2020){
			throw new IllegalArgumentException("Invalid year: has to be a number between 1900 to 2020");
		}
		this.title = title;
		this.year = year;
		locObj = new ArrayList<Location>();
	}

	/**
	 * Constructs a new Movie object with specifiec title, year, director, writer, actor1, actor2, actor3
	 * @param title component of this Movie object, any non-empty string
	 * @param year component of this Movie object, a number between 1900 and 2020 (including both end points)
	 * @param director component of this Movie object, any non-empty string
	 * @param writer component of this Movie object,  any non-empty string to indicate the name, null or an empty string when the data is not available
	 * @param actor1 component of this Movie object, any non-emptyh string
	 * @param actor2 component of this Movie object, any non-empty string to indicate the name, null or an empty string when the data is not available
	 * @param actor3 component of this Movie object, any non-empty string to indicate the name, null or an empty string when the data is not available
	 * @throws IllegalArgumentException if title, year, actor1 parameters are invalid
	*/
	public Movie(String title, int year, String director, String writer, Actor actor1, Actor actor2, Actor actor3) throws IllegalArgumentException{
		if (title == null || title.isEmpty()){
			throw new IllegalArgumentException("Invalid title: cannot be empty or null");
		}
		if (year < 1900 || year > 2020){
			throw new IllegalArgumentException("Invalid year: has to be a number between 1900 to 2020");
		}
		if (actor1 == null){
			throw new IllegalArgumentException("Invalid actor1: cannot be empty or null");
		}
		
		this.title = title;
		this.year = year;
		this.director = director;
		this.writer = writer;
		this.actor1 = actor1;
		this.actor2 = actor2; 
		this.actor3 = actor3;
		locObj = new ArrayList<Location>();
	}

	/**
	 * Returns the string Title representing this Movie object
	 * @return the title of this Title object
	*/
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the string Year representing this Movie object
	 * @return the year of this Movie object
	*/
	public int getYear() {
		return year;
	}

	/**
	 * Returns the string Director representing this Director object
	 * @return the director of this Director object
	*/
	public String getDirector() {
		return director;
	}

	/**
	 * Returns the string Writer representing this Movie object
	 * @return the writer of this Movie object
	*/
	public String getWriter() {
		return writer;
	}

	/**
	 * Returns the Actor object Actor1 representing this Movie object
	 * @return the actor1 of this Movie object
	*/
	public Actor getActor1() {
		return actor1;
	}

	/**
	 * Returns the Actor object Actor2 representing this Movie object
	 * @return the actor2 of this Movie object
	 * @return if actor2 is null, returns null
	*/
	public Actor getActor2() {
		if (actor2 != null)
			return actor2;
		return null;
	}

	/**
	 * Returns the Actor object Actor3 representing this Movie object
	 * @return the actor3 of this Movie object
	 * @return if actor3 is null, returns null
	*/
	public Actor getActor3() {
		if (actor3 != null){
			return actor3;
		}
		return null;
	}
	
	/**
	 * Returns the Location object arraylist location representing this Movie object
	 * @return the Location object of this Movie object
	*/
	public ArrayList<Location> getLocObj(){
		return locObj;
	}

	/**
	 * Adds an object Location to ArrayList location representing this Movie object
	 * @throws IllegalArgumentException if Location object is invalid
	*/
	public void addLocation(Location loc) throws IllegalArgumentException{ 
		if (loc == null){
			throw new IllegalArgumentException("Invalid location: unable to add location");
		}
		locObj.add(loc);
	}

	/**
	 * Compares the title and year of two Movie objects
	 * @return true if they are the same
	 * @return false if they are not the same
	*/
	// @Override
	public boolean equals(Movie obj){
		if (obj.getTitle().equalsIgnoreCase(this.getTitle()) && obj.getYear() == this.getYear()){
			return true;
		}
		return false;
	}

	/**
	 * Compares two Movie object based on their year
	 * If they have equal year, then compare their titles
	 * @return 1 if the year or title is greater for this Movie object
	 * @return -1 if the year or title is greater for input Movie object
	 * @return 0 if both Movie objects have same year and title
	*/
	public int compareTo(Movie o){
		if (this.getYear() == o.getYear()){
			return this.getTitle().compareToIgnoreCase(o.getTitle());
		} else if (this.getYear() > o.getYear()){
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * Returns the string representation of this Movie.
	 * @return the string representation of this Movie object 
	*/
	@Override
	public String toString(){
		String description = "";

		description += this.getTitle() + " (" + this.getYear() + ")\n";
		description += "-----------------------------------------\n";
		description += "director\t\t: " + this.getDirector() + "\n";
		description += "writer\t\t\t: " + this.getWriter() + "\n";
		description += "starring\t\t: ";

		// print out all the actors
		if (actor1 != null && !actor1.getName().isEmpty()){
			description += this.getActor1().getName() + ", ";
		}
		if (actor2 != null && !actor2.getName().isEmpty()){
			description += this.getActor2().getName() + ", ";
		}
		if (actor3 != null && !actor2.getName().isEmpty()){
			description += this.getActor3().getName() + ", ";
		}

		description += "\nfilmed on location at:\n";

		// reverse the location Object to print it in the right order
		Collections.reverse(locObj);

		// print out all the locations
		for (Location loc : locObj){
			description += "\t" + loc.getLocation();

			// if there is a fun fact, then print it out!
			if (loc.getFact() != null && !loc.getFact().isEmpty()){
				description += " (" + loc.getFact().trim() + ")";
			}
			description += "\n";
		}
		
		return description;
	}
}

