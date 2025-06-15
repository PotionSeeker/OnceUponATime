package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import codyhuh.onceuponatime.common.entities.Hydra;
import codyhuh.onceuponatime.common.entities.Unicorn;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, OnceUponATime.MOD_ID);

    public static final RegistryObject<EntityType<Hippogryph>> HIPPOGRYPH = ENTITIES.register("hippogryph", () -> EntityType.Builder.of(Hippogryph::new, MobCategory.CREATURE).sized(1.25F, 1.6F).build("hippogryph"));
    public static final RegistryObject<EntityType<Unicorn>> UNICORN = ENTITIES.register("unicorn", () -> EntityType.Builder.of(Unicorn::new, MobCategory.CREATURE).sized(0.75F, 1.25F).build("unicorn"));
    public static final RegistryObject<EntityType<Hydra>> HYDRA = ENTITIES.register("hydra", () -> EntityType.Builder.of(Hydra::new, MobCategory.MONSTER).sized(1.2F, 1.0F).build("hydra"));
}
