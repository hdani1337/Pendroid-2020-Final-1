package hu.cehessteg.TetrisClasses;

import com.badlogic.gdx.math.Vector2;

import hu.cehessteg.Actor.BlockActor;

public class Block {
    public BlockType blockType;
    private Vector2 position;
    private BlockActor blockActor;

    public Block(BlockType blockType, BlockActor blockActor) {
        this.blockType = blockType;
        this.blockActor = blockActor;
    }

    public void setPosition(int x, int y){
        this.position.x = x;
        this.position.y = y;
    }

    public void moveLeft(){
        if(this.position.x > 0)
            this.position.x--;
    }

    public void moveRight(){
        if(this.position.x-1 < 24)
            this.position.x++;
    }
}
