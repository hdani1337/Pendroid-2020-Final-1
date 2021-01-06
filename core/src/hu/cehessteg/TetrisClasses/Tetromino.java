package hu.cehessteg.TetrisClasses;

import static hu.cehessteg.TetrisClasses.Tetris.playField;
import static hu.cehessteg.TetrisClasses.Tetris.playFieldHeight;
import static hu.cehessteg.TetrisClasses.Tetris.playFieldWidth;

public class Tetromino {
    /**Globális tetromino tömb**/
    public static String[] tetrominoes;

    /**Tetrominok 4x4-ben ábrázolva, tömb inicializálása**/
    public static void buildTetrominoes(){
        tetrominoes = new String[]{};
        tetrominoes[0] = ("..A...A...A...A.");
        tetrominoes[1] = ("..B..BB...B.....");
        tetrominoes[2] = (".....CC..CC.....");
        tetrominoes[3] = ("..D..DD..D......");
        tetrominoes[4] = (".E...EE...E.....");
        tetrominoes[5] = (".F...F...FF.....");
        tetrominoes[6] = ("..G...G..GG.....");

    }

    /**Visszaadja az elforgatott blokk új indexét**/
    public static int rotateBlock(int x, int y, int r){
        /**
         * 0: 0°
         * 1: 90°
         * 2: 180°
         * 3: 270°
         **/

        switch (r%4){
            case 0: default: return y*4+x;
            case 1: return 12+y-(x*4);
            case 2: return 15-(y*4)-x;
            case 3: return 3-y+(x*4);
        }
    }

    /**Visszaadja, hogy befér e a megadott tetromino**/
    public static boolean doesPieceFitIn(int tetrominoID, int rotation, int newX, int newY)
    {
        /**A tetromino blokkjainak végigvizsgálása**/
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++)
            {
                /**A blokk indexe a tetrominoban**/
                int pi = rotateBlock(px, py, rotation);

                /**A blokk indexe a játékmezőben**/
                int fi = (newY + py) * playFieldWidth + (newX + px);

                /**Ütközésvizsgálat
                 * Ha a blokk alatt van egy másik blokk vagy ha a szélén van, nem fér be
                 * **/
                if (newX + px >= 0 && newX + px < playFieldWidth)
                    if (newY + py >= 0 && newY + py < playFieldHeight)
                        if (tetrominoes[tetrominoID].charAt(pi) != '.' && playField[fi] != "0")
                            return false;
            }
        /**Ha nem találtunk ütközést, akkor igazzal térünk vissza**/
        return true;
    }
}
