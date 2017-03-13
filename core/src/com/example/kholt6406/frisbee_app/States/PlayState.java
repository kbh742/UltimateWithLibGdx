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

public class PlayState extends State implements GestureDetector.GestureListener{
    static float w = Gdx.graphics.getWidth();
    static float h = Gdx.graphics.getHeight();
    float scbdWd;
    float scbdHt;

    float xPos=600;
    float yPos=600;
    float playerWd;
    float playerHt;

    OrthographicCamera camera;

    public static final int WORLD_WIDTH=1920;
    public static final int WORLD_HEIGHT=1080;
    static float xScl =w/WORLD_WIDTH;
    static float yScl =h/WORLD_HEIGHT;

    public static final int GAME_TIME=500;

    public static final float CAMERA_HEIGHT=500;

    private Player player1;
    private Player cpuPlayer;

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
    float rotation;
    float cpuRotation;
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
    private double caughtTime;
    private double stallTime;
    private boolean onOffense;
    float diskHeight;
    double timeOfThrow;
    double timeToVertex;
    double airTime;
    float playerHeight;


    int drawCounter = 0;
    boolean inLeftEndZone=false;
    boolean inRightEndZone=false;
    float playerRotationAtTimeOfThrow;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        player1=new Player(400,400);
        cpuPlayer = new Player(800, 600);
        cpuRotation = 0;
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

        caughtTime = 0;
        stallTime = 0;
        playerHeight = -50;
        diskHeight = playerHeight;


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

        scbdTexture = new Texture("scoreboard_left_offense.png");
        scoreboardLeft=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*2*xScl;
        scbdHt = scbdTexture.getHeight()*2*yScl;
        scoreboardLeft.setSize(scbdWd, scbdHt);
        scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardY=h-scbdHt;
        scoreboardLeft.setX(scoreboardX);
        scoreboardLeft.setY(scoreboardY);

        scbdTexture = new Texture("scoreboard_right_offense.png");
        scoreboardRight=new Sprite(scbdTexture);
        scbdWd = scbdTexture.getWidth()*2*xScl;
        scbdHt = scbdTexture.getHeight()*2*yScl;
        scoreboardRight.setSize(scbdWd, scbdHt);
        scoreboardX=(w/2)-(scbdWd)/2;
        scoreboardY=h-scbdHt;
        scoreboardRight.setX(scoreboardX);
        scoreboardRight.setY(scoreboardY);

        //Swipes

        tris = new SwipeTriStrip();
        swipe = new SwipeHandler(250);
        swipe.minDistance = 10;
        swipe.initialDistance = 10;
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
        diskWd = diskTexture.getWidth()*xScl;
        diskHt = diskTexture.getHeight()*yScl;
        disk.setSize(diskWd, diskHt);

        catchableDistance = 100;

        player1.setHoldingDisk(true);
        player1.setX(w/6 - playerWd/2);
        player1.setY(h/2 - playerHt/2);

        cpuPlayer.setX(w/6 - playerWd/2);
        cpuPlayer.setY(h/3 - playerHt/2);

        disk.setX(w/6 -diskWd/2);
        disk.setY(h/2 - diskHt/2);

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
                stallTime = (GAME_TIME-playTime) - caughtTime;

