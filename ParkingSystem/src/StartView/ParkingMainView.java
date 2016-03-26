package StartView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import FileManeger.FileSystem;
import Information.ParkCarInfo;

public class ParkingMainView {
	ParkingStartView frame; //ParkingStartView에서 생성된 JFrame를 받기 위한 변수
	JPanel parkingMainFullScreen = new JPanel(null); //ParkingStartView에서 생성된 JFrame의 ContentPane을 여기서 JPanel로 변경하여 화면이 넘어가게 한다.
	
	//로고이미지를 상단에 넣기 위한 컴포넌트
	ImageIcon logoImgIcon;
	JLabel logoImgLabel = new JLabel();
	Image changeSizeImg; //ImageIcon 객체로는 사이즈를 변경하기가 힘들다. Image객체를 이용해서 이미지 사이즈를 변경한다.
	
	//상단에 Main 주차이미지가 삽입된다.
	ImageIcon mainImgIcon[] = new ImageIcon[7]; //메인 화면 상단에 이미지를 삽입하기 위해 이미지아이콘 객체이용
	JLabel mainImgLabel = new JLabel(); //이미지를 담기위한 라벨
	
	//날짜와 시간을 담기 위한 컴포넌트
	ImageIcon timeImgIcon;
	JLabel timeImgLabel;
	JLabel pDateLabel = new JLabel(); //연도, 월, 일을 담기 위한 Label
	JLabel pTimeLabel = new JLabel(); //시간을 담기 위한 Label
	
	//이미지 아래부분에 주차공간(버튼)을 생성한다.
	CardLayout cardLayout = new CardLayout(); //주차공간의 레이아웃은 CardLayout이어야한다.
	JPanel cardPanel = new JPanel(cardLayout); //중간에 삽입 될 주차공간 패널을 통제하는 패널로 카드레이아웃이 필요하다.
	JPanel[] parkFloorPanel = new JPanel[3]; //3층까지 이므로 3개로 설정, CardLayout으로 설정된 cardPanel에 add한다.
	int cardCount = 0; //CardLayout 이전, 다음 관리를 하기위한 변수
	
	//ActionEvent에 쓰일 버튼을 저장한 JButton클래스의 객체 배열 선언 ,출차할 때 쓰기위해 static으로 선언
	public static JButton[] parkButton = new JButton[60];
	
	//각 주차공간 버튼 다음의 Label들을 저장할 배열 선언
	JLabel parkLabel;
	
	//동쪽에 테이블, 버튼을 생성한다.
	JPanel buttonFullPanel = new JPanel(null); //버튼 들을 담기 위한 패널
	JButton parkListButton = new JButton("주차내역"); //관리자만
	ImageIcon parkListIcon = new ImageIcon("주차내역.jpg");
	JButton feeCalcButton = new JButton("요금정산"); //관리자만
	ImageIcon feeCalcIcon = new ImageIcon("요금정산.jpg");
	JButton outCarButton = new JButton("출차하기"); //고객, 관리자
	ImageIcon outCarIcon = new ImageIcon("출차.jpg");
	JButton parkStateBtn = new JButton("주차현황"); //고객, 관리자
	ImageIcon parkStateIcon = new ImageIcon("주차현황.jpg");
	JButton prevButton = new JButton("이전층"); //고객, 관리자
	ImageIcon prevBtnIcon = new ImageIcon("이전층.jpg");
	JButton nextButton = new JButton("다음층"); //고객, 관리자
	ImageIcon nextBtnIcon = new ImageIcon("다음층.jpg");
	
	//회원의 이름을 시간 밑에 출력 시킨다.
	JLabel memNameLabel = new JLabel();
	JLabel afterNameLabel = new JLabel();
	JButton logoutBtn = new JButton("로그아웃"); //로그아웃 버튼, 누르면 로그인 화면으로 넘어간다.
	ImageIcon logoutIcon = new ImageIcon("로그아웃.png");
	
