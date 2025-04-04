module pissinin.gabriel.animacaoheap {
    requires javafx.controls;
    requires javafx.fxml;


    opens pissinin.gabriel.animacaoheap to javafx.fxml;
    exports pissinin.gabriel.animacaoheap;
}