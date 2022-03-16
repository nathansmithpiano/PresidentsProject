package com.skilldistillery.history;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class DataHandler {
	
	private List<President> original;
	private List<President> modified;
	private List<Filter> filterList;
	private List<String> filterNames;
	
	public DataHandler() {
		original = new ArrayList<>();
		modified = new ArrayList<>();
		filterList = new ArrayList<>();
		filterNames = new ArrayList<>();
		populateAllFilters();
	}
	
	public void populateAllFilters() {
		filterNames.add("First Name");
		filterNames.add("Middle Name");
		filterNames.add("Last Name");
	}
	
	public boolean isFileLoaded() {
		return original.size() != 0;
	}
	
	public void addToList(List<President> newList) {
		modified.addAll(newList);
	}
	
	public void loadPresidents(List<President> newList) {
		original.clear();
		modified.clear();
		original.addAll(newList);
		modified.addAll(newList);
		
		if (original.size() != 0) {
			System.out.println("\n=== Successfully loaded " + original.size() + " Presidents ===");
		} else {
			System.err.println("ERROR: DataHandler.loadPresidents() - none loaded");
		}
	}
	
	public void view() {
		for (President p : modified) {
			System.out.println(p);
		}
	}
	
	public List<President> getList() {
		return modified;
	}
	
	public int getNumPresidents() {
		return modified.size();
	}
	
	public void resetFilters() {
		//reset and create new modified list from original
		modified.clear();
		filterList.clear();
		modified.addAll(original);
	}
	
	public List<String> getFilterList() {
		List<String> tempList = new ArrayList<>();
		
		for (Filter filter : filterList) {
			tempList.add(filter.toString());
		}
		
		return tempList;
	}
	
	public List<String> getFilterNames() {
		List<String> tempList = new ArrayList<>();
		tempList.addAll(filterNames);
		return tempList;
	}
	
	public int getNumActiveFilters() {
		return filterList.size();
	}
	
	public String getInputType(String name) {
		String output = "";
		
		switch (name) {
		case "First Name":
		case "Middle Name":
		case "Last Name":
			output = "string";
			break;
		default:
			System.err.println("DataHandler.getInputType not implemented for name: " + name);
			System.exit(1);
		}
		
		return output;
	}
	
	public void addFilter(String name, String string) {
		filterList.add(new Filter(true, name, string));
		applyFilters();
	}
	
	public void removeFilter(String toString) {
		List<Filter> filtered = new ArrayList<>();
		
		for (Filter f : filterList) {
			if (!f.toString().equals(toString)) {
				filtered.add(f);
			}
		}
		
		resetFilters();
		filterList.addAll(filtered);
		applyFilters();
	}
	
	public void orderFilters() {
		
	}
	
	//apply in order from filterList
	public void applyFilters() {
		int numFilters = filterList.size();
		for (int i = 0; i < numFilters; i++) {
			Filter f = filterList.get(i);
			if (f.isOn()) {
				switch (f.getName()) {
				case "First Name":
					filterFirstName(f.getString());
					break;
				case "Middle Name":
					filterMiddleName(f.getString());
					break;
				case "Last Name":
					filterLastName(f.getString());
					break;
					
				} //end switch
			}
		}
	}
	
	//generic filter
	private List<President> filter(String string, BiPredicate<President, String> matcher){
		List<President> filtered = new ArrayList<>();
		
		for (President p : modified) {
			if (matcher.test(p, string)) {
				filtered.add(p);
			}
		}
		
		return filtered;
	}
	
	//specific filters
	private void filterFirstName(String name) {
		modified = filter(name, (pres, string) -> {
			return pres.getFirstName().equalsIgnoreCase(string);
		});
	}
	
	private void filterMiddleName(String name) {
		modified = filter(name, (pres, string) -> {
			return pres.getMiddleName().equalsIgnoreCase(string);
		});
	}
	
	private void filterLastName(String name) {
		modified = filter(name, (pres, string) -> {
			return pres.getLastName().equalsIgnoreCase(string);
		});
	}
	
//	System.out.println("====== Filter by firstName = James ======");
////	printPresidents(filter("James", (pres, string) -> {
////		return pres.getFirstName().equalsIgnoreCase(string);
////	}));

}
