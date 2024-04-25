package pfdyemaker.src.util;

import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class Utils {

    public static void worldHop() {
        World cWorld = Worlds.getCurrent();
        World worldToHop = Worlds.getRandomWorld(world -> world != null && world.isF2P() && world.isNormal() && world.getMinimumLevel() <= Players.getLocal().getLevel());
        int delay = Calculations.random(DyeMakerConfig.dyeConfig().getWorldHopDelayMin(), DyeMakerConfig.dyeConfig().getWorldHopDelayMax());
        Sleep.sleepTicks(delay);
        DyeMakerConfig.dyeConfig().setStatus("Hopping worlds");
        WorldHopper.hopWorld(worldToHop);
        Sleep.sleepUntil(() -> Worlds.getCurrent() != cWorld || Client.isLoggedIn(), 8000, 600);
    }
}
