package pfdyemaker.src.paint;

import java.awt.*;
import java.util.List;

public class PaintUtils {

    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Color DEFAULT_COLOR = Color.WHITE;
    public static final Color OFF_WHITE = new Color(232,232,232);
    public static final Color LIGHT_GRAY = new Color(187, 187, 187);
    public static final Color GRAY = new Color(84, 84, 84);
    public static final Color YELLOW_LIGHT = new Color(255, 241, 118);
    public static final Color BLUE_LIGHT = new Color(100, 181, 246);
    private static final int DEFAULT_X = 5;
    private static final int DEFAULT_Y = 20;
    private static final RenderingHints ANTI_ALIASING = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    /**
     * Enables or disables antialiasing rendering hints for the given Graphics object.
     *
     * @param g          the Graphics object to enable or disable antialiasing for
     * @param enabled    true to enable anti-aliasing, false to disable it
     */
    public static void setAntiAliasingEnabled(Graphics g, boolean enabled) {
        Graphics2D graphics2D = (Graphics2D) g;
        if (enabled) {
            graphics2D.setRenderingHints(ANTI_ALIASING);
        } else {
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }

    /**
     * Draws a string of text using the default font, color, x, and y values.
     *
     * @param g the Graphics object
     * @param text the text to draw
     */
    public static void drawText(Graphics g, String text) {
        drawText(g, DEFAULT_COLOR, text, DEFAULT_X, DEFAULT_Y);
    }

    /**
     * Draws a string of text using the specified color and default x and y values.
     *
     * @param g the Graphics object
     * @param text the text to draw
     * @param color the color to use
     */
    public static void drawText(Graphics g, Color color, String text) {
        drawText(g, color, text, DEFAULT_X, DEFAULT_Y);
    }

    /**
     * Draws a string of text using the specified color, x, and y values.
     *
     * @param g the Graphics object
     * @param color the color to use
     * @param text the text to draw
     * @param x the x-coordinate to start drawing at
     * @param y the y-coordinate to start drawing at
     */
    public static void drawText(Graphics g, Color color, String text, int x, int y) {
        g.setColor(color);
        g.drawString(text, x, y);
    }

    /**
     * Draws a string of text using the specified color, x, and y values.
     *
     * @param g the Graphics object
     * @param text the text to draw
     * @param x the x-coordinate to start drawing at
     * @param y the y-coordinate to start drawing at
     */
    public static void drawText(Graphics g, String text, int x, int y) {
        g.setFont(DEFAULT_FONT);
        g.drawString(text, x, y);
    }

    /**
     * Draws a list of strings using the specified x and y values.
     * Each string in the list is drawn on a separate line.
     *
     * @param g the Graphics object
     * @param x the x-coordinate to start drawing at
     * @param y the y-coordinate to start drawing at
     * @param list the list of strings to draw
     */
    public static void drawTextList(Graphics g, int x, int y, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            g.drawString(list.get(i), x, y + i * 16);
        }
    }

    // todo fix color attribute setting color for all painted objects
    /**
     * Draws a progress bar using the specified x, y, width, height, color, and progress values.
     *
     * @param g the Graphics object
     * @param x the x-coordinate to start drawing at
     * @param y the y-coordinate to start drawing at
     * @param width the width of the progress bar
     * @param height the height of the progress bar
     * @param color the color of the progress bar
     */
    public static void drawProgressBar(Graphics g, int x, int y, long width, int height, Color color) {
        g.setColor(color);
        g.fillRect(x, y, (int) width, height);
    }

    /**
     * Draws a progress bar using the specified x, y, width, height, color, and progress values.
     *
     * @param g the Graphics object
     * @param x the x-coordinate to start drawing at
     * @param y the y-coordinate to start drawing at
     * @param width the width of the progress bar
     * @param height the height of the progress bar
     * @param progress the progress value (between 0 and 100)
     * @param color the color of the progress bar
     */
    public static void drawProgressBar(Graphics g, int x, int y, int width, int height, long progress, Color color) {
        g.setColor(color);
        g.fillRect(x, y, (int) (width * (progress / 100.0)), height);
    }
}
