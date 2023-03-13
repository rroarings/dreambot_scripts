package pfchaosdruids.src;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.utilities.Timer;
import pfchaosdruids.src.activity.combat.branch.AttackDruidBranch;
import pfchaosdruids.src.activity.looting.leaf.LootItemLeaf;
import pfchaosdruids.src.util.PricedItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ScriptManifest(category = Category.COMBAT, name = "PF Chaos Druids", author = "pharaoh", version = 1.0)
public class PFChaosDruids extends TreeScript {

    private boolean hidePaint = false;
    private final Rectangle PAINT_BUTTON = new Rectangle(475, 458, 40, 18);

    private long startTime;
    private Image image;
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public List<PricedItem> lootTrack = new ArrayList<>();

    @Override
    public void onStart() {
        startTime = System.currentTimeMillis();
        addBranches(new AttackDruidBranch().addLeaves(new LootItemLeaf()));

        lootTrack.add(new PricedItem("Law rune", false));
        lootTrack.add(new PricedItem("Nature rune", false));

        log(lootTrack.stream().sorted().toString());
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

        if (!hidePaint) {
            if (getCurrentBranchName() != null) {
                if (image != null) g.drawImage(image, 0, 0, null);

                graphics2D.setColor(opaqueBlack);
                graphics2D.fillRoundRect(5, 342, 510, 134, 2, 2);

                graphics2D.setColor(opaqueOrange);
                graphics2D.draw(PAINT_BUTTON);

                graphics2D.setColor(Color.ORANGE);
                graphics2D.drawString(getManifest().name(), 370, 473);

                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString("Time: " + Timer.formatTime(runTime), x, 360);
                graphics2D.drawString(getCurrentBranchName(), x, 380);
                graphics2D.drawString(getCurrentLeafName(), x, 400);
                //graphics2D.drawString("Status: " + ec.getStatus(), x, 380);
                graphics2D.drawString("Experience: " + SkillTracker.getGainedExperience(Skill.MAGIC), x, 460);
                graphics2D.drawString("v " + getVersion(), 483, 473);

                /*if (ec.getPricedItem() != null) {
                    int itemsPH = (int) (ec.getPricedItem().getAmount() / ((System.currentTimeMillis() - startTime) / 3600000.0D));
                    ec.setProfit(ec.getPricedItem().getAmount() * ec.getPricedItem().getPrice());
                    graphics2D.drawString("Items: " + ec.getPricedItem().getAmount() + " (" + itemsPH + ")", x, 400);
                    graphics2D.drawString("Gold: " + QuantityFormatter.formatNumber(ec.getProfit()) + " (" + QuantityFormatter.formatNumber(timer.getHourlyRate(ec.getProfit())) + ")", x, 420);
                }*/
            }
        } else {
            graphics2D.setColor(Color.RED);
            graphics2D.drawString("Paint hidden", PAINT_BUTTON.x - 45, PAINT_BUTTON.y + 14);
        }
    }
}
