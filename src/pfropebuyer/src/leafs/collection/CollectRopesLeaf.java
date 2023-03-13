package pfropebuyer.src.leafs.collection;

import org.dreambot.api.Client;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.Shop;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import pfropebuyer.src.data.RopeBuyerConfig;
import pfropebuyer.src.framework.Leaf;

public class CollectRopesLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 1000
                && !Inventory.isFull()
                && RopeBuyerConfig.NEDS_HOUSE.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC NED = NPCs.closest(npc -> npc.getName().contains("Ned") && npc.isClickable());
        World worldToHop = Worlds.getRandomWorld(world -> world != null && world.isF2P() && world.isNormal() && world.getMinimumLevel() <= Players.getLocal().getLevel());

        if (NED == null) return 600;

        if (NED != null) {
            RopeBuyerConfig.status = "Trading Ned";
            if (NED.interact("Trade")) {
                Sleep.sleepUntil(() -> Shop.isOpen(), 1000);
            }
        }

        if (Shop.isOpen()) {
            if (Shop.contains("Rope")) {
                RopeBuyerConfig.status = "Buying ropes";
                Shop.purchaseFifty("Rope");
                Shop.close();
                RopeBuyerConfig.status = "Hopping worlds";
                WorldHopper.hopWorld(worldToHop);
                Sleep.sleepUntil(Client::isLoggedIn, 5000);
            }
        }
        return 800;
    }
}
