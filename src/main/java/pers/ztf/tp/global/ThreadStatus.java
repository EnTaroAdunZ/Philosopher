package main.java.pers.ztf.tp.global;

/**
 * 
* @Description: 线程状态枚举类
* @author ZTF
* @date 2017年11月7日 上午10:39:21 
* @version V1.0
 */
public enum ThreadStatus {
	 INIT("哲学家初始状态"),
	 Think("哲学家正在思考"),
	 Eatting("哲学家正在吃饭"),
	 Hunger_LEFT("由于缺少左手边筷子而处于阻塞状态"),
	 Hunger_RIGHT("由于缺少右手边筷子而处于阻塞状态"),
	 Hunger_Both("由于缺少左以及右手边的筷子而处于阻塞状态");
	 private String reason ;

	private ThreadStatus(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	 
}
