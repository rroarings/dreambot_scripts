package pfdyemaker.src.action.redberries.leaf;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.util.PotionHandler;

public class CollectRedberriesLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();
    private boolean worldHopped = false; // Initialize the flag

    private final int REDBERRY_BUSH_ID = 23628;
    private final int REDBERRY_BUSH_ID2 = 23629;
    private GameObject REDBERRY_BUSH;

    @Override
    public boolean isValid() {
        return config.REDBERRY_AREA.contains(Players.getLocal()) && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        REDBERRY_BUSH = GameObjects.closest(BerryBush -> BerryBush.getID() == REDBERRY_BUSH_ID || BerryBush.getID() == REDBERRY_BUSH_ID2);

        if (!worldHopped && REDBERRY_BUSH == null) {
            PotionHandler.worldHop();
            worldHopped = true;
        } else {
            worldHopped = false;
        }

        if (REDBERRY_BUSH != null) {
            if (REDBERRY_BUSH.interact("Pick-from")) {
                config.setStatus("Picking redberry bush");
                Sleep.sleep(Calculations.random(900, 1200));
                Sleep.sleepUntil(this::isPlayerNotAnimating, 8000, 3000);
                worldHopped = false;
                return 600;
            }
        }

        return 600;
    }

    private boolean isPlayerNotAnimating() {
        return !Players.getLocal().isAnimating();
    }
}
