package com.example.kholt6406.frisbee_app.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


public class Player extends Sprite{
    //private Vector2 position;
    private Vector2 velocity;
    private float velocityX;
    private float velocityY;
    //private Texture img;
    private boolean hasDisk;
    //private float x;
    //private float y;
    private float rotation;

    public Player(){
        //img=new Texture("idle.png");
        velocityX= 0;
        velocityY=0;
        //x=0;
        //y=0;
        velocity = new Vector2(velocityX,velocityY);
        //position = new Vector2(x,y);
        rotation = 0;
    }
    public void setHoldingDisk(boolean tf){ hasDisk = tf;}

    /*public void setX(float xPos) {
        Gdx.app.log("x", ""+x);
        x = xPos;}
*/
 /*   public void setY(float yPos) {
        y = yPos;
    }*/

    public void setVX(float v) {velocityX = v;}

    public void setVY(float v) {velocityY = v;}

    public void setRotation(float r) {rotation = r;}

    /*public void setTexture(Texture t) {img = t;}*/


    public float getRotation() {return rotation;}

    /*public Vector2 getPosition() {
        return position;
    }*/

    public Vector2 getVelocity() { return velocity; }

    /*public Texture getTexture() {
        return img;
    }*/

    public boolean hasDisk() {return hasDisk; }
}
