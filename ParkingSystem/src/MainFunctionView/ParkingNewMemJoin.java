package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.ImageGraphicAttribute;

import javax.swing.*;
import javax.swing.event.*;

import FileManeger.FileSystem;
import Information.*;

import java.util.*;

public class ParkingNewMemJoin implements ActionListener {
	ParkingStartView frame; //맨 처음 생성된 Frame인 ParkingStartView의 frame을 받기 위함
	public static ArrayList<MemberInfo> memList = new ArrayList<MemberInfo>(); //모든 클래스에서 공유할 수 있도록 static으로 설정, 회원가입시 회원정보를 가지는 MemberInfo객체가 전부 이 ArrayList에 저장하여 관리한다.
	
	//회원가입 다이어로그 구성요소
	JDialog registerDialog; //가장 큰 틀이 될 다이어로그
	JPanel registerPanel = new JPanel(null); //회원가입폼도 좌표로 지정하자! 
	JLabel regTitleLabel = new JLabel("회원 가입");
	JLabel regIdLabel = new JLabel("아이디 : ");
	JTextField regIdText = new JTextField("", 12); //아이디는 12자리까지만 입력가능
	JButton regDupleBtn = new JButton("중복체크"); //아이디 중복 체크 버튼
	JLabel regPwdLabel = new JLabel("비밀번호 : ");
	JTextField regPwdText = new JTextField("",13); //비밀번호는 13자리까지 입력가능
	JLabel regNameLabel = new JLabel("이름 : ");
	JTextField regNameText = new JTextField(""); //이름 입력
	JLabel regPhoneLabel = new JLabel("핸드폰 : ");
	JTextField regPhoneText = new JTextField(""); //핸드폰 번호 입력
	JLabel regEmailLabel = new JLabel("이메일 : ");
	JTextField regEmailText = new JTextField(""); //이메일 주소 입력
	JButton regOkBtn = new JButton("가입완료"); //가입완료 버튼
	JButton regCancelBtn = new JButton("가입취소"); //가입취소 버튼
	
	ImageIcon dupleIcon = new ImageIcon("중복체크.png");
	ImageIcon regOkIcon = new ImageIcon("가입완료.png");
	ImageIcon regCancelIcon = new ImageIcon("취소.png");
	Image changeSize;
	
	public ParkingNewMemJoin (ParkingStartView frame)
	{
		this.frame = frame;
		
		//다이어로그 안의 버튼들의 이벤트 리스너는 생성자에 넣는게 좋다!, 1개씩만 존재해야 하므로!
		regDupleBtn.addActionListener(new regDupleAction()); //'중복확인'버튼 클릭 시
		regOkBtn.addActionListener(new regOkAction()); //'가입완료'버튼 클릭 시
		regCancelBtn.addActionListener(new regCancelAction()); //'가입취소'버튼 클릭 시
	} //ParkingNewMemJoin 생성자 End

