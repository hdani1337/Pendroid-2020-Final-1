package hu.cehessteg.TetrisClasses;

import java.util.Random;

public class Tetromino {
    private ShapeType pieceShape;//Az alakzat
    private int coords[][];//Az alakzat koordinátái
    private int[][][] coordsTable;//3 dimenziós koordináta táblázat

    /**Konstruktor inicializálással**/
    public Tetromino() {
        initShape();
    }

    /**Alakzat inicializálása és üresre állítása alapértelmezetten**/
    private void initShape() {
        coords = new int[4][2];
        coordsTable = new int[][][] {
                { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
                { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
                { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
                { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
                { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
                { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
                { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
                { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
        };
        setShape(ShapeType.NoShape);
    }

    /**Alakzat beállítása**/
    protected void setShape(ShapeType shape) {
        for (int i = 0; i < 4 ; i++)
            for (int j = 0; j < 2; ++j)
                coords[i][j] = coordsTable[shape.ordinal()][i][j];
        pieceShape = shape;
    }

    /**X koordináta módosítása**/
    private void setX(int index, int x) {
        coords[index][0] = x;
    }

    /**Y koordináta módosítása**/
    private void setY(int index, int y) {
        coords[index][1] = y;
    }

    /**X koordinátát adja vissza**/
    public int x(int index) {
        return coords[index][0];
    }

    /**Y koordinátát adja vissza**/
    public int y(int index) {
        return coords[index][1];
    }

    /**A**/
    public ShapeType getShape()  {
        return pieceShape;
    }

    /**Új random alakzat beállítása**/
    public void setRandomShape() {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;

        ShapeType[] values = ShapeType.values();
        setShape(values[x]);
    }

    /**Legkisebb X koordinátát adja vissza**/
    public int minX() {
        int m = coords[0][0];

        for (int i=0; i < 4; i++)
            m = Math.min(m, coords[i][0]);

        return m;
    }


    /**Legkisebb Y koordinátát adja vissza**/
    public int minY() {
        int m = coords[0][1];

        for (int i=0; i < 4; i++)
            m = Math.min(m, coords[i][1]);

        return m;
    }

    /**Alakzat forgatása balra**/
    public Tetromino rotateLeft() {

        if (pieceShape == ShapeType.SquareShape)
            return this;//Ha kocka, akkor saját magát visszaadjuk

        Tetromino result = new Tetromino();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }

        return result;
    }

    /**Alakzat forgatása jobbra**/
    public Tetromino rotateRight() {

        if (pieceShape == ShapeType.SquareShape)
            return this;//Ha kocka, akkor saját magát visszaadjuk

        Tetromino result = new Tetromino();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, -y(i));
            result.setY(i, x(i));
        }

        return result;
    }
}

