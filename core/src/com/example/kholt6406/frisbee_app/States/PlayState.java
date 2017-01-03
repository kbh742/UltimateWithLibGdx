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

    public final int WORLD_WIDTH=1920;
    public final int WORLD_HEIGHT=1080;
    float xScl =w/WORLD_WIDTH;
    float yScl =h/WORLD_HEIGHT;

    private Player player1;
    //private Player cpuPlayer;

    private Sprite disk;
    private Texture diskTexture;
    float diskWd;
    float diskHt;

    private Stage stage;
    private ImageButton pauseButton;
    private ImageButton.ImageButtonStyle pauseButtonStyle=new ImageButton.ImageButtonStyle();
    private Skin pauseButtonSkin=new Skin();
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
    boolean stopped=false;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        player1=new Player(600,600);
        //cpuPlayer = new Player(100, 50);
//        camera=new OrthographicCamera();
//        camera.setToOrtho(false,WORLD_WIDTH*xMultiplier,WORLD_HEIGHT*yMultiplier);
//        camera.position.set(0,0,0);
        background = new Texture("field_background.png");


/*        pauseButtonSkin.add("pauseButton","button_pause.png");
        pauseButtonStyle=new ImageButton.ImageButtonStyle();
        pauseButtonStyle.imageUp=pauseButtonSkin.getDrawable("pauseButton");
        pauseButton=new ImageButton(pauseButtonStyle);
        pauseButton.setBounds(w*xScl,(h-pauseButton.getHeight())*yScl,pauseButton.getWidth()*xScl,pauseButton.getHeight()*xScl);*/

        scbdTexture = new Texture("scoreboard.png");
        scoreboard=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*2*xScl;
        scbdHt = scbdTexture.getHeight()*2*yScl;
        scoreboard.setSize(scbdWd, scbdHt);
        scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardY=h-scbdHt;
        scoreboard.setX(scoreboardX);
        scoreboard.setY(scoreboardY);

        freeTypeFontParameter.size=(int)(9*scbdHt)/18;
        clockText =freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        scoreText1 = freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        scoreText2 = freeTypeFontGenerator.generateFont(freeTypeFontParameter);

        playerWd = player1.getTexture().getWidth()/2;
        playerHt = player1.getTexture().getHeight()/2;

        diskTexture = new Texture("frisbee_snake.png");
        disk = new Sprite(diskTexture);
        diskWd = diskTexture.getWidth()*xScl;
        diskHt = diskTexture.getHeight()*yScl;
        disk.setSize(diskWd, diskHt);
        disk.setX(player1.getPosition().x + playerWd - diskWd/2);
        disk.setY(player1.getPosition().y + playerHt - diskHt/2 + 200);

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
        touchpad.setBounds(30*xScl, 30*yScl, 400*xScl, 400*yScl);



        stage = new Stage();
        stage.addActor(touchpad);
        //stage.addActor(pauseButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new MenuState(gsm));
            dispose();
        }
        if (pauseButton.isPressed()){

        }
    }

    @Override
    protected void update(float dt) {
        handleInput();
//        if(team1Scored == true){
//            teamScore1++;
//        }
//        if(team2Scored == true){
//            teamScore2++;
//        }
        player1.update(dt);

        player1.setHoldingDisk(true);
        if(player1.hasDisk()){
            player1.setVelocity(0);
        }
        xPos = player1.getPosition().x;
        yPos = player1.getPosition().y;
        float deltaX=touchpad.getKnobPercentX();
        float deltaY=touchpad.getKnobPercentY();
        deltaX *= xScl;
        deltaY *= yScl;
        player1.setX(xPos+deltaX*player1.getVelocity());
        player1.setY(yPos+deltaY*player1.getVelocity());
        float deltaXAbs=Math.abs(deltaX);
        float deltaYAbs=Math.abs(deltaY);
        int xMultiplier=1;
        int yMultiplier=1;
        if (deltaX != 0  && deltaY != 0) {

            angle = (float) Math.toDegrees(Math.atan(deltaXAbs / deltaYAbs));
            if (deltaX > 0 && deltaY > 0) {
                rotation = 360 - angle;
                xMultiplier=-1;
            } else if (deltaX > 0 && deltaY < 0) {
                rotation = 180 + angle;
                yMultiplier=-1;
            } else if (deltaX < 0 && deltaY < 0) {
                rotation = 180 - angle;
                yMultiplier=-1;
                xMultiplier=-1;
            } else {
                rotation = angle;
            }

        }

            disk.setX(player1.getPosition().x + playerWd - diskWd/2 + xMultiplier*(200*(float)Math.cos(Math.toRadians(angle))));
            disk.setY(player1.getPosition().y + playerHt - diskHt/2 + yMultiplier*(200*(float)Math.sin(Math.toRadians(angle))));
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
        sb.draw(player1.getTexture(),xPos,yPos,playerWd* xScl,playerHt* yScl,playerWd*2*xScl,playerHt*2*yScl,1,1,rotation+90,0,0,Math.round(playerWd*2),Math.round(playerHt*2),false,false);
        disk.draw(sb);
        //sb.draw(cpuPlayer.getTexture(), cpuPlayer.getPosition().x, cpuPlayer.getPosition().y);
        scoreboard.draw(sb);
        clockText.draw(sb, clock(), scoreboardX + (79*scbdWd)/112, scoreboardY + (5*scbdHt)/8);
        scoreText1.draw(sb, score1(), scoreboardX + (2*scbdWd)/16, scoreboardY + (5*scbdHt)/8);
        scoreText2.draw(sb, score2(), scoreboardX + (35*scbdWd)/64, scoreboardY + (5*scbdHt)/8);
        //pauseButton.draw(sb,1);
        font.draw(sb, clock(), scoreboardX + (79*scbdWd)/112, scoreboardY + (2*scbdHt)/3);
        touchpad.draw(sb,1);
        sb.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {

    }

    public String clock(){
        String time;
        if (!stopped) {
            playTime -= Gdx.graphics.getDeltaTime();
        }
            int seconds = (int)playTime % 60;
            int minutes = (int)Math.floor(playTime/60);

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
        //score1 = "" + teamScore1;

        return score1;
    }

    public String score2(){
        String score2 = "0";
        //score2 = "" + teamScore2;

        return score2;
    }

}
