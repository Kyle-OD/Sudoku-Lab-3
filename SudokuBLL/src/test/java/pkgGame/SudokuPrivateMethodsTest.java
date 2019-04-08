package pkgGame;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import pkgGame.Sudoku;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SudokuPrivateMethodsTest {
	
	@Test
	public void FillDiagonalRegions_Test() throws Exception {
		int size = 9;
		Sudoku test = new Sudoku(size);
		try {
		Class<?> cls = test.getClass();
		Method[] methods = cls.getMethods();
		Method mSet = cls.getDeclaredMethod("SetRegion", int.class);
		Method mShuffleArray = cls.getDeclaredMethod("shuffleArray", int[].class);
		Method mShuffleRegion = cls.getDeclaredMethod("ShuffleRegion", int.class);
		Method mFill = cls.getDeclaredMethod("FillDiagonalRegions");
		mSet.setAccessible(true);
		mShuffleArray.setAccessible(true);
		mShuffleRegion.setAccessible(true);
		mFill.setAccessible(true);
		mFill.invoke(test);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		test.PrintPuzzle();
	}
}
