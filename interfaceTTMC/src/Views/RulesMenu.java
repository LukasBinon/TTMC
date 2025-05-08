package Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RulesMenu {

    private static final String FONT_PATH = "/fonts/PressStart2P-Regular.ttf";
    private static final String BG_IMAGE_PATH = "/images/menuTTMC.png";

    private StackPane mainLayout;
    private Button backButton;

    public RulesMenu() {
    	
        // load bg image 
        Image backgroundImage = new Image(getClass().getResourceAsStream(BG_IMAGE_PATH));
        BackgroundImage bg = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );

        // title
        Text title = new Text("Game Rules");
        title.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
        title.setFill(Color.YELLOW);
        title.setStroke(Color.RED);
        title.setStrokeWidth(2);
        title.setEffect(new DropShadow(15, Color.RED)); 

        // rules section
        VBox rulesBox = new VBox(10,
            createLabel("Basic Game Rules:", 25, Color.YELLOW),
            createLabel("- Each player draws a theme card", 18, Color.WHITE),
            createLabel("- Choose difficulty level (1-4)", 18, Color.WHITE),
            createLabel("- Correct answer: move forward", 18, Color.WHITE),
            createLabel("- Wrong answer: stay in place", 18, Color.WHITE),
            new Label(""),
            createLabel("Special Rules:", 25, Color.YELLOW),
            createLabel("- Hard mode: wrong answer = move backward", 18, Color.WHITE),
            createLabel("- Strategic risk/reward element", 18, Color.WHITE)
        );
        rulesBox.setAlignment(Pos.CENTER); // center

        
        backButton = createButton("Back to Menu");

        //main vertical box
        VBox content = new VBox(30, title, rulesBox, backButton);
        content.setAlignment(Pos.CENTER);
        // add spacing 
        content.setPadding(new Insets(50)); 

        //dark overlay for reading 
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

       
        StackPane backgroundStack = new StackPane(overlay);
        backgroundStack.setBackground(new Background(bg));

        mainLayout = new StackPane(backgroundStack, content);
    }

    
    private Label createLabel(String text, int size, Color color) {
        Label label = new Label(text);
        label.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), size));
        label.setTextFill(color);
        label.setWrapText(true); 
        return label;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 25));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        //hover effect 
        button.setOnMouseEntered(e -> {
            button.setEffect(new DropShadow(10, Color.YELLOW));
            button.setTextFill(Color.RED);
        });
        button.setOnMouseExited(e -> {
            button.setEffect(null);
            button.setTextFill(Color.WHITE);
        });

        return button;
    }


    public StackPane getMainLayout() {
        return mainLayout;
    }


    public Button getBackButton() {
        return backButton;
    }
}
