package com.palmercodingclub.tentensolver.solutions;

import java.util.ArrayList;

import com.palmercodingclub.tentensolver.Piece;
import com.palmercodingclub.tentensolver.Solution;
import com.palmercodingclub.tentensolver.Board;
import com.palmercodingclub.tentensolver.Coordinate;

public class ClearFocusSolution extends Solution {

	public ClearFocusSolution() {
		super("Clear Focus Solution - directly prioritizes clearing lines");
	}

	@Override
	public int doMove(ArrayList<Piece> choices) {
		Piece bestPiece=null;
		Coordinate bestLocation=null;
		int bestCount=0;
		for(Piece p:choices) {
			for (Coordinate c : b.getAvailableSpots(p))
			if (countClearable(p,c,b)>bestCount) {
				bestPiece=p;
				bestLocation=c;
				bestCount=countClearable(p,c,b);
			}
		}
		if(bestPiece==null) {
			Solution s=new ImprovedAdjacencySol();
			s.setBoard(b);
			return s.doMove(choices);
		}
		b.placePiece(bestPiece, bestLocation);
		choices.remove(bestPiece);
		return bestPiece.getScore();
	}
	private int countClearable(Piece p, Coordinate atSpot, Board b){
		Board temp = b.copyBoard();
		temp.placePiece(p, atSpot);
		return temp.scanAndClear();
	}
}
