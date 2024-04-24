
package pfdyemaker.src.util;

import org.dreambot.api.script.frameworks.treebranch.Branch;
import pfdyemaker.src.action.redberries.branch.CollectRedberriesBranch;
import pfdyemaker.src.action.redberries.leaf.BankRedberriesLeaf;
import pfdyemaker.src.action.redberries.leaf.WalkToRedberriesLeaf;
import pfdyemaker.src.action.navigation.WalkToVarrockBankLeaf;
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
import pfdyemaker.src.action.yellowdye.leaf.DepositYellowDyeLeaf;
import pfdyemaker.src.action.yellowdye.leaf.MakeYellowDyeLeaf;
import pfdyemaker.src.action.yellowdye.leaf.WithdrawOnionLeaf;
import pfdyemaker.src.action.yellowdye.leaf.YellowDyeStartLeaf;

import java.util.function.Supplier;

public enum ActionBranch {

    BUY_WOAD_LEAVES("Buy woad leaves", () -> {
        Branch actionBranch = new BuyWoadLeavesBranch();
        actionBranch.addLeaves(
                new WalkToWysonLeaf(),
                new BuyWoadLeavesLeaf()
        );
        return actionBranch;
    }),

    COLLECT_ONIONS("Collect onions", () -> {
        Branch actionBranch = new CollectOnionsBranch();
        actionBranch.addLeaves(
                new WalkToOnionsLeaf(),
                new CollectOnionsLeaf(),
                new WalkToDraynorBankLeaf(),
                new BankOnionsLeaf()
        );
        return actionBranch;
    }),

    COLLECT_REDBERRIES("Collect redberries", () -> {
        Branch actionBranch = new CollectRedberriesBranch();
        actionBranch.addLeaves(
                new WalkToRedberriesLeaf(),
                new CollectRedberriesLeaf(),
                new WalkToVarrockBankLeaf(),
                new BankRedberriesLeaf()
        );
        return actionBranch;
    }),

    MAKE_BLUE_DYE("Make blue dye", () -> {
        Branch actionBranch = new MakeBlueDyeBranch();
        actionBranch.addLeaves(
                new WalkToAggieLeaf(),
                new MakeBlueDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new BankBlueDyeLeaf()
        );
        return actionBranch;
    }),

    MAKE_RED_DYE("Make red dye", () -> {
        Branch actionBranch = new MakeRedDyeBranch();
        actionBranch.addLeaves(
                new WalkToAggieLeaf(),
                new MakeRedDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new BankRedDyeLeaf()
        );
        return actionBranch;
    }),

    MAKE_YELLOW_DYE("Make yellow dye", () -> {
        Branch actionBranch = new MakeYellowDyeBranch();
        actionBranch.addLeaves(
                new YellowDyeStartLeaf(),
                new WithdrawOnionLeaf(),
                new WalkToAggieLeaf(),
                new MakeYellowDyeLeaf(),
                new WalkToDraynorBankLeaf(),
                new DepositYellowDyeLeaf()
        );
        return actionBranch;


    });

    private final String displayName;
    private final Supplier<Branch> actionBranchSupplier;

    ActionBranch(String displayName, Supplier<Branch> actionBranchSupplier) {
        this.displayName = displayName;
        this.actionBranchSupplier = actionBranchSupplier;
    }

    public Branch getActionBranch() {
        return actionBranchSupplier.get();
    }

    @Override
    public String toString() {
        String name = name().toLowerCase().replace("_", " ");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}