package Information;

import java.io.Serializable;

import javax.swing.JButton;

//���� ���¸� �����ϱ� ����
public class ParkCarInfo implements Serializable { //�� ���� ������ ������ ��� ���� Ŭ���� �ۼ� , ���Ͽ� �����ؾ��ϱ� ������ ����ȭ ���ѳ��´�.

	private int parkPlaceNum; //���������� �ε����� ����
	private String carKind; //���� ������ ����
	private String carNum; //������ȣ�� ����
	private boolean memOrNotmem; //������ ������ ȸ���� �������� ��ȸ���� �������� �˰� ���ִ� ���� , ������ �� �����ؼ� ����Ǹ� ������ �� ��ݰ�꿡 ������ �ְ� �ȴ�.
								 //�����ڰ� �����ϱ⸦ �� ���, �� ������ ȸ���� �������� ��ȸ���� �������� �ν��� �Ǿ�� ����� ��Ȯ�ϰ� �ȴ�.
	private long carInTime; //carIntime.getTimeInMillis()�� �̿��� ���� �ð��� �ʷ� �޾ƿ´�
	
	public ParkCarInfo(int parkPlaceNum, String carKind, String carNum, boolean memOrNotmem, long carInTime)
	{
		//setter()�� ������ ���ÿ� �����ϰ� �ϰ� , getter()�� ���� �޼ҵ�� ����� �ٸ� ��Ű���� Ŭ�������� �����ϴ� ���� ����Ѵ�.
		this.parkPlaceNum = parkPlaceNum;
		this.carKind = carKind;
		this.carNum = carNum;
		this.memOrNotmem = memOrNotmem;
		this.carInTime = carInTime;
	}

	public int getparkPlaceNum()
	{
		return parkPlaceNum;
	}
	
	public String getcarKind() {
		return carKind;
	}

	public String getcarNum() {
		return carNum;
	}
	
	public boolean getMemOrNotmem() {
		return memOrNotmem;
	}
	
	public long getCarInTime() {
		return carInTime;
	}
}
