package pfropebuyer;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;

import pfropebuyer.src.branches.banking.ShouldBankRopeBranch;
import pfropebuyer.src.branches.collection.CollectRopesBranch;
import pfropebuyer.src.branches.navigation.WalkToDraynorBankBranch;
import pfropebuyer.src.branches.navigation.WalkToNedsBranch;
import pfropebuyer.src.data.RopeBuyerConfig;
import pfropebuyer.src.framework.Tree;
import pfropebuyer.src.leafs.banking.ShouldBankRopesLeaf;
import pfropebuyer.src.leafs.collection.CollectRopesLeaf;
import pfropebuyer.src.leafs.navigation.WalkToDraynorBankLeaf;
import pfropebuyer.src.leafs.navigation.WalkToNedsLeaf;

import java.awt.*;

@ScriptManifest(category = Category.MONEYMAKING, name = "PF Rope Buyer", author = "pharaoh", version = 0.1)
public class PFRopeBuyer extends AbstractScript {

    //todo add energy potion support

    private long startTime;
    private long runTime;
    public static Tree tree = new Tree();
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private void initTasks() {
        tree.addBranches(
                new WalkToNedsBranch().addLeafs(new WalkToNedsLeaf()),
                new CollectRopesBranch().addLeafs(new CollectRopesLeaf()),
                new WalkToDraynorBankBranch().addLeafs(new WalkToDraynorBankLeaf()),
                new ShouldBankRopeBranch().addLeafs(new ShouldBankRopesLeaf()));

        log("Tasks instantiated");
    }

    @Override
    public void onStart() {
        initTasks();
        startTime = System.currentTimeMillis();
    }
    
    @Override
    public int onLoop() {
        return tree.onLoop();
    }

    @Override
    public void onPaint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHints(aa);

        runTime = System.currentTimeMillis() - startTime;

        int alpha = 127;
        Color opaqueBlack = new Color(0, 0, 0, alpha);

        graphics2D.setColor(opaqueBlack);
        graphics2D.fillRect(4, 239, 250, 100);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("RuneScape Chat '07", Font.PLAIN, 16));

        graphics2D.drawString("Time: " + Timer.formatTime(runTime), 10, 260);
        graphics2D.drawString("Status: " + RopeBuyerConfig.status, 10, 280);
        graphics2D.drawString("v " + getVersion(), 220, 333);

        graphics2D.setColor(Color.CYAN);
        graphics2D.drawString(getManifest().name(), 130, 333);
    }
}
