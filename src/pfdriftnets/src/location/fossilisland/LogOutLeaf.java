package pfdriftnets.src.location.fossilisland;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import pfdriftnets.src.data.DriftNetsConfig;
import pfdriftnets.src.framework.Leaf;

public class LogOutLeaf extends Leaf {

    DriftNetsConfig config = DriftNetsConfig.getDriftNetsConfig();

    @Override
    public boolean isValid() {
        final Area fossil_bankchest = new Area(3742, 3806, 3738, 3801);
        return config.getFOSSIL_LOOM().contains(Players.getLocal()) && Inventory.count("Jute fibre") == 1 || fossil_bankchest.contains(Players.getLocal()) && Inventory.count("Jute fibre") == 1;
    }

    @Override
    public int onLoop() {
       if (Inventory.contains("Jute fibre") && Inventory.count("Jute fibre") == 1) {
           DriftNetsConfig.setStatus("Logging out");
           Tabs.logout();
           Logger.log("Out of drift nets, stopping script");
           ScriptManager.getScriptManager().stop();
       }
        return 1000;
    }
}
