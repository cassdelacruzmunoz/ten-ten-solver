package com.palmercodingclub.tentensolver.solutions;

import java.util.ArrayList;
import com.palmercodingclub.tentensolver.Board;
import com.palmercodingclub.tentensolver.Coordinate;
import com.palmercodingclub.tentensolver.Piece;
import com.palmercodingclub.tentensolver.Solution;

public class ClearFocusSol2 extends Solution {

	public ClearFocusSol2() {
		super("Clear Focus Solution - prioritizes clearing lines with the availibile pieces");
	}

	@Override
	public int doMove(ArrayList<Piece> choices) {
		Piece bestPiece=null;
		Coordinate bestLocation=null;
		int bestCount=0;
		for(int a=0;a<choices.size();a++) {
			Piece p=choices.get(a);
			for (Coordinate c : b.getAvailableSpots(p))
			if (countClearable(p,c,b)>bestCount) {
				bestPiece=p;
				bestLocation=c;
				bestCount=countClearable(p,c,b);
			}
			for(int d=0;d<choices.size();d++) {
				if(a!=d) {
					Piece temp=choices.get(d);
					for (Coordinate c : b.getAvailableSpots(temp)) {
						Board tempBoard = testPlacePiece(temp,c);
						for (Coordinate e : tempBoard.getAvailableSpots(p)) {
							if (countClearable(p,e,tempBoard)>bestCount) {
								bestPiece=temp;
								bestLocation=c;
								bestCount=countClearable(temp,c,b);
							}
						}
					}
				}
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
	private Board testPlacePiece(Piece p, Coordinate atSpot) {
		Board temp=b.copyBoard();
		temp.placePiece(p, atSpot);
		return temp;
	}
	private int countClearable(Piece p, Coordinate atSpot, Board b){
		Board temp = b.copyBoard();
		temp.placePiece(p, atSpot);
		return temp.scanAndClear();
	}

}
