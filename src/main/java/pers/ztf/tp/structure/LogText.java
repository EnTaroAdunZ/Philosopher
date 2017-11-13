package main.java.pers.ztf.tp.structure;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * 
* @Description: 日志类，用于映射控件
* @author ZTF
* @date 2017年11月7日 上午10:44:55 
* @version V1.0
 */
public class LogText {
	private StringProperty text;
	public LogText(String text) {
		this.text=new SimpleStringProperty(text);
	}
	public String getText() {
		return text.get();
	}
	public void setText(String grade) {
		this.text.set(grade);
	}
}
