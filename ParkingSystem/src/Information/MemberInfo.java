package Information;

import java.io.Serializable;

public class MemberInfo implements Serializable{ //ȸ�������� �����ϱ� ���� Ŭ���� , ����ȭ�Ͽ� �� ��ü���� ���Ͽ� �����ϱ� ����!

	private int memCheck; //�����ڿ� ���� �����ϱ� ���� ������ �����ڴ� 1, ȸ���� 2, ��ȸ���� 3���� ����ȴ�.
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

	public int getMemCheck() { //�ܺ���Ű�� Ŭ�������� ���� �������� �ʹٸ� ���� Ŭ������ get()�޼ҵ带 ȣ���Ͽ� ���� �޾ư����Ѵ�.
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
	
	public void serParkCount(int parkCount) { //������� �� �� ȸ���� ������ �� ���¶�� �� �ν��ϱ� ���� ���� , ������� �� ���� 1, ��������� ���°� �ƴ� �� 0
		this.parkCount = parkCount;
	}
	
	public int gerParkCount()
	{
		return parkCount;
	}
}
