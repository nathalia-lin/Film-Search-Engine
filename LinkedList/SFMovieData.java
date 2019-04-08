package project3;

import java.util.*;
import java.lang.*;
import java.io.*;


/**
 * This class is a program that searches through a file with movies filmed in SF
 * The program is interactive. 
 * When the program is executed the name of the input file containing the list of all the named 
 * movies and locations
 * It asks for user input: they will enter a keyword that will matche based on actor names or movie titles
 * Based on the user input, the program responds by printing out all the matches
 * 
 * @author Nathalia Lin
 */
public class SFMovieData {

	/**
	* Splits the given line of a CSV file according to commas and double quotes
	* (double quotes are used to surround multi-word entries so that they may contain commas)
	* @author Joanna Klukowska
	* @param textLine a line of text to be passed
	* @return an Arraylist object containing all individual entries found on that line
	*/
	public static ArrayList<String> splitCSVLine(String textLine){

		if (textLine == null ){return null;}

		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;

		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
		nextChar = textLine.charAt(i);

			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar == '\u201D') {

				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false;
				} else {
					insideQuotes = true;
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
					// add it to the current entry
					nextWord.append( nextChar );
				} else { // skip all spaces between entries
					continue;
				}
			} else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar);
				} else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			}

		}
		// add the last word ( assuming not empty )
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		// if actor2 and actor3 are missing
		while (entries.size() < 11){
			entries.add(null);
		}
		return entries;
	}


	/**
	 * The main() method of this program
	 * @param args array of Strings provided on the command line when the program is started; 
	 * the first string should be the name of the input file containing the list of movies filmed in SF. 
	 * @author Joanna Klukowska -> built the input files
	 * @author Nathalia Lin -> built the interactive mode
	*/
	public static void main(String[] args){
		//verify that the command line argument exists 
		if (args.length == 0 ) {
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		}
		
		//verify that command line argument contains a name of an existing file 
		File sfFile = new File(args[0]); 
		if (!sfFile.exists()){
			System.err.println("Error: the file " + sfFile.getAbsolutePath() + " does not exist.\n");
			System.exit(1);
		}
		if (!sfFile.canRead()){
			System.err.println("Error: the file " + sfFile.getAbsolutePath() + " cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//open the file for reading 
		Scanner file = null; 
		
		try {
			file = new Scanner(new FileReader(sfFile));
		} catch (FileNotFoundException e){
			System.err.println("Error: the file " + sfFile.getAbsolutePath()+ " cannot be opened for reading.\n");
			System.exit(1);
		}

		// read the content of the file and save the data in a list of sf movies description
		MovieList list = new MovieList();
		ArrayList<String> data = new ArrayList<String>();
		Location currentLoc = null;
		String line = null; 

		while (file.hasNext()){
			try {
				data = splitCSVLine(file.nextLine());
			} 
			catch (NoSuchElementException ex){
				//caused by an incomplete or miss-formatted line in the input file
				System.err.println(line);
				continue; 
			}

			try {
				Actor act = new Actor(data.get(8));
				Actor act2 = null;
				if (!data.get(9).isEmpty() && data.get(9) != null){
					act2 = new Actor(data.get(9));
				}
				Actor act3 = null;
				if (data.get(10) != null && !data.get(10).isEmpty()){
					act3 = new Actor(data.get(10));
				} 
				Movie current = new Movie(data.get(0), Integer.parseInt(data.get(1)), data.get(6), data.get(7), act, act2, act3);
				list.add(current);
				currentLoc = new Location(data.get(2), data.get(3));
				current.addLocation(currentLoc);
				// System.out.println(current.getLocObj(0).getLocation());
			}
			catch (IllegalArgumentException ex){
				// ignore this exception and skip to the next line
				// System.err.println("Invalid input");
			}
		}

		// Interactive mode:
		System.out.println("Search the database by matching keywords to titles or actor names.");
		System.out.println("   To search for matching titles, enter\n      title KEYWORD");
		System.out.println("   To search for matching actor names, enter\n      actor KEYWORD");
		System.out.println("   To finish the program, enter\n      quit\n\n");

		Scanner in = new Scanner(System.in);
		String search = null;
		String[] breakdown;
		String first = null;
		String keyword = null;
		Location loc = null;

		MovieList matchingTitles = null;
		MovieList uniqueMatchingTitles = null;
		MovieList matchingActor = null;
		MovieList uniqueMatchingActor = null;

		do {
			System.out.println("Enter your search query:\n");
			// get value of from the user
			search = in.nextLine();
			// break down to get the first word and the keyword
			breakdown = search.split(" ");
			first = breakdown[0];
			keyword = search.replace(breakdown[0],"").trim();
			System.out.println();

			// keep asking for proper input until the first word is valid
			while (!first.equalsIgnoreCase("title") && !first.equalsIgnoreCase("actor") && !first.equalsIgnoreCase("quit")){ 
				System.out.println("This is not a valid query. Try again.\n");
				search = in.nextLine();
				breakdown = search.split(" ");
				first = breakdown[0];
				keyword = search.replace(breakdown[0],"").trim();
				System.out.println();
			}

			// if they are searching for title
			if (first.equalsIgnoreCase("title")){

				// if there are no matching title with the keyword
				if (list.getMatchingTitles(keyword) == null){
					System.out.println("No matches found. Try again.");
					continue;
				} 

				// if there are matching title with the keyword
				else {
					matchingTitles = list.getMatchingTitles(keyword);
					uniqueMatchingTitles = list.getMatchingTitles(keyword);

					// loop through to add location and get a unique title list
					for (int i = 1; i < matchingTitles.size(); i++){
						Movie previous = matchingTitles.get(i-1);
						Movie current = matchingTitles.get(i);

						// add all the locations from one movie
						if (previous.equals(current)){
							for (Location l : previous.getLocObj()){
								if (!current.getLocObj().contains(l)){
									current.addLocation(l);
								}
							}
							// make the list unique
							uniqueMatchingTitles.remove(previous);
						}
					}

					// print out the matching titles result
					for (Movie mt: uniqueMatchingTitles){
						System.out.println(mt);
					}
				}
			}

			// if they are searching for actor
			else if (first.equalsIgnoreCase("actor")) {

				// if there are no matching actor with the keyword
				if (list.getMatchingActor(keyword) == null){
					System.out.println("No matches found. Try again.");
					continue;
				}

				// if there are matching actor with the keyword
				else {
					matchingActor = list.getMatchingActor(keyword);
					uniqueMatchingActor = list.getMatchingActor(keyword);

					// loop through to add location and get a unique actor list
					for (int i = 1; i < matchingActor.size(); i++){
						Movie previous = matchingActor.get(i-1);
						Movie current = matchingActor.get(i);

						// add all the locations from one movie
						if (previous.equals(current)){
							for (Location l : previous.getLocObj()){
								if (!current.getLocObj().contains(l)){
									current.addLocation(l);
								}
							}
							// make the list unique
							uniqueMatchingActor.remove(previous);
						}
					}

					// print out the matching actors result
					for (Movie ma: uniqueMatchingActor){
						System.out.println(ma);
					}
				}
			}
		}

		// exit loop if user inputs quit
		while (!search.equalsIgnoreCase("quit"));
		in.close();
	}
}