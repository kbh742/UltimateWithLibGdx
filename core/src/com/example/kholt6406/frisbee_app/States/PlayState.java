package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
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

import java.util.ArrayList;

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
    float diskVx;
    float diskVy;
    float diskCurve;

    int catchableDistance;

    boolean p1Threw = false;
    boolean cpuThrew = false;

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
    float cpuRotation;
    BitmapFont clockText;
    BitmapFont scoreText1;
    BitmapFont scoreText2;
    double playTime=300;
    boolean stopped=false;
    int team1Score=0;
    int team2Score=0;

    int drawCounter = 0;
    boolean inLeftEndZone=false;
    boolean inRightEndZone=false;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        player1=new Player(400,400);
        cpuPlayer = new Player(800, 600);
        cpuRotation = 0;
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
        disk.setX(player1.getPosition().x + playerWd/2 - diskWd/2);
        disk.setY(player1.getPosition().y + playerHt/2 - diskHt/2 + 40);

        catchableDistance = 100;

        player1.setHoldingDisk(true);

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
        //Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new GestureDetector(this)));
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
            player1.update(dt);

            if (player1.hasDisk()) {
                player1.setVelocity(0);
            }
            else if (!player1.hasDisk()){
                player1.setVelocity(10);
            }
            xPos = player1.getPosition().x;
            yPos = player1.getPosition().y;
            float deltaX = touchpad.getKnobPercentX();
            float deltaY = touchpad.getKnobPercentY();
            //deltaX *= xScl;
            //deltaY *= yScl;
            player1.setX(xPos + deltaX * player1.getVelocity());
            player1.setY(yPos + deltaY * player1.getVelocity());

            if (deltaX != 0 && deltaY != 0) {
                rotation = (float) Math.toDegrees(Math.atan(deltaY / deltaX));
                if (deltaX < 0) {
                    rotation += 180;
                }
                int r = 50;
                if(player1.hasDisk()) {
                    disk.setX((float) (player1.getPosition().x + (playerWd * xScl) / 2 - diskWd / 2 + (r * (deltaX / Math.sqrt(deltaX * deltaX + deltaY * deltaY)))));
                    disk.setY((float) (player1.getPosition().y + (playerHt * yScl) / 2 - diskHt / 2 + (r * (deltaY / Math.sqrt(deltaX * deltaX + deltaY * deltaY)))));
                }
            }

            //Gdx.app.log("ROTATION", rotation + "");


            if (xPos + playerWd/2*xScl <= 0) {
                player1.setX(xPos + 1);
            }
            if (xPos + playerWd/2*xScl >= w) {
                player1.setX(xPos - 1);
            }
            if (yPos + playerHt/2*yScl <= 0) {
                player1.setY(yPos + 1);
            }
            if (yPos + playerHt/2*yScl >= h) {
                player1.setY(yPos - 1);
            }

            /*float sideV = sketchyJohnThrowMethod(5,1.57,1).get(0); //I need to come up with a way to do this better so it only calls John's method when a throw happens
            float straightV = sketchyJohnThrowMethod(5,1.57,1).get(1);
            diskCurve = sketchyJohnThrowMethod(5,1.57,1).get(2);
            sideV -= diskCurve;
            double alpha = Math.toRadians(rotation - 90);

            diskVx = (float) (Math.cos(alpha)*sideV - Math.sin(alpha)*straightV);
            diskVy = (float) (Math.sin(alpha)*sideV + Math.cos(alpha)*straightV);*/


            disk.setX(disk.getX() + diskVx);
            disk.setY(disk.getY() + diskVy);


            double player1DistToDisk = Math.sqrt(Math.pow(player1.getPosition().x+playerWd/2-(disk.getX()+diskWd/2),2) + Math.pow(player1.getPosition().y+playerHt/2-(disk.getY()+diskHt/2),2));
            //Gdx.app.log("LALALALA", player1DistToDisk + "");
            if(player1DistToDisk <= catchableDistance && !player1.hasDisk() && !p1Threw){
                player1.setHoldingDisk(true);
                diskVx = 0;
                diskVy = 0;
            }
            else if(player1DistToDisk > catchableDistance){
                player1.setHoldingDisk(false);
                p1Threw = false;
            }


            cpuPlayer.update(dt);
            float cpuX = cpuPlayer.getPosition().x;
            float cpuY = cpuPlayer.getPosition().y;
            float cpuWd = cpuPlayer.getTexture().getWidth();
            float cpuHt = cpuPlayer.getTexture().getHeight();
            double cpuDistToDisk = Math.sqrt(Math.pow(cpuX+cpuWd/2-(disk.getX()+diskWd/2),2) + Math.pow(cpuY+cpuHt/2-(disk.getY()+diskHt/2),2));

            if(cpuDistToDisk <= 300 && diskVx != 0 && diskVy != 0){
                cpuRotation = (float) Math.toDegrees(Math.atan(diskVy/diskVx));
                if(diskVx < 0){
                    cpuRotation+= 180;
                }
                if(cpuRotation < 0){
                    cpuRotation+=360;
                }
                //cpuRotation+=180;
            }
            Gdx.app.log("CPUROTATION", cpuRotation + "");

            if(cpuDistToDisk <= catchableDistance && !cpuPlayer.hasDisk() && !cpuThrew){
                cpuPlayer.setHoldingDisk(true);
                diskVx = 0;
                diskVy = 0;
            }
            else if(cpuDistToDisk > catchableDistance ){
                cpuPlayer.setHoldingDisk(false);
                cpuThrew = false;
            }

            if (xPos+playerWd/2*xScl <= w/6 && !inLeftEndZone){
                team1Score++;
                inLeftEndZone=true;
            } else if (xPos+playerWd/2*xScl > w/6){
                inLeftEndZone=false;
            }

            if (xPos+playerWd/2*xScl >= w-w/6 && !inRightEndZone){
                team2Score++;
                inRightEndZone=true;
            } else if (xPos+playerWd/2*xScl < w-w/6){
                inRightEndZone=false;
            }

