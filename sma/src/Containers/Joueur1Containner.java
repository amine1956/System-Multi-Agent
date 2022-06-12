package Containers;

import agent.Joueur1;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;


public class Joueur1Containner extends Application {

    static Joueur1 joueur1;
    public ObservableList<String> items = FXCollections.observableArrayList();

    public void setAgent(Joueur1 joueur1) {
        this.joueur1 = joueur1;
    }

    public Joueur1 getPlayer1() {
        return joueur1;
    }

    public static void main(String[] args) throws ControllerException {
        Joueur1Containner joueur1Containner = new Joueur1Containner();
        joueur1Containner.startContainer();

        launch(args);
    }

    public void startContainer() throws ControllerException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST, "localhost");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController agentController = agentContainer.createNewAgent("joueur1", "agent.Joueur1", new Object[]{this});
        agentController.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ////////////////////////////////////////////////////////////
        BorderPane root = new BorderPane();
        // Set the Size of the VBox
        root.setPrefSize(400, 400);
        // Set the Style-properties of the BorderPane
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: #ff0000;");

        root.setPadding(new Insets(15, 20, 10, 10));

        HBox hBox = new HBox();
        hBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        Label label = new Label("entrer une valeur");
        label.setPadding(new javafx.geometry.Insets(0, 10, 0, 10));
        TextField textField = new TextField();
        Button button = new Button("envoyer");

        hBox.getChildren().addAll(label, textField, button);
        root.setTop(hBox);

        ListView<String> listView = new ListView<>();

        listView.setItems(items);

        VBox vBox = new VBox();
        vBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(listView);
        root.setCenter(vBox);

        Scene scene = new Scene(root, 600, 400);
        scene.setFill(new RadialGradient(
                0, 0, 0, 0, 1, true,                  //sizing
                CycleMethod.NO_CYCLE,                 //cycling
                new Stop(0, Color.web("#81c483")),    //colors
                new Stop(1, Color.web("#fcc200")))
        );
        primaryStage.setScene(scene);
        primaryStage.setTitle("joueur1");
        primaryStage.show();

        button.setOnAction(event -> {
            String message = textField.getText();
            GuiEvent guiEvent = new GuiEvent(event, 0);
            guiEvent.addParameter(message);
            guiEvent.addParameter("Servere");
            guiEvent.addParameter(listView);
            joueur1.onGuiEvent(guiEvent);
        });
    }
}
