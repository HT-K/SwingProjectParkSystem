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
	ParkingStartView frame; //ParkingStartView���� ������ JFrame�� �ޱ� ���� ����
	JPanel parkingMainFullScreen = new JPanel(null); //ParkingStartView���� ������ JFrame�� ContentPane�� ���⼭ JPanel�� �����Ͽ� ȭ���� �Ѿ�� �Ѵ�.
	
	//�ΰ��̹����� ��ܿ� �ֱ� ���� ������Ʈ
	ImageIcon logoImgIcon;
	JLabel logoImgLabel = new JLabel();
	Image changeSizeImg; //ImageIcon ��ü�δ� ����� �����ϱⰡ �����. Image��ü�� �̿��ؼ� �̹��� ����� �����Ѵ�.
	
	//��ܿ� Main �����̹����� ���Եȴ�.
	ImageIcon mainImgIcon[] = new ImageIcon[7]; //���� ȭ�� ��ܿ� �̹����� �����ϱ� ���� �̹��������� ��ü�̿�
	JLabel mainImgLabel = new JLabel(); //�̹����� ������� ��
	
	//��¥�� �ð��� ��� ���� ������Ʈ
	ImageIcon timeImgIcon;
	JLabel timeImgLabel;
	JLabel pDateLabel = new JLabel(); //����, ��, ���� ��� ���� Label
	JLabel pTimeLabel = new JLabel(); //�ð��� ��� ���� Label
	
	//�̹��� �Ʒ��κп� ��������(��ư)�� �����Ѵ�.
	CardLayout cardLayout = new CardLayout(); //���������� ���̾ƿ��� CardLayout�̾���Ѵ�.
	JPanel cardPanel = new JPanel(cardLayout); //�߰��� ���� �� �������� �г��� �����ϴ� �гη� ī�巹�̾ƿ��� �ʿ��ϴ�.
	JPanel[] parkFloorPanel = new JPanel[3]; //3������ �̹Ƿ� 3���� ����, CardLayout���� ������ cardPanel�� add�Ѵ�.
	int cardCount = 0; //CardLayout ����, ���� ������ �ϱ����� ����
	
	//ActionEvent�� ���� ��ư�� ������ JButtonŬ������ ��ü �迭 ���� ,������ �� �������� static���� ����
	public static JButton[] parkButton = new JButton[60];
	
	//�� �������� ��ư ������ Label���� ������ �迭 ����
	JLabel parkLabel;
	
	//���ʿ� ���̺�, ��ư�� �����Ѵ�.
	JPanel buttonFullPanel = new JPanel(null); //��ư ���� ��� ���� �г�
	JButton parkListButton = new JButton("��������"); //�����ڸ�
	ImageIcon parkListIcon = new ImageIcon("��������.jpg");
	JButton feeCalcButton = new JButton("�������"); //�����ڸ�
	ImageIcon feeCalcIcon = new ImageIcon("�������.jpg");
	JButton outCarButton = new JButton("�����ϱ�"); //��, ������
	ImageIcon outCarIcon = new ImageIcon("����.jpg");
	JButton parkStateBtn = new JButton("������Ȳ"); //��, ������
	ImageIcon parkStateIcon = new ImageIcon("������Ȳ.jpg");
	JButton prevButton = new JButton("������"); //��, ������
	ImageIcon prevBtnIcon = new ImageIcon("������.jpg");
	JButton nextButton = new JButton("������"); //��, ������
	ImageIcon nextBtnIcon = new ImageIcon("������.jpg");
	
	//ȸ���� �̸��� �ð� �ؿ� ��� ��Ų��.
	JLabel memNameLabel = new JLabel();
	JLabel afterNameLabel = new JLabel();
	JButton logoutBtn = new JButton("�α׾ƿ�"); //�α׾ƿ� ��ư, ������ �α��� ȭ������ �Ѿ��.
	ImageIcon logoutIcon = new ImageIcon("�α׾ƿ�.png");
	
	//���� �޴� ���̺�
	String[] colName = {"������", "ȸ��", "��ȸ��"}; //���� ��Ÿ�� ��� �����!
	String[][] rowName = new String[4][3]; //�࿡ ����� ������ ���̱� ���� 3X3�� ���̺����¸� ���� 2�����迭 ����
	DefaultTableModel parkTableModel; //DefaultTabelModel (vector, vector)���·� ��뿹��
	JTable parkJTable; //DefaultTableModel�� ��� ���� JTable����
	
	//���� ��� �ȳ���
	ImageIcon feeInfoIcon;
	JLabel feeInfoImgLabel = new JLabel();
	
	//�����ڿ� ȸ�� ����
	int memCheck = 0;
	String memName = "";
	
	public ParkingMainView (ParkingStartView frame, int memCheck , String memName)
	{
		this.frame = frame; //������ ParkingStartView�� frame�� �޾ƿ´�.
		this.memCheck = memCheck; //ȸ�����Խ� �ڵ����� ����Ǵ� MemberInfo�� memCheck�� ������ ���������� ȸ������ ��ȸ������ �����Ѵ�.
		this.memName = memName; //ȭ�鿡 ȸ�� �̸��� ���� ���� �α��� �� memList���� ȸ���� �̸��� �޾ƿ´�. �����ڴ� ������, ��ȸ���� ��ȸ������ ��Ʈ�� ���� �޾ƿ´�.
		this.frame.setContentPane(parkingMainFullScreen); //ParkingStartView�� ContentPane�̾��� ���� ���� Ŭ������ JPanel�� ���������μ� ȭ���� �ٲ۴�. ȭ�� ��ȯ�� �̷��� ����!!!
		
		view(); //�����ý��� ����ȭ���� ������ view()�޼ҵ� ȣ��
		
		//addActionListener�� 1���� ����� �Ǵϱ� �����ڿ� �־�δ°��� ����
		outCarButton.addActionListener(new ParkingCarOut(frame, memCheck)); //'����ϱ�'�� Ŭ�� �� �߻��� �̺�Ʈ�� ó���ϴ� �����ʸ� ������ Ŭ���� ����
		feeCalcButton.addActionListener(new ParkingFeeList(frame)); //'�������'�� Ŭ�� �� �߻��� �̺�Ʈ�� ó���ϴ� �����ʸ� ������ Ŭ���� ����
		parkListButton.addActionListener(new ParkingCarList(frame)); //'��������'�� Ŭ�� �� �߻��� �̺�Ʈ�� ó���ϴ� �����ʸ� ������ Ŭ���� ����
		parkStateBtn.addActionListener(new ParkingCarState(frame, memCheck)); //'������Ȳ'�� Ŭ�� �� �߻��� �̺�Ʈ�� ó���ϴ� �����ʸ� ������ Ŭ���� ��ü ����
		logoutBtn.addActionListener(new ParkingLogout());
		
		//���Ͽ� ����� ������� ���� �ҷ��´�.
		FileSystem.loadCarInfo(); //���Ͽ� ����� �������� ����Ʈ�� �о�´�.
		System.out.println("���� ����Ʈ ; " + ParkingCarIn.parkCarList); //���Ͽ� ����� ����Ʈ ���� Ȯ��.
		for (ParkCarInfo car : ParkingCarIn.parkCarList) //���Ͽ��� �о�� ����Ʈ�� �������� �������� ������ ������ �����ý��ۿ� �����Ų��. (�����Ǿ��ִ� ������ ��� ������ ǥ��!)
		{
			 JButton complete = ParkingMainView.parkButton[car.getparkPlaceNum()-1]; //�����Ϸ� �ÿ� Ŭ���ߴ� ��ư�� ������ �޾ƿ´�.
			 complete.setText("������"); //���α׷��� ���� �����ÿ� �����Ǿ��ִ� �κ��� �ʱ�ȭ���� �ʵ��� ���Ͽ� ����� List�� ������ ���� �ٽ� ���������� �ٲ��ش�
			 complete.setBackground(new Color(0xFF, 66, 66)); //�������� ������ ������
			 complete.setEnabled(false); //�о�� �����ʹ�� �������� ���� ��Ȱ��ȭ ó���Ѵ�.
		}
		
		FileSystem.loadParkListInfo(); //���Ͽ� ����� �������� ����Ʈ�� �о�´�.
		System.out.println("���� ����Ʈ; " + ParkingCarIn.parkPrintList);
		
		FileSystem.loadFeeListInfo(); //���Ͽ� ����� ��ݳ��� ����Ʈ�� �о�´�
		System.out.println("��� ����Ʈ : " + ParkingCarOut.feeList);
	} //ParkingMainView ������ End
	
	public void view() //�����ý��� ����ȭ���� ������ �޼ҵ� ���� ȣ���Ѵ�.
	{
		makeParkLogoImg();//�ΰ��̹��� ���
		makeParkMainImg(); //�� ���� ��Ÿ���� �̹���
		makeDateTime(); //�� ���� ��Ÿ���� ��¥�� �ð�
		makeTimeImg(); //�ð��� �� �̹���
		makePrintName(); //���� �� �̸��� ����ִ�
		makeParkPlaceBtn(); //�������� ��ư�� ����
		if (memCheck == 2 || memCheck == 3) //ȸ��, ��ȸ���� ���
		{
			makeFeeInfo(); //������� �ȳ���
		}
		
		if (memCheck == 1) //�����ڸ� ����������ȣ����Ʈ�� ��µǰ� �Ѵ�.
		{
			new ParkingCarNumList(frame);
		}
		makeSystemBtn(); //��� ��ư��
	} //view() End
	
	public void makeParkLogoImg() //��ܿ� �ΰ��̹����� ���� �޼ҵ�
	{
		logoImgIcon = new ImageIcon("�ΰ�5.png");
		changeSizeImg = logoImgIcon.getImage(); //�ΰ��̹����� ũ�⸦ ��ȯ�ϱ� ���� ImageŬ���� ��ü�� �̿��Ѵ�.
		changeSizeImg = changeSizeImg.getScaledInstance(140, 120, java.awt.Image.SCALE_SMOOTH); //�ΰ��̹����� ũ�⸦ 140, 140���� ��ȯ��Ų��.
		logoImgIcon = new ImageIcon(changeSizeImg); //��ȯ�� ũ���� �̹����� ImageIcon�� ��´�.
		logoImgLabel.setIcon(logoImgIcon);
		logoImgLabel.setBounds(30, 0, 140, 200); //�ΰ��̹����� ���� Label�� ��ġ�� ũ�� ����
		parkingMainFullScreen.add(logoImgLabel); //�ΰ��̹����� ���� Label�� ����Ʈ�ҿ� ���δ�.
	}
	
	public void makeParkMainImg()
	{
		//���ʿ� �� ���� �̹������̴�.
		parkingMainFullScreen.add(mainImgLabel);
		parkingMainFullScreen.setBackground(Color.white);
		mainImgLabel.setBounds(150, 0, 760, 200);
		
		for (int i = 0; i < mainImgIcon.length; i++)
		{
			mainImgIcon[i] = new ImageIcon("logo"+(i+1)+".png"); //�� ImageIcon�� �̹����� ��´�.
			changeSizeImg = mainImgIcon[i].getImage(); //�̹��� ũ�⸦ �����ϱ� ���� �̹��������ܿ� ����� �̹����� ������ Image������ ����
			changeSizeImg = changeSizeImg.getScaledInstance(780,200, java.awt.Image.SCALE_SMOOTH); //���� �̹����� ũ�� ����
			mainImgIcon[i] = new ImageIcon(changeSizeImg); //����� ũ���� �̹����� �ٽ� ImageIcon�� ��´�.
		}
		Thread thread = new Thread(new Runnable() { //ù ȭ�鿡 �ڵ��� �������� �ϴ� ������
			@Override
			public void run() {
				while (true)
				{
					for (int i = 0; i < mainImgIcon.length; i++) 
					{
						mainImgLabel.setIcon(mainImgIcon[i]); //1�ʸ��� �󺧿� �̹����� �ٲپ� �ִ´�. �׷��� ��ġ �����̴� ���� �̹����� ����� �� �� ������!
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
				}
			}
		});
		thread.start(); //�̹��� ü���� ������ ����!
	} //makeParkMainImg() End
	
	public void makeDateTime()
	{
		pDateLabel.setFont(new Font("DS-Digital", Font.BOLD, 35)); //���� ����, ��, ���� �۾�ü�� ������
		//pDateLabel.setBounds(930, 60, 300, 30); //DATE�� ũ�� �� ��ġ ����
		
		pTimeLabel.setFont(new Font("DS-Digital", Font.BOLD, 55)); //���� �ð��� �۾�ü�� ������
		//pTimeLabel.setBounds(930, 90, 300, 50); //�ð��� ũ�� �� ��ġ ����
		
		/*parkingMainFullScreen.add(pDateLabel); //ParkingStartView�� ContentPane�� Date�� �߰�
		parkingMainFullScreen.add(pTimeLabel); //ParkingStartView�� ContentPane�� Time�� �߰�
*/		Thread thread = new Thread(new Runnable() { //����ð��� �����ϱ� ���� ������, �͸�Ŭ���� ����� ����Ͽ� Runnable�������̽��� run()�޼ҵ� �������̵� �Ͽ� ����
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					Calendar presentTime = Calendar.getInstance(); //���� �ð��� �����´�.
					SimpleDateFormat format = new SimpleDateFormat("   HH      mm  ss"); //�ð� ��� ���� ����
					try {
						String date = presentTime.get(Calendar.YEAR) + "       " + (presentTime.get(Calendar.MONTH) + 1) + "     " + presentTime.get(Calendar.DATE) + ""; //������ �ð��� ����, ��, ���� �����Ѵ�.
						String time = format.format(presentTime.getTime()); //���� �ð��� �����ͼ� format1���Ŀ� �°� String�� ����
						pDateLabel.setText(date); //pDate Label�� ������ ���
						pTimeLabel.setText(time); //pTime Label�� �ð� ���
						Thread.sleep(1000); //�ð谡 �����ϵ��� 1�ʸ��� �����ϰԲ� ����
					} catch (InterruptedException e) {}
				}
			}
		}); //Time�� ��Ÿ���� Thread End
		thread.start();// ������ - �ǽð� �ð��ݿ�
	} //makeDateTime() End
	
	public void makeTimeImg() //�ð� �̹����� ��ܿ� �ִ´�.
	{	
		timeImgIcon = new ImageIcon("�ð�6.jpg");
		changeSizeImg = timeImgIcon.getImage();
		changeSizeImg = changeSizeImg.getScaledInstance(320, 155 , java.awt.Image.SCALE_SMOOTH);
		timeImgIcon = new ImageIcon(changeSizeImg);
		
		timeImgLabel = new JLabel("�׽�Ʈ", timeImgIcon, JLabel.CENTER); //Label�� �����ϰ� �� �ȿ� �ð� Image�� �����Ѵ�.
		timeImgLabel.setBounds(880, -10, 320, 170);
		
		//������� ������ ��¥�� �ð� ����� �ð� �̹��� ���� ��쵵�� �����Ѵ�.
		timeImgLabel.add(pDateLabel); //��¥ �ؽ�Ʈ�� ���� Label�� timeImg���� ��� 
		timeImgLabel.add(pTimeLabel); //�ð� �ؽ�Ʈ�� ���� Label�� timeImg���� ���
		pDateLabel.setBounds(15, -20, 300, 100); //��,��,���� ��Ÿ���� Label�� ��ġ ���� , timeImgLabel����
		pTimeLabel.setBounds(15, 60, 300, 100); //��, ��, �ʰ� ��Ÿ���� Label�� ��ġ ����, timeImgLabel����
		
		parkingMainFullScreen.add(timeImgLabel);
	}
	
	public void makePrintName()
	{
		//ȸ���� �̸��� �ý��ۿ� ��½�Ų��.
		parkingMainFullScreen.add(memNameLabel); //ȸ�� �̸��� ���� Label
		memNameLabel.setBounds(900, 160, 50, 30);
		memNameLabel.setText(memName);
		memNameLabel.setFont(new Font("���", Font.BOLD, 15));
		memNameLabel.setForeground(Color.BLUE);
		
		parkingMainFullScreen.add(afterNameLabel); //ȸ���̸��ڿ� ���� ������ ���� Label
		afterNameLabel.setBounds(950, 160, 130, 30);
		afterNameLabel.setText("�� ������ ȯ���մϴ�.");
		
		parkingMainFullScreen.add(logoutBtn); //�α׾ƿ� ��ư
		changeSizeImg = logoutIcon.getImage();
		changeSizeImg = changeSizeImg.getScaledInstance(100,30, java.awt.Image.SCALE_SMOOTH);
		logoutIcon = new ImageIcon(changeSizeImg);
		logoutBtn.setBackground(Color.white);
		logoutBtn.setIcon(logoutIcon);
		logoutBtn.setBounds(1090, 160, 90, 30);
	} //makePrintName() End
	
	public void makeParkPlaceBtn() //�������� ��ư�� �����ϴ� �޼ҵ��. 1~3������ ���������� ��ư Ŭ���� ���� ������ ���� ��ư���� �������ؼ� CardLayout���� �����ߴ�.
	{
		for (int i = 0; i < parkFloorPanel.length; i++)
		{
			parkFloorPanel[i] = new JPanel(new GridLayout(3, 10)); //�� �гο� 3�� 10���� ��ġ�����ڸ� ���� �гΰ�ü�� �����Ѵ�.
			cardPanel.add(parkFloorPanel[i]); //CardLayout���� �Ǿ��ִ� cardPanel�� �� ���� ���������� ��Ÿ���� �г� �߰�!, �� ī���г��̸��� 1, 2, 3���� ����
		}
		cardPanel.setBounds(5, 200, 870, 465); //ī�� ���̾ƿ� ��ġ�� ũ�� ����
		parkingMainFullScreen.add(cardPanel);
		
		prevButton.addActionListener(new ActionListener() { //'������'�� ������ �� ����
			@Override
			public void actionPerformed(ActionEvent e) { 
				if (cardCount > 0) //0���� ũ�ٴ� ���� 2���� 3�� �г��� ���Ѵ�. 0�̸� 1���̴ϱ� 1���� ��õ���� ������ �� ī�巹�̾ƿ��� ������ �ʰ� �ƹ��ϵ� �Ͼ�� �ʰ� �Ѵ�.
				{
					cardLayout.previous(cardPanel); //���� �гη� ���ư���!!
					cardCount--; //���� �гη� ���� �� Count�� �Ѱ� ���ҽ�Ų��.
				}
			}
		}); //prevButtion event End
		
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { //'������'�� ������ �� ����
				// TODO Auto-generated method stub
				if (cardCount < 2) //cardCount�� 2���� �۴ٴ� ���� 1��(0)�� 2��(1)�г��� ���Ѵ�. 2�� �Ǹ� ���������� 3���̴ϱ� �������� ������ �� ī�巹�̾ƿ� ȭ���� ������ �ʰ� �ƹ��ϵ� �Ͼ�� �ʰ� �Ѵ�.
				{
					cardLayout.next(cardPanel); //���� �гη� �Ѿ��!
					cardCount++; //���� �гη� ������ Count�� �Ѱ� �÷��ش�
				}
			}
		}); //nextButton event End
		
		int buttonCount = 1; //�� ��ư �迭�� �ε����� �����ϱ� ���� ����
		int labelCount = 1; //�� �迭�� �� �ε����� �����ϱ� ���� ����
		for (int f = 0; f < parkFloorPanel.length; f++) //�� ���� ���������� ��ư�� �󺧷� ����
		{
			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 10; j++)
				{
					if (i % 2 == 0)
					{
						//�� ���� ��ư(2o��)�� �����ϰ� �� ��ư�� JButton�迭�� �����Ѵ� , ������ ���� ���λ����� ����
						parkButton[buttonCount-1] = (JButton)parkFloorPanel[f].add(new JButton(""+ buttonCount));
						parkButton[buttonCount-1].setBackground(new Color(99, 0xFF, 99)); //��ư���� ���λ�
						buttonCount++;
					}
					else //10�� �������� ���� �� ���� ������ ��������!
					{
						parkFloorPanel[f].add(new Label()); //�������� ���� �߰��� ������ �ֱ� ����
					}
				}
			}
		}
		System.out.println(labelCount);
		
		for (int i = 0; i < parkButton.length; i++)
		{
			//�� ��ư�� �����ʸ� ������ Ŭ������ �͸�Ŭ����, �� ���� �ٸ� ��ü �������� �ڵ鷯 ó��
			parkButton[i].addActionListener(new ParkingCarIn(frame, memCheck)); //��ư Ŭ�� �� �߻��ϴ� �̺�Ʈ�� ó���ϱ� ���� �����ʸ� ������ Ŭ���� ��ü�� ����
		}
	} //makeParkPlaceBtn() End
	
	public void makeFeeInfo() //���� �ý��� ȭ�鿡 ����ϰ� �� ���� ��� �ȳ��� �̹����̴�.
	{
		feeInfoIcon = new ImageIcon("������ݾȳ���.png");
		changeSizeImg = feeInfoIcon.getImage(); //�̹����� ũ�⸦ ��ȯ�ϱ� ���� Image��ü ������ �̹��� ���
		changeSizeImg = changeSizeImg.getScaledInstance(300,240, java.awt.Image.SCALE_SMOOTH); //���� �̹����� ũ�� ����
		feeInfoIcon = new ImageIcon(changeSizeImg); //ũ�⸦ �ٲ� �̹����� �ٽ� ImageIcon�� �ִ´�.
		feeInfoImgLabel.setIcon(feeInfoIcon); //Label�� ImageIcon�� ��´�.
		feeInfoImgLabel.setBounds(880, 250, 310, 300); //�̹����� ��� Label�� ũ��� ��ġ ����
		parkingMainFullScreen.add(feeInfoImgLabel); //�̹��� Label �гο� �߰�
	} //makeFeeInfo() End
	
	public void makeSystemBtn() //���� �ý��� ȭ�鿡 ��Ÿ���� �� ��� ��ư���̴�.
	{
		//�� ��ư ũ�� ����
		buttonFullPanel.setBounds(875, 455, 310, 205); //��ư���� ���� ��� ���� �г��� ��ġ�� ũ�� ����
		buttonFullPanel.setBackground(Color.white); //��ư �г��� ������ �Ͼ������ ����
		parkingMainFullScreen.add(buttonFullPanel); //ContentePane�� buttonFullPanel add!
		
		//��ư ������Ʈ 6�� buttonFullPanel�� ��ġ, �̹����� ũ�⸦ ��ư�� ũ�⿡ �°� �����Ͽ� ��ư�� �����Ѵ�.
		if (memCheck == 1) //�� �ΰ��� ��ư�� �����ڸ� �ʿ��� ���̴�! �����ڷ� �α��� ���� �ÿ��� ��쵵�� �����Ѵ�.
		{
			buttonFullPanel.add(feeCalcButton); //'�������'��ư , �����ڸ����̰��ϱ�
			feeCalcButton.setBounds(15, 0, 140, 60);
			feeCalcIcon = changeBtnImgSize(feeCalcIcon);
			feeCalcButton.setIcon(feeCalcIcon);
			
			buttonFullPanel.add(parkListButton); //'��������'��ư, �����ڸ� ���̰��ϱ�
			parkListButton.setBounds(165, 0, 140, 60);
			parkListIcon = changeBtnImgSize(parkListIcon);
			parkListButton.setIcon(parkListIcon);
		}
		
		buttonFullPanel.add(outCarButton); //'�����ϱ�'��ư
		outCarButton.setBounds(15, 70, 140, 60);
		outCarIcon = changeBtnImgSize(outCarIcon);
		outCarButton.setIcon(outCarIcon);
		
		buttonFullPanel.add(parkStateBtn); //'������Ȳ'��ư
		parkStateBtn.setBounds(165, 70, 140, 60);
		parkStateIcon = changeBtnImgSize(parkStateIcon);
		parkStateBtn.setIcon(parkStateIcon);
		
		buttonFullPanel.add(prevButton); //'������'��ư
		prevButton.setBounds(15, 140, 140, 60);
		prevBtnIcon = changeBtnImgSize(prevBtnIcon);
		prevButton.setIcon(prevBtnIcon);
		
		buttonFullPanel.add(nextButton); //'������'��ư
		nextButton.setBounds(165, 140, 140, 60);
		nextBtnIcon = changeBtnImgSize(nextBtnIcon);
		nextButton.setIcon(nextBtnIcon);
	} //makeSystemBtn() End
	
	public ImageIcon changeBtnImgSize(ImageIcon imgIcon) //���� �信 ��� ��ư�鿡 �� �̹����� ũ�⸦ �������ִ� �޼ҵ�!
	{
		Image chaImg = imgIcon.getImage();
		chaImg = chaImg.getScaledInstance(150,60, java.awt.Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(chaImg);
		return imgIcon;
	}
	
	class ParkingLogout implements ActionListener //�α׾ƿ� ��ư Ŭ�� �� �̺�Ʈ�� ó���ϴ� �ڵ鷯
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			frame.dispose(); //���� �������� �����Ų��. dispose()�� ��������ִ� �޼ҵ��̴�.
			new ParkingStartView(); //�ٽ� �α��� ȭ���� ����ڿ��� ���̵��� ���ο� �������� �����Ѵ�.
		}
	} //ParkingLogout class End
	
} //ParkingMainView class End
