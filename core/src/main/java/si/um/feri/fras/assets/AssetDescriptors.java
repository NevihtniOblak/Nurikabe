package si.um.feri.fras.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

    //ATLASES
    public static final AssetDescriptor<TextureAtlas> GAME_ATLAS =
        new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_ATLAS, TextureAtlas.class);

    //FONTS
    public static final AssetDescriptor<BitmapFont> PRIMARY_FONT =
        new AssetDescriptor<BitmapFont>(AssetPaths.PRIMARY_FONT, BitmapFont.class);


    //SOUNDS
    public static final AssetDescriptor<Sound> LEVEL_COMPLETE_SOUND =
        new AssetDescriptor<Sound>(AssetPaths.LEVEL_COMPLETE_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> PUT_BLACK_SOUND =
        new AssetDescriptor<Sound>(AssetPaths.PUT_BLACK_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> PUT_DOT_SOUND =
        new AssetDescriptor<Sound>(AssetPaths.PUT_DOT_SOUND, Sound.class);

    //UI STUFF
    public static final AssetDescriptor<BitmapFont> UI_FONT =
        new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final AssetDescriptor<Skin> UI_SKIN =
        new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);

    public static final AssetDescriptor<TextureAtlas> UI_ATLAS =
        new AssetDescriptor<TextureAtlas>(AssetPaths.UI_ATLAS, TextureAtlas.class);


    public static final AssetDescriptor<BitmapFont> INTRO_FONT =
        new AssetDescriptor<BitmapFont>(AssetPaths.INTRO_FONT, BitmapFont.class);


    private AssetDescriptors() {
    }
}
