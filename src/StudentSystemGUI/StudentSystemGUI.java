package StudentSystemGUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
import java.io.IOException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Algowihi - Alamoudi
 */
public class StudentSystemGUI extends Application {

    TextField search;
    String searchInput = "";
    Slider slider;
    TextField nameField = new TextField();
    DatePicker dateField = new DatePicker();
    static Label addMessage;
    static Label searchMessage;
    GridPane gridPane = new GridPane();
    double defaultSliderValue = 0.0;
    String sliderValue = "0.0";
    static ObservableList<Student> students = FXCollections.observableArrayList();
    boolean isSearched = false;

    @Override
    public void start(Stage primaryStage) {

        // DEFINING COMPONENTS -----------------------------------------------------------------------------------------
        TabPane tab = new TabPane();

        Tab tb1 = new Tab("add record");
        Tab tb2 = new Tab("Search");

        VBox vb1 = new VBox(15);
        vb1.setPadding(new Insets(20));
        vb1.setAlignment(Pos.CENTER);

        HBox hb1 = new HBox(15);
        hb1.setAlignment(Pos.CENTER);

        gridPane.setAlignment(Pos.CENTER);
//        gp.setGridLinesVisible(true);
        gridPane.setVgap(25);
        gridPane.setHgap(25);

        Label credet = new Label();

        Label sName = new Label("Name");

        Label date = new Label("Date");

        Label gpa = new Label("GPA");
        slider = new Slider(0, 4, defaultSliderValue);
        Label sliderLabel = new Label("Value: 0.0");

        addMessage = new Label("");
        addMessage.setFont(Font.font("", FontWeight.BOLD, 16));

        Button addBtn = new Button("ADD");
        Button exitBtn = new Button("EXIT");

        // CHANGING DETAILS OF COMPONENTS AND MODIFYING IT -----------------------------------------------------------------------------------------
        credet.setText("Ahmed Algowihi 438018480 - Ahmed Alamoudi-00000000");
        credet.setFont(Font.font("", FontWeight.BOLD, 16));

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.5);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(0.1);
        slider.setSnapToTicks(true);

        dateField.setEditable(false);

        // ADDING COMPONENTS INTO EACHOTHER AND INTO THE SCENE -----------------------------------------------------------------------------------------
        tab.getTabs().add(tb1);
        tab.getTabs().add(tb2);

        // ACTION FUNCTIONS
        addBtn.setOnAction(e -> {
            String dateInput;
            if (dateField.getValue() == null) {
                dateInput = "";
            } else {
                dateInput = dateField.getValue().toString();
            }
            String nameInput = nameField.getText();
            try {
                if (connectDB.add(nameInput, dateInput, sliderValue)) {
                    clear();
                }
            } catch (SQLException ex) {
                addMessage.setTextFill(Color.RED);
                addMessage.setText(ex.getMessage());
            } catch (IOException ex) {
                addMessage.setTextFill(Color.RED);
                addMessage.setText(ex.getMessage());
            }
        });

        exitBtn.setOnAction(e -> {
            Platform.exit();
        });

        slider.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double value = newValue.doubleValue();
                sliderValue = String.format("%,.1f", value);
                slider.setValue(Double.parseDouble(sliderValue));
                sliderLabel.setText("value: " + sliderValue);
            }
        });

        //FIRST TAB DATA
        vb1.getChildren().add(credet);
        gridPane.add(sName, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(date, 0, 1);
        gridPane.add(dateField, 1, 1);
        gridPane.add(gpa, 0, 2);
        gridPane.add(slider, 1, 2);
        vb1.getChildren().add(gridPane);
        vb1.getChildren().add(sliderLabel);
        vb1.getChildren().add(addMessage);
        hb1.getChildren().add(addBtn);
        hb1.getChildren().add(exitBtn);
        vb1.getChildren().add(hb1);
        tb1.setContent(vb1);

        //SECOND TAB DATA
        TableView<Student> tableView = new TableView<Student>();
        tableView.setMaxWidth(500);

        TableColumn col1 = new TableColumn<>("ID");
        col1.setCellValueFactory(
                new PropertyValueFactory<>("ID"));
        col1.setMinWidth(100);

        TableColumn col2 = new TableColumn<>("Full name");
        col2.setCellValueFactory(
                new PropertyValueFactory<>("FullName"));

        col2.setMinWidth(150);

        TableColumn col3 = new TableColumn<>("Date of birth");
        col3.setCellValueFactory(
                new PropertyValueFactory<>("DateOfBirth"));
        col3.setMinWidth(150);

        TableColumn col4 = new TableColumn<>("GPA");
        col4.setCellValueFactory(
                new PropertyValueFactory<>("GPA"));
        col4.setMinWidth(100);

        Text tap2Title = new Text("Searching For Students Record");
        tap2Title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label searchLabel = new Label("Search By Name:");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        searchMessage = new Label("");
        searchMessage.setFont(Font.font("", FontWeight.BOLD, 16));
        search = new TextField();
        search.setMaxWidth(200);
        Button searchBtn = new Button("Search");
        Button refreshBtn = new Button("Refresh");
        Button exitBtn2 = new Button("Exit");

        tableView.getColumns().add(col1);
        tableView.getColumns().add(col2);
        tableView.getColumns().add(col3);
        tableView.getColumns().add(col4);

        tb2.setOnSelectionChanged(e -> {                         /////////////////////////////// EDIT ME !!!!!!!!!!!!!!!!!!!
            if (isSearched) {
                connectDB.getName("");
                clear();
            }
            addMessage.setText("");
        });

        searchBtn.setOnAction(e -> {
            isSearched = true;
            searchInput = search.getText();
            connectDB.getName(searchInput);
            clear();
        });

        refreshBtn.setOnAction(e -> {
            isSearched = true;
            connectDB.getName("");
            clear();
        });

        exitBtn2.setOnAction(e -> {
            Platform.exit();
        });

        tableView.setItems(students);

        VBox vb2 = new VBox(10);
        vb2.setAlignment(Pos.CENTER);

        HBox hb2 = new HBox(15);
        hb2.setAlignment(Pos.CENTER);

        hb2.getChildren().add(searchBtn);
        hb2.getChildren().add(refreshBtn);

        vb2.setPadding(new Insets(20, 10, 20, 10));
        vb2.getChildren().addAll(tap2Title, searchLabel, search, hb2, searchMessage, tableView, exitBtn2);

        tb2.setContent(vb2);

        // SCENE Details
        Scene scene = new Scene(tab);
        primaryStage.setTitle("Student System");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);

    }

    public void clear() {
        nameField.setText("");
        dateField.setValue(null);
        slider.setValue(0.0);
        search.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
