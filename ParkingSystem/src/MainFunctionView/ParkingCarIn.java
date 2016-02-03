package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageProducer;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import FileManeger.FileSystem;
import Information.*;

public class ParkingCarIn extends JOptionPane implements ActionListener {
	ParkingStartView frame; //ParkingStartView의 프레임을 받아오기 위한 객체 변수 선언
	public static ArrayList<ParkCarInfo> parkCarList = new ArrayList<ParkCarInfo>();  //주차완료 시 주차된 차량들의 정보를 담기위한 ArrayList
	public static ArrayList<String> parkPrintList = new ArrayList<String>(); //주차내역 클릭 시 보여줄 내용을 저장하는 ArrayList
	
	JDialog parkInputDialog; //각 버튼 클릭시 생성될 '주차입고 다이어로그'
	JPanel dialogFullPanel = new JPanel(null); //각 패널을 배치할 fullPanel 컨테이너
	JLabel titleLabel = new JLabel(); //parkInputDialog의 제목을 출력할 Label
	
	JLabel carKindLabel = new JLabel("차 종류 : "); //차 종류를 출력할 Label
	
	CheckboxGroup carChoice = new CheckboxGroup();
	JPanel boxPanel = new JPanel(new FlowLayout()); //체크박스를 담을 패널
	Checkbox car1Box = new Checkbox("소형차", carChoice, true);
	Checkbox car2Box = new Checkbox("중형차", carChoice, false);
	
	JLabel carNumLabel = new JLabel("차량번호 : "); //차량번호를 출력할 Label
	JTextField inputNumText = new JTextField(null,9); //최대 9칸까지!
	
	JButton parkOkButton = new JButton("주차등록"); //'주차등록'버튼
	JButton cancelButton = new JButton("취소"); //'취소'버튼
	
	//ParkingMainView에서 받아와야 할 변수들
	int parkNum; //주차한 공간의 인덱스 번호
	JButton parkButton; //클릭한 주차공간의 버튼 정보
	
	public ParkingCarIn (ParkingStartView frame) //ParkingSystem에서 버튼(주차공간) 클릭 이벤트 발생 시 이쪽으로 넘어오게 된다. //ParkingCarIn클래스의 객체 생성과 동시에 다이어로그를 구성해놓는다.
	{
		this.frame = frame; //ParkingStartView클래스에서 생성된 JFrame을 가져온다.
		
		//각 버튼의 이벤트 핸들러는 한개씩만 존재해야 하므로 생성자에 넣어준다! , 이렇게 하는게 좋다!
		parkOkButton.addActionListener(new parkOkListener());
		cancelButton.addActionListener(new cancelListener());
	} //ParkingCarIn 생성자 End
	
	@Override
	public void actionPerformed(ActionEvent e) { //각 '주차공간'버튼 클릭 시 발생한 이벤트를 처리하는 핸들러
		// TODO Auto-generated method stub
		inputNumText.setText("");//텍스트 필드는 다이어로그 호출 시 항상 초기화 상태로!
		makeRegisterDialog(); //'주차등록' 다이어로그 호출

		//나중에 '출차완료'클릭 시 주차했을 당시 버튼의 인덱스번호와 그 버튼이 무엇이었는지 알아야한다. (그래야 버튼을 다시 활성화하고 그 공간에 주차했던 내용을 삭제한다.)
		parkNum = Integer.parseInt(e.getActionCommand()); //주차공간 버튼 클릭시 해당 주차공간의 인덱스 번호를 가져옴
		parkButton = (JButton)e.getSource(); //이벤트가 발생한 버튼을 가져옴
		
		//titleLabel 내용변경 
		titleLabel.setText("" + parkNum + "번 주차공간입니다.");
		titleLabel.setFont(new Font("고딕", 35, 20));
		
		parkInputDialog.setVisible(true); //다이어로그 구성이 완료되었으니 화면에 출력!!
	} //'주차공간' 이벤트 핸들러(actionPerformed)' End
	
