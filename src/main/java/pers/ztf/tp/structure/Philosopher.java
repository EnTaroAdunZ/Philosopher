package main.java.pers.ztf.tp.structure;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import main.java.pers.ztf.tp.controller.MainController;
import main.java.pers.ztf.tp.global.ThreadStatus;
import main.java.pers.ztf.tp.global.TimeSetting;

/**
 * 
* @Description: 哲学家类，包含与哲学家有关的数据以及操作
* @author ZTF
* @date 2017年11月7日 上午10:47:30 
* @version V1.0
 */
public class Philosopher extends Thread{
	
	private int id;
	Random random = new Random();
	private Forks chopsticks;
	PCB pcb;
	ReentrantLock getLock;
	ReentrantLock backLock;
	private volatile boolean isPause;
	private MainController mainController;
	
	/**
	 * 初始化状态
	 * @param id
	 * @param chopsticks
	 * @param getLock
	 * @param backLock
	 * @param mainController
	 */
	public Philosopher(int id,Forks chopsticks,ReentrantLock getLock,ReentrantLock backLock,MainController mainController)
	{
		super();
		this.id=id;
		this.chopsticks=chopsticks;
		pcb=new PCB(ThreadStatus.INIT, id);
		this.getLock=getLock;
		this.backLock=backLock;
		isPause=false;
		this.mainController=mainController;
	}
	
	@Override
	public void run() {	
		while(true) {
			if(!isPause) {
				int choose=random.nextInt(2); 
				if(choose==1) {
					thinking();
				}else {
					semWait();
					eatting();
					semSignal();
				}
			}else {
				try {
					sleep(300);
				} catch (InterruptedException e) {
					break;
				}
			}

		}
	}
	
	/**
	 * 
	* @Title: semWait 
	* @Description:获取叉子，否则阻塞
	* @return void
	* @throws
	 */
	private void semWait() {
		  getLock.lock();
			while(chopsticks.getSemaphore()[id]||chopsticks.getSemaphore()[(id+1)%5]) {
				try {
					if(chopsticks.getSemaphore()[id]&&chopsticks.getSemaphore()[(id+1)%5]) {
						pcb.setThreadStatus(ThreadStatus.Hunger_Both);
						mainController.addLog("哲学家"+id+"缺少左手以及右手边的筷子");
					}
					else if(chopsticks.getSemaphore()[id]) {
						pcb.setThreadStatus(ThreadStatus.Hunger_LEFT);
						mainController.addLog("哲学家"+id+"缺少左手边的筷子");
//						System.out.println(id+"缺少"+id+"的筷子");
					}else if(chopsticks.getSemaphore()[(id+1)%5]){
						pcb.setThreadStatus(ThreadStatus.Hunger_RIGHT);
						mainController.addLog("哲学家"+id+"缺少右手边的筷子");
					}
					mainController.changeImg(this.id, pcb.getThreadStatus());
					
//					System.out.println("哲学家"+id+"：正在等待筷子"+id+"和筷子"+((id+1)%5));
					sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			mainController.addLog("哲学家"+id+"：拿到筷子"+id+"和筷子"+((id+1)%5));
//			System.out.println("哲学家"+id+"：拿到筷子"+id+"和筷子"+((id+1)%5));
			chopsticks.getSemaphore()[id]=true;
			chopsticks.getSemaphore()[(id+1)%5]=true;
			getLock.unlock();
	}

	/**
	 *
	* @Title: semSignal 
	* @Description:  放下叉子
	* @return void
	* @throws
	 */
	private void semSignal() {
		    backLock.lock();
			chopsticks.getSemaphore()[id]=false;
			chopsticks.getSemaphore()[(id+1)%5]=false;
			mainController.addLog("哲学家"+id+"放下了筷子"+id+":"+((id+1)%5));
//			System.out.println(id+"放下了筷子"+id+":"+((id+1)%5));
			pcb.setThreadStatus(ThreadStatus.Think);
			mainController.changeImg(this.id, pcb.getThreadStatus());
			backLock.unlock();
	}

	/**
	 * 
	* @Title: eatting 
	* @Description: 吃饭状态，需要时间
	* @return void
	* @throws
	 */
	private void eatting() {
		int costTime=random.nextInt(TimeSetting.EATTING_PLUS_TIME);
		pcb.setThreadStatus(ThreadStatus.Eatting);
		mainController.changeImg(this.id, pcb.getThreadStatus());
		mainController.addLog("哲学家"+id+"：拿到筷子，开始吃饭！ ");
//		System.out.println("哲学家"+id+"：拿到筷子，开始吃饭！ ");
		try {
			sleep(costTime+TimeSetting.EATTING_MIN_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mainController.addLog("哲学家"+id+"：吃饱了，准备放下筷子 ");
//		System.out.println("哲学家"+id+"：吃饱了，准备放下筷子 ");
	}

	/**
	 * 
	* @Title: thinking 
	* @Description: 思考状态，需要时间
	* @return void
	* @throws
	 */
	private void thinking() {
		int costTime=random.nextInt(TimeSetting.THINKING_PLUS_TIME);
		pcb.setThreadStatus(ThreadStatus.Think);
		mainController.addLog("哲学家"+id+"：开始思考了！");
//		System.out.println("哲学家"+id+"：开始思考了！");
		mainController.changeImg(this.id, pcb.getThreadStatus());
		try {
			sleep(costTime+TimeSetting.THINKING_MIN_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainController.addLog("哲学家"+id+"：思考完毕，准备下一步行动！");
//		System.out.println("哲学家"+id+"：思考完毕，准备下一步行动！");
	}
	
	/**
	 * 
	* @Title: pause 
	* @Description: 暂停，暂时不使用
	* @return void
	* @throws
	 */
	public void pause() {
		this.isPause=true;
	}
	/**
	 * 
	* @Title: restore 
	* @Description: 恢复，暂时不使用
	* @return void
	* @throws
	 */
	public void restore() {
		this.isPause=false;
	}
	
}
