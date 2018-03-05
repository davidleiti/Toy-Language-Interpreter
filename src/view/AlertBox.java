package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertBox {

    public static void show(String title, String errorMessage){
        Stage mainStage = new Stage();
        mainStage.setTitle(title);
        mainStage.setMinHeight(200);
        mainStage.setMinWidth(300);

        Label label = new Label(errorMessage);

        Button okButton = new Button("Ok");
        okButton.setOnAction(e ->{
           mainStage.close();
        });

        VBox layout = new VBox();
        layout.setPadding(new Insets(20,20,20,20));
        layout.setSpacing(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label,okButton);

        Scene scene = new Scene(layout);
        mainStage.setScene(scene);
        mainStage.showAndWait();

    }

}
