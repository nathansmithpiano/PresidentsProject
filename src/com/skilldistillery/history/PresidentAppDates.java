package com.skilldistillery.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PresidentAppDates {
	private static final String fileName = "resources" + File.separator + "presidents.tsv";
	private List<President> presidents = new ArrayList<>();
	Scanner kb = new Scanner(System.in);

	// input options
	private int minYear = 1770;
	private int maxYear = 2030;
	private final String RESTRICTED_FILENAME = "presidents.tsv";

	public PresidentAppDates() {
		this.loadPresidents(fileName);
	}

	public static void main(String[] args) {
		PresidentAppDates app = new PresidentAppDates();
		app.start();
	}

	public void start() {
		mainMenu();
	}

	public void mainMenu() {
		System.out.println("1. Print from presidents.tsv");
		System.out.println("2. Enter your own list");
		System.out.println("3. Quit");
		String inputString = "";
		do {
			System.out.print("Your choice: ");
			inputString = kb.next();
		} while (!inputString.equals("1") && !inputString.equals("2") && !inputString.equals("3"));

		switch (inputString) {
		case "1":
			// print presidents.tsv
			// TODO: ask, why this?
			this.printPresidents(this.getPresidents());
			System.out.println("--- COMPLETE ---\n");
			mainMenu();
			break; // this seems unnecessary
		case "2":
			// enter
			this.enterList();

		case "3":
			// quit
			this.quit();
		}

	}

	private void quit() {
		System.out.println("--- GOODBYE ---");
		kb.close();
		System.exit(1);
	}

	public List<President> getPresidents() {
		return this.presidents;
	}

	public void printPresidents(List<President> pres) {
		for (President p : pres) {
			System.out.println(p);
		}

		// return to mainMenu
		System.out.println(); // line break
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
			termNumber = getInt("termNumber: ");
			System.out.print("firstName: ");
			firstName = kb.next();
			System.out.print("middleName: ");
			middleName = kb.next();
			System.out.print("lastName: ");
			lastName = kb.next();
			termBegan = getDate("termBegin YYYY-MM-DD: ", true);
			termEnded = getDate("termEnded YYYY-MM-DD or null if still in office: ", false);
			electionsWon = getInt("electionsWon: ");
			System.out.print("whyLeftOffice: ");
			whyLeftOffice = kb.next();
			System.out.print("party: ");
			party = kb.next();
			System.out.println(); // line break

			President pres = new President(termNumber, firstName, middleName, lastName, termBegan, termEnded,
					electionsWon, whyLeftOffice, party);
			inputList.add(pres);

			int choice = 0;
			while (choice != 1 && choice != 2) {
				choice = getInt("Add another?\t1. Yes 2. No.\tYour choice:");
			}

			if (choice == 2) {
				keepAdding = false;
				break;
			}

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
			// format month as Proper Noun
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
