package com.skilldistillery.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiPredicate;

public class PresidentAppLambda {
	
	private Scanner kb;
	private UserHandler user;
	private FileHandler file;
	private List<President> presidents;
	
	public PresidentAppLambda() {
		kb = new Scanner(System.in);
		file = new FileHandler(kb);
		user = new UserHandler();
//		presidents = file.loadFile(SETTINGS.LOAD_DIR + SETTINGS.RESTRICTED_FILENAME);
	}

	public static void main(String[] args) {
		PresidentAppLambda app = new PresidentAppLambda();
		app.start();
	}

	private void start() {
		user.mainMenu();
//		System.out.println("====== ===== WELCOME TO PRESIDENT MANAGER ===== =====");
//		mainMenu();

		// initial print
//		this.printPresidents(this.getPresidents());;

//		LAMBDA Sorting 1, anonymous
//		System.out.println("====== Sorting by Party, then by Term ======");
//		printPresidents(sortByPartyAndTerm());

//		LAMBDA Sorting 2, anonymous
//		System.out.println("====== Sorting by Reason Left Office, then by Term ======");
//		printPresidents(sortByReasonLeftOffice());

//		LAMBDA Sorting 3, anonymous
//		System.out.println("====== Sorting by Last Name, then by Term ======");
//		printPresidents(sortByLastName());

//		LAMBDA Filtering 1
//		System.out.println("====== Filter by party = Whig ======");
//		printPresidents(filter("Whig", (pres, string) -> {
//			return pres.getParty().equalsIgnoreCase(string);
//		}));

//		LAMBDA Filtering 2
//		System.out.println("====== Filter by firstName = James ======");
//		printPresidents(filter("James", (pres, string) -> {
//			return pres.getFirstName().equalsIgnoreCase(string);
//		}));
		
//		LAMBDA Filtering 3
//		System.out.println("====== Filter by getWhyLeftOffice() = Term ended ======");
//		printPresidents(filter("Term ended", (pres, string) -> {
//			return pres.getWhyLeftOffice().equalsIgnoreCase(string);
//		}));
		
//		LAMBDA Filtering 4.i.
//		System.out.println("====== Filter by First Name Starts with C ======");
//		printPresidents(filter("C", (pres, string) -> {
//			return pres.getFirstName().toLowerCase().startsWith(string.toLowerCase());
//		}));
		
//		LAMBDA 4.ii. Party contains the string "Democrat".
//		System.out.println("====== Filter by Party = Democrat ======");
//		printPresidents(filter("Democrat", (pres, string) -> {
//			return pres.getParty().equalsIgnoreCase(string);
//		}));

//		LAMBDA 4.iii. Died in office or Assassinated (my choice)
//		System.out.println("====== Filter by Died in Office or Assassinated ======");
//		printPresidents(filter("Died in office", (pres, string) -> {
//			return pres.getWhyLeftOffice().equalsIgnoreCase(string)
//					|| pres.getWhyLeftOffice().equalsIgnoreCase("Assassinated");
//		}));

//		LAMBDA 4.iv. Won only a single election
//		System.out.println("====== Filter by Won Only 1 Elections ======");
//		printPresidents(filter("1", (pres, string) -> {
//			return (pres.getElectionsWon() + "").equalsIgnoreCase(string);
//		}));

//		LAMBDA 4.v. Terms started in the 19th century
//		printPresidents(filter("1800", (pres, string) -> {
//				return ( (pres.getTermBegan().getYear() > Integer.parseInt(string))
//						&& (pres.getTermBegan().getYear() < Integer.parseInt(string) + 99));
//		}));

	}

	public List<President> getPresidents() {
		return this.presidents;
	}

	// SORTING 1:
	// LAMBDA Anonymous
	public List<President> sortByPartyAndTerm() {
		List<President> copy = new ArrayList<>(presidents);

		Comparator<President> comp = (p0, p1) -> {
			int result = p0.getParty().compareTo(p1.getParty());
			if (result == 0) {
				result = p0.getTermNumber() - p1.getTermNumber();
			}
			return result;
		};

		Collections.sort(copy, comp);
		return copy;
	}

	// SORTING 2:
	// LAMBDA Anonymous
	public List<President> sortByReasonLeftOffice() {
		List<President> copy = new ArrayList<>(presidents);

		Comparator<President> comp = (p0, p1) -> {
			int result = p0.getWhyLeftOffice().compareTo(p1.getWhyLeftOffice());
			if (result == 0) {
				result = p0.getTermNumber() - p1.getTermNumber();
			}
			return result;
		};

		Collections.sort(copy, comp);
		return copy;
	}

	// SORTING 3:
	// LAMBDA Anonymous
	public List<President> sortByLastName() {
		List<President> copy = new ArrayList<>(presidents);

		Comparator<President> comp = (p0, p1) -> {
			int result = p0.getLastName().compareTo(p1.getLastName());
			if (result == 0) {
				result = p0.getTermNumber() - p1.getTermNumber();
			}
			return result;
		};

		Collections.sort(copy, comp);
		return copy;
	}

	public void printPresidents(List<President> pres) {
//		System.out.println(); //line break
		for (President p : pres) {
			System.out.println(p);
		}

//		// return to mainMenu
//		this.mainMenu();
	}

	public List<President> filter(String string, BiPredicate<President, String> matcher){
		List<President> filtered = new ArrayList<>();
		
		for (President p : presidents) {
			if (matcher.test(p, string)) {
				filtered.add(p);
			}
		}
		
		return filtered;
	}
	
	// TODO: File Saving Stuff
	// FileHandler.saveToFile(inputHandler.enterList());


}
