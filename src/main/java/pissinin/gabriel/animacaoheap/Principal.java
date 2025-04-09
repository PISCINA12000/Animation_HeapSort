package pissinin.gabriel.animacaoheap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.concurrent.CountDownLatch;
import static java.lang.Thread.sleep;

public class Principal extends Application {
    private AnchorPane pane;
    private Button botao_inicio;
    private Button[] vet;
    private Button[] arvore;
    private Text[] programa;
    private Text titulo;
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
        botao_inicio.setLayoutY(210);
        botao_inicio.setText("Iniciar");
        botao_inicio.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
        botao_inicio.setOnAction(e -> {
            botao_inicio.setDisable(true);
            iniciarOrdenacao();
        });
        pane.getChildren().add(botao_inicio);
        criarBotoes();
        criarArvore();
        criarAlgoritmo();
        criarTitulo();

        Scene scene = new Scene(pane, 1100, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        inicializar(stage);
    }

    public void criarAlgoritmo() {
        programa = new Text[25]; //meu programa contém 25 linhas
        programa[0] = new Text("public void heapSort() {");
        programa[1] = new Text("|\tint TL2=this.TL, aux;");
        programa[2] = new Text("|\twhile(TL2>1){");
        programa[3] = new Text("|\t|\theapify(TL2);");
        programa[4] = new Text("|\t|\taux = this.vetor[0];");
        programa[5] = new Text("|\t|\tthis.vetor[0] = this.vetor[TL2-1];");
        programa[6] = new Text("|\t|\tthis.vetor[TL2-1] = aux;");
        programa[7] = new Text("|\t|\tTL2--;");
        programa[8] = new Text("|\t}");
        programa[9] = new Text("}");
        programa[10] = new Text("private void heapify(int tl2) {");
        programa[11] = new Text("|\tint filhoEsq, filhoDir, filhoMax, aux;");
        programa[12] = new Text("|\tfor(int pai = tl2/2-1; pai>=0; pai--){");
        programa[13] = new Text("|\t|\tfilhoEsq = pai*2+1;");
        programa[14] = new Text("|\t|\tfilhoDir = pai*2+2;");
        programa[15] = new Text("|\t|\tfilhoMax = filhoEsq;");
        programa[16] = new Text("|\t|\tif(filhoDir<tl2 && vetor[filhoDir]>this.vetor[filhoEsq])");
        programa[17] = new Text("|\t|\t|\tfilhoMax = filhoDir;");
        programa[18] = new Text("|\t|\tif(vetor[filhoMax]>vetor[pai]){");
        programa[19] = new Text("|\t|\t|\taux = vetor[filhoMax];");
        programa[20] = new Text("|\t|\t|\tvetor[filhoMax] = vetor[pai];");
        programa[21] = new Text("|\t|\t|\tvetor[pai] = aux;");
        programa[22] = new Text("|\t|\t}");
        programa[23] = new Text("|\t}");
        programa[24] = new Text("}");

        //vou posicioná-los
        for (int i = 0; i < programa.length; i++) {
            programa[i].setLayoutX(630);
            programa[i].setLayoutY(30+i*20);
            programa[i].setStyle("-fx-fill: #000000;");
            programa[i].setVisible(true);
            programa[i].setFont(new Font(14));
            pane.getChildren().add(programa[i]);
        }
    }

    public void criarTitulo(){
        titulo = new Text("HEAP SORT");
        titulo.setFont(new Font("Arial Bold", 32));
        titulo.setLayoutY(80);
        titulo.setLayoutX(250);

        // Efeito de sombreamento para dar profundidade
        DropShadow sombra = new DropShadow();
        sombra.setColor(Color.GRAY);
        sombra.setOffsetX(3);
        sombra.setOffsetY(3);
        sombra.setRadius(5);
        titulo.setEffect(sombra);

        // Aplicando um gradiente linear para o texto
        LinearGradient gradiente = new LinearGradient(
                0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.DARKBLUE),
                new Stop(0.5, Color.BLUE),
                new Stop(1, Color.DARKBLUE)
        );

        titulo.setFill(gradiente);

        // Contorno do texto
        titulo.setStrokeWidth(1);
        titulo.setStroke(Color.BLACK);

        pane.getChildren().add(titulo);
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

