package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;


import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import StartView.ParkingStartView;

public class ParkingCarNumList {
	ParkingStartView frame; //ParkingStartView객체의 JFrame을 가져오기 위한 변수
	//기본적으로 JTable을 사용하는 방법
	Vector<String> rowData; //주차 목록이 담긴 벡터의 내용을 담기 위한 벡터 변수
	Vector<String> colName = new Vector<String>(); //테이블의 열 제목을 저장하기 위한 벡터변수

	DefaultTableModel parkTableModel = new DefaultTableModel(); //DefaultTabelModel (vector, vector)형태로 사용예정
	JTable parkJTable; //DefaultTableModel을 담기 위한 JTable변수, JTable의 인자 중 하나가 TableModel이다
	public static JScrollPane parkScroll; //JTable에 Scroll기능을 달기 위한 JScroll변수, '주차현황'클릭 시 '주차내역'테이블이 보이지 않아야하므로 서로 상호작용하기 위해 static로 설정
	
	
}
