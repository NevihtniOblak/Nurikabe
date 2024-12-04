package si.um.feri.fras.lwjgl3;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE = false;
    private static final String RAW_ASSETS_PATH = "lwjgl3/assets_raw";
    private static final String ASSETS_PATH = "assets/atlases";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;

        TexturePacker.process(settings,
            RAW_ASSETS_PATH + "",    // the directory where the images are
            ASSETS_PATH + "/nurikabeAtlas",   // the directory where the pack file will be written
            "nurikabe"   // the name of the pack file / atlas name
        );
    }
}
