package main.java.pers.ztf.tp.structure;

import main.java.pers.ztf.tp.global.ThreadStatus;

/**
 * 
* @Description: 简化版PCB，记录了当前进程状态，以及ID
* @author ZTF
* @date 2017年11月7日 上午10:45:23 
* @version V1.0
 */
public class PCB {
	//包含进程状态以及当前阻塞原因
	private ThreadStatus threadStatus;
	private int id;
	public PCB(ThreadStatus threadStatus, int id) {
		super();
		this.threadStatus = threadStatus;
		this.id = id;
	}
	public ThreadStatus getThreadStatus() {
		return threadStatus;
	}
	public void setThreadStatus(ThreadStatus threadStatus) {
		this.threadStatus = threadStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
