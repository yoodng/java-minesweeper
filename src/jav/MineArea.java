package jav;

import java.util.Random;

public class MineArea {


    private int mineCount = 0;
    private boolean[][] mines;
    private int[][] nearMineCount;

    private int row;
    private int col;

    public MineArea(int r, int c, int mCount) {
        mines = new boolean[r][c];
        nearMineCount = new int[r][c];
        row = r;
        col = c;
        mineCount = mCount;
        initializeMines();
        calcNearMine();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isMine(int r, int c) {
        return mines[r][c];
    }

    public int getMineCount() {
        return mineCount;
    }


    public int getNearMineCount(int row, int col) {

        return nearMineCount[row][col];
    }

    private void initializeMines() {
        Random random = new Random();
        int r, c;
        for (int i = 0; i < mineCount; i++) {
            do {
                r = random.nextInt(row);
                c = random.nextInt(col);
            } while (isMine(r, c));
            mines[r][c] = true;
        }
    }

    private void calcNearMine() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                nearMineCount[i][j] = calcNearMine(i, j);
            }
        }
    }

    private int calcNearMine(int row, int col) {

        int count = 0;
        count = 0;
        if (row - 1 >= 0 && col - 1 >= 0) {
            if (mines[row - 1][col - 1]) {
                count++;
            }
        }
        if (row - 1 >= 0) {
            if (mines[row - 1][col]) {
                count++;
            }
        }
        if (row - 1 >= 0 && col + 1 < this.col) {
            if (mines[row - 1][col + 1]) {
                count++;
            }
        }
        if (col - 1 >= 0) {
            if (mines[row][col - 1]) {
                count++;
            }
        }
        if (col + 1 < this.col) {
            if (mines[row][col + 1]) {
                count++;
            }
        }
        if (row + 1 < this.row && col - 1 >= 0) {
            if (mines[row + 1][col - 1]) {
                count++;
            }
        }

        if (row + 1 < this.row) {
            if (mines[row + 1][col]) {
                count++;
            }
        }

        if (row + 1 < this.row && col + 1 < this.col) {
            if (mines[row + 1][col + 1]) {
                count++;
            }
        }
        return count;
    }

    public boolean isValid(int r, int c) {
        if (r < 0 || r >= this.row || c < 0 || c >= this.col) {
            return false;
        }
        return true;
    }


}
