package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OnceUponATime.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ONCE_UPON_A_TIME_TAB = TABS.register("once_upon_a_time_tab", () -> CreativeModeTab.builder()
            .icon(ModItems.GOLDEN_HIPPOGRYPH_ARMOR.get()::getDefaultInstance)
            .title(Component.translatable("itemGroup." + OnceUponATime.MOD_ID))
            .displayItems((itemDisplayParameters, output) -> {
                ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> output.accept(itemRegistryObject.get()));
            })
            .build());
}