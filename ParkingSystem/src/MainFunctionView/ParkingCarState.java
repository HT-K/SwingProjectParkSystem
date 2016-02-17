package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;
import Information.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
	
public class ParkingCarState implements ActionListener {
	public ParkingStartView frame; //ParkingStartView객체의 JFrame을 가져오기 위한 변수
	String[][] rowName = new String[3][3]; //행에 제목과 내용을 붙이기 위해 3X3의 테이블형태를 가진 2차원배열 생성
	String[] colName = {"몇층?", "주차현황", "빈공간 수"}; //열에 나타날 목록 제목들!
	
	DefaultTableModel parkTableModel; //DefaultTabelModel (vector, vector)형태로 사용예정
	JTable parkJTable; //DefaultTableModel을 담기 위한 JTable변수
	public static JScrollPane parkScroll; //JTable에 Scroll기능을 달기 위한 JScroll변수, '주차내역'클릭 시 '주차현황'테이블이 사라져야하는 상호작용을 하기 위해 static으로 설정한다.
	
	//관리자와 회원 구분
	int memCheck = 0;
	
	public ParkingCarState (ParkingStartView frame, int memCheck)
	{
		this.memCheck = memCheck;
		this.frame = frame; //ParkingStartView에서 가져온 frame을 저장한다.
		
		rowName[0][0] = "1층"; //맨 처음에  행에 들어갈 목록의 제목들이다
		rowName[1][0] = "2층";
		rowName[2][0] = "3층";
		parkTableModel = new DefaultTableModel(rowName, colName); //DefaultTableModel의 형태 지정 null에도 벡터가 들어갈 예정이다.
		parkJTable = new JTable(parkTableModel); //JTable의 생성자 인자에 TableModel을 넣어줘야해서 DefaultTableModel을 사용한다
		parkJTable.setFont(new Font("굴림", Font.PLAIN, 14));
		parkScroll = new JScrollPane(parkJTable); //JTable에 Scroll기능을 넣는다.
		parkScroll.setBounds(880, 200, 310, 72); //출력 테이블 위치와 크기 지정
		
		parkScroll.setVisible(true);
		frame.add(parkScroll); //ParkingStartView의 frame에 JTable을 배치한다!
		thread.start(); //객체 생성과 동시에'주차현황'을 최신화하는 스레드를 실행시킨다.
	} //ParkingCarState 생성자 End

	//'ParkingMainView에서 '주차현황' 클릭 시 실행되는 이벤트 리스너
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
		ParkingCarList.parkScroll.setVisible(false); //같은 공간에 출력되는 '주차내역'을 보이지 않게 설정한다.
		ParkingCarNumList.parkScroll.setVisible(true); //'주차차량번호'테이블이 출력되게 설정한다.
		parkScroll.setVisible(true); //'주차현황'테이블이 출력되게 설정한다.
	} 
	
	Thread thread = new Thread(new Runnable() { //'주차현황'테이블에 출력될 내용들이 1초마다 자동으로 수정되게끔 설정한다.
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true)
			{
				//각 층에 들어가게 될 주차공간의 숫자들을 담을 변수들이다
				int firstIn = 0;
				int firstRemain = 0;
				int secondIn = 0;
				int secondRemain = 0;
				int thirdIn = 0;
				int thirdRemain = 0;
				for (ParkCarInfo car : ParkingCarIn.parkCarList) //주차한 차량의 데이터가 저장된 ArrayList에서 각 차량의 주차공간 번호를 가져온다.
				{
					int parkPlaceNum = car.getparkPlaceNum();
					if (parkPlaceNum <= 20)
					{
						++firstIn; //주차등록된 공간 인덱스 번호가 20보다 작거나 같은 경우 1층이다
					}
					else if (parkPlaceNum >= 21 && parkPlaceNum <= 40)
					{
						++secondIn; //주차등록된 공간 인덱스 번호가 20보다 크거나 같고 40보다 작거나 같은 같은 경우 1층이다
					}
					else
					{
						++thirdIn; //나머지면 3층!
					}
				} //for문 End
				
				firstRemain = 20 - firstIn; //1층에 남은 공간의 수
				secondRemain = 20 - secondIn; //2층에 남은 공간의 수
				thirdRemain = 20 - thirdIn; //3층에 남은 공간의 수
				
				//2차원 배열을 쓰는 DefaultTable은 이렇게 모든 값들이 초기화 되어있지 않으면 나타낼 수 없다. 버튼을 클릭 할 때마다 들어가는 데이터를 초기화 시킨다.
				rowName[0][0] = "1층";
				rowName[1][0] = "2층";
				rowName[2][0] = "3층";
				rowName[0][1] = String.valueOf(firstIn);
				rowName[0][2] = String.valueOf(firstRemain);
				rowName[1][1] = String.valueOf(secondIn);
				rowName[1][2] = String.valueOf(secondRemain);
				rowName[2][1] = String.valueOf(thirdIn);
				rowName[2][2] = String.valueOf(thirdRemain); 
				parkTableModel = new DefaultTableModel(rowName, colName); //초기화 시킨 데이터를 DefaultTableModel에 넣어서 객체를 생성하고
				
				parkJTable.setModel(parkTableModel); //새로 생성한 TableModel을 JTable에 set시키면 새로운 데이터를 가진 TableModel이 화면에 보이게 된다.
				try {
					thread.sleep(1000);
				} catch (InterruptedException e) {}
			} //while End
		} //run() End
	}); //Thread End
} //ParkingCarState class End
