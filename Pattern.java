
//	William Knowles-Kellett
//	9/15/2018
//	This program generates a valid 3x3 pattern lock for an android phone

import java.util.ArrayList;
import java.util.Random;

public class Pattern {
    public static void main(String[] args) {
	Pattern p = new Pattern();
	System.out.println(p);

    }

    private boolean errOn = false;
    private static Random r = new Random();
    // length is the number of digits, and is somewhat unnecessary now that the
    // middles
    // are manually calculated, but it was set at a time when the middles were to be
    // detected by the program.
    private static final int length = 9;
    // matches, given the digit of the last dot, says whether there is a dot
    // between it and the next, intended, dot.
    private static final int[][][] matches = { { { 2, 1 }, { 6, 3 }, { 8, 4 } }, { { 7, 4 } },
	    { { 0, 1 }, { 6, 4 }, { 8, 5 } }, { { 5, 4 } }, {}, { { 3, 4 } }, { { 0, 3 }, { 2, 4 }, { 8, 7 } },
	    { { 1, 4 } }, { { 0, 4 }, { 2, 5 }, { 6, 7 } } };
    private int[] dots;
    private int dotsLength;
    private ArrayList<Integer> unused;

    public Pattern() {
	dots = new int[length];
	dotsLength = 0;
	unused = new ArrayList<Integer>();
	doUnused();
	printer("Initiating:");
	initiate();
    }

    public String toString() {
	String retVal = "";
	for (int i = 0; i < length; i++) {
	    if (i < dotsLength)
		retVal += dots[i];
	    else
		retVal += "*";
	}
	return retVal;
    }

    private void printer(String s) {
	// Prints updates to the console if they are enabled in the hard code
	if (errOn)
	    System.out.println(s);
    }

    private void initiate() {
	// One by one, move random digits from unused to dots
	while (unused.size() > 0) {
	    printer("Adding:");
	    int n = r.nextInt(unused.size());
	    add(n);
	}
    }

    private void doUnused() {
	// fill unused with the 9 digits 0-8
	for (int i = 0; i < length; i++) {
	    unused.add(i);
	}
    }

    private void hasMiddle(int a) {
	// If there is a dot directly between the last dot
	// and the dot that is to be added, place it in the
	// list first.
	// This bit is what separates this program from any old random
	// number generator.
	Integer middle = -1;
	printer("Checking Middle:");
	if (dotsLength > 0) {
	    printer("Actually checking Middle:");
	    for (int[] b : matches[dots[dotsLength - 1]]) {
		printer("Checking " + b[0] + ", " + a);
		if (b[0] == a) {
		    middle = b[1];
		    printer("Found middle " + middle);
		} else
		    printer("Not a middle.");
	    }
	}
	if ((int) middle != -1) {
	    printer("Adding Middle:");
	    printer("" + middle);
	    printer(add(middle) ? "Added middle." : "Failed to add middle.");
	} else
	    printer("No middle.");
    }

    private boolean add(Integer a) {
	// if the digit we are told to add exists, add it
	// return whether the add was successful
	if (unused.indexOf(a) != -1)
	    return add(unused.indexOf(a));
	return false;
    }

    private boolean add(int a) {
	// move the digit at the given location in unused to the end of
	// dots[]
	Integer t = unused.get(a);
	if (t > -1) {
	    printer("Item:");
	    printer("" + t);
	    unused.remove(t);
	    // Check if there is a dot in the way which should be added first
	    hasMiddle(t);
	    printer("Continuing add:");
	    printer("" + t);
	    dots[dotsLength++] = t;
	    printer("Added. Current list:");
	    printer("" + this);
	    return true;
	} else
	    return false;
    }
}
