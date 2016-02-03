package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import Information.ParkFeeInfo;

public class ParkingFeeList implements ActionListener {
	ParkingStartView frame; //ParkingStartView에서 frame 받기 위한 변수
	String[] feeChoice= {"이번달 요금정산", "일일 요금 정산", "기간 검색 정산", "메뉴나가기"}; //showOptionDialog의 각 버튼 문자열들
	
	JDialog feeResultDialog; //요금 다이어로그
	JPanel feeFullPanel = new JPanel(null); //요금 다이어로그의 화면을 채울 패널
	JLabel titleFeeLabel = new JLabel(); //무슨 정산인지 알려줄 Label
	JLabel resultFeeLabel = new JLabel(); //정산 결과를 담을 Label
	JButton feeOkButton = new JButton("정산확인");
	
	//기간 검색 을 위한 컴포넌트들
	JDialog termSearchDig; //기간 검색창을 띄우기 위한 다이어로그
	JPanel termDigPanel = new JPanel(null);
	JLabel tetmTitleLabel = new JLabel("기간을 입력해주세요.");
	JTextField firstDateText = new JTextField("yyyy-MM-dd");
	JLabel tildeLabel = new JLabel("~");
	JTextField secondDateText = new JTextField("yyyy-MM-dd");
	JButton termOkBtn = new JButton("검색확인");
	
	public ParkingFeeList (ParkingStartView frame) //ParkingSystem에서 '요금정산'버튼 클릭 이벤트 발생 시 요금정산 다이어로그 출력 위치가 넘어오게 된다.
	{
		this.frame = frame; //ParkingStartView객체의 frame을 가져와서 저장		
		
		feeOkButton.addActionListener(new feeOkListener()); //'정산확인' 버튼 클릭 시, 이벤트 리스너
		termOkBtn.addActionListener(new termOkListener()); //기간 검색 다이어로그의 '검색확안' 버튼 클릭 시 이벤트 리스너
	} //ParkingFeeList 생성자 End

	//'요금정산' 클릭 시 처리하는 이벤트 리스너
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int choice = 0;
		choice = makeChoiceFeeDialog(); //choice가 3이아니고 CLOSED_OPTION이 아니면 요금정산 결과 다이어로그 출력!
		
