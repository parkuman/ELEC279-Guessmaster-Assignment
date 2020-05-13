package com.example.guessmaster;

public class Country extends Entity implements Cloneable{
	private String capital;
	
	public Country(String name, Date born, String capital, Double difficulty) {
		super(name, born, difficulty);
		this.capital = capital;
	}
	
	// copy constructor
	public Country(Country otherCountry) {
		// calls constructor of the superclass
		super(otherCountry);
		this.capital = otherCountry.getCapital();
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	@Override
	public String entityType() {
		return "country";
	}

	@Override
	public Country clone() {
		return new Country(this);
	}
	
	@Override
	public String toString() {
		return String.format("%sCapital: %s\n", super.toString(), capital);
	}
}