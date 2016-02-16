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
	ParkingStartView frame; //�� ó�� ������ Frame�� ParkingStartView�� frame�� �ޱ� ����
	public static ArrayList<MemberInfo> memList = new ArrayList<MemberInfo>(); //��� Ŭ�������� ������ �� �ֵ��� static���� ����, ȸ�����Խ� ȸ�������� ������ MemberInfo��ü�� ���� �� ArrayList�� �����Ͽ� �����Ѵ�.
	
	//ȸ������ ���̾�α� �������
	JDialog registerDialog; //���� ū Ʋ�� �� ���̾�α�
	JPanel registerPanel = new JPanel(null); //ȸ���������� ��ǥ�� ��������! 
	JLabel regTitleLabel = new JLabel("ȸ�� ����");
	JLabel regIdLabel = new JLabel("���̵� : ");
	JTextField regIdText = new JTextField("", 12); //���̵�� 12�ڸ������� �Է°���
	JButton regDupleBtn = new JButton("�ߺ�üũ"); //���̵� �ߺ� üũ ��ư
	JLabel regPwdLabel = new JLabel("��й�ȣ : ");
	JTextField regPwdText = new JTextField("",13); //��й�ȣ�� 13�ڸ����� �Է°���
	JLabel regNameLabel = new JLabel("�̸� : ");
	JTextField regNameText = new JTextField(""); //�̸� �Է�
	JLabel regPhoneLabel = new JLabel("�ڵ��� : ");
	JTextField regPhoneText = new JTextField(""); //�ڵ��� ��ȣ �Է�
	JLabel regEmailLabel = new JLabel("�̸��� : ");
	JTextField regEmailText = new JTextField(""); //�̸��� �ּ� �Է�
	JButton regOkBtn = new JButton("���ԿϷ�"); //���ԿϷ� ��ư
	JButton regCancelBtn = new JButton("�������"); //������� ��ư
	
	ImageIcon dupleIcon = new ImageIcon("�ߺ�üũ.png");
	ImageIcon regOkIcon = new ImageIcon("���ԿϷ�.png");
	ImageIcon regCancelIcon = new ImageIcon("���.png");
	Image changeSize;
	
	public ParkingNewMemJoin (ParkingStartView frame)
	{
		this.frame = frame;
		
		//���̾�α� ���� ��ư���� �̺�Ʈ �����ʴ� �����ڿ� �ִ°� ����!, 1������ �����ؾ� �ϹǷ�!
		regDupleBtn.addActionListener(new regDupleAction()); //'�ߺ�Ȯ��'��ư Ŭ�� ��
		regOkBtn.addActionListener(new regOkAction()); //'���ԿϷ�'��ư Ŭ�� ��
		regCancelBtn.addActionListener(new regCancelAction()); //'�������'��ư Ŭ�� ��
	} //ParkingNewMemJoin ������ End

	//'ȸ������' ��ư Ŭ�� �� ����Ǵ� �̺�Ʈ ������
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//���̾�α� ȣ�� �� �� �ؽ�Ʈ�ʵ� �ʱ�ȭ
		regIdText.setText("");
		regPwdText.setText("");
		regNameText.setText("");
		regPhoneText.setText("");
		regEmailText.setText("");
		makeRegisterDialog(); //ȸ������ ��ư�� Ŭ���Ǹ� ȸ������ ���̾�α׸� ����.
	}
	
	public void makeRegisterDialog()
	{
		registerDialog = new JDialog(frame, "ȸ������ â");
		registerDialog.setBounds(500, 100, 300, 340); //(x��ġ, y��ġ, widthũ��, heightũ��)
		
		registerPanel.add(regTitleLabel); //Dialog �� ���ٿ� ����
		regTitleLabel.setBounds(110, 20, 70, 20);
		
		registerPanel.add(regIdLabel); //���̵� �Է�â
		registerPanel.add(regIdText);
		registerPanel.add(regDupleBtn);
		regIdLabel.setBounds(25, 50, 100, 30);
		regIdText.setBounds(75, 50, 100, 30);
		regIdText.requestFocus(); //���̵� �Է�â�� ��Ŀ���� �����.
		
		regDupleBtn.setBounds(180, 50, 90, 30); //�ߺ�üũ ��ư
		dupleIcon = changeSize(dupleIcon); //�ߺ�üũ ��ư �̹��� ũ�� ����
		regDupleBtn.setIcon(dupleIcon); //��ư�� �̹��� �����
		
		registerPanel.add(regPwdLabel); //��й�ȣ �Է�â
		registerPanel.add(regPwdText);
		regPwdLabel.setBounds(10, 85, 130, 30);
		regPwdText.setBounds(75, 85, 130, 30);
		
		registerPanel.add(regNameLabel); //�̸� �Է�â
		registerPanel.add(regNameText);
		regNameLabel.setBounds(35, 120, 100, 30);
		regNameText.setBounds(75, 120, 100, 30);
		
		registerPanel.add(regPhoneLabel); //�޴��� ��ȣ �Է�â
		registerPanel.add(regPhoneText);
		regPhoneLabel.setBounds(25, 155, 130, 30);
		regPhoneText.setBounds(75, 155, 130, 30);
		
		registerPanel.add(regEmailLabel); //�̸��� �ּ� �Է�â
		registerPanel.add(regEmailText);
		regEmailLabel.setBounds(25, 190, 130, 30);
		regEmailText.setBounds(75, 190, 160, 30);
		
		registerPanel.add(regOkBtn); //'���ԿϷ�'��ư
		registerPanel.add(regCancelBtn); //'�������'��ư
		regOkBtn.setBounds(50, 230, 90, 30);
		regOkIcon = changeSize(regOkIcon); //���ԿϷ� ��ư �̹��� ũ�� ����
		regOkBtn.setIcon(regOkIcon); //���ԿϷ� ��ư�� �̹��� �����
		regCancelBtn.setBounds(155, 230, 90, 30);
		regCancelIcon = changeSize(regCancelIcon); //������� ��ư �̹��� ũ�� ����
		regCancelBtn.setIcon(regCancelIcon); //������� ��ư�� �̹��� �����
		
		registerDialog.add(registerPanel);
		registerDialog.setVisible(true);
	} //makeRegisterDialog() End
	
	public ImageIcon changeSize(ImageIcon imgIcon) //ȸ������ ���̾�α׿� �ִ� ��ư�� �̹��� ũ�⸦ ��ȯ�����ִ� �޼ҵ�
	{
		Image chaImg = imgIcon.getImage();
		chaImg = chaImg.getScaledInstance(100,30, java.awt.Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(chaImg);
		return imgIcon;
	}
	
	class regDupleAction implements ActionListener //'�ߺ�üũ'��ư Ŭ�� ��
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String id = regIdText.getText();
			
			int flag = 0; //���̵� �ߺ� �˻� flag
			//for - each��! , Collection���¿� ����� ��ü���� �ϳ��� ������ ������ �� �ִ�.
			//���� ArrayList�� ����� ��ü�� Member��ü�̹Ƿ� �Ʒ��� ���� ���ָ� ����Ʈ���� �ϳ��� �����ؼ� ������ �� �ִ�.
			for (MemberInfo mem : memList)
			{
				if (id.equals(mem.getId())) //�ش� Member��ü�� ����ִ� id���� �޾ƿ´�
				{
					flag = 1;
					break;
				}
			}
			
			if (flag == 1) //�ߺ��� ���̵� �ִ°��
			{
				JOptionPane.showMessageDialog(frame, "�Է��Ͻ� id�� �̹� �ֽ��ϴ�. �ٽ� �Է����ּ���","����â", 0);
				regIdText.setText(""); //�ٽ� �Է��ϰ� ��������!	
				regIdText.requestFocus(); //��Ŀ���� �ؽ�Ʈ�ʵ���Ѵ�
			}
			else //�ߺ��� ���̵� ���� ���
			{
				JOptionPane.showMessageDialog(frame, "����Ͻ� �� �ִ� id�Դϴ�.","�˸�â", JOptionPane.INFORMATION_MESSAGE);
				regPwdText.requestFocus();
			}
		}
	} //class regDupleAction End
	
	class regOkAction implements ActionListener //'���ԿϷ�'��ư �̺�Ʈ ������ ����
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//�ؽ�Ʈ �ʵ忡 �Էµ� ������ �����ͼ� �����Ѵ�.
			String id = regIdText.getText();
			String pwd = regPwdText.getText();
			String name = regNameText.getText();
			String phone = regPhoneText.getText();
			String email = regEmailText.getText();
			int flag = 0;
			
			//for - each��! , Collection���¿� ����� ��ü���� �ϳ��� ������ ������ �� �ִ�.
			//���� ArrayList�� ����� ��ü�� Member��ü�̹Ƿ� �Ʒ��� ���� ���ָ� ����Ʈ���� �ϳ��� �����ؼ� ������ �� �ִ�.
			for( MemberInfo mem : memList ) 
			{
				if( id.equals(mem.getId()))
				{
					flag = 1; //�ߺ� id�� ã���� flag�� 1���ϰ� while���� ����������
					break;
				}
			}

			if (flag == 1) //�ߺ��� ���̵� �ִ� ���
			{
				JOptionPane.showMessageDialog(frame, "�Է��Ͻ� id�� �̹� �ֽ��ϴ�. �ٽ� �Է����ּ���","����â", 0);
				regIdText.setText(""); //�ٽ� �Է��ϰ� ��������!	
				regIdText.requestFocus(); //��Ŀ���� �ؽ�Ʈ�ʵ���Ѵ�
			}
			else if (id.equals("") || pwd.equals("") || name.equals("") || phone.equals("") || email.equals("")) //��� �ϳ��� �� ���ڿ��� �Է����� �ʰ� '���ԿϷ�'�� �������
			{
				JOptionPane.showMessageDialog(frame, "�� ������ �ֽ��ϴ�. �ٽ� �Է����ּ���","����â", 0);
			}
			else //����Ʈ�� �ߺ��� ���̵� ���� ���
			{
				memList.add(new MemberInfo(2,id, pwd, name, phone, email)); //�Է��� id�� ���� id�� ������ ArrayList�� ȸ������ ������ �����Ѵ�. , ȸ�������� �ϴ� �ο����� ��� memCheck���� 2�� ����ȴ�.
				FileSystem.saveMemberInfo(memList);
				JOptionPane.showMessageDialog(frame, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
				System.out.println("üũ! --------- ȸ�������� �Ϸ�Ǿ����ϴ�. ");
				registerDialog.setVisible(false);
				registerDialog.dispose();
			}
		}
	} //regOkAction class End
	
	class regCancelAction implements ActionListener //'�������'��ư Ŭ�� ��
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			registerDialog.setVisible(false);
			registerDialog.dispose();
		}
	} //regCancelAction class End
}
