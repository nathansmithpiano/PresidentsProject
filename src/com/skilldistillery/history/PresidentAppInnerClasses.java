package com.skilldistillery.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class PresidentAppInnerClasses {
	private static final String fileName = "resources" + File.separator + "presidents.tsv";
	private List<President> presidents = new ArrayList<>();
	Scanner kb = new Scanner(System.in);

	// input options
	private int minYear = 1770;
	private int maxYear = 2030;
	private final int MAX_TERM_NUMBER = 1000;
	private final int CURRENT_YEAR = LocalDate.now().getYear();
	private final String RESTRICTED_FILENAME = "presidents.tsv";

	public PresidentAppInnerClasses() {
		this.loadPresidents(fileName);
	}

	public static void main(String[] args) {
		PresidentAppInnerClasses app = new PresidentAppInnerClasses();
		app.start();
	}

	public void start() {
		System.out.println("====== ===== WELCOME TO PRESIDENT MANAGER ===== =====");
		mainMenu();

		// initial print
//		this.printPresidents(this.getPresidents());;
		
//		Sorting 1, member class
//		System.out.println("====== Sorting by Party, then by Term ======");
//		printPresidents(sortByPartyAndTerm());
		
//		Sorting 2, local class
//		System.out.println("====== Sorting by Reason Left Office, then by Term ======");
//		printPresidents(sortByReasonLeftOffice());
		
//		Sorting 3, anonymous class
//		System.out.println("====== Sorting by Last Name, then by Term ======");
//		printPresidents(sortByLastName());
		
//		Filtering 1, exiting structure
//		System.out.println("====== Filter by party = Whig ======");
//		printPresidents(filter("Whig", new PresidentPartyMatcher()));
		
//		Filtering 2, member class
//		System.out.println("====== Filter by firstName = James ======");
//		printPresidents(filter("James", new PresidentFirstNameMatcher()));
		
//		Filtering 3, local class
//		class PresidentWhyLeftOfficeMatcher implements PresidentMatcher {
//			@Override
//			public boolean matches(President pres, String string) {
//				return pres.getWhyLeftOffice().equalsIgnoreCase(string);
//			}
//		}
//		System.out.println("====== Filter by getWhyLeftOffice() = Term ended ======");
//		printPresidents(filter("Term ended", new PresidentWhyLeftOfficeMatcher()));
		
//		Filtering 4.i. Anonymous class; last names start with "C"
//		printPresidents(filter("C", new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return pres.getFirstName().toLowerCase().startsWith(string.toLowerCase());
//			}
//		}));
		
//		Filtering 4.ii. Party contains the string "Democrat".
//		printPresidents(filter("Democrat", new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return pres.getParty().equalsIgnoreCase(string);
//			}
//		}));
		
//		Filtering 4.iii. Died in office or Assassinated (my choice)
//		printPresidents(filter("Died in office", new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return (pres.getWhyLeftOffice().equalsIgnoreCase(string)
//						|| pres.getWhyLeftOffice().equalsIgnoreCase("Assassinated"));
//			}
//		}));
		
//		Filtering 4.iv. Won only a single election
//		printPresidents(filter("1", new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return (pres.getElectionsWon() + "").equalsIgnoreCase(string);
//			}
//		}));
		
//		Filtering 4.v. Terms started in the 19th century
//		printPresidents(filter("1800", new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return ( (pres.getTermBegan().getYear() > Integer.parseInt(string))
//						&& (pres.getTermBegan().getYear() < Integer.parseInt(string) + 99));
//			}
//		}));

	}

	public void mainMenu() {
		System.out.println("\n====== MAIN MENU ======");
		System.out.println("1. Print from presidents.tsv");
		System.out.println("2. Enter your own list");
		System.out.println("3. Filter from presidents.tsv");
		System.out.println("4. Quit");
		int choice = getIntWithinRange("Your choice: ", 1, 4);
		
		switch (choice) {
		case 1:
			// print presidents.tsv
			// TODO: ask, why this?
			this.printPresidents(this.getPresidents());
			mainMenu();
			break; // this seems unnecessary
		case 2:
			// enter
			this.enterList();
			break;
		case 3:
			//filter
			this.filterList();
			break;
		case 4:
			// quit
			this.quit();
		}

	}
	
	private void sortList() {
		
	}
	
	private void filterList() {
		//reused things within switch, more efficient
		String text;
		int choice1;
		int choice2;
		int choice3;
		int startRange;
		int endRange;
		int highest;
		int lowest;
		boolean noneFound = false;
		Set<String> set = new TreeSet<>();
		List<String> list = new ArrayList<String>();
		
		System.out.println("\n====== FILTER OPTIONS ======");
		System.out.println("Filter by...");
		System.out.println("1. name");
		System.out.println("2. first name starts with");
		System.out.println("3. party affiliation");
		System.out.println("4. when term began or ended");
		System.out.println("5. number of elections won");
		System.out.println("6. why they left office");
		System.out.println("7. Return to Main Menu");
		choice1 = getIntWithinRange("Your choice: ", 1, 7);
		
		switch (choice1) {
		case 1:
			System.out.println("\t=== FILTER BY NAME ===");
			System.out.println("\t1. First Name");
			System.out.println("\t2. Middle Name");
			System.out.println("\t3. Last Name");
			choice2 = getIntWithinRange("\tYour choice: ", 1, 3);
			
			switch (choice2) {
			case 1:
				System.out.println("\t\t=== FILTER BY FIRST NAME ===");
				// create set of unique first names
				set.clear();
				list.clear();
				for (President pres : presidents) {
					text = pres.getFirstName();
					//format as Title case
					if (text.length() > 0) {
						text = text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
					}
					set.add(text);
				}
				// use list to use indexes and naturally sort alphabetically
				list.addAll(set);
				Collections.sort(list);
				//print all options and prompt
				for (int i = 0; i < list.size(); i++) {
					//empty
					if (list.get(i).length() == 0) {
						System.out.println("\t\t" + (i + 1) + ": no first name in list");
					} else {
						//not empty
						System.out.println("\t\t" + (i + 1) + ": " + list.get(i));
					}
				}
				choice3 = getIntWithinRange("\t\tYour choice: ", 1, list.size() - 1);
				text = list.get(choice3 - 1);
				
				// Filtering Part 2. Member class
				//empty
				if (text.length() == 0) {
					System.out.println("====== FILTERED BY FIRST NAME NOT IN LIST ======");
				} else {
					//not empty
					System.out.println("====== FILTERED BY FIRST NAME = " + text.toUpperCase() + " ======");
				}
				printPresidents(filter(text, new PresidentMatcher() {
					@Override
					public boolean matches(President pres, String string) {
						//empty
						if (string.length() == 0) {
							return pres.getFirstName().length() == 0;
						} else {
							//not empty
							return pres.getFirstName().equalsIgnoreCase(string);
						}
					}
				}));
				break;
			case 2:
				System.out.println("\t\t=== FILTER BY MIDDLE NAME ===");
				// create set of unique middle names
				set.clear();
				list.clear();
				for (President pres : presidents) {
					text = pres.getMiddleName();
					//format as Title case
					if (text.length() > 0) {
						text = text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
					}
					set.add(text);
				}
				// use list to use indexes and naturally sort alphabetically
				list.addAll(set);
				Collections.sort(list);
				//print all options and prompt
				for (int i = 0; i < list.size(); i++) {
					//empty
					if (list.get(i).length() == 0) {
						System.out.println("\t\t" + (i + 1) + ": no middle name in list");
					} else {
						//not empty
						System.out.println("\t\t" + (i + 1) + ": " + list.get(i));
					}
				}
				choice3 = getIntWithinRange("\t\tYour choice: ", 1, list.size());
				text = list.get(choice3 - 1);
				
				// Filtering Part 2. Member class
				//empty
				if (text.length() == 0) {
					System.out.println("====== FILTERED BY MIDDLE NAME NOT IN LIST ======");
				} else {
					//not empty
					System.out.println("====== FILTERED BY MIDDLE NAME = " + text.toUpperCase() + " ======");
				}
				printPresidents(filter(text, new PresidentMatcher() {
					@Override
					public boolean matches(President pres, String string) {
						//empty
						if (string.length() == 0) {
							return pres.getMiddleName().length() == 0;
						} else {
							//not empty
							return pres.getMiddleName().equalsIgnoreCase(string);
						}
					}
				}));
				break;
			case 3:
				System.out.println("\t\t=== FILTER BY LAST NAME ===");
				// create set of unique last names
				set.clear();
				list.clear();
				//MARKER
				for (President pres : presidents) {
					text = pres.getLastName();
					//format as Title case
					if (text.length() > 0) {
						text = text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
					}
					set.add(text);
				}
				// use list to use indexes and naturally sort alphabetically
				list.addAll(set);
				Collections.sort(list);
				//print all options and prompt
				for (int i = 0; i < list.size(); i++) {
					//empty
					if (list.get(i).length() == 0) {
						System.out.println("\t\t" + (i + 1) + ": no last name in list");
					} else {
						//not empty
						System.out.println("\t\t" + (i + 1) + ": " + list.get(i));
					}
				}
				choice3 = getIntWithinRange("\t\tYour choice: ", 1, list.size());
				text = list.get(choice3 - 1);
				
				// Filtering Part 2. Member class
				//empty
				if (text.length() == 0) {
					System.out.println("====== FILTERED BY LAST NAME NOT IN LIST ======");
				} else {
					//not empty
					System.out.println("====== FILTERED BY LAST NAME = " + text.toUpperCase() + " ======");
				}
				printPresidents(filter(text, new PresidentMatcher() {
					@Override
					public boolean matches(President pres, String string) {
						//empty
						if (string.length() == 0) {
							return pres.getLastName().length() == 0;
						} else {
							//not empty
							return pres.getLastName().equalsIgnoreCase(string);
						}
					}
				}));
				break; //not necessary, last case
			} //end sub-menu
			break; //break from sub-menu case 1
		case 2:
			System.out.println("\t=== FILTER BY LAST NAME STARTS WITH ===");
			System.out.print("\tLast name starts with: ");
			text = kb.next();
			// Filtering 4.i. Anonymous class
			
			//see if any matches
			//TODO: should be able to do this via matcher and lambda
			noneFound = true; //reset
			for (President pres : presidents) {
				if (pres.getParty().equalsIgnoreCase(text)) {
					noneFound = false;
				}
			}
			if (noneFound) {
				System.out.println("====== NO LAST NAMES START WITH " + text.toUpperCase() + " ======");
			} else {
				System.out.println("====== FILTERED BY LAST NAME STARTS WITH " + text.toUpperCase() + " ======");
				printPresidents(filter(text, new PresidentMatcher() {
					@Override
					public boolean matches(President pres, String string) {
						return pres.getFirstName().toLowerCase().startsWith(string.toLowerCase());
					}
				}));
			}
			break;
		case 3:
			System.out.println("\t=== FILTER BY PARTY AFFILIATION ===");
			// create set of unique party affiliations
			set.clear();
			list.clear();
			for (President pres : presidents) {
				text = pres.getParty();
				//format as Title case
				if (text.length() > 0) {
					text = text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
				}
				set.add(text);
			}
			// use list to use indexes and naturally sort alphabetically
			list.addAll(set);
			Collections.sort(list);
			//print all options and prompt
			for (int i = 0; i < list.size(); i++) {
				//empty
				if (list.get(i).length() == 0) {
					System.out.println("\t" + (i + 1) + ": no party in list");
				} else {
					//not empty
					System.out.println("\t" + (i + 1) + ": " + list.get(i));
				}
			}
			choice2 = getIntWithinRange("\tYour choice: ", 1, list.size());
			text = list.get(choice2 - 1);
			// Filtering Part 1, exiting structure
			//printPresidents(filter(text, new PresidentPartyMatcher())); //does not work for empty
			//original PresidentPartyMatcher.java doesn't work for empty, doing here via anonymous class
			
			//empty
			if (text.length() == 0) {
				System.out.println("====== FILTERED BY NO PARTY IN LIST ======");
			} else {
				//not empty
				System.out.println("====== FILTERED BY PARTY = " + text.toUpperCase() + " ======");
			}
			printPresidents(filter(text, new PresidentMatcher() {
				@Override
				public boolean matches(President pres, String string) {
					//empty
					if (string.length() == 0) {
						return pres.getParty().length() == 0;
					} else {
						//not empty
						return pres.getParty().equalsIgnoreCase(string);
					}
				}
			}));
			break;
		case 4:
			System.out.println("\t=== FILTER BY WHEN TERM BEGAN OR ENDED ===");
			System.out.println("\t1. When term began");
			System.out.println("\t2. When term ended");
			choice2 = getIntWithinRange("\tYour choice: ", 1, 2);
			
			//do these for both options (began and ended)
			//get lowest and highest years
			lowest = 99999; //arbitrary
			highest = 0;
			for (President pres : presidents) {
				int beganYear = pres.getTermBegan().getDayOfYear();
				if (beganYear < lowest) {
					lowest = beganYear;
				}
				if (beganYear > highest) {
					highest = beganYear;
				}
			}
			//prompt for range
			startRange = getIntWithinRange("\t\tStart of range bewtween " + lowest + " - " + highest + ": ", lowest, highest);
			endRange = getIntWithinRange("\t\tEnd of range bewtween " + startRange + " - " + highest + ": ", startRange, highest);
			
			switch (choice2) {
			case 1:
				System.out.println("=== FILTERED BY TERM BEGAN BETWEEN " + startRange + "-" + endRange + " ===");
				//Filtering 4.v. Terms range
				printPresidents(filter(startRange + "", new PresidentMatcher() {
					@Override
					public boolean matches(President pres, String string) {
						return ( (pres.getTermBegan().getYear() > Integer.parseInt(string))
								&& (pres.getTermBegan().getYear() < endRange));
					}
				}));
				break;
			case 2:
				System.out.println("=== FILTERED BY TERM ENDED BETWEEN " + startRange + "-" + endRange + " ===");
				//Filtering 4.v. Terms range
				printPresidents(filter(startRange + "", new PresidentMatcher() {
					@Override
					public boolean matches(President pres, String string) {
						return ( (pres.getTermBegan().getYear() > Integer.parseInt(string))
								&& (pres.getTermBegan().getYear() < endRange));
					}
				}));
				break;
			} //end sub-menu
			break; //break from main switch
		case 5:
			System.out.println("\t=== FILTER BY NUMBER OF ELECTIONS WON ===");
			highest = 0; //arbitrary
			lowest = 999;
			
			for (President pres : presidents) {
				int elections = pres.getElectionsWon();
				if (elections > highest) {
					highest = elections;
				}
				if (elections < lowest) {
					lowest = elections;
				}
			}
			
			choice2 = getIntWithinRange("\tElections won ( " + lowest + "-" + highest + ": ", lowest, highest);
			// Filtering 4.iv. Won number of elections
			System.out.println("====== FILTERED NUMBER OF ELECTIONS WON = " + choice2 + " ======");
			printPresidents(filter(choice2 + "", new PresidentMatcher() { //first argument must be string
				@Override
				public boolean matches(President pres, String string) {
					return (pres.getElectionsWon() + "").equalsIgnoreCase(string);
				}
			}));
			break;
		case 6:
			System.out.println("\t=== FILTER BY WHY THEY LEFT OFFICE ===");
			
			// create set of unique first names
			set.clear();
			list.clear();
			for (President pres : presidents) {
				set.add(pres.getWhyLeftOffice().toLowerCase());
			}
			// use list to use indexes and naturally sort alphabetically
			list.addAll(set);
			Collections.sort(list);
			//print all options and prompt
			for (int i = 0; i < list.size(); i++) {
				//empty
				if (list.get(i).length() == 0) {
					System.out.println("\t" + (i + 1) + ": no reason in list");
				} else {
					//not empty
					System.out.println("\t" + (i + 1) + ": " + list.get(i));
				}
			}
			choice3 = getIntWithinRange("\tYour choice: ", 1, list.size());
			text = list.get(choice3 - 1);
			
			//Filtering 4.iii. Reason left office
			if (text.length() == 0) {
				System.out.println("====== FILTERED BY REASON LEFT OFFICE NOT IN LIST ======");
			} else {
				//not empty
				System.out.println("====== FILTERED BY LEFT OFFICE = " + text.toUpperCase() + " ======");
			}
			printPresidents(filter(text, new PresidentMatcher() {
				@Override
				public boolean matches(President pres, String string) {
					//empty
					if (string.length() == 0) {
						return pres.getWhyLeftOffice().length() == 0;
					} else {
						//not empty
						return (pres.getWhyLeftOffice().equalsIgnoreCase(string));
					}
				}
			}));
			break;
		case 7:
			break; //not necessary, last case
		}// end main switch
		
		//return to  main menu when done
		this.mainMenu();
	}

	private void quit() {
		System.out.println("--- GOODBYE ---");
		kb.close();
		System.exit(1);
	}

	public List<President> getPresidents() {
		return this.presidents;
	}

	// SORTING 1:
	// Member Class
	private class sortByPartyAndTerm implements Comparator<President> {

		@Override
		public int compare(President p0, President p1) {
			int result = p0.getParty().compareTo(p1.getParty());
			if (result == 0) {
				result = p0.getTermNumber() - p1.getTermNumber();
			}
			return result;
		}
	}

	// SORTING 2:
	// Local Class
	public List<President> sortByReasonLeftOffice() {
		List<President> copy = new ArrayList<>(presidents);

		class sortByReasonLeftOffice implements Comparator<President> {

			@Override
			public int compare(President p0, President p1) {
				int result = p0.getWhyLeftOffice().compareTo(p1.getWhyLeftOffice());
				if (result == 0) {
					result = p0.getTermNumber() - p1.getTermNumber();
				}
				return result;
			}
		}

		copy.sort(new sortByReasonLeftOffice());
		return copy;
	}

	// SORTING 3:
	// Anonymous Class
	public List<President> sortByLastName() {
		List<President> copy = new ArrayList<>(presidents);

		Comparator<President> comp = new Comparator<President>() {

			public int compare(President p0, President p1) {
				int result = p0.getLastName().compareTo(p1.getLastName());
				if (result == 0) {
					result = p0.getTermNumber() - p1.getTermNumber();
				}
				return result;
			}
		};
		Collections.sort(copy, comp);
		return copy;
	}

	// FILTER 2:
	private class PresidentFirstNameMatcher implements PresidentMatcher {

		@Override
		public boolean matches(President pres, String string) {
			return pres.getFirstName().equalsIgnoreCase(string);
		}
	}

	public List<President> sortByPartyAndTerm() {
		List<President> copy = new ArrayList<>(presidents);

		copy.sort(new sortByPartyAndTerm());
		return copy;
	}

	public void printPresidents(List<President> pres) {
		System.out.println(); //line break
		for (President p : pres) {
			System.out.println(p);
		}

//		// return to mainMenu
		this.mainMenu();
	}

	public List<President> filter(String string, PresidentMatcher matcher) {
		List<President> filtered = new ArrayList<>();
		for (President p : presidents) {
			if (matcher.matches(p, string)) {
				filtered.add(p);
			}
		}
		return filtered;
	}

	private void enterList() {
		boolean keepAdding = true; // false when done
		List<President> inputList = new ArrayList<>();
		int termNumber;
		String firstName;
		String middleName;
		String lastName;
		LocalDate termBegan;
		LocalDate termEnded;
		int electionsWon;
		String whyLeftOffice;
		String party;

		do {
			// label
			System.out.println(); // line break
			System.out.println("----- New President #" + (inputList.size() + 1) + " -----");
			// prompts
			termNumber = getIntWithinRange("termNumber: ", 1, MAX_TERM_NUMBER);
			System.out.print("firstName: ");
			firstName = kb.next();
			System.out.print("middleName: ");
			middleName = kb.next();
			System.out.print("lastName: ");
			lastName = kb.next();
			termBegan = getDate("termBegin YYYY-MM-DD: ", true);
			termEnded = getDate("termEnded YYYY-MM-DD or null if still in office: ", false);
			electionsWon = getIntWithinRange("electionsWon: ", 1, MAX_TERM_NUMBER);
			System.out.print("whyLeftOffice: ");
			whyLeftOffice = kb.next();
			System.out.print("party: ");
			party = kb.next();
			System.out.println(); // line break

			President pres = new President(termNumber, firstName, middleName, lastName, termBegan, termEnded,
					electionsWon, whyLeftOffice, party);
			inputList.add(pres);

			int choice = getIntWithinRange("Add another?\t1. Yes 2. No.\tYour choice: ", 1, 2);

		} while (keepAdding);

		// TODO: Prompt for file name and save

		this.saveToFile(inputList);

		// print
		this.printPresidents(inputList);
	}

	private int getInt(String prompt) {
		String inputString = "";
		int output;

		do {
			System.out.print(prompt);
			inputString = kb.next();

			// if invalid
			if (!stringIsInt(inputString)) {
				// print message and repeat
				System.out.println("INVALID INPUT. MUST BE INTEGER");
			}
		} while (!stringIsInt(inputString));

		output = Integer.parseInt(inputString);

		return output;
	}
	
	private int getIntWithinRange(String prompt, int min, int max) {
		String inputString = "";
		int output = -9999999; //erroneous if ever returned
		boolean withinRange = false;

		do {
			System.out.print(prompt + " (" + min + "-" + max + "): ");
			inputString = kb.next();

			// if not an integer
			if (!stringIsInt(inputString)) {
				// print message and repeat
				System.out.println("INVALID INPUT. MUST BE INTEGER BETWEEN" + min + " AND " + max);
			} else {
				//set to Integer and check if within range
				output = Integer.parseInt(inputString);
				
				if (output >= min && output <= max) {
					withinRange = true;
				}
			}
		} while (!stringIsInt(inputString) || !withinRange);
		
		return output;
	}
	
	private boolean stringIsInt(String string) {
		boolean isInt = true;

		// if empty string
		if (string.length() == 0) {
			isInt = false;
		}

		// check each character for number
		for (int i = 0; i < string.length(); i++) {
			// if not number, break and return false
			if (string.charAt(i) < '0' || string.charAt(i) > '9') {
				isInt = false;
				break;
			}
		}

		return isInt;
	}

	private LocalDate getDate(String prompt, boolean isTermStart) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		LocalDate output = null;

		while (output == null) {
			System.out.print(prompt);
			String inputString = kb.next();

			// skip if "null"
			if (!inputString.equals("null")) {

				boolean valid = true; // conditions for valid input

				String[] split = inputString.split("-");
				String year = split[0];
				String month = split[1];
				String day = split[2];

				// must have 3 parts (year-month-date)
				if (split.length != 3) {
					valid = false;
				}

				// each part must be int
				for (String part : split) {
					if (!stringIsInt(part)) {
						valid = false;
						break;
					}
				}

				if (valid) {
					int yearInt = Integer.parseInt(year);
					int monthInt = Integer.parseInt(month);
					int dayInt = Integer.parseInt(day);
					// verify via additional method
					valid = verifyDate(yearInt, monthInt, dayInt);
				}

				// parse only if valid
				if (valid) {
					output = LocalDate.parse(inputString, formatter);
				} else {
					System.out.println("INVALID INPUT. MUST BE YYYY-MM-DD");
				}
			} else {
				break; // end while
			}

			// will repeat if anything considered invalid
		}

		// allow for "null" as termEnded to return null object
		if (!isTermStart && prompt.equals("null")) {
			output = null;
		}

		return output;
	}

	private boolean verifyDate(int year, int month, int day) {

		boolean valid = true;

		// year out of range (also handles 2-digit years)
		if (year < minYear || year > maxYear) {
			valid = false;
		}

		// month out of range
		if (month < 1 || month > 12) {
			valid = false;
		}

		// day out of range for year and month
		if (valid) {
			YearMonth monthObject = YearMonth.of(year, month);
			int daysInMonth = monthObject.lengthOfMonth();
			if (day < 1 || day > daysInMonth) {
				valid = false;
			}
		}

		return valid;
	}

	private void saveToFile(List<President> list) {
		// get fileName and prevent overwriting RESTRICTED_FILENAME
		String fileName = "";
		do {
			System.out.print("Enter a filename (without an extension): ");
			fileName = kb.next();
			fileName += ".tsv";

			if (fileName.equals(RESTRICTED_FILENAME)) {
				System.err.println("ERROR: " + RESTRICTED_FILENAME + " is restricted");
			}
		} while (fileName.equals("jetdata.txt"));

		try {
			File myObj = new File(fileName);

			// true enables appending data on new lines
			FileWriter myWriter = new FileWriter(fileName, true);

			// clear if file already exists
			if (myObj.exists() && myObj.isFile()) {
				// false disables appending data and will clear file
				FileWriter tempWriter = new FileWriter(fileName, false);
				tempWriter.write("");
				tempWriter.close();
			}

			// write to file
			int index = 1;
			for (President pres : list) {
				myWriter.write(formatForSaving(pres));
				if (index != list.size()) {
					myWriter.write("\n");
					index++;
				}
			}
			myWriter.close();

			System.out.println("--- Saved to " + fileName);

		} catch (IOException e) {
			System.err.println("PresidentAppDates.saveToFile(): An error occured.");
			e.printStackTrace();
		}
	}

	private String formatForSaving(President pres) {
		String output = "";

		// example
		// 13 Millard Fillmore July 9, 1850 March 4, 1853 0 Did not seek reelection Whig
		output += pres.getTermNumber();
		output += "\t";
		output += pres.getFirstName();
		output += "\t";
		output += pres.getMiddleName();
		output += "\t";
		output += pres.getLastName();
		output += "\t";
		output += formatDateForSaving(pres.getTermBegan());
		output += "\t";
		output += formatDateForSaving(pres.getTermEnded());
		output += "\t";
		output += pres.getElectionsWon();
		output += "\t";
		output += pres.getWhyLeftOffice();
		output += "\t";
		output += pres.getParty();

		return output;
	}

	private String formatDateForSaving(LocalDate date) {
		String output = "";
		// if null, leave as null
		if (date != null) {
			// format month as Title case
			String month = date.getMonth().toString().toUpperCase(); // upper case by default
			char first = month.charAt(0);
			String rest = month.substring(1, month.length()).toLowerCase();
			output += first + rest;
			output += " ";
			output += date.getDayOfMonth();
			output += ", ";
			output += date.getYear();
		}

		return output;
	}

	private void loadPresidents(String fileName) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

		// File format (tab-separated):
		// # First Middle Last Inaugurated Left office Elections won Reason left office
		// Party
		// 1 George Washington July 1, 1789 March 4, 1797 2 Did not seek re-election
		// Independent
		// 3 Thomas Jefferson March 4, 1801 March 4, 1809 2 Did not seek reelection
		// Democratic-Republican
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String record = reader.readLine(); // Read and discard header line
			while ((record = reader.readLine()) != null) {
				String[] col = record.split("\\t");
				// SAMPLE FROM FIRST ENTRY
				int term = Integer.parseInt(col[0]); // 1 (index of all president terms from 1+)
				String fName = col[1]; // George
				String mName = col[2]; // empty
				String lName = col[3]; // Washington
				LocalDate tBegan = LocalDate.parse(col[4], formatter); // July 1, 1789
				// allow for empty string
				LocalDate tEnd = null;
				if (col[5].length() != 0) {
					tEnd = LocalDate.parse(col[5], formatter);
				}
				int won = Integer.parseInt(col[6]); // 2 (how many times they won, not necessarily same as number of
													// terms)
				String whyLeft = col[7]; // Did not seek re-election
				String party = col[8]; // Independent

				President pres = new President(term, fName, mName, lName, tBegan, tEnd, won, whyLeft, party);
				presidents.add(pres);

				// remove header?
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
	}

}