	public void makeRegisterDialog() //등록 다이어로그  창 구성
	{
		parkInputDialog = new JDialog(frame, "차량 정보 입력", true); //ParkingStartView객체의 Frame에 다이어로그를 띄운다
		parkInputDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 300, 210); //차량 입력 다이어로그를 Frame의 가운데에 나타내기
		parkInputDialog.add(dialogFullPanel); //다이어로그 안에 들어갈 내용을 구성한 fullPanel을 다이어로그에 add!	
		
		dialogFullPanel.add(titleLabel); //다이어로그 상단에 제목출력 Label
		titleLabel.setBounds(55, 10, 250, 20);
		
		dialogFullPanel.add(boxPanel); //체크박스 출력
		boxPanel.setBounds(25, 40, 240, 30);
		boxPanel.add(carKindLabel); //차 종류 출력 Label
		boxPanel.add(car1Box);
		boxPanel.add(car2Box);
		
		dialogFullPanel.add(carNumLabel); //차 번호 출력 Label
		carNumLabel.setBounds(50, 80, 80, 30);
		
		dialogFullPanel.add(inputNumText); //차 번호 입력 텍스트필드
		inputNumText.setBounds(120, 80, 120, 30);
		
		dialogFullPanel.add(parkOkButton); //'주차완료'버튼
		parkOkButton.setBounds(40, 130, 100, 30);
		
