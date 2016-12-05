package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MenuState extends State{
    private Stage stage;
    private Texture background;

    private ImageButton playBtn;
    private Skin playBtnSkin;
    private ImageButton.ImageButtonStyle playBtnStyle;

    private ImageButton managerBtn;
    private Skin managerBtnSkin;
    private ImageButton.ImageButtonStyle managerBtnStyle;

    private ImageButton settingsBtn;
    private Skin settingsBtnSkin;
    private ImageButton.ImageButtonStyle settingsBtnStyle;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        Gdx.input.setCatchBackKey(true);

        background = new Texture("official_background.png");
        //String demensions=Gdx.graphics.getWidth()+","+Gdx.graphics.getHeight();

        playBtnSkin = new Skin();   //create button skin
        playBtnSkin.add("playBtn", new Texture("button_play_game.png"));    //add the image to the skin
        playBtnStyle = new ImageButton.ImageButtonStyle();  //create button style
        playBtnStyle.imageUp = playBtnSkin.getDrawable("playBtn");  //sets the button appearance when it is not pressed
        playBtnStyle.imageDown = playBtnSkin.getDrawable("playBtn");    //sets the button appearance when it is pressed
        playBtn = new ImageButton(playBtnStyle);    //initializes the ImageButton with the created style as a parameter
        playBtn.setBounds((Gdx.graphics.getWidth()/3)-(playBtn.getWidth()/2),(Gdx.graphics.getHeight()/3)*2-(playBtn.getHeight()/2), playBtn.getWidth(), playBtn.getHeight());  //tells the button where to go

        managerBtnSkin = new Skin();
        managerBtnSkin.add("managerBtn", new Texture("button_team_manager.png"));
        managerBtnStyle = new ImageButton.ImageButtonStyle();
        managerBtnStyle.imageUp = managerBtnSkin.getDrawable("managerBtn");
        managerBtnStyle.imageDown = managerBtnSkin.getDrawable("managerBtn");
        managerBtn = new ImageButton(managerBtnStyle);
        managerBtn.setBounds((Gdx.graphics.getWidth()/3)*2-(managerBtn.getWidth()/2),(Gdx.graphics.getHeight()/3)*2-(managerBtn.getHeight()/2), managerBtn.getWidth(), managerBtn.getHeight());

        settingsBtnSkin = new Skin();
        settingsBtnSkin.add("settingsBtn", new Texture("button_settings.png"));
        settingsBtnStyle = new ImageButton.ImageButtonStyle();
        settingsBtnStyle.imageUp = settingsBtnSkin.getDrawable("settingsBtn");
        settingsBtnStyle.imageDown = settingsBtnSkin.getDrawable("settingsBtn");
        settingsBtn = new ImageButton(settingsBtnStyle);
        settingsBtn.setBounds((Gdx.graphics.getWidth()/3)-(settingsBtn.getWidth()/2),(Gdx.graphics.getHeight()/3)-(settingsBtn.getHeight()/2), settingsBtn.getWidth(), settingsBtn.getHeight());
        
        //make the stage and add stuff to it, the stage somehow makes everything do stuff
        stage = new Stage();
        stage.addActor(playBtn);
        stage.addActor(managerBtn);
        stage.addActor(settingsBtn);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void handleInput() {
        if (playBtn.isPressed()){
            gsm.set(new PlayState(gsm));
            dispose();
        }

        if (managerBtn.isPressed()){
            gsm.set(new ManagerState(gsm));
            dispose();
        }

        if (settingsBtn.isPressed()){
            gsm.set(new SettingsState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playBtn.draw(sb, 1);
        managerBtn.draw(sb, 1);
        settingsBtn.draw(sb, 1);
        sb.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
