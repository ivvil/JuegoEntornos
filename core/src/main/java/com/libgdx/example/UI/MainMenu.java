package com.libgdx.example.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.libgdx.example.Main;

public class MainMenu implements Screen {
    private TextureRegion title;
    private SpriteBatch batch;
    private final Main game;
    private OrthographicCamera camera;
    private Stage stage;

    public MainMenu(Main game) {
        this.game = game;
    }


    @Override
    public void show() {
        BitmapFont font = new BitmapFont(Gdx.files.internal("new.fnt"));

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton playButton = new TextButton("Play", style);
        table.add(playButton).padTop(100);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evt, float x, float y) {
                System.out.println("Play");
            }
        });
    }

    @Override
    public void render(float delta) {
        show();
        Gdx.gl.glClearColor(0, 0.2f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
