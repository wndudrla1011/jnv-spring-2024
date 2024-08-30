package com.example.demo;

public class MainClass {

	public static void main(String[] args) {
		
		MyCalculator myCal = new MyCalculator();
		myCal.setCalculator(new Calculator());
		
		myCal.setFirstNum(10);
		myCal.setSecondNum(2);
		
		myCal.add();
		myCal.sub();
		myCal.mul();
		myCal.div();
		
	}

}
