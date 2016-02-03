package Information;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ParkFeeInfo implements Serializable {
	private int fee;
	private Date time; //���� �ð��� Date Ŭ������ �̿��ؼ� �޾ƿ´�.
	
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
