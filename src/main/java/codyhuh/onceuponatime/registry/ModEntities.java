package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.entities.Griffin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, OnceUponATime.MOD_ID);

    public static final RegistryObject<EntityType<Griffin>> GRIFFIN = ENTITIES.register("griffin", () -> EntityType.Builder.of(Griffin::new, MobCategory.CREATURE).sized(1.25F, 1.6F).build("griffin"));
}
