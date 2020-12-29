package hu.cehessteg.TetrisClasses;

public class Activity {
    public BlockMap blockMap;
    public Tetromino currentTetromino;
    public Tetromino nextTetromino;

    public Activity() {
    }

    public void step(){
        currentTetromino.stepDown();
    }
}
