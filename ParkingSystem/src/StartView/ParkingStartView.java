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
	ParkingStartView frame;
	Container frameContentPane; //��üȭ���� ������ ���� �̹� �����Ǿ� �ִ� ����Ʈ���� �����ϱ� ����, �� ����Ʈ�� ������ GUI������Ʈ�� �����ؾ��Ѵ�. (����!)
	
	//���� �ΰ� �̹����� �����̰� �ϱ� ���� ����
	JLabel moveLabel = new JLabel(); //�����̴� �̹����� ���� Label
	int x;
	int y;
	int xdir;
	int ydir;
	ImageIcon moveCarImgIcon;
	Image moveCarImg;
	Thread thread2;
	boolean thread2Flag = true; //����ȭ������ �Ѿ �� �������� �����带 ������Ű�� �Ѿ���Ѵ�.
	
	//ó�� �α��� ȭ�� ��ܿ� �̹����� �ֱ� ���� ������
	//�ڵ��� �̹����� 1�ʸ��� ���� �ϱ� ���� ����
	JLabel carImgLabel = new JLabel();
	ImageIcon carImgIcon[] = new ImageIcon[3]; //�̹����� ������ ImageIcon�迭 ����
	Thread thread1;
	boolean thread1Flag = true; //����ȭ������ �Ѿ �� �������� �����带 ������Ű�� �Ѿ���Ѵ�.
	
	//�α��� â ���ʿ� ���� �ΰ��̹���
	JLabel logoImgLabel = new JLabel();
	ImageIcon logoImgIcon; //�̹����� ���� ImageIcon
	Image changeSize; //�����̹����� ũ�⸦ �ٲ� ImageŬ���� ��ü
	
	//�α���, ȸ������ �г�
	JPanel loginPanel = new JPanel(null);
	JLabel idLable = new JLabel("���̵� : ");
	JTextField idText = new JTextField("");
	JLabel passwordLabel = new JLabel("�н����� : ");
	JTextField passwordText = new JTextField("");
	
	//�α��� ��ư�� ȸ������ ��ư
	JButton loginBtn = new JButton("�α���");
	ImageIcon loginIcon = new ImageIcon("�α���.png");
	JButton newRegBtn = new JButton("ȸ������");
	ImageIcon regIcon = new ImageIcon("ȸ������.png");
	JButton notMemBtn = new JButton("��ȸ��");
	ImageIcon notMemIcon = new ImageIcon("��ȸ��.png");

	int memCheck = 0; //�����ڿ� ���� �����ϱ� ���� ����
	String memName = ""; //ȸ���̸� �� ȸ���� �̸��� Systemȭ�鿡 �����ϱ� ���� ����
	
	public ParkingStartView()
	{
		this("���������ý���"); //�α׾ƿ� Ŭ�� �� �� �������� �������� �ٽ� ���ο� �������� �����ؼ� ����ڿ��� �����ش�.
	} //ParkingStartView() ������ End
	
	public ParkingStartView (String name) //�������� �̸��� �����ϸ鼭 �������� �����ϴ� �����ڴ�!
	{
		super(name); //������ ������ ������ �ֱ�����, ParkingStart���� new ParkingLogin() ���� �� �����߰�!
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //�������� X�� ������ �� ����
		setBounds(50, 50, 1200, 700); //�������� ��Ÿ���� ��ġ�� ũ�� ����
		setResizable(false); //ȭ���� ���̰� ���� �� ���� ������ ũ�⸦ ������Ų��.
		
		frameContentPane = getContentPane(); //���� ������ �������� ����Ʈ���� �����Ѵ�. �� ����Ʈ �� ���� ������Ʈ���� add�Ͽ� ȭ�鿡 ���̰� �Ѵ�.
		frameContentPane.setLayout(null); //��ġ�����ڸ� null��! (���� ��ǥ�� ������� ���������ϴ�.) //�α��� ȭ���� ��ǥ�� ������Ʈ �����غ���!
		frameContentPane.setBackground(Color.white); //������ �������� ������ �Ͼ������ �����Ѵ�
		
		//�� ��ư�� �̺�Ʈ �ڵ鷯�� �Ѱ����� �����ؾ� �ϹǷ� �����ڿ� �־��ش�! , �̷��� �ϴ°� ����!
		loginBtn.addActionListener(new LoginAction()); //'�α���'��ư Ŭ�� ��
		newRegBtn.addActionListener(new ParkingNewMemJoin(this)); //'ȸ������'��ư Ŭ�� ��, ������ Ŭ������ �̵��Ѵ�.
		notMemBtn.addActionListener(new NotMemAction());
		
		//ó�� ȭ�� ȣ�� �� �α���â�� ���̵�� ��й�ȣ �ؽ�Ʈ �ʵ带 �ʱ�ȭ��Ų��. ���� ����鵵 �Է��ϰ� �Ϸ��� ������ ������ ���������Ѵ�.
		idText.setText("");
		passwordText.setText("");
		
		makeMoveImage(); //�� ó�� �α��� ȭ�鿡 ���̴� ���� �����̴� �׸��� ������ �޼ҵ�
		login(); //����ȭ�� �Ʒ��� �α��ΰ� ȸ�������� �޸� �г� ���
		MakeMoveCar(); //�α��� ȭ�鿡�� �ڵ��� �ΰ� �̹����� �ڵ����� �����̰� �ϴ� Ŭ������ ��ü�� �����Ѵ�.

		setVisible(true); //ParkingStartView�� ��ӹ��� JFrame�� ContentPane On!
		
		FileSystem.loadMemberInfo(); //���Ͽ� ����� ȸ������ ����Ʈ�� �о�´�.
		System.out.println("ȸ�� ����Ʈ : " + ParkingNewMemJoin.memList);
		
		boolean find = false; //������ id�� ���α׷� �����ڰ� ���� ����������Ѵ�. ó�� ���α׷��� ������ �� ������ ������ �˻��ؼ� ������ id�� ������ ���� �־��ְ� �ƴϸ� �׳� �Ѿ�� �ڵ��Ѵ�.
		for (MemberInfo mem : ParkingNewMemJoin.memList)
		{
			if (mem.getId().equals("admin"))
			{
				find = true; //�̹� memList�� ������ ���̵� ������ �߰� �� �ʿ䰡 ����!
				break;
			}
		}
		if (find == false)
		{
			ParkingNewMemJoin.memList.add(new MemberInfo(1, "admin", "admin", "������", "010-4303-1013", "propose0506@naver.com")); //��ü ������ ���ÿ� 
			FileSystem.saveMemberInfo(ParkingNewMemJoin.memList);
		}
	} //ParkingStartView ������ End
	
	public void MakeMoveCar() //�ΰ� �̹����� ������ ȭ�� �ȿ��� �����̰� �ϴ� �޼ҵ�
	{
		//�ΰ� �̹����� ���� Label�� �����̰� �ϱ� ���� ��ǥ ���� �����̴�.
		x = 0;
		y = 0;
		xdir = 1;
		ydir = 2;
		
		frameContentPane.add(moveLabel); //���� �������� ����Ʈ �ҿ� �ΰ� �̹����� ��� Label�� add�Ѵ�.
		moveLabel.setBounds(x, y, 80, 80); //�ΰ� �̹����� ���� Label�� ��ġ�� ũ�� ����
		moveCarImgIcon = new ImageIcon("�ΰ�1.png"); //�ΰ� �̹����� �����ͼ�
		moveCarImg = moveCarImgIcon.getImage(); //Image��ü ������ ��� ����� ����
		moveCarImg = moveCarImg.getScaledInstance(80,80, java.awt.Image.SCALE_SMOOTH); //�̹��� ������ ����
		moveCarImgIcon = new ImageIcon(moveCarImg);  //����� ������ �̹����� �ٽ� ��ƿ´�.
		moveLabel.setIcon(moveCarImgIcon); //Label�� �̹����� �ִ´�.
		
		thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (thread2Flag) //Flag�� true�϶��� �����尡 ����ȴ�.
				{
					// TODO Auto-generated method stub									
					if (x == 0) //���� ������ �Ʒ��� �̹��� �̵�
					{
						y += ydir;
					}
					
					if (y == 590) //���� �Ʒ����� ���������� �̹��� �̵�
					{
						x += xdir;
					}
					
					if (x == 1115) //������ �Ʒ����� ���� �̹��� �̵�
					{
						y -= ydir;
					}
					
					if (y == 0) //������ ������ �������� �̹��� �̵�
					{
						x -= xdir;
					}

					moveLabel.setLocation(x, y); //�̹����� ���� Label�� ������ ȭ�鿡�� �Դ� ���� �ϰ� �����Ѵ�.
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread2.start();
	} //MakeMoveCar() End
	
	public void makeMoveImage() 
	{
		for (int i = 0; i < carImgIcon.length; i++)
		{
			carImgIcon[i] = new ImageIcon("car"+(i+1)+".png"); //ImgIcon�� ������ �̹����� ����ִ´�.
		}
		carImgLabel.setBounds(312, 80, 800, 280); //�� ��ġ�� ũ�� (�̹����� ũ�⵵ ���� ũ�⸸ŭ �ٲ��.)
		frameContentPane.add(carImgLabel); //����Ʈ�ҿ� �̹������� �� �߰�
		
		thread1 = new Thread(new Runnable() { //ù ȭ�鿡 �ڵ��� �������� �ϴ� ������
			@Override
			public void run() {
				while (thread1Flag)
				{
					for (int i = 0; i < carImgIcon.length; i++)
					{
						carImgLabel.setIcon(carImgIcon[i]); //1�ʸ��� �󺧿� �̹����� �ٲپ� �ִ´�.
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
				}
			}
		});
		thread1.start(); //�̹��� ü���� ������ ����!
	} //makeMoveImage() End
	
	public void login() //���̵�� ��й�ȣ�� �Է��ϴ� �α��� â�� �����Ѵ�.
	{
		loginPanel.setForeground(Color.WHITE);
		loginPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		loginPanel.setBounds(396, 395, 420, 200);  //�Ʒ� ��ư�� ��, �ؽ�Ʈ�� ��� ���� �г� ��ġ ����, (x��ġ, y��ġ, widthũ��, heightũ��)
		loginPanel.setBackground(Color.WHITE);
		frameContentPane.add(loginPanel); //�α��� ȭ���� ���� �г� ����
		idLable.setFont(new Font("����", Font.BOLD, 13));
		
		idLable.setBounds(174, 22, 66, 60); //���̵� �Է�â ����
		loginPanel.add(idLable);
		idText.setBounds(236, 38, 172, 35);
		loginPanel.add(idText);
		passwordLabel.setFont(new Font("����", Font.BOLD, 13));
		
		passwordLabel.setBounds(160, 70, 80, 60); //�н����� �Է�â ����
		loginPanel.add(passwordLabel);
		passwordText.setBounds(236, 84, 172, 35);
		loginPanel.add(passwordText);
		
		loginBtn.setBounds(160, 131, 75, 35); //�α��� ��ư
		//loginBtn.setBackground(new Color(0xEEEEEE));
		//loginBtn.setFont(new Font("����", Font.BOLD, 10));
		loginIcon = changeSize(loginIcon); //�α��ι�ư�� �� �̹����� ũ�⸦ �޼ҵ带 ���� ��ȭ��Ű�� �޾ƿ´�.
		loginBtn.setIcon(loginIcon); //�α��� ��ư�� �̹����� �ִ´�.
		
		newRegBtn.setBounds(242, 131, 75, 35); //ȸ������ ��ư
		//newRegBtn.setBackground(new Color(0xEEEEEE)); 
		//newRegBtn.setFont(new Font("����", Font.BOLD, 10));
		regIcon = changeSize(regIcon); //ȸ������ ��ư�� �� �̹����� ũ�� ����
		newRegBtn.setIcon(regIcon); //ȸ������ ��ư�� �̹��� ����
		
		notMemBtn.setBounds(328, 131, 75, 35); //��ȸ�� ��ư
		//notMemBtn.setBackground(new Color(0xEEEEEE));
		//notMemBtn.setFont(new Font("����", Font.BOLD, 10));
		notMemIcon = changeSize(notMemIcon); //��ȸ�� ��ư�� �� �̹����� ũ�� ����
		notMemBtn.setIcon(notMemIcon); //��ȸ�� ��ư�� �̹��� ����
		
		//�� ��ư �гο� �߰�
		loginPanel.add(loginBtn);
		loginPanel.add(newRegBtn);
		loginPanel.add(notMemBtn);
		
		//�α��� â ���� �ΰ��̹���
		logoImgIcon = new ImageIcon("�ΰ�5.png"); //ImageIcon�� �̹����� ��´�.
		changeSize = logoImgIcon.getImage(); //�̹����� ũ�⸦ ��ȯ�ϱ� ���� Image��ü ������ �̹��� ���
		changeSize = changeSize.getScaledInstance(140,100, java.awt.Image.SCALE_SMOOTH); //���� �̹����� ũ�� ����
		logoImgIcon = new ImageIcon(changeSize); //ũ�⸦ �ٲ� �̹����� �ٽ� ImageIcon�� �ִ´�.
		logoImgLabel.setIcon(logoImgIcon); //Label�� ImageIcon�� ��´�.
		logoImgLabel.setBounds(10, 30, 140, 100); //�̹����� ��� Label�� ũ��� ��ġ ����
		loginPanel.add(logoImgLabel); //�̹��� Label �гο� �߰�
	} //login() End
	
	public ImageIcon changeSize(ImageIcon imgIcon) //�α��� �г� ��ư���� �̹����� ��ȯ��Ű�� �޼ҵ��.
	{
		Image chaImg = imgIcon.getImage();
		chaImg = chaImg.getScaledInstance(86,35, java.awt.Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(chaImg);
		return imgIcon;
	}
	
	class LoginAction implements ActionListener //'�α���'��ư Ŭ�� ��
	{
		@Override
		public void actionPerformed(ActionEvent e) { //'�α���'��ư Ŭ�� �� �����ϴ� �޼ҵ�
			// TODO Auto-generated method stub
			//�� ���� ���ڿ� ���� �����´�.
			String id = idText.getText(); 
			String password = passwordText.getText();
			int flag = 0; //ArrayList�ȿ��� �ؽ�Ʈ�� ����� ������ id�� password ������ ã������ flag = 1�̴�.
			
			//for - each��! , Collection����(List, Set, Map ���� �� �����ϴ�!)�� ����� ����(����� ��ü���� ����Ǿ���)���� �ϳ��� ������ ������ �� �ִ�.
			//���� ArrayList�� ����� ��ü�� Member��ü�̹Ƿ� �Ʒ��� ���� ���ָ� ����Ʈ���� �ϳ��� �����ؼ� ������ �� �ִ�.
			for (MemberInfo mem : ParkingNewMemJoin.memList) //static���� ����� memList�� �����ؼ� Member��ü�� �ϳ��� ���´�
			{
				if (id.equals(mem.getId())) //�ؽ�Ʈ â�� �Է��� id�� ArrayList�ȿ� �ִ� id�� ������
				{
					if (password.equals(mem.getPassword())) //�ؽ�Ʈ â�� �Է��� password�� ArrayList�ȿ� �ִ� password�� ������
					{
						memCheck = mem.getMemCheck(); //ȸ������ �� �����ڴ� memCheck�� 1, ȸ���� 2, ��ȸ���� 3���� �����Ѵ�.
						memName = mem.getName(); //ȸ������ �� �����ߴ� �̸��� ������ �����Ѵ�.
						flag = 1;
						break;
					}
				} //if End
			} //for End
			
			if (flag == 0) //�Էµ� ���̵� Ȥ�� ��й�ȣ�� ����Ǿ� �ִ� List���� ����� ��ġ���� �ʴٸ� �ٽ� �α����ϰ� �����.
			{
				JOptionPane.showMessageDialog(frameContentPane, "�Է��Ͻ� ������ �߸��Ǿ����ϴ�. �ٽ� �Է����ּ���","����â", 0);
				idText.setText("");
				passwordText.setText("");
				idText.requestFocus(); //id�ؽ�Ʈ �ʵ� â���� Ŀ���� �ڵ����� �ٽ� ��ġ��Ų��
			}
			else //�ؽ�Ʈ�ʵ忡 �Է��� id�� password�� �Ѵ� ��Ȯ�ϸ� ����ȭ������ �Ѿ�� �����Ѵ�. ������ �α��� ȭ�鿡 �ִ� �̹����� �����ϰ� �ϴ� �����带 ������Ų��.
			{
				thread1Flag = false; //ȭ�� �ڵ��� �׸� �����带 ������Ų��. ����ȭ���� �̰� ���ư� �ʿ䰡 ����.
				thread2Flag = false; //���� �ΰ� �̹����� �����̴� �����带 ������Ų��. ����ȭ���� �̰� ���ư� �ʿ䰡 ����.
				new ParkingMainView(ParkingStartView.this, memCheck, memName); //���� �������� ParkingSystem���� �����Ѵ�. ���� Ŭ���� ���̶� �������� �������� Ŭ������.this�� �ؾ��Ѵ�. Ŭ���� ���̸� this�� �ᵵ �������. 
				//memCheck ������ ���� ���� �����ڿ� ���� ȭ���� �������´�. (1�� ������, 2�� ȸ��, 3�� ��ȸ���̴�)
			}
		} //actionPerformed() End
	} //class LoginAction End
	
	class NotMemAction implements ActionListener //'��ȸ��'��ư Ŭ�� ��
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			thread1Flag = false; //ȭ�� �ڵ��� �׸� �����带 ������Ų��. ����ȭ���� �̰� ���ư� �ʿ䰡 ����.
			thread2Flag = false; //���� �ΰ� �̹����� �����̴� �����带 ������Ų��. ����ȭ���� �̰� ���ư� �ʿ䰡 ����.
			memCheck = 3; //��ȸ���� �����ȣ�� 3���̴�!
			memName = "��ȸ��";
			new ParkingMainView(ParkingStartView.this, memCheck, memName);
		}
	} //NotMemAction class End
} //ParkingStartView class End
