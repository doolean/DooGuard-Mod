package com.doolean.dooguard.client.gui;

import java.util.ArrayList;
import java.util.List;

public class FlagUtils {
    public static void addFlags(List<Flag> flags) {
        flags.add(new Flag("Protection-Related", "build", "state", "Block placement and mining"));
        flags.add(new Flag("Protection-Related", "interact", "state", "Use of blocks/entities (doors, levers, etc.)"));
        flags.add(new Flag("Protection-Related", "block-break", "state", "Whether blocks can be broken"));
        flags.add(new Flag("Protection-Related", "block-place", "state", "Whether blocks can be placed"));
        flags.add(new Flag("Protection-Related", "use", "state", "Whether doors, levers, etc. can be used"));
        flags.add(new Flag("Protection-Related", "damage-animals", "state", "Whether players can harm friendly animals"));
        flags.add(new Flag("Protection-Related", "chest-access", "state", "Whether inventories can be accessed"));
        flags.add(new Flag("Protection-Related", "ride", "state", "Whether vehicles can be mounted"));
        flags.add(new Flag("Protection-Related", "pvp", "state", "Whether PvP combat is allowed"));
        flags.add(new Flag("Protection-Related", "sleep", "state", "Whether players can sleep in beds"));
        flags.add(new Flag("Protection-Related", "respawn-anchors", "state", "Whether respawn anchors can be activated"));
        flags.add(new Flag("Protection-Related", "tnt", "state", "Whether TNT can cause damage or detonate"));
        flags.add(new Flag("Protection-Related", "vehicle-place", "state", "Whether vehicles (boats, minecarts) can be placed"));
        flags.add(new Flag("Protection-Related", "vehicle-destroy", "state", "Whether vehicles can be destroyed"));
        flags.add(new Flag("Protection-Related", "lighter", "state", "Whether flint and steel or fire charge can be used"));
        flags.add(new Flag("Protection-Related", "block-trampling", "state", "Whether farmland and eggs can be trampled"));
        flags.add(new Flag("Protection-Related", "frosted-ice-form", "state", "Whether players with Frost Walker form ice"));
        flags.add(new Flag("Protection-Related", "item-frame-rotation", "state", "Whether items can rotate in item frames"));
        flags.add(new Flag("Protection-Related", "firework-damage", "state", "Whether fireworks can deal damage"));
        flags.add(new Flag("Protection-Related", "use-anvil", "state", "Whether anvils can be used"));
        flags.add(new Flag("Protection-Related", "use-dripleaf", "state", "Whether dripleaf can be used"));

        flags.add(new Flag("Mobs, Fire, and Explosions", "creeper-explosion", "state", "Whether creeper explosions cause damage"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "enderdragon-block-damage", "state", "Whether Enderdragons can destroy blocks"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "ghast-fireball", "state", "Whether ghast fireballs can damage"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "other-explosion", "state", "Whether other explosions can do damage"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "fire-spread", "state", "Whether fire can spread"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "enderman-grief", "state", "Whether Endermen can break/place blocks"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "snowman-trails", "state", "Whether snow golems leave snow trails"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "ravager-grief", "state", "Whether ravagers break blocks"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "mob-damage", "state", "Whether mobs can hurt players"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "mob-spawning", "state", "Whether mobs can spawn"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "deny-spawn", "set of strings", "List of entity types that cannot spawn"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "entity-painting-destroy", "state", "Whether non-player entities can destroy paintings"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "entity-item-frame-destroy", "state", "Whether non-player entities can destroy item frames"));
        flags.add(new Flag("Mobs, Fire, and Explosions", "wither-damage", "state", "Whether withers can deal damage"));

        flags.add(new Flag("Natural Events", "lava-fire", "state", "Whether lava can start fires"));
        flags.add(new Flag("Natural Events", "lightning", "state", "Whether lightning can strike"));
        flags.add(new Flag("Natural Events", "water-flow", "state", "Whether water can flow"));
        flags.add(new Flag("Natural Events", "lava-flow", "state", "Whether lava can flow"));
        flags.add(new Flag("Natural Events", "snow-fall", "state", "Whether snow will form"));
        flags.add(new Flag("Natural Events", "snow-melt", "state", "Whether snow will melt"));
        flags.add(new Flag("Natural Events", "ice-form", "state", "Whether ice will form"));
        flags.add(new Flag("Natural Events", "ice-melt", "state", "Whether ice will melt"));
        flags.add(new Flag("Natural Events", "frosted-ice-melt", "state", "Whether frosted ice will melt"));
        flags.add(new Flag("Natural Events", "mushroom-growth", "state", "Whether mushrooms will grow"));
        flags.add(new Flag("Natural Events", "leaf-decay", "state", "Whether leaves will decay"));
        flags.add(new Flag("Natural Events", "grass-growth", "state", "Whether grass will grow"));
        flags.add(new Flag("Natural Events", "mycelium-spread", "state", "Whether mycelium will spread"));
        flags.add(new Flag("Natural Events", "vine-growth", "state", "Whether vines will grow"));
        flags.add(new Flag("Natural Events", "rock-growth", "state", "Whether rocks (dripstone, etc.) will grow"));
        flags.add(new Flag("Natural Events", "sculk-growth", "state", "Whether sculk will grow"));
        flags.add(new Flag("Natural Events", "crop-growth", "state", "Whether crops (wheat, potatoes, etc.) will grow"));
        flags.add(new Flag("Natural Events", "soil-dry", "state", "Whether soil will dry"));
        flags.add(new Flag("Natural Events", "coral-fade", "state", "Whether coral fades when out of water"));
        flags.add(new Flag("Natural Events", "copper-fade", "state", "Whether copper fades"));

        flags.add(new Flag("Movement", "entry", "state", "Whether players can enter the region"));
        flags.add(new Flag("Movement", "exit", "state", "Whether players can exit the region"));
        flags.add(new Flag("Movement", "exit-via-teleport", "state", "Whether players can exit via teleport"));
        flags.add(new Flag("Movement", "exit-override", "boolean", "Whether to always allow exit"));
        flags.add(new Flag("Movement", "entry-deny-message", "string", "Message for denied entry"));
        flags.add(new Flag("Movement", "exit-deny-message", "string", "Message for denied exit"));
        flags.add(new Flag("Movement", "notify-enter", "boolean", "Notify players upon entry"));
        flags.add(new Flag("Movement", "notify-leave", "boolean", "Notify players upon leave"));
        flags.add(new Flag("Movement", "greeting", "string", "Greeting message on entry"));
        flags.add(new Flag("Movement", "greeting-title", "string", "Greeting title on entry"));
        flags.add(new Flag("Movement", "farewell", "string", "Farewell message on exit"));
        flags.add(new Flag("Movement", "farewell-title", "string", "Farewell title on exit"));
        flags.add(new Flag("Movement", "enderpearl", "state", "Whether enderpearls can be used"));
        flags.add(new Flag("Movement", "chorus-fruit-teleport", "state", "Whether chorus fruit can teleport"));
        flags.add(new Flag("Movement", "teleport", "location", "Teleport location for region"));
        flags.add(new Flag("Movement", "spawn", "location", "Spawn location on player death"));
        flags.add(new Flag("Movement", "teleport-message", "string", "Message for teleporting players"));

        flags.add(new Flag("Map Making", "item-pickup", "state", "Whether items can be picked up"));
        flags.add(new Flag("Map Making", "item-drop", "state", "Whether items can be dropped"));
        flags.add(new Flag("Map Making", "exp-drops", "state", "Whether XP drops are allowed"));
        flags.add(new Flag("Map Making", "deny-message", "string", "Message for denied actions"));
        flags.add(new Flag("Map Making", "invincible", "state", "Whether players are invincible"));
        flags.add(new Flag("Map Making", "fall-damage", "state", "Whether fall damage is applied"));
        flags.add(new Flag("Map Making", "game-mode", "gamemode", "Set game mode for players"));
        flags.add(new Flag("Map Making", "time-lock", "string", "Time of day lock for region"));
        flags.add(new Flag("Map Making", "weather-lock", "weather", "Weather lock for region"));
        flags.add(new Flag("Map Making", "natural-health-regen", "state", "Whether players regen health naturally"));
        flags.add(new Flag("Map Making", "natural-hunger-drain", "state", "Whether hunger drains naturally"));
        flags.add(new Flag("Map Making", "heal-delay", "integer", "Seconds between heals"));
        flags.add(new Flag("Map Making", "heal-amount", "integer", "Amount of health to heal"));
        flags.add(new Flag("Map Making", "heal-min-health", "double", "Min health to heal"));
        flags.add(new Flag("Map Making", "heal-max-health", "double", "Max health to heal"));
        flags.add(new Flag("Map Making", "feed-delay", "integer", "Seconds between feeding"));
        flags.add(new Flag("Map Making", "feed-amount", "integer", "Amount of hunger to feed"));
        flags.add(new Flag("Map Making", "feed-min-hunger", "integer", "Min hunger to feed"));
        flags.add(new Flag("Map Making", "feed-max-hunger", "integer", "Max hunger to feed"));
        flags.add(new Flag("Map Making", "blocked-cmds", "set of strings", "List of blocked commands"));
        flags.add(new Flag("Map Making", "allowed-cmds", "set of strings", "List of allowed commands"));

        flags.add(new Flag("Miscellaneous", "pistons", "state", "Whether pistons can be used"));
        flags.add(new Flag("Miscellaneous", "send-chat", "state", "Whether players can send chat"));
        flags.add(new Flag("Miscellaneous", "receive-chat", "state", "Whether players can receive chat"));
        flags.add(new Flag("Miscellaneous", "potion-splash", "state", "Whether potions can splash"));

    }

    public static List<Flag> filterFlagsByCategory(List<Flag> flags, String category) {
        List<Flag> filteredFlags = new ArrayList<>();

        // Проходим по всем флагам в списке
        for (Flag flag : flags) {
            if (flag.getCategory().equals(category)) {
                filteredFlags.add(flag); // Добавляем флаг в результат, если категория совпадает
            }
        }

        return filteredFlags; // Возвращаем отфильтрованный список флагов
    }
}
