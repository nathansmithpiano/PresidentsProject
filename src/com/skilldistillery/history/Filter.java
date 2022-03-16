package com.skilldistillery.history;

public class Filter {

	private boolean isOn;
	private String name;
	private String string;
	private int min;
	private int max;

	public Filter() {
		isOn = false;
		name = null;
		string = null;
		min = -1;
		max = -1;
	}
	
	@Override
	public String toString() {
		String output = "";
		output += name + ": ";
		
//		System.out.println("\t\tTESTING");
//		System.out.println("\t\tisOn: " + isOn + " name " + name + " string " + string + " min " + min + " max " + max);
		
		if (!string.equals(null)) {
			output += this.string;
		}
		
		if (this.min != -1) {
			if (this.max != -1) {
				output += " between " + this.min + " and " + this.max;
			} else {
				output += min;
			}
		}
		
		return output;
	}

	// Constructor for string only, default isOn = off
	public Filter(String name, String string) {
		this.name = name;
		this.string = string;
		min = -1;
		max = -1;
	}
	
	// Constructor for string only
	public Filter(boolean isOn, String name, String string) {
		this.isOn = isOn;
		this.name = name;
		this.string = string;
		min = -1;
		max = -1;
	}

	// Constructor for single value int only, default isOn = off
	public Filter(String name, int min) {
		this.isOn = isOn;
		this.name = name;
		this.min = min;
	}
	
	// Constructor for single value int only
	public Filter(boolean isOn, String name, int min) {
		this.isOn = isOn;
		this.name = name;
		this.min = min;
	}

	// Constructor for min/max int only
	public Filter(boolean isOn, String name, int min, int max) {
		this.isOn = isOn;
		this.name = name;
		this.min = min;
		this.max = max;
	}

	// constructor for default isOn = off
	public Filter(String name, String string, int min, int max) {
		this.name = name;
		this.string = string;
		this.min = min;
		this.max = max;
	}

	public Filter(boolean isOn, String name, String string, int min, int max) {
		this.isOn = isOn;
		this.name = name;
		this.string = string;
		this.min = min;
		this.max = max;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

}
