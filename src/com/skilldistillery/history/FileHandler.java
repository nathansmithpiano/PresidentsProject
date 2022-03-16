package com.skilldistillery.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
	
	Scanner kb;

	public FileHandler(Scanner kb) {
		this.kb = kb;
	}
	
	public String[] getFileList() {
		String[] files;
		
		File f = new File(SETTINGS.FILE_PATH);
		files = f.list();
		
		return files;
	}
	
	public void deleteFile(String fileName) {
		
		if (fileName.equals(SETTINGS.RESTRICTED_FILENAME)) {
			System.out.println("\n=== FILE === " + SETTINGS.LOAD_DIR + SETTINGS.RESTRICTED_FILENAME + " CANNOT BE DELETED ===");
		} else {
			fileName = SETTINGS.LOAD_DIR + fileName;
			File myObj = new File(fileName);
			
			System.out.println("\n=== CONFIRM DELETE ===");
			System.out.println("1. Yes");
			System.out.println("2. No");
			int choice = 0;
			do {
				System.out.print("-> Your choice: ");
				choice = kb.nextInt();
			} while (choice != 1 && choice != 2);
			
			if (myObj.delete()) {
				System.out.println("\n=== File " + fileName + " successfully deleted ===");
			} else {
				System.out.println("=== ERROR: File was not deleted. ===");
			}
		}
	}
	
	public List<President> loadFile(String fileName) {
		List<President> presidents = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

		try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS.LOAD_DIR + fileName))) {
			String record = reader.readLine(); // Read and discard header line
			while ((record = reader.readLine()) != null) {
				String[] col = record.split("\\t");
				
				int term = Integer.parseInt(col[0]); // 1 (index of all president terms from 1+)
				String fName = col[1];
				String mName = col[2];
				String lName = col[3];
				
				//if no termEnd/termBegin from file, allow for null LocalDate objects 
				LocalDate tBegan;
				if (col[4].length() != 0) {
					tBegan = LocalDate.parse(col[4], formatter);
				} else {
					tBegan = null;
				}
				
				LocalDate tEnd;
				if (col[5].length() != 0) {
					tEnd = LocalDate.parse(col[5], formatter);
				} else {
					tEnd = null;
				}
				
				//prepare for no-entry of elections won to prevent throwing exceptions
				int won;
				if (col[6].length() != 0) {
					won = Integer.parseInt(col[6]);
				} else {
					won = 0;
				}
				String whyLeft = col[7]; // Did not seek re-election
				String party = col[8]; // Independent

				President pres = new President(term, fName, mName, lName, tBegan, tEnd, won, whyLeft, party);
				presidents.add(pres);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
		
		if (presidents.size() == 0) {
			System.out.println("Sorry, " + fileName + " could not load.  Please check formatting (TSV) and try again.");
		}
		
		return presidents;
	}

	public void saveToFile(List<President> list) {
		// get fileName and prevent overwriting RESTRICTED_FILENAME
		String fileName = "";
		do {
			System.out.print("Enter a filename (without an extension): ");
			fileName = kb.next();
			fileName += ".tsv";

			if (fileName.equals(SETTINGS.RESTRICTED_FILENAME)) {
				System.err.println("ERROR: " + SETTINGS.RESTRICTED_FILENAME + " is restricted");
			}
		} while (fileName.equals(SETTINGS.RESTRICTED_FILENAME));
		
		fileName = SETTINGS.LOAD_DIR + fileName;

		try {
			File myObj = new File(fileName);
			int choice = 0;
			//prompt for overwrite
			boolean exists = myObj.exists() && myObj.isFile();
			if (exists) {
				System.out.println("File " + fileName + " already exists. Overwrite?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				
				do {
					System.out.print("-> Your choice: ");
					choice = kb.nextInt();
				} while (choice != 1 && choice != 2);
			}
			
			//overwrite or skip
			if (choice == 1 || !exists) {
				// true enables appending data on new lines
				FileWriter myWriter = new FileWriter(fileName, true);
				
				// clear if file already exists
				if (myObj.exists() && myObj.isFile()) {
					// false disables appending data and will clear file
					FileWriter tempWriter = new FileWriter(fileName, false);
					tempWriter.write("");
					tempWriter.close();
				}
				
				
				//write header to match original file
				myWriter.write(SETTINGS.FILE_HEADER);
				
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
			} else {
				System.out.println("=== SAVE SKIPPED, ORIGINAL FILE REMAINS ===");
			}


		} catch (IOException e) {
			System.err.println("PresidentAppDates.saveToFile(): An error occured.");
			e.printStackTrace();
		}
	}
	
	private String formatForSaving(President pres) {
		String output = "";

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

}
