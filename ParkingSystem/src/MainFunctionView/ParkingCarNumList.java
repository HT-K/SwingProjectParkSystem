package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;


import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Information.ParkCarInfo;
import StartView.ParkingStartView;

public class ParkingCarNumList { //현재 주차된 차량의 리스트를 보여주는 테이블
	ParkingStartView frame; //ParkingStartView객체의 JFrame을 가져오기 위한 변수
	//기본적으로 JTable을 사용하는 방법
	Vector<String> rowData; //주차 목록이 담긴 벡터의 내용을 담기 위한 벡터 변수
	Vector<String> colName = new Vector<String>(); //테이블의 열 제목을 저장하기 위한 벡터변수

	DefaultTableModel parkTableModel = new DefaultTableModel(); //DefaultTabelModel (vector, vector)형태로 사용예정
	JTable parkJTable; //DefaultTableModel을 담기 위한 JTable변수, JTable의 인자 중 하나가 TableModel이다
	public static JScrollPane parkScroll; //JTable에 Scroll기능을 달기 위한 JScroll변수, '주차현황'클릭 시 '주차내역'테이블이 보이지 않아야하므로 서로 상호작용하기 위해 static로 설정
	
	public ParkingCarNumList (ParkingStartView frame) //현재 주차된 차량의 리스트를 보여주는 테이블
	{
		this.frame = frame;
		
		//JTable 모양, 크기 지정 및 위치 지정
		colName.addElement("현재 주차 차량 번호 출력"); //colName벡터에 내용저장 (테이블의 제목이라고 보면된다)
		parkTableModel = new DefaultTableModel(null, colName); //DefaultTableModel의 row(열)내용인 null에도 벡터가 들어갈 예정이다.
		parkJTable = new JTable(parkTableModel); //JTable의 생성자 인자에 TableModel을 넣어줘야해서 DefaultTableModel을 사용한다
		parkScroll = new JScrollPane(parkJTable); //JTable 오른쪽에 스크롤바를 붙인다!
		parkScroll.setBounds(880, 280, 310, 165); //출력 테이블 위치와 크기 지정
		
		parkScroll.setVisible(true);
		frame.add(parkScroll); //ParkingStartView의 frame에 JTable을 배치한다!
		thread.start(); //객체 생성과 동시에 '주차내역'을 최신화하는 스레드를 실행시킨다.
	}
	
	Thread thread = new Thread(new Runnable() { //'주차내역'테이블에 출력될 내용들이 자동으로 수정되게끔 설정한다.
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true)
			{
				//추가된 내용까지 합쳐서 다시 출력하기 위해 먼저 Table에 들어가있는 내용을 전부 지운다.
				int rowCount = parkTableModel.getRowCount();
				for (int i = rowCount - 1; i > -1; i--)
				{
					parkTableModel.removeRow(i);
				}
				
				for (ParkCarInfo car : ParkingCarIn.parkCarList) //차량이 주차된 공간의 인덱스 즉, 층을 구분하기 위해 가져온다.
				{
					String carNum = "";
					if (car.getparkPlaceNum() > 0 && car.getparkPlaceNum() <= 20)
					{
						carNum = "1층 " + car.getparkPlaceNum() + "번 주차공간에 " + car.getcarNum() +"번 차량 주차중";
					}
					else if (car.getparkPlaceNum() > 20 && car.getparkPlaceNum() <= 40)
					{
						carNum = "2층에 " + car.getparkPlaceNum() + "번 주차공간에 " + car.getcarNum() +"번 차량 주차중";
					}
					else
					{
						carNum = "3층에 " + car.getparkPlaceNum() + "번 주차공간에 " + car.getcarNum() +"번 차량 주차중";
					}
					rowData = new Vector<String>(); //벡터 생성
					rowData.addElement(carNum); //생성한 벡터에 스트링 값 저장
					parkTableModel.addRow(rowData); //테이블의 행에 스트링 값(주차목록)을 하나씩 추가한다.
				}
				
				try {
					thread.sleep(1000);
				} catch (InterruptedException e) {}
			} //while End
		} //run() End
	}); //Thread End
	
	
}
