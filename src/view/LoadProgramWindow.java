package view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.statements.Statement;

import java.util.List;

public class LoadProgramWindow {

    public static int choice;

    public static int show(List<Statement> programList){

        Stage mainStage = new Stage();
        mainStage.setTitle("Load a program");
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.setMinWidth(650);
        mainStage.setMinHeight(300);

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setSpacing(20);

        ListView<Statement> programsListView = new ListView<>();
        programsListView.setItems(FXCollections.observableList(programList));
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        programsListView.getSelectionModel().selectIndices(0);

        Label label = new Label("Please choose a program from the above list, then press Go! to load it");

        Button loadButton = new Button("Go!");
        loadButton.setOnAction(e -> {
                int index = programsListView.getSelectionModel().getSelectedIndex();
                if (index != -1){
                    choice = index;
                    mainStage.close();
                }
            });

        layout.getChildren().addAll(programsListView, label, loadButton);
        Scene scene = new Scene(layout);
        mainStage.setScene(scene);
        mainStage.showAndWait();
        return choice;
    }

}
