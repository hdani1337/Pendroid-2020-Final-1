package hu.cehessteg.TetrisClasses;

import java.util.Vector;

import static hu.cehessteg.TetrisClasses.Tetromino.doesPieceFitIn;
import static hu.cehessteg.TetrisClasses.Tetromino.rotateBlock;
import static hu.cehessteg.TetrisClasses.Tetromino.tetrominoes;

public class Tetris {
    public static String[] playField;
    public static int playFieldWidth = 12;
    public static int playFieldHeight = 18;
    public static boolean isGameOver;

    public static int currentTetrominoID = 0;
    public static int currentRotation = 0;
    public static int currentX = playFieldWidth / 2;
    public static int currentY = 0;

    public static int speed = 20;
    public static int speedCounter = 0;
    public static boolean forceDown = false;

    public static int score = 0;

    private int fullRow;

    private TetrominoDirections userInput;

    public Tetris() {
        this.userInput = TetrominoDirections.NULL;
        this.fullRow = -1;
        createPlayfield();
    }

    /**A játéktér létrehozása**/
    private void createPlayfield(){
        for (int i = 0; i < playFieldWidth*playFieldHeight; i++)
            playField[i] = " ";

        /**A tábla szélei**/
        for (int x = 0; x < playFieldWidth; x++)
            for (int y = 0; y < playFieldHeight; y++)
                playField[y*playFieldWidth + x] = (x == 0 || x == playFieldWidth - 1 || y == playFieldHeight - 1) ? "Q" : "0";
    }

    /**Tetromino irányításának vizsgálata**/
    private void checkUserInput(){
        switch (userInput) {
            case LEFT:
                if(doesPieceFitIn(currentTetrominoID,currentRotation,currentX-1,currentY))
                    currentX--;
                break;
            case RIGHT:
                if(doesPieceFitIn(currentTetrominoID,currentRotation,currentX+1,currentY))
                    currentX++;
                break;
            case DOWN:
                if(doesPieceFitIn(currentTetrominoID,currentRotation,currentX,currentY+1))
                    currentY++;
                break;
            case ROTATE:
                if(doesPieceFitIn(currentTetrominoID,currentRotation+1,currentX,currentY))
                    rotateBlock(currentX,currentY,currentRotation++);
                break;
            case NULL:
                break;
        }
    }

    /**Tetromino mozgatása lefelé**/
    private void stepDown(){
        /**Ha befér, lemegy**/
        if(doesPieceFitIn(currentTetrominoID,currentRotation,currentX,currentY+1)){
            currentY++;
        }
        /**Ha nem, zárjuk be a tetrominot a pályába, ellenőrzések, új tetromino**/
        else {
            lockTetromino();
            checkRow();
            checkUserInput();
            newTetromino();
            checkGameOver();
        }

        speedCounter = 0;
    }

    /**Jelenlegi tetromino beillesztése a pályába**/
    private void lockTetromino(){
        score += 25;//Tetrominonként 25 pont
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++)
                if (tetrominoes[currentTetrominoID].charAt(rotateBlock(px, py, currentRotation)) != '.')
                    playField[(currentY + py) * playFieldWidth + (currentX + px)] = "ABCDEFG".charAt(currentTetrominoID)+"";
    }

    /**Teli sor ellenőrzése**/
    private void checkRow(){
        for (int py = 0; py < 4; py++)
            if(currentY + py < playFieldHeight - 1)
            {
                /**Ha egy helyen üres, akkor már nincs teli sorunk**/
                boolean scored = true;
                for (int px = 1; px < playFieldWidth - 1; px++) {
                    if((playField[(currentY + py) * playFieldWidth + px]) == " "){
                        scored = false;
                        break;
                    }
                }

                /**Ha pedig tele a sor, akkor tűntessük el**/
                if (scored)
                {
                    for (int px = 1; px < playFieldWidth - 1; px++)
                        playField[(currentY + py) * playFieldWidth + px] = "=";
                    fullRow = currentY+py;
                }
            }

        /**Pontszám növelése, fentmaradt sorok lemozgatása**/
        if(fullRow != -1) {
            score += 100;
            for (int px = 1; px < playFieldWidth - 1; px++)
            {
                for (int py = fullRow; py > 0; py--)
                    playField[py * playFieldWidth + px] = playField[(py - 1) * playFieldWidth + px];
                playField[px] = " ";
            }

            fullRow = -1;
        }
    }

    /**Új tetromino választása**/
    private void newTetromino(){
        currentX = playFieldWidth / 2;
        currentY = 0;
        currentRotation = 0;
        currentTetrominoID = (int) (Math.random()*7);
    }

    /**Leellenőrzi, hogy vége van e a játéknak**/
    private void checkGameOver(){
        isGameOver = !doesPieceFitIn(currentTetrominoID, currentRotation, currentX, currentY);
    }
}
