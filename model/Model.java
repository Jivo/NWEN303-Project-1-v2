package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Model {
    public static final double size=900;
    public static final double gravitationalConstant=0.002;
    public static final double lightSpeed=10;//the smaller, the larger is the chunk of universe we simulate
    public static final double timeFrame=20;//the bigger, the shorter is the time of a step
    public volatile List<Particle> p=new ArrayList<Particle>();
    public volatile List<DrawableParticle> pDraw=new ArrayList<DrawableParticle>();

    public abstract void step();
}