    public void criarArvore() {
        arvore = new Button[10];
        for (int i = 0; i < vetor.getTL(); i++) {
            arvore[i] = new Button(String.valueOf(vetor.getIndex(i)));
            arvore[i].setMinHeight(40);
            arvore[i].setMinWidth(40);
            arvore[i].setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
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
                int TL2 = vetor.getTL();
                setarCorTexto(programa[1], 1);sleep(400);
                setarCorTexto(programa[1], 0); //voltar ao normal

                //grifar o teste do while
                setarCorTexto(programa[2], 1); sleep(400);
                while(TL2>1) {
                    //verde se entrou
                    setarCorTexto(programa[2], 2);sleep(200);
                    // ARRUMA A ÁRVORE PARA DEIXAR O MAIOR ELEMENTO NA PRIMEIRA POSIÇÃO
                    //grifar a chamada de função heapfy
                    setarCorTexto(programa[3], 3);sleep(200);
                    heapify(TL2);
                    setarCorTexto(programa[3], 0); //voltar a função heapify para o padrão
                    //grifar a permutação
                    setarCorTexto(programa[4], 1);
                    setarCorTexto(programa[5], 1);
                    setarCorTexto(programa[6], 1);
                    setarCorTexto(programa[7], 1);
                    move_botoes(0, TL2 - 1);
                    //voltar o código da permutação ao normal
                    setarCorTexto(programa[4], 0);
                    setarCorTexto(programa[5], 0);
                    setarCorTexto(programa[6], 0);
                    setarCorTexto(programa[7], 0);
                    setarCorBotao(vet[TL2-1],6); //seta o último botão para cinza escuro, indicando que está ordenado
                    setarCorBotao(arvore[TL2-1],6);
                    TL2--;
                }
                setarCorTexto(programa[2], 1);sleep(400); //vermelho rapidamente pois testou e deu falso
                setarCorTexto(programa[2], 0);
                setarCorBotao(arvore[TL2-1],6);
                botao_inicio.setDisable(false);
            }
            private void heapify(int tl2) throws InterruptedException {
                //grifar as delarações
                setarCorTexto(programa[11], 1); sleep(400);
                setarCorTexto(programa[11], 0);
                int filhoEsq, filhoDir, filhoMax;

                //grifar o teste do for
                setarCorTexto(programa[12], 1); sleep(400);
                for(int pai = tl2/2-1; pai>=0; pai--){
                    //verde se entrou
                    setarCorTexto(programa[12], 2); sleep(400);
                    setarCorBotao(vet[pai], 3);
                    setarCorBotao(arvore[pai], 3);
                    //grifar rapidamente as declarações
                    setarCorTexto(programa[13], 1); sleep(400);
                    setarCorTexto(programa[14], 1); sleep(400);
                    setarCorTexto(programa[15], 1); sleep(400);
                    setarCorTexto(programa[13], 0);
                    setarCorTexto(programa[14], 0);
                    setarCorTexto(programa[15], 0);
                    filhoEsq = pai*2+1;
                    filhoDir = pai*2+2;
                    filhoMax = filhoEsq;
                    //grifar a pergunta do if
                    setarCorTexto(programa[16], 1); sleep(400);
                    if(filhoDir<tl2 && Integer.parseInt(vet[filhoDir].getText()) > Integer.parseInt(vet[filhoEsq].getText())){
                        //se entrar no if eu o deixo como verde
                        setarCorTexto(programa[16], 2); sleep(400);
                        //grifar a atribuição
                        setarCorTexto(programa[17], 1); sleep(400);
                        setarCorTexto(programa[17], 0);
                        filhoMax = filhoDir;
                    }
                    setarCorTexto(programa[16], 0); //retiro a formatação pois saí do if
                    //esse if é apenas visual para os botões, não necessário tratar
                    if(filhoDir<tl2){
                        setarCorBotao(vet[filhoDir],4);
                        setarCorBotao(arvore[filhoDir],4);
                    }
                    setarCorBotao(vet[filhoEsq],4);
                    setarCorBotao(arvore[filhoEsq],4);
                    sleep(200);
                    //grifar a pergunta
                    setarCorTexto(programa[18], 1); sleep(400);
                    if(Integer.parseInt(vet[filhoMax].getText()) > Integer.parseInt(vet[pai].getText())){
                        //verde se verdade
                        setarCorTexto(programa[18], 2); sleep(400);
                        //grifar a permutação
                        setarCorTexto(programa[19], 1);
                        setarCorTexto(programa[20], 1);
                        setarCorTexto(programa[21], 1);
                        move_botoes(pai, filhoMax);
                        //'desgrifar' a permutação
                        setarCorTexto(programa[19], 0);
                        setarCorTexto(programa[20], 0);
                        setarCorTexto(programa[21], 0);
                    }
                    setarCorTexto(programa[18], 0); //voltar ao original pois saiu do if
                    //voltar as cores originais
                    setarCorBotao(vet[pai],0);
                    setarCorBotao(arvore[pai],2);
                    setarCorBotao(vet[filhoEsq],0);
                    setarCorBotao(arvore[filhoEsq],2);
                    if(filhoDir<tl2) {
                        setarCorBotao(vet[filhoDir], 0);
                        setarCorBotao(arvore[filhoDir], 2);
                    }
                }
                setarCorTexto(programa[12], 1); sleep(400); //pois para sair o for precisa ser falso
                setarCorTexto(programa[12], 0); //retirar a formatação pois saí do for
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

            public void setarCorTexto(Text texto, int codigo) {
                Platform.runLater(() -> {
                    // Remover estilos anteriores
                    texto.setStyle("");

                    switch (codigo) {
                        case 0: // Padrão
                            texto.setStyle("-fx-fill: #000000;");
                            break;
                        case 1: // Vermelho
                            texto.setStyle("-fx-background-color: #A9A9A9; -fx-fill: #ff4d4d;");
                            break;
                        case 2: // Verde
                            texto.setStyle("-fx-fill: #4CAF50;");
                            break;
                        case 3: // Azul
                            texto.setStyle("-fx-fill: #2196F3;");
                            break;
                        case 4: // Amarelo
                            texto.setStyle("-fx-fill: #FFC107;");
                            break;
                        case 5: // Roxo
                            texto.setStyle("-fx-fill: #9C27B0;");
                            break;
                        case 6: // Cinza
                            texto.setStyle("-fx-fill: #4F4F4F;");
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
