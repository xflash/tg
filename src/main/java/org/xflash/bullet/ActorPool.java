package org.xflash.bullet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Object pooling for actors.
 */
public abstract class ActorPool<T extends Actor> {
    protected int actorIdx = 0;
    private T[] actor;


    public ActorPool(int n, Class<T> clazz) {
        this(n, clazz, null);
    }

    public ActorPool(int n, Class<T> clazz, Object[] args) {
        createActors(clazz, n, args);
    }

    protected void createActors(Class<T> clazz, int n, Object[] args) {

        actor = (T[]) Array.newInstance(clazz, n);

        for (int i = 0; i < actor.length; i++) {
            try {
                actor[i] = clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            actor[i].exists(false);
            actor[i].init(args);
        }
        actorIdx = 0;
    }

    public T getInstance() {
        for (int i = 0; i < actor.length; i++) {
            actorIdx--;
            if (actorIdx < 0)
                actorIdx = actor.length - 1;
            if (!actor[actorIdx].exists())
                return actor[actorIdx];
        }
        return null;
    }

    public T getInstanceForced() {
        actorIdx--;
        if (actorIdx < 0)
            actorIdx = actor.length - 1;
        return actor[actorIdx];
    }

    public List<T> getMultipleInstances(int n) {
        List<T> rsl=new ArrayList<T>();
        for (int i = 0; i < n; i++) {
            T inst = getInstance();
            if (inst == null) {
                for (T t : rsl) t.exists(false);
                return null;
            }
            inst.exists(true);
//            rsl ~ = inst;
            rsl.add(inst);
        }
        for (T t : rsl) t.exists(false);
        return rsl;
    }


    public void update(GameContainer gc, int delta) {
        for (T ac : actor) {
            if (ac.exists())
                ac.update(gc, delta);
        }
    }

    public void render(GameContainer gc, Graphics g) {
        for (T ac : actor) {
            if (ac.exists())
                ac.render(gc, g);
        }
    }

    public void clear() {
        for (T ac : actor) {
            ac.exists(false);
        }
        actorIdx = 0;
    }
}
