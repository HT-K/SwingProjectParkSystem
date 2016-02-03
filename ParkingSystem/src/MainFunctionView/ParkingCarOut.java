package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import FileManeger.FileSystem;
import Information.*;

public class ParkingCarOut extends JOptionPane implements ActionListener {
	public ParkingStartView frame; //ParkingStartView의 Frame을 받기 위한 변수
	public static ArrayList<ParkFeeInfo> feeList = new ArrayList<>(); //출차완료 시 그 차량에 대한 주차요금과 차량이 나간 시간의 변수를 가진 FeeInfo객체를 저장하는 ArrayList
	
	//출차 하기 다이어로그 구성
	JDialog outCarDialog; //출차를 하기 위해 차량번호를 입력할 다이어로그 창
	JPanel outCarFullPanel = new JPanel(null); //출차 다이어로그 안의 컴포넌트 구성을 위한 fullPanel
	JLabel outCarLabel = new JLabel("차량 출차 하기"); //다이어로그 안의 맨 윗줄에 나타나게될 Label
	JLabel carNumLabel = new JLabel("차량번호 : "); //중간에 사용자가 입력하게될 텍스트필드 부분
	JTextField inputNumText = new JTextField("",9); //최대 9칸까지!
	JButton outOkButton = new JButton("출차완료");
	JButton cancelButton = new JButton("취소");
	
	//출차 등록 후 결과를 보여주는 다이어로그
	JDialog resultOutDialog; //출차 완료 결과를 보여주는 다이어로그
	JPanel resultFullPanel = new JPanel(null); //출차결과 다이어로그에 컴포넌트를 담을 패널
	JLabel rfTitleLabel = new JLabel(); //출차 결과 다이어로그 title Label
	JLabel rfCarTypeLabel = new JLabel(); //차량 종류 출력 Label
	JLabel rfCarNumLabel = new JLabel(); //차량 번호 출력 Label
	JLabel rfParkFeeLabel = new JLabel(); //주차 요금 출력 Label
	JButton rfOkButton = new JButton("출차확인");
	
	int memCheck = 0;
	
	public ParkingCarOut (ParkingStartView frame, int memCheck) //ParkingSystme에서 출차하기 버튼 클릭 이벤트 발생 시 이쪽으로 넘어오게 된다.
	{
		this.memCheck = memCheck; //관리자, 회원, 비회원을 구분하기 위한 변수
		this.frame = frame; //ParkingStartView클래스의 Frame을 가져온다.
		
		outOkButton.addActionListener(new outOkListener()); //'출차완료'클릭 시
		cancelButton.addActionListener(new cancelListener()); //'출차취소'클릭 시
		rfOkButton.addActionListener(new rfOkListener()); //'출차결과확인'클릭 시
	}
	
	public void makeOutCarDialog() //차량 출차 등록 다이어로그 구성하기
	{
		outCarDialog = new JDialog(frame, "차량 출차 등록 하기");
		outCarDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 300, 150); //다이어로그가 나타날 위치 지정
		outCarDialog.add(outCarFullPanel); //다이어로그 안에 들어갈 내용을 구성한 fullPanel을 다이어로그에 add!
		
		outCarFullPanel.add(outCarLabel); //맨 윗줄 Label
		outCarLabel.setBounds(80, 0, 150, 30);
		
		outCarFullPanel.add(carNumLabel); //차량 번호 입력 Label
		carNumLabel.setBounds(60, 40, 70, 20);
		
		outCarFullPanel.add(inputNumText); //번호 입력 텍스트 필드
		inputNumText.setBounds(125, 40, 90, 20);
		
		outCarFullPanel.add(outOkButton); //'출차완료'버튼
		outOkButton.setBounds(40, 70, 100, 30);
		
		outCarFullPanel.add(cancelButton); //'출차취소'버튼
		cancelButton.setBounds(145, 70, 100, 30);
		
