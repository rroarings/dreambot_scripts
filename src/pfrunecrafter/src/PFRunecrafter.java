package pfrunecrafter.src;

import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import pfrunecrafter.src.activity.branch.navigation.WalkEarthAltarBranch;
import pfrunecrafter.src.activity.leaf.navigation.WalkEarthAltarLeaf;

import java.awt.*;

@ScriptManifest(category = Category.RUNECRAFTING, name = "PF Runecrafter", author = "pharaoh", version = 1.0)
public class PFRunecrafter extends TreeScript {

    private boolean hidePaint = false;
    private final Rectangle PAINT_BUTTON = new Rectangle(475, 458, 40, 18);
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private long startTime;

    @Override
    public void onStart() {
        startTime = System.currentTimeMillis();
        addBranches(new WalkEarthAltarBranch().addLeaves(new WalkEarthAltarLeaf()));
    }

    @Override
    public void onPaint(Graphics g) {
        long runTime = System.currentTimeMillis() - startTime;
        int alpha = 127;
        int x = 12;

        Graphics2D graphics2D = (Graphics2D) g;
        Color opaqueBlack = new Color(0, 0, 0, alpha);
        Color opaqueOrange = new Color(255, 165, 0, alpha);

        graphics2D.setRenderingHints(aa);
        graphics2D.setFont(new Font("Bahnschrift", Font.PLAIN, 14));

        if (getCurrentBranchName() != null) {

            graphics2D.setColor(opaqueBlack);
            graphics2D.fillRoundRect(5, 342, 510, 134, 2, 2);

            // transparent click box to show/hide the paint
            graphics2D.setColor(Color.ORANGE);
            //graphics2D.setColor(opaqueOrange);
            graphics2D.draw(PAINT_BUTTON);


            graphics2D.drawString(getManifest().name(), 300, 473);

            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(getCurrentBranchName(), x, 360);
            graphics2D.drawString(getCurrentLeafName(), x, 380);
            graphics2D.drawString("v " + getVersion(), 483, 473);
        }
    }
}
