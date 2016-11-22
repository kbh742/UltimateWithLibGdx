package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.example.kholt6406.frisbee_app.sprites.Player;

public class PlayState extends State {
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    float scbdWd;
    float scbdHt;

    float xPos=600;
    float yPos=600;
    float playerWd;
    float playerHt;

    OrthographicCamera camera;

    public final int WORLD_WIDTH=50;
    public final int WORLD_HEIGHT=25;
    float xMultiplier=w/WORLD_WIDTH;
    float yMultiplier=h/WORLD_HEIGHT;

    private Player player1;
    //private Player cpuPlayer;
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
    BitmapFont clockText;
    BitmapFont scoreText1;
    BitmapFont scoreText2;
    double playTime=300;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        player1=new Player(800,800);
        //cpuPlayer = new Player(100, 50);
//        camera=new OrthographicCamera();
//        camera.setToOrtho(false,WORLD_WIDTH*xMultiplier,WORLD_HEIGHT*yMultiplier);
//        camera.position.set(0,0,0);
        background = new Texture("field_background.png");

        scbdTexture = new Texture("scoreboard.png");
        scoreboard=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*2;
        scbdHt = scbdTexture.getHeight()*2;
        scoreboard.setSize(scbdWd, scbdHt);
        scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardY=h-scbdHt;
        scoreboard.setX(scoreboardX);
        scoreboard.setY(scoreboardY);

        freeTypeFontParameter.size=(int)(9*scbdHt)/16;
        clockText =freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        scoreText1 = freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        scoreText2 = freeTypeFontGenerator.generateFont(freeTypeFontParameter);


        playerWd = player1.getTexture().getWidth()/2;
        playerHt = player1.getTexture().getHeight()/2;
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
        //sb.setProjectionMatrix(camera.combined);

        sb.begin();
        sb.draw(background, 0,0, w, h);
        sb.draw(player1.getTexture(),xPos,yPos,playerWd,playerHt,playerWd*2,playerHt*2,1,1,rotation+90,0,0,Math.round(playerWd*2),Math.round(playerHt*2),false,false);
        //sb.draw(cpuPlayer.getTexture(), cpuPlayer.getPosition().x, cpuPlayer.getPosition().y);
        scoreboard.draw(sb);
        clockText.draw(sb, clock(), scoreboardX + (79*scbdWd)/112, scoreboardY + (2*scbdHt)/3);
        scoreText1.draw(sb, score1(), scoreboardX + (2*scbdWd)/16, scoreboardY + (2*scbdHt)/3);
        scoreText2.draw(sb, score2(), scoreboardX + (17*scbdWd)/32, scoreboardY + (2*scbdHt)/3);
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

        return time;
    }

    public String score1(){
        String score1 = "0";

        return score1;
    }

    public String score2(){
        String score2 = "0";

        return score2;
    }

}
