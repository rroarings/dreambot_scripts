package pfdriftnets.src.util;

import pfdriftnets.src.framework.Branch;
import pfdriftnets.src.location.fossilisland.*;

import java.util.function.Supplier;

public enum ActivityBranch {

    FOSSIL_ISLAND(() -> {
        Branch branch = new FossilIsland();
        branch.addLeafs(
                new FossilIslandBankingLeaf(), new WalkToLoom(), new SpinNetsLeaf(), new LogOutLeaf());
        return branch;
    });

    private final Supplier<Branch> activityBranchSupplier;

    ActivityBranch(Supplier<Branch> questBranchSupplier) {
        this.activityBranchSupplier = questBranchSupplier;
    }

    public Branch getActivityBranch() {
        return activityBranchSupplier.get();
    }
}
