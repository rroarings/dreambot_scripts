package pfitemmixer.src.activity.branch.banking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class BankItemsBranch extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.contains("Sulphurous fertiliser");
    }

}
