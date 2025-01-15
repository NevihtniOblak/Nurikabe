package si.um.feri.fras.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import si.um.feri.fras.FrasBoardGame;
import si.um.feri.fras.assets.AssetDescriptors;
import si.um.feri.fras.assets.RegionNames;
import si.um.feri.fras.config.GameConfig;
import si.um.feri.fras.global.GameManager;
import si.um.feri.fras.global.Result;

public class LeaderboardScreen extends ScreenAdapter {

    private final FrasBoardGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    private Skin skin;
    private TextureAtlas uiAtlas;

    private TextureAtlas gameplayAtlas;

    public LeaderboardScreen(FrasBoardGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);

        stage.addActor(createUi());
        //stage.addActor(createLeaderboardTable());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Actor createUi() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.defaults().pad(20);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.SECONDARY_BACKGROUND);
        mainTable.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Add the leaderboard table
        mainTable.add(createLeaderboardTable()).expand().fill().row();

        // Create and add the back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        mainTable.add(backButton).size(120,40).padTop(20).bottom();

        return mainTable;
    }

    private Table createLeaderboardTable() {
        Table leaderboardTable = new Table();
        leaderboardTable.defaults().pad(10); // Padding between cells
        leaderboardTable.pad(20); // Padding around the outer edges of the table
        leaderboardTable.top();

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.ALPHA_BACKGROUND);
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(backgroundRegion);
        backgroundDrawable.setMinWidth(GameConfig.HUD_WIDTH);
        backgroundDrawable.setMinHeight(GameConfig.HUD_HEIGHT);
        leaderboardTable.setBackground(backgroundDrawable.tint(new Color(0, 0, 0, 1f)));

        BitmapFont customFont = skin.getFont("font-custom");
        customFont.getData().setScale(0.5f);

        // Add header row
        leaderboardTable.add(new Label("Rank", skin)).padRight(20);
        leaderboardTable.add(new Label("Time", skin)).padRight(20); // Display time instead of player name
        leaderboardTable.add(new Label("Board Size", skin)).padRight(20);
        leaderboardTable.add(new Label("Score", skin)).row();

        // Retrieve results and sort by score
        ArrayList<Result> results = GameManager.INSTANCE.getResults();
        results.sort((r1, r2) -> Integer.compare(r2.calcScore(), r1.calcScore())); // Sort by score in descending order

        // Add leaderboard entries dynamically
        int rank = 1;
        for (Result result : results) {
            leaderboardTable.add(new Label(String.valueOf(rank++), skin)).padRight(20);
            leaderboardTable.add(new Label(String.valueOf(result.time), skin)).padRight(20);
            leaderboardTable.add(new Label(String.valueOf(result.boardSize), skin)).padRight(20);
            leaderboardTable.add(new Label(String.valueOf(result.calcScore()), skin)).padBottom(10).row();
        }

        // Create a ScrollPane to make the leaderboard scrollable
        ScrollPane scrollPane = new ScrollPane(leaderboardTable);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setOverscroll(false, false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setTouchable(Touchable.enabled);
        scrollPane.setScrollingDisabled(true, false);

        // Create a container table to hold the ScrollPane
        Table container = new Table();
        container.setBackground(backgroundDrawable);
        container.add(scrollPane).expand().fill();

        return container;
    }





}

