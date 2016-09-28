package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State{
    private Texture background;
    private Texture playBtn;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        playBtn = new Texture("Play.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(playBtn, (Gdx.graphics.getWidth()/2)-(playBtn.getWidth()/2),(Gdx.graphics.getHeight()/2)-(playBtn.getHeight()/2), playBtn.getWidth(), playBtn.getHeight());
        sb.end();

    }

    @Override
    public void dispose() {
        //background.dispose();
        playBtn.dispose();
    }
}
