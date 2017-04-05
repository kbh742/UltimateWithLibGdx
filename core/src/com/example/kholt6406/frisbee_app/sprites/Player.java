package com.example.kholt6406.frisbee_app.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


public class Player extends Sprite{
    private static final int ACCELERATION=15;
    private Vector2 position;
    private float velocity;
    private Texture player;
    private boolean hasDisk;
    float x = 0;
    float y = 0;

    public Player(int x0, int y0){
        position=new Vector2(x0, y0);
        velocity= 0;
        player=new Texture("idle.png");
        x=x0;
        y=y0;
    }
    public void setHoldingDisk(boolean tf){ hasDisk = tf;}

    public void setX(float xPos) {x = xPos;}

    public void setY(float yPos) {
        y = yPos;
    }

    public void setVelocity(int v) {velocity = v;}

    public void update(float dt){
        //velocity.scl(dt);
        position.set(x,y);
        //velocity.scl(1/dt);
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getVelocity() { return velocity; }

    public Texture getTexture() {
        return player;
    }

    public boolean hasDisk() {return hasDisk; }
}
