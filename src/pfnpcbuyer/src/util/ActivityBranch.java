package pfnpcbuyer.src.util;

import org.dreambot.api.script.frameworks.treebranch.Branch;
import pfnpcbuyer.src.activity.branch.buying.BuyKebabs;
import pfnpcbuyer.src.activity.branch.buying.BuyNewspapers;
import pfnpcbuyer.src.activity.leaf.banking.DepositKebabs;
import pfnpcbuyer.src.activity.leaf.banking.DepositNewspapers;
import pfnpcbuyer.src.activity.leaf.buying.PurchaseKebab;
import pfnpcbuyer.src.activity.leaf.buying.PurchaseNewspaper;
import pfnpcbuyer.src.activity.leaf.traversal.WalkAlKharidBank;
import pfnpcbuyer.src.activity.leaf.traversal.WalkBenny;
import pfnpcbuyer.src.activity.leaf.traversal.WalkToKarim;
import pfnpcbuyer.src.activity.leaf.traversal.WalkVarrockEastBank;

import java.util.function.Supplier;

public enum ActivityBranch {
    BUY_KEBABS(
            () -> {
                Branch questBranch = new BuyKebabs();
                questBranch.addLeaves(
                        new WalkToKarim(),
                        new PurchaseKebab(),
                        new WalkAlKharidBank(),
                        new DepositKebabs());
                return questBranch;
            }),

    BUY_NEWSPAPERS(
            () -> {
                Branch questBranch = new BuyNewspapers();
                questBranch.addLeaves(
                        new WalkBenny(),
                        new PurchaseNewspaper(),
                        new WalkVarrockEastBank(),
                        new DepositNewspapers()
                );
                return questBranch;
            });

    private final Supplier<Branch> activityBranchSupplier;

    ActivityBranch(Supplier<Branch> activityBranchSupplier) {
        this.activityBranchSupplier = activityBranchSupplier;
    }

    public Branch getActivityBranch() {
        return activityBranchSupplier.get();
    }
}
