package codyhuh.onceuponatime.common.items;

import codyhuh.onceuponatime.OnceUponATime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class HippogryphArmorItem extends Item {
    private final int protection;
    private final ResourceLocation texture;

    public HippogryphArmorItem(int pProtection, String pIdentifier, Item.Properties pProperties) {
        this(pProtection, new ResourceLocation(OnceUponATime.MOD_ID, "textures/entity/hippogryph/armor/armor_" + pIdentifier + ".png"), pProperties);
    }

    public HippogryphArmorItem(int pProtection, ResourceLocation pIdentifier, Item.Properties pProperties) {
        super(pProperties);
        this.protection = pProtection;
        this.texture = pIdentifier;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getProtection() {
        return this.protection;
    }
}
