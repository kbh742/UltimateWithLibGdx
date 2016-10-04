package com.example.kholt6406.frisbee_app.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;


public class Player {
    private static final int ACCELERATION=15;
    private Vector3 position;
    private Vector3 velocity;
    private Texture player;

    public Player(int x, int y){
        position=new Vector3(50,50,0);
        velocity=new Vector3(30,0,0);
        player=new Texture("idle.png");
    }

    public void setVelocity(){
        //velocity
    }

    public void update(float dt){
        velocity.scl(dt);
        position.add(velocity.x,0,0);
        velocity.scl(1/dt);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return player;
    }
}
