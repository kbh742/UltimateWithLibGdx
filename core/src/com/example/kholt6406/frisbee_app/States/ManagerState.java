package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Random;


public class ManagerState extends State {
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    private Stage stage;
    private Texture background;
    private Texture playerFrame;
    private Texture playerCard;
    private Texture playerPortrait1;
    private Texture playerPortrait2;
    private Texture playerPortrait3;
    private Texture playerPortrait4;
    private Texture playerPortrait5;
    private Texture playerSelected;
    int selected = -1;

    private ImageButton playerBtn1;
    private Skin playerBtnSkin1;
    private ImageButton.ImageButtonStyle playerBtnStyle1;

    private ImageButton playerBtn2;
    private Skin playerBtnSkin2;
    private ImageButton.ImageButtonStyle playerBtnStyle2;

    private ImageButton playerBtn3;
    private Skin playerBtnSkin3;
    private ImageButton.ImageButtonStyle playerBtnStyle3;

    private ImageButton playerBtn4;
    private Skin playerBtnSkin4;
    private ImageButton.ImageButtonStyle playerBtnStyle4;

    private ImageButton playerBtn5;

    private Skin playerBtnSkin5;
    private ImageButton.ImageButtonStyle playerBtnStyle5;

    public final int WORLD_WIDTH=1920;
    public final int WORLD_HEIGHT=1080;
    float xScl =w/WORLD_WIDTH;
    float yScl =h/WORLD_HEIGHT;

