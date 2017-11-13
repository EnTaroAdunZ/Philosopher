package main.java.pers.ztf.tp.structure;

import java.util.Arrays;

/**
 * 
* @Description: 叉子类
* @author ZTF
* @date 2017年11月7日 上午10:42:31 
* @version V1.0
 */
public class Forks {
	//叉子的使用情况,相当于信号量
	private Boolean[] semaphore = {false, false, false, false, false};

	public Boolean[] getSemaphore() {
		return semaphore;
	}

	/**
	 * 
	* @Title: reSet 
	* @Description: 初始化叉子
	* @return void
	* @throws
	 */
	public void reSet() {
		for(int i=0;i<semaphore.length;i++) {
			semaphore[i]=false;
		}
	}

	@Override
	public String toString() {
		return "Chopsticks [semaphore=" + Arrays.toString(semaphore) + "]";
	}
	
	
	
}
