package si.um.feri.fras.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.fras.FrasBoardGame;
import si.um.feri.fras.assets.AssetDescriptors;
import si.um.feri.fras.config.GameConfig;
import com.badlogic.gdx.utils.ScreenUtils;


public class IntroScreen extends ScreenAdapter {

    public static final float INTRO_DURATION= 3f;   // duration of the (intro) animation

    private final FrasBoardGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private TextureAtlas gameplayAtlas;

    private float duration = 0f;

    private Stage stage;

    public IntroScreen(FrasBoardGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        // load assets
        assetManager.load(AssetDescriptors.UI_FONT);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.UI_ATLAS);
        assetManager.finishLoading();

        gameplayAtlas = assetManager.get(AssetDescriptors.UI_ATLAS  );

        stage.addActor(createLetterAnimation());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(65 / 255f, 159 / 255f, 221 / 255f, 0f);

        duration += delta;

        // go to the MenuScreen after INTRO_DURATION_IN_SEC seconds
        if (duration > INTRO_DURATION) {
            game.setScreen(new MenuScreen(game));
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


    private Actor createLetterAnimation() {
        // Get your main font from the AssetManager
        BitmapFont font = assetManager.get(AssetDescriptors.UI_FONT);

        // Create Labels for "N", "U", and "RIKABE" letters
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        Label letterN = new Label("N", labelStyle);
        Label letterU = new Label("U", labelStyle);

        Group remainingLetters = new Group();
        String remainingText = "RIKABE";
        float spacing = 30; // Spacing between letters
        for (int i = 0; i < remainingText.length(); i++) {
            Label letter = new Label(String.valueOf(remainingText.charAt(i)), labelStyle);
            letter.setPosition(i * (font.getCapHeight() + spacing), 0);
            remainingLetters.addActor(letter);
        }

        // Position "N" and "U" in the center
        float centerX = viewport.getWorldWidth() / 2f;
        float centerY = viewport.getWorldHeight() / 2f;
        letterN.setPosition(centerX - 150, centerY);
        letterU.setPosition(centerX + 50, centerY);

        // Initially, set "RIKABE" letters as invisible
        remainingLetters.setPosition(centerX - 100, centerY - 100); // Adjust as needed
        remainingLetters.setVisible(false);

        // Create animation sequences for "N", "U", and "RIKABE"
        letterN.addAction(Actions.sequence(
            Actions.fadeIn(1f), // Fade in "N"
            Actions.run(() -> {
                // Switch background and text color
                ScreenUtils.clear(Color.BLACK); // Set background to black
                letterN.setColor(Color.WHITE);  // Set text to white
                letterU.setColor(Color.WHITE);
            }),
            Actions.fadeOut(0.5f) // Optionally fade out "N"
        ));

        letterU.addAction(Actions.sequence(
            Actions.fadeIn(1f, Interpolation.smooth),
            Actions.delay(1f), // Wait until the background switches
            Actions.fadeOut(0.5f)
        ));

        remainingLetters.addAction(Actions.sequence(
            Actions.delay(2f), // Wait for "N" and "U" animations to finish
            Actions.run(() -> {
                // Switch colors for the remaining letters
                for (Actor letter : remainingLetters.getChildren()) {
                    ((Label) letter).setStyle(new Label.LabelStyle(font, Color.WHITE));
                }
            }),
            Actions.show(), // Reveal the "RIKABE" group
            Actions.fadeIn(1f) // Fade in "RIKABE"
        ));

        // Add "N", "U", and "RIKABE" to a Group
        Group animationGroup = new Group();
        animationGroup.addActor(letterN);
        animationGroup.addActor(letterU);
        animationGroup.addActor(remainingLetters);

        return animationGroup;
    }

}
