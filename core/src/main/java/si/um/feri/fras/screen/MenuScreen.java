package si.um.feri.fras.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.fras.FrasBoardGame;
import si.um.feri.fras.assets.AssetDescriptors;
import si.um.feri.fras.assets.RegionNames;
import si.um.feri.fras.config.GameConfig;
import si.um.feri.fras.global.GameManager;

public class MenuScreen extends ScreenAdapter {

    private final FrasBoardGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    private Skin skin;
    private TextureAtlas uiAtlas;

    private TextureAtlas gameplayAtlas;

    private Music menuMusic;

    private BitmapFont font;

    public MenuScreen(FrasBoardGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        font = assetManager.get(AssetDescriptors.MENU_FONT);
        font.getData().setScale(2.5f);

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);
        menuMusic = assetManager.get(AssetDescriptors.MAIN_MENU_MUSIC);

        if(GameManager.INSTANCE.isMusicEnabled()) {
            Music gameMusic = assetManager.get(AssetDescriptors.GAME_MUSIC);
            if(gameMusic.isPlaying()) {
                gameMusic.stop();
            }
            menuMusic.setLooping(true);
            menuMusic.play();
        }

        stage.addActor(createUi());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);

        if(!GameManager.INSTANCE.isMusicEnabled()) {
            menuMusic.stop();
        }
        else{
            if(!menuMusic.isPlaying()) {
                menuMusic.play();
            }
        }

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
        Table table = new Table();
        table.defaults().pad(20);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.CLASSIC_BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        /*TextButton introButton = new TextButton("Intro screen", skin);
        introButton.addListener(new ClickListener() {
             @Override
             public void clicked(InputEvent event, float x, float y) {
                 game.setScreen(new IntroScreen(game));
             }
         });

         */
        Color customColor = new Color(130f / 255f, 100f / 255f, 65f / 255f, 1f); // Slightly darker version
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, customColor);
        Label nurikabeLabel = new Label("NURIKABE", labelStyle);
        table.top().add(nurikabeLabel).padTop(30).center().row();



        TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });


        TextButton leaderboardButton = new TextButton("Leaderboard", skin);
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardScreen(game));
            }
        });


        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });



        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table buttonTable = new Table();
        buttonTable.defaults().padLeft(30).padRight(30);

        //TextureRegion menuBackgroundRegion = gameplayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        //buttonTable.setBackground(new TextureRegionDrawable(menuBackgroundRegion));

        BitmapFont customFont = skin.getFont("font");
        customFont.getData().setScale(1.2f);

        float buttonWidth = 150f;
        float buttonHeight = 50f;
        //buttonTable.add(introButton).padBottom(15).expandX().fillX().row();
        buttonTable.add(playButton).padBottom(15).expandX().fill().size(buttonWidth, buttonHeight).row();
        buttonTable.add(leaderboardButton).padBottom(15).fillX().size(buttonWidth, buttonHeight).row();
        buttonTable.add(settingsButton).padBottom(15).fillX().size(buttonWidth, buttonHeight).row();
        buttonTable.add(quitButton).size(buttonWidth, buttonHeight).fillX();

        buttonTable.center();


        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

}
