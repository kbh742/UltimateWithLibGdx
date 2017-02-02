package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class WinState extends State {
    private Stage stage;
    private Texture background;
    private Texture winText;
    private Texture greenBackground;
    float w = Gdx.graphics.getWidth();
    float h= Gdx.graphics.getHeight();
    float realWidth;

    private Skin backBtnSkin;
    private ImageButton.ImageButtonStyle backBtnStyle;

    public WinState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("win.png");
        greenBackground = new Texture("green_background.png");
        winText= new Texture("win_text1.png");
        realWidth=background.getWidth()-200;


/*        backBtnSkin = new Skin();
        backBtnSkin.add("backBtn", new Texture("button_back.png"));
        backBtnStyle = new ImageButton.ImageButtonStyle();
        backBtnStyle.imageUp = backBtnSkin.getDrawable("backBtn");
        backBtnStyle.imageDown = backBtnSkin.getDrawable("backBtn");*/

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    protected void handleInput() {
/*        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new MenuState(gsm));
            dispose();
        }*/
    }

    @Override
    protected void update(float dt) {
        handleInput();
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(greenBackground,0,0,w,h);
        sb.draw(background, 100, 0, Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight()/2);
        sb.draw(winText, (w/2)-(winText.getWidth()/2), h-winText.getHeight(), winText.getWidth(), winText.getHeight());
        sb.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
