package si.um.feri.fras.global;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CellActor extends Image {

    private CellState state;

    public CellActor(TextureRegion region) {
        super(region);
        state = CellState.NEUTRAL;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public void setDrawable(TextureRegion region) {
        super.setDrawable(new TextureRegionDrawable(region));
    }

    public boolean isNeutral() {
        return state == CellState.NEUTRAL;
    }

}
