package pfitemlooter.src.util;

import org.dreambot.api.script.frameworks.treebranch.Branch;
import pfitemlooter.src.activity.branch.LootCauldron;
import pfitemlooter.src.activity.branch.LootFishFood;
import pfitemlooter.src.activity.leaf.banking.DepositCauldronItems;
import pfitemlooter.src.activity.leaf.banking.DepositFishFood;
import pfitemlooter.src.activity.leaf.looting.TakeCauldronItems;
import pfitemlooter.src.activity.leaf.looting.TakeFishFood;
import pfitemlooter.src.activity.leaf.traversal.WalkCauldron;
import pfitemlooter.src.activity.leaf.traversal.WalkDraynorBank;
import pfitemlooter.src.activity.leaf.traversal.WalkDraynorManor;
import pfitemlooter.src.activity.leaf.traversal.WalkVarrockWest;

import java.util.function.Supplier;

public enum ActivityBranch {

    LOOT_CAULDRON(
            () -> {
                Branch activity = new LootCauldron();
                activity.addLeaves(
                        new WalkCauldron(),
                        new TakeCauldronItems(),
                        new WalkVarrockWest(),
                        new DepositCauldronItems()
                );
                return activity;
            }),

    LOOT_FISH_FOOD(
            () -> {
                Branch questBranch = new LootFishFood();
                questBranch.addLeaves(
                        new WalkDraynorManor(),
                        new TakeFishFood(),
                        new WalkDraynorBank(),
                        new DepositFishFood());
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
