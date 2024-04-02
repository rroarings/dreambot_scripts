
package pfdyemaker.src.util;

import org.dreambot.api.script.frameworks.treebranch.Branch;
import pfdyemaker.src.action.redberries.branch.CollectRedberriesBranch;
import pfdyemaker.src.action.redberries.leaf.BankRedberriesLeaf;
import pfdyemaker.src.action.redberries.leaf.WalkToRedberriesLeaf;
import pfdyemaker.src.action.redberries.leaf.WalkToVarrockBankLeaf;
import pfdyemaker.src.action.woadleaves.leaf.WalkToWysonLeaf;
import pfdyemaker.src.action.woadleaves.branch.BuyWoadLeavesBranch;
import pfdyemaker.src.action.onions.leaf.WalkToOnionsLeaf;
import pfdyemaker.src.action.redberries.leaf.CollectRedberriesLeaf;
import pfdyemaker.src.action.bluedye.leaf.BankBlueDyeLeaf;
import pfdyemaker.src.action.navigation.WalkToDraynorBankLeaf;
import pfdyemaker.src.action.navigation.WalkToAggieLeaf;
import pfdyemaker.src.action.onions.branch.CollectOnionsBranch;
import pfdyemaker.src.action.bluedye.branch.MakeBlueDyeBranch;
import pfdyemaker.src.action.reddye.branch.MakeRedDyeBranch;
import pfdyemaker.src.action.yellowdye.branch.MakeYellowDyeBranch;

import pfdyemaker.src.action.onions.leaf.BankOnionsLeaf;
import pfdyemaker.src.action.reddye.leaf.BankRedDyeLeaf;
import pfdyemaker.src.action.woadleaves.leaf.BuyWoadLeavesLeaf;
import pfdyemaker.src.action.onions.leaf.CollectOnionsLeaf;
import pfdyemaker.src.action.bluedye.leaf.MakeBlueDyeLeaf;
import pfdyemaker.src.action.reddye.leaf.MakeRedDyeLeaf;
import pfdyemaker.src.action.yellowdye.leaf.MakeYellowDyeLeaf;

import java.util.function.Supplier;

public enum ActionBranch {

    BUY_WOAD_LEAFS(() -> {
        Branch actionBranch = new BuyWoadLeavesBranch();
        actionBranch.addLeaves(
                new WalkToWysonLeaf(),
                new BuyWoadLeavesLeaf()
        );
        return actionBranch;
    }),

    COLLECT_ONIONS(() -> {
        Branch actionBranch = new CollectOnionsBranch();
        actionBranch.addLeaves(
                new WalkToOnionsLeaf(),
                new CollectOnionsLeaf(),
                new WalkToDraynorBankLeaf(),
                new BankOnionsLeaf()
        );
        return actionBranch;
    }),

    COLLECT_REDBERRIES(() -> {
        Branch actionBranch = new CollectRedberriesBranch();
        actionBranch.addLeaves(
                new WalkToRedberriesLeaf(),
                new CollectRedberriesLeaf(),
                new WalkToVarrockBankLeaf(),
                new BankRedberriesLeaf()
        );
        return actionBranch;
    }),

    MAKE_BLUE_DYE(() -> {
        Branch actionBranch = new MakeBlueDyeBranch();
        actionBranch.addLeaves(
                new WalkToAggieLeaf(),
                new MakeBlueDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new BankBlueDyeLeaf()
        );
        return actionBranch;
    }),

    MAKE_RED_DYE(() -> {
        Branch actionBranch = new MakeRedDyeBranch();
        actionBranch.addLeaves(
                new WalkToAggieLeaf(),
                new MakeRedDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new BankRedDyeLeaf()
        );
        return actionBranch;
    }),

    MAKE_YELLOW_DYE(() -> {
        Branch actionBranch = new MakeYellowDyeBranch();
        actionBranch.addLeaves(
                new WalkToAggieLeaf(),
                new MakeYellowDyeLeaf(),
                new WalkToDraynorBankLeaf()
        );
        return actionBranch;
    });

    private final Supplier<Branch> actionBranchSupplier;

    ActionBranch(Supplier<Branch> questBranchSupplier) {
        this.actionBranchSupplier = questBranchSupplier;
    }

    public Branch getActionBranch() {
        return actionBranchSupplier.get();
    }
}