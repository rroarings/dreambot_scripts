package pfdyemaker.src.util;

import pfdyemaker.src.branches.buying.BuyWoadLeavesBranch;
import pfdyemaker.src.branches.collecting.CollectOnionsBranch;
import pfdyemaker.src.branches.collecting.CollectRedberriesBranch;
import pfdyemaker.src.branches.making.MakeBlueDyeBranch;
import pfdyemaker.src.branches.making.MakeRedDyeBranch;
import pfdyemaker.src.branches.making.MakeYellowDyeBranch;
import pfdyemaker.src.framework.Branch;
import pfdyemaker.src.leafs.banking.BankOnionsLeaf;
import pfdyemaker.src.leafs.banking.BankYellowDyeLeaf;
import pfdyemaker.src.leafs.banking.DoDraynorBankLeaf;
import pfdyemaker.src.leafs.banking.DoVarrockBankLeaf;
import pfdyemaker.src.leafs.buying.BuyWoadLeavesLeaf;
import pfdyemaker.src.leafs.collecting.CollectOnionsLeaf;
import pfdyemaker.src.leafs.collecting.CollectRedberriesLeaf;
import pfdyemaker.src.leafs.making.MakeBlueDyeLeaf;
import pfdyemaker.src.leafs.making.MakeRedDyeLeaf;
import pfdyemaker.src.leafs.making.MakeYellowDyeLeaf;
import pfdyemaker.src.leafs.walking.*;

import java.util.function.Supplier;

public enum ActivityBranch {

    BUY_WOAD_LEAFS(() -> {
        Branch activityBranch = new BuyWoadLeavesBranch();
        activityBranch.addLeafs(
                new WalkToWysonLeaf(),
                new BuyWoadLeavesLeaf()
        );
        return activityBranch;
    }),

    MAKE_BLUE_DYE(() -> {
        Branch activityBranch = new MakeBlueDyeBranch();
        activityBranch.addLeafs(
                new WalkToAggieLeaf(),
                new MakeBlueDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new DoDraynorBankLeaf()
        );
        return activityBranch;
    }),

    COLLECT_REDBERRIES(() -> {
        Branch activityBranch = new CollectRedberriesBranch();
        activityBranch.addLeafs(
                new WalkToRedberriesLeaf(),
                new CollectRedberriesLeaf(),
                new WalkToVarrockBankLeaf(),
                new DoVarrockBankLeaf()
        );
        return activityBranch;
    }),

    MAKE_RED_DYE(() -> {
        Branch activityBranch = new MakeRedDyeBranch();
        activityBranch.addLeafs(
                new WalkToAggieLeaf(),
                new MakeRedDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new DoDraynorBankLeaf()
        );
        return activityBranch;
    }),

    COLLECT_ONIONS(() -> {
        Branch activityBranch = new CollectOnionsBranch();
        activityBranch.addLeafs(
                new WalkToOnionsLeaf(),
                new CollectOnionsLeaf(),
                new WalkToDraynorBankLeaf(),
                new BankOnionsLeaf()
        );
        return activityBranch;
    }),

    MAKE_YELLOW_DYE(() -> {
        Branch activityBranch = new MakeYellowDyeBranch();
        activityBranch.addLeafs(
                new WalkToAggieLeaf(),
                new MakeYellowDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new BankYellowDyeLeaf()
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
