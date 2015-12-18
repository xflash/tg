package org.xflash.detection;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.xflash.utils.ColorUtils;

/**
 * @author rcoqueugniot
 * @since 17.12.15
 */
public class Sight {

    private static final double SIGHT_LENGTH = Math.PI / 8;
    private final Color sightColor;
    private final Vector2f location = new Vector2f();
    private final int radius;
    private int sightTurn;
    private double sightAngle;
    private double sightAngleTarget;
    private double sightDelta;

    public Sight(int radius) {
        this.radius = radius;
        sightColor = ColorUtils.buildTrnasprentColor(Color.yellow, .6f);
    }

    public void moveTo(float x, float y) {
        location.set(x - radius / 2, y - radius / 2);
    }

    /*

 |____/
 | x /|
 |  / |
 | /  | y
 |/ a |
-+----+--
 |

 x=cos(a)
 y=sin(a)

 */

    void update(GameContainer container, int delta) {
        sightTurn += delta;
        double v = (double) sightTurn % (2 * SIGHT_LENGTH);

//            sightAngle+=sightDelta;
//            if(sightAngle>sightAngleTarget) {
//                sightAngle = sightAngleTarget;
//        }

    }

    public void render(GameContainer container, Graphics g) {

        g.setColor(Color.yellow);
        g.drawOval(location.x, location.y, radius, radius);

        g.setColor(sightColor);
        g.fillArc(location.x, location.y,
                radius, radius,
                (float) Math.toDegrees(sightAngle - SIGHT_LENGTH),
                (float) Math.toDegrees(sightAngle + SIGHT_LENGTH));

        g.setColor(Color.orange);
//        g.drawLine(location.x, location.y, (float) (location.x + Math.cos(moveAngle) * SIGHT_RADIUS), (float) (centerY + Math.sin(moveAngle) * SIGHT_RADIUS));
    }

    public void lookAt(double angle) {
        System.out.println("lookAt = " + Math.toDegrees(angle)+" sightAngle: "+String.format("%.2f",Math.toDegrees(sightAngle)));
        this.sightAngle=angle;
//        this.sightAngleTarget=angle;
//        sightDelta = (sightAngleTarget - sightAngle)/16;


    }
}
