package Information;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ParkFeeInfo implements Serializable {
	private int fee;
	private Date time; //현재 시간을 Date 클래스를 이용해서 받아온다.
	
	public ParkFeeInfo (int fee, Date time)
	{
		this.fee = fee;
		this.time = time;
	}

	public int getFee() {
		return fee;
	}

	public Date getTime() {
		return time;
	}
}
