package codyhuh.onceuponatime.client;

import codyhuh.onceuponatime.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientProxy extends CommonProxy {
    public static List<UUID> blockedEntityRenders = new ArrayList<>();

    public void clientInit() {
        //MinecraftForge.EVENT_BUS.register(new ClientForgeEvents());
    }

    public void blockRenderingEntity(UUID id) {
        blockedEntityRenders.add(id);
    }

    public void releaseRenderingEntity(UUID id) {
        blockedEntityRenders.remove(id);
    }
}
