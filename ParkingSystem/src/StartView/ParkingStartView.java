package StartView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import FileManeger.FileSystem;
import Information.*;

import javax.swing.border.LineBorder;

public class ParkingStartView extends JFrame {
	Container frameContentPane; //전체화면인 프레임 위에 이미 생성되어 있는 컨텐트팬을 저장하기 위함, 이 컨텐트팬 위에서 GUI컴포넌트를 부착해야한다. (유의!)
	
	//처음 로그인 화면 상단에 이미지를 넣기 위한 변수들
	//자동차 이미지를 1초마다 띄우게 하기 위한 변수
	JLabel carImgLabel = new JLabel();
	ImageIcon carImgIcon[] = new ImageIcon[3]; //이미지를 가져올 ImageIcon배열 선언
	Thread thread;
	boolean threadFlag = true; //다음화면으로 넘어갈 때 실행중인 스레드를 정지시키고 넘어가게한다.
	
	//로그인 창 왼쪽에 넣을 로고이미지
	JLabel logoImgLabel = new JLabel();
	ImageIcon logoImgIcon; //이미지를 담을 ImageIcon
	Image changeSize; //원본이미지의 크기를 바꿀 Image클래스 객체
	
	//로그인, 회원가입 패널
	JPanel loginPanel = new JPanel(null);
	JLabel idLable = new JLabel("아이디 : ");
	JTextField idText = new JTextField("");
	JLabel passwordLabel = new JLabel("패스워드 : ");
	JTextField passwordText = new JTextField("");
	
	//로그인 버튼과 회원가입 버튼
	JButton loginBtn = new JButton("로그인");
	JButton newRegBtn = new JButton("회원가입");
	JButton notMemBtn = new JButton("비회원");
	
	int memCheck = 0; //관리자와 고객을 구분하기 위한 변수
	String memName = ""; //회원이면 그 회원의 이름을 System화면에 전달하기 위한 변수
	
	public ParkingStartView (String name) //프레임의 이름을 설정하면서 프레임을 생성하는 생성자다!
	{
		super(name); //프레임 생성시 제목을 주기위함, ParkingStart에서 new ParkingLogin() 생성 시 제목추가!
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임의 X를 눌렀을 시 종료
		setBounds(50, 50, 1200, 700); //프레임이 나타나는 위치와 크기 지정
		setResizable(false); //화면을 늘이고 줄일 수 없게 프레임 크기를 고정시킨다.
		
		frameContentPane = getContentPane(); //현재 생성된 프레임의 컨텐트팬을 저장한다. 이 컨텐트 팬 위에 컴포넌트들을 add하여 화면에 보이게 한다.
		frameContentPane.setLayout(null); //배치관리자를 null로! (내가 좌표로 마음대로 설정가능하다.) //로그인 화면은 좌표로 컴포넌트 설정해본다!
		frameContentPane.setBackground(Color.white); //생성된 프레임의 배경색을 하얀색으로 지정한다
		
		//각 버튼의 이벤트 핸들러는 한개씩만 존재해야 하므로 생성자에 넣어준다! , 이렇게 하는게 좋다!
		loginBtn.addActionListener(new LoginAction()); //'로그인'버튼 클릭 시
		newRegBtn.addActionListener(new ParkingNewMemJoin(this)); //'회원가입'버튼 클릭 시, 인자의 클래스로 이동한다.
		notMemBtn.addActionListener(new NotMemAction());
		
		//처음 화면 호출 전 로그인창에 아이디와 비밀번호 텍스트 필드를 초기화시킨다. 다음 사람들도 입력하게 하려면 이전의 정보가 지워져야한다.
		idText.setText("");
		passwordText.setText("");
		
		makeMoveImage(); //맨 처음 로그인 화면에 보이는 차가 움직이는 그림을 구현한 메소드
		login(); //메인화면 아래에 로그인과 회원가입이 달린 패널 출력
		new MakeMoveCar(this); //로그인 화면에서 자동차 로고 이미지가 자동으로 움직이게 하는 클래스의 객체를 생성한다.

		setVisible(true); //ParkingStartView가 상속받은 JFrame의 ContentPane On!
		
		FileSystem.loadMemberInfo(); //파일에 저장된 회원정보 리스트를 읽어온다.
		System.out.println("회원 리스트 : " + ParkingNewMemJoin.memList);
		
		boolean find = false; //관리자 id는 프로그램 관리자가 직접 설정해줘야한다. 처음 프로그램이 시작할 때 파일의 내용을 검사해서 관리자 id가 없으면 만들어서 넣어주고 아니면 그냥 넘어가게 코딩한다.
		for (MemberInfo mem : ParkingNewMemJoin.memList)
		{
			if (mem.getId().equals("admin"))
			{
				find = true; //이미 memList에 관리자 아이디가 있으면 추가 할 필요가 없다!
				break;
			}
		}
		if (find == false)
		{
			ParkingNewMemJoin.memList.add(new MemberInfo(1, "admin", "admin", "관리자", "010-4303-1013", "propose0506@naver.com")); //객체 생성과 동시에 
			FileSystem.saveMemberInfo(ParkingNewMemJoin.memList);
		}
	} //ParkingStartView 생성자 End
	
