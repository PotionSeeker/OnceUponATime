package codyhuh.onceuponatime.common.items;

import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;

public class DyeableHippogryphArmorItem extends HippogryphArmorItem implements DyeableLeatherItem {
    public DyeableHippogryphArmorItem(int pProtection, String pIdentifier, Item.Properties pProperties) {
        super(pProtection, pIdentifier, pProperties);
    }

    public DyeableHippogryphArmorItem(int pProtection, net.minecraft.resources.ResourceLocation pIdentifier, Item.Properties pProperties) {
        super(pProtection, pIdentifier, pProperties);
    }
}
