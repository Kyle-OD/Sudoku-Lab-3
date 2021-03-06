package pkgGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;

import pkgEnum.ePuzzleViolation;
import pkgHelper.LatinSquare;
import pkgHelper.PuzzleViolation;

public class Sudoku extends LatinSquare {

	private int iSize;
	
	private int iSqrtSize;
	
	public Sudoku(int iSize) throws Exception {
		this.iSize = iSize;

		double SQRT = Math.sqrt(iSize);
		if ((SQRT == Math.floor(SQRT)) && !Double.isInfinite(SQRT)) {
			this.iSqrtSize = (int) SQRT;
		} else {
			throw new Exception("Invalid size");
		}
		int[][] puzzle = new int[iSize][iSize];
		this.setLatinSquare(puzzle);
	}
	
	public Sudoku(int[][] puzzle) throws Exception {
		super(puzzle);
		this.iSize = puzzle.length;
		double SQRT = Math.sqrt(iSize);
		if ((SQRT == Math.floor(SQRT)) && !Double.isInfinite(SQRT)) {
			this.iSqrtSize = (int) SQRT;
		} else {
			throw new Exception("Invalid size");
		}

	}
	
	private void FillDiagonalRegions() {
		for(int iRgnNbr = 0; iRgnNbr<this.iSize;iRgnNbr+=(iSqrtSize+1)) {
			this.SetRegion(iRgnNbr);
			this.ShuffleRegion(iRgnNbr);
		}
	}
	
	public int[][] getPuzzle() {
		return super.getLatinSquare();
	}

	public int[] getRegion(int iCol, int iRow) {

		int i = (iCol / iSqrtSize) + ((iRow / iSqrtSize) * iSqrtSize);

		return getRegion(i);
	}

	public int[] getRegion(int r) {

		int[] reg = new int[super.getLatinSquare().length];


		int i = (r / iSqrtSize) * iSqrtSize;
		int j = (r % iSqrtSize) * iSqrtSize;		
		int jMax = j + iSqrtSize;
		int iMax = i + iSqrtSize;
		int iCnt = 0;

		for (; i < iMax; i++) {
			for (j = (r % iSqrtSize) * iSqrtSize; j < jMax; j++) {
				reg[iCnt++] = super.getLatinSquare()[i][j];
			}
		}

		return reg;
	} 
	
	@Override
	public boolean hasDuplicates()
	{
		if (super.hasDuplicates())
			return true;
		
		for (int k = 0; k < this.getPuzzle().length; k++) {
			if (super.hasDuplicates(getRegion(k))) {
				super.AddPuzzleViolation(new PuzzleViolation(ePuzzleViolation.DupRegion,k));
			}
		}
	
		return (super.getPV().size() > 0);
	}
	
	public int getRegionNbr(int iCol, int iRow) {
		int nbr = (iCol / iSqrtSize) + ((iRow / iSqrtSize) * iSqrtSize);
		
		return nbr;
	}

	public boolean isPartialSudoku() {

		super.setbIgnoreZero(true);
		
		super.ClearPuzzleViolation();
		
		if (hasDuplicates())
			return false;

		if (!ContainsZero()) {
			super.AddPuzzleViolation(new PuzzleViolation(ePuzzleViolation.MissingZero, -1));
			return false;
		}
		return true;

	}

	public boolean isSudoku() {

		this.setbIgnoreZero(false);
		
		super.ClearPuzzleViolation();
		
		if (hasDuplicates())
			return false;
		
		if (!super.isLatinSquare())
			return false;
		
		for (int i = 1; i < super.getLatinSquare().length; i++) {

			if (!hasAllValues(getRow(0), getRegion(i))) {
				return false;
			}
		}

		if (ContainsZero()) {
			return false;
		}

		return true;
	}

	public boolean isValidValue(int iCol, int iRow, int iValue) {
		
		if (doesElementExist(super.getRow(iRow),iValue))
		{
			return false;
		}
		if (doesElementExist(super.getColumn(iCol),iValue))
		{
			return false;
		}
		if (doesElementExist(this.getRegion(iCol, iRow),iValue))
		{
			return false;
		}
		
		return true;
	}
	
	public void PrintPuzzle() {
		int[][] puzzle = super.getLatinSquare();
		for(int iRow = 0; iRow<this.iSize;iRow++) {
			for(int iCol = 0; iCol<this.iSize;iCol++) {
				System.out.print(puzzle[iRow][iCol]+" ");
			}
			System.out.println();
		}
	}
	
	private void SetRegion(int r) {
		int[][] puzzle = super.getLatinSquare();
		
		int i = (r / iSqrtSize) * iSqrtSize;
		int j = (r % iSqrtSize) * iSqrtSize;		
		int jMax = j + iSqrtSize;
		int iMax = i + iSqrtSize;
		int iCnt = 1;

		for (; i < iMax; i++) {
			for (j = (r % iSqrtSize) * iSqrtSize; j < jMax; j++) {
				puzzle[i][j] = iCnt++;	
			}
		}
	}
	
	private void shuffleArray(int[] arr) {
		ArrayList<Integer> list = new ArrayList<>();
		  for (int i : arr) {
		    list.add(i);
		  }

		  Collections.shuffle(list);

		  for (int i = 0; i < list.size(); i++) {
		    arr[i] = list.get(i);
		  } 
	}
	
	private void ShuffleRegion(int r) {
		int[] reg = this.getRegion(r);
		this.shuffleArray(reg);
		int[][] puzzle = super.getLatinSquare();
		
		int i = (r / iSqrtSize) * iSqrtSize;
		int j = (r % iSqrtSize) * iSqrtSize;		
		int jMax = j + iSqrtSize;
		int iMax = i + iSqrtSize;
		int iCnt = 0;

		for (; i < iMax; i++) {
			for (j = (r % iSqrtSize) * iSqrtSize; j < jMax; j++) {
				puzzle[i][j] = reg[iCnt++];				
			}
		}
		
	}
}
