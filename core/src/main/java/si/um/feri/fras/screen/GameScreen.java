package si.um.feri.fras.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import si.um.feri.fras.Board;
import si.um.feri.fras.FrasBoardGame;
import si.um.feri.fras.assets.AssetDescriptors;
import si.um.feri.fras.config.GameConfig;
import si.um.feri.fras.global.GameManager;
import si.um.feri.fras.global.loading.BoardConfiguration;
import si.um.feri.fras.global.loading.Island;

public class GameScreen extends ScreenAdapter {

    private final FrasBoardGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Viewport hudViewport;

    private Stage gameplayStage;
    private Stage hudStage;

    private Skin skin;
    private TextureAtlas gameplayAtlas;

    private Music gameMusic;

    private float timer = 0f;  // Timer in seconds
    private boolean timerRunning = true;  // Flag to control if the timer is running

    private Label timerLabel;

    private Board board;

    public GameScreen(FrasBoardGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);

        gameplayStage = new Stage(viewport, game.getBatch());
        hudStage = new Stage(hudViewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);

        gameMusic = assetManager.get(AssetDescriptors.GAME_MUSIC);
        if(GameManager.INSTANCE.isMusicEnabled()) {
            Music menuMusic = assetManager.get(AssetDescriptors.MAIN_MENU_MUSIC);
            if(menuMusic.isPlaying()) {
                menuMusic.stop();
            }
            gameMusic.play();
        }


        gameplayStage.addActor(createGrid());
        hudStage.addActor(createBackButton());
        hudStage.addActor(createCheckResultButton());
        timerLabel = createTimer();
        hudStage.addActor(timerLabel);

        Gdx.input.setInputProcessor(new InputMultiplexer(hudStage, gameplayStage));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(195 / 255f, 195 / 255f, 195 / 255f, 0f);

        if(!GameManager.INSTANCE.isMusicEnabled()) {
            gameMusic.stop();
        }
        else{
            if(!gameMusic.isPlaying()) {
                gameMusic.play();
            }
        }

        updateTimer(delta);

        // update
        gameplayStage.act(delta);
        hudStage.act(delta);

        // draw
        gameplayStage.draw();
        hudStage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameplayStage.dispose();
        hudStage.dispose();
    }


    private Actor createBackButton() {
        final TextButton backButton = new TextButton("Back", skin);
        backButton.setWidth(100);
        backButton.setPosition(GameConfig.HUD_WIDTH / 2f - backButton.getWidth() / 2f, 20f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        return backButton;
    }

    private Table createGrid() {
        Table table = new Table();
        table.setFillParent(true);
        ArrayList<Island> islands = GameManager.INSTANCE.getRandomBoardBySize(GameManager.INSTANCE.getGridSize());
        board = new Board(GameManager.INSTANCE.getGridSize(), GameManager.INSTANCE.getGridSize(), 5f, assetManager, islands);

        table.add(board)
            .pad(20)
            .align(Align.center)
            .expand();
        //table.setDebug(true); // Remove this line once you're satisfied with the layout

        return table; // Return the configured table
    }

    private Actor createCheckResultButton() {
        final TextButton checkResultButton = new TextButton("Check Result", skin);
        checkResultButton.setWidth(150);
        checkResultButton.setPosition(GameConfig.HUD_WIDTH / 2f - checkResultButton.getWidth() / 2f, 100f);  // Position above back button
        checkResultButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Stop the timer when the button is clicked
                if(board.validateSolution()) {
                    timerRunning = false;
                    GameManager.INSTANCE.addResult((int)timer);

                }
            }
        });
        return checkResultButton;
    }


    private Label createTimer(){
        // Create the timer label
        Label timerLabel;
        Label.LabelStyle timerStyle = new Label.LabelStyle();
        timerStyle.font = skin.getFont("font");  // Ensure you have a default font in the skin
        timerLabel = new Label("Time: 0s", timerStyle);
        timerLabel.setPosition(GameConfig.HUD_WIDTH - 120, GameConfig.HUD_HEIGHT - 30); // Top right corner

        return timerLabel;
    }

    private void updateTimer(float delta) {
        if (timerRunning) {
            timer += delta;  // Increment timer by delta (time passed since last frame)
            timerLabel.setText("Time: " + (int) timer + "s");  // Update the label text
        }
    }



}