		if (choice != 2 && choice != 3 && choice != JOptionPane.CLOSED_OPTION)
		{
			makeResultDialog();
		}
	}
	
	public int makeChoiceFeeDialog()
	{
		int choice; //다이어로그 버튼 클릭 시 무엇을 클릭했는지 알기 위한 변수
		int resultFee = 0; //각 메뉴마다 나타나게될 정산요금이 다르다! , 총 번 요금의 정보를 담게 될 변수
		//'요금정산' 버튼 클릭 시 나타나게 될 선택 다이어로그, 선택하는게 무엇인지에 따라 나타나는 다이어로그가 다르다, 유용한 JOptionPane이니까 알아두자!
		choice = JOptionPane.showOptionDialog(frame, "무엇을 확인하시겠습니다.", "요금 정산 메뉴", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, feeChoice, null);
		
		titleFeeLabel.setFont(new Font("고딕", 35, 15));
		if (choice == 0) //'이번달 요금정산' 클릭 시
		{
			titleFeeLabel.setText("이번달 요금 정산 결과입니다.");
			resultFee = monthFee();
		}
		else if (choice == 1) //"주간 요금 정산" 클릭 시
		{
			titleFeeLabel.setText("일일 요금 정산 결과입니다.");
			resultFee = dayFee();
		}
		else if (choice == 2) //"검색 요금 정산" 클릭 시
		{
			makeTermSearchDig(); //기간 검색 다이어로그가 호출된다.
		}
		else if (choice == 3) //'메뉴나가기' 클릭 시
		{
			JOptionPane.showMessageDialog(frame, "다음에 다시 이용해주세요");
		}
		else
		{
			choice = JOptionPane.CLOSED_OPTION; //X클릭시 리턴되는 값은 JOptionPane.CLOSED_OPTION 이다!
		}
		resultFeeLabel.setText("총 " + resultFee + "원입니다."); //각 메뉴 클릭 시 실행되는 메소드가 다르다. 그에 따른 총 주차요금 계산결과도 다르게 된다.
		return choice;
	} //makeChoiceFeeDialog() End
	
	public void makeResultDialog()
	{
		//feeResultDialog 구성하기
		feeResultDialog = new JDialog(frame, "요금정산"); //요금정산 결과를 나타낼 다이어로그 생성
		feeResultDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 300, 150); //요금정산 결과를 나타낼 다이어로그가 나타날 위치
		feeResultDialog.add(feeFullPanel);
		
		feeFullPanel.add(titleFeeLabel); //맨 윗줄 제목
		titleFeeLabel.setBounds(60, 0, 200, 30);
		
		feeFullPanel.add(resultFeeLabel); //가운데 정산결과 요금
		resultFeeLabel.setBounds(95, 40, 180, 30);
		
		feeFullPanel.add(feeOkButton); //'정산확인' 버튼
		feeOkButton.setBounds(90, 80, 100, 30);
		
		feeResultDialog.setVisible(true);
	} //makeResultDialog() End
	
	public void makeTermSearchDig() //기간을 지정해서 요금 정산을 하고 싶을 시 이 다이어로그가 호출되게 된다.
	{
		termSearchDig = new JDialog(frame, "기간 검색 요금 정산"); //기간 검색창을 띄우기 위한 다이어로그
		termSearchDig.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 265, 150);
		termSearchDig.add(termDigPanel);
		termDigPanel.setBounds(0, 0, 200, 200);
		
		tetmTitleLabel.setBounds(65, 0, 150, 20);
		firstDateText.setBounds(10, 30, 100, 30);
		tildeLabel.setBounds(120, 30, 20, 30);
		tildeLabel.setFont(new Font("고딕", Font.PLAIN, 15));
		secondDateText.setBounds(140, 30, 100, 30);
		termOkBtn.setBounds(75, 70, 100, 30);
		
		termDigPanel.add(tetmTitleLabel);
		termDigPanel.add(firstDateText);
		termDigPanel.add(tildeLabel);
		termDigPanel.add(secondDateText);
		termDigPanel.add(termOkBtn);
		
		firstDateText.requestFocus();
		termSearchDig.setVisible(true);
	} //makeTermSearchDig() End
	
	public int monthFee()
	{
		int resultFee = 0; //이번달 총 요금을 저장하기 위한 변수
		
		Calendar presentTime = Calendar.getInstance(); //Calendar클래스를 이용해서 현재 시간의 년도,달,일을 알아낸다.
		Date sDay = new Date(presentTime.get(Calendar.YEAR) - 1900, presentTime.get(Calendar.MONTH), 1); //sDay에 현재 시간의 년도, 월, 1일(시작날)을 집어넣는다. ex)2016년 1월 1일 00:00:00 으로 저장된다.
		System.out.println("현재 월의 시작 : " + sDay);
		Date eDay = new Date(presentTime.get(Calendar.YEAR) - 1900, presentTime.get(Calendar.MONTH), presentTime.getActualMaximum(Calendar.DATE), 23, 59, 59); //eDay에 현재 시간의 년도, 월, 마지막일(30,31일 2월은 28일29일) 을 집어넣는다. ex)2016년 1월 31일 23:59:59 으로 저장된다.
		//3916년도로 설정되어있어서 -1900을 해야 현재 시간의 년도가 나온다. 왜지?
		System.out.println("현재 월의 끝 : " + eDay);
		
		for (ParkFeeInfo fee : ParkingCarOut.feeList)
		{
			System.out.println("주차 출차 시간 : " + fee.getTime());
			//sDay가 현재 달(월)의 첫 날이다. eDay는 현재 달(월)의 끝 날이다. 저장된 출차 시간들이 sDay이후이고 eDay이전이면 한달이라는 시간의 사이에 시간들이 된다. 이를 통해 한달치의 요금을 계산할 수 있다.
			if ((fee.getTime().after(sDay) && fee.getTime().before(eDay))  && fee.getTime().before(eDay)) //1월이라고 치면 1월 1일 ~ 1월31일 사이면 요금을 다 더하는거다!
			{
				resultFee += fee.getFee();
			}
		}
		
		return resultFee;
	} //monthFee() End
	
	public int dayFee() //일일요금계산
	{
		int resultFee = 0;
		
		Calendar presentTime = Calendar.getInstance(); //Calendar클래스를 이용해서 현재 시간의 년도,달,일을 알아낸다.
		
		Date sDay = new Date(presentTime.get(Calendar.YEAR) - 1900, presentTime.get(Calendar.MONTH), presentTime.get(Calendar.DATE)); //sDay에 오늘의 년도, 월, 일을 집어넣는다. ex)2016년 1월 1일 00:00:00 으로 저장된다.
		System.out.println("오늘의 시작! : " + sDay);
		Date eDay = new Date(presentTime.get(Calendar.YEAR) - 1900, presentTime.get(Calendar.MONTH), presentTime.get(Calendar.DATE), 23, 59, 59); //eDay에 오늘의 년도, 월, 일, 23시59분59초를 넣어놓는다.
		System.out.println("오늘의 끝! : " + eDay);
		
		for (ParkFeeInfo fee : ParkingCarOut.feeList) //출차 차량에 대한 요금정보와 출차시간을 가진 feeList의 feeInfo객체들을 하나씩 꺼내서 값을 비교한다.
		{
			System.out.println("주차 출차 시간 : " + fee.getTime());
			//sDay가 현재 달(월)의 첫 날이다. eDay는 현재 달(월)의 끝 날이다. 저장된 출차 시간들이 sDay이후이고 eDay이전이면 한달이라는 시간의 사이에 시간들이 된다. 이를 통해 한달치의 요금을 계산할 수 있다.
			if ((fee.getTime().after(sDay) && fee.getTime().before(eDay))  && fee.getTime().before(eDay)) //1월이라고 치면 1월 1일 ~ 1월31일 사이면 요금을 다 더하는거다!
			{
				resultFee += fee.getFee();
			}
		}
		return resultFee;
	} //dayFee() End
	
	public int searchFee(String date1, String date2) //기간을 설정, 검색해서 요금정산 결과를 얻는 메소드
	{
		int resultFee = 0;

		//첫번째 텍스트 필드에 있는 년,월,일 값을 가져오는 코드
		int fYear = Integer.parseInt(firstDateText.getText().substring(0, 4));
		int fMonth = Integer.parseInt(firstDateText.getText().substring(5, 7));
		int fDay = Integer.parseInt(firstDateText.getText().substring(8));
		
		//두번째 텍스트 필드에 있는 년,월,일 값을 가져오는 코드
		int tYear = Integer.parseInt(secondDateText.getText().substring(0, 4));
		int tMonth = Integer.parseInt(secondDateText.getText().substring(5, 7));
		int tDay = Integer.parseInt(secondDateText.getText().substring(8));
		
		Date sDay = new Date(fYear - 1900, fMonth -1, fDay); //sDay에 오늘의 년도, 월, 일을 집어넣는다. ex)2016년 1월 1일 00:00:00 으로 저장된다.
		System.out.println("오늘의 시작! : " + sDay);
		Date eDay = new Date(tYear - 1900, tMonth -1, tDay, 23, 59, 59); //eDay에 오늘의 년도, 월, 일, 23시59분59초를 넣어놓는다.
		System.out.println("오늘의 끝! : " + eDay);
		
		for (ParkFeeInfo fee : ParkingCarOut.feeList) //출차 차량에 대한 요금정보와 출차시간을 가진 feeList의 feeInfo객체들을 하나씩 꺼내서 값을 비교한다.
		{
			System.out.println("주차 출차 시간 : " + fee.getTime());
			//sDay가 현재 달(월)의 첫 날이다. eDay는 현재 달(월)의 끝 날이다. 저장된 출차 시간들이 sDay이후이고 eDay이전이면 한달이라는 시간의 사이에 시간들이 된다. 이를 통해 한달치의 요금을 계산할 수 있다.
			if ((fee.getTime().after(sDay) && fee.getTime().before(eDay))  && fee.getTime().before(eDay)) //1월이라고 치면 1월 1일 ~ 1월31일 사이면 요금을 다 더하는거다!
			{
				resultFee += fee.getFee();
			}
		}
		return resultFee;
	} //searchFee() End
	
	class feeOkListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			feeResultDialog.setVisible(true); //'정산확인'을 누르면 다이어로그가 종료된다.
			feeResultDialog.dispose();
		}
	} //feeOkListener class End
	
	class termOkListener implements ActionListener //기간 검색 결과 확인을 눌렀을 시 동작하게 되는 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int resultFee = 0;
			termSearchDig.setVisible(false);
			termSearchDig.dispose();
			
			titleFeeLabel.setText("검색 요금 정산 결과입니다.");
			String date1 = firstDateText.getText();
			String date2 = secondDateText.getText();
			resultFee = searchFee(date1, date2);
			System.out.println("검색 결과: " + resultFee);
			titleFeeLabel.setText("검색 요금 정산 결과입니다.");
			resultFeeLabel.setText("총 " + resultFee + "원입니다.");
			makeResultDialog();
		}
	} //termOkListener class End
}
