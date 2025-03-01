package com.doolean.dooguard.client.input;

import com.doolean.dooguard.client.gui.GuiScreenDooGuard;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;

public class KeybindHandler {
    public static final KeyBinding openGuiKey = new KeyBinding("Open DooGuard", Keyboard.KEY_RSHIFT, "DooGuard");

    public static void register() {
        ClientRegistry.registerKeyBinding(openGuiKey);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (openGuiKey.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenDooGuard());
        }
    }
}
