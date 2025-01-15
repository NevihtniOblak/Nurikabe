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
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.fras.FrasBoardGame;
import si.um.feri.fras.assets.AssetDescriptors;
import si.um.feri.fras.assets.RegionNames;
import si.um.feri.fras.config.GameConfig;
import si.um.feri.fras.global.ColorTheme;
import si.um.feri.fras.global.GameManager;

public class SettingsScreen extends ScreenAdapter {

    private final FrasBoardGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    private boolean musicEnabled;

    private boolean soundEffectsEnabled;

    private BitmapFont titleFont;

    private BitmapFont font;

    public SettingsScreen(FrasBoardGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        musicEnabled = GameManager.INSTANCE.isMusicEnabled();
        soundEffectsEnabled = GameManager.INSTANCE.areSoundEffectsEnabled();
        titleFont = assetManager.get(AssetDescriptors.MENU_FONT);
        font = assetManager.get(AssetDescriptors.PRIMARY_FONT);

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
        table.setFillParent(true);
        table.top();


        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.SECONDARY_BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        BitmapFont fontText = new BitmapFont(font.getData().getFontFile(), font.getRegion(), false);
        fontText.getData().setScale(0.8f);
        Label.LabelStyle textLabelStyle = new Label.LabelStyle();
        textLabelStyle.font = fontText;
        textLabelStyle.fontColor = new Color(120f / 255f, 90f / 255f, 60f / 255f, 1f);

        // Grid Size Label
        Label gridSizeLabel = new Label("Grid Size:", textLabelStyle);
        // Spinner for grid size (up/down arrows)
        // Spinner for grid size (left/right arrows)
        final int minGridSize = 5;
        final int maxGridSize = 10;
        final Label gridSizeValueLabel = new Label(String.valueOf(GameManager.INSTANCE.getGridSize()), uiSkin);
        TextButton leftButton = new TextButton("<", uiSkin);  // Left arrow
        TextButton rightButton = new TextButton(">", uiSkin); // Right arrow


        // Up button listener
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int currentGridSize = Integer.parseInt(gridSizeValueLabel.getText().toString());
                if (currentGridSize < maxGridSize) {
                    gridSizeValueLabel.setText(String.valueOf(currentGridSize + 1));
                }
            }
        });

        // Down button listener
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int currentGridSize = Integer.parseInt(gridSizeValueLabel.getText().toString());
                if (currentGridSize > minGridSize) {
                    gridSizeValueLabel.setText(String.valueOf(currentGridSize - 1));
                }
            }
        });

        //Color theme label
        Label colorThemeLabel = new Label("Color theme:", textLabelStyle);
        SelectBox<String> colorThemeSelectBox = new SelectBox<>(uiSkin);
        colorThemeSelectBox.setItems("BASIC", "AQUA", "MAGMA");
        colorThemeSelectBox.setSelected(GameManager.INSTANCE.getColorTheme().name());


        Label musicLabel = new Label("Music:", textLabelStyle);
        CheckBox musicCheckBox = new CheckBox("", uiSkin);
        musicCheckBox.setChecked(musicEnabled);

        Label soundEffectsLabel = new Label("Sound Effects:", textLabelStyle);
        CheckBox soundEffectsCheckBox = new CheckBox("", uiSkin);
        soundEffectsCheckBox.setChecked(soundEffectsEnabled);

// Add listeners to the checkboxes
        musicCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Callback when music checkbox state changes
                onMusicCheckboxChanged(musicCheckBox.isChecked());
            }
        });

        soundEffectsCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Callback when sound effects checkbox state changes
                onSoundEffectsCheckboxChanged(soundEffectsCheckBox.isChecked());
            }
        });

        // Apply Button
        TextButton applyButton = new TextButton("Apply", uiSkin);
        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                int selectedGridSize = Integer.parseInt(gridSizeValueLabel.getText().toString());
                String selectedColorTheme = colorThemeSelectBox.getSelected();
                ColorTheme colorTheme = ColorTheme.fromString(selectedColorTheme);

                // Use GameManager to save the grid size and color theme
                GameManager.INSTANCE.setGridSize(selectedGridSize);
                GameManager.INSTANCE.setColorTheme(colorTheme);
                GameManager.INSTANCE.setMusicEnabled(musicEnabled);
                GameManager.INSTANCE.setSoundEffectsEnabled(soundEffectsEnabled);
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


        BitmapFont fontTitle = new BitmapFont(titleFont.getData().getFontFile(), titleFont.getRegion(), false);
        fontTitle.getData().setScale(1.5f);
        Label.LabelStyle titleLabelStyle = new Label.LabelStyle();
        titleLabelStyle.font = fontTitle;
        titleLabelStyle.fontColor = new Color(105f / 255f, 80f / 255f, 50f / 255f, 1f);
        Label settingsLabel = new Label("Settings", titleLabelStyle);
        table.add(settingsLabel).padTop(20).padBottom(70).row();



        // Table layout for the spinner
        Table spinnerTable = new Table();
        spinnerTable.add(leftButton).padRight(10);
        spinnerTable.add(gridSizeValueLabel).padRight(10);
        spinnerTable.add(rightButton);

        Table buttonsTable = new Table();
        buttonsTable.add(applyButton).padRight(10);
        buttonsTable.add(backButton);
        buttonsTable.center().bottom().padBottom(40);

        // Content Table
        Table contentTable = new Table(uiSkin);
        contentTable.center();
        contentTable.add(gridSizeLabel).padBottom(20);
        contentTable.add(spinnerTable).padBottom(20).row();

        contentTable.add(colorThemeLabel).padBottom(20);
        contentTable.add(colorThemeSelectBox).padBottom(20).row();

        contentTable.add(musicLabel).padBottom(20);
        contentTable.add(musicCheckBox).padBottom(20).row();

        contentTable.add(soundEffectsLabel).padBottom(20);
        contentTable.add(soundEffectsCheckBox).padBottom(20).row();

        // Make the checkboxes for music and sound effects bigger
        musicCheckBox.getImage().setScale(2f);
        soundEffectsCheckBox.getImage().setScale(2f);

        table.add(contentTable).row();
        table.add(buttonsTable).expand().fillY().row(); // Buttons table stretches to take all vertical space, aligns to bottom
        table.pack();

        return table;
    }

    private void onMusicCheckboxChanged(boolean isChecked) {
        if (isChecked) {
            musicEnabled = true;
        } else {
            musicEnabled = false;
        }
    }

    private void onSoundEffectsCheckboxChanged(boolean isChecked) {
        if (isChecked) {
            soundEffectsEnabled = true;
        } else {
            soundEffectsEnabled = false;
        }
    }

}