	//주차 메뉴 테이블
	String[] colName = {"차종류", "회원", "비회원"}; //열에 나타날 목록 제목들!
	String[][] rowName = new String[4][3]; //행에 제목과 내용을 붙이기 위해 3X3의 테이블형태를 가진 2차원배열 생성
	DefaultTableModel parkTableModel; //DefaultTabelModel (vector, vector)형태로 사용예정
	JTable parkJTable; //DefaultTableModel을 담기 위한 JTable변수
	
	//주차 요금 안내판
	ImageIcon feeInfoIcon;
	JLabel feeInfoImgLabel = new JLabel();
	
	//관리자와 회원 구분
	int memCheck = 0;
	String memName = "";
	
	public ParkingMainView (ParkingStartView frame, int memCheck , String memName)
	{
		this.frame = frame; //전송한 ParkingStartView의 frame을 받아온다.
		this.memCheck = memCheck; //회원가입시 자동으로 저장되는 MemberInfo의 memCheck를 가져와 관리자인지 회원인지 비회원인지 구분한다.
		this.memName = memName; //화면에 회원 이름을 띄우기 위해 로그인 시 memList에서 회원의 이름을 받아온다. 관리자는 관리자, 비회원은 비회원으로 스트링 값을 받아온다.
		this.frame.setContentPane(parkingMainFullScreen); //ParkingStartView의 ContentPane이었던 것을 현재 클래스의 JPanel로 변경함으로서 화면을 바꾼다. 화면 전환은 이렇게 하자!!!
		
		view(); //주차시스템 메인화면을 구성한 view()메소드 호출
		
		//addActionListener은 1번만 만들면 되니까 생성자에 넣어두는것이 좋다
		outCarButton.addActionListener(new ParkingCarOut(frame, memCheck)); //'출고하기'를 클릭 시 발생한 이벤트를 처리하는 리스너를 구현한 클래스 생성
		feeCalcButton.addActionListener(new ParkingFeeList(frame)); //'요금정산'을 클릭 시 발생한 이벤트를 처리하는 리스너를 구현한 클래스 생성
		parkListButton.addActionListener(new ParkingCarList(frame)); //'주차내역'을 클릭 시 발생한 이벤트를 처리하는 리스너를 구현한 클래스 생성
		parkStateBtn.addActionListener(new ParkingCarState(frame, memCheck)); //'주차현황'을 클릭 시 발생한 이벤트를 처리하는 리스너를 구현한 클래스 객체 생성
		logoutBtn.addActionListener(new ParkingLogout());
		
		//파일에 저장된 내용들을 전부 불러온다.
		FileSystem.loadCarInfo(); //파일에 저장된 차량정보 리스트를 읽어온다.
		System.out.println("차량 리스트 ; " + ParkingCarIn.parkCarList); //파일에 저장된 리스트 개수 확인.
		for (ParkCarInfo car : ParkingCarIn.parkCarList) //파일에서 읽어온 리스트의 내용으로 주차차량 정보를 가져와 주차시스템에 적용시킨다. (주차되어있는 공간은 계속 빨간색 표시!)
		{
			 JButton complete = ParkingMainView.parkButton[car.getparkPlaceNum()-1]; //주차완료 시에 클릭했던 버튼의 정보를 받아온다.
			 complete.setText("주차중"); //프로그램을 껏다 켯을시에 주차되어있는 부분이 초기화되지 않도록 파일에 저장된 List의 내용을 토대로 다시 주차중으로 바꿔준다
			 complete.setBackground(new Color(0xFF, 66, 66)); //주차중의 배경색은 빨간색
			 complete.setEnabled(false); //읽어온 데이터대로 주차중인 곳은 비활성화 처리한다.
		}
		
		FileSystem.loadParkListInfo(); //파일에 저장된 주차내역 리스트를 읽어온다.
		System.out.println("주차 리스트; " + ParkingCarIn.parkPrintList);
		
		FileSystem.loadFeeListInfo(); //파일에 저장된 요금내역 리스트를 읽어온다
		System.out.println("요금 리스트 : " + ParkingCarOut.feeList);
	} //ParkingMainView 생성자 End
	
