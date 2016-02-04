package Information;

import java.io.Serializable;

public class MemberInfo implements Serializable{ //회원정보를 저장하기 위한 클래스 , 직렬화하여 이 객체들을 파일에 저장하기 위함!

	private int memCheck; //관리자와 고객을 구분하기 위한 변수로 관리자는 1, 회원은 2, 비회원은 3으로 저장된다.
	private String id;
	private String password;
	private String name;
	private String phone;
	private String email;
	private int parkCount;
	
	public MemberInfo (int memCheck, String id, String password, String name, String phone, String email)
	{
		this.memCheck = memCheck;
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public int getMemCheck() { //외부패키지 클래스에서 값을 가져가고 싶다면 현재 클래스의 get()메소드를 호출하여 값을 받아가야한다.
		return memCheck;
	}
	
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}
	
	public void serParkCount(int parkCount) { //주차등록 시 이 회원이 주차를 한 상태라는 걸 인식하기 위한 변수 , 주차등록 시 값을 1, 주차등록한 상태가 아닐 시 0
		this.parkCount = parkCount;
	}
	
	public int gerParkCount()
	{
		return parkCount;
	}
}