	//'회원가입' 버튼 클릭 시 실행되는 이벤트 리스너
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//다이어로그 호출 전 각 텍스트필드 초기화
		regIdText.setText("");
		regPwdText.setText("");
		regNameText.setText("");
		regPhoneText.setText("");
		regEmailText.setText("");
		makeRegisterDialog(); //회원가입 버튼이 클릭되면 회원가입 다이어로그를 띄운다.
	}
	
	public void makeRegisterDialog()
	{
		registerDialog = new JDialog(frame, "회원가입 창");
		registerDialog.setBounds(500, 100, 300, 340); //(x위치, y위치, width크기, height크기)
		
		registerPanel.add(regTitleLabel); //Dialog 맨 윗줄에 제목
		regTitleLabel.setBounds(110, 20, 70, 20);
		
		registerPanel.add(regIdLabel); //아이디 입력창
		registerPanel.add(regIdText);
		registerPanel.add(regDupleBtn);
		regIdLabel.setBounds(25, 50, 100, 30);
		regIdText.setBounds(75, 50, 100, 30);
		regIdText.requestFocus(); //아이디 입력창에 포커스를 맞춘다.
		
		regDupleBtn.setBounds(180, 50, 90, 30); //중복체크 버튼
		dupleIcon = changeSize(dupleIcon); //중복체크 버튼 이미지 크기 조절
		regDupleBtn.setIcon(dupleIcon); //버튼에 이미지 씌우기
		
		registerPanel.add(regPwdLabel); //비밀번호 입력창
		registerPanel.add(regPwdText);
		regPwdLabel.setBounds(10, 85, 130, 30);
		regPwdText.setBounds(75, 85, 130, 30);
		
		registerPanel.add(regNameLabel); //이름 입력창
		registerPanel.add(regNameText);
		regNameLabel.setBounds(35, 120, 100, 30);
		regNameText.setBounds(75, 120, 100, 30);
		
		registerPanel.add(regPhoneLabel); //휴대폰 번호 입력창
		registerPanel.add(regPhoneText);
		regPhoneLabel.setBounds(25, 155, 130, 30);
		regPhoneText.setBounds(75, 155, 130, 30);
		
		registerPanel.add(regEmailLabel); //이메일 주소 입력창
		registerPanel.add(regEmailText);
		regEmailLabel.setBounds(25, 190, 130, 30);
		regEmailText.setBounds(75, 190, 160, 30);
		
		registerPanel.add(regOkBtn); //'가입완료'버튼
		registerPanel.add(regCancelBtn); //'가입취소'버튼
		regOkBtn.setBounds(50, 230, 90, 30);
		regOkIcon = changeSize(regOkIcon); //가입완료 버튼 이미지 크기 조절
		regOkBtn.setIcon(regOkIcon); //가입완료 버튼에 이미지 씌우기
		regCancelBtn.setBounds(155, 230, 90, 30);
		regCancelIcon = changeSize(regCancelIcon); //가입취소 버튼 이미지 크기 조절
		regCancelBtn.setIcon(regCancelIcon); //가입취소 버튼에 이미지 씌우기
		
		registerDialog.add(registerPanel);
		registerDialog.setVisible(true);
	} //makeRegisterDialog() End
	
	public ImageIcon changeSize(ImageIcon imgIcon) //회원가입 다이어로그에 있는 버튼의 이미지 크기를 변환시켜주는 메소드
	{
		Image chaImg = imgIcon.getImage();
		chaImg = chaImg.getScaledInstance(100,30, java.awt.Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(chaImg);
		return imgIcon;
	}
	
	class regDupleAction implements ActionListener //'중복체크'버튼 클릭 시
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String id = regIdText.getText();
			
			int flag = 0; //아이디 중복 검사 flag
			//for - each문! , Collection형태에 저장된 객체들을 하나씩 꺼내서 접근할 수 있다.
			//현재 ArrayList에 저장된 객체가 Member객체이므로 아래와 같이 써주면 리스트에서 하나씩 접근해서 가져올 수 있다.
			for (MemberInfo mem : memList)
			{
				if (id.equals(mem.getId())) //해당 Member객체에 들어있는 id값을 받아온다
				{
					flag = 1;
					break;
				}
			}
			
			if (flag == 1) //중복된 아이디가 있는경우
			{
				JOptionPane.showMessageDialog(frame, "입력하신 id가 이미 있습니다. 다시 입력해주세요","에러창", 0);
				regIdText.setText(""); //다시 입력하게 공백으로!	
				regIdText.requestFocus(); //포커스를 텍스트필드로한다
			}
			else //중복된 아이디가 없는 경우
			{
				JOptionPane.showMessageDialog(frame, "사용하실 수 있는 id입니다.","알림창", JOptionPane.INFORMATION_MESSAGE);
				regPwdText.requestFocus();
			}
		}
	} //class regDupleAction End
	
	class regOkAction implements ActionListener //'가입완료'버튼 이벤트 리스너 구현
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//텍스트 필드에 입력된 값들을 가져와서 저장한다.
			String id = regIdText.getText();
			String pwd = regPwdText.getText();
			String name = regNameText.getText();
			String phone = regPhoneText.getText();
			String email = regEmailText.getText();
			int flag = 0;
			
			//for - each문! , Collection형태에 저장된 객체들을 하나씩 꺼내서 접근할 수 있다.
			//현재 ArrayList에 저장된 객체가 Member객체이므로 아래와 같이 써주면 리스트에서 하나씩 접근해서 가져올 수 있다.
			for( MemberInfo mem : memList ) 
			{
				if( id.equals(mem.getId()))
				{
					flag = 1; //중복 id를 찾으면 flag는 1로하고 while문을 빠져나간다
					break;
				}
			}

			if (flag == 1) //중복된 아이디가 있는 경우
			{
				JOptionPane.showMessageDialog(frame, "입력하신 id가 이미 있습니다. 다시 입력해주세요","에러창", 0);
				regIdText.setText(""); //다시 입력하게 공백으로!	
				regIdText.requestFocus(); //포커스를 텍스트필드로한다
			}
			else if (id.equals("") || pwd.equals("") || name.equals("") || phone.equals("") || email.equals("")) //어디 하나라도 빈 문자열로 입력하지 않고 '가입완료'를 누른경우
			{
				JOptionPane.showMessageDialog(frame, "빈 공간이 있습니다. 다시 입력해주세요","에러창", 0);
			}
			else //리스트에 중복된 아이디가 없는 경우
			{
				memList.add(new MemberInfo(2,id, pwd, name, phone, email)); //입력한 id와 같은 id가 없으면 ArrayList에 회원가입 내용을 저장한다. , 회원가입을 하는 인원들은 모두 memCheck값이 2로 저장된다.
				FileSystem.saveMemberInfo(memList);
				JOptionPane.showMessageDialog(frame, "회원가입이 완료되었습니다.");
				System.out.println("체크! --------- 회원가입이 완료되었습니다. ");
				registerDialog.setVisible(false);
				registerDialog.dispose();
			}
		}
	} //regOkAction class End
	
	class regCancelAction implements ActionListener //'가입취소'버튼 클릭 시
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			registerDialog.setVisible(false);
			registerDialog.dispose();
		}
	} //regCancelAction class End
}