	public void view() //주차시스템 메인화면을 구성한 메소드 들을 호출한다.
	{
		makeParkLogoImg();//로고이미지 출력
		makeParkMainImg(); //맨 위에 나타나는 이미지
		makeDateTime(); //맨 위에 나타나는 날짜와 시간
		makeTimeImg(); //시간이 들어갈 이미지
		makePrintName(); //접속 시 이름을 띄워주는
		makeParkPlaceBtn(); //주차공간 버튼을 구현
		if (memCheck == 2 || memCheck == 3) //회원, 비회원만 출력
		{
			makeFeeInfo(); //주차요금 안내판
		}
		
		if (memCheck == 1) //관리자만 주차차량번호리스트가 출력되게 한다.
		{
			new ParkingCarNumList(frame);
		}
		makeSystemBtn(); //기능 버튼들
	} //view() End
	
	public void makeParkLogoImg() //상단에 로고이미지를 띄우는 메소드
	{
		logoImgIcon = new ImageIcon("로고5.png");
		changeSizeImg = logoImgIcon.getImage(); //로고이미지의 크기를 변환하기 위해 Image클래스 객체를 이용한다.
		changeSizeImg = changeSizeImg.getScaledInstance(140, 120, java.awt.Image.SCALE_SMOOTH); //로고이미지의 크기를 140, 140으로 변환시킨다.
		logoImgIcon = new ImageIcon(changeSizeImg); //변환된 크기의 이미지를 ImageIcon에 담는다.
		logoImgLabel.setIcon(logoImgIcon);
		logoImgLabel.setBounds(30, 0, 140, 200); //로고이미지를 담은 Label의 위치와 크기 설정
		parkingMainFullScreen.add(logoImgLabel); //로고이미지를 담은 Label을 컨텐트팬에 붙인다.
	}
	
