package io.github.rubiksimplosion.minecrafttas.input;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.savestate.SavestateManager;
import io.github.rubiksimplosion.minecrafttas.script.ScriptManager;
import io.github.rubiksimplosion.minecrafttas.util.PlayerState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TasKeyBindings {
    public static KeyBinding keyTasTest = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minecrafttas.test",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.minecrafttas.tas"));
    public static KeyBinding keyScriptStop = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minecrafttas.script.stop",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "category.minecrafttas.tas"));
    public static KeyBinding keyScriptStart = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minecrafttas.script.start",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_I,
            "category.minecrafttas.tas"));
    public static KeyBinding keyCreateSavestate = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minecrafttas.savestate.create",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            "category.minecrafttas.tas"));
    public static KeyBinding keyLoadSavestate = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minecrafttas.savestate.load",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "category.minecrafttas.tas"));
    public static KeyBinding keyDeleteSavestate = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minecrafttas.savestate.delete",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_APOSTROPHE,
            "category.minecrafttas.tas"));

    public static void registerKeys() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyTasTest.wasPressed()) {
                onKeyTasTestPressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyScriptStart.wasPressed()) {
                onKeyScriptStartPressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyScriptStop.wasPressed()) {
                onKeyScriptStopPressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyCreateSavestate.wasPressed()) {
                onKeyCreateSavestatePressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyLoadSavestate.wasPressed()) {
                onKeyLoadSavestatePressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyDeleteSavestate.wasPressed()) {
                onKeyDeleteSavestatePressed();
            }
        });
    }

    public static void onKeyDeleteSavestatePressed() {
        MinecraftTas.savestateManager.removeSoftSavestate();
    }

    public static void onKeyCreateSavestatePressed() {
        MinecraftTas.savestateManager.addSoftSavestate(new PlayerState(
                MinecraftClient.getInstance().player.getPos(),
                MinecraftClient.getInstance().player.getVelocity(),
                MinecraftClient.getInstance().player.getYaw(),
                MinecraftClient.getInstance().player.getPitch())
        );
    }

    public static void onKeyLoadSavestatePressed() {
        MinecraftTas.savestateManager.loadSoftSavestate();
    }


    public static void onKeyTasTestPressed() {
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(ScriptManager.scriptDirectory.getPath()), false);
    }

    public static void onKeyScriptStopPressed() {
        if (MinecraftTas.scriptManager.executing) {
            MinecraftClient.getInstance().player.sendMessage(new LiteralText("Stopped executing script"), false);
            MinecraftTas.scriptManager.stop();
        }
    }

    public static void onKeyScriptStartPressed() {
        if (!MinecraftTas.scriptManager.executing) {
            MinecraftClient.getInstance().player.sendMessage(new LiteralText("Started executing script"), false);
            MinecraftTas.scriptManager.start();
        }
    }
}
