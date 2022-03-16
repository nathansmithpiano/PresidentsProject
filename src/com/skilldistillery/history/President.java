package com.skilldistillery.history;

import java.time.LocalDate;
import java.time.Period;

public class President implements Comparable<President> {
	private int termNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate termBegan;
	private LocalDate termEnded;
	private int electionsWon;
	private String whyLeftOffice;
	private String party;
	
	public President(int termNumber, String firstName, String middleName, String lastName, LocalDate termBegan, LocalDate termEnded, int electionsWon,
			String reasonLeftOffice, String party) {
		super();
		this.termNumber = termNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.termBegan = termBegan;
		this.termEnded = termEnded;
		this.electionsWon = electionsWon;
		this.whyLeftOffice = reasonLeftOffice;
		this.party = party;
	}

	public int getTermNumber() {
		return termNumber;
	}

	public void setTermNumber(int termNumber) {
		this.termNumber = termNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public LocalDate getTermBegan() {
		return termBegan;
	}

	public void setTermBegan(LocalDate termBegan) {
		this.termBegan = termBegan;
	}

	public LocalDate getTermEnded() {
		return termEnded;
	}

	public void setTermEnded(LocalDate termEnded) {
		this.termEnded = termEnded;
	}

	public int getElectionsWon() {
		return electionsWon;
	}

	public void setElectionsWon(int electionsWon) {
		this.electionsWon = electionsWon;
	}

	public String getWhyLeftOffice() {
		return whyLeftOffice;
	}

	public void setWhyLeftOffice(String reasonLeftOffice) {
		this.whyLeftOffice = reasonLeftOffice;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}
	
	public Period getTermLength() {
		if (termEnded == null) {
			return Period.between(termBegan, LocalDate.now());
		} else {
			return Period.between(termBegan, termEnded);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(termNumber + ": ");
		builder.append(firstName + " ");
		if (middleName.length() > 0) {
			builder.append(middleName + " ");
		}
		builder.append(lastName);
		builder.append(" (" + party + ")");
		builder.append(", termBegan=");
		builder.append(termBegan);
		builder.append(", termEnded=");
		builder.append(termEnded);
		builder.append(", getTermLength()=");
		builder.append(getTermLength());
		builder.append(", electionsWon=");
		builder.append(electionsWon);
		builder.append(", whyLeftOffice=");
		builder.append(whyLeftOffice);
		return builder.toString();
	}

	@Override
	public int compareTo(President other) {
		if (this.termNumber > other.termNumber) {
			return 1;
		} else if (this.termNumber < other.termNumber) {
			return -1;
		}
		return 0;
	}

	public String getMiddleNames() {
		// TODO Auto-generated method stub
		return middleName;
	}

}