		dialogFullPanel.add(cancelButton); //'주차취소'버튼
		cancelButton.setBounds(150, 130, 100, 30);
	} //makeRegisterDialog() End
	
	class parkOkListener implements ActionListener //'주차등록' 클릭 시 이벤트를 처리 할 이벤트 리스너
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
            boolean checkException = false; //예외조건들을 체크하기 위한 boolean 변수
           
            checkException = checkTextFieldString(); //텍스트필드에 입력된 것이 숫자인지 숫자가 맞다면 4자리가 맞는지 검사하는 메소드, false면 입력을 잘못한것이다.
            if (checkException == false)
            {
        	    inputNumText.setText("");
        	    inputNumText.requestFocus();
        	    return; //텍스트 필드에 입력한 문자열이 문자이거나 4자리 숫자가 아니므로 다시 입력받게 한다.
            }
           
            checkException = checkNumInCarList(); //입력받은 4자리 숫자가 carList에 있는 숫자인지 아닌지 검사하는 메소드
            if (checkNumInCarList() == true) //true면 이미 주차등록한 차량의 번호이므로 다시 입력받게 한다!
            {
        	    inputNumText.setText("");
        	    inputNumText.requestFocus();
        	    return; //텍스트 필드에 입력한 숫자가 이미 주차등록된 차량이므로 다시 입력받게 한다.
            }
           
            storeCarInfoIntoList(); //위의 모든 오류들을 바로잡고 입력된 숫자면 주차공간에 등록하게 한다!
			
			parkInputDialog.setVisible(false); //주차등록이 완료되면 다이어로그를 숨긴다
			parkInputDialog.dispose(); //등록완료되었으니 등록다이어로그 끄기
		}
	}//parkOkListener 클래스 End
	
	public boolean checkTextFieldString() //텍스트필드에서 가져온 차량번호가 문자열이거나 4자리 숫자가 아니면 다시 입력받게 한다.
	{
		  try { //번호 입력창에 숫자가 아닌 문자 입력 시 오류창을 띄우기 위한 try-catch문
              int check = Integer.parseInt(inputNumText.getText());
          } catch (Exception ae) {
              JOptionPane.showMessageDialog(null, "문자는 입력하시면 안돼요~", "숫자를 입력하세요.", 0);
              return false; //입력받은게 숫자가 아닌 문자열이라서 예외 발생 시 false 리턴
          }
         
          if (inputNumText.getText().length() != 4) //차량번호 입력은 4자리만 가능하게 설정
          {
              JOptionPane.showMessageDialog(null, "차량번호는 4자리겠지?", "차량번호 입력", 0);
              return false; //4자리 숫자가 아닐 시  false 리턴
          }
          return true; //텍스트필드에서 가져온 값이 4자리 숫자가 정확하면 true리턴
	}
	
	public boolean checkNumInCarList()
	{
		  boolean find = false; //List에서 같은 데이터를 찾으면 true로 바꾸고 아니면 false상태로 for문을 빠져나간다.
		  for (ParkCarInfo car : parkCarList) //주차차량 정보를 담은 CarInfo객체들이 저장된 List에서 객체들을 하나씩 접근해서 그 안의 내용을 검사한다.
		  {
			  if (car.getcarNum().equals(inputNumText.getText())) //입력받은 차량번호와 List안에 차량번호와 같은 번호가 있으면 빠져나간다.
			  {
			       find = true;
				   break;
			  }
		  }
		
		  if (find == true) //List안에 입력받은 번호과 같은 번호가 있으면 텍스트필드에 다시 입력받게 한다.
		  {
		   	  JOptionPane.showMessageDialog(null, "입력하신 번호는 이미 등록된 차량입니다. 다시 입력해주세요", "에러창", 0);
			  return true; //parkOkListener의 처음으로 돌아간다.
		  }
		 
		  return false;
	}
	
	public void storeCarInfoIntoList()
	{
		 //List안에 입력받은 번호과 같은 번호가 없으면 주차등록이 가능하게 된다!
		 String checkKind = ""; //라디오버튼에 있는 텍스트(소형차,중형차)를 담기 위한 변수
		 if (car1Box.getState() == true)
		 {
			 checkKind = car1Box.getLabel(); //"소형차" 스트링 값 가져오기
		 }
		 else if (car2Box.getState() == true)
		 {
			 checkKind = car2Box.getLabel(); //"중형차" 스트링 값 가져오기
		 }
		
		 Calendar presentTime = Calendar.getInstance(); //차량 주차 등록을 클릭한 시간 얻어오기
		 long carIntime = presentTime.getTimeInMillis(); //주차 등록 한  시간을 초로 바꿔서 저장
		 SimpleDateFormat format = new SimpleDateFormat("hh시 mm분 ss초"); //시간을 저장할 틀
		 String time = format.format(presentTime.getTime()); //'주차내역'테이블에 출력하기 위해 변환해서 저장
		
		 parkCarList.add(new ParkCarInfo(parkNum, checkKind, inputNumText.getText(), carIntime)); //주차공간번호, 차량종류, 입력받은 차량번호, 주차공간버튼On설정, 주차차량 입고설정
		 FileSystem.saveCarInfo(parkCarList); //주차완료된 차량의 정보를 담은 ArrayList를 파일에 저장한다
		
		 parkPrintList.add(parkNum + "번 주차공간 " + inputNumText.getText() + "번 차량  " + time + "에 입차."); //parkPrint 벡터에 어느 주차공간에 몇번 차량을 등록되었는지 저장한다.
		 FileSystem.saveParkInfo(parkPrintList); //주차 내역 정보를 담은 ArrayList를 파일에 저장한다.
		
		 parkButton.setText("주차중"); //주차 등록이 완료되면 그 주차공간은 '주차중'이라는 표시를 하게함. ParkingMainView에서 주차공간 클릭 시 이 클래스의 이벤트 리스너로 클릭된 버튼의 내용과 인덱스를 받아오게 된다.
		 parkButton.setBackground(new Color(0xFF, 66, 66)); //주차중 버튼의 배경을 빨간색으로 지정한다.
		 parkButton.setEnabled(false); //주차된 곳 버튼 비활성화
	}
	
	class cancelListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			parkInputDialog.setVisible(false);
			parkInputDialog.dispose(); //다이어로그 닫기
		}
	} //cancelListener 클래스 End
} //ParkingCarIn class End
