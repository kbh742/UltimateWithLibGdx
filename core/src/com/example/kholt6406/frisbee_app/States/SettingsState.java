package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.example.kholt6406.frisbee_app.sprites.PlayerCard;

/**
 * Created by kholt6406 on 10/17/2016.
 */
public class SettingsState extends State {
    private Stage stage;
    private Texture background;

    private Skin backBtnSkin;
    private ImageButton.ImageButtonStyle backBtnStyle;

    public SettingsState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("settings_menu.png");


        backBtnSkin = new Skin();
        backBtnSkin.add("backBtn", new Texture("button_back.png"));
        backBtnStyle = new ImageButton.ImageButtonStyle();
        backBtnStyle.imageUp = backBtnSkin.getDrawable("backBtn");
        backBtnStyle.imageDown = backBtnSkin.getDrawable("backBtn");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void update(float dt) {
        handleInput();
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
