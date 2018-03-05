package view;
import containers.MyIDictionary;
import containers.MyIList;
import containers.MyIStack;
import controller.Controller;
import exceptions.ContainerException;
import exceptions.DomainException;
import exceptions.FileException;
import exceptions.StatementExecutionException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import model.PrgState;
import model.statements.*;
import model.expressions.*;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ViewController {

    private Controller controller;
    private List<Statement> loadablePrograms = new ArrayList<>();

    @FXML
    private TableView<Pair<String, Integer>> symbolsTable;

    @FXML
    private TableColumn<Pair<String, Integer>, String> symbolNameColumn;

    @FXML
    private TableColumn<Pair<String, Integer>, Integer> symbolValueColumn;

    @FXML
    private ListView<String> programsList;

    @FXML
    private ListView<Statement> exeStackList;

    @FXML
    private TableView<Pair<Integer, String>> filesTable;

    @FXML
    private TableColumn<Pair<Integer, String>, Integer> fileIdColumn;

    @FXML
    private TableColumn<Pair<Integer, String>, String> fileNameColumn;

    @FXML
    private TableView<Pair<Integer, Integer>> heapTable;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> heapIdColumn;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> heapValueColumn;

    @FXML
    private ListView<String> outputList;

    @FXML
    public void initialize(){
        Repository repo = new Repository(null,"logFile.txt");
        controller = new Controller(repo);
        fillProgramList();
        symbolNameColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        symbolValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        fileIdColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        heapIdColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        programsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        programsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                    populateSymbolTable();
                    populateExecutionStack();
                }
            }
        );
    }

    @FXML
    void loadButtonHandler(ActionEvent event) {
        int programChosenIndex = LoadProgramWindow.show(loadablePrograms);
        controller.loadProgram(loadablePrograms.get(programChosenIndex));
        populateAllViews();
        programsList.getSelectionModel().selectIndices(0);
    }

    @FXML
    void oneStepButtonHandler(ActionEvent event) {
        try {
            if (controller.getPrograms().size() > 0) {
                controller.executeOneStepAll();
                int lastIndex = programsList.getSelectionModel().getSelectedIndex();
                populateAllViews();
                if (programsList.getItems().size() - 1 < lastIndex)
                    programsList.getSelectionModel().selectIndices(0);
                else
                    programsList.getSelectionModel().selectIndices(lastIndex);
            }
        }
        catch(StatementExecutionException e){
            AlertBox.show("Unexpected exception", e.getMessage());
        }
    }

    private void populateAllViews(){
        populateProgramsList();
        populateSymbolTable();
        populateFileTable();
        populateHeap();
        populateOutputList();
        populateExecutionStack();
    }

    private void populateSymbolTable(){
        int index = programsList.getSelectionModel().getSelectedIndex();
        if (index != -1){
            List<Pair<String, Integer>> entries = new ArrayList<>();
            MyIDictionary<String, Integer> dict = controller.getPrograms().get(index).getSymbols();
            dict.getCollection().forEach((key, value) ->
                    entries.add(new Pair<>(key,value)));
            symbolsTable.getItems().setAll(entries);
        }
    }

    private void populateFileTable(){
        if (controller.getPrograms().size() > 0){
            List<Pair<Integer, String>> entries = new ArrayList<>();
            controller.getPrograms().get(0).getFileTable().getAll().forEach(
                    (key, value) -> entries.add(new Pair<>(key, value.getFileName()))
            );
            filesTable.getItems().setAll(entries);
        }
    }

    private void populateHeap(){
        if (controller.getPrograms().size() > 0){
            List<Pair<Integer, Integer>> entries = new ArrayList<>();
            controller.getPrograms().get(0).getHeap().getValues().forEach(
                    (key, value) -> entries.add(new Pair<>(key, value))
            );
            ObservableList<Pair<Integer, Integer>> observableList = FXCollections.observableList(entries);
            heapTable.setItems(observableList);
        }
    }

    private void populateExecutionStack(){
        int index = programsList.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            List<Statement> entries = new ArrayList<>();
            MyIStack<Statement> exeStack = controller.getPrograms().get(index).getExeStack();
            for (Statement s: exeStack.getAll()){
                if (s instanceof CompStatement)
                    while (s instanceof CompStatement) {
                        entries.add(((CompStatement)s).getFirst());
                        s = ((CompStatement)s).getSecond();
                    }
                entries.add(s);
            }
            exeStackList.getItems().setAll(entries);
        }
        else
            exeStackList.setItems(FXCollections.observableArrayList());
    }

    private void populateProgramsList(){
        List<PrgState> programs = controller.getPrograms();
        List<String> fields = new ArrayList<>();
        programs.forEach(prg -> {
            if (!prg.getExeStack().isEmpty()) {
                Statement topStatement = prg.getExeStack().top();
                if (topStatement instanceof CompStatement)
                    fields.add("" + prg.getId() + ": " + ((CompStatement) topStatement).getFirst());
                else
                    fields.add("" + prg.getId() + ": " + topStatement);
            }
        });
        programsList.getItems().setAll(fields);
    }

    private void populateOutputList(){
        if (controller.getPrograms().size() > 0){
            List<String> output = controller.getPrograms().get(0).getOut().getAll();
            outputList.getItems().setAll(output);
        }
    }

    private void fillProgramList(){
        Statement prog1 = new CompStatement(
                new CompStatement(
                        new AssignStatement("a", new ArithmeticExpression(
                                '+', new ConstExpression(2), new ConstExpression(5))),
                        new PrintStatement(new VarExpression("a"))),
                new CompStatement(
                        new AssignStatement("a", new ArithmeticExpression(
                                '*', new VarExpression("a"), new ConstExpression(7))),
                        new PrintStatement(new VarExpression("a"))));
        loadablePrograms.add(prog1);
        Statement prog2 = new CompStatement(
                new OpenFileStatement("a", "sampleFile.txt"), new CompStatement(
                new ReadStatement(new VarExpression("a"), "b"),
                new PrintStatement(new VarExpression("b"))
        ));
        loadablePrograms.add(prog2);
        Statement prog3 = new CompStatement(
                new AssignStatement("v", new ConstExpression(10)),
                new CompStatement(
                        new AssignStatement("b", new ConstExpression(50)),
                        new CompStatement(
                                new AllocateStatement("v", new ConstExpression(20)),
                                new CompStatement(
                                        new AllocateStatement("a", new ConstExpression(22)),
                                        new CompStatement(
                                                new PrintStatement(new ArithmeticExpression(
                                                        '+', new ConstExpression(100), new HeapVarExpression("v")
                                                )),
                                                new CompStatement(
                                                        new AssignStatement("a", new ConstExpression(100)),
                                                        new PrintStatement(new VarExpression("a"))
                                                )
                                        )
                                )
                        )
                )
        );
        loadablePrograms.add(prog3);
        Statement prog4 = new CompStatement(
                new AssignStatement("v", new ConstExpression(6)),
                new CompStatement(
                        new WhileStatement(
                                new ArithmeticExpression('-', new VarExpression("v"), new ConstExpression(4)),
                                new CompStatement(
                                        new PrintStatement(new VarExpression("v")),
                                        new AssignStatement("v", new ArithmeticExpression(
                                                '-', new VarExpression("v"), new ConstExpression(1))
                                        )
                                )
                        ),
                        new PrintStatement(new VarExpression("v"))
                )
        );
        loadablePrograms.add(prog4);
        Statement prog5 = new CompStatement(
                new AssignStatement( "v", new ConstExpression(10)),
                new CompStatement(
                        new AllocateStatement("a",new ConstExpression(20)),
                        new CompStatement(
                                new ForkStatement(
                                        new CompStatement(
                                                new WriteHeapStatement("a", new ConstExpression(30)),
                                                new CompStatement(
                                                        new AssignStatement("v",new ConstExpression(40)),
                                                        new CompStatement(
                                                                new PrintStatement(new VarExpression("v")),
                                                                new PrintStatement(new HeapVarExpression("a"))
                                                        )
                                                )
                                        )
                                ),
                                new CompStatement(
                                        new AssignStatement("v", new ConstExpression(50)),
                                        new CompStatement(
                                                new PrintStatement(new VarExpression("v")),
                                                new PrintStatement(new HeapVarExpression("a"))
                                        )
                                )
                        )
                )
        );
        loadablePrograms.add(prog5);
    }

}
