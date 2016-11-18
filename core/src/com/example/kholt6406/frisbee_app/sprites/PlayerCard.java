package com.example.kholt6406.frisbee_app.sprites;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kholt6406 on 10/17/2016.
 */
public class PlayerCard {
    private Texture playerImg;

    public PlayerCard(String img, int spd, int stam, int thr, int cat, int def){
        playerImg = new Texture(img);

    }
}
