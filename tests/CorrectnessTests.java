package tests;
import datasets.ParallelDataSetLoader;
import datasets.SequentialDataSetLoader;
import model.ModelParallel;
import model.ModelAbstract;
import model.Particle;
import model.Model;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.IntStream;

public class CorrectnessTests {
    ModelParallel mp;
    Model ms;
    final int MAX_ITERATIONS = 500;

    @Test public void testEvery_20_States(){ testAll(20); }
    @Test public void testEvery_10_States(){ testAll(10); }
    @Test public void testEvery_1_States(){ testAll(1); }
    @Test public void testEvery_13_States(){ testAll(1); }

    private void testAll(int period){
        setRandomSet(100);test_iterations(period);
        setRotationGrid();test_iterations(period);
        setRandomGrid(30);test_iterations(period);
        setRandomGrid(40);test_iterations(period);
    }
    private void test_iterations(int period){
        IntStream.range(0, MAX_ITERATIONS).forEach(i -> {
            mp.step();
            ms.step();
            if(i%period ==0){ assertTrue(compareState(mp, ms)); }
        });
    }

    private void setRandomSet(int size){
        mp= ParallelDataSetLoader.getRandomSet(100, 800, size);
        ms= SequentialDataSetLoader.getRandomSet(100, 800, size);
    }
    private void setRotationGrid(){
        mp= ParallelDataSetLoader.getRandomRotatingGrid(100, 800, 40);
        ms= SequentialDataSetLoader.getRandomRotatingGrid(100, 800, 40);
    }
    private void setRandomGrid(int dist){
        mp = ParallelDataSetLoader.getRandomGrid(100, 800, dist);
        ms = SequentialDataSetLoader.getRandomGrid(100, 800, dist);
    }
    private boolean compareState(ModelAbstract m1, ModelAbstract m2){
        List<Particle> ps1 = m1.p;
        List<Particle> ps2 = m2.p;
        return Particle.equalSets(ps1, ps2);
    }

}
