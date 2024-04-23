package pfdyemaker.src.action.redberries.leaf;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.util.Utils;

public class CollectRedberriesLeaf extends Leaf {

    private boolean worldHopped = false;

    private final int redberryBushId = 23628;
    private final int redberryBushId2 = 23629;
    private GameObject redberryBush;

    @Override
    public boolean isValid() {
        return DyeMakerConfig.dyeConfig().getRedberryArea().contains(Players.getLocal()) && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        redberryBush = GameObjects.closest(berryBush -> berryBush.getID() == redberryBushId || berryBush.getID() == redberryBushId2);

        if (!worldHopped && redberryBush == null) {
            Utils.worldHop();
            worldHopped = true;
        } else {
            worldHopped = false;
        }

        if (redberryBush != null) {
            if (redberryBush.interact("Pick-from")) {
                DyeMakerConfig.dyeConfig().setStatus("Picking redberry bush");
                Sleep.sleep(Calculations.random(900, 1200));
                Sleep.sleepUntil(this::isPlayerNotAnimating, 8000, 3000);
                worldHopped = false;
            }
        }
        return 600;
    }

    private boolean isPlayerNotAnimating() {
        return !Players.getLocal().isAnimating();
    }
}
