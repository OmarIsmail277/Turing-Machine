//************************************************JAVA***********************************************\\
package toc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
//import java.io.*;

public class TM {

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		char curState = '0';
		char newchar;
		char curChar;
		System.out.println("Please Enter Number Of States");
		// taking inputs from user
		int NumOfStates = input.nextInt();
		input.nextLine();

		System.out.println("Please Enter The Alphabet ex a,b,#.");
		String Alphabet = input.nextLine();

		// getting the alphabet length
		int alphaLength = (Alphabet.length() + 1) / 2;

		System.out.println("Please Enter The Input Tape ex <aabba#");
		String mytape = input.nextLine();

		// Building a StringBuilder to be able to modify in our Tape
		// since that the String un-modifiable

		StringBuffer tape = new StringBuffer(mytape);

		System.out.println("Please Enter Position Of Head");
		int PositionOfHead = input.nextInt();

		// Array of characters to store the alphabets
		char[] ArrayOfAlphabets = new char[alphaLength];
		int k = 0;
		for (int i = 0; i < alphaLength; i++) {
			ArrayOfAlphabets[i] = Alphabet.charAt(k);
			k = k + 2;
		}

		// Number of transition functions is the result of multiplying
		// the number of states * the number of characters in the alphabet
		int NumberOfTransitions = NumOfStates * alphaLength;

		// Array of Strings to Store each transition function
		String[] ArrayOfTransitions = new String[NumberOfTransitions];

		// Reading the Transition Function input from File
		File file = new File("C:\\Users\\Omar&Medo\\Desktop\\transition.txt");

		// BufferedReader to read from file
		BufferedReader Breader = new BufferedReader(new FileReader(file));

		String str;
		int w = 0;
		while ((str = Breader.readLine()) != null) {
			ArrayOfTransitions[w] = str;
			w++;
		}

		int flagOfStartSymbol = 0;
		if (tape.charAt(0) == '<') {

			flagOfStartSymbol = 1;

		}
		if (!(tape.charAt(tape.length() - 1) == '#')) {

			tape.append("#");
			// System.out.println(tape);
		}
		char action;
		curChar = tape.charAt(PositionOfHead);

		if (PositionOfHead == 0 && curChar == '<') {

			PositionOfHead++;
			curChar = tape.charAt(PositionOfHead);
		}
		// Inputs taking is done! start implementing the machine!

		int i = 0, j;
		// label
		myloop:
		// nested loop to pass by Transtions functions one by one & pass by its
		// contents
		while (i < NumberOfTransitions) {
			for (j = 0; j < NumberOfTransitions; j++) {
				// getting the current state & the currentChar of the alphabet &
				// also check
				if (curState == ArrayOfTransitions[i].charAt(0) && curChar == ArrayOfTransitions[j].charAt(2)
						&& i == j) {

					// action ,, go right or left ? Accept or Reject?
					action = ArrayOfTransitions[i].charAt(8);

					// If The Action is YES (Accept) !
					if (action == 'Y' || action == 'y') {
						newchar = ArrayOfTransitions[i].charAt(6);
						tape.setCharAt(PositionOfHead, newchar);
						// to distinguish where the head is pointing at last ,
						// by
						// surrounding it by ( )
						String newPrint = String.valueOf(ArrayOfTransitions[i].charAt(6));
						String output = "(" + newPrint + ")";
						tape.replace(PositionOfHead, PositionOfHead + 1, output);
						System.out.println(tape);
					//	System.out.println("YES");
						System.exit(0);
					}
					// If The Action is NO (REJECT) !
					if (action == 'N' || action == 'n') {
						newchar = ArrayOfTransitions[i].charAt(6);
						tape.setCharAt(PositionOfHead, newchar);
						String newPrint = String.valueOf(ArrayOfTransitions[i].charAt(6));
						String output = "(" + newPrint + ")";
						tape.replace(PositionOfHead, PositionOfHead + 1, output);
						System.out.println(tape);
				//		System.out.println("NO");
						System.exit(0);
					}
					// If The Action R (move right)
					if (action == 'r' || action == 'R') {
						newchar = ArrayOfTransitions[i].charAt(6);
						tape.setCharAt(PositionOfHead, newchar);
						PositionOfHead++;
						curChar = tape.charAt(PositionOfHead);
						curState = ArrayOfTransitions[i].charAt(4);
						// reached the end of the tape and passed it
						if (PositionOfHead >= tape.length()) {
							System.exit(0);
						}
						i = 0;
						j = 0;
						continue myloop;
					}

					// if The Action L (move left)
					if (action == 'l' || action == 'L') {
						newchar = ArrayOfTransitions[i].charAt(6);
						tape.setCharAt(PositionOfHead, newchar);
						PositionOfHead--;
						curChar = tape.charAt(PositionOfHead);
						curState = ArrayOfTransitions[i].charAt(4);
						// reached the very start of the tape '<'
						if (PositionOfHead == 0 && flagOfStartSymbol == 1) {
							System.exit(0);
						}
						i = 0;
						j = 0;
						continue myloop;
					}

				} // If statement that checks the transition function

			} // for loop(inner)

			j = 0;
			i++;
		}//while loop
	}//main

}//Class
