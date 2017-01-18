package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.example.kholt6406.frisbee_app.sprites.Player;

public class PlayState extends State implements GestureDetector.GestureListener{
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
    private Player cpuPlayer;

    private Sprite disk;
    private Texture diskTexture;
    float diskWd;
    float diskHt;

    private Stage stage;
    private ImageButton pauseButton;
    private ImageButton.ImageButtonStyle pauseButtonStyle;
    private Skin pauseButtonSkin;
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
    boolean before;

    int drawCounter = 0;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        player1=new Player(600,600);
        //cpuPlayer = new Player(800, 800);
//        camera=new OrthographicCamera();
//        camera.setToOrtho(false,WORLD_WIDTH*xMultiplier,WORLD_HEIGHT*yMultiplier);
//        camera.position.set(0,0,0);
        background = new Texture("field_background.png");

        pauseButtonSkin= new Skin();   //create button skin
        pauseButtonSkin.add("pauseButton", new Texture("button_pause.png"));    //add the image to the skin
        pauseButtonStyle= new ImageButton.ImageButtonStyle();  //create button style
        pauseButtonStyle.imageUp = pauseButtonSkin.getDrawable("pauseButton");  //sets the button appearance when it is not pressed
        pauseButton = new ImageButton(pauseButtonStyle);    //initializes the ImageButton with the created style as a parameter
        pauseButton.setBounds(0,(WORLD_HEIGHT-pauseButton.getHeight())*yScl,pauseButton.getWidth()*xScl,pauseButton.getHeight()*yScl);

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

        playerWd = player1.getTexture().getWidth();
        playerHt = player1.getTexture().getHeight();

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
        stage.addActor(pauseButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new MenuState(gsm));
            dispose();
        }
        if(pauseButton.isPressed()&&(!pauseButton.isDisabled())){
            pauseButton.setDisabled(true);
            drawCounter=0;
            stopped = !stopped;

        }
    }


    @Override
    protected void update(float dt) {
        handleInput();
        if (!stopped) {
//        if(team1Scored == true){
//            teamScore1++;
//        }
//        if(team2Scored == true){
//            teamScore2++;
//        }
            player1.update(dt);

            //player1.setHoldingDisk(true);
            if (player1.hasDisk()) {
                player1.setVelocity(0);
            }
            xPos = player1.getPosition().x;
            yPos = player1.getPosition().y;
            float deltaX = touchpad.getKnobPercentX();
            float deltaY = touchpad.getKnobPercentY();
            deltaX *= xScl;
            deltaY *= yScl;
            player1.setX(xPos + deltaX * player1.getVelocity());
            player1.setY(yPos + deltaY * player1.getVelocity());

            if (deltaX != 0 && deltaY != 0) {
                rotation = (float) Math.toDegrees(Math.atan(deltaY / deltaX));
                if (deltaX < 0) {
                    rotation += 180;
                }
                int r = 150;
                disk.setX((float)(player1.getPosition().x+(playerWd*xScl)/2-diskWd/2 + (r*(deltaX/Math.sqrt(deltaX*deltaX+deltaY*deltaY)))));
                disk.setY((float)(player1.getPosition().y+(playerHt*yScl)/2-diskHt/2 + (r*(deltaY/Math.sqrt(deltaX*deltaX+deltaY*deltaY)))));
            }


            if (xPos + playerWd <= 0) {
                player1.setX(xPos + 1);
            }
            if (xPos + playerWd >= w) {
                player1.setX(xPos - 1);
            }
            if (yPos + playerHt <= 0) {
                player1.setY(yPos + 1);
            }
            if (yPos + playerHt >= h) {
                player1.setY(yPos - 1);
            }

/*        cpuPlayer.update(dt);

        cpuPlayer.setVelocity(5);*/
/*        if(cpuPlayer.getPosition().x + cpuPlayer.getTexture().getWidth()/2 >= w){
            cpuPlayer.setX(w - cpuPlayer.getTexture().getWidth()/2 - 1);
            cpuPlayer.setVelocity((int) cpuPlayer.getVelocity() * -1);
        }
        cpuPlayer.setX(cpuPlayer.getPosition().x + cpuPlayer.getVelocity());*/
        }
    }

    @Override
    protected void render(SpriteBatch sb) {
        //sb.setProjectionMatrix(camera.combined);
        drawCounter++;
        sb.begin();
        sb.draw(background, 0,0, w, h);
        sb.draw(player1.getTexture(),xPos,yPos,playerWd/2*xScl,playerHt/2*yScl,playerWd*xScl,playerHt*yScl,1,1,rotation,0,0,Math.round(playerWd),Math.round(playerHt),false,false);
        disk.draw(sb);
        //sb.draw(cpuPlayer.getTexture(),cpuPlayer.getPosition().x,cpuPlayer.getPosition().y,cpuPlayer.getWidth()/2*xScl,cpuPlayer.getHeight()/2*yScl,cpuPlayer.getWidth()*xScl,cpuPlayer.getHeight()*yScl,1,1,rotation,0,0,Math.round(cpuPlayer.getWidth()),Math.round(cpuPlayer.getHeight()),false,false);
        scoreboard.draw(sb);
        clockText.draw(sb, clock(), scoreboardX + (79*scbdWd)/112, scoreboardY + (5*scbdHt)/8);
        scoreText1.draw(sb, score1(), scoreboardX + (2*scbdWd)/16, scoreboardY + (5*scbdHt)/8);
        scoreText2.draw(sb, score2(), scoreboardX + (35*scbdWd)/64, scoreboardY + (5*scbdHt)/8);
        pauseButton.draw(sb,1);
        touchpad.draw(sb,1);

        if (drawCounter>=10){
            pauseButton.setDisabled(false);
        }

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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        Gdx.app.error("Swipe", "Completed");
        if(Math.abs(velocityX)>Math.abs(velocityY)){
            if(velocityX>0){
                Gdx.app.error("Swipe", "Right");
            }else if(velocityX<0){
                Gdx.app.error("Swipe", "Left");
            }else{

            }
        }else{
            Gdx.app.error("Swipe", "Up or Down");

        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
