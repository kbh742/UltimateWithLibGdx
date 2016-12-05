package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.example.kholt6406.frisbee_app.sprites.PlayerCard;
import com.badlogic.gdx.scenes.scene2d.InputEvent;


/**
 * Created by kholt6406 on 10/17/2016.
 */
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
    float xMultiplier=w/WORLD_WIDTH;
    float yMultiplier=h/WORLD_HEIGHT;

    //private ImageButton backBtn;
    //private Skin backBtnSkin;
    private ImageButton.ImageButtonStyle backBtnStyle;
    int[] positions = {1240, 150, 210, 500, 580, 500, 960, 500, 415, 200, 770, 200, 1280, 485};
    int drawCounter = 0;


    public ManagerState(GameStateManager gsm) {
        super(gsm);
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
        playerBtn1.setBounds(positions[2]*xMultiplier, positions[3]*yMultiplier, playerBtn1.getWidth(), playerBtn1.getHeight());  //tells the button where to go

        playerBtnSkin2 = new Skin();   //create button skin
        playerBtnSkin2.add("playerBtn2", new Texture("player_portrait_2.png"));    //add the image to the skin
        playerBtnStyle2 = new ImageButton.ImageButtonStyle();  //create button style
        playerBtnStyle2.imageUp = playerBtnSkin2.getDrawable("playerBtn2");  //sets the button appearance when it is not pressed
        playerBtnStyle2.imageDown = playerBtnSkin2.getDrawable("playerBtn2");    //sets the button appearance when it is pressed
        playerBtn2 = new ImageButton(playerBtnStyle2);    //initializes the ImageButton with the created style as a parameter
        playerBtn2.setBounds(positions[4]*xMultiplier, positions[5]*yMultiplier, playerBtn2.getWidth(), playerBtn2.getHeight());  //tells the button where to go

        playerBtnSkin3 = new Skin();   //create button skin
        playerBtnSkin3.add("playerBtn3", new Texture("player_portrait_3.png"));    //add the image to the skin
        playerBtnStyle3 = new ImageButton.ImageButtonStyle();  //create button style
        playerBtnStyle3.imageUp = playerBtnSkin3.getDrawable("playerBtn3");  //sets the button appearance when it is not pressed
        playerBtnStyle3.imageDown = playerBtnSkin3.getDrawable("playerBtn3");    //sets the button appearance when it is pressed
        playerBtn3 = new ImageButton(playerBtnStyle3);    //initializes the ImageButton with the created style as a parameter
        playerBtn3.setBounds(positions[6]*xMultiplier, positions[7]*yMultiplier, playerBtn3.getWidth(), playerBtn3.getHeight());  //tells the button where to go

        playerBtnSkin4 = new Skin();   //create button skin
        playerBtnSkin4.add("playerBtn4", new Texture("player_portrait_4.png"));    //add the image to the skin
        playerBtnStyle4 = new ImageButton.ImageButtonStyle();  //create button style
        playerBtnStyle4.imageUp = playerBtnSkin4.getDrawable("playerBtn4");  //sets the button appearance when it is not pressed
        playerBtnStyle4.imageDown = playerBtnSkin4.getDrawable("playerBtn4");    //sets the button appearance when it is pressed
        playerBtn4 = new ImageButton(playerBtnStyle4);    //initializes the ImageButton with the created style as a parameter
        playerBtn4.setBounds(positions[8]*xMultiplier, positions[9]*yMultiplier, playerBtn4.getWidth(), playerBtn4.getHeight());  //tells the button where to go

        playerBtnSkin5 = new Skin();   //create button skin
        playerBtnSkin5.add("playerBtn5", new Texture("player_portrait_5.png"));    //add the image to the skin
        playerBtnStyle5 = new ImageButton.ImageButtonStyle();  //create button style
        playerBtnStyle5.imageUp = playerBtnSkin5.getDrawable("playerBtn5");  //sets the button appearance when it is not pressed
        playerBtnStyle5.imageDown = playerBtnSkin5.getDrawable("playerBtn5");    //sets the button appearance when it is pressed
        playerBtn5 = new ImageButton(playerBtnStyle5);    //initializes the ImageButton with the created style as a parameter
        playerBtn5.setBounds(positions[10]*xMultiplier, positions[11]*yMultiplier, playerBtn5.getWidth(), playerBtn5.getHeight());  //tells the button where to go



        
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
        if(playerBtn1.isPressed()&&(playerBtn1.isDisabled()==false)){
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
        if(playerBtn2.isPressed()&&(playerBtn2.isDisabled()==false)){
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
        if(playerBtn3.isPressed()&&(playerBtn3.isDisabled()==false)){
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
        if(playerBtn4.isPressed()&&(playerBtn4.isDisabled()==false)){
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
        if(playerBtn5.isPressed()&&(playerBtn5.isDisabled()==false)){
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

    }

    @Override
    protected void update(float dt) {
        handleInput();
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.begin();
        drawCounter++;
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        sb.draw(playerCard, positions[0]*xMultiplier, positions[1]*yMultiplier);
        sb.draw(playerFrame, positions[2]*xMultiplier, positions[3]*yMultiplier);
        sb.draw(playerFrame, positions[4]*xMultiplier, positions[5]*yMultiplier);
        sb.draw(playerFrame, positions[6]*xMultiplier, positions[7]*yMultiplier);
        sb.draw(playerFrame, positions[8]*xMultiplier, positions[9]*yMultiplier);
        sb.draw(playerFrame, positions[10]*xMultiplier, positions[11]*yMultiplier);

        //sb.draw(playerPortrait1, positions[2], positions[3]);
        //sb.draw(playerPortrait2, positions[4], positions[5]);
        //sb.draw(playerPortrait3, positions[6], positions[7]);
        //sb.draw(playerPortrait4, positions[8], positions[9]);
        //sb.draw(playerPortrait5, positions[10], positions[11]);
        //backBtn.draw(sb, 1);

        playerBtn1.draw(sb, 1);
        playerBtn2.draw(sb, 1);
        playerBtn3.draw(sb, 1);
        playerBtn4.draw(sb, 1);
        playerBtn5.draw(sb, 1);

        if(selected >= 0){
            sb.draw(playerSelected, (positions[(selected+1)*2]-10)*xMultiplier, (positions[(selected+1)*2+1]-10)*yMultiplier);
            Texture playerPreview = new Texture("player_portrait_" + (selected+1) + ".png");
            sb.draw(playerPreview, positions[12]*xMultiplier, positions[13]*yMultiplier);
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
        playerBtn1.setBounds(positions[2]*xMultiplier, positions[3]*yMultiplier, playerBtn1.getWidth(), playerBtn1.getHeight());  //tells the button where to go
        playerBtn2.setBounds(positions[4]*xMultiplier, positions[5]*yMultiplier, playerBtn2.getWidth(), playerBtn2.getHeight());  //tells the button where to go
        playerBtn3.setBounds(positions[6]*xMultiplier, positions[7]*yMultiplier, playerBtn3.getWidth(), playerBtn3.getHeight());  //tells the button where to go
        playerBtn4.setBounds(positions[8]*xMultiplier, positions[9]*yMultiplier, playerBtn4.getWidth(), playerBtn4.getHeight());  //tells the button where to go
        playerBtn5.setBounds(positions[10]*xMultiplier, positions[11]*yMultiplier, playerBtn5.getWidth(), playerBtn5.getHeight());  //tells the button where to go
    }

    int[][] stats = new int[5][5];
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