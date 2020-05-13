package com.example.guessmaster;

/**
 * @author Parker Rowe, 20122320 ELEC 279 Assignment 2: March 2020
 */
public abstract class Entity implements Cloneable {
	private String name;
	private Date born;
	private Double difficulty;

	public Entity(String name, Date born, Double difficulty) {
		this.name = name;
		this.born = born;
		this.difficulty = difficulty;
	}

	// copy constructor
	public Entity(Entity otherEntity) {
		// no privacy leak since getName and getBorn do not directly refer to
		// otherEntities' names and born date
		this.name = otherEntity.getName();
		this.born = otherEntity.getBorn();
		this.difficulty = otherEntity.getDifficulty();

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBorn() {
		return new Date(born); // to avoid privacy leak, create new instance of Date class using copy
								// constructor
	}

	public void setBorn(Date born) {
		this.born = born;
	}

	public Double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Double difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * checks if the passed parameter otherEntity is equal to the current instance
	 * of entity
	 * 
	 * @param otherEntity the other entity we wish to compare to the current
	 *                    instance of entity
	 * @return boolean indicating if current instance equals parameter
	 * @author Parker Rowe
	 */
	public boolean equals(Entity otherEntity) {
		if (otherEntity.name.equals(this.name) && otherEntity.born.equals(this.born)) {
			return true;
		} else {
			return false;
		}
	}

	public int getAwardedTicketNumber() {
		return (int) (difficulty * 100);
	}

	// toString method
	public String toString() {
		// returns a formatted string displaying the name and date the entity was born
		// on
		return String.format("Name: %s\nBorn at: %s\n", name, born.toString());
	}

	// abstract method entityType to have method body defined by sub-classes
	// inheriting entity
	public abstract String entityType();

	// abstract method clone to have method body defined by sub-classes inheriting
	// entity
	public abstract Entity clone();

	public String welcomeMessage() {
		// utilize late binding to insert the entityType of the corresponding subclass
		// on runtime, not on compilation
		return String.format("***************************\nWelcome! Let\'s start the game! This entity is a %s!\n\n", entityType());
	}

	public String closingMessage() {
		// continue to utilize late binding to indicate to the user the information of
		// the entity they jst guessed using the late binded toString method
		return String.format("Congratudations! The detailed information of the entity you guessed is: \n%s", toString());
	}

}