//            cpuPlayer.setVelocity(1);
//            if(cpuPlayer.getPosition().x + cpuPlayer.getTexture().getWidth()/2 >= w){
//                cpuPlayer.setX(w - cpuPlayer.getTexture().getWidth()/2 - 1);
//                cpuPlayer.setVelocity((int) cpuPlayer.getVelocity() * -1);
//            }
//            cpuPlayer.setX(cpuPlayer.getPosition().x + cpuPlayer.getVelocity());
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
        sb.draw(cpuPlayer.getTexture(),cpuPlayer.getPosition().x,cpuPlayer.getPosition().y,cpuPlayer.getTexture().getWidth()/2*xScl,cpuPlayer.getTexture().getHeight()/2*yScl,cpuPlayer.getTexture().getWidth()*xScl,cpuPlayer.getTexture().getHeight()*yScl,1,1,cpuRotation,0,0,Math.round(cpuPlayer.getTexture().getWidth()),Math.round(cpuPlayer.getTexture().getHeight()),false,false);
        scoreboard.draw(sb);
        clockText.draw(sb, clock(), scoreboardX + (79*scbdWd)/112, scoreboardY + (5*scbdHt)/8);
        scoreText1.draw(sb, Integer.toString(team1Score), scoreboardX + (2*scbdWd)/16, scoreboardY + (5*scbdHt)/8);
        scoreText2.draw(sb, Integer.toString(team2Score), scoreboardX + (35*scbdWd)/64, scoreboardY + (5*scbdHt)/8);
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

   /* public ArrayList<Float> sketchyJohnThrowMethod(float velocity, double theta, float acceleration){
        ArrayList<Float> values = new ArrayList<Float>();

        float sideV = velocity*(float)Math.cos(theta);
        float straightV = velocity*(float)Math.sin(theta);

        values.add(sideV);
        values.add(straightV);
        values.add(acceleration);

        return values;
    }*/

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
        boolean validThrow = false;

        Gdx.app.log("Swipe", "Completed");

        if(player1.hasDisk()){
            p1Threw = true;
            player1.setHoldingDisk(false);
            validThrow = true;
        }
        else if(cpuPlayer.hasDisk()){
            cpuThrew = true;
            cpuPlayer.setHoldingDisk(false);
            validThrow = true;
        }
        if(validThrow) {
            diskVx = 5 * (velocityX / ((float) Math.sqrt(velocityX * velocityX + velocityY * velocityY))); //makes total V=5 with x and y components matching the proportions of the swipe
            diskVy = -5 * (velocityY / ((float) Math.sqrt(velocityX * velocityX + velocityY * velocityY)));
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
