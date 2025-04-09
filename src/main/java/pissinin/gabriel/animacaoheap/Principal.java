package pissinin.gabriel.animacaoheap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.concurrent.CountDownLatch;
import static java.lang.Thread.sleep;

public class Principal extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button[] vet;
    private Button[] arvore;
    Vetor vetor = new Vetor();

    public static void main(String[] args) {
        launch(args);
    }

    private void inicializar(Stage stage) {
        vetor.criarVetor();
        stage.setTitle("Pesquisa e Ordenação - HEAP SORT");
        pane = new AnchorPane();

        // DEFINO O BOTÃO INÍCIO E TODAS AS SUAS FUNÇÕES
        botao_inicio = new Button();
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(100);
        botao_inicio.setText("Iniciar");
        botao_inicio.setOnAction(e -> {
            iniciarOrdenacao();
        });
        pane.getChildren().add(botao_inicio);
        criarBotoes();
        criarArvore();

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        inicializar(stage);
    }

    public void criarBotoes() {
        vet = new Button[10];
        for (int i = 0; i < vetor.getTL(); i++) {
            vet[i] = new Button(String.valueOf(vetor.getIndex(i)));
            vet[i].setLayoutX(100 + (i*50));
            vet[i].setLayoutY(200);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            vet[i].setFont(new Font(14));
            pane.getChildren().add(vet[i]);
        }
    }

    public void instanciarArvore() {

    }

    public void criarArvore() {
        arvore = new Button[10];
        for (int i = 0; i < vetor.getTL(); i++) {
            arvore[i] = new Button(String.valueOf(vetor.getIndex(i)));
            arvore[i].setMinHeight(40);
            arvore[i].setMinWidth(40);
            arvore[i].setFont(new Font(14));
        }
        posicionarNoArvore(0,100,600,0); //pos vet, inicio, fim, nivel
        for (int i = 0; i < vetor.getTL(); i++) {
            pane.getChildren().add(arvore[i]);
        }
    }

    public void posicionarNoArvore(int pos, int inicio, int fim, int nivel) {
        arvore[pos].setText(vet[pos].getText());
        arvore[pos].setLayoutX((inicio+fim)/2);
        arvore[pos].setLayoutY(300+nivel*50);
        if(pos*2+1 <= vetor.getTL()-1) {
            posicionarNoArvore(pos*2+1,inicio,(inicio+fim)/2,nivel+1);
        }
        if(pos*2+2 <= vetor.getTL()-1) {
            posicionarNoArvore(pos*2+2,(inicio+fim)/2,fim, nivel+1);
        }
    }

    public void iniciarOrdenacao() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {
                heapSort();
                setarBotoesVerde();
                return null;
            }

            public void setarBotoesVerde() throws InterruptedException {
                for(int i = 0; i < vetor.getTL(); i++){
                    setarCorBotao(vet[i], 2); //o dois é o código para a cor verde
                    sleep(100);
                }
            }

            private void heapSort() throws InterruptedException {
                int TL2 = vetor.getTL(), aux;

                while(TL2>1) {
                    // ARRUMA A ÁRVORE PARA DEIXAR O MAIOR ELEMENTO NA PRIMEIRA POSIÇÃO
                    heapify(TL2);
                    move_botoes(0, TL2 - 1);
                    setarCorBotao(vet[TL2-1],6); //seta o último botão para cinza escuro, indicando que está ordenado
                    setarCorBotao(arvore[TL2-1],6);
                    TL2--;
                }
            }
            private void heapify(int tl2) throws InterruptedException {
                int filhoEsq, filhoDir, filhoMax, aux;

                for(int pai = tl2/2-1; pai>=0; pai--){
                    setarCorBotao(vet[pai], 3);
                    setarCorBotao(arvore[pai], 3);
                    filhoEsq = pai*2+1;
                    filhoDir = pai*2+2;
                    filhoMax = filhoEsq;
                    if(filhoDir<tl2 && Integer.parseInt(vet[filhoDir].getText()) > Integer.parseInt(vet[filhoEsq].getText())){
                        filhoMax = filhoDir;
                    }
                    if(filhoDir<tl2){
                        setarCorBotao(vet[filhoDir],4);
                        setarCorBotao(arvore[filhoDir],4);
                    }
                    setarCorBotao(vet[filhoEsq],4);
                    setarCorBotao(arvore[filhoEsq],4);
                    sleep(200);
                    if(Integer.parseInt(vet[filhoMax].getText()) > Integer.parseInt(vet[pai].getText())){
                        move_botoes(pai, filhoMax);
                    }
                    //voltar as cores originais
                    setarCorBotao(vet[pai],0);
                    setarCorBotao(arvore[pai],0);
                    setarCorBotao(vet[filhoEsq],0);
                    setarCorBotao(arvore[filhoEsq],0);
                    if(filhoDir<tl2) {
                        setarCorBotao(vet[filhoDir], 0);
                        setarCorBotao(arvore[filhoDir], 0);
                    }
                }
            }

            private void move_botoes(int x, int y) throws InterruptedException {
                CountDownLatch latch = new CountDownLatch(2);

                Platform.runLater(() -> {
                    Task<Void> mover = new Task<Void>() {
                        @Override
                        protected Void call() {
                            try {
                                //permutação na tela do vetor principal
                                // SUBIR O X e DESCER O Y
                                for (int i = 0; i < 10; i++) {
                                    Platform.runLater(() -> vet[x].setLayoutY(vet[x].getLayoutY() + 5));
                                    Platform.runLater(() -> vet[y].setLayoutY(vet[y].getLayoutY() - 5));
                                    sleep(25);
                                }

                                int iteracoes = (int) ((vet[y].getLayoutX() - vet[x].getLayoutX()) / 5);
                                //ANDAR PARA DIREITA O X e ANDAR PARA A ESQUERDA O Y
                                for (int i = 0; i < iteracoes; i++) {
                                    Platform.runLater(() -> vet[x].setLayoutX(vet[x].getLayoutX() + 5));
                                    Platform.runLater(() -> vet[y].setLayoutX(vet[y].getLayoutX() - 5));
                                    sleep(25);
                                }

                                //DESCER O X e SUBIR O Y
                                for (int i = 0; i < 10; i++) {
                                    Platform.runLater(() -> vet[x].setLayoutY(vet[x].getLayoutY() - 5));
                                    Platform.runLater(() -> vet[y].setLayoutY(vet[y].getLayoutY() + 5));
                                    sleep(25);
                                }

//                                //Permutação na tela da minha árvore
//                                if(arvore[x].getLayoutX() < arvore[y].getLayoutX()) {
//                                    int movX, movY;
//                                    movX = (int)(arvore[y].getLayoutX()-arvore[x].getLayoutX())/5;
//                                    movY = (int)(arvore[y].getLayoutY()-arvore[x].getLayoutY())/movX;
//                                    for(int i = 0; i < movX; i++) {
//                                        Platform.runLater(()-> arvore[x].setLayoutX(arvore[x].getLayoutX() + 5));
//                                        Platform.runLater(()-> arvore[x].setLayoutY(arvore[x].getLayoutY() + movY));
//                                        Platform.runLater(()-> arvore[y].setLayoutX(arvore[y].getLayoutX() - 5));
//                                        Platform.runLater(()-> arvore[y].setLayoutY(arvore[y].getLayoutY() - movY));
//                                        sleep(25);
//                                    }
//                                }
//                                else{
//                                    int movX, movY;
//                                    movX = (int)(arvore[x].getLayoutX()-arvore[y].getLayoutX())/5;
//                                    movY = (int)(arvore[y].getLayoutY()-arvore[x].getLayoutY())/movX;
//                                    for(int i = 0; i < movX; i++) {
//                                        Platform.runLater(()-> arvore[x].setLayoutX(arvore[x].getLayoutX() - 5));
//                                        Platform.runLater(()-> arvore[x].setLayoutY(arvore[x].getLayoutY() + movY));
//                                        Platform.runLater(()-> arvore[y].setLayoutX(arvore[y].getLayoutX() + 5));
//                                        Platform.runLater(()-> arvore[y].setLayoutY(arvore[y].getLayoutY() - movY));
//                                        sleep(25);
//                                    }
//                                }

                                //Posições iniciais
                                double x1 = arvore[x].getLayoutX();
                                double y1 = arvore[x].getLayoutY();
                                double x2 = arvore[y].getLayoutX();
                                double y2 = arvore[y].getLayoutY();

                                double dx = (x2 - x1) / 20;
                                double dy = (y2 - y1) / 20;

                                for (int i = 0; i < 20; i++) {
                                    Platform.runLater(() -> {
                                        arvore[x].setLayoutX(arvore[x].getLayoutX() + dx);
                                        arvore[x].setLayoutY(arvore[x].getLayoutY() + dy);
                                        arvore[y].setLayoutX(arvore[y].getLayoutX() - dx);
                                        arvore[y].setLayoutY(arvore[y].getLayoutY() - dy);
                                    });
                                    try {
                                        Thread.sleep(25);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                //Para ter certeza que os botões não fiquem deslocados
                                Platform.runLater(() -> {
                                    arvore[x].setLayoutX(x2);
                                    arvore[x].setLayoutY(y2);
                                    arvore[y].setLayoutX(x1);
                                    arvore[y].setLayoutY(y1);
                                });

                                //permutação na memória
                                Platform.runLater(() -> {
                                    Button aux = vet[x];
                                    vet[x] = vet[y];
                                    vet[y] = aux;
                                    latch.countDown();
                                });

                                //Permutação na memória da árvore
                                Platform.runLater(() -> {
                                   Button aux = arvore[x];
                                   arvore[x] = arvore[y];
                                   arvore[y] = aux;
                                   latch.countDown();
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                                latch.countDown();
                            }
                            return null;
                        }
                    };

                    Thread t = new Thread(mover);
                    t.setDaemon(true);
                    t.start();
                });

                // Aguarda a conclusão da animação antes de prosseguir
                latch.await();
            } /*termino do move_botoes*/

            public void setarCorBotao(Button botao, int codigo) {
                Platform.runLater(() -> {
                    // Remover estilos anteriores
                    botao.setStyle("");

                    switch (codigo) {
                        case 0: // Padrão
                            // Não é necessário adicionar estilo, apenas remover os anteriores
                            break;
                        case 1: // Vermelho
                            botao.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
                            break;
                        case 2: // Verde
                            botao.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                            break;
                        case 3: // Azul
                            botao.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                            break;
                        case 4: // Amarelo
                            botao.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black;");
                            break;
                        case 5: // Roxo
                            botao.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
                            break;
                        case 6: // Cinza
                            botao.setStyle("-fx-background-color: #4F4F4F; -fx-text-fill: white;");
                            break;
                        default: // Caso o código não seja reconhecido, use a cor padrão
                            break;
                    }
                });
            } /*definir cores para os botões*/

        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
