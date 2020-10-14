package jav;

import javax.swing.*;
import javax.swing.plaf.metal.MetalInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private MineArea mineArea;
    private int mineCount;
    private int clickCount;

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    private int level;

    public Game(int row, int col, int mC) {
        mineArea = new MineArea(row, col, mC);
        mineCount = mC;
    }

    public MineArea getMineArea() {
        return mineArea;
    }
}












