package pissinin.gabriel.animacaoheap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Principal extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button[] vet;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenação");
        pane = new AnchorPane();

        // DEFINO O BOTÃO INÍCIO E TODAS AS SUAS FUNÇÕES
        botao_inicio = new Button();
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(100);
        botao_inicio.setText("Inicia...");
        botao_inicio.setOnAction(e -> {
            move_botoes();
        });
        pane.getChildren().add(botao_inicio);

        // ADICIONANDO O PRIMEIRO ELEMENTO A SE MOVIMENTAR
        vet = new Button[2];
        vet[0] = new Button("10");
        vet[0].setLayoutX(100);
        vet[0].setLayoutY(200);
        vet[0].setMinHeight(40);
        vet[0].setMinWidth(40);
        vet[0].setFont(new Font(14));

        // ADICIONANDO O SEGUNDO ELEMENTO A SE MOVIMENTAR
        pane.getChildren().add(vet[0]);
        vet[1] = new Button("20");
        vet[1].setLayoutX(180);
        vet[1].setLayoutY(200);
        vet[1].setMinHeight(40);
        vet[1].setMinWidth(40);
        vet[1].setFont(new Font(14));
        pane.getChildren().add(vet[1]);
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void move_botoes() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                //permutação na tela
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() + 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16; i++) {
                    Platform.runLater(() -> vet[0].setLayoutX(vet[0].getLayoutX() + 5));
                    Platform.runLater(() -> vet[1].setLayoutX(vet[1].getLayoutX() - 5));

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() - 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //permutação na memória
                Button aux = vet[0];
                vet[0] = vet[1];
                vet[1] = aux;
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}
