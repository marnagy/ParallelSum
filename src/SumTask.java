import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Integer> {
    int[] arr;
    int low, mid, hi;
    public SumTask(int[] arr, int low, int hi) {
        this.arr = arr;
        this.low = low;
        this.hi = hi;
        this.mid = (low+hi)/2;
    }

    @Override
    protected Integer compute() {
        if (hi == low){
            return arr[hi];
        }
        else{
            SumTask left = new SumTask(arr, low, mid);
            SumTask right = new SumTask(arr, mid + 1, hi);
            invokeAll(left, right);
            int leftSum = 0, rightSum = 0;
            try {
                leftSum = left.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            try {
                rightSum = right.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return leftSum + rightSum;
        }
    }
}
