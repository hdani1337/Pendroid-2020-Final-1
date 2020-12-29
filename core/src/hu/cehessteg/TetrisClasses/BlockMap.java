package hu.cehessteg.TetrisClasses;

import com.badlogic.gdx.math.Vector2;

public class BlockMap {
    public Block[][] map = {};
    public Vector2 size;

    public BlockMap() {
        this(new Vector2(10,24));
    }

    public BlockMap(Vector2 size) {
        this.size = size;
        for (int i = 0; i < size.y; i++)
            for (int j = 0; j < size.x; j++)
                map[i][j] = new Block(BlockType.NULL,null);
    }
}
