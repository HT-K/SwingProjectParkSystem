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
	public ParkingStartView frame; //ParkingStartView�� Frame�� �ޱ� ���� ����
	public static ArrayList<ParkFeeInfo> feeList = new ArrayList<>(); //�����Ϸ� �� �� ������ ���� ������ݰ� ������ ���� �ð��� ������ ���� FeeInfo��ü�� �����ϴ� ArrayList
	
	//���� �ϱ� ���̾�α� ����
	JDialog outCarDialog; //������ �ϱ� ���� ������ȣ�� �Է��� ���̾�α� â
	JPanel outCarFullPanel = new JPanel(null); //���� ���̾�α� ���� ������Ʈ ������ ���� fullPanel
	JLabel outCarLabel = new JLabel("���� ���� �ϱ�"); //���̾�α� ���� �� ���ٿ� ��Ÿ���Ե� Label
	JLabel carNumLabel = new JLabel("������ȣ : "); //�߰��� ����ڰ� �Է��ϰԵ� �ؽ�Ʈ�ʵ� �κ�
	JTextField inputNumText = new JTextField("",9); //�ִ� 9ĭ����!
	JButton outOkButton = new JButton("�����Ϸ�");
	JButton cancelButton = new JButton("���");
	
	//���� ��� �� ����� �����ִ� ���̾�α�
	JDialog resultOutDialog; //���� �Ϸ� ����� �����ִ� ���̾�α�
	JPanel resultFullPanel = new JPanel(null); //������� ���̾�α׿� ������Ʈ�� ���� �г�
	JLabel rfTitleLabel = new JLabel(); //���� ��� ���̾�α� title Label
	JLabel rfCarTypeLabel = new JLabel(); //���� ���� ��� Label
	JLabel rfCarNumLabel = new JLabel(); //���� ��ȣ ��� Label
	JLabel rfParkFeeLabel = new JLabel(); //���� ��� ��� Label
	JLabel rfParkTimeLabel = new JLabel();
	JButton rfOkButton = new JButton("����Ȯ��");
	
	int memCheck = 0;
	
	public ParkingCarOut (ParkingStartView frame, int memCheck) //ParkingSystme���� �����ϱ� ��ư Ŭ�� �̺�Ʈ �߻� �� �������� �Ѿ���� �ȴ�.
	{
		this.memCheck = memCheck; //������, ȸ��, ��ȸ���� �����ϱ� ���� ����
		this.frame = frame; //ParkingStartViewŬ������ Frame�� �����´�.
		
		outOkButton.addActionListener(new outOkListener()); //'�����Ϸ�'Ŭ�� ��
		cancelButton.addActionListener(new cancelListener()); //'�������'Ŭ�� ��
		rfOkButton.addActionListener(new rfOkListener()); //'�������Ȯ��'Ŭ�� ��
	}
	
	public void makeOutCarDialog() //���� ���� ��� ���̾�α� �����ϱ�
	{
		outCarDialog = new JDialog(frame, "���� ���� ��� �ϱ�");
		outCarDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 300, 150); //���̾�αװ� ��Ÿ�� ��ġ ����
		outCarDialog.add(outCarFullPanel); //���̾�α� �ȿ� �� ������ ������ fullPanel�� ���̾�α׿� add!
		
		outCarFullPanel.add(outCarLabel); //�� ���� Label
		outCarLabel.setBounds(80, 0, 150, 30);
		
		outCarFullPanel.add(carNumLabel); //���� ��ȣ �Է� Label
		carNumLabel.setBounds(60, 40, 70, 20);
		
		outCarFullPanel.add(inputNumText); //��ȣ �Է� �ؽ�Ʈ �ʵ�
		inputNumText.setBounds(125, 40, 90, 20);
		
		outCarFullPanel.add(outOkButton); //'�����Ϸ�'��ư
		outOkButton.setBounds(40, 70, 100, 30);
		
		outCarFullPanel.add(cancelButton); //'�������'��ư
		cancelButton.setBounds(145, 70, 100, 30);
		
		outCarDialog.setVisible(true);
	} //makeOutCarDialog() End
	
	public void makeResultOutDialog()
	{
		resultOutDialog = new JDialog(frame, "���� ��� �Ϸ�");
		resultOutDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 200, 250);
		resultOutDialog.add(resultFullPanel); //���� Ȯ�� ���̾�α׸� ������ �г� add
		//resultFullPanel.setSize(new Dimension(200, 200));
		
		resultFullPanel.add(rfTitleLabel); //���̾�α� ���� Label
		rfTitleLabel.setBounds(15, 0, 150, 20);
		
		resultFullPanel.add(rfCarTypeLabel); //���� ���� ��� Label
		rfTitleLabel.setBounds(45, 30, 150, 20);
		
		resultFullPanel.add(rfCarNumLabel); //���� ��ȣ ��� Label
		rfCarNumLabel.setBounds(45, 60, 150, 20);
		
		resultFullPanel.add(rfParkFeeLabel); //���� ��� ��� Label
		rfParkFeeLabel.setBounds(45, 90, 150, 20);
		
		resultFullPanel.add(rfParkTimeLabel); //���� �ð� ��� Label
		rfParkTimeLabel.setBounds(45, 120, 150, 20);
		
		resultFullPanel.add(rfOkButton); //'�������Ȯ��'��ư
		rfOkButton.setBounds(40, 150, 100, 30);
		
		resultOutDialog.setVisible(true);
	} //makeResultOutDialog() End

	//����ȭ�鿡�� '�����ϱ�' Ŭ���� �߻��ϴ� �̺�Ʈ�� ó���ϴ� �ڵ鷯
	@Override
	public void actionPerformed(ActionEvent e) { //'�����ϱ�'�� ��ư�� �Ѱ��̱⶧���� �ѹ� Ŭ�� �� ������ ��ü �ϳ��� �۵��ȴ�.
		// TODO Auto-generated method stub
		makeOutCarDialog(); //���� ���̾�α� ȣ��
		inputNumText.setText(""); //'�����Ϸ�'Ŭ�� �� �ؽ�Ʈ �ʵ带 �ʱ�ȭ�Ѵ�.
		outCarLabel.setFont(new Font("���", 35, 20)); //���� ���̾�α� ���� ����
	}//'�����ϱ�'��ư �̺�Ʈ �ڵ鷯 End
	
	class outOkListener implements ActionListener //'�����Ϸ�' Ŭ�� �� ����Ǵ� �̺�Ʈ �ڵ鷯
	{
		/*for(CarInfo car : ParkingCarIn.parkDataMap.values())
		{
			if (car.getcNum().equals(inputNumText.getText()))
			{
				System.out.println("���� ��Ȯ�� �Է� = " + inputNumText.getText());
				flag = 1;
				break;
			}
		}
		*/
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ParkCarInfo park = null;
			int resultFee = 0; //����� �����ϱ� ���� ����
			int resultMinute = 0; //�� �����ð��� �����ϱ� ���� ����
			int flag = 0; //Ʈ���� ���� ������ȣ �� �ؽ�Ʈ�ʵ忡�� �Է¹��� ����� ���� ���� �ִ��� ������ �˻�, 0�̸� ���� 1�̸� ã����
			for (ParkCarInfo car : ParkingCarIn.parkCarList)
			{
				if (car.getcarNum().equals(inputNumText.getText())) //�Է¹��� ��ȣ�� ����Ʈ�� �ִ� ��ȣ�� ������
				{
					park = car; //����Ʈ���� �� ������ ���������� ��� CarInfo��ü�� �����Ѵ�.
					flag = 1;
					break;
				}
			}
			
			try { //��ȣ �Է�â�� ���ڰ� �ƴ� ���� �Է� �� ����â�� ���� ���� try-catch��
                int check = Integer.parseInt(inputNumText.getText()); //�ؽ�Ʈ�ʵ忡 ���ڰ��ƴ� ���ڰ� ������ int�� ������ ������ �ȵȴ�. �� �� Exception(����)�� �߻��Ѵ�.
            } catch (Exception ae) {
                JOptionPane.showMessageDialog(null, "���ڴ� �Է��Ͻø� �ȵſ�~", "���ڸ� �Է��ϼ���.", 0);
                inputNumText.setText("");
                return;
            }
            
            if (inputNumText.getText().length() > 4) //������ȣ �Է��� 4�ڸ��� �����ϰ� ����
            {
                JOptionPane.showMessageDialog(null, "������ȣ�� 4�ڸ�����?", "������ȣ �Է�", 0);
                inputNumText.setText("");
                return;
            }
			
			if (flag == 0) //parkCarList(ArrayList)�� ����Ǿ� �ִ� ���� ��ȣ�� �Է¹��� ��ȣ�� ã�� ������ ��
			{
				System.out.println("���� �߸� �Է� = "  + inputNumText.getText());
				JOptionPane.showMessageDialog(resultOutDialog, "�Է��Ͻ� ��ȣ�� ������ �����ϴ�. �ٽ� �Է����ּ���","����â", 0);
				inputNumText.setText("");
				return; //outOkButton�������� ó������ ���ư���!
			}
			else //parkCarList(ArrayList)�� �ִ� ��ȣ���� �Է¹��� ��ȣ�� ���� ���� ã���� ��
			{
				makeResultOutDialog(); //���� ��� ���̾�α�(resultDialog) ȣ��
				rfTitleLabel.setText(park.getparkPlaceNum() + "�� ���� ��� �Ϸ�"); //resultDialog ���� ����
				rfCarTypeLabel.setText("�������� : " + park.getcarKind());
				rfCarNumLabel.setText("������ȣ : " + park.getcarNum());
				
				JButton complete = ParkingMainView.parkButton[park.getparkPlaceNum()-1]; //���� �ÿ� Ŭ���ߴ� ��ư�� ������ �޾ƿ´�. �� ��ư�� �ε��� ���� ParkNum - 1�� �ش� ��ư�� ������ ���� ��ư�迭�� �ε��� ���̴�.
				complete.setText("" + park.getparkPlaceNum()); //'������'���� �Ǿ��ִ� ��ư�� �ؽ�Ʈ�� �ٽ� ���ڷ� �ٲ۴�
				complete.setBackground(new Color(99, 0xFF, 99)); //���λ�
				complete.setEnabled(true); //���� �Ϸᰡ �� ��ư�� �ٽ� Ȱ��ȭ ���Ѿ��Ѵ�!!
			
				Calendar presentTime = Calendar.getInstance(); //���� ���� ����� Ŭ���� �ð� ������
				long carOutTime = presentTime.getTimeInMillis(); //���� ��� ��  �ð��� �ʷ� �ٲ㼭 ����
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = format.format(presentTime.getTime()); //����Ʈ �ϱ� ���� parkPring ���Ϳ� ����
				
				resultMinute = calculateMinute(park.getCarInTime(), carOutTime); //�� ����� �����ߴ��� ���ϴ� �޼ҵ��
				rfParkTimeLabel.setText("�����ð� : " + resultMinute + "��");
				
				if (park.getMemOrNotmem() == true) //�����ڴ� memCheck�� 1�ε� ������� ���� �� ȸ�� Ȥ�� ��ȸ���� ���� ������� ���Ǿ���Ѵ�.
				{
					memCheck = 2; //������ ������ ȸ���� ��� �����ڵ� ȸ�������� ���� 2���� ������
				}
				else
				{
					memCheck = 3; //������ ������ ��ȸ���� ��� �����ڵ� ��ȸ�� ������ ���� 3���� �������� ��������� ���ȴ�.
				}
				resultFee = calculateFee(memCheck, park.getcarKind(), park.getCarInTime(), carOutTime); //������� ��� �޼ҵ� ȣ��, �ڵ����� ��������� ����ؼ� ���� ���Ϲ޴´�.
				rfParkFeeLabel.setText("������� : " + resultFee + "��");
				
				feeList.add(new ParkFeeInfo(resultFee, new Date())); //�����ϱ⸦ ������ �� ��ݰ� ����ð��� FeeInfo��ü�� �����ϸ� �����͸� �ְ�, ArrayList�� ��ü�� �����Ѵ�.
				FileSystem.saveFeeListInfo(feeList); //feeList�� �ִ� ��ݰ� �ð��� ���Ͽ� �����Ѵ�.
				
				ParkingCarIn.parkPrintList.add(park.getparkPlaceNum() + "�� ���� " + inputNumText.getText() + "�� ����  " + time + "�� ����."); //parkPrint ���Ϳ� ��� ���������� ��� ������ �����Ǿ����� �����Ѵ�.
				FileSystem.saveParkInfo(ParkingCarIn.parkPrintList); //���� ������ ���Ͽ� �����Ѵ�.
				
				ParkingCarIn.parkCarList.remove(park); //������ �Ϸ�Ǹ� �� ���������� ���� ������ ���� CarInfo��ü(park)�� ArrayList�������� �����Ѵ�.
				FileSystem.saveCarInfo(ParkingCarIn.parkCarList); //������ �����ϸ� �ٽ� ���Ͽ� ������������ �����Ѵ�.
			}
		}
	} //outOkButton ������ ����
	
	class cancelListener implements ActionListener //'�������' Ŭ�� ��, ���� ���̾�α׸� ���ش�
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			outCarDialog.setVisible(false);
			outCarDialog.dispose(); //���̾�α� �����
		}
	} //cancelListener class End
	
	class rfOkListener implements ActionListener //'�������Ȯ��'Ŭ�� ��, �� ���� ���̾�α׸� ��� �ݴ´�.
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
	
	int calculateFee(int memCheck, String cKind, long carInTime, long carOutTime) //�����ϷḦ ���� �� ȣ���ؾ��ϴ� �޼ҵ�, ������ ������ �ð��� ���� ����� �޶�����.
	{
		int resultFee = 0;
		long parkTime = 0;
		int parkCal = 0;
		carInTime = carInTime / 60000; //���� ����� �ð��� ������ ����, Millisecond�� 1�ʰ� 1000�̴�, �� 60000���� ������ ������ ���� �� �ִ�.
		carOutTime = carOutTime / 60000; //���� ����� �ð��� ������ ����
		
		parkTime = carOutTime - carInTime; //���� �� �ð��� ������ �� �ִ�.
		System.out.println("�����ð� = " + parkTime + "��");
		if (memCheck == 2) //ȸ���� �⺻����� 1500��, 2000���̴�
		{
			if (cKind.equals("������"))
			{
				if (parkTime <= 30)
				{
					resultFee = 1500;
				}
				else
				{
					parkTime = parkTime - 30;
					parkCal = (int)(parkTime / 10); 
					resultFee = 1500 + (500 * parkCal); //�⺻ ���� ��� 1500���� 10�п� 500���߰� 
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
					resultFee = 2000 + (600 * parkCal); //�⺻ ���� ��� 2000���� 10�п� 600���߰� 
				}
			}
		}
		else if (memCheck == 3) //��ȸ���� �⺻����� 2000��, 2500���̴�!
		{
			if (cKind.equals("������"))
			{
				if (parkTime <= 30)
				{
					resultFee = 2000;
				}
				else
				{
					parkTime = parkTime - 30;
					parkCal = (int)(parkTime / 10); 
					resultFee = 2000 + (500 * parkCal); //�⺻ ���� ��� 1500���� 10�п� 500���߰� 
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
					resultFee = 2500 + (600 * parkCal); //�⺻ ���� ��� 2000���� 10�п� 600���߰� 
				}
			}
		}
		return resultFee;
	} //calculateFee() End
	
	int calculateMinute(long carIntime, long carOutTime) //�� ���� �ð��� '��'���� ����ϱ� ���� �� �ð��� ���ϴ� �޼ҵ�
	{
		int resultMinute = 0;
		
		resultMinute = (int)(carOutTime - carIntime) / 60000; //���� �ð�(��) - ���� �ð�(��)�� �ϸ� ���� �ð�(��)�� ���� �� �ִ�. ���⼼ 60000���� ������ '��'���� �� �� �����ð��� ��������.
		
		return resultMinute;
	} //calculateMinute() End
} //ParkingCarOut class End
