package com.mvpjava.random;


public class Application {

	private final AtmosphericRandom atmosphericRandom;

	public Application(AtmosphericRandom atmosphericRandom) {
		this.atmosphericRandom = atmosphericRandom;
	}

	public void startApplication() {
		  System.out.println("==========================================");
		  int randomInt = atmosphericRandom.nextInt();
		  System.out.println("Random Atmospheric Integer = " + randomInt);
		  System.out.println("==========================================");
		  System.out.println("");

		/*removed excess calls here as we only require 1 value return for randomInt and do not require the overloaded funtions */
		  
	}
}