		outCarDialog.setVisible(true);
	} //makeOutCarDialog() End
	
	public void makeResultOutDialog()
	{
		resultOutDialog = new JDialog(frame, "차량 출고 완료");
		resultOutDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 200, 200);
		resultOutDialog.add(resultFullPanel); //출차 확인 다이어로그를 구성한 패널 add
		resultFullPanel.setSize(new Dimension(200, 200));
		
		resultFullPanel.add(rfTitleLabel); //다이어로그 제목 Label
		rfTitleLabel.setBounds(25, 0, 150, 20);
		
		resultFullPanel.add(rfCarTypeLabel); //차량 종류 출력 Label
		rfTitleLabel.setBounds(45, 30, 150, 20);
		
		resultFullPanel.add(rfCarNumLabel); //차량 번호 출력 Label
		rfCarNumLabel.setBounds(45, 60, 150, 20);
		
		resultFullPanel.add(rfParkFeeLabel); //주차 요금 출력 Label
		rfParkFeeLabel.setBounds(45, 90, 150, 20);
		
		resultFullPanel.add(rfOkButton); //'출차결과확인'버튼
		rfOkButton.setBounds(40, 125, 100, 30);
		
		resultOutDialog.setVisible(true);
	} //makeResultOutDialog() End

	//메인화면에서 '출차하기' 클릭시 발생하는 이벤트를 처리하는 핸들러
	@Override
	public void actionPerformed(ActionEvent e) { //'출차하기'는 버튼이 한개이기때문에 한번 클릭 시 생성된 객체 하나로 작동된다.
		// TODO Auto-generated method stub
		makeOutCarDialog(); //출차 다이어로그 호출
		inputNumText.setText(""); //'출차완료'클릭 전 텍스트 필드를 초기화한다.
		outCarLabel.setFont(new Font("고딕", 35, 20)); //출차 다이어로그 내용 변경
	}//'출차하기'버튼 이벤트 핸들러 End
	
	class outOkListener implements ActionListener //'출차완료' 클릭 시 실행되는 이벤트 핸들러
	{
		/*for(CarInfo car : ParkingCarIn.parkDataMap.values())
		{
			if (car.getcNum().equals(inputNumText.getText()))
			{
				System.out.println("출차 정확히 입력 = " + inputNumText.getText());
				flag = 1;
				break;
			}
		}
		*/
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ParkCarInfo park = null;
			int resultFee = 0; //요금을 저장하기 위한 변수
			int flag = 0; //트리맵 안의 차량번호 중 텍스트필드에서 입력받은 내용과 같은 것이 있는지 없는지 검사, 0이면 없고 1이면 찾은것
			for (ParkCarInfo car : ParkingCarIn.parkCarList)
			{
				if (car.getcarNum().equals(inputNumText.getText())) //입력받은 번호와 리스트에 있는 번호가 같으면
				{
					park = car; //리스트에서 그 차량의 주차정보가 담긴 CarInfo객체를 저장한다.
					flag = 1;
					break;
				}
			}
			
			try { //번호 입력창에 숫자가 아닌 문자 입력 시 오류창을 띄우기 위한 try-catch문
                int check = Integer.parseInt(inputNumText.getText());
            } catch (Exception ae) {
                JOptionPane.showMessageDialog(null, "문자는 입력하시면 안돼요~", "숫자를 입력하세요.", 0);
                inputNumText.setText("");
                return;
            }
            
            if (inputNumText.getText().length() > 4) //차량번호 입력은 4자리만 가능하게 설정
            {
                JOptionPane.showMessageDialog(null, "차량번호는 4자리겠지?", "차량번호 입력", 0);
                inputNumText.setText("");
                return;
            }
			
			if (flag == 0) //parkCarList(ArrayList)에 저장되어 있는 차량 번호중 입력받은 번호를 찾지 못했을 시
			{
				System.out.println("출차 잘못 입력 = "  + inputNumText.getText());
				JOptionPane.showMessageDialog(resultOutDialog, "입력하신 번호의 차량이 없습니다. 다시 입력해주세요","에러창", 0);
				inputNumText.setText("");
				return; //outOkButton리스너의 처음으로 돌아간다!
			}
			else //parkCarList(ArrayList)에 있는 번호에서 입력받은 번호와 같은 것을 찾았을 시
			{
				makeResultOutDialog(); //출차 결과 다이어로그(resultDialog) 호출
				rfTitleLabel.setText(park.getparkPlaceNum() + "번 공간 출고 완료"); //resultDialog 내용 변경
				rfCarTypeLabel.setText("차량종류 : " + park.getcarKind());
				rfCarNumLabel.setText("차량번호 : " + park.getcarNum());
				
				JButton complete = ParkingMainView.parkButton[park.getparkPlaceNum()-1]; //입차 시에 클릭했던 버튼의 정보를 받아온다. 그 버튼의 인덱스 값인 ParkNum - 1이 해당 버튼의 정보를 가진 버튼배열의 인덱스 값이다.
				complete.setText("" + park.getparkPlaceNum()); //'주차중'으로 되어있던 버튼의 텍스트를 다시 숫자로 바꾼다
				complete.setBackground(new Color(99, 0xFF, 99)); //연두색
				complete.setEnabled(true); //출차 완료가 된 버튼은 다시 활성화 시켜야한다!!
			
				Calendar presentTime = Calendar.getInstance(); //차량 출차 등록을 클릭한 시간 얻어오기
				long carOutTime = presentTime.getTimeInMillis(); //출차 등록 한  시간을 초로 바꿔서 저장
				SimpleDateFormat format = new SimpleDateFormat("hh시 mm분 ss초");
				String time = format.format(presentTime.getTime()); //프린트 하기 위해 parkPring 벡터에 저장
				
				resultFee = calculateFee(memCheck, park.getcarKind(), park.getCarInTime(), carOutTime); //주차요금 계산 메소드 호출, 자동으로 주차요금을 계산해서 값을 리턴받는다.
				rfParkFeeLabel.setText("주차요금 : " + resultFee + "원");
				
				feeList.add(new ParkFeeInfo(resultFee, new Date())); //출차하기를 눌렀을 때 요금과 현재시간을 FeeInfo객체를 생성하며 데이터를 넣고, ArrayList에 객체를 저장한다.
				FileSystem.saveFeeListInfo(feeList); //feeList에 있는 요금과 시간을 파일에 저장한다.
				
				ParkingCarIn.parkPrintList.add(park.getparkPlaceNum() + "번 주차공간 " + inputNumText.getText() + "번 차량  " + time + "에 출차."); //parkPrint 벡터에 어느 주차공간에 몇번 차량이 출차되었는지 저장한다.
				FileSystem.saveParkInfo(ParkingCarIn.parkPrintList); //차량 출차를 파일에 저장한다.
				
				ParkingCarIn.parkCarList.remove(park); //출차가 완료되면 그 주차공간과 차량 내용을 담은 CarInfo객체(park)를 ArrayList에서에서 삭제한다.
				FileSystem.saveCarInfo(ParkingCarIn.parkCarList); //차량이 출차하면 다시 파일에 주차차량들을 갱신한다.
			}
		}
	} //outOkButton 리스너 구현
	
	class cancelListener implements ActionListener //'출차취소' 클릭 시, 출차 다이어로그를 없앤다
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			outCarDialog.setVisible(false);
			outCarDialog.dispose(); //다이어로그 숨기기
		}
	} //cancelListener class End
	
	class rfOkListener implements ActionListener //'출차결과확인'클릭 시, 두 개의 다이어로그를 모두 닫는다.
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			resultOutDialog.setVisible(false);
			resultOutDialog.dispose();
			outCarDialog.setVisible(false);
			outCarDialog.dispose();
		}
	} //rfOkListener class End
	
	int calculateFee(int memCheck, String cKind, long carInTime, long carOutTime) //출차완료를 누를 때 호출해야하는 메소드, 차량의 종류와 시간에 따라 결과가 달라진다.
	{
		int resultFee = 0;
		long parkTime = 0;
		int parkCal = 0;
		carInTime = carInTime / 60000; //주차 등록한 시간을 분으로 저장, Millisecond는 1초가 1000이다, 즉 60000으로 나누면 분으로 구할 수 있다.
		carOutTime = carOutTime / 60000; //출차 등록한 시간을 분으로 저장
		
		parkTime = carOutTime - carInTime; //주차 한 시간이 분으로 들어가 있다.
		System.out.println("주차시간 = " + parkTime + "분");
		if (memCheck == 2) //회원은 기본요금이 1500원, 2000원이다
		{
			if (cKind.equals("소형차"))
			{
				if (parkTime <= 30)
				{
					resultFee = 1500;
				}
				else
				{
					parkTime = parkTime - 30;
					parkCal = (int)(parkTime / 10); 
					resultFee = 1500 + (500 * parkCal); //기본 주차 요금 1500원에 10분에 500원추가 
				}
			}
			else
			{
				if (parkTime <= 30)
				{
					resultFee = 2000;
				}
				else
				{
					parkTime = parkTime - 30;
					parkCal = (int)(parkTime / 10); 
					resultFee = 2000 + (600 * parkCal); //기본 주차 요금 2000원에 10분에 600원추가 
				}
			}
		}
		else if (memCheck == 3) //비회원은 기본요금이 2000원, 2500원이다!
		{
			if (cKind.equals("소형차"))
			{
				if (parkTime <= 30)
				{
					resultFee = 2000;
				}
				else
				{
					parkTime = parkTime - 30;
					parkCal = (int)(parkTime / 10); 
					resultFee = 2000 + (500 * parkCal); //기본 주차 요금 1500원에 10분에 500원추가 
				}
			}
			else
			{
				if (parkTime <= 30)
				{
					resultFee = 2500;
				}
				else
				{
					parkTime = parkTime - 30;
					parkCal = (int)(parkTime / 10); 
					resultFee = 2500 + (600 * parkCal); //기본 주차 요금 2000원에 10분에 600원추가 
				}
			}
		}
		return resultFee;
	} //calculateFee() End
} //ParkingCarOut class End
