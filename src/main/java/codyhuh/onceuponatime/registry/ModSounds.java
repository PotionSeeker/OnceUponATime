package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, OnceUponATime.MOD_ID);

    public static final RegistryObject<SoundEvent> UNICORN_SHEEN = createSoundEvent("unicorn.sheen");

    private static RegistryObject<SoundEvent> createSoundEvent(final String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(OnceUponATime.MOD_ID, name)));

    }
}