	public void makeMoveImage() 
	{
		for (int i = 0; i < carImgIcon.length; i++)
		{
			carImgIcon[i] = new ImageIcon("car"+(i+1)+".png"); //ImgIcon에 각각의 이미지를 집어넣는다.
		}
		carImgLabel.setBounds(312, 56, 800, 300); //라벨 위치와 크기 (이미지의 크기도 라벨의 크기만큼 바뀐다.)
		frameContentPane.add(carImgLabel); //컨텐트팬에 이미지넣을 라벨 추가
		
		thread = new Thread(new Runnable() { //첫 화면에 자동차 지나가게 하는 스레드
			@Override
			public void run() {
				while (threadFlag)
				{
					for (int i = 0; i < carImgIcon.length; i++)
					{
						carImgLabel.setIcon(carImgIcon[i]); //1초마다 라벨에 이미지를 바꾸어 넣는다.
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
				}
			}
		});
		thread.start(); //이미지 체인지 스레드 시작!
	} //makeMoveImage() End
	
	public void login() //아이디와 비밀번호를 입력하는 로그인 창을 구성한다.
	{
		loginPanel.setForeground(Color.WHITE);
		loginPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		loginPanel.setBounds(396, 415, 420, 200);  //아래 버튼과 라벨, 텍스트를 담기 위한 패널 위치 설정, (x위치, y위치, width크기, height크기)
		loginPanel.setBackground(Color.WHITE);
		frameContentPane.add(loginPanel); //로그인 화면을 담을 패널 지정
		idLable.setFont(new Font("굴림", Font.BOLD, 13));
		
		idLable.setBounds(174, 22, 66, 60); //아이디 입력창 구현
		loginPanel.add(idLable);
		idText.setBounds(236, 38, 172, 35);
		loginPanel.add(idText);
		passwordLabel.setFont(new Font("굴림", Font.BOLD, 13));
		
		passwordLabel.setBounds(160, 70, 80, 60); //패스워드 입력창 구현
		loginPanel.add(passwordLabel);
		passwordText.setBounds(236, 84, 172, 35);
		loginPanel.add(passwordText);
		
		loginBtn.setBounds(160, 131, 75, 35); //로그인 버튼
		loginBtn.setBackground(new Color(0xEEEEEE));
		loginBtn.setFont(new Font("굴림", Font.BOLD, 10));
		newRegBtn.setBounds(242, 131, 78, 35); //회원가입 버튼
		newRegBtn.setBackground(new Color(0xEEEEEE)); 
		newRegBtn.setFont(new Font("굴림", Font.BOLD, 10));
		notMemBtn.setBounds(328, 131, 75, 35); //비회원 버튼
		notMemBtn.setBackground(new Color(0xEEEEEE));
		notMemBtn.setFont(new Font("굴림", Font.BOLD, 10));
		
		//각 버튼 패널에 추가
		loginPanel.add(loginBtn);
		loginPanel.add(newRegBtn);
		loginPanel.add(notMemBtn);
		
		//로그인 창 왼쪽 로고이미지
		logoImgIcon = new ImageIcon("로고5.png"); //ImageIcon에 이미지를 담는다.
		changeSize = logoImgIcon.getImage(); //이미지의 크기를 변환하기 위해 Image객체 변수에 이미지 담기
		changeSize = changeSize.getScaledInstance(140,100, java.awt.Image.SCALE_SMOOTH); //원본 이미지의 크기 조정
		logoImgIcon = new ImageIcon(changeSize); //크기를 바꾼 이미지를 다시 ImageIcon에 넣는다.
		logoImgLabel.setIcon(logoImgIcon); //Label에 ImageIcon을 담는다.
		logoImgLabel.setBounds(10, 30, 140, 100); //이미지가 담긴 Label의 크기와 위치 지정
		loginPanel.add(logoImgLabel); //이미지 Label 패널에 추가
	} //login() End
	
	
	/*public void MakeMoveCar()
	{
		JLabel moveLabel = new JLabel();
		int x = 0;
		int y = 0;
		int xdir = 1;
		int ydir = 2;
		int i = 0;
		int j = 0;
		
		ImageIcon moveCarImgIcon;
		Image moveCarImg;
		
		frameContentPane.add(moveLabel);
		moveLabel.setBounds(x, y, 140, 100);
		moveCarImgIcon = new ImageIcon("로고1.png");	
		moveCarImg = moveCarImgIcon.getImage();
		moveCarImg = moveCarImg.getScaledInstance(140,100, java.awt.Image.SCALE_SMOOTH);
		moveCarImgIcon = new ImageIcon(moveCarImg);
		moveLabel.setIcon(moveCarImgIcon);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true)
				{
					// TODO Auto-generated method stub
					x = (int)(Math.random() * 1200);
					y = (int)(Math.random() * 700);
					i = (int)(Math.random() * 1200);
					j = (int)(Math.random() * 700);
					if (x < 0 || x > frameContentPane.getWidth())
					{
						xdir *= -1;
					}
					if (y < 0 || y > frameContentPane.getHeight())
					{
						ydir *= -1;
					}
					x += xdir;
					y += ydir;

					moveLabel.setLocation(x, y);
					//moveLabel.setLocation(i, j);
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}*/
	
	class MakeMoveCar { //로그인 화면에 로고 이미지가 움직이게 하는 클래스다.
		ParkingStartView frame;
		JLabel moveLabel = new JLabel();
		int x = 0;
		int y = 0;
		int xdir = 1;
		int ydir = 2;
		int i = 0;
		int j = 0;
		
		ImageIcon moveCarImgIcon;
		Image moveCarImg;

		public MakeMoveCar(ParkingStartView frame)
		{
			this.frame = frame;
			frame.add(moveLabel);
			moveLabel.setBounds(x, y, 140, 100);
			moveLabel.setVisible(true);
			
			moveCarImgIcon = new ImageIcon("로고1.png");	
			moveCarImg = moveCarImgIcon.getImage();
			moveCarImg = moveCarImg.getScaledInstance(140,100, java.awt.Image.SCALE_SMOOTH);
			moveCarImgIcon = new ImageIcon(moveCarImg);
			moveLabel.setIcon(moveCarImgIcon);
			
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true)
					{
						// TODO Auto-generated method stub
						/*x = (int)(Math.random() * 1200);
						y = (int)(Math.random() * 700);
						i = (int)(Math.random() * 1200);
						j = (int)(Math.random() * 700);*/
						if (x < 0 || x > frame.getWidth())
						{
							xdir *= -1; //화면의 바깥으로 나가는 경우 다시 돌아오게 하기 위함
						}
						if (y < 0 || y > frame.getHeight())
						{
							ydir *= -1; //화면의 바깥으로 나가는 경우 다시 돌아오게 하기 위함
						}
						x += xdir;
						y += ydir;

						moveLabel.setLocation(x, y); //이미지를 담은 Label의 위치가 계속 바뀐다.
						//moveLabel.setLocation(i, j);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			thread.start();
		}
	} //MakeMoveCar class End
	
	class LoginAction implements ActionListener //'로그인'버튼 클릭 시
	{
		@Override
		public void actionPerformed(ActionEvent e) { //'로그인'버튼 클릭 시 수행하는 메소드
			// TODO Auto-generated method stub
			//각 라벨의 문자열 값을 가져온다.
			String id = idText.getText(); 
			String password = passwordText.getText();
			int flag = 0; //ArrayList안에서 텍스트의 내용과 동일한 id와 password 내용을 찾았으면 flag = 1이다.
			
			//for - each문! , Collection형태(List, Set, Map 전부 다 가능하다!)에 저장된 내용(현재는 객체들이 저장되었다)들을 하나씩 꺼내서 접근할 수 있다.
			//현재 ArrayList에 저장된 객체가 Member객체이므로 아래와 같이 써주면 리스트에서 하나씩 접근해서 가져올 수 있다.
			for (MemberInfo mem : ParkingNewMemJoin.memList) //static으로 선언된 memList에 접근해서 Member객체를 하나씩 빼온다
			{
				if (id.equals(mem.getId())) //텍스트 창에 입력한 id와 ArrayList안에 있는 id가 같으면
				{
					if (password.equals(mem.getPassword())) //텍스트 창에 입력한 password와 ArrayList안에 있는 password가 같으면
					{
						memCheck = mem.getMemCheck(); //회원가입 시 관리자는 memCheck를 1, 회원은 2, 비회원은 3으로 저장한다.
						memName = mem.getName(); //회원가입 시 저장했던 이름을 가져와 저장한다.
						flag = 1;
						break;
					}
				} //if End
			} //for End
			
			if (flag == 0) //입력된 아이디 혹은 비밀번호가 저장되어 있는 List안의 내용과 일치하지 않다면 다시 로그인하게 만든다.
			{
				JOptionPane.showMessageDialog(frameContentPane, "입력하신 정보가 잘못되었습니다. 다시 입력해주세요","에러창", 0);
				idText.setText("");
				passwordText.setText("");
				idText.requestFocus(); //id텍스트 필드 창으로 커서를 자동으로 다시 위치시킨다
			}
			else //텍스트필드에 입력한 id와 password가 둘다 정확하면 다음화면으로 넘어가게 설정한다. 그전에 로그인 화면에 있는 이미지를 동작하게 하는 스레드를 정지시킨다.
			{
				threadFlag = false; //화면 자동차 그림 스레드를 정지시킨다. 다음화면은 이게 돌아갈 필요가 없다.
				new ParkingMainView(ParkingStartView.this, memCheck, memName); //현재 프레임을 ParkingSystem으로 전달한다. 현재 클래스 안이라서 프레임을 보내려면 클래스명.this를 해야한다. 클래스 밖이면 this로 써도 상관없다. 
				//memCheck 변수를 같이 보내 관리자와 고객의 화면을 구분짓는다. (1은 관리자, 2는 회원, 3은 비회원이다)
			}
		} //actionPerformed() End
	} //class LoginAction End
	
	class NotMemAction implements ActionListener //'비회원'버튼 클릭 시
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			threadFlag = false; //화면 자동차 그림 스레드를 정지시킨다. 다음화면은 이게 돌아갈 필요가 없다.
			memCheck = 3;
			memName = "비회원";
			new ParkingMainView(ParkingStartView.this, memCheck, memName);
		}
	} //NotMemAction class End
} //ParkingStartView class End
