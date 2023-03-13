package pfdriftnets.src;

import org.dreambot.api.Client;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;
import pfdriftnets.src.data.DriftNetsConfig;
import pfdriftnets.src.framework.Tree;
import pfdriftnets.src.ui.DriftNetsUI;
import pfdriftnets.src.util.QuantityFormatter;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import static pfdriftnets.src.data.DriftNetsConfig.*;

@ScriptManifest(category = Category.MONEYMAKING, name = "PF Drift Nets", author = "pharaoh", version = 0.0)
public class PFDriftNets extends AbstractScript implements ChatListener {

    private long startTime;
    private Image image;
    private final Tree tree = new Tree();
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    DriftNetsConfig config = getDriftNetsConfig();

    private void initTree() {
        tree.addBranches(DriftNetsUI.getSelectedItem().getActivityBranch());
    }

    @Override
    public void onStart() {
        getDriftNetsConfig().setNetsPrice(LivePrices.get("Drift net"));
        startTime = System.currentTimeMillis();

        if (Client.isLoggedIn()) {
            SkillTracker.start(Skill.CRAFTING);
        }
        SwingUtilities.invokeLater(DriftNetsUI::new);

        try {
            image = ImageIO.read(new URL("https://i.imgur.com/ISw4eOO.png"));
        } catch (IOException e) {
            log("Failed to load image");
        }
    }

    @Override
    public int onLoop() {
        if (DriftNetsUI.isStartLoop()) {
            initTree();
        }
        return this.tree.onLoop();
    }

    @Override
    public void onMessage(Message m)  {
        if (m != null && m.getMessage().contains("You weave the jute fibres into this item.")) {
            getDriftNetsConfig().netsMade++;
        }
    }

    @Override
    public void onPaint(Graphics g) {
        long runTime = System.currentTimeMillis() - startTime;
        int pPH = (int) (getDriftNetsConfig().getProfit() / ((System.currentTimeMillis() - startTime) / 3600000.0D));
        int alpha = 127;
        int x = 12;

        Graphics2D graphics2D = (Graphics2D) g;
        Color opaqueBlack = new Color(0, 0, 0, alpha);

        getDriftNetsConfig().setProfit(getDriftNetsConfig().getNetsMade() * getDriftNetsConfig().getNetsPrice());
        graphics2D.setRenderingHints(aa);
        graphics2D.setFont(new Font("Bahnschrift", Font.PLAIN, 14));

        if (image != null) { g.drawImage(image, 0, 0, null);}

        graphics2D.setColor(opaqueBlack);
        graphics2D.fillRoundRect(5, 347, 324, 124, 8 , 8);

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(getManifest().name(), 127, 322);

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Time: " + Timer.formatTime(runTime), x, 360);
        graphics2D.drawString("Status: " + status, x, 380);
        graphics2D.drawString("Levels: " + Skills.getRealLevel(Skill.CRAFTING) + " (" + SkillTracker.getGainedLevels(Skill.CRAFTING) + ")", x, 400);
        graphics2D.drawString("Experience: " + QuantityFormatter.formatNumber(SkillTracker.getGainedExperience(Skill.CRAFTING)) + " (" + QuantityFormatter.formatNumber(SkillTracker.getGainedExperiencePerHour(Skill.CRAFTING)) + ")", x, 420);
        graphics2D.drawString("Nets: " + getDriftNetsConfig().getNetsMade(), x, 440);
        graphics2D.drawString("Gold: " + QuantityFormatter.quantityToRSDecimalStack(getDriftNetsConfig().getProfit()) + " (" + QuantityFormatter.quantityToStackSize(pPH) + ")", x, 460);
        graphics2D.drawString("v " + getVersion(), 294, 471);
    }
}
