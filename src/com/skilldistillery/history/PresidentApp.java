package com.skilldistillery.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PresidentApp {
  private static final String fileName = "resources" + File.separator + "presidents.tsv";
  private List<President> presidents = new ArrayList<>();

  public static void main(String[] args) {
    PresidentApp app = new PresidentApp();
    app.start();
  }
  
  public void start() {
    this.printPresidents(this.getPresidents());
    
    
  }

  public PresidentApp() {
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

  private void loadPresidents(String fileName) {
    // File format (tab-separated):
    //# First Middle  Last  Inaugurated Left office Elections won Reason left office  Party
    //1 George    Washington  July 1, 1789  March 4, 1797 2 Did not seek re-election  Independent
	//3	Thomas		Jefferson	March 4, 1801	March 4, 1809	2	Did not seek reelection	Democratic-Republican
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String record = reader.readLine(); // Read and discard header line
      while ((record = reader.readLine()) != null) {
        String[] col = record.split("\\t");
        										//SAMPLE FROM FIRST ENTRY
        int term = Integer.parseInt(col[0]); 	//1 (index of all president terms from 1+)
        String fName = col[1]; 					//George
        String mName = col[2]; 					//empty
        String lName = col[3];					//Washington
        // col[4]: Date term began.				//July 1, 1789
        // col[5]: Date term ended.				//March 4, 1797 (empty if current president)
        int won = Integer.parseInt(col[6]);		//2 (how many times they won, not necessarily same as number of terms)
        String whyLeft = col[7];				//Did not seek re-election
        String party = col[8];					//Independent

        //TODO: Implement with additional fields
//        President pres = new President(term, fName, mName, lName, won, whyLeft, party);
//        presidents.add(pres);
        
        //remove header?
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
      System.exit(1);
    }
  }

}


