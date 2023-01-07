package pfdyemaker.src.leafs.collecting;

import org.dreambot.api.Client;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;


public class CollectRedberriesLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return config.REDBERRY_AREA.contains(Players.getLocal()) && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        World worldToHop = Worlds.getRandomWorld(world -> world != null && world.isF2P() && world.isNormal() && world.getMinimumLevel() <= Players.getLocal().getLevel());
        GameObject REDBERRY_BUSH = GameObjects.closest(gameObject -> gameObject.getName().contains("Redberry bush") && gameObject.getID() == 23628);

        if (REDBERRY_BUSH == null) {
            DyeMakerConfig.status = "Hopping worlds";
            WorldHopper.hopWorld(worldToHop);
            Sleep.sleepUntil(Client::isLoggedIn, 5000);
        }

        if (REDBERRY_BUSH != null) {
            if (REDBERRY_BUSH.interact("Pick-from")) {
                DyeMakerConfig.status = "Picking redberry bush";
                Sleep.sleepUntil(() -> !Players.getLocal().isAnimating(), 1000);
            }
        }
        return 800;
    }
}
