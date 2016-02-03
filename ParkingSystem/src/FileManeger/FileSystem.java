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

public class FileSystem { //파일 저장, 출력은 static으로 구현해서 (클래스명.함수명)을 통해 호출해서 사용할 수 있도록 전역처리한다.
	static FileOutputStream fos; //파일에 내용을 쓰기 위한 fos와 oos
	static ObjectOutputStream oos;
	
	static FileInputStream fis; //파일의 내용을 읽어오기 위한 fis와 ois
	static ObjectInputStream ois;
	
	//각 ArrayList별로 파일을 따로 만들어 저장하게 된다. 유의해야 할 점은 파일에 들어갈 ArrayList안에 있는 내용이 여러 변수를 가진 클래스의 객체라면 직렬화해야 한다는 것이다.
	//(implements Serializable) <--- 이것을 클래스에 적어줘야 직렬화가 된다.
	
	public static void saveCarInfo(ArrayList<ParkCarInfo> parkDataList) //주차차량 정보를 담은 리스트를 파일에 저장한다.
	{
		try {		
			fos = new FileOutputStream("CarInfo.txt");
			oos = new ObjectOutputStream(fos);
		
			oos.writeObject(parkDataList);
			oos.close();
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public static void loadCarInfo () //주차차량들 정보를 가진 파일의 내용을 다시 리스트에 다 저장한다.
	{
		try {
			fis = new FileInputStream("CarInfo.txt");
			ois = new ObjectInputStream(fis);
			
			ParkingCarIn.parkCarList = (ArrayList<ParkCarInfo>) ois.readObject();
			ois.close();
		} catch (Exception ae) { ae.printStackTrace();}
	}
	
	public static void saveMemberInfo (ArrayList<MemberInfo> memList) //회원정보를 가진 리스트를 파일에 모두 저장한다.
	{
		try {
			fos = new FileOutputStream("MemberInfo.txt");
			oos = new ObjectOutputStream(fos);
		
			oos.writeObject(memList); //MemberInfo가 직렬화되어있기 때문에 파일에 리스트 그대로 저장이 가능하다.
			oos.close();
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public static void loadMemberInfo () //회원정보를 가진 파일의 내용을 다시 리스트에 다 저장한다.
	{
		try {
			fis = new FileInputStream("MemberInfo.txt");
			ois = new ObjectInputStream(fis);
			
			ParkingNewMemJoin.memList = (ArrayList<MemberInfo>) ois.readObject(); //직렬화되어있는 내용을 다시 읽어와서 리스트에 저장한다.
			ois.close();
		} catch (Exception ae) { ae.printStackTrace();}
	}
	
	public static void saveParkInfo (ArrayList<String> parkList) //주차 내역을 가진 parkList(ArrayList)를 파일에 저장한다.
	{
		try {
			fos = new FileOutputStream("parkListInfo.txt");
			oos = new ObjectOutputStream(fos);
		
			oos.writeObject(parkList); //
			oos.close();
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public static void loadParkListInfo () //주차내역이 담긴 파일의 내용을 읽어와서 다시 parkList(ArrayList)에 저장한다.
	{
		try {
			fis = new FileInputStream("parkListInfo.txt");
			ois = new ObjectInputStream(fis);
			
			ParkingCarIn.parkPrintList = (ArrayList<String>) ois.readObject(); //
			ois.close();
		} catch (Exception ae) { ae.printStackTrace();}
	}
	
	public static void saveFeeListInfo (ArrayList<ParkFeeInfo> feeList) //출차요금과 출차시간을 저장한 feeList(ArrayList)의 내용을 파일에 저장한다.
	{
		try {
			fos = new FileOutputStream("feeInfo.txt");
			oos = new ObjectOutputStream(fos);
		
			oos.writeObject(feeList); 
			oos.close();
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public static void loadFeeListInfo () //출차요금과 출차시간이 담긴 파일의 내용을 읽어와서 다시 feeList(ArrayList)에 저장한다.
	{
		try {
			fis = new FileInputStream("feeInfo.txt");
			ois = new ObjectInputStream(fis);
			
			ParkingCarOut.feeList = (ArrayList<ParkFeeInfo>) ois.readObject(); //
			ois.close();
		} catch (Exception ae) { ae.printStackTrace();}
	}
}
