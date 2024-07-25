import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

public class QuickSort {

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
//        int[] array = {7, 2, 1, 6, 8, 5, 3, 4};
        int tamanho=1000000;
        int limiteInferior=1;
        int limiteSuperior=10000;

        String tamanString = JOptionPane.showInputDialog("Insira o tamanho do vetor: ");
        tamanho = Integer.parseInt(tamanString);

        // JOptionPane.showMessageDialog(null,tamanString,"QuickSort",JOptionPane.DEFAULT_OPTION);

        // JOptionPane.showMessageDialog(null, "alerta","alerta", JOptionPane.ERROR_MESSAGE);



        int[] array = gerarVetor(tamanho,limiteInferior,limiteSuperior);

//        System.out.println("Array antes do Quicksort: " + Arrays.toString(array));

        long startTime = System.currentTimeMillis();
        quickSort(array, 0, array.length - 1);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
//        System.out.println(estaOrdenadoCrescente(array));
        // System.out.println("Tempo millisegundos: "+duration);
        JOptionPane.showMessageDialog(null,"Tempo  para um vetor de tamanho "+tamanString+": "+duration+" ms","QuickSort",JOptionPane.DEFAULT_OPTION);

//        System.out.println("Array depois do Quicksort: " + Arrays.toString(array));
    }

    public static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right);

            quickSort(array, left, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, right);
        }
    }

    public static int partition(int[] array, int left, int right) {
        int pivot = array[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, right);

        return i + 1;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
