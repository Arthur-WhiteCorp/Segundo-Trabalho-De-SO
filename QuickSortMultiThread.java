import java.util.Random;
import java.util.concurrent.RecursiveAction;

import javax.swing.JOptionPane;

import java.util.concurrent.ForkJoinPool;

public class QuickSortMultiThread {
    //private int static final numThreads = Runtime.getRuntime().availableProcessors();
    private static int numThreads;

    public QuickSortMultiThread(){
        //System.out.println("Num threads: "+numThreads);
    }

    public static void quicksort(int[] array) {
        int tamanho=0;
        String tamanString = JOptionPane.showInputDialog("Insira o numero de threads: ");
        tamanho = Integer.parseInt(tamanString);
        QuickSortMultiThread.numThreads=tamanho;
        
        
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
    public static void main(String[] args) {
//        int[] array = {10, 7, 8, 9, 1, 5};
        int tamanho=10;
        int limiteInferior=1;
        int limiteSuperior=10000;

        String tamaho_vetor = JOptionPane.showInputDialog("Insira o tamanho do vetor: ");
        tamanho = Integer.parseInt(tamaho_vetor);

        Utilitarios utilitarios = new Utilitarios();

        int[] array = utilitarios.gerarVetor(tamanho,limiteInferior,limiteSuperior);
        long startTime = System.currentTimeMillis();
        quicksort(array);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
//        for (int i : array) {
//            System.out.print(i + " ");
//        }
       System.out.println(utilitarios.estaOrdenadoCrescente(array));
       System.out.println("Tempo millisegundos: "+duration);
       System.err.println("Numero threads:"+numThreads);


       JOptionPane.showMessageDialog(null,"Tempo para um vetor de tamanho "+tamaho_vetor+" com "+numThreads+": "+duration+" ms","QuickSort",JOptionPane.DEFAULT_OPTION);
    }
}

class Utilitarios{
    public boolean estaOrdenadoCrescente(int[] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            if (vetor[i - 1] > vetor[i]) {
                return false;
            }
        }
        return true;
    }

    public  int[] gerarVetor(int tamanho, int limiteInferior, int limiteSuperior) {
        Random random = new Random();
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = random.nextInt(limiteSuperior - limiteInferior + 1) + limiteInferior;
        }
        return vetor;
    }
}