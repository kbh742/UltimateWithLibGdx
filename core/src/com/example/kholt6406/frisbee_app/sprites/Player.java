package com.example.kholt6406.frisbee_app.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;


public class Player {
    private static final int ACCELERATION=15;
    private Vector3 position;
    private float velocity;
    private Texture player;
    float x = 0;
    float y = 0;


    public Player(int x, int y){
        position=new Vector3(50,50,0);
        velocity= 10;
        player=new Texture("idle.png");
    }

    public void setX(float xPos) { x = xPos; }

    public void setY(float yPos) { y = yPos; }

    public void setVelocity() {}

    public void update(float dt){
        //velocity.scl(dt);
        position.set(x,y,0);
        //velocity.scl(1/dt);
    }

    public Vector3 getPosition() {
        return position;
    }

    public float getVelocity() { return velocity; }

    public Texture getTexture() {
        return player;
    }
}
