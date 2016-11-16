package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.kholt6406.frisbee_app.sprites.Player;

public class PlayState extends State {
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    float scbdWd;
    float scbdHt;

    float xPos=800;
    float yPos=800;
    float playerWd;
    float playerHt;

/*    public final int WORLD_WIDTH=50;
    public final int WORLD_HEIGHT=25;
    float aspectRatio;*/
    private Player player1;
    private Player cpuPlayer;
    private Stage stage;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture background;
    private Sprite scoreboard;
    private Texture scbdTexture;
    FreeTypeFontGenerator freeTypeFontGenerator=new FreeTypeFontGenerator(Gdx.files.internal("lucon.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
    float scoreboardX;
    float scoreboardY;
    float rotation;
    float angle;
    BitmapFont font;
    double playTime=300;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        player1=new Player(800,800);
        //cpuPlayer = new Player(100, 50);
        //cam.setToOrtho(false,w,h);
        camera = new PerspectiveCamera();
        fitViewport = new FitViewport(w,h, camera);
        background = new Texture("field_background.png");

        scbdTexture = new Texture("scoreboard.png");
        scoreboard=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*3;
        scbdHt = scbdTexture.getHeight()*3;
        scoreboard.setSize(scbdWd, scbdHt);

        freeTypeFontParameter.size=100;
        font=freeTypeFontGenerator.generateFont(freeTypeFontParameter);

        scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardY=h-scbdHt;
        scoreboard.setX(scoreboardX);
        scoreboard.setY(scoreboardY);

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

        //Gdx.input.setCatchBackKey(true);

        stage = new Stage();
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new MenuState(gsm));
            dispose();
        }
    }

    @Override
    protected void update(float dt) {
        //handleInput();
        player1.update(dt);
        xPos = player1.getPosition().x;
        yPos = player1.getPosition().y;
        float deltaX=touchpad.getKnobPercentX();
        float deltaY=touchpad.getKnobPercentY();
        player1.setX(xPos+deltaX*player1.getVelocity());
        player1.setY(yPos+deltaY*player1.getVelocity());
        float deltaXAbs=Math.abs(deltaX);
        float deltaYAbs=Math.abs(deltaY);
        if (deltaX != 0  && deltaY != 0) {

            angle = (float) Math.toDegrees(Math.atan(deltaXAbs / deltaYAbs));
            if (deltaX > 0 && deltaY > 0) {
                rotation = 360 - angle;
            } else if (deltaX > 0 && deltaY < 0) {
                rotation = 180 + angle;
            } else if (deltaX < 0 && deltaY < 0) {
                rotation = 180 - angle;
            } else {
                rotation = angle;
            }
        }





        if(xPos + playerWd <= 0) {
           player1.setX(xPos + 1);
        }
        if(xPos + playerWd >= w) {
            player1.setX(xPos - 1);
        }
        if(yPos + playerHt <= 0) {
            player1.setY(yPos +1);
        }
        if(yPos + playerHt >= h) {
            player1.setY(yPos - 1);
        }

//        cpuPlayer.update(dt);
//
//        cpuPlayer.setVelocity(5);
//        if(cpuPlayer.getPosition().x + cpuPlayer.getTexture().getWidth()/2 >= w){
//            cpuPlayer.setX(w - cpuPlayer.getTexture().getWidth()/2 - 1);
//            cpuPlayer.setVelocity((int) cpuPlayer.getVelocity() * -1);
//        }
//        cpuPlayer.setX(cpuPlayer.getPosition().x + cpuPlayer.getVelocity());
    }

    @Override
    protected void render(SpriteBatch sb) {
        //sb.setProjectionMatrix(cam.combined);

        sb.begin();
        sb.draw(background, 0,0, w, h);
        sb.draw(player1.getTexture(),xPos,yPos,playerWd,playerHt,playerWd*2,playerHt*2,1,1,rotation+90,0,0,Math.round(playerWd*2),Math.round(playerHt*2),false,false);
        //sb.draw(cpuPlayer.getTexture(), cpuPlayer.getPosition().x, cpuPlayer.getPosition().y);
        scoreboard.draw(sb);
        font.draw(sb, clock(), scoreboardX + (39*scbdWd)/56, scoreboardY + (2*scbdHt)/3);
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
        int seconds = (int)playTime % 60;
        int minutes = (int)Math.floor(playTime/60);
        String time;
        if (playTime>0 && seconds >= 10) {
            time = minutes + ":" + seconds;
        }
        else if(playTime>0 && seconds<10){
            time = minutes + ":" + "0" + seconds;
        }
        else {
            time="0:00";
        }
//        if (time.indexOf('.') != -1){
//            time=time.substring(0,time.indexOf('.'));
//        }

        return time;
    }

}
