package gui;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import datasets.ParallelDataSetLoader;
import datasets.SequentialDataSetLoader;
import model.ModelAbstract;

public class Gui extends JFrame implements Runnable{
  private static int frameTime=10;//use a bigger or smaller number for faster/slower simulation
  private static int stepsForFrame=20;//use a bigger or smaller number for faster/slower simulation
  //it will attempt to do 4 steps every 20 milliseconds (less if the machine is too slow)

  public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
  ModelAbstract m;
  String name;
  Gui(ModelAbstract m, String name){this.m=m;this.name=name;}
  public void run() {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle(name);
    getRootPane().setLayout(new BorderLayout());
    JPanel p=new Canvas(m);
    getRootPane().add(p,BorderLayout.CENTER);
    pack();
    setVisible(true);
    scheduler.scheduleAtFixedRate(
      ()->SwingUtilities.invokeLater(()->repaint()),
      500,25, TimeUnit.MILLISECONDS
      );
    }
  private static final class MainLoop implements Runnable {
    ModelAbstract m;MainLoop(ModelAbstract m){this.m=m;}
    public void run() {
      try{
        while(true){
          long ut=System.currentTimeMillis();
          for(int i=0;i<stepsForFrame;i++) {m.step();}
          ut=System.currentTimeMillis()-ut;//used time
          //System.out.println("Particles: "+m.p.size()+" time:"+ut);//if you want to have an idea of the time consumption
          long sleepTime=frameTime-ut;
          if(sleepTime>1){ Thread.sleep(sleepTime);}
          }//if the step was short enough, it wait to make it at least frameTime long.
        }
      catch(Throwable t){//not a perfect solution, but
        t.printStackTrace();//makes sure you see the error and the program dies.
        System.exit(0);//the "right" solution is much more involved
        }//and would require storing and passing the exception between different objects.
      }
    }
  public static void main(String[] args) {
    //ModelAbstract m=SequentialDataSetLoader.getRegularGrid(100, 800, 40);//Try those configurations
//    ModelAbstract m= SequentialDataSetLoader.getRandomRotatingGrid(100, 800, 40);
//    ModelAbstract m=SequentialDataSetLoader.getRandomSet(100, 800, 1000);
    //ModelAbstract m=SequentialDataSetLoader.getRandomSet(100, 800, 100);
    //ModelAbstract m=SequentialDataSetLoader.getRandomGrid(100, 800, 30);

    //ModelAbstract m=ParallelDataSetLoader.getRegularGrid(100, 800, 40);//Try those configurations
//    ModelAbstract m= ParallelDataSetLoader.getRandomRotatingGrid(100, 800, 40);
//    ModelAbstract m=ParallelDataSetLoader.getRandomSet(100, 800, 1000);
    //ModelAbstract m=ParallelDataSetLoader.getRandomSet(100, 800, 100);
    //ModelAbstract m=ParallelDataSetLoader.getRandomGrid(100, 800, 30);

    ModelAbstract mp= ParallelDataSetLoader.getRandomRotatingGrid(100, 800, 40);
    ModelAbstract ms= SequentialDataSetLoader.getRandomRotatingGrid(100, 800, 40);
//    ModelAbstract mp=ParallelDataSetLoader.getRandomSet(100, 800, 100);
//    ModelAbstract ms=SequentialDataSetLoader.getRandomSet(100, 800, 100);

    scheduler.schedule(new MainLoop(mp), 500, TimeUnit.MILLISECONDS);
    SwingUtilities.invokeLater(new Gui(mp, "Parallel"));
    scheduler.schedule(new MainLoop(ms), 500, TimeUnit.MILLISECONDS);
    SwingUtilities.invokeLater(new Gui(ms, "Sequential"));

    }
  }