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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
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

    public static final float INTRO_DURATION= 3.5f;   // duration of the (intro) animation

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

        uiAtlas = game.getUiAtlas();
        font = assetManager.get(AssetDescriptors.INTRO_FONT);
        font.getData().setScale(2.0f);

        TextureAtlas gameAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);
        tileBlack = gameAtlas.findRegion(RegionNames.TILE_BLACK);
        tileWhite = gameAtlas.findRegion(RegionNames.TILE_WHITE);

        //Init the stage
        stage.addActor(createLetterAnimation());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        //play intro animation
        duration += delta;

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
        float centerX = viewport.getWorldWidth() / 2f;
        float centerY = viewport.getWorldHeight() / 2f;
        float firstLetterPositionX = centerX - 200f;
        float letterPositionY_final = centerY;
        float letterSpacing = 10f; // Fixed spacing between letters

        //BlackImage actor
        Image tileBlackImage = new Image(tileBlack);
        tileBlackImage.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        tileBlackImage.setPosition(0, 0);
        tileBlackImage.setVisible(false); // Initially hidden

        //WhiteImage actor
        Image tileWhiteImage = new Image(tileWhite);
        tileWhiteImage.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        tileWhiteImage.setPosition(0, 0);

        // Create Labels for "N", "U", and "RIKABE" letters
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        Label letterN = new Label("N", labelStyle);
        Label letterU = new Label("U", labelStyle);
        Label letterR = new Label("R", labelStyle);

        float letterN_positionX_initial = 100f;
        float letterN_positionY_initial = viewport.getWorldHeight() + 100f;
        float letterU_positionX_initial = 200f;
        float letterU_positionY_initial = viewport.getWorldHeight() + 100f;
        float letterR_positionX_initial = 300f;
        float letterR_positionY_initial = viewport.getWorldHeight() + 100f;

        float letterN_positionX_final = firstLetterPositionX;
        float letterU_positionX_final = firstLetterPositionX + letterN.getWidth() + letterSpacing;
        float letterR_positionX_final = firstLetterPositionX + letterN.getWidth() + letterU.getWidth() + 2 * letterSpacing;
        letterPositionY_final = letterPositionY_final + letterN.getHeight();

        Container<Label> letterN_container = new Container<>(letterN);
        letterN_container.setTransform(true); // Enable scaling and rotation
        letterN_container.setPosition(letterN_positionX_initial, letterN_positionY_initial);
        letterN_container.setScale(3f); // Initial scale

        Container<Label> letterU_container = new Container<>(letterU);
        letterU_container.setTransform(true); // Enable scaling and rotation
        letterU_container.setPosition(letterU_positionX_initial, letterU_positionY_initial);
        letterU_container.setScale(3f); // Initial scale

        Container<Label> letterR_container = new Container<>(letterR);
        letterR_container.setTransform(true); // Enable scaling and rotation
        letterR_container.setPosition(letterR_positionX_initial, letterR_positionY_initial);
        letterR_container.setScale(3f); // Initial scale

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
        remainingLetters.setPosition( letterR_positionX_final + letterR.getWidth()/2f + letterSpacing, letterPositionY_final - letterN.getHeight()/2f);
        remainingLetters.setVisible(false);


        letterN_container.addAction(Actions.sequence(
            Actions.parallel(
                Actions.moveTo(letterN_positionX_final, letterPositionY_final, 0.7f, Interpolation.smooth), // Move to final position
                Actions.scaleTo(1f, 1f, 0.7f, Interpolation.smooth) // Shrink to final size
            )
        ));

        letterU_container.addAction(Actions.sequence(
            Actions.delay(0.5f),
            Actions.parallel(
                Actions.moveTo(letterU_positionX_final, letterPositionY_final, 0.7f, Interpolation.smooth), // Move to final position
                Actions.scaleTo(1f, 1f, 0.7f, Interpolation.smooth) // Shrink to final size
            )
        ));

        letterR_container.addAction(Actions.sequence(
            Actions.delay(1f),
            Actions.parallel(
                Actions.moveTo(letterR_positionX_final, letterPositionY_final, 0.7f, Interpolation.smooth), // Move to final position
                Actions.scaleTo(1f, 1f, 0.7f, Interpolation.smooth) // Shrink to final size
            )
        ));

        remainingLetters.addAction(Actions.sequence(
            Actions.delay(2f),
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
        animationGroup.addActor(letterN_container);;
        animationGroup.addActor(letterU_container);
        animationGroup.addActor(letterR_container);
        animationGroup.addActor(remainingLetters);

        // Add fade-out action to lower opacity
        animationGroup.addAction(Actions.sequence(
            Actions.delay(3.5f), // Delay before starting the fade-out (adjust based on your animation timing)
            Actions.fadeOut(1f) // Gradually lower opacity over 1 second
        ));

        return animationGroup;
    }




}
