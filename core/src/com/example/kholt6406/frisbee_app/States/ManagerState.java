package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.kholt6406.frisbee_app.sprites.PlayerCard;

/**
 * Created by kholt6406 on 10/17/2016.
 */
public class ManagerState extends State {
    private PlayerCard p1;
    private PlayerCard p2;
    private PlayerCard p3;
    private PlayerCard p4;
    private PlayerCard p5;

    public ManagerState(GameStateManager gsm) {
        super(gsm);

        p1 = new PlayerCard("idle.png", 1, 1, 1, 1, 1);

    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void update(float dt) {

    }

    @Override
    protected void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }
}
