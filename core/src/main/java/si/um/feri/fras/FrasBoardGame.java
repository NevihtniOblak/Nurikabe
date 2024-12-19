package si.um.feri.fras;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import si.um.feri.fras.assets.AssetDescriptors;
import si.um.feri.fras.assets.RegionNames;
import si.um.feri.fras.global.GameManager;
import si.um.feri.fras.screen.IntroScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FrasBoardGame extends Game {
    private SpriteBatch batch;

    private AssetManager assetManager;

    private TextureAtlas gameAtlas;

    private TextureAtlas uiAtlas;

    private BitmapFont font;

    private Sound levelCompleteSound;

    private Sound putBlackSound;

    private Sound putDotSound;

    @Override
    public void create() {
        batch = new SpriteBatch();

        //load all assets into AssetManager
        assetManager = new AssetManager();
        assetManager.load(AssetDescriptors.GAME_ATLAS);
        assetManager.load(AssetDescriptors.PRIMARY_FONT);
        assetManager.load(AssetDescriptors.LEVEL_COMPLETE_SOUND);
        assetManager.load(AssetDescriptors.PUT_BLACK_SOUND);
        assetManager.load(AssetDescriptors.PUT_DOT_SOUND);
        assetManager.load(AssetDescriptors.INTRO_FONT);
        assetManager.load(AssetDescriptors.UI_FONT);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.UI_ATLAS);
        assetManager.load(AssetDescriptors.MAIN_MENU_MUSIC);
        assetManager.load(AssetDescriptors.GAME_MUSIC);
        assetManager.load(AssetDescriptors.PUT_BLACK_SOUND_2);
        assetManager.load(AssetDescriptors.PUT_DOT_SOUND_2);

        assetManager.finishLoading();

        //Asset loading demo

        // Initialize variables with loaded assets
        gameAtlas = assetManager.get(AssetDescriptors.GAME_ATLAS);
        uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);
        font = assetManager.get(AssetDescriptors.PRIMARY_FONT);

        //TEXTURES
        /*
        tile1 = gameAtlas.findRegion(RegionNames.TILE_1);
        tile2 = gameAtlas.findRegion(RegionNames.TILE_2);
        tile3 = gameAtlas.findRegion(RegionNames.TILE_3);
        tile4 = gameAtlas.findRegion(RegionNames.TILE_4);
        tile5 = gameAtlas.findRegion(RegionNames.TILE_5);
        tile6 = gameAtlas.findRegion(RegionNames.TILE_6);
        tile7 = gameAtlas.findRegion(RegionNames.TILE_7);
        tile8 = gameAtlas.findRegion(RegionNames.TILE_8);
        tile9 = gameAtlas.findRegion(RegionNames.TILE_9);
        tile10 = gameAtlas.findRegion(RegionNames.TILE_10);
        tileBlack = gameAtlas.findRegion(RegionNames.TILE_BLACK);
        tileMarked = gameAtlas.findRegion(RegionNames.TILE_MARKED);
        tileWhite = gameAtlas.findRegion(RegionNames.TILE_WHITE);
        */


        //SOUNDS
        levelCompleteSound = assetManager.get(AssetDescriptors.LEVEL_COMPLETE_SOUND);
        putBlackSound = assetManager.get(AssetDescriptors.PUT_BLACK_SOUND);
        putDotSound = assetManager.get(AssetDescriptors.PUT_DOT_SOUND);

        //GENERATED SOUNDS





        setScreen(new IntroScreen(this));
    }


    @Override
    public void dispose() {
        GameManager.INSTANCE.saveResults();
        batch.dispose();
        assetManager.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public TextureAtlas getGameAtlas() {
        return gameAtlas;
    }

    public TextureAtlas getUiAtlas() {
        return uiAtlas;
    }
}
