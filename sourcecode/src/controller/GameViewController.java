package src.controller;


import javafx.beans.property.SimpleStringProperty;
import src.entity.Player;
import src.entity.Square;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameViewController {
    private boolean run = false; 
    private boolean spread = false; 
    private GameController gameController;
    @FXML
    private AnchorPane gamePane; //AnchorPane cho màn chơi
   @FXML
    private StackPane insertNamePane; // màn nhập tên người chơi trước khi chơi
    @FXML
    private StackPane menuGamePane;
    @FXML
    private StackPane endGamePane; // màn end Game
    //element cho insert name view
    @FXML
    private TextField player1TextField;
    @FXML
    private TextField player2TextField;
    @FXML
    private Button confirmNameButton;
    //element cho game view
    @FXML
    private Label label0,label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11;

    @FXML
    private Text timeText;
    @FXML
    private GridPane squareGrid;
    @FXML
    private StackPane square0;
    @FXML
    private StackPane square1;
    @FXML
    private StackPane square2;
    @FXML
    private StackPane square3;
    @FXML
    private StackPane square4;
    @FXML
    private StackPane square5;
    @FXML
    private StackPane square6;
    @FXML
    private StackPane square7;
    @FXML
    private StackPane square8;
    @FXML
    private StackPane square9;
    @FXML
    private StackPane square10;
    @FXML
    private StackPane square11;
    @FXML
    private Arc square0Arc;
    @FXML
    private Arc square6Arc;
    @FXML
    private final List<StackPane> squares = new ArrayList<>();
    @FXML
    private Text currentPlayerText;
    @FXML
    private Label namePlayer1;
    @FXML
    private Label namePlayer2;
    @FXML
    private Label scorePlayer1;
    @FXML
    private Label scorePlayer2;
    @FXML
    private Label borrowGemPlayer1;
    @FXML
    private Label borrowGemPlayer2;
    @FXML
    private Button rightButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button spreadGemButton;
    @FXML
    private StackPane pickSquare;
    //element cho màn menu trong game
    @FXML
    private Button continuePlayButton;
    @FXML
    private Button exitGamePaneButton;

    ///element cho màn end Game
    @FXML
    private Text endText;
    @FXML
    private Label winnerLabel;
    @FXML
    private Label player1ScoreLabel;
    @FXML
    private Label player2ScoreLabel;
    private StackPane lastSelectedSquare ;// ô được chọn
    private Button lastSelectedDirection ;
    private Boolean clockwise;// chiều được chọn
    ////////
//Controller cho giao diện
////////
    @FXML
    private void initialize() {
        lastSelectedDirection=null;
        lastSelectedSquare = null;
        clockwise=null;
        pickSquare.getChildren().clear();
        squares.add(0,square0);
        squares.add(1,square1);
        squares.add(2,square2);
        squares.add(3,square3);
        squares.add(4,square4);
        squares.add(5,square5);
        squares.add(6,square6);
        squares.add(7,square7);
        squares.add(8,square8);
        squares.add(9,square9);
        squares.add(10,square10);
        squares.add(11,square11);
        for (int i=0;i<squares.size();i++) {
            squares.get(i).getChildren().removeIf(node -> node instanceof ImageView);
            if(i%6!=0) squares.get(i).setStyle("-fx-background-color: LightBlue; -fx-border-width: 1; -fx-border-color: black");

        }

        if(confirmNameButton!=null) {
            confirmNameButton.setOnAction(event -> confirmInsertName() );
        }
    }

    //Controller cho InsertName view
    @FXML
    private void confirmInsertName(){
        run = true;
        insertNamePane.setVisible(false);
        insertNamePane.setManaged(false);
        initGameView();
        gamePane.setVisible(true);
        gamePane.setManaged(true);
    }

    @FXML
    private void replayGame(){
        run =true;
        initGameView();
    }
    //Controller cho PlayView
    @FXML
      public void initGameView(){

        if(run) {
            initialize();
            endGamePane.setManaged(false);
            endGamePane.setVisible(false);
            String player1Name = player1TextField.getText();
            String player2Name = player2TextField.getText();
            gameController = new GameController(player1Name, player2Name);

            List<Square> squaresLogic = gameController.getBoard().getSquareList();
            for (int i = 0; i < squaresLogic.size(); i++) {
                StackPane square = squares.get(i);
                Square squareEntity = squaresLogic.get(i);
                deleteAllGemImageInSquare(square);
                if (i % 6 == 0) {
                    Image bigGemImage = new Image("/image/big_gem.png");
                    ImageView bigGemView = new ImageView(bigGemImage);
                    bigGemView.setFitHeight(40);
                    bigGemView.setFitWidth(40);
                    square.getChildren().add(bigGemView);
                } else {
                    for (int j = 0; j < squareEntity.getGemsQuantity(); j++) {
                        addGemImage(square, 0);
                    }
                }
            }

            currentPlayerText.setText("Player 1: " + player1Name);
            namePlayer1.setText("Player1: " + gameController.getPlayer1().getName());
            namePlayer2.setText("Player2: " + gameController.getPlayer2().getName());
            updateScoreAndBorrowGem();
        }
    }
    public void updateScoreAndBorrowGem(){
        bindLabels();
        scorePlayer1.setText("Score: "+gameController.getPlayer1().getScore());
        scorePlayer2.setText("Score: "+ gameController.getPlayer2().getScore());
        borrowGemPlayer1.setText("Borrow gems: "+ gameController.getPlayer1().getBorrowGems());
        borrowGemPlayer2.setText("Borrow gems: "+ gameController.getPlayer2().getBorrowGems());
        currentPlayerText.setText("Lượt: Player"+gameController.getCurrentPlayer().getId()+" "+gameController.getCurrentPlayer().getName());
    }
    private void bindLabels(){
        label0.setText(String.valueOf(gameController.getBoard().getSquareById(0).getScoreInSquare()));
        label1.setText(String.valueOf(gameController.getBoard().getSquareById(1).getScoreInSquare()));
        label2.setText(String.valueOf(gameController.getBoard().getSquareById(2).getScoreInSquare()));
        label3.setText(String.valueOf(gameController.getBoard().getSquareById(3).getScoreInSquare()));
        label4.setText(String.valueOf(gameController.getBoard().getSquareById(4).getScoreInSquare()));
        label5.setText(String.valueOf(gameController.getBoard().getSquareById(5).getScoreInSquare()));
        label6.setText(String.valueOf(gameController.getBoard().getSquareById(6).getScoreInSquare()));
        label7.setText(String.valueOf(gameController.getBoard().getSquareById(7).getScoreInSquare()));
        label8.setText(String.valueOf(gameController.getBoard().getSquareById(8).getScoreInSquare()));
        label9.setText(String.valueOf(gameController.getBoard().getSquareById(9).getScoreInSquare()));
        label10.setText(String.valueOf(gameController.getBoard().getSquareById(10).getScoreInSquare()));
        label11.setText(String.valueOf(gameController.getBoard().getSquareById(11).getScoreInSquare()));

    }
    @FXML
    public void deleteAllGemImageInSquare(StackPane square){
        square.getChildren().removeIf(node -> node instanceof ImageView);

    }

    @FXML
    public void addGemImage(StackPane square, int type){
        Image smallGemImage = new Image("image/small_gem.png");
        ImageView gemImage = new ImageView(smallGemImage);
        gemImage.setFitHeight(20);
        gemImage.setFitWidth(20);
        if(type==0) {
            double paneWidth = square.getWidth();
            double paneHeight = square.getHeight();
            double margin = 40;

            // Giới hạn X và Y để đảm bảo cách cạnh 40px
            double randomX = (Math.random() * (paneWidth - 2 * margin)) - (paneWidth / 2 - margin);
            double randomY = (Math.random() * (paneHeight - 2 * margin)) - (paneHeight / 2 - margin);
            gemImage.setTranslateX(randomX);
            gemImage.setTranslateY(randomY);
        }else {
            int baseAngle = 0,rootX=0,rootY=0;
            if(type==1) {
                baseAngle  = 100;

                rootX = 70;
            }
            else if(type==2){
                baseAngle = 280;
                rootX = -70;
            }
            double radiusX = 140 -20; // Bán kính X của elip
            double radiusY = 140 -20; // Bán kính Y của elip
            double angle = Math.toRadians(baseAngle + Math.random() * 150);
            // Tính tọa độ Descartes
            double randomX = rootX +Math.cos(angle) * radiusX;
            double randomY =  rootY +Math.sin(angle) * radiusY;

            // Đặt tọa độ cho hình ảnh
            gemImage.setTranslateX(randomX);
            gemImage.setTranslateY(randomY);

        }
        square.getChildren().add(gemImage);

    }
    @FXML
    public void handleSquareClick(MouseEvent event) {
       if(run&&!spread) {
           lastSelectedDirection = null;

           if (lastSelectedSquare != null) {
               resetSquareColor(lastSelectedSquare);
               lastSelectedSquare = null;
           }
           double mouseX = event.getX();
           double mouseY = event.getY();

           // Lấy thông tin hàng và cột từ tọa độ chuột
           int column = (int) (mouseX / 140);  // 50px là kích thước mỗi ô
           int row = (int) (mouseY / 140);     // 50px là kích thước mỗi ô
           StackPane[][] squareNames = {
                   {square11, square10, square9, square8, square7},
                   {square1, square2, square3, square4, square5}
           };
           if (row >= 0 && row < squareNames.length && column >= 0 && column < squareNames[row].length) {
               lastSelectedSquare = squareNames[row][column];
               String partAfterSquare = lastSelectedSquare.getId().substring("square".length()); // Lấy phần sau "square"
               Square square = gameController.getBoard().getSquareById(Integer.parseInt(partAfterSquare));

               if (square.getGemsQuantity() == 0 || !gameController.getCurrentPlayer().getPlayerSquares().contains(square)) {
                   rightButton.setVisible(false);
                   rightButton.setManaged(false);
                   leftButton.setVisible(false);
                   leftButton.setManaged(false);
                   spreadGemButton.setManaged(false);
                   spreadGemButton.setVisible(false);
               } else {
                   highlightSquare(lastSelectedSquare);
                   // Show the buttons
                   rightButton.setVisible(true);
                   rightButton.setManaged(true);
                   leftButton.setVisible(true);
                   leftButton.setManaged(true);
               }
           }


       }
    }

    private void highlightSquare(StackPane square) { 
        String id = square.getId();
        switch (id){
            case "square0":
                square0Arc.setFill(Color.YELLOW);
                break;
            case "square6":
                square6Arc.setFill(Color.YELLOW);
                break;
            default:
                square.setStyle("-fx-background-color: yellow; -fx-border-width: 1; -fx-border-color: black");
                break;
        }
    }

    private void resetSquareColor(StackPane square) {
        // Reset the color of the square back to its default color
        String id = square.getId();
        switch (id){
            case "square0":
                square0Arc.setFill(Color.DEEPSKYBLUE);
                break;
            case "square6":
                square6Arc.setFill(Color.DEEPSKYBLUE);
                break;
            default:
                square.setStyle("-fx-background-color: lightblue;-fx-border-width: 1; -fx-border-color: black");
                break;
        }
    }

    @FXML
    public void handleButtonDirectionClick(MouseEvent event) { 
        if(run) {
            // Determine which button is clicked
            Button clickedButton = (Button) event.getSource();

            if (lastSelectedDirection != null) {
                resetButtonColor(lastSelectedDirection);
            }

            // Highlight the clicked button
            highlightButton(clickedButton);

            // Save the selected button for later
            lastSelectedDirection = clickedButton;

            // Make the "Spread Gem" button visible
            spreadGemButton.setVisible(true);
            spreadGemButton.setManaged(true);
        }
    }

    private void highlightButton(Button button) { 
        // Set the color of the selected button to yellow
        button.setStyle("-fx-background-color: yellow;");
    }

    private void resetButtonColor(Button button) {
        // Reset the color of the button back to its default color
        button.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white;");
    }

    @FXML
    public void handleSpreadGemButtonClick(MouseEvent event) {
        if(run) {
            spread = true;
            handleAfterTurn();
            Button clickedButton = (Button) event.getSource();

            if (clickedButton == spreadGemButton) {
                highlightButton(clickedButton);
            }
            int pickSquareId = squares.indexOf(lastSelectedSquare);
            if (pickSquareId <= 5 && pickSquareId >= 1)
                clockwise = !Objects.equals(lastSelectedDirection.getId(), "leftButton");
            else
                clockwise = Objects.equals(lastSelectedDirection.getId(), "leftButton");
            spreadGemButton.setVisible(false);
            spreadGemButton.setManaged(false);
            leftButton.setManaged(false);
            rightButton.setManaged(false);
            leftButton.setVisible(false);
            rightButton.setVisible(false);
            resetButtonColor(clickedButton);
            resetButtonColor(lastSelectedDirection);
            resetSquareColor(lastSelectedSquare);
            spreadGem(pickSquareId, clockwise);
        }
    }
    private void spreadGem(int pickSquareId, boolean clockwise ) {
         if(run) {
             pickSquareAnimation(() -> {
                 {
                     List<Integer> spreadSquareId;
                     spreadSquareId = gameController.spreadGems(pickSquareId, clockwise);
                     spreadGemsAnimation(spreadSquareId, () -> {
                         if (lastSelectedSquare != null)
                             resetSquareColor(lastSelectedSquare);
                         if (lastSelectedDirection != null)
                             resetButtonColor(lastSelectedDirection);
                     });
                 }
             });
         }
    }

    public void pickSquareAnimation(Runnable onFinished) {
        // Tạo một Pane mới để chứa các ảnh trong hoạt ảnh
        StackPane movingPane = new StackPane();
        // Thêm movingPane vào vào cây giao diện người dùng
        gamePane.getChildren().add(movingPane);
        movingPane.toFront();
        // Lấy tất cả các ImageView con từ lastSelectedSquare
        for (var child : new ArrayList<>(lastSelectedSquare.getChildren())) {
            if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;
                // Thêm từng ImageView vào movingPane
                movingPane.getChildren().add(imageView);
            }
        }

        Bounds lastSelectedBounds = lastSelectedSquare.localToScene(lastSelectedSquare.getBoundsInLocal());
        Bounds pickSquareBounds = pickSquare.localToScene(pickSquare.getBoundsInLocal());

// Tính toán khoảng cách dịch chuyển (deltaX, deltaY) từ lastSelectedSquare đến pickSquare
        double deltaX = pickSquareBounds.getCenterX() - lastSelectedBounds.getCenterX();
        double deltaY = pickSquareBounds.getCenterY() - lastSelectedBounds.getCenterY();

// Đặt vị trí ban đầu của movingPane tại lastSelectedSquare (chú ý không trừ gamePane's layoutX và layoutY)
        movingPane.setLayoutX(lastSelectedBounds.getCenterX());
        movingPane.setLayoutY(lastSelectedBounds.getCenterY());

// Tạo hoạt ảnh cho movingPane để di chuyển đến vị trí của pickSquare
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), movingPane);
        transition.setToX(deltaX);
        transition.setToY(deltaY);

        // Lưu hoạt ảnh hoàn thành
        transition.setOnFinished(event -> {
            // Khi hoạt ảnh kết thúc, di chuyển ảnh vào pickSquare
            for (var child : movingPane.getChildren()) {
                if (child instanceof ImageView) {
                    addGemImage(pickSquare, 0);  // Thêm viên đá vào pickSquare
                }
            }
            // Xóa movingPane khỏi cây giao diện người dùng
            gamePane.getChildren().remove(movingPane);

            // Sau khi hoàn thành, gọi hàm onFinished
            onFinished.run();
        });

        // Chạy hoạt ảnh di chuyển pane
        transition.play();
    }

    public void spreadGemsAnimation(List<Integer> targetSquareIds,Runnable onFinished) {
        // Tạo danh sách các ImageView từ lastSelectedSquareSy
    if(run) {
        List<ImageView> gemsToMove = new ArrayList<>();
        for (var child : pickSquare.getChildren()) {
            if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;
                gemsToMove.add(imageView);

            }
        }

        moveGemsRecursively(pickSquare,gemsToMove, targetSquareIds, 0, onFinished);

    }
    }

    private void moveGemsRecursively(StackPane square,List<ImageView> gemsToMove, List<Integer> targetSquareIds, int index, Runnable onFinished) {

        if(run) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(400), event -> {
                        if (index >= gemsToMove.size()) {
                            bindLabels();
                            int currentIndex = targetSquareIds.getLast();
                            int count = 0;
                            square.getChildren().removeIf(node -> node instanceof ImageView);
                            handleAfterSpreadGem(currentIndex,count);
                            spread = false;
                            onFinished.run();
                            return; // Thoát khi đã xử lý hết các viên đá
                        }

                        ImageView gem = gemsToMove.get(index);
                        int targetSquareId = targetSquareIds.get(index);  // Lấy ID ô đích tương ứng
                        StackPane targetSquare = squares.get(targetSquareId);

                        // Tạo hoạt ảnh di chuyển cho viên đá
                        TranslateTransition transition = new TranslateTransition(Duration.millis(400), gem);

                        // Lấy tọa độ của pickSquare và targetSquare trong hệ tọa độ của Scene
                        Bounds pickBounds = pickSquare.localToScene(pickSquare.getBoundsInLocal());
                        Bounds targetBounds = targetSquare.localToScene(targetSquare.getBoundsInLocal());

                        // Tính khoảng cách delta giữa pickSquare và targetSquare
                        double deltaX = targetBounds.getCenterX() - pickBounds.getCenterX();
                        double deltaY = targetBounds.getCenterY() - pickBounds.getCenterY();

                        // Set chuyển động (di chuyển gem từ vị trí cũ đến vị trí mới trong Parent)
                        transition.setByX(deltaX);
                        transition.setByY(deltaY);

                        // Khi hoạt ảnh hoàn thành, xử lý xóa và thêm viên đá vào ô đích
                        transition.setOnFinished(event1 -> {
                            transition.stop();
                            if(pickSquare.getChildren()!=null) pickSquare.getChildren().removeFirst();
                            if (targetSquare == square0) {
                                addGemImage(targetSquare, 1);
                            } else if (targetSquare == square6) {
                                addGemImage(targetSquare, 2);
                            } else {
                                addGemImage(targetSquare, 0);
                            }
                            // Dừng 2 giây trước khi thực hiện lần lặp tiếp theo
                            PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
                            pause.setOnFinished(e -> moveGemsRecursively(square,gemsToMove, targetSquareIds, index + 1, onFinished));
                            pause.play();
                        });
                        if(run) transition.play(); // Bắt đầu hoạt ảnh
                    }));
            timeline.play();

        }
    }
    public void handleAfterSpreadGem(int index,int count) {

        int nextLastIndex = gameController.getNextSquareIndex(index, clockwise);
        int response = gameController.handleNextEndSquare(nextLastIndex, clockwise);
        System.out.println("bbb" + count);

        if (response == 0) {
            count++;
            int nextTwoLastIndex = gameController.getNextSquareIndex(nextLastIndex, clockwise);
            squares.get(nextTwoLastIndex).getChildren().removeIf(node -> node instanceof ImageView);
            handleAfterSpreadGem(nextTwoLastIndex, count);
        } else {
            if (count == 0) {
                if (response == 1) {
                    lastSelectedSquare = squares.get(nextLastIndex);
                    spreadGem(nextLastIndex, clockwise);
                    updateScoreAndBorrowGem();
                } else {

                    gameController.switchTurn();
                    handleAfterTurn();

                    updateScoreAndBorrowGem();
                }
            } else {
                gameController.switchTurn();
                handleAfterTurn();
                updateScoreAndBorrowGem();
            }
        }

    }
    public void handleAfterTurn() {
        if(gameController.isGameOver())  {
            endText.setText("2 ô quan đã trống! Trò chơi kết thúc!\n Kết quả:\n");
            endGame();
        }
        else {
            switch (gameController.handleRefillGems()) {
                case 2:
                    endText.setText("Đối phương không đủ đá cho mượn! Trò chơi kết thúc!\n Kết quả:\n ");
                    endGame();
                    break;
                case 1:
                    refillGems(gameController.getCurrentPlayer());
                    break;
            }
        }
    }

    @FXML
    public void refillGems( Player currentPlayer) {
        if(currentPlayer.getId()==1){
            for(int i=1;i<=5;i++){
                addGemImage(squares.get(i),0);
            }
        }else {
            for(int i=7;i<=11;i++){
                addGemImage(squares.get(i),0);
            }
        }
        updateScoreAndBorrowGem();
    }

