package main.java.pers.ztf.tp.controller;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import main.java.pers.ztf.tp.global.ThreadStatus;
import main.java.pers.ztf.tp.load.LoadView;
import main.java.pers.ztf.tp.structure.Forks;
import main.java.pers.ztf.tp.structure.LogText;
import main.java.pers.ztf.tp.structure.Philosopher;

/**
* @Description: 界面控制器
* @author ZTF
* @date 2017年11月7日 上午10:18:16 
* @version V1.0
 */
public class MainController implements Initializable{
	//以下均为界面中的控件
	@FXML
	private ImageView image_Philosopher1;
	@FXML
	private ImageView image_Philosopher2;
	@FXML
	private ImageView image_Philosopher3;
	@FXML
	private ImageView image_Philosopher4;
	@FXML
	private ImageView image_Philosopher5;
	@FXML
	private TableColumn col_log;
	@FXML
	private TableView tb_log;
	@FXML
	private Text text_1;
	@FXML
	private Text text_2;
	@FXML
	private Text text_3;
	@FXML
	private Text text_4;
	@FXML
	private Text text_5;
	
	private LoadView application;
	//哲学家类
	private Philosopher philosopher1;
	private Philosopher philosopher2;
	private Philosopher philosopher3;
	private Philosopher philosopher4;
	private Philosopher philosopher5;
	//叉子类
	private Forks forks;
	//公平锁，实现队列同步
	private ReentrantLock getLock;
	private ReentrantLock backLock;
	private ReentrantLock logLock;
	
	/**
	 * 
	* @Title: setApp 
	* @Description: application传入，用于回调
	* @param application
	* @return void
	* @throws
	 */
	public void setApp(LoadView application) {
		this.application = application;
	}
	
	/**
	 * 改变哲学家头像下的文字
	* @Title: changeLabel 
	* @Description: TODO
	* @param status
	* @param lab
	* @return void
	* @throws
	 */
	private void changeText(ThreadStatus status, Text text) {
		if(status==ThreadStatus.Eatting) {
			text.setText("用餐中");
		}else if(status==ThreadStatus.Think){
			text.setText("思考中");
		}else if(status==ThreadStatus.Hunger_Both) {
			text.setText("等待左手边以及右手边的叉子");
		}else if(status==ThreadStatus.Hunger_LEFT) {
			text.setText("等待左手边的叉子");
		}else if(status==ThreadStatus.Hunger_RIGHT) {
			text.setText("等待右手边的叉子");
		}
	}


	/**
	 * 控制器自带初始化
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		forks=new Forks();
		getLock=new ReentrantLock();
		backLock=new ReentrantLock();
		logLock=new ReentrantLock();
		col_log.setCellValueFactory(new PropertyValueFactory("text"));// 映射
		ObservableList<LogText> list =FXCollections.observableArrayList();
		list.addAll(new LogText("等待开始中..."));
		tb_log.setItems(list);
	}                                                           
	
	/**
	* @Title: enterRoom 
	* @Description: 点击开始后的操作
	* @param event
	* @return void
	* @throws
	 */
	@FXML
	public void enterRoom(ActionEvent event) {
		getLock=new ReentrantLock();
		backLock=new ReentrantLock();
		philosopher1=new Philosopher(0,forks,getLock,backLock,this);
		philosopher2=new Philosopher(1,forks,getLock,backLock,this);
		philosopher3=new Philosopher(2,forks,getLock,backLock,this);
		philosopher4=new Philosopher(3,forks,getLock,backLock,this);
		philosopher5=new Philosopher(4,forks,getLock,backLock,this);
		philosopher1.start();
		philosopher2.start();
		philosopher3.start();
		philosopher4.start();
		philosopher5.start();
	}
	
	/**
	* @Title: pause 
	* @Description:点击暂停 
	* @param event
	* @throws InterruptedException
	* @return void
	* @throws
	 */
	@SuppressWarnings("deprecation")
	@FXML
	public void pause(ActionEvent event) throws InterruptedException {
		philosopher1.suspend();
		philosopher2.suspend();
		philosopher3.suspend();
		philosopher4.suspend();
		philosopher5.suspend();
	}
	
	/**
	* @Title: restore 
	* @Description: 点击恢复
	* @param event
	* @return void
	* @throws
	 */
	@SuppressWarnings("deprecation")
	@FXML
	public void restore(ActionEvent event) {
		philosopher1.resume();
		philosopher2.resume();
		philosopher3.resume();
		philosopher4.resume();
		philosopher5.resume();
	}
	
	/**
	* @Title: stop 
	* @Description: 点击停止
	* @param event
	* @return void
	* @throws
	 */
	@SuppressWarnings("deprecation")
	@FXML
	public void stop(ActionEvent event) {
		philosopher1.stop();
		philosopher2.stop();
		philosopher3.stop();
		philosopher4.stop();
		philosopher5.stop();
		forks.reSet();
		getLock=null;
		backLock=null;
		for(int i=0;i<5;i++)
		changeImg(i,ThreadStatus.Think );
		ObservableList<LogText> list = tb_log.getItems();
		list.clear();
		list.addAll(new LogText("等待开始中..."));
		tb_log.setItems(list);
	}
	
	/**
	 *
	* @Title: changeImg 
	* @Description: 改变哲学家头像
	* @param id
	* @param status
	* @return void
	* @throws
	 */
	public void changeImg(int id,ThreadStatus status) {
		switch (id) {
		case 0:
			changeImg(id,status,image_Philosopher1);
			changeText(status,text_1);
			break;
		case 1:
			changeImg(id,status,image_Philosopher2);
			changeText(status,text_2);
			break;
		case 2:
			changeImg(id,status,image_Philosopher3);
			changeText(status,text_3);
			break;
		case 3:
			changeImg(id,status,image_Philosopher4);
			changeText(status,text_4);
			break;			
		case 4:
			changeImg(id,status,image_Philosopher5);
			changeText(status,text_5);
			break;		
		default:
			break;
		}
	}

	private void changeImg(int id,ThreadStatus status, ImageView image_Philosopher) {
		if(status==ThreadStatus.Eatting) {
			
			image_Philosopher.setImage(new Image("/main/resource/image/eatting.png"));
		}else if(status==ThreadStatus.Think){
			image_Philosopher.setImage(new Image("/main/resource/image/Philosopher"+(id+1)+".png"));
		}else if(status==ThreadStatus.Hunger_Both||status==ThreadStatus.Hunger_LEFT||status==ThreadStatus.Hunger_RIGHT) {
			image_Philosopher.setImage(new Image("/main/resource/image/waitting.png"));
		}
	}
	
	/**
	 *
	* @Title: addLog 
	* @Description: 添加日志到日志板
	* @param text
	* @return void
	* @throws
	 */
	public void addLog(String text) {
		logLock.lock();
		ObservableList list = tb_log.getItems();
		list.addAll(new LogText(text));
		tb_log.setItems(list);
		logLock.unlock();
	}
	

}
