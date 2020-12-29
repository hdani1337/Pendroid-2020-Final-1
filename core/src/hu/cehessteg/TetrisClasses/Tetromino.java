package hu.cehessteg.TetrisClasses;

import com.badlogic.gdx.math.Vector2;

public class Tetromino {
    public TetrominoType tetrominoType;
    private BlockMap blockMap;

    public Tetromino(TetrominoType tetrominoType) {
        this.tetrominoType = tetrominoType;
        this.blockMap = new BlockMap();
        switch (tetrominoType) {
            case STRAIGHT:
                blockMap.size = new Vector2(4,1);
                break;
            case SQUARE:
                blockMap.size = new Vector2(2,2);
                break;
            case T:
                blockMap.size = new Vector2(3,2);
                break;
            case L:
                blockMap.size = new Vector2(2,3);
                break;
            case SKEW:
                blockMap.size = new Vector2(4,2);
                break;
        }
    }

    //na majd ez lesz vicces geci
    //90 fokos forgatás
    //blockmap méretek cserélése
    //koordináták átszervezése
    public void rotate(){

    }

    //legalsó blokkokat kell majd vizsgálni, hogy van e alattuk blokk
    //ha nincsenek, akkor lépjenek lefele, ha vannak, álljanak meg és épüljenek be a blockmapbe
    public void stepDown(){

    }
}
