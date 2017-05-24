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
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Arrays;

class PlayState extends State implements GestureDetector.GestureListener{
    World world;
    Body body;

    static float w = Gdx.graphics.getWidth();
    static float h = Gdx.graphics.getHeight();
    float scbdWd;
    float scbdHt;

    float isJohnGay;
    float xPos;
    float yPos;
    float playerWd;
    float playerHt;
    LinkedHashMap<Player,Boolean> playerPossData = new LinkedHashMap<Player, Boolean>();
    OrthographicCamera camera;

    public static final int WORLD_WIDTH=1920;
    public static final int WORLD_HEIGHT=1080;
    static float xScl =w/WORLD_WIDTH;
    static float yScl =h/WORLD_HEIGHT;

    public static final int GAME_TIME=500;

    public static final float CAMERA_HEIGHT=500;

    private Player player1;
    private Player cpuPlayer;
    private double player1DistToDisk;
    private double cpuDistToDisk;
    private Player cpu2Player;
    private double cpu2DistToDisk;

    private Player enemy1;
    private Player enemy2;
    private Player enemy3;
    private double enemy1distToDisk;
    private double enemy2distToDisk;
    private double enemy3distToDisk;

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
    boolean cpu2Threw = false;
    boolean enemyThrew = false;

    boolean cpuAIReleased;
    boolean cpu2AIReleased;

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
    float rotation;
    float cpuRotation;
    float cpu2Rotation;
    float enemy1Rotation;
    float enemy2Rotation;
    float enemy3Rotation;
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
    float player1VelocityX;
    float player1VelocityY;
    float cpuVelocityX;
    float cpuVelocityY;
    float cpu2VelocityX;
    float cpu2VelocityY;
    float enemy1VelocityX;
    float enemy1VelocityY;
    float enemy2VelocityX;
    float enemy2VelocityY;
    float enemy3VelocityX;
    float enemy3VelocityY;
    private Texture enemyTexture;
    private double timeOfStall;
    private double stallStallTime;
    ArrayList<Vector2> selectWaypoints = new ArrayList<Vector2> ();
    ArrayList<Vector2> select2Waypoints = new ArrayList<Vector2> ();
    private int cpuWayPoint;
    private int cpu2WayPoint;
    float cpuSpeed;
    float cpu2Speed;
    int[][] permutations = new int[3][6];
    //public bool isAbdul;


    int drawCounter = 0;
    boolean inLeftEndZone=false;
    boolean inRightEndZone=false;
    float playerRotationAtTimeOfThrow;

