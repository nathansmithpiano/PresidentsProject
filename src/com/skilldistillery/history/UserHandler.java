package com.skilldistillery.history;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserHandler {

	private Scanner kb;
	private FileHandler file;
	private DataHandler data;

	public UserHandler() {
		kb = new Scanner(System.in);
		file = new FileHandler(kb);
		data = new DataHandler();
	}

	public void mainMenu() {
		System.out.println();
		System.out.println("        .-'~\"-. 	==========================");
		System.out.println("       / `-    \\ 	=== PRESIDENTS MANAGER ===");
		System.out.println("      />  `.  -.| 	======  MAIN MENU ========");
		System.out.println("     /_     '-.__) 	  1. Load List from File");
		System.out.println("      |-  _.' \\ |     	  2. Add to List / Create New List");
		System.out.println("      `~~;     \\\\    	  3. Manage Current List");
		System.out.println("         /      \\\\)  	  4. Delete File(s)");
		System.out.println("   jgs  '.___.-'`\"	  5. Quit");
		int choice = getIntWithinRange("\t\t\t   Your choice: ", 1, 5, true);

		switch (choice) {
		case 1:
			System.out.println("\n===== LOAD LIST FROM FILE =====");
			String fileName = this.chooseFile();
			if (data.getNumPresidents() != 0) {
				System.out.println("\n=== WARNING: This will replace your current list.");
				System.out.println("1. Add entries from file to current list");
				System.out.println("2. Replace current list");
				int fChoice = this.getIntWithinRange(1, 2);
				switch (fChoice) {
				case 1:
					data.addToList(file.loadFile(fileName));
					System.out.println("\n=== Entries added to current list ===");
					this.manage();
					break;
				case 2:
					data.loadPresidents(file.loadFile(fileName));
					System.out.println("\n=== Current list replaced with file ===");
					this.manage();
					break;
				} //end switch
			} else {
				//no current list
				data.loadPresidents(file.loadFile(fileName));
				System.out.println("\n=== List loaded from file ===");
			}
			break;
		case 2:
			this.enterList();
			break;
		case 3:
			if (!data.isFileLoaded()) {
				System.out.println("\n=== No file loaded! Please load first. ===");
				mainMenu();
				break; // unnecessary
			} else {
				this.manage();
			}
			break;
		case 4:
			this.delete();
			break;
		case 5:
			this.close();
			System.out.println("\n===== GOODBYE =====");
			System.exit(0);
		}

		// all options return to main menu
		this.mainMenu();
	}

	private String chooseFile() {
		String[] files = file.getFileList();
		int choice;
		int numFiles = files.length;
		if (files.length == 0) {
			System.out.println("ERROR: could not find any files in " + SETTINGS.FILE_PATH);
			choice = -1;
		} else {
			for (int i = 0; i < files.length; i++) {
				System.out.println((i + 1) + ". " + files[i]);
			}
			choice = getIntWithinRange(1, numFiles, false);
		}

		return files[choice - 1];
	}

	private void manage() {
		int numEntries = data.getNumPresidents();
		System.out.println("\n===== MANAGE LIST =====");
		System.out.println("1. View list (" + numEntries + " entries)");
		System.out.println("2. Add to list");
		System.out.println("3. Sort and/or Filter");
		System.out.println("4. Save list to file");
		int choice = getIntWithinRange(1, 4);

		switch (choice) {
		case 1:
			this.printList();
			break;
		case 2:
			this.enterList();
			break;
		case 3:
			this.filter();
			break;
		case 4:
			this.save();
			break;
		}
		
		//return
		this.manage();
	}

	private void filter() {
		System.out.println("\n===== SORT AND/OR FILTER =====");
		System.out.println("1. View list (" + data.getNumPresidents() + " entries)");
		System.out.println("2. Manage Filter(s)");
		System.out.println("3. Manage Sort(s)");
		System.out.println("4. Reset back to original file");
		System.out.println("5. Return to previous menu");
		int choice = getIntWithinRange(1, 5);
		
		//return
		if (choice == 5) {
			this.manage();
		}

		switch (choice) {
		case 1:
			this.printList();
			break;
		case 2:
			this.manageFilters();
		case 3:
			System.out.println(" Manage Sorts - NOT YET IMPLEMENTED");
			break;
		case 4:
			System.out.println("=== WARNING: THIS WILL RESET BACK TO ORIGINAL STATE ===");
			System.out.println("Are you sure?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			int confirm = getIntWithinRange(1, 2, true);

			switch (confirm) {
			case 1:
				data.resetFilters();
				System.out.println("\n=== LIST HAS BEEN RESET === ");
				break;
			case 2:
				System.out.println("\n=== LIST HAS NOT BEEN MOFIDIED === ");
				break;
			} // end confirm switch
			break; //end case 4
		case 5:
			this.manage();
			break; //unnecessary
		} // end choice switch
		
		//return
		this.filter();
	}
	
	private void save() {
		System.out.println("\n===== SAVE LIST TO FILE =====");
		file.saveToFile(data.getList());
		this.manage();
	}
	
	private void save(List<President> list) {
		System.out.println("\n===== SAVE LIST TO FILE =====");
		file.saveToFile(list);
		if (data.getNumPresidents() != 0) {
			System.out.println("\n=== CURRENT LIST STILL ACTIVE ===");
			System.out.println("=== TO WORK WITH THIS LIST, LOAD FILE FROM MAIN MENU ===");
			this.mainMenu();
		} else {
			data.loadPresidents(list);
			System.out.println("\n=== LIST LOADED ===");
			this.manage();
		}
	}
	
	private void delete() {
		System.out.println("\n===== DELETE FILE =====");
		file.deleteFile(this.chooseFile());
		this.mainMenu();
	}

	private void printList() {
		System.out.println("\n===== VIEW LIST =====");
		data.view();
	}

	private void manageFilters() {
		System.out.println("\n===== MANAGE FILTERS =====");
		System.out.println("1. View list " + data.getNumPresidents() + " entries)");
		System.out.println("2. View Filters");
		System.out.println("3. Reapply Filters (i.e. after adding to list or changing list)");
		System.out.println("4. Add Filter");
		System.out.println("5. Reorder Filters");
		System.out.println("6. Remove Filter");
		System.out.println("7. Return to previous menu");
		int choice = getIntWithinRange(1, 7);

		switch (choice) {
		case 1:
			this.printList();
			break;
		case 2:
			//view filters
			if (data.getNumActiveFilters() == 0) {
				System.out.println("=== no filters active ===");
			} else {
				System.out.println("\n=== VIEWING FILTERS ===");
				this.printList(data.getFilterList());
			}
			break;
		case 3:
			//view filters
			if (data.getNumActiveFilters() == 0) {
				System.out.println("=== no filters active ===");
			} else {
				System.out.println("\n=== VIEWING FILTERS ===");
				this.printList(data.getFilterList());
				System.out.println("Previous list: " + data.getNumPresidents() + " entries");
				data.applyFilters();
				System.out.println("After applying filters, " + data.getNumPresidents() + " entries");
			}
			
			break;
		case 4:
			//add filter
			this.addFilter();
			break;
		case 5:
			//reorder
			System.out.println("Reorder Filters not yet implemented");
			this.manageFilters();
			break;
		case 6:
			//remove filters
			if (data.getNumActiveFilters() == 0) {
				System.out.println("=== no filters active ===");
			} else {
				this.deleteFilter();
			}
			break;
		case 7:
			//return up
			this.filter();
			break; //unnecessary
		} //end switch

		//repeat
		this.manageFilters();
	}
	
	private void printList(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + ": " + list.get(i));
		}
	}
	
	private void addFilter() {
		System.out.println("\n=== ADD FILTER ===");
		List<String> filterNames = data.getFilterNames();
		this.printList(filterNames);
		System.out.println(filterNames.size() + 1 + ": Return to previous menu");
		int choice = this.getIntWithinRange(1, filterNames.size() + 1);
		
		//return to previous menu
		if (choice == filterNames.size() + 1) {
			this.manageFilters();
		} else {
			//proceed
			String name = filterNames.get(choice - 1);
			String type = data.getInputType(name);
			
			if (type.equals("string") ) {
				System.out.print("\n-> ADD FILTER " + name + ": ");
				data.addFilter(name, kb.next());
			}
			
			System.out.println("=== FILTER ADDED ===");
		}
		
	}
	
	private void deleteFilter() {
		System.out.println("\n=== REMOVE FILTER ===");
		List<String> filters = data.getFilterList();
		this.printList(filters);
		System.out.println(filters.size() + 1 + ": Return to previous menu");
		int mChoice = getIntWithinRange(1, filters.size() + 1, true);
		
		//return to previous menu
		if (mChoice == filters.size() + 1) {
			this.manageFilters();
		} else {
			//proceed
			System.out.println("CONFIRM REMOVE FILTER: " + filters.get(mChoice - 1));
			System.out.println("1. Delete");
			System.out.println("2. Cancel");
			int dChoice = getIntWithinRange(1, 2, true);
			
			if (dChoice == 1) {
				data.removeFilter(filters.get(mChoice - 1));
				System.out.println("=== FILTER REMOVED ===");
			} 
		}
		
		//go to previous menu if empty
		if (filters.size() == 0) {
			this.manageFilters();
		}
		
		//repeat
		this.deleteFilter();
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
			termNumber = getIntWithinRange("termNumber:", 1, SETTINGS.MAX_TERM_NUMBER, true);
			System.out.print("firstName: ");
			firstName = kb.next();
			System.out.print("middleName: ");
			middleName = kb.next();
			System.out.print("lastName: ");
			lastName = kb.next();
			termBegan = getDate("termBegin YYYY-MM-DD: ", true);
			termEnded = getDate("termEnded YYYY-MM-DD or null if still in office: ", false);
			electionsWon = getIntWithinRange("electionsWon: ", 1, SETTINGS.MAX_TERM_NUMBER, true);
			System.out.print("whyLeftOffice: ");
			whyLeftOffice = kb.next();
			System.out.print("party: ");
			party = kb.next();
			System.out.println(); // line break

			President pres = new President(termNumber, firstName, middleName, lastName, termBegan, termEnded,
					electionsWon, whyLeftOffice, party);
			inputList.add(pres);
			System.out.println("=== Add another? ===");
			System.out.println("1. Yes");
			System.out.println("2. No.");
			int choice = getIntWithinRange(1, 2, true);
			if (choice == 2) {
				keepAdding = false;
			}
		} while (keepAdding);
		
		if (inputList.size() != 0) {
			afterAddingList(inputList);
		} else {
			System.out.println("\n=== No entries in new list. ===");
			this.mainMenu();
		}
		
	}
	
	private void afterAddingList(List<President> inputList) {
		System.out.println("\n=== NEW LIST HAS " + inputList.size() + " ENTRIES ===");
		System.out.println("What would you like to do next?");
		System.out.println("1. Save list to file");
		System.out.println("2. Load and manage list without saving to file");
		System.out.println("3. Add entries to current list");
		System.out.println("4. Discard list");
		int choice = getIntWithinRange(1, 4);
		switch (choice) {
		case 1:
			this.save(inputList);
			break;
		case 2:
			data.loadPresidents(inputList);
			this.manage();
			break;
		case 3:
			if (data.getNumPresidents() != 0) {
				data.addToList(inputList);
				this.manage();
			} else {
				data.loadPresidents(inputList);
				this.manage();
			}
			break;
		case 4:
			System.out.println("\n=== WARNING: ALL ENTRIES WILL BE DISCARDED ===");
			System.out.println("Are you sure?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			int confirm = getIntWithinRange(1, 2, true);

			switch (confirm) {
			case 1:
				System.out.println("\n=== ENTRIES DISCARDED === ");
				this.mainMenu();
				break;
			case 2:
				System.out.println("\n=== LIST HAS NOT BEEN MOFIDIED === ");
				//return to options
				this.afterAddingList(inputList);
				break;
			} // end confirm switch
		}
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
		if (year < SETTINGS.MIN_YEAR || year > SETTINGS.MAX_YEAR) {
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

	private int getIntWithinRange(int min, int max) {
		return getIntWithinRange(min, max, false);
	}

	// hide prompt if true
	private int getIntWithinRange(int min, int max, boolean hideReturn) {
		String defaultPrompt = "-> Your choice";
		return getIntWithinRange(defaultPrompt, min, max, hideReturn);
	}

	// do not hide return choice by default
	private int getIntWithinRange(String prompt, int min, int max) {
		return getIntWithinRange(prompt, min, max, false);
	}

	// overloaded constructor to allow for custom prompt
	private int getIntWithinRange(String prompt, int min, int max, boolean hideReturn) {
		String inputString = "";
		int output = -9999999; // erroneous if ever returned
		boolean withinRange = false;

		// Additional option to quit and return to main menu
		if (!hideReturn) {
			System.out.println((max + 1) + ". Return to main menu");
			max += 1;
		}

		do {
			// different helper for only 1 option
			if (min != max) {
				System.out.print(prompt + " (" + min + "-" + max + "): ");
			} else {
				System.out.print(prompt + ": ");
			}
			inputString = kb.next();

			// if not an integer
			if (!stringIsInt(inputString)) {
				// print message and repeat
				System.out.println("INVALID INPUT. MUST BE INTEGER BETWEEN" + min + " AND " + max);
			} else {
				// set to Integer and check if within range
				output = Integer.parseInt(inputString);

				if (output >= min && output <= (max)) {
					withinRange = true;
				}
			}
		} while (!stringIsInt(inputString) || !withinRange);

		if (!hideReturn && output == max) {
			this.mainMenu();
		}

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

	private void close() {
		kb.close();
	}
}
