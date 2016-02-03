package FileManeger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Information.ParkCarInfo;
import Information.ParkFeeInfo;
import Information.MemberInfo;
import MainFunctionView.ParkingCarIn;
import MainFunctionView.ParkingCarOut;
import MainFunctionView.ParkingNewMemJoin;

public class FileSystem { //���� ����, ����� static���� �����ؼ� (Ŭ������.�Լ���)�� ���� ȣ���ؼ� ����� �� �ֵ��� ����ó���Ѵ�.
	static FileOutputStream fos; //���Ͽ� ������ ���� ���� fos�� oos
	static ObjectOutputStream oos;
	
	static FileInputStream fis; //������ ������ �о���� ���� fis�� ois
	static ObjectInputStream ois;
	
	//�� ArrayList���� ������ ���� ����� �����ϰ� �ȴ�. �����ؾ� �� ���� ���Ͽ� �� ArrayList�ȿ� �ִ� ������ ���� ������ ���� Ŭ������ ��ü��� ����ȭ�ؾ� �Ѵٴ� ���̴�.
	//(implements Serializable) <--- �̰��� Ŭ������ ������� ����ȭ�� �ȴ�.
	
	public static void saveCarInfo(ArrayList<ParkCarInfo> parkDataList) //�������� ������ ���� ����Ʈ�� ���Ͽ� �����Ѵ�.
	{
		try {		
			fos = new FileOutputStream("CarInfo.txt");
			oos = new ObjectOutputStream(fos);
		
			oos.writeObject(parkDataList);
			oos.close();
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public static void loadCarInfo () //���������� ������ ���� ������ ������ �ٽ� ����Ʈ�� �� �����Ѵ�.
	{
		try {
			fis = new FileInputStream("CarInfo.txt");
			ois = new ObjectInputStream(fis);
			
			ParkingCarIn.parkCarList = (ArrayList<ParkCarInfo>) ois.readObject();
			ois.close();
		} catch (Exception ae) { ae.printStackTrace();}
	}
	
	public static void saveMemberInfo (ArrayList<MemberInfo> memList) //ȸ�������� ���� ����Ʈ�� ���Ͽ� ��� �����Ѵ�.
	{
		try {
			fos = new FileOutputStream("MemberInfo.txt");
			oos = new ObjectOutputStream(fos);
		
			oos.writeObject(memList); //MemberInfo�� ����ȭ�Ǿ��ֱ� ������ ���Ͽ� ����Ʈ �״�� ������ �����ϴ�.
			oos.close();
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public static void loadMemberInfo () //ȸ�������� ���� ������ ������ �ٽ� ����Ʈ�� �� �����Ѵ�.
	{
		try {
			fis = new FileInputStream("MemberInfo.txt");
			ois = new ObjectInputStream(fis);
			
			ParkingNewMemJoin.memList = (ArrayList<MemberInfo>) ois.readObject(); //����ȭ�Ǿ��ִ� ������ �ٽ� �о�ͼ� ����Ʈ�� �����Ѵ�.
			ois.close();
		} catch (Exception ae) { ae.printStackTrace();}
	}
	
	public static void saveParkInfo (ArrayList<String> parkList) //���� ������ ���� parkList(ArrayList)�� ���Ͽ� �����Ѵ�.
	{
		try {
			fos = new FileOutputStream("parkListInfo.txt");
			oos = new ObjectOutputStream(fos);
		
			oos.writeObject(parkList); //
			oos.close();
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public static void loadParkListInfo () //���������� ��� ������ ������ �о�ͼ� �ٽ� parkList(ArrayList)�� �����Ѵ�.
	{
		try {
			fis = new FileInputStream("parkListInfo.txt");
			ois = new ObjectInputStream(fis);
			
			ParkingCarIn.parkPrintList = (ArrayList<String>) ois.readObject(); //
			ois.close();
		} catch (Exception ae) { ae.printStackTrace();}
	}
	
	public static void saveFeeListInfo (ArrayList<ParkFeeInfo> feeList) //������ݰ� �����ð��� ������ feeList(ArrayList)�� ������ ���Ͽ� �����Ѵ�.
	{
		try {
			fos = new FileOutputStream("feeInfo.txt");
			oos = new ObjectOutputStream(fos);
		
			oos.writeObject(feeList); 
			oos.close();
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public static void loadFeeListInfo () //������ݰ� �����ð��� ��� ������ ������ �о�ͼ� �ٽ� feeList(ArrayList)�� �����Ѵ�.
	{
		try {
			fis = new FileInputStream("feeInfo.txt");
			ois = new ObjectInputStream(fis);
			
			ParkingCarOut.feeList = (ArrayList<ParkFeeInfo>) ois.readObject(); //
			ois.close();
		} catch (Exception ae) { ae.printStackTrace();}
	}
}