    PlayState(GameStateManager gsm) {
        super(gsm);
        player1=new Player(400,400);
        cpuPlayer = new Player(800, 600);
        cpu2Player = new Player(800, 600);
        enemy1 = new Player(1200, 500);
        enemy2 = new Player(1000, 700);
        enemy3 = new Player(1000, 700);
        cpuRotation = 0;
        cpu2Rotation = 0;
        //enemy1Rotation=180;
        //enemy2Rotation=180;
        //enemy3Rotation = 180;
        enemy1Rotation=enemy1.getRot();
        enemy2Rotation=enemy2.getRot();
        enemy3Rotation=enemy3.getRot();
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

        timeOfStall = 0;
        caughtTime = 0;
        stallTime = 0;
        changingPoss = false;
        playerHeight = -50;
        diskHeight = playerHeight;
        diskInAir = false;
        isShortPass = false;

        cpuWayPoint = 0;
        cpu2WayPoint = 0;
        cpuAIReleased = true;
        cpu2AIReleased = true;
        cpuSpeed = 0;
        cpu2Speed = 0;


        permutations[0][0] = 0;
        permutations[1][0] = 1;
        permutations[2][0] = 2;
        permutations[0][1] = 0;
        permutations[1][1] = 2;
        permutations[2][1] = 1;
        permutations[0][2] = 1;
        permutations[1][2] = 0;
        permutations[2][2] = 2;
        permutations[0][3] = 1;
        permutations[1][3] = 2;
        permutations[2][3] = 0;
        permutations[0][4] = 2;
        permutations[1][4] = 0;
        permutations[2][4] = 1;
        permutations[0][5] = 2;
        permutations[1][5] = 1;
        permutations[2][5] = 0;


        pauseButtonSkin= new Skin();   //create button skin
        pauseButtonSkin.add("pauseButton", new Texture("button_pause.png"));    //add the image to the skin
        pauseButtonStyle= new ImageButton.ImageButtonStyle();  //create button style
        pauseButtonStyle.imageUp = pauseButtonSkin.getDrawable("pauseButton");  //sets the button appearance when it is not pressed
        pauseButton = new ImageButton(pauseButtonStyle);    //initializes the ImageButton with the created style as a parameter
        pauseButton.setBounds(0,(WORLD_HEIGHT-pauseButton.getHeight()),pauseButton.getWidth(),pauseButton.getHeight());

        scbdTexture = new Texture("scoreboard.png");
        scoreboard=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*2;
        scbdHt = scbdTexture.getHeight()*2;
        scoreboard.setSize(scbdWd, scbdHt);
        //scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardX=w-scbdWd;
        scoreboardY=h-scbdHt;
        scoreboard.setX(scoreboardX);
        scoreboard.setY(scoreboardY);

        scbdTexture = new Texture("scoreboard_left_offense.png");
        scoreboardLeft=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*2;
        scbdHt = scbdTexture.getHeight()*2;
        scoreboardLeft.setSize(scbdWd, scbdHt);
        //scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardX=w-scbdWd;
        scoreboardY=h-scbdHt;
        scoreboardLeft.setX(scoreboardX);
        scoreboardLeft.setY(scoreboardY);

        scbdTexture = new Texture("scoreboard_right_offense.png");
        scoreboardRight=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*2;
        scbdHt = scbdTexture.getHeight()*2;
        scoreboardRight.setSize(scbdWd, scbdHt);
        //scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardX=w-scbdWd;
        scoreboardY=h-scbdHt;
        scoreboardRight.setX(scoreboardX);
        scoreboardRight.setY(scoreboardY);

        //Swipes

        tris = new SwipeTriStrip();
        swipe = new SwipeHandler(256);
        swipe.minDistance = 1;
        swipe.initialDistance = 1;
        tex = new Texture("gradient.png");
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        shapes = new ShapeRenderer();


        freeTypeFontParameter.size=(int)(9*scbdHt)/18;
        clockText =freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        scoreText1 = freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        scoreText2 = freeTypeFontGenerator.generateFont(freeTypeFontParameter);

        playerWd = player1.getTexture().getWidth();
        playerHt = player1.getTexture().getHeight();

        diskTexture = new Texture("frisbee_snake.png");
        disk = new Sprite(diskTexture);
        diskWd = diskTexture.getWidth();
        diskHt = diskTexture.getHeight();
        disk.setSize(diskWd, diskHt);

        catchableDistance = 100;

        player1.setHoldingDisk(true);
        playerPossData.replace(player1,true);
        player1.setX(w/6 - playerWd/2);
        player1.setY((h)/2 - playerHt/2);

        cpuPlayer.setX(w/6 - playerWd/2);
        cpuPlayer.setY((h)/4 - playerHt/2);
        cpu2Player.setX(w/6 - playerWd/2);
        cpu2Player.setY((3*h)/4 - playerHt/2);

        enemy1.setX(5*w/6 - playerWd/2);
        enemy1.setY(h/4 - playerHt/2);

        enemy2.setX(5*w/6 - playerWd/2);
        enemy2.setY(3*h/8 - playerHt/2);
        enemy3.setX(5*w/6 - playerWd/2);
        enemy3.setY(h/8 - playerHt/2);

        enemyTexture=new Texture("idle_enemy.png");
        enemy1.setTexture(enemyTexture);
        enemy2.setTexture(enemyTexture);
        enemy3.setTexture(enemyTexture);

        disk.setX(w/6 -diskWd/2);
        disk.setY((h)/2 - diskHt/2);

        player1VelocityX = 0;
        enemy1VelocityX = 0;
        enemy2VelocityX = 0;
        enemy3VelocityX = 0;
        cpuVelocityX = 0;
        cpu2VelocityX = 0;
        player1VelocityY = 0;
        enemy1VelocityY = 0;
        enemy2VelocityY = 0;
        enemy3VelocityY = 0;
        cpuVelocityY = 0;
        cpu2VelocityY = 0;

        world = new World(new Vector2(0, -98f), true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(player1.getX(), player1.getY());
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(player1.getWidth()/2, player1.getHeight()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        //fixtureDef.density = 1f;

        //Fixture fixture = body.createFixture(fixtureDef);

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
        touchpad.setBounds(25,25,210,210);


        stage = new Stage();
        stage.addActor(touchpad);
        stage.addActor(pauseButton);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new GestureDetector(this), swipe));
        //shape.dispose();
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
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        handleInput();
        player1.update(dt);
        cpuPlayer.update(dt);
        cpu2Player.update(dt);
        enemy1.update(dt);
        enemy2.update(dt);
        enemy3.update(dt);
        if (!stopped) {

            Gdx.app.log("Enemy threw: ", "" + enemyThrew);

            if (player1.hasDisk()) {
                player1.setVelocity(0);
                stallTime = (GAME_TIME-playTime) - caughtTime;

                onOffense = true;
                diskHeight = -50;



            }
            else if (!player1.hasDisk()){
                player1.setVelocity(10);
                if(onOffense){
                    airTime = (GAME_TIME-playTime) - timeOfThrow;
                }

                //float c = 150;
                float c = 150;
                if (airTime > 0){
                    Gdx.app.log("disk", "thrown");
                    Gdx.app.log("Trash at code", "Wow! Savage.");
                    if(Math.abs(diskCurve)>0.1){
                        diskHeight = (float) (1.5*(((-1*Math.abs(diskCurve*c))/(timeToVertex*timeToVertex))*(airTime-timeToVertex)*(airTime-timeToVertex)+Math.abs(diskCurve)*c+playerHeight));
                    } else if (!isShortPass){
                        diskHeight = (float) (((-1*Math.abs(3*straightV)*(c/30))/(timeToVertex*timeToVertex))*(airTime-timeToVertex)*(airTime-timeToVertex)+Math.abs(straightV)*(c/30)-playerHeight);
                    } else {
                        c = 100;
                        diskHeight = (float) (((-1*Math.abs(3*straightV)*(c/30))/(timeToVertex*timeToVertex))*(airTime-timeToVertex)*(airTime-timeToVertex)+Math.abs(straightV)*(c/30)-playerHeight);
                        //diskHeight = (float) (((-1*Math.abs(2*straightV)*(c/30)))*(airTime)*(airTime)+Math.abs(straightV)*(c/30)-playerHeight);
                    }
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

            if(enemy1.hasDisk()||enemy2.hasDisk()||enemy3.hasDisk()){
                stallTime = (GAME_TIME-playTime) - caughtTime;

                onOffense = false;
                diskHeight = -50;
                if(enemy1.hasDisk()){
                    enemy1VelocityX = 0;
                    enemy1VelocityY = 0;
                } else if (enemy2.hasDisk()){
                    enemy2VelocityX = 0;
                    enemy2VelocityY = 0;
                } else if (enemy3.hasDisk()){
                    enemy3VelocityX = 0;
                    enemy3VelocityY = 0;
                }
            }


            //Duncan is stupid
            Gdx.app.log("Waypoints", "cpuAIReleased: " + cpuAIReleased);
            if(cpuAIReleased == false){
                Gdx.app.log("Waypoints", "cpuWayPoint: " + cpuWayPoint);
                float xPt = selectWaypoints.get(cpuWayPoint).x;
                float yPt = selectWaypoints.get(cpuWayPoint).y;
                float xDist = (xPt) - (cpuPlayer.getPosition().x+playerWd/2);
                float yDist = (yPt) - (cpuPlayer.getPosition().y+playerHt/2);
                float pVx = (cpuSpeed/5) * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float pVy = (cpuSpeed/5) * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                cpuVelocityX = pVx;
                cpuVelocityY = pVy;
                cpuRotation = (float) Math.toDegrees(Math.atan(yDist / xDist));
                if (xDist < 0) {
                    cpuRotation += 180;
                }
                if(cpuRotation<0){
                    cpuRotation += 360;
                }
                if (cpuWayPoint >= selectWaypoints.size()-1){
                    cpuAIReleased = true;
                    cpuWayPoint = 0;
                    selectWaypoints.clear();
                } else {
                    //float xPtNext = selectWaypoints.get(cpuWayPoint).x;
                    //float yPtNext = selectWaypoints.get(cpuWayPoint).y;
                    //float xDistNext = (xPtNext) - (cpuPlayer.getPosition().x+playerWd/2);
                    //float yDistNext = (yPtNext) - (cpuPlayer.getPosition().y+playerHt/2);
                    double distToNextWaypoint = Math.sqrt(xDist*xDist + yDist*yDist);
                    cpuSpeed = (float) (Math.sqrt(Math.pow((selectWaypoints.get(cpuWayPoint+1).x-selectWaypoints.get(cpuWayPoint).x),2)+Math.pow((selectWaypoints.get(cpuWayPoint+1).y-selectWaypoints.get(cpuWayPoint).y),2)));
                    if(cpuSpeed>45){
                        cpuSpeed = 45;
                    }
                    Gdx.app.log("Waypoints", "distToNextWayPoint: " + distToNextWaypoint);
                    if(distToNextWaypoint<10){
                        cpuWayPoint++;
                    }
                }
            }
            if(cpu2AIReleased == false){
                //Gdx.app.log("Waypoints", "cpuWayPoint: " + cpuWayPoint);
                float xPt = select2Waypoints.get(cpu2WayPoint).x;
                float yPt = select2Waypoints.get(cpu2WayPoint).y;
                float xDist = (xPt) - (cpu2Player.getPosition().x+playerWd/2);
                float yDist = (yPt) - (cpu2Player.getPosition().y+playerHt/2);
                float pVx = (cpu2Speed/5) * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float pVy = (cpu2Speed/5) * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                cpu2VelocityX = pVx;
                cpu2VelocityY = pVy;
                cpu2Rotation = (float) Math.toDegrees(Math.atan(yDist / xDist));
                if (xDist < 0) {
                    cpu2Rotation += 180;
                }
                if(cpu2Rotation<0){
                    cpu2Rotation += 360;
                }
                if (cpu2WayPoint >= select2Waypoints.size()-1){
                    cpu2AIReleased = true;
                    cpu2WayPoint = 0;
                    select2Waypoints.clear();
                } else {
                    //float xPtNext = selectWaypoints.get(cpuWayPoint).x;
                    //float yPtNext = selectWaypoints.get(cpuWayPoint).y;
                    //float xDistNext = (xPtNext) - (cpuPlayer.getPosition().x+playerWd/2);
                    //float yDistNext = (yPtNext) - (cpuPlayer.getPosition().y+playerHt/2);
                    double distToNextWaypoint = Math.sqrt(xDist*xDist + yDist*yDist);
                    cpu2Speed = (float) (Math.sqrt(Math.pow((select2Waypoints.get(cpu2WayPoint+1).x-select2Waypoints.get(cpu2WayPoint).x),2)+Math.pow((select2Waypoints.get(cpu2WayPoint+1).y-select2Waypoints.get(cpu2WayPoint).y),2)));
                    if(cpu2Speed>45){
                        cpu2Speed = 45;
                    }
                    //Gdx.app.log("Waypoints", "distToNextWayPoint: " + distToNextWaypoint);
                    if(distToNextWaypoint<10){
                        cpu2WayPoint++;
                    }
                }
            }




            xPos = player1.getPosition().x;
            yPos = player1.getPosition().y;
            float deltaX = touchpad.getKnobPercentX();
            float deltaY = touchpad.getKnobPercentY();
            if(changingPoss && p1Threw){
                deltaX = 0;
                deltaY = 0;
            }

            //deltaX *= xScl;
            //deltaY *= yScl;
            player1.setX(xPos + deltaX * player1.getVelocity());
            player1.setY(yPos + deltaY * player1.getVelocity());
            enemy1.setX (enemy1.getPosition().x + enemy1VelocityX);
            enemy1.setY(enemy1.getPosition().y + enemy1VelocityY);
            enemy2.setX(enemy2.getPosition().x + enemy2VelocityX);
            enemy2.setY(enemy2.getPosition().y + enemy2VelocityY);
            enemy3.setX(enemy3.getPosition().x + enemy3VelocityX);
            enemy3.setY(enemy3.getPosition().y + enemy3VelocityY);
            cpuPlayer.setX(cpuPlayer.getPosition().x + cpuVelocityX);
            cpuPlayer.setY(cpuPlayer.getPosition().y + cpuVelocityY);
            cpu2Player.setX(cpu2Player.getPosition().x + cpu2VelocityX);
            cpu2Player.setY(cpu2Player.getPosition().y + cpu2VelocityY);
            Gdx.app.log("Enemy v", enemy1VelocityX + "");



            if (deltaX != 0 && deltaY != 0) {
                rotation = (float) Math.toDegrees(Math.atan(deltaY / deltaX));
                if (deltaX < 0) {
                    rotation += 180;
                }
                if(rotation<0){
                    rotation += 360;
                }
                int r = 50;
                if(player1.hasDisk()) {

                    disk.setX((float) (player1.getPosition().x + (playerWd) / 2 - diskWd / 2 + (r * (deltaX / Math.sqrt(deltaX * deltaX + deltaY * deltaY)))));
                    disk.setY((float) (player1.getPosition().y + (playerHt) / 2 - diskHt / 2 + (r * (deltaY / Math.sqrt(deltaX * deltaX + deltaY * deltaY)))));

                }
            }
            int r = 50;
            if (enemy1.hasDisk()){
                disk.setX((float) (enemy1.getPosition().x + (playerWd) / 2 - diskWd / 2 + (r * (Math.cos(Math.toRadians(180-enemy1Rotation))))));
                disk.setY((float) (enemy1.getPosition().y + (playerHt) / 2 - diskHt / 2 + (r * (Math.sin(Math.toRadians(180-enemy1Rotation))))));
            } else if (enemy2.hasDisk()){
                disk.setX((float) (enemy2.getPosition().x + (playerWd) / 2 - diskWd / 2 + (r * (Math.cos(Math.toRadians(180-enemy1Rotation))))));
                disk.setY((float) (enemy2.getPosition().y + (playerHt) / 2 - diskHt / 2 + (r * (Math.sin(Math.toRadians(180-enemy1Rotation))))));
            } else if (enemy3.hasDisk()){
                disk.setX((float) (enemy3.getPosition().x + (playerWd) / 2 - diskWd / 2 + (r * (Math.cos(Math.toRadians(180-enemy1Rotation))))));
                disk.setY((float) (enemy3.getPosition().y + (playerHt) / 2 - diskHt / 2 + (r * (Math.sin(Math.toRadians(180-enemy1Rotation))))));
            }
            Gdx.app.log("Disk", "Enemy1 position " + enemy1.getPosition().x);
            Gdx.app.log("Disk", "Sin hting" + r * (Math.toDegrees(Math.cos(180-enemy1Rotation))));

            keepPlayerInBounds();

            if(!player1.hasDisk() && !cpuPlayer.hasDisk() && !cpu2Player.hasDisk() && !enemy1.hasDisk() && !enemy2.hasDisk() && !enemy3.hasDisk()) {
                sideV += diskCurve;
                double alpha = Math.toRadians(playerRotationAtTimeOfThrow - 90);

                diskVx = (float) (Math.cos(alpha) * sideV - Math.sin(alpha) * straightV);
                diskVy = (float) (Math.sin(alpha) * sideV + Math.cos(alpha) * straightV);
                if(!diskInAir){
                    diskVx = 0;
                    diskVy = 0;
                    if(!onOffense){
                        changingPoss = true;
                    }
                }
            }

            if(!player1.hasDisk() && !cpuPlayer.hasDisk() && !cpu2Player.hasDisk() && !enemy1.hasDisk() && !enemy2.hasDisk() && !enemy3.hasDisk()){
                keepDiskInBounds();
            }

            player1DistToDisk = getDistanceToDisk(player1);
            //Gdx.app.log("playerhasdisk?", "" + player1.hasDisk());
            //Gdx.app.log("p1threw", ""+p1Threw);
            if(player1DistToDisk <= catchableDistance && !player1.hasDisk() && !p1Threw && onOffense && diskHeight < 80){
                player1.setHoldingDisk(true);
                playerPossData.replace(player1,true);
                diskVx = 0;
                diskVy = 0;
                caughtTime = GAME_TIME-playTime;
                Gdx.app.log("Catching", "Caught" + caughtTime);
                //cpuVelocityX = 0;
                //cpuVelocityY = 0;
                enemyThrew = false;

            }
            else if(player1DistToDisk > catchableDistance){
                player1.setHoldingDisk(false);
                p1Threw = false;
                playerPossData.replace(player1,false);
                //p1Threw = false;
            }


            cpuDistToDisk = getDistanceToDisk(cpuPlayer);
            cpu2DistToDisk = getDistanceToDisk(cpu2Player);
            if(cpuDistToDisk <= 2*catchableDistance && diskVx != 0 && diskVy != 0){
                cpuRotation = (float) Math.toDegrees(Math.atan(diskVy/diskVx));
                if(diskVx < 0){
                    cpuRotation+= 180;
                }
                if(cpuRotation < 0){
                    cpuRotation+=360;
                }
                cpuRotation+=180;
            }
            if(cpuDistToDisk <= catchableDistance && !cpuPlayer.hasDisk() && !cpuThrew && diskHeight < 80 && onOffense){
                cpuPlayer.setHoldingDisk(true);
                playerPossData.replace(cpuPlayer,true);
                diskVx = 0;
                diskVy = 0;
                caughtTime = GAME_TIME-playTime;
                cpuVelocityX = 0;
                cpuVelocityY = 0;
                enemyThrew = false;
            }
            else if(cpuDistToDisk > catchableDistance ){
                cpuPlayer.setHoldingDisk(false);
                playerPossData.replace(cpuPlayer,false);
                cpuThrew = false;
            }
            if(cpuPlayer.hasDisk()){
                selectWaypoints.clear();
                cpuAIReleased = true;
                switchToPlayerWithDisk();
            }



            if(cpu2DistToDisk <= 2*catchableDistance && diskVx != 0 && diskVy != 0){
                cpu2Rotation = (float) Math.toDegrees(Math.atan(diskVy/diskVx));
                if(diskVx < 0){
                    cpu2Rotation+= 180;
                }
                if(cpu2Rotation < 0){
                    cpu2Rotation+=360;
                }
                cpu2Rotation+=180;
            }
            if(cpu2DistToDisk <= catchableDistance && !cpu2Player.hasDisk() && !cpu2Threw && diskHeight < 80 && onOffense){
                cpu2Player.setHoldingDisk(true);
                playerPossData.replace(cpu2Player,true);
                diskVx = 0;
                diskVy = 0;
                caughtTime = GAME_TIME-playTime;
                cpu2VelocityX = 0;
                cpu2VelocityY = 0;
                enemyThrew = false;
            }
            else if(cpu2DistToDisk > catchableDistance ){
                cpu2Player.setHoldingDisk(false);
                playerPossData.replace(cpu2Player,false);
                cpu2Threw = false;
            }
            if(cpu2Player.hasDisk()){
                select2Waypoints.clear();
                cpu2AIReleased = true;
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
            enemy1.setRot(enemy1Rotation); //Line below is John's cancerous work in progress
            if((enemy1distToDisk <= catchableDistance && !enemy1.hasDisk() && diskHeight < 80 && !enemyThrew && !player1.hasDisk())||(distanceBetweenPlayers(enemy1, player1) <= 151 && enemy1distToDisk <= catchableDistance/2 && !enemy1.hasDisk() && diskHeight < 80 && !enemyThrew && !player1.hasDisk())){
                enemy1.setHoldingDisk(true);
                playerPossData.replace(enemy1,true);
                diskVx = 0;
                diskVy = 0;
                caughtTime = GAME_TIME-playTime;
            }
            else if(enemy1distToDisk > catchableDistance ){
                enemy1.setHoldingDisk(false);
                playerPossData.replace(enemy1,false);
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
            enemy2.setRot(enemy2Rotation);
            if(enemy2distToDisk <= catchableDistance && !enemy2.hasDisk() && diskHeight < 80 && !enemyThrew && !player1.hasDisk()){
                enemy2.setHoldingDisk(true);
                playerPossData.replace(enemy2,true);
                diskVx = 0;
                diskVy = 0;
                caughtTime = GAME_TIME-playTime;
            }
            else if(enemy2distToDisk > catchableDistance ){
                enemy2.setHoldingDisk(false);
                playerPossData.replace(enemy2,false);
            }



            enemy3distToDisk = getDistanceToDisk(enemy3);
            if(enemy3distToDisk <= 4*catchableDistance && diskVx != 0 && diskVy != 0){
                enemy3Rotation = (float) Math.toDegrees(Math.atan(diskVy/diskVx));
                if(diskVx < 0){
                    enemy3Rotation+= 180;
                }
                if(enemy3Rotation < 0){
                    enemy3Rotation+=360;
                }
                enemy3Rotation+=180;
            }
            enemy3.setRot(enemy3Rotation);
            if(enemy3distToDisk <= catchableDistance && !enemy3.hasDisk() && diskHeight < 80 && !enemyThrew && !player1.hasDisk()){
                enemy3.setHoldingDisk(true);
                playerPossData.replace(enemy3,true);
                diskVx = 0;
                diskVy = 0;
                caughtTime = GAME_TIME-playTime;
            }
            else if(enemy3distToDisk > catchableDistance ){
                enemy3.setHoldingDisk(false);
                playerPossData.replace(enemy3,false);
            }

            if(onOffense){
                double[][] distanceArray = new double[3][3];
                for (int i=0; i<3; i++){
                    for (int j=0; j<3; j++){
                        distanceArray[i][j] = distanceBetweenPlayers(intToPlayer(i, true), intToPlayer(j, false));
                    }
                }
                double[] distanceSum = new double[6];
                for (int i=0; i<6; i++){
                    distanceSum[i] = distanceArray[0][permutations[0][i]] + distanceArray[1][permutations[1][i]] + distanceArray[2][permutations[2][i]];
                }
                double minDistance = distanceSum[0];
                int minElement = 0;
                for(int i=0; i<6; i++){
                    if(distanceSum[i]<minDistance){
                        minDistance = distanceSum[i];
                        minElement = i;
                    }
                }
                playDefense(enemy1, intToPlayer(permutations[0][minElement], true));
                playDefense(enemy2, intToPlayer(permutations[1][minElement], true));
                playDefense(enemy3, intToPlayer(permutations[2][minElement], true));
            }
            if (!onOffense){
                playOffense();
            }



            if(!player1.hasDisk()){
                disk.setX(disk.getX() + diskVx);
                disk.setY(disk.getY() + diskVy);
            }


            if (disk.getX()+diskWd/2 <= w/6 && !inLeftEndZone){
                inLeftEndZone=true;
                if(enemy1.hasDisk() || enemy2.hasDisk() || enemy3.hasDisk()){
                    team2Score++;
                    resetAfterScore(false);
                }
            } else if (disk.getX()+diskWd/2 > w/6){
                inLeftEndZone=false;
            }

            if (disk.getX()+diskWd/2 >= w-w/6 && !inRightEndZone){
                inRightEndZone=true;
                if(player1.hasDisk() || cpuPlayer.hasDisk() || cpu2Player.hasDisk()){
                    team1Score++;
                    resetAfterScore(true);
                }
            } else if (disk.getX()+diskWd/2 < w-w/6){
                inRightEndZone=false;
            }
            if(inRightEndZone && diskHeight == -100){
                disk.setX(w-w/6-diskWd/2);
            }
            if(inLeftEndZone && diskHeight == -100){
                disk.setX(w/6+diskWd/2);
            }

        }
        if (changingPoss && !stopped){
            if(!p1Threw && onOffense){
                float xDist = (disk.getX()+diskWd/2) - (player1.getPosition().x+playerWd/2);
                float yDist = (disk.getY()+diskHt/2) - (player1.getPosition().y+playerHt/2);
                float p1Vx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float p1Vy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                player1VelocityX = p1Vx;
                player1VelocityY = p1Vy;
                Gdx.app.log("Possession", "" + player1.getVelocity());
                //player1.setX((player1.getPosition().x + p1Vx));
                //player1.setY((player1.getPosition().y + p1Vy));

                if(getDistanceToDisk(player1) < catchableDistance){
                    changingPoss = false;
                    //diskInAir =
                }
            } else if((enemy1distToDisk < enemy2distToDisk)&&(enemy1distToDisk < enemy3distToDisk)){
                float xDist = (disk.getX()+diskWd/2) - (enemy1.getPosition().x+playerWd/2);
                float yDist = (disk.getY()+diskHt/2) - (enemy1.getPosition().y+playerHt/2);
                float e1Vx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float e1Vy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                enemy1VelocityX = e1Vx;
                enemy1VelocityY = e1Vy;
                Gdx.app.log("Possession", "" + enemy1.getVelocity());

                enemy1Rotation = (float) Math.toDegrees(Math.atan(yDist/xDist));
                if(xDist < 0){
                    enemy1Rotation+= 180;
                }
                if(enemy1Rotation < 0){
                    enemy1Rotation+=360;
                }
                enemy1.setRot(enemy1Rotation);
                Gdx.app.log("enemy1distToDisk", ""+ enemy1distToDisk);
                if(getDistanceToDisk(enemy1) < catchableDistance){
                    changingPoss = false;
                    enemy1.setHoldingDisk(true);
                    playerPossData.replace(enemy1,true);
                    p1Threw = false;
                    enemy1.setHoldingDisk(true);
                }
            } else if((enemy2distToDisk < enemy1distToDisk)&&(enemy2distToDisk < enemy3distToDisk)){
                float xDist = (disk.getX()+diskWd/2) - (enemy2.getPosition().x+playerWd/2);
                float yDist = (disk.getY()+diskHt/2) - (enemy2.getPosition().y+playerHt/2);
                float p1Vx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float p1Vy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                enemy2.setX((enemy2.getPosition().x + p1Vx));
                enemy2.setY((enemy2.getPosition().y + p1Vy));
                enemy2Rotation = (float) Math.toDegrees(Math.atan(yDist/xDist));
                if(xDist < 0){
                    enemy2Rotation+= 180;
                }
                if(enemy2Rotation < 0){
                    enemy2Rotation+=360;
                }
                enemy2.setRot(enemy2Rotation);
                if(getDistanceToDisk(enemy2) < catchableDistance){
                    changingPoss = false;
                    enemy2.setHoldingDisk(true);
                    playerPossData.replace(enemy2,true);
                    p1Threw = false;
                    enemy2.setHoldingDisk(true);
                }
            } else if((enemy3distToDisk < enemy1distToDisk)&&(enemy3distToDisk < enemy2distToDisk)){
                float xDist = (disk.getX()+diskWd/2) - (enemy3.getPosition().x+playerWd/2);
                float yDist = (disk.getY()+diskHt/2) - (enemy3.getPosition().y+playerHt/2);
                float p1Vx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float p1Vy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                enemy3.setX((enemy3.getPosition().x + p1Vx));
                enemy3.setY((enemy3.getPosition().y + p1Vy));
                enemy3Rotation = (float) Math.toDegrees(Math.atan(yDist/xDist));
                if(xDist < 0){
                    enemy3Rotation+= 180;
                }
                if(enemy3Rotation < 0){
                    enemy3Rotation+=360;
                }
                enemy3.setRot(enemy3Rotation);
                if(getDistanceToDisk(enemy3) < catchableDistance){
                    changingPoss = false;
                    enemy3.setHoldingDisk(true);
                    playerPossData.replace(enemy3,true);
                    p1Threw = false;
                    enemy3.setHoldingDisk(true);
                }
            }
        } else {
            //enemy1VelocityX = 0;
            //enemy1VelocityY = 0;
        }
    }

/*    private void movePlayerToPoint(Player player, float x, float y){

    }*/

    private void playDefense(Player defender, Player mark) {
        if (!changingPoss) {
            player1.setRot(rotation);
            cpuPlayer.setRot(cpuRotation);
            cpu2Player.setRot(cpu2Rotation);
            defender.setRot(mark.getRot() + 180);
            float xDist = (mark.getPosition().x) - (defender.getPosition().x);
            float yDist = (mark.getPosition().y) - (defender.getPosition().y);
            float Vx = 0;
            float Vy = 0;
            if (Math.sqrt(xDist * xDist + yDist * yDist) > catchableDistance && !mark.hasDisk()) {
                //Gdx.app.log("running", "1");
                Vx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                Vy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));


            }
            else if (mark.hasDisk()) {
                float totalV = 5f;
                if(Math.sqrt(xDist * xDist + yDist * yDist) < catchableDistance){
                    totalV=2f;
                }
                Gdx.app.log("running", "2");
                Gdx.app.log("running", "" + mark.getRot());

                float dx = (mark.getPosition().x +playerWd/2 + 3*((disk.getX() + diskWd/2)-(mark.getPosition().x + playerWd/2))) - (defender.getPosition().x + playerWd/2);
                float dy = (mark.getPosition().y +playerHt/2 + 3*((disk.getY() + diskHt/2)-(mark.getPosition().y + playerHt/2))) - (defender.getPosition().y + playerHt/2);
                Vx = totalV * (dx / ((float) Math.sqrt(dx * dx + dy * dy)));
                Vy = totalV * (dy / ((float) Math.sqrt(dx * dx + dy * dy)));
            }

            defender.setX(defender.getPosition().x + Vx);
            defender.setY(defender.getPosition().y + Vy);
            //Gdx.app.log("running", ""+Math.sqrt(xDist*xDist + yDist*yDist));


        }
    }
    private Player intToPlayer(int i, boolean ally){
        Player returnPlayer = player1;
        if(ally){
            if(i==0){
                returnPlayer = player1;
            } else if (i==1){
                returnPlayer = cpuPlayer;
            } else if (i==2){
                returnPlayer = cpu2Player;
            }
        } else {
            if(i==0){
                returnPlayer = enemy1;
            } else if (i==1){
                returnPlayer = enemy2;
            } else if (i==2){
                returnPlayer = enemy3;
            }
        }
        return returnPlayer;

    }

    private double distanceBetweenPlayers(Player playeruno, Player playerdos){
        return Math.sqrt((playeruno.getPosition().x-playerdos.getPosition().x)*(playeruno.getPosition().x-playerdos.getPosition().x)+(playeruno.getPosition().y-playerdos.getPosition().y)*(playeruno.getPosition().y-playerdos.getPosition().y));
    }

    private void playOffense(){
        Vector2 enemyPosition;
        if (getEnemyWithPossession() != null){
            enemyPosition=new Vector2(getEnemyWithPossession().getPosition().x,getEnemyWithPossession().getPosition().y);
            Vector2 newPosition=new Vector2(enemyPosition.x-100,enemyPosition.y+100);
            Player closestEnemy=getClosestEnemy(newPosition);
            float xDist = (newPosition.x) - (closestEnemy.getPosition().x);
            float yDist = (newPosition.y) - (closestEnemy.getPosition().y);
            if (Math.sqrt(xDist*xDist + yDist*yDist)<catchableDistance) {
                float Vx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                float Vy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
                closestEnemy.setX(closestEnemy.getPosition().x + Vx);
                closestEnemy.setY(closestEnemy.getPosition().y + Vy);
                enemy2.setRotation((float) Math.toDegrees(Math.atan(yDist/xDist)));
                if(xDist < 0){
                    enemy2.setRotation(enemy2.getRotation()+180);
                }
                if(enemy2.getRotation() < 0){
                    enemy2.setRotation(enemy2.getRotation()+360);
                }
            }
        }
        //Gdx.app.error("Enemy With Possession",enemyPosition.toString());
    }

    private Player getEnemyWithPossession (){
        ArrayList<Boolean> valueArray=new ArrayList<>(playerPossData.values());
        ArrayList<Player> keyArray=new ArrayList<>(playerPossData.keySet());
        int indexOfTrue=valueArray.indexOf(true);
        if (indexOfTrue != -1) {
            return keyArray.get(indexOfTrue);
        }
        else {
            return null;
        }
    }

    private Player getClosestEnemy(Vector2 newPosition) {
        Player closestEnemy=null;
        float closestDistance=999999999999f;
        for (Player player:players){
            float xDist = (newPosition.x) - (player.getPosition().x);
            float yDist = (newPosition.y) - (player.getPosition().y);
            float distance=(float)Math.sqrt(xDist*xDist + yDist*yDist);
            if (distance<closestDistance && player != getEnemyWithPossession()){
                closestDistance=distance;
                closestEnemy=player;
            }
        }
        if (closestEnemy != null) {
            return closestEnemy;
        }
        else {
            return null;
        }
    }


    private double getDistanceToDisk(Player player){
        double distToDisk;
        float X = player.getPosition().x;
        float Y = player.getPosition().y;
        float Wd = player.getTexture().getWidth();
        float Ht = player.getTexture().getHeight();
        distToDisk = Math.sqrt(Math.pow(X+Wd/2-(disk.getX()+diskWd/2),2) + Math.pow(Y+Ht/2-(disk.getY()+diskHt/2),2));
        return distToDisk;
    }

    private void switchToPlayerWithDisk() {
        float currP1X = player1.getPosition().x;
        float currP1Y = player1.getPosition().y;
        float currP1R = rotation;
        float currC1X = 0;
        float currC1Y = 0;
        float currC1R = 0;
        if (cpuPlayer.hasDisk()){
            currC1X = cpuPlayer.getPosition().x;
            currC1Y = cpuPlayer.getPosition().y;
            currC1R = cpuRotation;
            cpuPlayer.setX(currP1X);
            cpuPlayer.setY(currP1Y);
            cpuRotation = currP1R;
            cpuPlayer.setHoldingDisk(false);
            playerPossData.replace(cpuPlayer,false);
        } else if (cpu2Player.hasDisk()){
            currC1X = cpu2Player.getPosition().x;
            currC1Y = cpu2Player.getPosition().y;
            currC1R = cpu2Rotation;
            cpu2Player.setX(currP1X);
            cpu2Player.setY(currP1Y);
            cpu2Rotation = currP1R;
            cpu2Player.setHoldingDisk(false);
            playerPossData.replace(cpu2Player,false);
        }
        player1.setX(currC1X);
        player1.setY(currC1Y);
        rotation = currC1R;
        player1.setHoldingDisk(true);
        playerPossData.replace(player1,true);
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
        players.add(cpu2Player);
        players.add(enemy1);
        players.add(enemy2);
        players.add(enemy3);

        for(Player p : players){
            if (p.getPosition().x + playerWd/2 <= 0) {
                p.setX(p.getPosition().x + 1);
            }
            if (p.getPosition().x + playerWd/2 >= w) {
                p.setX(p.getPosition().x - 1);
            }
            if (p.getPosition().y + playerHt/2 <= 0) {
                p.setY(p.getPosition().y + 1);
            }
            if (p.getPosition().y + playerHt/2 >= h) {
                p.setY(p.getPosition().y - 1);
            }
        }
    }

    @Override
    protected void render(SpriteBatch sb) {
        //world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        //player1.setPosition(body.getPosition().x, body.getPosition().y);
        //sb.setProjectionMatrix(camera.combined);
        Gdx.app.log("Cpu1", ""+cpuAIReleased);
        Gdx.app.log("Cpu2", ""+cpu2AIReleased);
        drawCounter++;
        sb.begin();

        sb.draw(background, 0,0, w, h);
        float stallWidth = Gdx.graphics.getWidth()/2;
        float stallX = Gdx.graphics.getWidth()/4;
        if(stallTime>0/* && onOffense*/){
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
            } else if (stallTime < 11){
                timeOfStall = GAME_TIME-playTime;
                stallTime = 0;
                airTime = 0;
                if(onOffense){
                    changingPoss = true;
                    p1Threw = true;
                } else {
                    enemyThrew = true;
                }
                turnover();
                diskInAir = false;



                airTime = 0;

            } else {

            }
        }
        stallStallTime = (GAME_TIME-playTime) - timeOfStall;
        if(stallStallTime>0 && timeOfStall!= 0 && stallStallTime<1){
            sb.draw(stallCount0, stallX, 0, stallWidth, h);
        }
        if(Math.abs(airTime-timeToVertex)<0.05){
            //Gdx.app.log("Time", ""+timeToVertex);
        }
        sb.draw(selectRing, (float) (player1.getPosition().x+51.2/2), (float) (player1.getPosition().y+51.2/2), (float) (selectRing.getWidth()*0.8), (float) (selectRing.getHeight()*0.8));
        //Gdx.app.log("Time", ""+airTime);




        sb.draw(player1.getTexture(),player1.getPosition().x,player1.getPosition().y,playerWd/2,playerHt/2,playerWd,playerHt,1,1,rotation,0,0,Math.round(playerWd),Math.round(playerHt),false,false);
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

        Gdx.app.log("disk", ""+diskScale);

        sb.draw(cpuPlayer.getTexture(),cpuPlayer.getPosition().x,cpuPlayer.getPosition().y,cpuPlayer.getTexture().getWidth()/2,cpuPlayer.getTexture().getHeight()/2,cpuPlayer.getTexture().getWidth(),cpuPlayer.getTexture().getHeight(),1,1,cpuRotation,0,0,Math.round(cpuPlayer.getTexture().getWidth()),Math.round(cpuPlayer.getTexture().getHeight()),false,false);
        sb.draw(cpu2Player.getTexture(),cpu2Player.getPosition().x,cpu2Player.getPosition().y,cpu2Player.getTexture().getWidth()/2,cpu2Player.getTexture().getHeight()/2,cpu2Player.getTexture().getWidth(),cpu2Player.getTexture().getHeight(),1,1,cpu2Rotation,0,0,Math.round(cpu2Player.getTexture().getWidth()),Math.round(cpu2Player.getTexture().getHeight()),false,false);
        sb.draw(enemyTexture,enemy1.getPosition().x,enemy1.getPosition().y,enemyTexture.getWidth()/2,enemyTexture.getHeight()/2,enemyTexture.getWidth(),enemyTexture.getHeight(),1,1,enemy1.getRot(),0,0,Math.round(enemyTexture.getWidth()), Math.round(enemyTexture.getHeight()),false,false);
        sb.draw(enemyTexture,enemy2.getPosition().x,enemy2.getPosition().y,enemyTexture.getWidth()/2,enemyTexture.getHeight()/2,enemyTexture.getWidth(),enemyTexture.getHeight(),1,1,enemy2.getRot(),0,0,Math.round(enemyTexture.getWidth()), Math.round(enemyTexture.getHeight()),false,false);
        sb.draw(enemyTexture,enemy3.getPosition().x,enemy3.getPosition().y,enemyTexture.getWidth()/2,enemyTexture.getHeight()/2,enemyTexture.getWidth(),enemyTexture.getHeight(),1,1,enemy3.getRot(),0,0,Math.round(enemyTexture.getWidth()), Math.round(enemyTexture.getHeight()),false,false);
        if(onOffense){
            scoreboardLeft.draw(sb);
        } else if (!onOffense){
            scoreboardRight.draw(sb);
        }
        disk.setSize(diskWd*diskScale, diskHt*diskScale);
        disk.draw(sb);
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
        //world.dispose();
    }

    public void resetAfterScore(boolean playerScored){
        player1.setHoldingDisk(false);
        playerPossData.replace(player1,false);
        cpuPlayer.setHoldingDisk(false);
        playerPossData.replace(cpuPlayer,false);
        cpu2Player.setHoldingDisk(false);
        playerPossData.replace(cpu2Player,false);
        enemy1.setHoldingDisk(false);
        playerPossData.replace(enemy1,false);
        enemy2.setHoldingDisk(false);
        playerPossData.replace(enemy2,false);
        enemy3.setHoldingDisk(false);
        playerPossData.replace(enemy3,false);
        changingPoss = false;
        selectWaypoints.clear();
        select2Waypoints.clear();
        cpuWayPoint = 0;
        cpu2WayPoint = 0;
        cpuAIReleased = true;
        cpu2AIReleased = true;
        player1.setX(w/6 - playerWd/2);
        player1.setY(h/2 - playerHt/2);
        rotation = 0;
        cpuPlayer.setX(w/6 - playerWd/2);
        cpuPlayer.setY(h/4 - playerHt/2);
        cpuRotation = 0;
        cpu2Player.setX(w/6 - playerWd/2);
        cpu2Player.setY((3*h)/4 - playerHt/2);
        cpu2Rotation = 0;
        enemy1.setX(5*w/6 - playerWd/2);
        enemy1.setY(h/4 - playerHt/2);
        enemy1.setRot(180);
        enemy2.setX(5*w/6 - playerWd/2);
        enemy2.setY(3*h/4 - playerHt/2);
        enemy2.setRot(180);
        enemy3.setX(5*w/6 - playerWd/2);
        enemy3.setY(h/2 - playerHt/2);
        enemy3.setRot(180);
        cpuVelocityX = 0;
        cpuVelocityY = 0;
        cpu2VelocityX = 0;
        cpu2VelocityY = 0;
        player1VelocityX = 0;
        player1VelocityY = 0;
        enemy1VelocityX = 0;
        enemy1VelocityY = 0;
        enemy2VelocityX = 0;
        enemy2VelocityY = 0;
        enemy3VelocityX = 0;
        enemy3VelocityY = 0;
        boolean isJohnGay = true;
        if(playerScored){
            disk.setX(5*w/6 - diskWd/2);
            disk.setY(h/2-diskHt/2);
            diskVx = 0;
            diskVy = 0;
            onOffense = false;
            enemy3.setHoldingDisk(true);
            playerPossData.replace(enemy3,true);
            //changingPoss = true;
            } else {
            disk.setX(w/6 -diskWd/2);
            disk.setY(h/2 - diskHt/2);
            diskVx = 0;
            diskVy = 0;
            onOffense = true;
            player1.setHoldingDisk(true);
            playerPossData.replace(player1,true);
        }
        cpuAIReleased = true;
        cpu2AIReleased = true;


    }

    public void turnover(){
        onOffense = !(onOffense);
        if(player1.hasDisk()){
            player1.setHoldingDisk(false);
            playerPossData.replace(player1,false);
        } else if (enemy1.hasDisk()){
            enemy1.setHoldingDisk(false);
            playerPossData.replace(enemy1,false);
        } else if (enemy2.hasDisk()){
            enemy2.setHoldingDisk(false);
            playerPossData.replace(enemy2,false);
        } else if (enemy3.hasDisk()){
            enemy3.setHoldingDisk(false);
            playerPossData.replace(enemy3,false);
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
            if(player1.hasDisk() || cpuPlayer.hasDisk() ||cpu2Player.hasDisk()) {
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
                    if(velocity>18){
                        timeToVertex = 38*((1/(Math.pow((3*velocity), 0.1))))*Gdx.graphics.getDeltaTime();
                        isShortPass = false;
                    } else {
                        //timeToVertex = 0;
                        timeToVertex = 38*((1/(Math.pow((3*velocity), 0.1))))*Gdx.graphics.getDeltaTime();
                        isShortPass = true;
                    }

                    Gdx.app.log("disk", "timetoVertex" + timeToVertex);
                }
                //Gdx.app.log("disk", ""+diskCurve);

                if (player1.hasDisk()) {
                    p1Threw = true;
                    player1.setHoldingDisk(false);
                    playerPossData.replace(player1,false);
                } else if (cpuPlayer.hasDisk()) {
                    cpuThrew = true;
                    cpuPlayer.setHoldingDisk(false);
                    playerPossData.replace(cpuPlayer,false);
                }  else if (cpu2Player.hasDisk()) {
                    cpu2Threw = true;
                    cpu2Player.setHoldingDisk(false);
                    playerPossData.replace(cpu2Player,false);
                }

            }
        }

        timeOfThrow = GAME_TIME-playTime;
    }

/*public void smartRoute(ArrayList<Vector2> waypoints, Player player){
        float xPt = waypoints.get(0).x;
        float yPt = waypoints.get(0).y;
        float xDist = (xPt) - (player.getPosition().x+playerWd/2);
        float yDist = (yPt) - (player.getPosition().y+playerHt/2);
        float pVx = 5f * (xDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
        float pVy = 5f * (yDist / ((float) Math.sqrt(xDist * xDist + yDist * yDist)));
        cpuVelocityX = pVx;
        cpuVelocityY = pVy;
    }*/



