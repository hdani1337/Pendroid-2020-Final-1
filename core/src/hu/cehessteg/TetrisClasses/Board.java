package hu.cehessteg.TetrisClasses;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import hu.cehessteg.Stage.HudStage;
import hu.cehessteg.Stage.OptionsStage;

public class Board {

    /**Tábla méretei**/
    public static int BOARD_WIDTH = OptionsStage.size;//Szélesség
    public int BOARD_HEIGHT = 11;//Magasság

    /**További globális változók**/
    public boolean isFallingFinished = false;//Befejeződött e a tetromino mozgása
    public static boolean isPaused = false;//Megvan e állítva a játék
    public static boolean isGameOver = false;//Vége van e a játéknak
    public int numLinesRemoved = 0;//Teli sorok száma
    public int curX = 0;//Jelenlegi tetromino X koordinátája
    public int curY = 0;//Jelenlegi tetromino Y koordinátája
    public Tetromino curPiece;//Jelenlegi tetromino alakja
    public static ShapeType nextPiece;//Következő tetromino alakja
    public ShapeType[] board;//Játéktábla

    /**Üres konstruktor a példányosítás miatt**/
    public Board() {
        isPaused = false;
        isGameOver = false;
    }

    /**Visszaadja az adott koordinátán levő kocka tetrominojának alakját**/
    public ShapeType shapeAt(int x, int y) {
        return board[(y * BOARD_WIDTH) + x];
    }

    /**Indító metódus**/
    public void start() {
        curPiece = new Tetromino();//Jelenlegi tetromino példányosítása
        board = new ShapeType[BOARD_WIDTH * BOARD_HEIGHT];//Játéktábla létrehozása
        setRandomShape();
        clearBoard();
        newPiece();
    }

    /**Játékmenet megállítása/folytatása**/
    private void pause() {
        isPaused = !isPaused;
    }

    /**Jelenlegi tetromino azonnali ledobása**/
    private void dropDown() {
        int newY = curY;

        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1))
                break;
            newY--;
        }

        pieceDropped();
    }

    /**Jelenlegi tetromino mozgása egy sorral lejjebb**/
    private void oneLineDown() {
        if (!tryMove(curPiece, curX, curY - 1))
            pieceDropped();
    }

    /**Játéktábla kiürítése**/
    private void clearBoard() {
        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++)
            board[i] = ShapeType.NoShape;
    }

    /**Ha leesik a tetromino, beleilleszti a játéktáblába a blokkokat**/
    private void pieceDropped() {
        for (int i = 0; i < 4; i++) {
            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * BOARD_WIDTH) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished)
            newPiece();
    }

    /**Új tetromino**/
    private void newPiece() {
        curPiece.setShape(nextPiece);
        setRandomShape();
        curX = BOARD_WIDTH / 2 + 1;
        curY = BOARD_HEIGHT - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY))
            isGameOver = true;
    }

    /**Visszaadja, hogy befér e a tetromino a megadott helyre**/
    private boolean tryMove(Tetromino newPiece, int newX, int newY) {
        for (int i = 0; i < 4; i++) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);

            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT)
                return false;//Játéktábla széle

            if (shapeAt(x, y) != ShapeType.NoShape)
                return false;//Foglalt
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;
        return true;
    }

    /**Teli sorok eltávolítása**/
    private void removeFullLines() {
        int numFullLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean lineIsFull = true;
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (shapeAt(j, i) == ShapeType.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                numFullLines++;
                for (int k = i; k < BOARD_HEIGHT - 1; k++) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {
            numLinesRemoved += numFullLines;
            isFallingFinished = true;
            curPiece.setShape(ShapeType.NoShape);
        }
    }

    /**Lépés a játékmenetben**/
    public void update() {
        if (isPaused)
            return;

        /**Ha leesett a tetromino, akkor újat hozunk létre, ha pedig nem akkor léptetjük lefelé**/
        if (isFallingFinished) {
            newPiece();
            isFallingFinished = false;
        } else oneLineDown();
    }

    public void control(String input){
        switch (input){
            case "p": {
                pause();
                break;
            }
            case "a" : {
                tryMove(curPiece, curX - 1, curY);
                break;
            }
            case "d" : {
                tryMove(curPiece, curX + 1, curY);
                break;
            }
            case "s" : {
                tryMove(curPiece.rotateRight(), curX, curY);
                break;
            }
            case "w": {
                tryMove(curPiece.rotateLeft(), curX, curY);
                break;
            }
            case " " : {
                dropDown();
                break;
            }
            case "q" : {
                oneLineDown();
                break;
            }
        }
    }

    /**Új random alakzat beállítása**/
    public void setRandomShape() {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;

        ShapeType[] values = ShapeType.values();
        nextPiece = values[x];
    }
}