///////////////////////////////////////////
///Controller cho màn menu trong game
//////////////////////////////////////////
@FXML
    public void handleMenuButton(MouseEvent event){
        menuGamePane.setVisible(true);
        menuGamePane.setManaged(true);
    }
@FXML
    public void handleContinueButton(MouseEvent event){
        menuGamePane.setVisible(false);
        menuGamePane.setManaged(false);
    }
@FXML
    public void handleExitGameButton(MouseEvent event){
        gamePane.setManaged(false);
        gamePane.setVisible(false);
        menuGamePane.setVisible(false);
        menuGamePane.setManaged(false);
        loadHomeView();
        run = false;
    }
    ////////
//Controller cho màn endGame
////////
   @FXML
    public void endGame(){
        gameController.endGame();
        endGamePane.setVisible(true);
        endGamePane.setManaged(true);
        player1ScoreLabel.setText("Điểm của player1: "+gameController.getPlayer1().getScore());
        player2ScoreLabel.setText("Điểm của player2: "+gameController.getPlayer2().getScore());
        if(gameController.getPlayer1().getScore()>gameController.getPlayer2().getScore()){
            winnerLabel.setText("Người chiến thắng là: Player1 "+gameController.getPlayer1().getName());
        }else {
            if(gameController.getPlayer2().getScore()>gameController.getPlayer1().getScore()){
                winnerLabel.setText("Người chiến thắng là: Player2 "+gameController.getPlayer2().getName());
            }
            else {
                winnerLabel.setText("Hòa");
            }
        }
        run =false;
   }
   @FXML
    public void exitEndGame(){ 
        gamePane.setVisible(false);
        gamePane.setManaged(false);
        endGamePane.setVisible(false);
        endGamePane.setManaged(false);
        loadHomeView();
        run=false;
    }
    // Về lại màn hình chính khi dừng cuộc chơi
    @FXML
     public void loadHomeView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/view/HomeView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) gamePane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