    //private ImageButton backBtn;
    //private Skin backBtnSkin;
    private ImageButton.ImageButtonStyle backBtnStyle;
    int[] positions = {1240, 150, 235, 500, 600, 500, 975, 500, 440, 200, 785, 200, 1280, 485};
    int drawCounter = 0;
    static int[][] stats= new int[5][6];
    FreeTypeFontGenerator freeTypeFontGenerator=new FreeTypeFontGenerator(Gdx.files.internal("lucon.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter=new FreeTypeFontGenerator.FreeTypeFontParameter();


    BitmapFont font;
    BitmapFont font2;
    BitmapFont font3;
    public int size = 10;


    public ManagerState(GameStateManager gsm) {
        super(gsm);
        double sum=0;
        for (int y=0; y<5; y++){
            for (int x=0; x<5; x++){
                Random random= new Random();
                int num=random.nextInt(5-1)+1;
                sum=sum+num;
                stats[y][x]=num;
            }
            int avg=(int)Math.round(sum/5);
            stats[y][5]=avg;
            sum=0;
        }
        background = new Texture("team_manager_menu.png");



        /*backBtnSkin = new Skin();
        backBtnSkin.add("backBtn", new Texture("button_back.png"));
        backBtnStyle = new ImageButton.ImageButtonStyle();
        backBtnStyle.imageUp = backBtnSkin.getDrawable("backBtn");
        backBtnStyle.imageDown = backBtnSkin.getDrawable("backBtn");
        backBtn = new ImageButton(backBtnStyle);
        backBtn.setBounds((Gdx.graphics.getWidth()/3)*2-(backBtn.getWidth()/2),(Gdx.graphics.getHeight()/3)-(backBtn.getHeight()/2), backBtn.getWidth(), backBtn.getHeight());*/


        playerCard = new Texture("player_card_small.png");
        playerFrame = new Texture("player_portrait.png");
        playerPortrait1 = new Texture("player_portrait_1.png");
        playerPortrait2 = new Texture("player_portrait_2.png");
        playerPortrait3 = new Texture("player_portrait_3.png");
        playerPortrait4 = new Texture("player_portrait_4.png");
        playerPortrait5 = new Texture("player_portrait_5.png");
        playerSelected = new Texture("player_portrait_select.png");


        playerBtnSkin1 = new Skin();   //create button skin
        playerBtnSkin1.add("playerBtn1", new Texture("player_portrait_1.png"));    //add the image to the skin
        playerBtnStyle1 = new ImageButton.ImageButtonStyle();  //create button style
        playerBtnStyle1.imageUp = playerBtnSkin1.getDrawable("playerBtn1");  //sets the button appearance when it is not pressed
        playerBtnStyle1.imageDown = playerBtnSkin1.getDrawable("playerBtn1");    //sets the button appearance when it is pressed
        playerBtn1 = new ImageButton(playerBtnStyle1);    //initializes the ImageButton with the created style as a parameter
        playerBtn1.setBounds(positions[2]* xScl, positions[3]* yScl, playerBtn1.getWidth()*xScl, playerBtn1.getHeight()*yScl);  //tells the button where to go

        playerBtnSkin2 = new Skin();   //create button skin
        playerBtnSkin2.add("playerBtn2", new Texture("player_portrait_2.png"));    //add the image to the skin
        playerBtnStyle2 = new ImageButton.ImageButtonStyle();  //create button style
        playerBtnStyle2.imageUp = playerBtnSkin2.getDrawable("playerBtn2");  //sets the button appearance when it is not pressed
        playerBtnStyle2.imageDown = playerBtnSkin2.getDrawable("playerBtn2");    //sets the button appearance when it is pressed
        playerBtn2 = new ImageButton(playerBtnStyle2);    //initializes the ImageButton with the created style as a parameter
        playerBtn2.setBounds(positions[4]* xScl, positions[5]* yScl, playerBtn2.getWidth()*xScl, playerBtn2.getHeight()*yScl);  //tells the button where to go

        playerBtnSkin3 = new Skin();   //create button skin
        playerBtnSkin3.add("playerBtn3", new Texture("player_portrait_3.png"));    //add the image to the skin
        playerBtnStyle3 = new ImageButton.ImageButtonStyle();  //create button style
        playerBtnStyle3.imageUp = playerBtnSkin3.getDrawable("playerBtn3");  //sets the button appearance when it is not pressed
        playerBtnStyle3.imageDown = playerBtnSkin3.getDrawable("playerBtn3");    //sets the button appearance when it is pressed
        playerBtn3 = new ImageButton(playerBtnStyle3);    //initializes the ImageButton with the created style as a parameter
        playerBtn3.setBounds(positions[6]* xScl, positions[7]* yScl, playerBtn3.getWidth()*xScl, playerBtn3.getHeight()*yScl);  //tells the button where to go

        playerBtnSkin4 = new Skin();   //create button skin
        playerBtnSkin4.add("playerBtn4", new Texture("player_portrait_4.png"));    //add the image to the skin
        playerBtnStyle4 = new ImageButton.ImageButtonStyle();  //create button style
        playerBtnStyle4.imageUp = playerBtnSkin4.getDrawable("playerBtn4");  //sets the button appearance when it is not pressed
        playerBtnStyle4.imageDown = playerBtnSkin4.getDrawable("playerBtn4");    //sets the button appearance when it is pressed
        playerBtn4 = new ImageButton(playerBtnStyle4);    //initializes the ImageButton with the created style as a parameter
        playerBtn4.setBounds(positions[8]* xScl, positions[9]* yScl, playerBtn4.getWidth()*xScl, playerBtn4.getHeight()*yScl);  //tells the button where to go

        playerBtnSkin5 = new Skin();   //create button skin
        playerBtnSkin5.add("playerBtn5", new Texture("player_portrait_5.png"));    //add the image to the skin
        playerBtnStyle5 = new ImageButton.ImageButtonStyle();  //create button style
        playerBtnStyle5.imageUp = playerBtnSkin5.getDrawable("playerBtn5");  //sets the button appearance when it is not pressed
        playerBtnStyle5.imageDown = playerBtnSkin5.getDrawable("playerBtn5");    //sets the button appearance when it is pressed
        playerBtn5 = new ImageButton(playerBtnStyle5);    //initializes the ImageButton with the created style as a parameter
        playerBtn5.setBounds(positions[10]* xScl, positions[11]* yScl, playerBtn5.getWidth()*xScl, playerBtn5.getHeight()*yScl);  //tells the button where to go


        freeTypeFontParameter.size=36;
        freeTypeFontParameter.color = Color.BLACK;
        font=freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        freeTypeFontParameter.size=30;
        freeTypeFontParameter.color = Color.BLACK;
        font2=freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        freeTypeFontParameter.size=80;
        freeTypeFontParameter.color = Color.BLACK;
        font3=freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        
        stage = new Stage();
        //stage.addActor(backBtn);
        stage.addActor(playerBtn1);
        stage.addActor(playerBtn2);
        stage.addActor(playerBtn3);
        stage.addActor(playerBtn4);
        stage.addActor(playerBtn5);
        Gdx.input.setInputProcessor(stage);




    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new MenuState(gsm));
            dispose();
        }
        if(playerBtn1.isPressed()&&(!playerBtn1.isDisabled())){
            if(selected == 0){
                selected = -1;
            } else if (selected == -1){
                selected = 0;
            } else {
                swap(selected, 0);
            }
            playerBtn1.setDisabled(true);
            drawCounter = 0;
        }
        if(playerBtn2.isPressed()&&(!playerBtn2.isDisabled())){
            if(selected == 1){
                selected = -1;
            } else if (selected == -1){
                selected = 1;
            } else {
                swap(selected, 1);
            }
            playerBtn2.setDisabled(true);
            drawCounter = 0;
            Gdx.app.log("selected", "selected " + selected);
        }
        if(playerBtn3.isPressed()&&(!playerBtn3.isDisabled())){
            if(selected == 2){
                selected = -1;
            } else if (selected == -1){
                selected = 2;
            } else {
                swap(selected, 2);
            }
            playerBtn3.setDisabled(true);
            drawCounter = 0;
            Gdx.app.log("selected", "selected " + selected);
        }
        if(playerBtn4.isPressed()&&(!playerBtn4.isDisabled())){
            if(selected == 3){
                selected = -1;
            } else if (selected == -1){
                selected = 3;
            } else {
                swap(selected, 3);
            }
            playerBtn4.setDisabled(true);
            drawCounter = 0;
            Gdx.app.log("selected", "selected " + selected);
        }
        if(playerBtn5.isPressed()&&(!playerBtn5.isDisabled())){
            if(selected == 4){
                selected = -1;
            } else if (selected == -1){
                selected = 4;
            } else {
                swap(selected, 4);
            }
            playerBtn5.setDisabled(true);
            drawCounter = 0;
            Gdx.app.log("selected", "selected " + selected);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new MenuState(gsm));
            dispose();
        }

    }

    @Override
    protected void update(float dt) {
        handleInput();
    }

    public void setCoolFont(int font){

    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.begin();
        drawCounter++;
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        sb.draw(playerCard, positions[0]* xScl, positions[1]* yScl, playerCard.getWidth()*xScl, playerCard.getHeight()*yScl);
        sb.draw(playerFrame, positions[2]* xScl, positions[3]* yScl, playerFrame.getWidth()*xScl, playerFrame.getHeight()*yScl);
        sb.draw(playerFrame, positions[4]* xScl, positions[5]* yScl, playerFrame.getWidth()*xScl, playerFrame.getHeight()*yScl);
        sb.draw(playerFrame, positions[6]* xScl, positions[7]* yScl, playerFrame.getWidth()*xScl, playerFrame.getHeight()*yScl);
        sb.draw(playerFrame, positions[8]* xScl, positions[9]* yScl, playerFrame.getWidth()*xScl, playerFrame.getHeight()*yScl);
        sb.draw(playerFrame, positions[10]* xScl, positions[11]* yScl, playerFrame.getWidth()*xScl, playerFrame.getHeight()*yScl);

        //sb.draw(playerPortrait1, positions[2], positions[3]);
        //sb.draw(playerPortrait2, positions[4], positions[5]);
        //sb.draw(playerPortrait3, positions[6], positions[7]);
        //sb.draw(playerPortrait4, positions[8], positions[9]);
        //sb.draw(playerPortrait5, positions[10], positions[11]);
        //backBtn.draw(sb, 1);
        font.draw(sb, "Throw", 1300*xScl, 420*yScl);
        font.draw(sb, "Catch", 1300*xScl, 370*yScl);
        font.draw(sb, "Defense", 1300*xScl, 320*yScl);
        font.draw(sb, "Speed", 1300*xScl, 270*yScl);
        font.draw(sb, "Stamina", 1300*xScl, 220*yScl);

        font2.draw(sb, "Overall", 1460*xScl, 620*yScl);

        if (selected != -1) {
            font.draw(sb, Integer.toString(stats[selected][0]), 1500 * xScl, 420 * yScl);
            font.draw(sb, Integer.toString(stats[selected][1]), 1500 * xScl, 370 * yScl);
            font.draw(sb, Integer.toString(stats[selected][2]), 1500 * xScl, 320 * yScl);
            font.draw(sb, Integer.toString(stats[selected][3]), 1500 * xScl, 270 * yScl);
            font.draw(sb, Integer.toString(stats[selected][4]), 1500 * xScl, 220 * yScl);
            font3.draw(sb,Integer.toString(stats[selected][5]), 1450 * xScl, 570 * yScl);
        }

        playerBtn1.draw(sb, 1);
        playerBtn2.draw(sb, 1);
        playerBtn3.draw(sb, 1);
        playerBtn4.draw(sb, 1);
        playerBtn5.draw(sb, 1);

        if(selected >= 0){
            sb.draw(playerSelected, (positions[(selected+1)*2]-10)* xScl, (positions[(selected+1)*2+1]-10)* yScl, playerSelected.getWidth()*xScl, playerSelected.getHeight()*yScl);
            Texture playerPreview = new Texture("player_portrait_" + (selected+1) + ".png");
            sb.draw(playerPreview, positions[12]* xScl, positions[13]* yScl);
        }
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }*/
        if(drawCounter>=10){
            playerBtn1.setDisabled(false);
            playerBtn2.setDisabled(false);
            playerBtn3.setDisabled(false);
            playerBtn4.setDisabled(false);
            playerBtn5.setDisabled(false);
        }



        sb.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {

    }

    public void swap(int card1, int card2){
        int card1x = positions[2*(card1+1)];
        int card1y = positions[2*(card1+1)+1];
        int card2x = positions[2*(card2+1)];
        int card2y = positions[2*(card2+1)+1];
        positions[2*(card1+1)] = card2x;
        positions[2*(card1+1)+1] = card2y;
        positions[2*(card2+1)] = card1x;
        positions[2*(card2+1)+1] = card1y;
        playerBtn1.setBounds(positions[2]* xScl, positions[3]* yScl, playerBtn1.getWidth(), playerBtn1.getHeight());  //tells the button where to go
        playerBtn2.setBounds(positions[4]* xScl, positions[5]* yScl, playerBtn2.getWidth(), playerBtn2.getHeight());  //tells the button where to go
        playerBtn3.setBounds(positions[6]* xScl, positions[7]* yScl, playerBtn3.getWidth(), playerBtn3.getHeight());  //tells the button where to go
        playerBtn4.setBounds(positions[8]* xScl, positions[9]* yScl, playerBtn4.getWidth(), playerBtn4.getHeight());  //tells the button where to go
        playerBtn5.setBounds(positions[10]* xScl, positions[11]* yScl, playerBtn5.getWidth(), playerBtn5.getHeight());  //tells the button where to go
    }

    String[] portraits = new String[5];
    boolean music = false;
    public void changeSpeed(int player, int variation) {
        stats[player][0]+=variation;
    }

    public void changeStamina(int player, int variation){
        stats[player][1]+=variation;
    }

    public void changeThrow(int player, int variation){
        stats[player][2]+=variation;
    }

    public void changeCatch(int player, int variation){
        stats[player][3]+=variation;
    }

    public void changeDefence(int player, int variation){
        stats[player][4]+=variation;
    }


}