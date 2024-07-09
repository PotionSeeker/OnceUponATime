package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.items.HippogryphArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OnceUponATime.MOD_ID);

    public static final RegistryObject<Item> HIPPOGRYPH_SPAWN_EGG = ITEMS.register("hippogryph_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.HIPPOGRYPH, 0x643c23, 0xdad7cf, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_HIPPOGRYPH_ARMOR = ITEMS.register("golden_hippogryph_armor", () -> new HippogryphArmorItem(7, "gold", new Item.Properties().stacksTo(1)));
}
