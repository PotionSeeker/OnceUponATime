package codyhuh.onceuponatime.client;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;

public class ClientShaders {

    public static ShaderInstance unicornHorn;
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_UNICORN_HORN = new RenderStateShard.ShaderStateShard(() -> unicornHorn);

}
