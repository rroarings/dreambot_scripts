package pfdyemaker.src.leafs.walking;

import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

public class WalkToLumbridgeBankLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public int onLoop() {
        return 0;
    }
}
