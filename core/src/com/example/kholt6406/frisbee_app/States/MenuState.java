package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State{
    private Texture background;
    private Texture playBtn;
    private Texture managerBtn;
    private Texture settingsBtn;
    private Texture exitBtn;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        playBtn = new Texture("button_play_game.png");
        managerBtn = new Texture("button_team_manager.png");
        settingsBtn = new Texture("button_settings.png");
        exitBtn = new Texture("button_exit.png");
        background = new Texture("menu_background.png");
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
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(playBtn, (Gdx.graphics.getWidth()/2)-(playBtn.getWidth()/2),(Gdx.graphics.getHeight()/5)*4-(playBtn.getHeight()/2), playBtn.getWidth(), playBtn.getHeight());
        sb.draw(managerBtn, (Gdx.graphics.getWidth()/2)-(managerBtn.getWidth()/2),(Gdx.graphics.getHeight()/5)*3-(managerBtn.getHeight()/2), managerBtn.getWidth(), managerBtn.getHeight());
        sb.draw(settingsBtn, (Gdx.graphics.getWidth()/2)-(settingsBtn.getWidth()/2),(Gdx.graphics.getHeight()/5)*2-(settingsBtn.getHeight()/2), settingsBtn.getWidth(), settingsBtn.getHeight());
        sb.draw(exitBtn, (Gdx.graphics.getWidth()/2)-(exitBtn.getWidth()/2),(Gdx.graphics.getHeight()/5)-(exitBtn.getHeight()/2), exitBtn.getWidth(), exitBtn.getHeight());
        sb.end();

    }

    @Override
    public void dispose() {
        //background.dispose();
        playBtn.dispose();
    }
}
