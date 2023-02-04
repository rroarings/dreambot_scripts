package pfenchanter.src.util;

import pfenchanter.src.activity.enchanting.CastSpellLeaf;
import pfenchanter.src.activity.enchanting.EnchantItemBranch;
import pfenchanter.src.activity.banking.HandleBankLeaf;
import pfenchanter.src.framework.Branch;

import java.util.function.Supplier;

public enum ActivityBranch {

    ENCHANT_ITEM(() -> {
        Branch activityBranch = new EnchantItemBranch();
        activityBranch.addLeafs(
                new CastSpellLeaf(),
                new HandleBankLeaf()
        );
        return activityBranch;
    });

    private final Supplier<Branch> activityBranchSupplier;

    ActivityBranch(Supplier<Branch> questBranchSupplier) {
        this.activityBranchSupplier = questBranchSupplier;
    }

    public Branch getActivityBranch() {
        return activityBranchSupplier.get();
    }
}
