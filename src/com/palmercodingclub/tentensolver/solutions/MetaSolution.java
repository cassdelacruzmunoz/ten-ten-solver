package com.palmercodingclub.tentensolver.solutions;

import java.util.ArrayList;
import java.util.Random;
import com.palmercodingclub.tentensolver.Piece;
import com.palmercodingclub.tentensolver.Solution;

public class MetaSolution extends Solution {
	public MetaSolution() {
		super("A combination of various solutions");
		rng=new Random();
	}
	
	public MetaSolution(long seed) {
		super("A combination of various solutions");
		rng=new Random(seed);
	}

	Solution[] sols;
	double[] probabilities; //should have a sum of 1.0
	Random rng;

	public void prepareSolutions() {
		sols = new Solution[4];
		probabilities = new double[4];
		//System.out.println(getBoard());
		sols[0]=new AdjacencySolution();
		sols[0].setBoard(b);
		probabilities[0]=Math.random();
		sols[1]=new ImprovedAdjacencySol();
		sols[1].setBoard(b);
		probabilities[1]=Math.random()*(1-probabilities[0]);
		sols[2]=new MySolution();
		sols[2].setBoard(b);
		probabilities[2]=Math.random()*(1-(probabilities[0]+probabilities[1]));
		sols[3]=new ClearFocusSolution();
		sols[3].setBoard(b);
		probabilities[3]=1-(probabilities[0]+probabilities[1]+probabilities[2]);
	}
	
	public int doMove(ArrayList<Piece> choices) {
		prepareSolutions();
		double solIndex = Math.random();
		for (int i=0; solIndex>0; i++) {
			if(solIndex>probabilities[i]) {
				solIndex-=probabilities[i];
			}
			else {
				return sols[i].doMove(choices);
			}
		}
		return 0;
	}
	
	
}
