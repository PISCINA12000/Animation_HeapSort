package pissinin.gabriel.animacaoheap;

import java.util.Random;

public class Vetor {
    private final int N = 10;
    private int TL = 10;
    private int[] vetor = new int[N];

    public Vetor() {}

    public int getIndex(int pos){
        return vetor[pos];
    }

    public void setIndex(int pos, int num){
        vetor[pos] = num;
    }

    public void criarVetor() {
        alimentar();
        embaralhar();
        exibir();
    }

    public void alimentar() {
        for(int i = 0; i < N; i++) {
            vetor[i] = i+1;
        }
    }

    public void embaralhar() {
        Random gerador = new Random();
        int aux;
        for(int i = 0; i < N; i++) {
            int pos = gerador.nextInt(N);
            aux = vetor[i];
            vetor[i] = vetor[pos];
            vetor[pos] = aux;
        }
    }

    public void permutacao(int x, int y) {
        int aux;
        aux = vetor[x];
        vetor[x] = vetor[y];
        vetor[y] = aux;
    }

    public void exibir() {
        for(int i = 0; i < N; i++) {
            System.out.print(vetor[i] + " ");
        }
        System.out.println();
    }

//    public void heapSort() {
//        int TL2=this.TL, aux;
//
//        while(TL2>1){
//            // ARRUMA A ÁRVORE PARA DEIXAR O MAIOR ELEMENTO NA PRIMEIRA POSIÇÃO
//            heapify(TL2);
//
//            // PERMUTAÇÃO DO MAIOR ELEMENTO PARA A ÚLTIMA POSIÇÃO POSSÍVEL
//            aux = this.vetor[0];
//            this.vetor[0] = this.vetor[TL2-1];
//            this.vetor[TL2-1] = aux;
//
//            TL2--;
//        }
//    }
//    private void heapify(int tl2) {
//        int filhoEsq, filhoDir, filhoMax, aux;
//
//        for(int pai = tl2/2-1; pai>=0; pai--){
//            filhoEsq = pai*2+1;
//            filhoDir = pai*2+2;
//            filhoMax = filhoEsq;
//            if(filhoDir<tl2 && vetor[filhoDir]>this.vetor[filhoEsq]){
//                filhoMax = filhoDir;
//            }
//            if(vetor[filhoMax]>vetor[pai]){
//                aux = vetor[filhoMax];
//                vetor[filhoMax] = vetor[pai];
//                vetor[pai] = aux;
//            }
//        }
//    }

    // GETS E SETS
    public int getN() {
        return N;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }

    public int[] getVetor() {
        return vetor;
    }

    public void setVetor(int[] vetor) {
        this.vetor = vetor;
    }
}
