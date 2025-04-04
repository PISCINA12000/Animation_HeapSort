package pissinin.gabriel.animacaoheap;

import java.util.Random;

public class Codigo {
    private final int N = 10;
    private int[] vetor = new int[N];

    public static void main(String[] args) {
        Codigo codigo = new Codigo();
        codigo.criarVetor();
        codigo.heapSort();
    }

    private void criarVetor() {
        alimentar();
        embaralhar();
        exibir();
    }

    private void alimentar() {
        for(int i = 0; i < N; i++) {
            vetor[i] = i+1;
        }
    }

    private void embaralhar() {
        Random gerador = new Random();
        int aux;
        for(int i = 0; i < N; i++) {
            int pos = gerador.nextInt(N);
            aux = vetor[i];
            vetor[i] = vetor[pos];
            vetor[pos] = aux;
        }
    }

    private void exibir() {
        for(int i = 0; i < N; i++) {
            System.out.print(vetor[i] + " ");
        }
    }

    private void heapSort() {

    }
}