	public void makeParkMainImg()
	{
		//위쪽에 들어갈 것은 이미지뿐이다.
		parkingMainFullScreen.add(mainImgLabel);
		parkingMainFullScreen.setBackground(Color.white);
		mainImgLabel.setBounds(150, 0, 760, 200);
		
		for (int i = 0; i < mainImgIcon.length; i++)
		{
			mainImgIcon[i] = new ImageIcon("logo"+(i+1)+".png"); //각 ImageIcon에 이미지를 담는다.
			changeSizeImg = mainImgIcon[i].getImage(); //이미지 크기를 변경하기 위해 이미지아이콘에 저장된 이미지를 꺼내서 Image변수에 저장
			changeSizeImg = changeSizeImg.getScaledInstance(780,200, java.awt.Image.SCALE_SMOOTH); //원본 이미지의 크기 조정
			mainImgIcon[i] = new ImageIcon(changeSizeImg); //변경된 크기의 이미지를 다시 ImageIcon에 담는다.
		}
		Thread thread = new Thread(new Runnable() { //첫 화면에 자동차 지나가게 하는 스레드
			@Override
			public void run() {
				while (true)
				{
					for (int i = 0; i < mainImgIcon.length; i++) 
					{
						mainImgLabel.setIcon(mainImgIcon[i]); //1초마다 라벨에 이미지를 바꾸어 넣는다. 그러면 마치 움직이는 듯한 이미지의 모습을 볼 수 있을것!
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
				}
			}
		});
		thread.start(); //이미지 체인지 스레드 시작!
	} //makeParkMainImg() End
	
	public void makeDateTime()
	{
		pDateLabel.setFont(new Font("DS-Digital", Font.BOLD, 35)); //현재 연도, 월, 일의 글씨체와 색지정
		//pDateLabel.setBounds(930, 60, 300, 30); //DATE라벨 크기 및 위치 지정
		
		pTimeLabel.setFont(new Font("DS-Digital", Font.BOLD, 55)); //현재 시간의 글씨체와 색지정
		//pTimeLabel.setBounds(930, 90, 300, 50); //시간라벨 크기 및 위치 지정
		
		/*parkingMainFullScreen.add(pDateLabel); //ParkingStartView의 ContentPane에 Date라벨 추가
		parkingMainFullScreen.add(pTimeLabel); //ParkingStartView의 ContentPane에 Time라벨 추가
*/		Thread thread = new Thread(new Runnable() { //현재시간을 동작하기 위한 스레드, 익명클래스 기법을 사용하여 Runnable인터페이스의 run()메소드 오버라이드 하여 구현
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					Calendar presentTime = Calendar.getInstance(); //현재 시간을 가져온다.
					SimpleDateFormat format = new SimpleDateFormat("   HH      mm  ss"); //시간 출력 형식 지정
					try {
						String date = presentTime.get(Calendar.YEAR) + "       " + (presentTime.get(Calendar.MONTH) + 1) + "     " + presentTime.get(Calendar.DATE) + ""; //가져온 시간의 연도, 월, 일을 저장한다.
						String time = format.format(presentTime.getTime()); //현재 시간을 가져와서 format1형식에 맞게 String에 저장
						pDateLabel.setText(date); //pDate Label에 연월일 출력
						pTimeLabel.setText(time); //pTime Label에 시간 출력
						Thread.sleep(1000); //시계가 동작하듯이 1초마다 동작하게끔 설정
					} catch (InterruptedException e) {}
				}
			}
		}); //Time을 나타내는 Thread End
		thread.start();// 스레드 - 실시간 시간반영
	} //makeDateTime() End
	
	public void makeTimeImg() //시계 이미지를 상단에 넣는다.
	{	
		timeImgIcon = new ImageIcon("시계6.jpg");
		changeSizeImg = timeImgIcon.getImage();
		changeSizeImg = changeSizeImg.getScaledInstance(320, 155 , java.awt.Image.SCALE_SMOOTH);
		timeImgIcon = new ImageIcon(changeSizeImg);
		
		timeImgLabel = new JLabel("테스트", timeImgIcon, JLabel.CENTER); //Label을 생성하고 그 안에 시계 Image를 삽입한다.
		timeImgLabel.setBounds(880, -10, 320, 170);
		
		//스레드로 구현한 날짜와 시간 출력을 시간 이미지 위에 띄우도록 설정한다.
		timeImgLabel.add(pDateLabel); //날짜 텍스트를 가진 Label을 timeImg위에 출력 
		timeImgLabel.add(pTimeLabel); //시간 텍스트를 가진 Label을 timeImg위에 출력
		pDateLabel.setBounds(15, -20, 300, 100); //년,월,일이 나타나는 Label의 위치 지정 , timeImgLabel위에
		pTimeLabel.setBounds(15, 60, 300, 100); //시, 분, 초가 나타나는 Label의 위치 지정, timeImgLabel위에
		
		parkingMainFullScreen.add(timeImgLabel);
	}
	
	public void makePrintName()
	{
		//회원의 이름을 시스템에 출력시킨다.
		parkingMainFullScreen.add(memNameLabel); //회원 이름이 들어가는 Label
		memNameLabel.setBounds(900, 160, 50, 30);
		memNameLabel.setText(memName);
		memNameLabel.setFont(new Font("고딕", Font.BOLD, 15));
		memNameLabel.setForeground(Color.BLUE);
		
		parkingMainFullScreen.add(afterNameLabel); //회원이름뒤에 들어가는 내용이 들어가는 Label
		afterNameLabel.setBounds(950, 160, 130, 30);
		afterNameLabel.setText("님 접속을 환영합니다.");
		
		parkingMainFullScreen.add(logoutBtn); //로그아웃 버튼
		changeSizeImg = logoutIcon.getImage();
		changeSizeImg = changeSizeImg.getScaledInstance(100,30, java.awt.Image.SCALE_SMOOTH);
		logoutIcon = new ImageIcon(changeSizeImg);
		logoutBtn.setBackground(Color.white);
		logoutBtn.setIcon(logoutIcon);
		logoutBtn.setBounds(1090, 160, 90, 30);
	} //makePrintName() End
	
	public void makeParkPlaceBtn() //주차공간 버튼을 구현하는 메소드다. 1~3층으로 구성했으며 버튼 클릭시 같은 공간에 같은 버튼들이 보여야해서 CardLayout으로 설정했다.
	{
		for (int i = 0; i < parkFloorPanel.length; i++)
		{
			parkFloorPanel[i] = new JPanel(new GridLayout(3, 10)); //각 패널에 3행 10열의 배치관리자를 가진 패널객체를 생성한다.
			cardPanel.add(parkFloorPanel[i]); //CardLayout으로 되어있는 cardPanel에 각 층의 주차공간을 나타내는 패널 추가!, 각 카드패널이름을 1, 2, 3으로 지정
		}
		cardPanel.setBounds(5, 200, 870, 465); //카드 레이아웃 위치와 크기 지정
		parkingMainFullScreen.add(cardPanel);
		
		prevButton.addActionListener(new ActionListener() { //'이전층'을 눌렀을 시 동작
			@Override
			public void actionPerformed(ActionEvent e) { 
				if (cardCount > 0) //0보다 크다는 뜻은 2층과 3층 패널을 뜻한다. 0이면 1층이니까 1층은 이천층을 눌렀을 시 카드레이아웃이 변하지 않게 아무일도 일어나지 않게 한다.
				{
					cardLayout.previous(cardPanel); //이전 패널로 돌아가기!!
					cardCount--; //이전 패널로 갔을 시 Count를 한개 감소시킨다.
				}
			}
		}); //prevButtion event End
		
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { //'다음층'을 눌렀을 시 동작
				// TODO Auto-generated method stub
				if (cardCount < 2) //cardCount가 2보다 작다는 뜻은 1층(0)과 2층(1)패널을 뜻한다. 2가 되면 마지막층인 3층이니까 다음층을 눌렀을 시 카드레이아웃 화면이 변하지 않게 아무일도 일어나지 않게 한다.
				{
					cardLayout.next(cardPanel); //다음 패널로 넘어가기!
					cardCount++; //다음 패널로 갔으니 Count를 한개 늘려준다
				}
			}
		}); //nextButton event End
		
		int buttonCount = 1; //각 버튼 배열의 인덱스를 지정하기 위한 변수
		int labelCount = 1; //라벨 배열의 각 인덱스를 저장하기 위한 변수
		for (int f = 0; f < parkFloorPanel.length; f++) //각 층에 주차공간을 버튼과 라벨로 구현
		{
			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 10; j++)
				{
					if (i % 2 == 0)
					{
						//각 층에 버튼(2o개)을 생성하고 그 버튼을 JButton배열에 저장한다 , 생성시 색을 연두색으로 설정
						parkButton[buttonCount-1] = (JButton)parkFloorPanel[f].add(new JButton(""+ buttonCount));
						parkButton[buttonCount-1].setBackground(new Color(99, 0xFF, 99)); //버튼색은 연두색
						buttonCount++;
					}
					else //10개 주차공간 생성 후 다음 라인은 공백으로!
					{
						parkFloorPanel[f].add(new Label()); //주차공간 사이 중간에 공백을 넣기 위함
					}
				}
			}
		}
		System.out.println(labelCount);
		
		for (int i = 0; i < parkButton.length; i++)
		{
			//각 버튼의 리스너를 구현한 클래스로 익명클래스, 즉 각기 다른 객체 생성으로 핸들러 처리
			parkButton[i].addActionListener(new ParkingCarIn(frame, memCheck)); //버튼 클릭 시 발생하는 이벤트를 처리하기 위해 리스너를 구현한 클래스 객체를 생성
		}
	} //makeParkPlaceBtn() End
	
	public void makeFeeInfo() //메인 시스템 화면에 출력하게 될 주차 요금 안내판 이미지이다.
	{
		feeInfoIcon = new ImageIcon("주차요금안내판.png");
		changeSizeImg = feeInfoIcon.getImage(); //이미지의 크기를 변환하기 위해 Image객체 변수에 이미지 담기
		changeSizeImg = changeSizeImg.getScaledInstance(300,240, java.awt.Image.SCALE_SMOOTH); //원본 이미지의 크기 조정
		feeInfoIcon = new ImageIcon(changeSizeImg); //크기를 바꾼 이미지를 다시 ImageIcon에 넣는다.
		feeInfoImgLabel.setIcon(feeInfoIcon); //Label에 ImageIcon을 담는다.
		feeInfoImgLabel.setBounds(880, 250, 310, 300); //이미지가 담긴 Label의 크기와 위치 지정
		parkingMainFullScreen.add(feeInfoImgLabel); //이미지 Label 패널에 추가
	} //makeFeeInfo() End
	
	public void makeSystemBtn() //메인 시스템 화면에 나타내게 될 기능 버튼들이다.
	{
		//각 버튼 크기 조정
		buttonFullPanel.setBounds(875, 455, 310, 205); //버튼들을 따로 담기 위한 패널의 위치와 크기 지정
		buttonFullPanel.setBackground(Color.white); //버튼 패널의 배경색을 하얀색으로 지정
		parkingMainFullScreen.add(buttonFullPanel); //ContentePane에 buttonFullPanel add!
		
		//버튼 컴포넌트 6개 buttonFullPanel에 배치, 이미지의 크기를 버튼의 크기에 맞게 변경하여 버튼에 삽입한다.
		if (memCheck == 1) //이 두개의 버튼은 관리자만 필요한 것이다! 관리자로 로그인 했을 시에만 띄우도록 설정한다.
		{
			buttonFullPanel.add(feeCalcButton); //'요금정산'버튼 , 관리자만보이게하기
			feeCalcButton.setBounds(15, 0, 140, 60);
			feeCalcIcon = changeBtnImgSize(feeCalcIcon);
			feeCalcButton.setIcon(feeCalcIcon);
			
			buttonFullPanel.add(parkListButton); //'주차내역'버튼, 관리자만 보이게하기
			parkListButton.setBounds(165, 0, 140, 60);
			parkListIcon = changeBtnImgSize(parkListIcon);
			parkListButton.setIcon(parkListIcon);
		}
		
		buttonFullPanel.add(outCarButton); //'출차하기'버튼
		outCarButton.setBounds(15, 70, 140, 60);
		outCarIcon = changeBtnImgSize(outCarIcon);
		outCarButton.setIcon(outCarIcon);
		
		buttonFullPanel.add(parkStateBtn); //'주차현황'버튼
		parkStateBtn.setBounds(165, 70, 140, 60);
		parkStateIcon = changeBtnImgSize(parkStateIcon);
		parkStateBtn.setIcon(parkStateIcon);
		
		buttonFullPanel.add(prevButton); //'이전층'버튼
		prevButton.setBounds(15, 140, 140, 60);
		prevBtnIcon = changeBtnImgSize(prevBtnIcon);
		prevButton.setIcon(prevBtnIcon);
		
		buttonFullPanel.add(nextButton); //'다음층'버튼
		nextButton.setBounds(165, 140, 140, 60);
		nextBtnIcon = changeBtnImgSize(nextBtnIcon);
		nextButton.setIcon(nextBtnIcon);
	} //makeSystemBtn() End
	
	public ImageIcon changeBtnImgSize(ImageIcon imgIcon) //메인 뷰에 기능 버튼들에 들어갈 이미지의 크기를 변경해주는 메소드!
	{
		Image chaImg = imgIcon.getImage();
		chaImg = chaImg.getScaledInstance(150,60, java.awt.Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(chaImg);
		return imgIcon;
	}
	
	class ParkingLogout implements ActionListener //로그아웃 버튼 클릭 시 이벤트를 처리하는 핸들러
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			frame.dispose(); //현재 프레임을 종료시킨다. dispose()는 종료시켜주는 메소드이다.
			new ParkingStartView(); //다시 로그인 화면이 사용자에게 보이도록 새로운 프레임을 생성한다.
		}//
	} //ParkingLogout class End
	
} //ParkingMainView class End
