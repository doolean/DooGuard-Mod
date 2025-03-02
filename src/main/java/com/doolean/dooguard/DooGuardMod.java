package com.doolean.dooguard;

import com.doolean.dooguard.proxy.ClientProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DooGuardMod.MODID, name = DooGuardMod.NAME, version = DooGuardMod.VERSION)
public class DooGuardMod {
    public static final String MODID = "dooguard";
    public static final String NAME = "DooGuard";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("DooGuardMod PreInit");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("DooGuardMod Init");
        ClientProxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("DooGuardMod PostInit");
    }
}
