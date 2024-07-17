import java.util.Random;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class QuickSortMultiThread {
    private static final int numThreads = Runtime.getRuntime().availableProcessors();

    public QuickSortMultiThread(){
        System.out.println("Num threads: "+numThreads);
    }

    public static void quicksort(int[] array) {
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        pool.invoke(new QuickSortTask(array, 0, array.length - 1));
    }

    private static class QuickSortTask extends RecursiveAction {
        private int[] array;
        private int low, high;

        public QuickSortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (low < high) {
                int pivotIndex = partition(array, low, high);
                QuickSortTask leftTask = new QuickSortTask(array, low, pivotIndex - 1);
                QuickSortTask rightTask = new QuickSortTask(array, pivotIndex + 1, high);
                invokeAll(leftTask, rightTask);
            }
        }

        private int partition(int[] array, int low, int high) {
            int pivot = array[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (array[j] <= pivot) {
                    i++;
                    swap(array, i, j);
                }
            }
            swap(array, i + 1, high);
            return i + 1;
        }

        private void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static boolean estaOrdenadoCrescente(int[] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            if (vetor[i - 1] > vetor[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] gerarVetor(int tamanho, int limiteInferior, int limiteSuperior) {
        Random random = new Random();
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = random.nextInt(limiteSuperior - limiteInferior + 1) + limiteInferior;
        }
        return vetor;
    }
    public static void main(String[] args) {
//        int[] array = {10, 7, 8, 9, 1, 5};
        int tamanho=20000000;
        int limiteInferior=1;
        int limiteSuperior=10000;
        int[] array = gerarVetor(tamanho,limiteInferior,limiteSuperior);
        long startTime = System.currentTimeMillis();
        quicksort(array);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
//        for (int i : array) {
//            System.out.print(i + " ");
//        }
//        System.out.println(estaOrdenadoCrescente(array));
        System.out.println("Tempo millisegundos: "+duration);
    }
}
