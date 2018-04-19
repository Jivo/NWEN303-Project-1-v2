package datasets;

import model.Particle;
import model.ParallelModel;

import java.util.Random;

public class ParallelDataSetLoader {
    public static ParallelModel getRegularGrid(int min, int max, int distance){
        ParallelModel result=new ParallelModel();
        for(int i=min;i<max;i+=distance){
            for(int j=min;j<max;j+=distance){
                result.p.add(new Particle(0.5,0,0,i,j));
            }
        }
        return result;
    }
    public static ParallelModel getRandomGrid(int min, int max, int distance){
        ParallelModel result=new ParallelModel();
        Random r=new Random(1);
        for(int i=min;i<max;i+=distance){
            for(int j=min;j<max;j+=distance){
                result.p.add(new Particle(0.5,0,0,i+0.5-r.nextDouble(),j+0.5-r.nextDouble()));
            }
        }
        return result;
    }
    public static ParallelModel getRandomSet(int min, int max, int size){
        ParallelModel result=new ParallelModel();
        Random r=new Random(1);
        for(int i=0;i<size;i++){
            result.p.add(new Particle(0.5,0,0,min+r.nextInt(max-min)+0.5-r.nextDouble(),min+r.nextInt(max-min)+0.5-r.nextDouble()));
        }
        return result;
    }
    public static ParallelModel getRandomRotatingGrid(int min, int max, int distance){
        ParallelModel result=new ParallelModel();
        Random r=new Random(1);
        for(int i=min;i<max;i+=distance){
            for(int j=min;j<max;j+=distance){
                result.p.add(new Particle(0.5,Math.signum(j-450)*r.nextDouble()/10d,Math.signum(450-i)*r.nextDouble()/10d,i+r.nextDouble(),j+r.nextDouble()));
            }
        }
        return result;
    }

}
