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
	ParkingStartView frame; //ParkingStartView�� �������� �޾ƿ��� ���� ��ü ���� ����
	public static ArrayList<ParkCarInfo> parkCarList = new ArrayList<ParkCarInfo>();  //�����Ϸ� �� ������ �������� ������ ������� ArrayList
	public static ArrayList<String> parkPrintList = new ArrayList<String>(); //�������� Ŭ�� �� ������ ������ �����ϴ� ArrayList
	
	JDialog parkInputDialog; //�� ��ư Ŭ���� ������ '�����԰� ���̾�α�'
	JPanel dialogFullPanel = new JPanel(null); //�� �г��� ��ġ�� fullPanel �����̳�
	
	JLabel titleLabel = new JLabel(); //parkInputDialog�� ������ ����� Label
	
	JLabel carKindLabel = new JLabel("�� ���� : "); //�� ������ ����� Label
	CheckboxGroup carChoice = new CheckboxGroup();
	JPanel boxPanel = new JPanel(new FlowLayout()); //üũ�ڽ��� ���� �г�
	Checkbox car1Box = new Checkbox("������", carChoice, true);
	Checkbox car2Box = new Checkbox("������", carChoice, false);
	
	JLabel carNumLabel = new JLabel("������ȣ : "); //������ȣ�� ����� Label
	JTextField inputNumText = new JTextField(null,9); //�ִ� 9ĭ����!
	
	JButton parkOkButton = new JButton("�������"); //'�������'��ư
	JButton cancelButton = new JButton("���"); //'���'��ư
	
	//ParkingMainView���� �޾ƿ;� �� ������
	int parkNum; //������ ������ �ε��� ��ȣ
	JButton parkButton; //Ŭ���� ���������� ��ư ����
	
	public ParkingCarIn (ParkingStartView frame) //ParkingSystem���� ��ư(��������) Ŭ�� �̺�Ʈ �߻� �� �������� �Ѿ���� �ȴ�. //ParkingCarInŬ������ ��ü ������ ���ÿ� ���̾�α׸� �����س��´�.
	{
		this.frame = frame; //ParkingStartViewŬ�������� ������ JFrame�� �����´�.
		
		//�� ��ư�� �̺�Ʈ �ڵ鷯�� �Ѱ����� �����ؾ� �ϹǷ� �����ڿ� �־��ش�! , �̷��� �ϴ°� ����!
		parkOkButton.addActionListener(new parkOkListener());
		cancelButton.addActionListener(new cancelListener());
	} //ParkingCarIn ������ End
	
	//�� '��������'��ư Ŭ�� �� �߻��� �̺�Ʈ�� ó���ϴ� �ڵ鷯
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		inputNumText.setText("");//�ؽ�Ʈ �ʵ�� ���̾�α� ȣ�� �� �׻� �ʱ�ȭ ���·�!
		makeRegisterDialog(); //'�������' ���̾�α� ȣ��

		//���߿� '�����Ϸ�'Ŭ�� �� �������� ��� ��ư�� �ε�����ȣ�� �� ��ư�� �����̾����� �˾ƾ��Ѵ�. (�׷��� ��ư�� �ٽ� Ȱ��ȭ�ϰ� �� ������ �����ߴ� ������ �����Ѵ�.)
		parkNum = Integer.parseInt(e.getActionCommand()); //�������� ��ư Ŭ���� �ش� ���������� �ε��� ��ȣ�� ������
		parkButton = (JButton)e.getSource(); //�̺�Ʈ�� �߻��� ��ư�� ������
		
		//titleLabel ���뺯�� 
		titleLabel.setText("" + parkNum + "�� ���������Դϴ�.");
		titleLabel.setFont(new Font("���", 35, 20));
		
		parkInputDialog.setVisible(true); //���̾�α� ������ �Ϸ�Ǿ����� ȭ�鿡 ���!!
	} //'��������' �̺�Ʈ �ڵ鷯(actionPerformed)' End
	
	public void makeRegisterDialog() //��� ���̾�α�  â ����
	{
		parkInputDialog = new JDialog(frame, "���� ���� �Է�", true); //ParkingStartView��ü�� Frame�� ���̾�α׸� ����
		parkInputDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 300, 210); //���� �Է� ���̾�α׸� Frame�� ����� ��Ÿ����
		parkInputDialog.add(dialogFullPanel); //���̾�α� �ȿ� �� ������ ������ fullPanel�� ���̾�α׿� add!	
		
		dialogFullPanel.add(titleLabel); //���̾�α� ��ܿ� ������� Label
		titleLabel.setBounds(55, 10, 250, 20);
		
		dialogFullPanel.add(boxPanel); //üũ�ڽ� ���
		boxPanel.setBounds(25, 40, 240, 30);
		boxPanel.add(carKindLabel); //�� ���� ��� Label
		boxPanel.add(car1Box);
		boxPanel.add(car2Box);
		
		dialogFullPanel.add(carNumLabel); //�� ��ȣ ��� Label
		carNumLabel.setBounds(50, 80, 80, 30);
		
		dialogFullPanel.add(inputNumText); //�� ��ȣ �Է� �ؽ�Ʈ�ʵ�
		inputNumText.setBounds(120, 80, 120, 30);
		
		dialogFullPanel.add(parkOkButton); //'�����Ϸ�'��ư
		parkOkButton.setBounds(40, 130, 100, 30);
		
		dialogFullPanel.add(cancelButton); //'�������'��ư
		cancelButton.setBounds(150, 130, 100, 30);
	} //makeRegisterDialog() End
	
	class parkOkListener implements ActionListener //'�������' Ŭ�� ��
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Calendar presentTime = Calendar.getInstance(); //���� ���� ����� Ŭ���� �ð� ������
			long carIntime = presentTime.getTimeInMillis(); //���� ��� ��  �ð��� �ʷ� �ٲ㼭 ����
			SimpleDateFormat format1 = new SimpleDateFormat("hh�� mm�� ss��"); //�ð��� ������ Ʋ
			String time = format1.format(presentTime.getTime()); //'��������'���̺� ����ϱ� ���� ��ȯ�ؼ� ����
			
			String cKind = ""; //������ư�� �ִ� �ؽ�Ʈ(������,������)�� ��� ���� ����
			if (car1Box.getState() == true)
			{
				cKind = car1Box.getLabel(); //"������" ��Ʈ�� �� ��������
			}
			else if (car2Box.getState() == true)
			{
				cKind = car2Box.getLabel(); //"������" ��Ʈ�� �� ��������
			}
			
			boolean find = false; //List���� ���� �����͸� ã���� true�� �ٲٰ� �ƴϸ� false���·� for���� ����������.
			for (ParkCarInfo car : parkCarList) //�������� ������ ���� CarInfo��ü���� ����� List���� ��ü���� �ϳ��� �����ؼ� �� ���� ������ �˻��Ѵ�.
			{
				if (car.getcarNum().equals(inputNumText.getText())) //�Է¹��� ������ȣ�� List�ȿ� ������ȣ�� ���� ��ȣ�� ������ ����������.
				{
					find = true;
					break;
				}
			}
			
			if (find == true) //List�ȿ� �Է¹��� ��ȣ�� ���� ��ȣ�� ������ �ؽ�Ʈ�ʵ忡 �ٽ� �Է¹ް� �Ѵ�.
			{
				JOptionPane.showMessageDialog(null, "�Է��Ͻ� ��ȣ�� �̹� ��ϵ� �����Դϴ�. �ٽ� �Է����ּ���", "����â", 0);
				inputNumText.setText(""); //return�Ǽ� ���� �ٽ� �Է¹ޱ� ���� �ؽ�Ʈ�ʵ带 �ʱ�ȭ�Ѵ�.
				inputNumText.requestFocus(); //
				return; //parkOkListener�� ó������ ���ư���.
			}
			else //List�ȿ� �Է¹��� ��ȣ�� ���� ��ȣ�� ������ ��������� �����ϰ� �ȴ�!
			{
				parkCarList.add(new ParkCarInfo(parkNum, cKind, inputNumText.getText(), carIntime)); //����������ȣ, ��������, �Է¹��� ������ȣ, ����������ưOn����, �������� �԰���
				FileSystem.saveCarInfo(parkCarList); //�����Ϸ�� ������ ������ ���� ArrayList�� ���Ͽ� �����Ѵ�
				
				parkPrintList.add(parkNum + "�� �������� " + inputNumText.getText() + "�� ����  " + time + "�� ����."); //parkPrint ���Ϳ� ��� ���������� ��� ������ ��ϵǾ����� �����Ѵ�.
				FileSystem.saveParkInfo(parkPrintList); //���� ���� ������ ���� ArrayList�� ���Ͽ� �����Ѵ�.
				
				parkButton.setText("������"); //���� ����� �Ϸ�Ǹ� �� ���������� '������'�̶�� ǥ�ø� �ϰ���.
				parkButton.setBackground(new Color(0xFF, 66, 66)); //������ ��ư�� ����� ���������� �����Ѵ�.
				parkButton.setEnabled(false); //������ �� ��ư ��Ȱ��ȭ
			}
			
			parkInputDialog.setVisible(false); //��������� �Ϸ�Ǹ� ���̾�α׸� �����
			parkInputDialog.dispose(); //��ϿϷ�Ǿ����� ��ϴ��̾�α� ����
		}
	}//parkOkListener Ŭ���� End
	
	class cancelListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			parkInputDialog.setVisible(false);
			parkInputDialog.dispose(); //���̾�α� �ݱ�
		}
	} //cancelListener Ŭ���� End
} //ParkingCarIn class End
