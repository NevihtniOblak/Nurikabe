package si.um.feri.fras.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.fras.FrasBoardGame;
import si.um.feri.fras.assets.AssetDescriptors;
import si.um.feri.fras.assets.RegionNames;
import si.um.feri.fras.config.GameConfig;
import com.badlogic.gdx.utils.ScreenUtils;


public class IntroScreen extends ScreenAdapter {

    public static final float INTRO_DURATION= 4f;   // duration of the (intro) animation

    private final FrasBoardGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private TextureAtlas uiAtlas;

    private TextureRegion tileBlack;

    private TextureRegion tileWhite;

    private BitmapFont font;

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

        uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);
        //TODO Get better font
        font = assetManager.get(AssetDescriptors.PRIMARY_FONT);
        font.getData().setScale(2.0f);

        TextureAtlas gameAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);
        tileBlack = gameAtlas.findRegion(RegionNames.TILE_BLACK);
        tileWhite = gameAtlas.findRegion(RegionNames.TILE_WHITE);

        stage.addActor(createLetterAnimation());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

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
        float centerX = viewport.getWorldWidth() / 2f;
        float centerY = viewport.getWorldHeight() / 2f;

        float firstLetterPositionX = centerX - 150f;
        float letterPositionY_final = centerY;
        float letterSpacing = 10f; // Fixed spacing between letters

        // Create the background actors
        Image tileBlackImage = new Image(tileBlack);
        tileBlackImage.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        tileBlackImage.setPosition(0, 0);
        tileBlackImage.setVisible(false); // Initially hidden

        Image tileWhiteImage = new Image(tileWhite);
        tileWhiteImage.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        tileWhiteImage.setPosition(0, 0);

        // Create Labels for "N", "U", and "RIKABE" letters
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        Label letterN = new Label("N", labelStyle);
        Label letterU = new Label("U", labelStyle);
        Label letterR = new Label("R", labelStyle);

        float letterN_positionX_initial = 100f;
        float letterN_positionY_initial = viewport.getWorldHeight() + letterN.getHeight();
        float letterU_positionX_initial = 200f;
        float letterU_positionY_initial = viewport.getWorldHeight() + letterU.getHeight();
        float letterR_positionX_initial = 300f;
        float letterR_positionY_initial = viewport.getWorldHeight() + letterR.getHeight();

        float letterN_positionX_final = firstLetterPositionX;
        float letterU_positionX_final = firstLetterPositionX + letterN.getWidth() + letterSpacing;
        float letterR_positionX_final = firstLetterPositionX + letterN.getWidth() + letterU.getWidth() + 2 * letterSpacing;

        // Position "N" and "U" in the center
        letterN.setPosition(letterN_positionX_initial, letterN_positionY_initial);
        letterN.setScale(10f);
        letterU.setPosition(letterU_positionX_initial, letterU_positionY_initial);
        letterU.setScale(3f);
        letterR.setPosition(letterR_positionX_initial, letterR_positionY_initial);
        letterR.setScale(3f);

        Group remainingLetters = new Group();
        String remainingText = "IKABE";
        float currentX = 0;
        for (int i = 0; i < remainingText.length(); i++) {
            Label letter = new Label(String.valueOf(remainingText.charAt(i)), labelStyle);
            letter.setPosition(currentX, 0);
            currentX += letter.getWidth() + letterSpacing;
            remainingLetters.addActor(letter);
        }

        // Adjust the position of the remaining letters group
        remainingLetters.setPosition( letterR_positionX_final + letterR.getWidth() + letterSpacing, letterPositionY_final);
        remainingLetters.setVisible(false);

        // Create animation sequences for "N", "U", and "RIKABE"
        letterN.addAction(Actions.sequence(
            Actions.parallel(
                Actions.moveTo(letterN_positionX_final, letterPositionY_final, 1f, Interpolation.smooth), // Move to final position
                Actions.scaleTo(1f, 1f, 1f, Interpolation.smooth) // Shrink to final size
            )
        ));

        letterU.addAction(Actions.sequence(
            Actions.delay(0.5f),
            Actions.parallel(
                Actions.moveTo(letterU_positionX_final, letterPositionY_final, 1f, Interpolation.smooth), // Move to final position
                Actions.scaleTo(1f, 1f, 1f, Interpolation.smooth) // Shrink to final size
            )
        ));

        letterR.addAction(Actions.sequence(
            Actions.delay(1f),
            Actions.parallel(
                Actions.moveTo(letterR_positionX_final, letterPositionY_final, 1f, Interpolation.smooth), // Move to final position
                Actions.scaleTo(1f, 1f, 1f, Interpolation.smooth) // Shrink to final size
            )
        ));

        remainingLetters.addAction(Actions.sequence(
            Actions.delay(2.5f),
            Actions.run(() -> {
                for (Actor letter : remainingLetters.getChildren()) {
                    ((Label) letter).setStyle(new Label.LabelStyle(font, Color.WHITE));
                }
                tileWhiteImage.setVisible(false); // Hide the white tile
                tileBlackImage.setVisible(true);  // Show the black tile
                letterN.setStyle(new Label.LabelStyle(font, Color.WHITE));
                letterU.setStyle(new Label.LabelStyle(font, Color.WHITE));
                letterR.setStyle(new Label.LabelStyle(font, Color.WHITE));
            }),
            Actions.show(),
            Actions.delay(1f)
        ));

        Group animationGroup = new Group();
        animationGroup.addActor(tileWhiteImage); // Add the tileWhite image first
        animationGroup.addActor(tileBlackImage); // Add the tileBlack image second
        animationGroup.addActor(letterN);
        animationGroup.addActor(letterU);
        animationGroup.addActor(letterR);
        animationGroup.addActor(remainingLetters);

        return animationGroup;
    }


}
