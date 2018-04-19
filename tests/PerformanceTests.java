package tests;
import datasets.ParallelDataSetLoader;
import datasets.SequentialDataSetLoader;
import model.Model;
import model.ParallelModel;
import model.Particle;
import model.SequentialModel;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.IntStream;

public class PerformanceTests {
    ParallelModel mp;
    SequentialModel ms;
    final int MAX_ITERATIONS = 500;
    final int MAX_REPEATS = 100;

    @Test
    public void testRandomSetLarge(){
        mp= ParallelDataSetLoader.getRandomSet(100, 800, 1000);
        ms= SequentialDataSetLoader.getRandomSet(100, 800, 1000);
        test(); }
    @Test
    public void testRandomSetSmall(){
        mp= ParallelDataSetLoader.getRandomSet(100, 800, 100);
        ms= SequentialDataSetLoader.getRandomSet(100, 800, 100);
        test(); }
    @Test
    public void testRotationGrid(){
        mp= ParallelDataSetLoader.getRandomRotatingGrid(100, 800, 20);
        ms= SequentialDataSetLoader.getRandomRotatingGrid(100, 800, 20);
        test(); }
    @Test
    public void testRandomGrid(){
        mp = ParallelDataSetLoader.getRandomGrid(100, 800, 20);
        ms = SequentialDataSetLoader.getRandomGrid(100, 800, 20);
        test(); }
    private void test(){
        double totalTimeP = 0;
        double totalTimeS = 0;

        for(int i=0;i<MAX_REPEATS;i++){
            double timeP = System.currentTimeMillis();
            IntStream.range(0, MAX_ITERATIONS).forEach(e ->mp.step());
            totalTimeP += System.currentTimeMillis()-timeP;

            double timeS = System.currentTimeMillis();
            IntStream.range(0, MAX_ITERATIONS).forEach(e ->ms.step());
            totalTimeS += System.currentTimeMillis()-timeS;
        }
        System.out.println(totalTimeP/MAX_REPEATS+" "+totalTimeS/MAX_REPEATS);
        assertTrue(totalTimeP/MAX_REPEATS<totalTimeS/MAX_REPEATS);
    }
}
