package Information;

import java.io.Serializable;

import javax.swing.JButton;

//현재 상태를 유지하기 위한
public class ParkCarInfo implements Serializable { //각 주차 차량의 정보를 담기 위한 클래스 작성 , 파일에 저장해야하기 떄문에 직렬화 시켜놓는다.

	private int parkPlaceNum; //주차공간의 인덱스를 저장
	private String carKind; //차량 종류를 저장
	private String carNum; //차량번호를 저장
	private boolean memOrNotmem; //입차한 차량이 회원의 차량인지 비회원의 차량인지 알게 해주는 변수 , 입차할 때 구분해서 저장되며 출차할 때 요금계산에 영향을 주게 된다.
								 //관리자가 출차하기를 할 경우, 그 차량이 회원의 차량인지 비회원의 차량인지 인식이 되어야 계산이 정확하게 된다.
	private long carInTime; //carIntime.getTimeInMillis()를 이용해 현재 시간을 초로 받아온다
	
	public ParkCarInfo(int parkPlaceNum, String carKind, String carNum, boolean memOrNotmem, long carInTime)
	{
		//setter()는 생성과 동시에 진행하게 하고 , getter()만 따로 메소드로 만들어 다른 패키지의 클래스에서 접근하는 것을 허용한다.
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
