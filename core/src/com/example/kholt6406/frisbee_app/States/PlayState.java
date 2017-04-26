package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.example.kholt6406.frisbee_app.sprites.Player;
import com.example.kholt6406.frisbee_app.swipe.SwipeHandler;
import com.example.kholt6406.frisbee_app.swipe.mesh.SwipeTriStrip;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayState extends State implements GestureDetector.GestureListener{
    static float w = Gdx.graphics.getWidth();
    static float h = Gdx.graphics.getHeight();
    float scbdWd;
    float scbdHt;

    float xPos;
    float yPos;
    float playerWd;
    float playerHt;

    OrthographicCamera camera;

    public static final int WORLD_WIDTH=1920;
    public static final int WORLD_HEIGHT=1080;
    static float xScl =w/WORLD_WIDTH;
    static float yScl =h/WORLD_HEIGHT;

    public static final int GAME_TIME=180;

    public static final float CAMERA_HEIGHT=500;

    private Player player1;
    private Player cpuPlayer;
    private double player1DistToDisk;
    private double cpuDistToDisk;

    private Player enemy1;
    private Player enemy2;
    private double enemy1distToDisk;
    private double enemy2distToDisk;

    private Sprite disk;
    private Texture diskTexture;
    float diskWd;
    float diskHt;
    float diskVx;
    float diskVy;
    float diskCurve;
    float sideV;
    float straightV;

    int catchableDistance;

    boolean hasPossesion;

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
    private Sprite scoreboardLeft;
    private Sprite scoreboardRight;
    private Texture scbdTexture;
    FreeTypeFontGenerator freeTypeFontGenerator=new FreeTypeFontGenerator(Gdx.files.internal("lucon.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
    float scoreboardX;
    float scoreboardY;
    //float rotation;
    float cpuRotation;
    float enemy1Rotation;
    float enemy2Rotation;
    BitmapFont clockText;
    BitmapFont scoreText1;
    BitmapFont scoreText2;
    double playTime=GAME_TIME;
    boolean stopped=false;
    boolean before;
    ShapeRenderer shapes;
    SwipeTriStrip tris;
    SwipeHandler swipe;
    Texture tex;
    int team1Score=0;
    int team2Score=0;
    static int points;
    private int stallCounter = 0;
    private Texture stallCount0;
    private Texture stallCount1;
    private Texture stallCount2;
    private Texture stallCount3;
    private Texture stallCount4;
    private Texture stallCount5;
    private Texture stallCount6;
    private Texture stallCount7;
    private Texture stallCount8;
    private Texture stallCount9;
    private Texture stallCount10;
    private Texture selectRing;
    private double caughtTime;
    private double stallTime;
    private boolean onOffense;
    private boolean changingPoss;
    float diskHeight;
    double timeOfThrow;
    double timeToVertex;
    double airTime;
    float playerHeight;
    boolean diskInAir;
    boolean isShortPass;
    //float player1VelocityX;
    //float player1VelocityY;
    //float cpuVelocityX;
    //float cpuVelocityY;
    //float enemy1VelocityX;
    //float enemy1VelocityY;
    //float enemy2VelocityX;
    //float enemy2VelocityY;


    int drawCounter = 0;
    boolean inLeftEndZone=false;
    boolean inRightEndZone=false;
    float playerRotationAtTimeOfThrow;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        player1=new Player();
        Texture blueTeamPlayer = new Texture("idle.png");
        player1.setTexture(blueTeamPlayer);
        playerWd = player1.getTexture().getWidth();
        playerHt = player1.getTexture().getHeight();
        player1.setHoldingDisk(true);
        player1.setX(w/6 - playerWd/2);
        Gdx.app.log("xpos", ""+player1.getX());
        player1.setY((h*2)/3 - playerHt/2);

        cpuPlayer = new Player();
        cpuPlayer.setTexture(blueTeamPlayer);
        cpuPlayer.setX(w/6 - playerWd/2);
        cpuPlayer.setY((h*2)/5 - playerHt/2);

        enemy1 = new Player();
        enemy1.setTexture(blueTeamPlayer);
        enemy1.setX(5*w/6 - playerWd/2);
        enemy1.setY(h/4 - playerHt/2);

        enemy2 = new Player();
        enemy2.setTexture(blueTeamPlayer);
        enemy2.setX(5*w/6 - playerWd/2);
        enemy2.setY(3*h/4 - playerHt/2);
        //cpuRotation = 0;
        //enemy1Rotation=0;
        //enemy2Rotation=0;
//        camera=new OrthographicCamera();
//        camera.setToOrtho(false,WORLD_WIDTH*xMultiplier,WORLD_HEIGHT*yMultiplier);
//        camera.position.set(0,0,0);
        background = new Texture("field_background.png");

        stallCount0 = new Texture("stall_0.png");
        stallCount1 = new Texture("stall_1.png");
        stallCount2 = new Texture("stall_2.png");
        stallCount3 = new Texture("stall_3.png");
        stallCount4 = new Texture("stall_4.png");
        stallCount5 = new Texture("stall_5.png");
        stallCount6 = new Texture("stall_6.png");
        stallCount7 = new Texture("stall_7.png");
        stallCount8 = new Texture("stall_8.png");
        stallCount9 = new Texture("stall_9.png");
        stallCount10 = new Texture("stall_10.png");
        selectRing = new Texture("select_ring.png");

        caughtTime = 0;
        stallTime = 0;
        changingPoss = false;
        playerHeight = -50;
        diskHeight = playerHeight;
        diskInAir = false;
        isShortPass = false;


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
        //scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardX=w-scbdWd;
        scoreboardY=h-scbdHt;
        scoreboard.setX(scoreboardX);
        scoreboard.setY(scoreboardY);

        scbdTexture = new Texture("scoreboard_left_offense.png");
        scoreboardLeft=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*2*xScl;
        scbdHt = scbdTexture.getHeight()*2*yScl;
        scoreboardLeft.setSize(scbdWd, scbdHt);
        //scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardX=w-scbdWd;
        scoreboardY=h-scbdHt;
        scoreboardLeft.setX(scoreboardX);
        scoreboardLeft.setY(scoreboardY);

        scbdTexture = new Texture("scoreboard_right_offense.png");
        scoreboardRight=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*2*xScl;
        scbdHt = scbdTexture.getHeight()*2*yScl;
        scoreboardRight.setSize(scbdWd, scbdHt);
        //scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardX=w-scbdWd;
        scoreboardY=h-scbdHt;
        scoreboardRight.setX(scoreboardX);
        scoreboardRight.setY(scoreboardY);

        //Swipes

        tris = new SwipeTriStrip();
        swipe = new SwipeHandler(253);
        swipe.minDistance = 10;
        swipe.initialDistance = 10;
        tex = new Texture("gradient.png");
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        shapes = new ShapeRenderer();


        freeTypeFontParameter.size=(int)(9*scbdHt)/18;
        clockText =freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        scoreText1 = freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        scoreText2 = freeTypeFontGenerator.generateFont(freeTypeFontParameter);



        diskTexture = new Texture("frisbee_snake.png");
        disk = new Sprite(diskTexture);
        diskWd = diskTexture.getWidth()*xScl;
        diskHt = diskTexture.getHeight()*yScl;
        disk.setSize(diskWd, diskHt);

        catchableDistance = 100;

        disk.setX(player1.getX() + playerWd/2);
        disk.setY(player1.getY() + playerHt/2);

//        player1VelocityX = 0;
//        enemy1VelocityX = 0;
//        enemy2VelocityX = 0;
//        cpuVelocityX = 0;
//        player1VelocityY = 0;
//        enemy1VelocityY = 0;
//        enemy2VelocityY = 0;
//        cpuVelocityY = 0;

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
        touchpad.setBounds(30*xScl, 30*yScl, 200*xScl, 200*yScl);


        stage = new Stage();
        stage.addActor(touchpad);
        stage.addActor(pauseButton);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new GestureDetector(this), swipe));
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



            if (player1.hasDisk())
            {
                //player1.setVelocity(0);
                stallTime = (GAME_TIME-playTime) - caughtTime;

                onOffense = true;
                diskHeight = 0;
            }
            else if (!player1.hasDisk()){
                player1.setVX((float)Math.sqrt(50));
                player1.setVY((float)Math.sqrt(50));
                airTime = (GAME_TIME-playTime) - timeOfThrow;
                //float c = 150;
                float c = 150;
                if(Math.abs(diskCurve)>0.1){
                    diskHeight = (float) (1.5*(((-1*Math.abs(diskCurve*c))/(timeToVertex*timeToVertex))*(airTime-timeToVertex)*(airTime-timeToVertex)+Math.abs(diskCurve)*c+playerHeight));
                } else if (!isShortPass){
                    diskHeight = (float) (((-1*Math.abs(3*straightV)*(c/30))/(timeToVertex*timeToVertex))*(airTime-timeToVertex)*(airTime-timeToVertex)+Math.abs(straightV)*(c/30)-playerHeight);
                } else {
                    diskHeight = (float) (((-1*Math.abs(3*straightV)*(c/30)))*(airTime)*(airTime)+Math.abs(straightV)*(c/30)-playerHeight);
                }


                //Gdx.app.log("Disk", "" + diskHeight);
                if(diskHeight<-100){
                    diskHeight = -100;

                    if(airTime>0.2&&(diskInAir)){
                        Gdx.app.log("Disk", "Touched Ground");
                        diskInAir = false;
                        turnover();
                        changingPoss = true;
                    }
                }

            }
            xPos = player1.getX();
            yPos = player1.getY();
            float deltaX = touchpad.getKnobPercentX();
            float deltaY = touchpad.getKnobPercentY();
            if(changingPoss && !p1Threw){
                deltaX = 0;
                deltaY = 0;
            }

            player1.setX(xPos + deltaX * player1.getVelocity().x);
            player1.setY(yPos + deltaY * player1.getVelocity().y);
            enemy1.setX(enemy1.getX() + enemy1.getVelocity().x);
            enemy1.setY(enemy1.getY() + enemy1.getVelocity().y);
            enemy2.setX(enemy2.getX() + enemy2.getVelocity().x);
            enemy2.setY(enemy2.getY() + enemy2.getVelocity().y);
            cpuPlayer.setX(cpuPlayer.getX() + cpuPlayer.getVelocity().x);
            cpuPlayer.setY(cpuPlayer.getY() + cpuPlayer.getVelocity().y);


            if (deltaX != 0 && deltaY != 0) {
                player1.setRotation((float) Math.toDegrees(Math.atan(deltaY / deltaX)));
                if (deltaX < 0) {
                    player1.setRotation(player1.getRotation()+180);
                }
                if(player1.getRotation()<0){
                    player1.setRotation(player1.getRotation()+360);
                }
                int r = 50;
                if(player1.hasDisk()) {

                    disk.setX((float) (player1.getX() + (playerWd * xScl) / 2 - diskWd / 2 + (r * (deltaX / Math.sqrt(deltaX * deltaX + deltaY * deltaY)))));
                    disk.setY((float) (player1.getY() + (playerHt * yScl) / 2 - diskHt / 2 + (r * (deltaY / Math.sqrt(deltaX * deltaX + deltaY * deltaY)))));

                }
            }

            keepPlayerInBounds();

            if(!player1.hasDisk() && !cpuPlayer.hasDisk() && !enemy1.hasDisk() && !enemy2.hasDisk()) {
                sideV += diskCurve;
                double alpha = Math.toRadians(playerRotationAtTimeOfThrow - 90);

                diskVx = (float) (Math.cos(alpha) * sideV - Math.sin(alpha) * straightV);
                diskVy = (float) (Math.sin(alpha) * sideV + Math.cos(alpha) * straightV);
                if(!diskInAir){
                    diskVx = 0;
                    diskVy = 0;
                    //changingPoss = true;
                }
            }

            if(!player1.hasDisk() && !cpuPlayer.hasDisk() && !enemy1.hasDisk() && !enemy2.hasDisk()){
                keepDiskInBounds();
            }

            player1DistToDisk = getDistanceToDisk(player1);
            Gdx.app.log("lalala", ""+player1DistToDisk);
            Gdx.app.log("playerhasdisk?", "" + player1.hasDisk());
            //Gdx.app.log("p1threw", ""+p1Threw);
            if(player1DistToDisk <= catchableDistance && !player1.hasDisk() && !p1Threw && onOffense){
                player1.setHoldingDisk(true);
                diskVx = 0;
                diskVy = 0;
                caughtTime = GAME_TIME-playTime;
                Gdx.app.log("Catching", "Caught" + caughtTime);

            }
            else if(player1DistToDisk > catchableDistance){
                player1.setHoldingDisk(false);
                //p1Threw = false;
            }

            cpuDistToDisk = getDistanceToDisk(cpuPlayer);
            if(cpuDistToDisk <= 4*catchableDistance && diskVx != 0 && diskVy != 0){
                cpuRotation = (float) Math.toDegrees(Math.atan(diskVy/diskVx));
                if(diskVx < 0){
                    cpuRotation+= 180;
                }
                if(cpuRotation < 0){
                    cpuRotation+=360;
                }
                cpuRotation+=180;
            }
            if(cpuDistToDisk <= catchableDistance && !cpuPlayer.hasDisk() && !cpuThrew){
                cpuPlayer.setHoldingDisk(true);
                diskVx = 0;
                diskVy = 0;
                caughtTime = GAME_TIME-playTime;
            }
            else if(cpuDistToDisk > catchableDistance ){
                cpuPlayer.setHoldingDisk(false);
                cpuThrew = false;
            }
            if(cpuPlayer.hasDisk()){
                switchToPlayerWithDisk();
            }


            enemy1distToDisk = getDistanceToDisk(enemy1);
            if(enemy1distToDisk <= 4*catchableDistance && diskVx != 0 && diskVy != 0){
                enemy1Rotation = (float) Math.toDegrees(Math.atan(diskVy/diskVx));
                if(diskVx < 0){
                    enemy1Rotation+= 180;
                }
                if(enemy1Rotation < 0){
                    enemy1Rotation+=360;
                }
                enemy1Rotation+=180;
            }

            //Gdx.app.log("Height of Disk= ", ""+diskHeight);
            if(enemy1distToDisk <= catchableDistance && !enemy1.hasDisk() && diskHeight < 80){
                onOffense = false;
                diskInAir = false;
                diskHeight = -50;
                enemy1.setHoldingDisk(true);
                diskVx = 0;
                diskVy = 0;
            }
            else if(enemy1distToDisk > catchableDistance ){
                enemy1.setHoldingDisk(false);
            }


            enemy2distToDisk = getDistanceToDisk(enemy2);
            if(enemy2distToDisk <= 4*catchableDistance && diskVx != 0 && diskVy != 0){
                enemy2Rotation = (float) Math.toDegrees(Math.atan(diskVy/diskVx));
                if(diskVx < 0){
                    enemy2Rotation+= 180;
                }
                if(enemy2Rotation < 0){
                    enemy2Rotation+=360;
                }
                enemy2Rotation+=180;
            }
            if(enemy2distToDisk <= catchableDistance && !enemy2.hasDisk() && diskHeight < 80){
                onOffense = false;
                diskInAir = false;
                diskHeight = -50;
                enemy2.setHoldingDisk(true);
                diskVx = 0;
                diskVy = 0;
            }
            else if(enemy2distToDisk > catchableDistance ){
                enemy2.setHoldingDisk(false);
            }


            if(!player1.hasDisk()){
                disk.setX(disk.getX() + diskVx);
                disk.setY(disk.getY() + diskVy);
            }


            if (disk.getX()+diskWd/2 <= w/6 && !inLeftEndZone && !player1.hasDisk() && !cpuPlayer.hasDisk() && (enemy1.hasDisk() || enemy2.hasDisk())){
                team1Score++;
                inLeftEndZone=true;
            } else if (disk.getX()+diskWd/2 > w/6){
                inLeftEndZone=false;
            }

            if (disk.getX()+diskWd/2*xScl >= w-w/6 && !inRightEndZone && player1.hasDisk()){
                team2Score++;
                inRightEndZone=true;
                resetAfterScore();
            } else if (disk.getX()+diskWd/2*xScl < w-w/6){
                inRightEndZone=false;
            }

        }
        if (changingPoss && !stopped){
            if(!p1Threw){
                float xDist = (disk.getX()+diskWd/2) - (player1.getX()+playerWd/2);
                float yDist = (disk.getY()+diskHt/2) - (player1.getY()+playerHt/2);
                float p1Vx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float p1Vy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                player1.setVX(p1Vx);
                player1.setVY(p1Vy);
                Gdx.app.log("Possession", "" + player1.getVelocity());
                //player1.setX((player1.getX() + p1Vx));
                //player1.setY((player1.getY() + p1Vy));

                if(getDistanceToDisk(player1) < catchableDistance){
                    changingPoss = false;
                    //diskInAir =
                }
            }
            else if(enemy1distToDisk < enemy2distToDisk){
                float xDist = (disk.getX()+diskWd/2) - (enemy1.getX()+playerWd/2);
                float yDist = (disk.getY()+diskHt/2) - (enemy1.getY()+playerHt/2);
                float e1Vx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float e1Vy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                enemy1.setVX(e1Vx);
                enemy1.setVY(e1Vy);
                Gdx.app.log("Possession", "" + enemy1.getVelocity());

                enemy1Rotation = (float) Math.toDegrees(Math.atan(yDist/xDist));
                if(xDist < 0){
                    enemy1Rotation+= 180;
                }
                if(enemy1Rotation < 0){
                    enemy1Rotation+=360;
                }
                Gdx.app.log("enemy1distToDisk", ""+ enemy1distToDisk);
                if(getDistanceToDisk(enemy1) < catchableDistance){
                    changingPoss = false;
                    p1Threw = false;
                }
            }
            else{
                float xDist = (disk.getX()+diskWd/2) - (enemy2.getX()+playerWd/2);
                float yDist = (disk.getY()+diskHt/2) - (enemy2.getY()+playerHt/2);
                float p1Vx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float p1Vy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                enemy2.setX((enemy2.getX() + p1Vx));
                enemy2.setY((enemy2.getY() + p1Vy));
                enemy2Rotation = (float) Math.toDegrees(Math.atan(yDist/xDist));
                if(xDist < 0){
                    enemy2Rotation+= 180;
                }
                if(enemy2Rotation < 0){
                    enemy2Rotation+=360;
                }

                if(getDistanceToDisk(enemy2) < catchableDistance){
                    changingPoss = false;
                    p1Threw = false;
                }
            }
        } else {
            enemy1.setVX(0);
            enemy1.setVY(0);
        }
    }

    private void movePlayerToPoint(Player player, float x, float y){

    }

    private double getDistanceToDisk(Player player){
        double distToDisk;
        float X = player.getX();
        float Y = player.getY();
        float Wd = player.getTexture().getWidth();
        float Ht = player.getTexture().getHeight();
        distToDisk = Math.sqrt(Math.pow(X+Wd/2-(disk.getX()+diskWd/2),2) + Math.pow(Y+Ht/2-(disk.getY()+diskHt/2),2));
        return distToDisk;
    }

    private void switchToPlayerWithDisk() {
        float currP1X = player1.getX();
        float currP1Y = player1.getY();
        float currP1R = player1.getRotation();
        float currC1X = cpuPlayer.getX();
        float currC1Y = cpuPlayer.getY();
        float currC1R = cpuRotation;

        player1.setX(currC1X);
        player1.setY(currC1Y);
        player1.setRotation(currC1R);
        cpuPlayer.setX(currP1X);
        cpuPlayer.setY(currP1Y);
        cpuRotation = currP1R;

        cpuPlayer.setHoldingDisk(false);
        player1.setHoldingDisk(true);
    }

    private void keepDiskInBounds() {

        if (disk.getX() + diskWd/2 <= 0) {
            diskInAir = false;
            diskHeight = -100;
            changingPoss = true;
            diskVx = 0;
            diskVy = 0;
            disk.setX(1 - diskWd/2);
            onOffense = (!onOffense);
        }
        if (disk.getX() + diskWd/2 >= w) {
            diskInAir = false;
            diskHeight = -100;
            changingPoss = true;
            diskVx = 0;
            diskVy = 0;
            disk.setX(w - diskWd/2 - 1);
            onOffense = (!onOffense);
        }
        if (disk.getY() + diskHt/2 <= 0) {
            diskInAir = false;
            diskHeight = -100;
            changingPoss = true;
            diskVx = 0;
            diskVy = 0;
            disk.setY(1 - diskHt/2);
            onOffense = (!onOffense);
        }
        if (disk.getY() + diskHt/2 >= h) {
            diskInAir = false;
            diskHeight = -100;
            changingPoss = true;
            diskVx = 0;
            diskVy = 0;
            disk.setY(h - diskHt/2 - 1);
            onOffense = (!onOffense);
        }
        //Gdx.app.log("changingPoss3", ""+changingPoss);
    }

    private void keepPlayerInBounds() {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player1);
        players.add(cpuPlayer);
        players.add(enemy1);
        players.add(enemy2);
        for(Player p : players){
            if (p.getX() + playerWd/2 <= 0) {
                p.setX(p.getX() + 1);
            }
            if (p.getX() + playerWd/2 >= w) {
                p.setX(p.getX() - 1);
            }
            if (p.getY() + playerHt/2 <= 0) {
                p.setY(p.getY() + 1);
            }
            if (p.getY() + playerHt/2 >= h) {
                p.setY(p.getY() - 1);
            }
        }
    }

    @Override
    protected void render(SpriteBatch sb) {

        //sb.setProjectionMatrix(camera.combined);
        drawCounter++;
        sb.begin();

        sb.draw(background, 0,0, w, h);
        float stallWidth = Gdx.graphics.getWidth()/2;
        float stallX = Gdx.graphics.getWidth()/4;
        if(stallTime>0 && onOffense){
            if(stallTime<1){
                sb.draw(stallCount10, stallX, 0, stallWidth, h);
            } else if (stallTime <2){
                sb.draw(stallCount9, stallX, 0, stallWidth, h);
            } else if (stallTime <3){
                sb.draw(stallCount8, stallX, 0, stallWidth, h);
            } else if (stallTime <4){
                sb.draw(stallCount7, stallX, 0, stallWidth, h);
            } else if (stallTime <5){
                sb.draw(stallCount6, stallX, 0, stallWidth, h);
            } else if (stallTime <6){
                sb.draw(stallCount5, stallX, 0, stallWidth, h);
            } else if (stallTime <7){
                sb.draw(stallCount4, stallX, 0, stallWidth, h);
            } else if (stallTime <8){
                sb.draw(stallCount3, stallX, 0, stallWidth, h);
            } else if (stallTime <9){
                sb.draw(stallCount2, stallX, 0, stallWidth, h);
            } else if (stallTime <10){
                sb.draw(stallCount1, stallX, 0, stallWidth, h);
            } else {
                sb.draw(stallCount0, stallX, 0, stallWidth, h);
                stallTime = 0;
                turnover();

            }
        }
        if(Math.abs(airTime-timeToVertex)<0.05){
            //Gdx.app.log("Time", ""+timeToVertex);
        }
        sb.draw(selectRing, (float) (player1.getX()+51.2/2), (float) (player1.getY()+51.2/2), (float) (selectRing.getWidth()*0.8), (float) (selectRing.getHeight()*0.8));
        //Gdx.app.log("Time", ""+airTime);



        Gdx.app.log("drawmethod:", ""+ player1.getX()+", "+player1.getY());
        sb.draw(player1.getTexture(),player1.getX(),player1.getY(),playerWd/2,playerHt/2,playerWd,playerHt,1,1,player1.getRotation(),0,0,Math.round(playerWd),Math.round(playerHt),false,false);
        float diskScale;
        float a = 0f;
        if(diskHeight==0){
            diskScale = 1;
        } else {
            a = CAMERA_HEIGHT/diskHeight;
            if(a==1){
                diskScale = 1000;
            } else {
                diskScale = 1/(1-(1/a));
            }
        }

        //Gdx.app.log("disk", ""+diskScale);
        disk.setSize(diskWd*diskScale, diskHt*diskScale);
        disk.draw(sb);
        //sb.draw(cpuPlayer.getTexture(),cpuPlayer.getX(),cpuPlayer.getY(),cpuPlayer.getTexture().getWidth()/2,cpuPlayer.getTexture().getHeight()/2,cpuPlayer.getTexture().getWidth(),cpuPlayer.getTexture().getHeight(),1,1,cpuRotation,0,0,Math.round(cpuPlayer.getTexture().getWidth()),Math.round(cpuPlayer.getTexture().getHeight()),false,false);
        //sb.draw(enemy1.getTexture(),enemy1.getX(),enemy1.getY(),enemy1.getTexture().getWidth()/2,enemy1.getTexture().getHeight()/2,enemy1.getTexture().getWidth(),enemy1.getTexture().getHeight(),1,1,enemy1Rotation,0,0,Math.round(enemy1.getTexture().getWidth()), Math.round(enemy1.getTexture().getHeight()),false,false);
        //sb.draw(enemy2.getTexture(),enemy2.getX(),enemy2.getY(),enemy2.getTexture().getWidth()/2,enemy2.getTexture().getHeight()/2,enemy2.getTexture().getWidth(),enemy2.getTexture().getHeight(),1,1,enemy2Rotation,0,0,Math.round(enemy2.getTexture().getWidth()), Math.round(enemy2.getTexture().getHeight()),false,false);
        if(onOffense){
            scoreboardLeft.draw(sb);
        } else if (!onOffense){
            scoreboardRight.draw(sb);
        }
        clockText.draw(sb, clock(), scoreboardX + (79*scbdWd)/112, scoreboardY + (5*scbdHt)/8);
        scoreText1.draw(sb, Integer.toString(team1Score), scoreboardX + (2*scbdWd)/16, scoreboardY + (5*scbdHt)/8);
        scoreText2.draw(sb, Integer.toString(team2Score), scoreboardX + (35*scbdWd)/64, scoreboardY + (5*scbdHt)/8);
        pauseButton.draw(sb,1);
        touchpad.draw(sb,1);
        //cam.update();
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //sb.setProjectionMatrix(cam.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //tex.bind();

        //the endcap scale
		tris.endcap = 5f;

        //the thickness of the line
        tris.thickness = 30f;

        //generate the triangle strip from our path
        tris.update(swipe.path());

        //the vertex color for tinting, i.e. for opacity
        tris.color = Color.WHITE;

        //render the triangles to the screen
        tris.draw(cam);

        //uncomment to see debug lines
        drawDebug();



        if (drawCounter>=10){
            pauseButton.setDisabled(false);
        }



        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        sb.end();
    }

    @Override
    public void dispose() {
        shapes.dispose();
        tex.dispose();
    }

    public void resetAfterScore(){

        player1.setX(w/6 - playerWd/2);
        player1.setY(h/2 - playerHt/2);
        player1.setRotation(0);
        cpuPlayer.setX(w/6 - playerWd/2);
        cpuPlayer.setY(h/3 - playerHt/2);
        cpuRotation = 0;
        disk.setX(w/6 -diskWd/2);
        disk.setY(h/2 - diskHt/2);
        diskVx = 0;
        diskVy = 0;
        player1.setHoldingDisk(true);
    }

    public void turnover(){
        onOffense = !(onOffense);
        if(player1.hasDisk()){
            player1.setHoldingDisk(false);
        } else if (enemy1.hasDisk()){
            enemy1.setHoldingDisk(false);
        }
        diskHeight = -100;
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
                points=Math.abs(team1Score-team2Score);
                gsm.set(new WinState(gsm));
            }

        return time;
    }

    public void smartThrow(double theta, double velocity, double acceleration){
        //Gdx.app.log("Disk", acceleration + "acc");
        Gdx.app.log("Disk", velocity + "vec");
        if(!(acceleration>3.8||acceleration>2.5&&velocity>25)){
            if(player1.hasDisk() || cpuPlayer.hasDisk()) {
                if (theta > 0 && theta <= 90) {
                    sideV = -(float) velocity * (float) Math.sin(Math.toRadians(theta));
                    straightV = (float) velocity * (float) Math.cos(Math.toRadians(theta));
                    diskCurve = (float) acceleration;
                    timeToVertex = -(sideV/diskCurve)*Gdx.graphics.getDeltaTime();
                }
                if (theta >= 270 && theta < 360) {
                    sideV = (float) velocity * (float) Math.sin(Math.toRadians(360 - theta));
                    straightV = (float) velocity * (float) Math.cos(Math.toRadians(360 - theta));
                    diskCurve = -(float) acceleration;
                    timeToVertex = -(sideV/diskCurve)*Gdx.graphics.getDeltaTime();
                }
                if (theta == 0 || theta == 360) {
                    sideV = 0;
                    straightV = (float) velocity;
                    diskCurve = 0;
                }
                if(Math.abs(diskCurve)<0.1){

                    //timeToVertex = 750*(1/velocity)*Gdx.graphics.getDeltaTime();
                    if(velocity>10){
                        timeToVertex = 45*((1/(Math.pow((3*velocity), 0.1))))*Gdx.graphics.getDeltaTime();
                        isShortPass = false;
                    } else {
                        timeToVertex = 0;
                        isShortPass = true;
                    }

                    Gdx.app.log("disk", "timetoVertex" + timeToVertex);
                }
                //Gdx.app.log("disk", ""+diskCurve);

                if (player1.hasDisk()) {
                    p1Threw = true;
                    player1.setHoldingDisk(false);
                } else if (cpuPlayer.hasDisk()) {
                    cpuThrew = true;
                    cpuPlayer.setHoldingDisk(false);
                }

            }
        }

        timeOfThrow = GAME_TIME-playTime;
    }

    public void smartRoute(ArrayList<Vector2> waypoints, Player player){
        float xPt = waypoints.get(0).x;
        float yPt = waypoints.get(0).y;
        float xDist = (xPt) - (player.getX()+playerWd/2);
        float yDist = (yPt) - (player.getY()+playerHt/2);
        float pVx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
        float pVy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
        cpuRotation = (float)Math.toDegrees(Math.atan(yDist/xDist));
        if (xDist < 0) {
            cpuRotation += 180;
        }
        if(cpuRotation<0){
            cpuRotation += 360;
        }
        cpuPlayer.setVX(pVx);
        cpuPlayer.setVY(pVy);
    }


    void drawDebug() {
        Array<Vector2> input = swipe.input();
        int maxDrawPoints = 10;


        //draw the raw input
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.GRAY);
        for (int i=0; i<input.size-1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i+1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();

        //draw the smoothed and simplified path
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.RED);
        Array<Vector2> out = swipe.path();
        for (int i=0; i<out.size-1; i++) {
            Vector2 p = out.get(i);
            Vector2 p2 = out.get(i+1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();


        //render our perpendiculars
        shapes.begin(ShapeRenderer.ShapeType.Line);
        Vector2 perp = new Vector2();

        for (int i=1; i<input.size-1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i+1);

            shapes.setColor(Color.LIGHT_GRAY);
            perp.set(p).sub(p2).nor();
            perp.set(perp.y, -perp.x);
            perp.scl(10f);
            shapes.line(p.x, p.y, p.x+perp.x, p.y+perp.y);
            perp.scl(-1f);
            shapes.setColor(Color.BLUE);
            shapes.line(p.x, p.y, p.x+perp.x, p.y+perp.y);
        }
        shapes.end();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        float allyX = cpuPlayer.getX();
        float allyY = cpuPlayer.getY();
        y = h - y;
        Gdx.app.log("Tap", "TapInputX"+x);
        Gdx.app.log("Tap", "TapInputY"+y);
        Gdx.app.log("Tap", "AllyX"+allyX);
        Gdx.app.log("Tap", "AllyY"+allyY);

        float allyRotation = cpuRotation;
        if (/*(Math.abs(x - allyX)<=20 && Math.abs(y - allyY)<=20)&&*/(!onOffense)){
            Gdx.app.log("Tap", "Tapping");
            cpuPlayer.setX(player1.getX());
            cpuPlayer.setY(player1.getY());
            cpuPlayer.setPosition(player1.getX(), player1.getY());
            cpuPlayer.setRotation(player1.getRotation());
            //cpuRotation = rotation;
            player1.setX(allyX);
            player1.setY(allyY);
            player1.setPosition(allyX, allyY);
            player1.setRotation(allyRotation);
            //rotation = allyRotation;
            xPos = allyX;
            yPos = allyY;
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        stallTime = 0;
        Array<Vector2> input = swipe.input();
        Vector2 lastPoint = input.first();
        Vector2 firstPoint = input.get(input.size-1);
        Gdx.app.log("JOHN LOOK AT THIS","I am trash at coding");
        //Gdx.app.log("Smart Swipe", "First Point: "+firstPoint);
        //Gdx.app.log("Smart Swipe", "Last Point: "+lastPoint);
        if(!changingPoss){
            if(Math.abs(firstPoint.x - (disk.getX()+diskWd/2))<=20 && Math.abs(firstPoint.y - (disk.getY()+diskHt/2))<=20){
                double averageVelocity = 0;
                double acceleration = 0;
                double arcLength = 0;
                for (int i = 0; i<input.size-2; i++){
                    double pointDistance = Math.sqrt(Math.pow((input.get(i+1).x-input.get(i).x),2)+Math.pow((input.get(i+1).y-input.get(i).y),2));
                    arcLength+=pointDistance;
                }
                averageVelocity = arcLength/input.size-2;
                double crowDistance = Math.sqrt(Math.pow((input.first().x-input.get(input.size-1).x),2)+Math.pow((input.first().y-input.get(input.size-1).y),2));
                acceleration = arcLength/crowDistance;
                if(acceleration<1){
                    acceleration = 1;
                }
                acceleration-=1;
                if(averageVelocity >= 48){
                    averageVelocity = 28;
                }
                else if (averageVelocity < 48){
                    averageVelocity = (averageVelocity*7)/12;
                }
                //Gdx.app.log("Smart Swipe", "Accleration: "+acceleration);
                //Gdx.app.log("Smart Swipe", "Average Velocity: "+averageVelocity);
                int bestFit = 5;
                if(acceleration<0.12){
                    bestFit = 12;
                    Gdx.app.log("Smart Swipe", "Best fit called");
                }
                if(input.size<bestFit){
                    bestFit = input.size;
                }
                double dirX = (input.get(input.size-bestFit).x-input.get(input.size-1).x);
                double dirY = (input.get(input.size-bestFit).y-input.get(input.size-1).y);
                double absoluteTheta = Math.toDegrees(Math.atan(dirY/dirX));
                if(dirX<0){
                    absoluteTheta += 180;
                }
                //Gdx.app.log("Smart Swipe", "DirX: "+dirX);
                //Gdx.app.log("Smart Swipe", "DirY: "+dirY);
                Gdx.app.log("I identify as a professional ddoser", "DirX: "+dirX);


                if(absoluteTheta<0){
                    absoluteTheta += 360;
                }
                double relativeTheta = player1.getRotation()-absoluteTheta;
                playerRotationAtTimeOfThrow = player1.getRotation();
                if(relativeTheta<0){
                    relativeTheta += 360;
                }
                relativeTheta = 360-relativeTheta;
                double playerDirX = (Math.cos((player1.getRotation()*2*Math.PI)/360));
                double playerDirY = (Math.sin((player1.getRotation()*2*Math.PI)/360));
                double projectionScale = (playerDirX*(input.first().x-player1.getX())+playerDirY*(input.first().y-player1.getY()))/(playerDirX*playerDirX+playerDirY*playerDirY);


                acceleration*=((3/(Math.sqrt(16.5)))*(Math.sqrt(averageVelocity)));
                Gdx.app.log("Test,", "rotation" + player1.getRotation());
                //Gdx.app.log("Test", "playerDirX " + playerDirX);
                //Gdx.app.log("Test", "playerDirY " + playerDirY);
                //Gdx.app.log("Test", "vx " + (input.first().x-player1.getX()));
                //Gdx.app.log("Test", "vy " + (input.first().y-player1.getY()));
                Gdx.app.log("Test", "scale " + projectionScale);

                //Gdx.app.log("Smart Swipe", "Rotation: "+rotation);
                //Gdx.app.log("Smart Swipe", "Frisbee Direction: "+relativeTheta);
                if((!(relativeTheta>90&&relativeTheta<270))&&projectionScale>0){
                    smartThrow(relativeTheta,averageVelocity,acceleration);
                    diskInAir = true;
                    Gdx.app.log("Disk", "diskInAir true");
                }

            }
            else if(Math.abs(firstPoint.x - (cpuPlayer.getX()+playerWd/2)) <= 20 && Math.abs(firstPoint.y - (cpuPlayer.getY()+playerHt/2)) <= 20){
                //cpuPlayer.setVelocity(10);
                ArrayList<Vector2> selectWaypoints = new ArrayList<Vector2> ();
                int x = 0;
                for(int i = input.size -1; i > 0; i--){
                    x++;
                    if(x%4 == 0) {
                        selectWaypoints.add(input.get(i));
                    }
                }
                smartRoute(selectWaypoints, cpuPlayer);
            }
        }

        /*Gdx.app.log("Swipe", "Completed");
        if(Math.abs(velocityX)>Math.abs(velocityY)){
            if(velocityX>0){
                Gdx.app.log("Swipe", "Right");
            }else if(velocityX<0){
                Gdx.app.log("Swipe", "Left");
            }else{
                Gdx.app.log("Swipe","No swipe");
            }
        }else{
            Gdx.app.log("Swipe", "Up or Down");
            Gdx.app.log("Du hail du hail du hail du hail");

        }*/
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
