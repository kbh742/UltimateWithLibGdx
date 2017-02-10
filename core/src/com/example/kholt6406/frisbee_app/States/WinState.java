package com.example.kholt6406.frisbee_app.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class WinState extends State {
    private Stage stage;
    private Texture background;
    private Texture winText;
    private GlyphLayout glyphLayoutTest = new GlyphLayout();
    private GlyphLayout glyphLayout = new GlyphLayout();
    private Texture greenBackground;
    float w = Gdx.graphics.getWidth();
    float h= Gdx.graphics.getHeight();
    FreeTypeFontGenerator freeTypeFontGeneratorTest =new FreeTypeFontGenerator(Gdx.files.internal("lucon.ttf"));
    FreeTypeFontGenerator freeTypeFontGenerator =new FreeTypeFontGenerator(Gdx.files.internal("lucon.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameterTest =new FreeTypeFontGenerator.FreeTypeFontParameter();
    FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter =new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont testText;
    BitmapFont pointsText;

    int points;

    public WinState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("win.png");
        greenBackground = new Texture("green_background.png");
        winText= new Texture("win_text1.png");
        points=PlayState.points;
        freeTypeFontParameterTest.size=200;
        freeTypeFontParameterTest.incremental=true;
        testText = freeTypeFontGeneratorTest.generateFont(freeTypeFontParameterTest);
        glyphLayoutTest.setText(testText,"Points: "+Integer.toString(points));
        float ratio= glyphLayoutTest.width/200;
        float fontSize =(w/3)/ratio;
        freeTypeFontParameter.size=Math.round(fontSize);
        freeTypeFontParameter.incremental=true;
        pointsText=freeTypeFontGenerator.generateFont(freeTypeFontParameter);
        glyphLayout.setText(pointsText,"Points: "+Integer.toString(points));






/*        backBtnSkin = new Skin();
        backBtnSkin.add("backBtn", new Texture("button_back.png"));
        backBtnStyle = new ImageButton.ImageButtonStyle();
        backBtnStyle.imageUp = backBtnSkin.getDrawable("backBtn");
        backBtnStyle.imageDown = backBtnSkin.getDrawable("backBtn");*/

        stage = new Stage();
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
        handleInput();
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(greenBackground,0,0,w,h);
        sb.draw(background, 100*PlayState.xScl, 0, w-(200*PlayState.xScl), h/2);
        sb.draw(winText, (w/2)-((winText.getWidth()*PlayState.xScl/3*2)/2), h-(winText.getHeight()*PlayState.yScl/3*2), winText.getWidth()*PlayState.xScl/3*2, winText.getHeight()*PlayState.yScl/3*2);
        pointsText.draw(sb, glyphLayout, (w/2)-(glyphLayout.width/2), h/3*2);
        sb.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
