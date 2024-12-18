package si.um.feri.fras.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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

public class SettingsScreen extends ScreenAdapter {

    private final FrasBoardGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    private ButtonGroup<CheckBox> checkBoxGroup;
    private CheckBox checkBoxX;
    private CheckBox checkBoxO;

    public SettingsScreen(FrasBoardGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

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

        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BAMBOO_BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Grid Size Label
        Label gridSizeLabel = new Label("Grid Size:", uiSkin);

        // Spinner for grid size (up/down arrows)
        final int minGridSize = 5;
        final int maxGridSize = 10;
        final Label gridSizeValueLabel = new Label(String.valueOf(GameManager.INSTANCE.getGridSize()), uiSkin);
        TextButton upButton = new TextButton("▲", uiSkin);
        TextButton downButton = new TextButton("▼", uiSkin);

        // Up button listener
        upButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int currentGridSize = Integer.parseInt(gridSizeValueLabel.getText().toString());
                if (currentGridSize < maxGridSize) {
                    gridSizeValueLabel.setText(String.valueOf(currentGridSize + 1));
                }
            }
        });

        // Down button listener
        downButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int currentGridSize = Integer.parseInt(gridSizeValueLabel.getText().toString());
                if (currentGridSize > minGridSize) {
                    gridSizeValueLabel.setText(String.valueOf(currentGridSize - 1));
                }
            }
        });

        // Apply Button
        TextButton applyButton = new TextButton("Apply", uiSkin);
        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int selectedGridSize = Integer.parseInt(gridSizeValueLabel.getText().toString());
                Preferences preferences = Gdx.app.getPreferences("GameSettings");
                preferences.putInteger("gridSize", selectedGridSize);
                preferences.flush();

                // You can call GameManager here if needed to apply the changes in real-time.
            }
        });

        // Back Button
        TextButton backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        // Table layout for the spinner
        Table spinnerTable = new Table();
        spinnerTable.add(downButton).padRight(10);
        spinnerTable.add(gridSizeValueLabel).padRight(10);
        spinnerTable.add(upButton);

        // Content Table
        Table contentTable = new Table(uiSkin);

        TextureRegion menuBackground = gameplayAtlas.findRegion(RegionNames.BAMBOO_BACKGROUND);
        contentTable.setBackground(new TextureRegionDrawable(menuBackground));

        contentTable.add(new Label("Settings", uiSkin)).padBottom(50).colspan(2).row();
        contentTable.add(gridSizeLabel).padBottom(20).colspan(2).row();
        contentTable.add(spinnerTable).padBottom(50).colspan(2).row();
        contentTable.add(applyButton).width(100).padBottom(20).colspan(2).row();
        contentTable.add(backButton).width(100).colspan(2);

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

}