    void drawDebug() {
        Array<Vector2> input = swipe.input();
        int maxDrawPoints = 10;


        //draw the raw input
/*        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.GRAY);
        for (int i=0; i<input.size-1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i+1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();*/

        //draw the smoothed and simplified path
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.WHITE);
        Array<Vector2> out = swipe.path();
        for (int i=0; i<out.size-1; i++) {
            Vector2 p = out.get(i);
            Vector2 p2 = out.get(i+1);
            shapes.rectLine(p.x, p.y, p2.x, p2.y,10);
        }
        shapes.end();


        //render our perpendiculars
/*        shapes.begin(ShapeRenderer.ShapeType.Line);
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
        shapes.end();*/
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        float allyX = cpuPlayer.getPosition().x;
        float allyY = cpuPlayer.getPosition().y;
        y = h - y;
        Gdx.app.log("Tap", "TapInputX"+x);
        Gdx.app.log("Tap", "TapInputY"+y);
        Gdx.app.log("Tap", "AllyX"+allyX);
        Gdx.app.log("Tap", "AllyY"+allyY);

        float allyRotation = cpuRotation;
        if (/*(Math.abs(x - allyX)<=20 && Math.abs(y - allyY)<=20)&& */(!onOffense)){
            Gdx.app.log("Tap", "Tapping");
            cpuPlayer.setX(player1.getPosition().x);
            cpuPlayer.setY(player1.getPosition().y);
            cpuPlayer.setPosition(player1.getPosition().x, player1.getPosition().y);
            cpuPlayer.setRotation(rotation);
            cpuRotation = rotation;
            player1.setX(allyX);
            player1.setY(allyY);
            player1.setPosition(allyX, allyY);
            player1.setRotation(allyRotation);
            rotation = allyRotation;
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
            if((Math.abs(firstPoint.x - (disk.getX()+diskWd/2))<=100 && Math.abs(firstPoint.y - (disk.getY()+diskHt/2))<=100)&&(player1.hasDisk())&&onOffense){
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
                if(acceleration<0.1){
                    averageVelocity *= 0.4;
                }
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
                double relativeTheta = rotation-absoluteTheta;
                playerRotationAtTimeOfThrow = rotation;
                if(relativeTheta<0){
                    relativeTheta += 360;
                }
                relativeTheta = 360-relativeTheta;
                double playerDirX = (Math.cos((rotation*2*Math.PI)/360));
                double playerDirY = (Math.sin((rotation*2*Math.PI)/360));
                double projectionScale = (playerDirX*(input.first().x-player1.getPosition().x)+playerDirY*(input.first().y-player1.getPosition().y))/(playerDirX*playerDirX+playerDirY*playerDirY);


                acceleration*=((3/(Math.sqrt(16.5)))*(Math.sqrt(averageVelocity)));
                Gdx.app.log("Test,", "rotation" + rotation);
                //Gdx.app.log("Test", "playerDirX " + playerDirX);
                //Gdx.app.log("Test", "playerDirY " + playerDirY);
                //Gdx.app.log("Test", "vx " + (input.first().x-player1.getPosition().x));
                //Gdx.app.log("Test", "vy " + (input.first().y-player1.getPosition().y));
                Gdx.app.log("Test", "scale " + projectionScale);

                //Gdx.app.log("Smart Swipe", "Rotation: "+rotation);
                //Gdx.app.log("Smart Swipe", "Frisbee Direction: "+relativeTheta);
                if((!(relativeTheta>90&&relativeTheta<270))&&projectionScale>0){
                    smartThrow(relativeTheta,averageVelocity,acceleration);
                    diskInAir = true;
                    Gdx.app.log("Disk", "diskInAir true");
                }

            } else if(Math.abs(firstPoint.x - (cpuPlayer.getPosition().x+playerWd/2)) <= 100 && Math.abs(firstPoint.y - (cpuPlayer.getPosition().y+playerHt/2)) <= 100){
                cpuAIReleased = false;
                //cpuPlayer.setVelocity(10);

                double arcLength = 0;
                for (int i = 0; i<input.size-2; i++){
                    double pointDistance = Math.sqrt(Math.pow((input.get(i+1).x-input.get(i).x),2)+Math.pow((input.get(i+1).y-input.get(i).y),2));
                    arcLength+=pointDistance;
                }
                cpuSpeed = (float) arcLength/input.size-2;
                if(cpuSpeed>45){
                    cpuSpeed = 45;
                }
                selectWaypoints.clear();
                cpuWayPoint = 0;
                int x = 0;
                for(int i = input.size -1; i > 0; i--){
                    x++;
                    if(x%1 == 0) {
                        selectWaypoints.add(input.get(i));
                    }
                }
                //smartRoute(selectWaypoints, cpuPlayer);
            } else if(Math.abs(firstPoint.x - (cpu2Player.getPosition().x+playerWd/2)) <= 50 && Math.abs(firstPoint.y - (cpu2Player.getPosition().y+playerHt/2)) <= 50){
                cpu2AIReleased = false;
                //cpuPlayer.setVelocity(10);
                double arcLength = 0;
                for (int i = 0; i<input.size-2; i++){
                    double pointDistance = Math.sqrt(Math.pow((input.get(i+1).x-input.get(i).x),2)+Math.pow((input.get(i+1).y-input.get(i).y),2));
                    arcLength+=pointDistance;
                }
                cpu2Speed = (float) arcLength/input.size-2;
                if(cpu2Speed>45){
                    cpu2Speed = 45;
                }
                select2Waypoints.clear();
                cpu2WayPoint = 0;

                int x = 0;
                for(int i = input.size -1; i > 0; i--){
                    x++;
                    if(x%1 == 0) {
                        select2Waypoints.add(input.get(i));
                    }
                }
                //smartRoute(selectWaypoints, cpuPlayer);
            }
        }

        Gdx.app.log("Swipe", "Completed");
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
        //john stop pinching me
    }
}
