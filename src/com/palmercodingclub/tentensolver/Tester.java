package com.palmercodingclub.tentensolver;

import com.cassdelacruzmunoz.library.ConsoleIO;
import com.cassdelacruzmunoz.library.math.SingleVariableStats;
import com.palmercodingclub.tentensolver.solutions.AdjacencySolution;
import com.palmercodingclub.tentensolver.solutions.ClearFocusSol2;
import com.palmercodingclub.tentensolver.solutions.ClearFocusSolution;
import com.palmercodingclub.tentensolver.solutions.ImprovedAdjacencySol;
import com.palmercodingclub.tentensolver.solutions.MySolution;

public class Tester {
	static Solution[] sols;
	static double[][] score;
	static long[] time;
	static int trials;
	static String pFP;
	static SingleVariableStats[] stats;
	
	public static void main(String[] args) {
		pFP = args[0];
		switch (ConsoleIO.generateMenu("Which mode would you like to use?", new String[] {"Comparison Test\n","Algorithm assisted play"} )) {
			case 0:
				trials=ConsoleIO.getNumFromUser("How many trials would you like?");
				comparisonTest();
				main(args);
				break;
			case 1:
				Game g = new Game(new ClearFocusSol2(), pFP, false);
				g.play(true);
				main(args);
				break;
			case 2:
				break;
		}
	}
	
	private static void comparisonTest() {
		prepareSolutions();
		prepareScores();
		for(int i = 0; i < trials; i++) {
			if(i%(trials/100.0)==0) {
				ConsoleIO.print((int)(((double)(i)/trials)*100)+"% done");
			}
			runTrial(i);
		}
		calculateStats();
		printFiveNumSum();
		printTimes();
	}
	
	private static void prepareSolutions() {
		sols = new Solution[5];
		sols[0]=(Solution)(new AdjacencySolution());
		sols[1]=(Solution)(new ImprovedAdjacencySol());
		sols[2]=(Solution)(new MySolution());
		sols[3]=(Solution)(new ClearFocusSolution());
		sols[4]=(Solution)(new ClearFocusSol2());
	}
	
	private static void prepareScores() {
		score = new double[sols.length][];
		time=new long[sols.length];
		for (int i = 0; i < score.length; i++) {
			score[i] = new double[trials];
			time[i]=0;
		}
	}
	
	private static void runTrial(int i) {
		long seed = System.currentTimeMillis();
		for(int a = 0; a < sols.length; a++) {
			Game g = new Game(sols[a], pFP, false, seed);
			long timeTemp=System.currentTimeMillis();
			g.play(false);
			score[a][i]=g.getScore();
			time[a]+=System.currentTimeMillis()-timeTemp;
		}
	}
	
	private static void calculateStats() {
		stats = new SingleVariableStats[sols.length];
		for(int i = 0; i < sols.length; i++) {
			stats[i] = new SingleVariableStats(score[i]);
			stats[i].calcAllStats();
		}
	}
	
	private static void printFiveNumSum() {
		for (int a = 0; a < 5; a++) {
			System.out.printf("%s\t",stats[0].getAllStatLabels()[4][a]);
			for (int b=0; b < stats.length; b++) {
				System.out.printf("%5d\t",(int)(stats[b].fiveNumSummary()[a]));
			}
			ConsoleIO.print("");
		}
	}
	private static void printTimes() {
		System.out.printf("\t");
		for (int a=0;a<time.length;a++) {
			System.out.printf("%5d\t", time[a]);
		}
		ConsoleIO.print("");
	}
}
