import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Random r = new Random();
        int[] arr = new int[5_000_000];
        for (int i = 0; i < arr.length; i++){
            arr[i] = r.nextInt();
        }
        int linSum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < arr.length; i++){
            linSum += arr[i];
        }
        long end = System.nanoTime();
        long singleRes = end - start;
        System.out.println("Single-threaded: " + singleRes + "ns");


        start = System.nanoTime();
        int parSum = parallelSum(arr);
        end = System.nanoTime();
        long multiRes = end - start;
        System.out.println("Multi-threaded(ForkJoinPool): " + multiRes + "ns");

        start = System.nanoTime();
        int parStreamSum = parallelSum(arr);
        end = System.nanoTime();
        long multiStreamRes = end - start;
        System.out.println("Multi-threaded(ParallelStream): " + multiRes + "ns");

        //System.out.println("Multi - Single --> " + (multiRes - singleRes));
        System.out.println("Is multi faster? " + (multiRes - singleRes < 0 ));
        assert linSum == parSum;
        assert linSum == parStreamSum;
    }
    public static int parallelSum(int[] arr) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SumTask(arr, 0, arr.length-1));
    }
    public static int parStreamSum(int[] arr){
        return Arrays.stream(arr).parallel().reduce(0, (a,b) -> Integer.sum(a,b));
    }
}
