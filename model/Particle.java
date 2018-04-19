package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Particle{
  public Particle(double mass, double speedX, double speedY, double x, double y){
    this.mass = mass;
    this.speedX = speedX;
    this.speedY = speedY;
    this.x = x;
    this.y = y;
  }
  public Set<Particle> impacting=new HashSet<Particle>();
  public double mass;
  public double speedX;
  public double speedY;
  public double x;
  public double y;
  public void move(Model m){
    x+=speedX/(Model.timeFrame);
    y+=speedY/(Model.timeFrame);
    //uncomment the following to have particle bouncing on the boundary
    //if(this.x<0){this.speedX*=-1;}
    //if(this.y<0){this.speedY*=-1;}
    //if(this.x>SequentialModel.size){this.speedX*=-1;}
    //if(this.y>SequentialModel.size){this.speedY*=-1;}
  }
  public  boolean isImpact(double dist,double otherMass){
    if(Double.isNaN(dist)){return true;}
    double distMass=Math.sqrt(mass)+Math.sqrt(otherMass);
    if(dist<distMass*distMass){return true;}
    return false;
  }
  public  boolean isImpact(Iterable<Particle> ps){
    for(Particle p:ps){
      if(this==p){continue;}
      double dist=distance2(p);
      if(isImpact(dist,p.mass)){return true;}
      }
    return false;
  }
  public double distance2(Particle p){
    double distX=this.x-p.x;
    double distY=this.y-p.y;
    return distX*distX+distY*distY;
  }
  public void interact(Model m){
    for(Particle p:m.p){
      if(p==this)continue;
      double dirX=-Math.signum(this.x-p.x);
      double dirY=-Math.signum(this.y-p.y);
      double dist=distance2(p);
      if(isImpact(dist,p.mass)){this.impacting.add(p);continue;}
      dirX=p.mass* SequentialModel.gravitationalConstant*dirX/dist;
      dirY=p.mass* SequentialModel.gravitationalConstant*dirY/dist;
      assert this.speedX<= SequentialModel.lightSpeed:this.speedX;
      assert this.speedY<= SequentialModel.lightSpeed:this.speedY;
      double newSpeedX=this.speedX+dirX;
      newSpeedX/=(1+(this.speedX*dirX)/ SequentialModel.lightSpeed);
      double newSpeedY=this.speedY+dirY;
      newSpeedY/=(1+(this.speedY*dirY)/ SequentialModel.lightSpeed);
      if(!Double.isNaN(dirX)){this.speedX=newSpeedX;}
      if(!Double.isNaN(dirY)){this.speedY=newSpeedY;}
    }
  }

  public boolean isEqual(Particle p){
      return this.mass == p.mass &&
              this.x == p.x &&
              this.y == p.y &&
              this.speedX == p.speedX &&
              equalSets(this.impacting, p.impacting);
  }

  public static boolean equalSets(Collection<Particle> set1, Collection<Particle> set2){
    if(set1.size() != set2.size()){return false;}

    for(Particle p1: set1){
      boolean found = false;
      for(Particle p2: set2){
        if(p1.isEqual(p2))found = true;
      }
      if(!found){return false;}
    }
    return true;
  }
}
