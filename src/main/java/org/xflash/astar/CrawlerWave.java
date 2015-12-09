package org.xflash.astar;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.InputAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CrawlerWave {

    private final List<Crawler> crawlers;
    private final GameContainer gc;
    private final PlayMap playMap;
    private final CrawerSpawnListener crawerSpawnListener;
    private int crawlingTime;
    private int max;

    public CrawlerWave(final GameContainer gc, PlayMap playMap, int max, CrawerSpawnListener crawerSpawnListener) {
        this.gc = gc;
        this.playMap = playMap;
        this.crawerSpawnListener = crawerSpawnListener;
        crawlers = new ArrayList<Crawler>();
        crawlingTime = 0;
        this.max = max;
        gc.getInput().addKeyListener(new InputAdapter(){
            @Override
            public void keyPressed(int key, char c) {
                if(Input.KEY_SPACE==key)
                spawn(gc);
            }
        });
    }

    public void update(GameContainer gc, int delta) {
        crawlingTime += delta;
        if (crawlingTime > 5000) {
            crawlingTime = 0;
            if (crawlers.size() < max)
                spawn(gc);
        }
        for (Crawler crawler : crawlers) {
            crawler.update(gc, delta);
        }
    }

    private boolean spawn(GameContainer gc) {
        Crawler crawler = new Crawler(gc, playMap);
        crawerSpawnListener.crawlerSpawned(crawler);
        crawler.spawn(playMap.getStart(), playMap.getFinish());
        return crawlers.add(crawler);
    }

    public void render(GameContainer gc, Graphics gfx) throws SlickException {
        for (Crawler crawler : crawlers) {
            crawler.render(gc, gfx);
        }

        gfx.drawString(String.format("Crawlers: %d", crawlers.size()), 10, 50);

    }
}
