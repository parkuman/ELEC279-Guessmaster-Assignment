package com.example.guessmaster;

public class Politician extends Person {
	private String party;

	public Politician(String name, Date born, String gender, String party, Double difficulty) {
		super(name, born, gender, difficulty);
		this.party = party;
	}

	// copy constructor
	public Politician(Politician otherPolitician) {
		super(otherPolitician);
		this.party = otherPolitician.getParty();
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	@Override
	public String entityType() {
		return "politician";
	}

	@Override
	public Politician clone() {
		return new Politician(this);
	}

	@Override
	public String toString() {
		return String.format("%sParty: %s\n", super.toString(), party);
	}
}
