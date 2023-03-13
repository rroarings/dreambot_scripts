package pfenchanter.src.activity.enchanting;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import pfenchanter.src.data.EnchanterConfig;
import pfenchanter.src.framework.Leaf;

public class CastSpellLeaf extends Leaf {

    EnchanterConfig ec = EnchanterConfig.getInstance();

    @Override
    public boolean isValid() {
        if (Magic.canCast(ec.getSpellToCast()) && Inventory.contains(item -> item.getName().contains(ec.getItemToEnchant()))) {
               return true;
           }
        return false;
    }

    @Override
    public int onLoop() {
        int r = Calculations.random(3, 5);
        String ein = ec.getItemToEnchant();
        Item ei = Inventory.get(item -> item.getName().contains(ein));
        if (ei == null) return 300;

        if (Magic.canCast(ec.getSpellToCast())) {
               ec.setStatus("Enchanting " + ec.getItemToEnchant());
               Magic.castSpellOn(ec.getSpellToCast(), ei);
               ec.setStatus("Enchant sleep");
               Sleep.sleepTicks(r);
        }

        if (ec.getPricedItem() != null) {
            ec.getPricedItem().update();
        }
        return 900;
    }
}
