package com.skilldistillery.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InnerClass_Example {
	private static final String fileName = "resources" + File.separator + "presidents.tsv";
	private List<President> presidents = new ArrayList<>();

	public static void main(String[] args) {
		InnerClass_Example app = new InnerClass_Example();
		app.start();
	}

	public void start() {
//		this.printPresidents(this.getPresidents());
		
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
		
//		Filtering 4, anonymous class
//		i. Last names start with "C"
//		printPresidents(filter("C", new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return pres.getFirstName().substring(0,1).equalsIgnoreCase(string);
//			}
//		}));
//		ii. Party contains the string "Democrat".
//		printPresidents(filter("Democrat", new PresidentPartyMatcher()));
//		iii. Died in office or Assassinated (my choice)
//		printPresidents(filter("Died in office", new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return (pres.getWhyLeftOffice().equalsIgnoreCase(string)
//						|| pres.getWhyLeftOffice().equalsIgnoreCase("Assassinated"));
//			}
//		}));
//		iv. Won only a single election
//		printPresidents(filter("1", new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return (pres.getElectionsWon() + "").equalsIgnoreCase(string);
//			}
//		}));
//		v. Terms started in the 19th century
//		printPresidents(filter("1800", new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return ( (pres.getTermBegan().getYear() > Integer.parseInt(string))
//						&& (pres.getTermBegan().getYear() < Integer.parseInt(string) + 99));
//			}
//		}));

	}

	public InnerClass_Example() {
		this.loadPresidents(fileName);
	}

	public List<President> getPresidents() {
		return this.presidents;
	}

	public void printPresidents(List<President> pres) {
		for (President p : pres) {
			System.out.println(p);
		}
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

	public List<President> sortByPartyAndTerm() {
		List<President> copy = new ArrayList<>(presidents);

		copy.sort(new sortByPartyAndTerm());
		return copy;
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
	
	private void loadPresidents(String fileName) {
		// File format (tab-separated):
		// # First Middle Last Inaugurated Left office Elections won Reason left office
		// Party
		// 1 George Washington July 1, 1789 March 4, 1797 2 Did not seek re-election
		// Independent
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String record = reader.readLine(); // Read and discard header line
			while ((record = reader.readLine()) != null) {
				String[] col = record.split("\\t");

				int term = Integer.parseInt(col[0]);
				String fName = col[1];
				String mName = col[2];
				String lName = col[3];
				// col[4]: Date term began.
				// col[5]: Date term ended.
				int won = Integer.parseInt(col[6]);
				String whyLeft = col[7];
				String party = col[8];

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM d, yyyy");

				LocalDate termBegin = LocalDate.parse(col[4], dtf);
				LocalDate termEnd = col[5].isEmpty() ? LocalDate.now() : LocalDate.parse(col[5], dtf);

				President pres = new President(term, fName, mName, lName, termBegin, termEnd, won, whyLeft, party);
				presidents.add(pres);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
	}

}