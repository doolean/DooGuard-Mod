package com.doolean.dooguard.proxy;

import com.doolean.dooguard.client.input.KeybindHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy {
    public static void init() {
        KeybindHandler.register();
        MinecraftForge.EVENT_BUS.register(new KeybindHandler());
    }
}