                onOffense = true;
            }
            else if (!player1.hasDisk()){
                player1.setVelocity(10);
                airTime = (GAME_TIME-playTime) - timeOfThrow;
                onOffense = false;
                float c = 150;

               if(Math.abs(diskCurve)>0.1){
                   diskHeight = (float) (((-1*Math.abs(diskCurve*c))/(timeToVertex*timeToVertex))*(airTime-timeToVertex)*(airTime-timeToVertex)+Math.abs(diskCurve)*c+playerHeight);
               } else {
                   diskHeight = (float) (((-1*Math.abs(3*straightV)*(c/30))/(timeToVertex*timeToVertex))*(airTime-timeToVertex)*(airTime-timeToVertex)+Math.abs(straightV)*(c/30)-playerHeight);
               }
                if(diskHeight<-100){
                    diskHeight = -100;
                }
                //Gdx.app.log("Disk", ""+diskHeight);

                //Gdx.app.log("disk", "Disk Height"+diskHeight);
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
                if(rotation<0){
                    rotation += 360;
                }
                int r = 50;
                if(player1.hasDisk()) {
                    disk.setX((float) (player1.getPosition().x + (playerWd * xScl) / 2 - diskWd / 2 + (r * (deltaX / Math.sqrt(deltaX * deltaX + deltaY * deltaY)))));
                    disk.setY((float) (player1.getPosition().y + (playerHt * yScl) / 2 - diskHt / 2 + (r * (deltaY / Math.sqrt(deltaX * deltaX + deltaY * deltaY)))));
                }
            }



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

            if(!player1.hasDisk() && !cpuPlayer.hasDisk()) {
                sideV += diskCurve;
                double alpha = Math.toRadians(playerRotationAtTimeOfThrow - 90); //THIS IS THE PLACE??



                diskVx = (float) (Math.cos(alpha) * sideV - Math.sin(alpha) * straightV);
                diskVy = (float) (Math.sin(alpha) * sideV + Math.cos(alpha) * straightV);
            }


            double player1DistToDisk = Math.sqrt(Math.pow(player1.getPosition().x+playerWd/2-(disk.getX()+diskWd/2),2) + Math.pow(player1.getPosition().y+playerHt/2-(disk.getY()+diskHt/2),2));
            //Gdx.app.log("LALALALA", player1DistToDisk + "");
            if(player1DistToDisk <= catchableDistance && !player1.hasDisk() && !p1Threw){
                player1.setHoldingDisk(true);
                diskVx = 0;
                diskVy = 0;
                caughtTime = GAME_TIME-playTime;
                Gdx.app.log("Catching", "Caught" + caughtTime);

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
            //Gdx.app.log("hello", cpuDis
            // tToDisk + "");
            double cpuDistToDisk = Math.sqrt(Math.pow(cpuX+cpuWd/2-(disk.getX()+diskWd/2),2) + Math.pow(cpuY+cpuHt/2-(disk.getY()+diskHt/2),2));

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
            //Gdx.app.log("CPUROTATION", cpuRotation + "");

            if(cpuDistToDisk <= catchableDistance && !cpuPlayer.hasDisk() && !cpuThrew){
                cpuPlayer.setHoldingDisk(true);
                diskVx = 0;
                diskVy = 0;
            }
            else if(cpuDistToDisk > catchableDistance ){
                cpuPlayer.setHoldingDisk(false);
                cpuThrew = false;
            }
            if(cpuPlayer.hasDisk()){
                float currP1X = player1.getPosition().x;
                float currP1Y = player1.getPosition().y;
                float currP1R = rotation;
                float currC1X = cpuPlayer.getPosition().x;
                float currC1Y = cpuPlayer.getPosition().y;
                float currC1R = cpuRotation;

                player1.setX(currC1X);
                player1.setY(currC1Y);
                rotation = currC1R;
                cpuPlayer.setX(currP1X);
                cpuPlayer.setY(currP1Y);
                cpuRotation = currP1R;

                cpuPlayer.setHoldingDisk(false);
                player1.setHoldingDisk(true);
            }

            //Gdx.app.log("Player Holding Disk?", ""+ player1.hasDisk());
            //Gdx.app.log("Cpu Holding Disk?", ""+cpuPlayer.hasDisk());
            if(!player1.hasDisk()){
                disk.setX(disk.getX() + diskVx);
                disk.setY(disk.getY() + diskVy);
            }

            if (disk.getX()+diskWd/2*xScl <= w/6 && !inLeftEndZone && !player1.hasDisk()){
                team1Score++;
                inLeftEndZone=true;
            } else if (disk.getX()+diskWd/2*xScl > w/6){
                inLeftEndZone=false;
            }

            if (disk.getX()+diskWd/2*xScl >= w-w/6 && !inRightEndZone && player1.hasDisk()){
                team2Score++;
                inRightEndZone=true;
                resetAfterScore();
            } else if (disk.getX()+diskWd/2*xScl < w-w/6){
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
        float stallWidth = Gdx.graphics.getWidth()/2;
        float stallX = Gdx.graphics.getWidth()/4;
        if(stallTime>0){
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
            }
        }
        if(Math.abs(airTime-timeToVertex)<0.05){
            //Gdx.app.log("Time", ""+timeToVertex);
        }
        //Gdx.app.log("Time", ""+airTime);



        sb.draw(player1.getTexture(),xPos,yPos,playerWd/2*xScl,playerHt/2*yScl,playerWd*xScl,playerHt*yScl,1,1,rotation,0,0,Math.round(playerWd),Math.round(playerHt),false,false);
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
        sb.draw(cpuPlayer.getTexture(),cpuPlayer.getPosition().x,cpuPlayer.getPosition().y,cpuPlayer.getTexture().getWidth()/2*xScl,cpuPlayer.getTexture().getHeight()/2*yScl,cpuPlayer.getTexture().getWidth()*xScl,cpuPlayer.getTexture().getHeight()*yScl,1,1,cpuRotation,0,0,Math.round(cpuPlayer.getTexture().getWidth()),Math.round(cpuPlayer.getTexture().getHeight()),false,false);
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
        rotation = 0;
        cpuPlayer.setX(w/6 - playerWd/2);
        cpuPlayer.setY(h/3 - playerHt/2);
        cpuRotation = 0;
        disk.setX(w/6 -diskWd/2);
        disk.setY(h/2 - diskHt/2);
        diskVx = 0;
        diskVy = 0;
        player1.setHoldingDisk(true);
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
        Gdx.app.log("Disk", acceleration + "acc");
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

                    timeToVertex = 750*(1/velocity)*Gdx.graphics.getDeltaTime();
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
        if(firstPoint.x - (disk.getX()+diskWd/2)<=20 && firstPoint.y - (disk.getY()+diskHt/2)<=20){
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
            boolean isJohnGay = true;
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
            double playerDirX = (Math.cos(rotation));
            double playerDirY = (Math.sin(rotation));
            double projectionScale = (playerDirX*(input.first().x-player1.getPosition().x)+playerDirY*(input.first().y-player1.getPosition().y))/(playerDirX*playerDirX+playerDirY*playerDirY);


            acceleration*=((3/(Math.sqrt(16.5)))*(Math.sqrt(averageVelocity)));
            Gdx.app.log("Test", "playerDirX " + playerDirX);
            Gdx.app.log("Test", "playerDirY " + playerDirY);
            Gdx.app.log("Test", "vx " + (input.first().x-player1.getPosition().x));
            Gdx.app.log("Test", "vy " + (input.first().y-player1.getPosition().y));
            Gdx.app.log("Test", "scale " + projectionScale);

            //Gdx.app.log("Smart Swipe", "Rotation: "+rotation);
            //Gdx.app.log("Smart Swipe", "Frisbee Direction: "+relativeTheta);
            if((!(relativeTheta>90&&relativeTheta<270))/*&&projectionScale>0*/){
                smartThrow(relativeTheta,averageVelocity,acceleration);
            }

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
