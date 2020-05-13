package com.example.guessmaster;

public class Person extends Entity {
	private String gender;

	public Person(String name, Date born, String gender, Double difficulty) {
		super(name, born, difficulty);
		this.gender = gender;
	}

	public Person(Person otherPerson) {
		super(otherPerson);
		this.gender = otherPerson.getGender();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String entityType() {
		return "person";
	}

	@Override
	public Person clone() {
		return new Person(this);
	}

	@Override
	public String toString() {
		return String.format("%sGender: %s\n", super.toString(), gender);
	}
}
