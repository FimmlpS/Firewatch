package Firewatch.ui;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public class AmbushOrb extends CustomEnergyOrb {
    private static final float ORB_IMG_SCALE;

    public AmbushOrb (String [] orbTexturePaths,String orbVfxPath,float[] layerSpeeds){
        super(orbTexturePaths,orbVfxPath,layerSpeeds);
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        sb.setColor(Color.WHITE);
        int i;
        if (enabled) {
            for(i = 0; i < this.energyLayers.length; ++i) {
                sb.draw(this.energyLayers[i], current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angles[i], 0, 0, 128, 128, false, false);
            }
        } else {
            for(i = 0; i < this.noEnergyLayers.length; ++i) {
                sb.draw(this.noEnergyLayers[i], current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angles[i], 0, 0, 128, 128, false, false);
            }
        }

        sb.draw(this.baseLayer, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
    }

    static {
        ORB_IMG_SCALE = 1.03F * Settings.scale;
    }
}
