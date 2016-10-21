package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.example.kholt6406.frisbee_app.sprites.Player;

public class PlayState extends State {
    private Player player;
    private Stage stage;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture background;
    private Texture scoreboard;
    FreeTypeFontGenerator freeTypeFontGenerator=new FreeTypeFontGenerator(Gdx.files.internal("lucon.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
    private EventListener listener;
    int scoreboardX;
    int scoreboardY;
    BitmapFont font;
    double playTime=15;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        player=new Player(50,100);
        //cam.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        background = new Texture("field_background.png");
        scoreboard=new Texture("scoreboard.png");

        freeTypeFontParameter.size=100;
        font=freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        scoreboardX=(Gdx.graphics.getWidth()/2)-(scoreboard.getWidth()/2*3);
        scoreboardY=(Gdx.graphics.getHeight()-scoreboard.getHeight()*3)-100;
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
        touchpad.setBounds(30, 30, 400, 400);

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
        //sb.setProjectionMatrix(cam.combined);
        player.setX(player.getPosition().x + touchpad.getKnobPercentX()*player.getVelocity());
        player.setY(player.getPosition().y + touchpad.getKnobPercentY()*player.getVelocity());
        sb.begin();
        sb.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(player.getTexture(),player.getPosition().x,player.getPosition().y);
        sb.draw(scoreboard,scoreboardX,scoreboardY,scoreboard.getWidth()*3,scoreboard.getHeight()*3);
        font.draw(sb, clock(), scoreboardX + scoreboard.getWidth() - scoreboard.getWidth() / 4, scoreboardY+scoreboard.getHeight()/2);
        touchpad.draw(sb,1);
        sb.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {

    }

    public String clock(){
        playTime-=Gdx.graphics.getDeltaTime();
        String time;
        if (playTime>0) {
            time = Double.toString(Math.floor(playTime));
        }
        else {
            time="0";
        }
        if (time.indexOf('.') != -1){
            time=time.substring(0,time.indexOf('.'));
        }

        return time;
    }

}
