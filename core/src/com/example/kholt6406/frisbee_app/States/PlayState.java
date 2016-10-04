package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.kholt6406.frisbee_app.sprites.Player;

public class PlayState extends State {
    private Player player;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        player=new Player(50,100);
        cam.setToOrtho(false,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void update(float dt) {
        handleInput();
        player.update(dt);
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(player.getTexture(),player.getPosition().x,player.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
