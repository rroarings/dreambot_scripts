package pfminer.src;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import pfminer.src.branches.WaitForLoadBranch;
import pfminer.src.branches.banking.BankBranch;
import pfminer.src.branches.mining.MineBranch;
import pfminer.src.branches.walking.WalkQuarryBranch;
import pfminer.src.branches.walking.WalkSoulsChestBranch;
import pfminer.src.data.MinerConfig;
import pfminer.src.framework.Tree;
import pfminer.src.leafs.GoToGELeaf;
import pfminer.src.leafs.InGELeaf;
import pfminer.src.leafs.LoadWaitLeaf;
import pfminer.src.leafs.banking.DepositItemsLeaf;
import pfminer.src.leafs.mining.MineRockLeaf;
import pfminer.src.leafs.walking.WalkQuarryLeaf;
import pfminer.src.leafs.walking.WalkSoulsChestLeaf;
import pfminer.src.paint.CustomPaint;
import pfminer.src.paint.PaintInfo;
import pfminer.src.util.API;

import java.awt.*;

@ScriptManifest(name = "TreeFrame Test", author = "pharaoh", version = 0.1, category = Category.MISC)
public class PFMiner extends AbstractScript implements PaintInfo {

    public static Tree tree = new Tree();

    private void initTasks() {
        tree.addBranches(
                new WalkQuarryBranch().addLeafs(new WalkQuarryLeaf()),
                new MineBranch().addLeafs(new MineRockLeaf()),
                new WalkSoulsChestBranch().addLeafs(new WalkSoulsChestLeaf()),
                new BankBranch().addLeafs(new DepositItemsLeaf()));
        log("Tasks instantiated");
    }

    @Override
    public void onStart() {
        start();
    }

    public void start() {
        initTasks();
    }

    @Override
    public int onLoop() {
        return tree.onLoop();
    }

    // Our paint info
    // Add new lines to the paint here
    @Override
    public String[] getPaintInfo() {
        return new String[] {
                getManifest().name() +" "+ getManifest().version(),
                "Branch: " + API.currentBranch,
                "Leaf: " + API.currentLeaf,
                "Status: " + MinerConfig.status
        };
    }

    // Instantiate the paint object. This can be customized to your liking.
    private final CustomPaint CUSTOM_PAINT = new CustomPaint(this,
            CustomPaint.PaintLocations.BOTTOM_LEFT_PLAY_SCREEN,
            new Color[] {new Color(255, 251, 255)},
            "Trebuchet MS",
            new Color[] {new Color(50, 50, 50, 175)},
            new Color[] {new Color(28, 28, 29)},
            1, false, 5, 3, 0);
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    @Override
    public void onPaint(Graphics2D graphics2D) {
        graphics2D.setRenderingHints(aa);
        CUSTOM_PAINT.paint(graphics2D);
    }
}
