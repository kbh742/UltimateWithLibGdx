package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kholt6406 on 9/20/2016.
 */
public class MenuState extends State{
    private Texture background;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("Ultimate.png");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0);

    }
}
