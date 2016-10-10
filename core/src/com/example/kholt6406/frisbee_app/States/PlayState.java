package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.example.kholt6406.frisbee_app.sprites.Player;

public class PlayState extends State {
    private Player player;
    private Stage stage;
    private SpriteBatch sb;
    private Texture background;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture scoreboard;
    private EventListener listener;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        player=new Player(50,100);
        cam.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        background = new Texture("field_background.png");
        scoreboard=new Texture("scoreboard.png");
        //Texture joystickKnob = new Texture("joystick_base.png");
        //joystickKnob.

        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("joystick_base.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("joystick_64.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 400, 400);

        stage = new Stage();
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
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
        player.setX(player.getPosition().x + touchpad.getKnobPercentX()*player.getVelocity());
        player.setY(player.getPosition().y + touchpad.getKnobPercentY()*player.getVelocity());
        sb.begin();
        sb.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(player.getTexture(),player.getPosition().x,player.getPosition().y);
        sb.draw(scoreboard,(Gdx.graphics.getWidth()/2)-(scoreboard.getWidth()*3/2),Gdx.graphics.getHeight()-scoreboard.getHeight()*3,scoreboard.getWidth()*3,scoreboard.getHeight()*3);
        touchpad.draw(sb,1);
        sb.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
