package org.xflash.steering;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.Vector;

/**
 * @author rcoqueugniot
 * @since 18.12.15
 */
public class Path {
    private Vector<Vector2f> nodes = new Vector<Vector2f>();

    public void addNode(Vector2f vector2f) {

        this.nodes.add(vector2f);
    }

    public Vector<Vector2f> getNodes() {
        return nodes;
    }

    public void render(GameContainer container, Graphics g, int radius) {

        Vector2f currentnode = nodes.elementAt(0);
        drawPoint(g, currentnode, radius);

        for (int i = 1; i < nodes.size(); i++) {
            Vector2f vector2f = nodes.elementAt(i);
            drawPoint(g, vector2f, radius);
            Vector2f node = nodes.get(i);
            g.setColor(new Color(0x323232));
            g.drawLine(currentnode.x, currentnode.y, node.x, node.y);
            currentnode = node;
        }
    }

    private void drawPoint(Graphics g, Vector2f force, int diameter) {
        int radius = 6 / 2;
        g.setColor(new Color(0xFF5000));
        g.fillOval(force.x - radius, force.y - radius, 6, 6);

        g.setLineWidth(1);
        g.setColor(new Color(0x323232));
        g.drawOval(force.x - radius, force.y - radius, 6, 6);

        g.setLineWidth(2f);
        radius = diameter / 2;
        g.setColor(new Color(0xFF, 0x50, 0, .6f));
        g.drawOval(force.x - radius, force.y - radius, diameter, diameter);

        g.resetLineWidth();
    }
}
