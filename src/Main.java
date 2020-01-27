import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        Random r = new Random();
        int[] arr = new int[500_000_000];
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
        System.out.println("Multi-threaded: " + multiRes + "ns");

        //System.out.println("Multi - Single --> " + (multiRes - singleRes));
        System.out.println("Is multi faster? " + (multiRes - singleRes < 0 ));
        assert linSum == parSum;
    }
    public static int parallelSum(int[] arr) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SumTask(arr, 0, arr.length-1));
    }
}
