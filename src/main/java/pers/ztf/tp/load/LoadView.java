package main.java.pers.ztf.tp.load;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.java.pers.ztf.tp.controller.MainController;
import main.java.pers.ztf.tp.global.ThreadStatus;


public class LoadView extends Application{
	
	private Stage stage;  
    public Stage getStage() {
    	return stage;
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;  
        stage.setTitle("题目三 线程的同步与互斥 by 201525010627"); 
        initView();
        stage.show();  
	}
	
	/**
	 * 
	* @Title: initView 
	* @Description: 初始化界面
	* @return void
	* @throws
	 */
	private void initView() {
	       try {  
	        	MainController mainController=(MainController) replaceSceneContent("/main/resource/fxml/FXML_MAIN.fxml");  
	        	mainController.setApp(this);
	          } catch (Exception ex) {  
	              Logger.getLogger(LoadView.class.getName()).log(Level.SEVERE, null, ex);  
	          }  
	}

	public static void main(String[] args) {
        launch(args);  
        System.exit(0);
	}
	
	/**
	 * 
	* @Title: replaceSceneContent 
	* @Description: 替换主界面
	* @param fxml
	* @return
	* @throws Exception
	* @return Initializable
	* @throws
	 */
    private Initializable replaceSceneContent(String fxml) throws Exception {  
        FXMLLoader loader = new FXMLLoader();  
        InputStream in = LoadView.class.getResourceAsStream(fxml);  
        loader.setBuilderFactory(new JavaFXBuilderFactory());  
        loader.setLocation(LoadView.class.getResource(fxml));  
        GridPane page;  
        try {  
            page = (GridPane) loader.load(in);  
        } finally {  
            in.close();  
        }   
        Scene scene = new Scene(page);  
        stage.setScene(scene);  
        stage.sizeToScene();  
        return (Initializable) loader.getController();  
    }  
	

}
