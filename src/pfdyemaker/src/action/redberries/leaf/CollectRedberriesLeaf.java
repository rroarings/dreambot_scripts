package pfdyemaker.src.action.redberries.leaf;

import org.dreambot.api.Client;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfdyemaker.src.data.DyeMakerConfig;

public class CollectRedberriesLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    private final int REDBERRY_BUSH_ID = 23628;
    private GameObject REDBERRY_BUSH;
    int distanceThreshold = 8;

    @Override
    public boolean isValid() {
        boolean isNearBush = Players.getLocal().distance(REDBERRY_BUSH) <= distanceThreshold;
        return config.REDBERRY_AREA.contains(Players.getLocal()) && !Inventory.isFull() && isNearBush;
    }

    @Override
    public int onLoop() {
        World worldToHop = Worlds.getRandomWorld(world -> world != null && world.isF2P() && world.isNormal() && world.getMinimumLevel() <= Players.getLocal().getLevel());
        REDBERRY_BUSH = GameObjects.closest(BerryBush -> BerryBush.getID() == REDBERRY_BUSH_ID && BerryBush.distance() <= distanceThreshold);

        if (REDBERRY_BUSH == null) {
            config.setStatus("Hopping worlds");
            WorldHopper.hopWorld(worldToHop);
            Sleep.sleepUntil(Client::isLoggedIn, 8000, 100);
        }

        if (REDBERRY_BUSH != null) {
            if (REDBERRY_BUSH.interact("Pick-from")) {
                config.setStatus("Picking redberry bush");
                Sleep.sleepUntil(() -> !Players.getLocal().isAnimating(), 5000, 100);
            }
        }
        return 800;
    }

}
