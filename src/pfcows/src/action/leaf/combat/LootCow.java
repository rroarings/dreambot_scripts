package pfcows.src.action.leaf.combat;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;
import pfcows.src.data.CowsConfig;
import pfcows.src.data.Location;
import pfcows.src.util.PricedItem;

import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class LootCow extends Leaf {

    private final Timer positionCheckTimer = new Timer();
    private Tile lastTile;

    @Override
    public boolean isValid() {
        GroundItem cowhide = GroundItems.closest(item -> item.getName().equals("Cowhide"));

        return cowhide != null && !Players.getLocal().isMoving()
                && Location.EAST_COWS.getArea().contains(cowhide.getTile());
    }


    @Override
    public int onLoop() {
        GameObject cowGate = GameObjects.closest(gate -> gate.hasAction("Open") && (gate.getID() == 1559 || gate.getID() == 1567));

        if (cowGate != null && cowGate.interact("Open")) {
            CowsConfig.getCowsConfig().setStatus("Opening gate");
        }

        GroundItem cowhide = GroundItems.closest(hide -> hide.getName().equals("Cowhide"));

        if (!Inventory.isFull()) {
            if (!Players.getLocal().isInCombat()) {
                if (cowhide != null && cowhide.interact("Take")) {
                    int invNum = Inventory.contains("Cowhide") ? Inventory.get("Cowhide").getAmount() : 0;
                    lastTile = cowhide.getTile();
                    CowsConfig.getCowsConfig().setStatus("Looting cowhide");
                    Sleep.sleepUntil(() -> Inventory.contains("Cowhide") && Inventory.get("Cowhide").getAmount() > invNum, 1000, 100);
                    CowsConfig.getCowsConfig().getPricedItem().update();
                    return 100;
                }
            }
        }

        // Check if the player is inside the East cows area before attempting to walk
        if (Location.EAST_COWS.getArea().contains(Players.getLocal())) {
           // Logger.log("timer: " + positionCheckTimer.elapsed());
            // Check for potential stuck scenario every 10 seconds
            if (positionCheckTimer.elapsed() > 3000) {

                if (lastTile == null) {
                    lastTile = Players.getLocal().getTile();
                } else {
                    Tile currentTile = Players.getLocal().getTile();
                    if (currentTile.equals(lastTile)) {
                        // Perform random walk within the East cows location
                        Tile randomTile = Location.EAST_COWS.getArea().getRandomTile();
                        Walking.walk(randomTile);
                        CowsConfig.getCowsConfig().setStatus("Random area walk");
                    }
                    lastTile = currentTile;
                }
                positionCheckTimer.reset();
               // Logger.log("timer reset");
            }
        }

        return 100;
    }

}
