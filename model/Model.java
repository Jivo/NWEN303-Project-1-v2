package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Model extends ModelAbstract {
  public void step() {
    for(Particle p:this.p){p.interact(this);}
    mergeParticles();
    for(Particle p:this.p){p.move(this);}
    updateGraphicalRepresentation();
  }
  private void updateGraphicalRepresentation() {
    ArrayList<DrawableParticle> d=new ArrayList<DrawableParticle>();
    Color c=Color.ORANGE;
    for(Particle p:this.p){
      d.add(new DrawableParticle((int)p.x, (int)p.y, (int)Math.sqrt(p.mass),c ));
    }
    this.pDraw=d;//atomic update
  }
  public void mergeParticles(){
    Stack<Particle> deadPs=new Stack<Particle>();
    for(Particle p:this.p){ if(!p.impacting.isEmpty()){deadPs.add(p);}; }
    this.p.removeAll(deadPs);
    while(!deadPs.isEmpty()){
      Particle current=deadPs.pop();
      Set<Particle> ps=getSingleChunck(current);
      deadPs.removeAll(ps);
      this.p.add(mergeParticles(ps));
    }
  }
  private Set<Particle> getSingleChunck(Particle current) {
    Set<Particle> impacting=new HashSet<Particle>();
    impacting.add(current);
    while(true){
      Set<Particle> tmp=new HashSet<Particle>();
      for(Particle pi:impacting){tmp.addAll(pi.impacting);}
      boolean changed=impacting.addAll(tmp);
      if(!changed){break;}
      }
    //now impacting have all the chunk of collapsing particles
    return impacting;
  }
    public Particle mergeParticles(Set<Particle> ps){
      double speedX=0;
      double speedY=0;
      double x=0;
      double y=0;
      double mass=0;
      for(Particle p:ps){  mass+=p.mass; }
      for(Particle p:ps){
        x+=p.x*p.mass;
        y+=p.y*p.mass;
        speedX+=p.speedX*p.mass;
        speedY+=p.speedY*p.mass;
        }
      x/=mass;
      y/=mass;
      speedX/=mass;
      speedY/=mass;
      return new Particle(mass,speedX,speedY,x,y);
    }
}
