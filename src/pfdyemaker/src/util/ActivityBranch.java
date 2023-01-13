package pfdyemaker.src.util;

import pfdyemaker.src.activity.buywoadleaves.WalkToWysonLeaf;
import pfdyemaker.src.activity.buywoadleaves.BuyWoadLeavesBranch;
import pfdyemaker.src.activity.collectonions.WalkToOnionsLeaf;
import pfdyemaker.src.activity.collectredberries.*;
import pfdyemaker.src.activity.makebluedye.DepositBlueDyeLeaf;
import pfdyemaker.src.activity.navigation.WalkToDraynorBankLeaf;
import pfdyemaker.src.activity.navigation.WalkToAggieLeaf;
import pfdyemaker.src.activity.collectonions.CollectOnionsBranch;
import pfdyemaker.src.activity.makebluedye.MakeBlueDyeBranch;
import pfdyemaker.src.activity.makereddye.MakeRedDyeBranch;
import pfdyemaker.src.activity.makeyellowdye.MakeYellowDyeBranch;
import pfdyemaker.src.framework.Branch;
import pfdyemaker.src.activity.collectonions.BankOnionsLeaf;
import pfdyemaker.src.activity.makeyellowdye.BankYellowDyeLeaf;
import pfdyemaker.src.activity.makereddye.DepositRedDyeLeaf;
import pfdyemaker.src.activity.buywoadleaves.BuyWoadLeavesLeaf;
import pfdyemaker.src.activity.collectonions.CollectOnionsLeaf;
import pfdyemaker.src.activity.makebluedye.MakeBlueDyeLeaf;
import pfdyemaker.src.activity.makereddye.MakeRedDyeLeaf;
import pfdyemaker.src.activity.makeyellowdye.MakeYellowDyeLeaf;

import java.util.Locale;
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

    COLLECT_REDBERRIES(() -> {
        Branch activityBranch = new CollectRedberriesBranch();
        activityBranch.addLeafs(
                new WalkToRedberriesLeaf(),
                new CollectRedberriesLeaf(),
                new WalkToVarrockBankLeaf(),
                new BankRedberriesLeaf()
        );
        return activityBranch;
    }),

    MAKE_BLUE_DYE(() -> {
        Branch activityBranch = new MakeBlueDyeBranch();
        activityBranch.addLeafs(
                new WalkToAggieLeaf(),
                new MakeBlueDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new DepositBlueDyeLeaf()
        );
        return activityBranch;
    }),

    MAKE_RED_DYE(() -> {
        Branch activityBranch = new MakeRedDyeBranch();
        activityBranch.addLeafs(
                new WalkToAggieLeaf(),
                new MakeRedDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new DepositRedDyeLeaf()
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
