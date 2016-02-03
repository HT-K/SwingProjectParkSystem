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
	ParkingStartView frame; //ParkingStartView���� frame �ޱ� ���� ����
	String[] feeChoice= {"�̹��� �������", "���� ��� ����", "�Ⱓ �˻� ����", "�޴�������"}; //showOptionDialog�� �� ��ư ���ڿ���
	
	JDialog feeResultDialog; //��� ���̾�α�
	JPanel feeFullPanel = new JPanel(null); //��� ���̾�α��� ȭ���� ä�� �г�
	JLabel titleFeeLabel = new JLabel(); //���� �������� �˷��� Label
	JLabel resultFeeLabel = new JLabel(); //���� ����� ���� Label
	JButton feeOkButton = new JButton("����Ȯ��");
	
	//�Ⱓ �˻� �� ���� ������Ʈ��
	JDialog termSearchDig; //�Ⱓ �˻�â�� ���� ���� ���̾�α�
	JPanel termDigPanel = new JPanel(null);
	JLabel tetmTitleLabel = new JLabel("�Ⱓ�� �Է����ּ���.");
	JTextField firstDateText = new JTextField("yyyy-MM-dd");
	JLabel tildeLabel = new JLabel("~");
	JTextField secondDateText = new JTextField("yyyy-MM-dd");
	JButton termOkBtn = new JButton("�˻�Ȯ��");
	
	public ParkingFeeList (ParkingStartView frame) //ParkingSystem���� '�������'��ư Ŭ�� �̺�Ʈ �߻� �� ������� ���̾�α� ��� ��ġ�� �Ѿ���� �ȴ�.
	{
		this.frame = frame; //ParkingStartView��ü�� frame�� �����ͼ� ����		
		
		feeOkButton.addActionListener(new feeOkListener()); //'����Ȯ��' ��ư Ŭ�� ��, �̺�Ʈ ������
		termOkBtn.addActionListener(new termOkListener()); //�Ⱓ �˻� ���̾�α��� '�˻�Ȯ��' ��ư Ŭ�� �� �̺�Ʈ ������
	} //ParkingFeeList ������ End

	//'�������' Ŭ�� �� ó���ϴ� �̺�Ʈ ������
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int choice = 0;
		choice = makeChoiceFeeDialog(); //choice�� 3�̾ƴϰ� CLOSED_OPTION�� �ƴϸ� ������� ��� ���̾�α� ���!
		
		if (choice != 2 && choice != 3 && choice != JOptionPane.CLOSED_OPTION)
		{
			makeResultDialog();
		}
	}
	
	public int makeChoiceFeeDialog()
	{
		int choice; //���̾�α� ��ư Ŭ�� �� ������ Ŭ���ߴ��� �˱� ���� ����
		int resultFee = 0; //�� �޴����� ��Ÿ���Ե� �������� �ٸ���! , �� �� ����� ������ ��� �� ����
		//'�������' ��ư Ŭ�� �� ��Ÿ���� �� ���� ���̾�α�, �����ϴ°� ���������� ���� ��Ÿ���� ���̾�αװ� �ٸ���, ������ JOptionPane�̴ϱ� �˾Ƶ���!
		choice = JOptionPane.showOptionDialog(frame, "������ Ȯ���Ͻðڽ��ϴ�.", "��� ���� �޴�", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, feeChoice, null);
		
		titleFeeLabel.setFont(new Font("���", 35, 15));
		if (choice == 0) //'�̹��� �������' Ŭ�� ��
		{
			titleFeeLabel.setText("�̹��� ��� ���� ����Դϴ�.");
			resultFee = monthFee();
		}
		else if (choice == 1) //"�ְ� ��� ����" Ŭ�� ��
		{
			titleFeeLabel.setText("���� ��� ���� ����Դϴ�.");
			resultFee = dayFee();
		}
		else if (choice == 2) //"�˻� ��� ����" Ŭ�� ��
		{
			makeTermSearchDig(); //�Ⱓ �˻� ���̾�αװ� ȣ��ȴ�.
		}
		else if (choice == 3) //'�޴�������' Ŭ�� ��
		{
			JOptionPane.showMessageDialog(frame, "������ �ٽ� �̿����ּ���");
		}
		else
		{
			choice = JOptionPane.CLOSED_OPTION; //XŬ���� ���ϵǴ� ���� JOptionPane.CLOSED_OPTION �̴�!
		}
		resultFeeLabel.setText("�� " + resultFee + "���Դϴ�."); //�� �޴� Ŭ�� �� ����Ǵ� �޼ҵ尡 �ٸ���. �׿� ���� �� ������� ������� �ٸ��� �ȴ�.
		return choice;
	} //makeChoiceFeeDialog() End
	
	public void makeResultDialog()
	{
		//feeResultDialog �����ϱ�
		feeResultDialog = new JDialog(frame, "�������"); //������� ����� ��Ÿ�� ���̾�α� ����
		feeResultDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 300, 150); //������� ����� ��Ÿ�� ���̾�αװ� ��Ÿ�� ��ġ
		feeResultDialog.add(feeFullPanel);
		
		feeFullPanel.add(titleFeeLabel); //�� ���� ����
		titleFeeLabel.setBounds(60, 0, 200, 30);
		
		feeFullPanel.add(resultFeeLabel); //��� ������ ���
		resultFeeLabel.setBounds(95, 40, 180, 30);
		
		feeFullPanel.add(feeOkButton); //'����Ȯ��' ��ư
		feeOkButton.setBounds(90, 80, 100, 30);
		
		feeResultDialog.setVisible(true);
	} //makeResultDialog() End
	
	public void makeTermSearchDig() //�Ⱓ�� �����ؼ� ��� ������ �ϰ� ���� �� �� ���̾�αװ� ȣ��ǰ� �ȴ�.
	{
		termSearchDig = new JDialog(frame, "�Ⱓ �˻� ��� ����"); //�Ⱓ �˻�â�� ���� ���� ���̾�α�
		termSearchDig.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 265, 150);
		termSearchDig.add(termDigPanel);
		termDigPanel.setBounds(0, 0, 200, 200);
		
		tetmTitleLabel.setBounds(65, 0, 150, 20);
		firstDateText.setBounds(10, 30, 100, 30);
		tildeLabel.setBounds(120, 30, 20, 30);
		tildeLabel.setFont(new Font("���", Font.PLAIN, 15));
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
		int resultFee = 0; //�̹��� �� ����� �����ϱ� ���� ����
		
		Calendar presentTime = Calendar.getInstance(); //CalendarŬ������ �̿��ؼ� ���� �ð��� �⵵,��,���� �˾Ƴ���.
		Date sDay = new Date(presentTime.YEAR + 115, presentTime.MONTH - 1, 1); //sDay�� ���� �ð��� �⵵, ��, 1��(���۳�)�� ����ִ´�. ex)2016�� 1�� 1�� 00:00:00 ���� ����ȴ�.
		System.out.println("���� ���� ���� : " + sDay);
		Date eDay = new Date(presentTime.YEAR + 115, presentTime.MONTH - 1, presentTime.getActualMaximum(Calendar.DATE)-1, 23, 59, 59); //eDay�� ���� �ð��� �⵵, ��, ��������(30,31�� 2���� 28��29��) �� ����ִ´�. ex)2016�� 1�� 31�� 23:59:59 ���� ����ȴ�.
		//1901�⵵�� �����Ǿ��־ 115���� ���ؾ� ���� �ð��� �⵵�� ���´�. ����?
		System.out.println("���� ���� �� : " + eDay);
		
		for (ParkFeeInfo fee : ParkingCarOut.feeList)
		{
			System.out.println("���� ���� �ð� : " + fee.getTime());
			//sDay�� ���� ��(��)�� ù ���̴�. eDay�� ���� ��(��)�� �� ���̴�. ����� ���� �ð����� sDay�����̰� eDay�����̸� �Ѵ��̶�� �ð��� ���̿� �ð����� �ȴ�. �̸� ���� �Ѵ�ġ�� ����� ����� �� �ִ�.
			if ((fee.getTime().after(sDay) && fee.getTime().before(eDay))  && fee.getTime().before(eDay)) //1���̶�� ġ�� 1�� 1�� ~ 1��31�� ���̸� ����� �� ���ϴ°Ŵ�!
			{
				resultFee += fee.getFee();
			}
		}
		
		return resultFee;
	} //monthFee() End
	
	public int dayFee() //���Ͽ�ݰ��
	{
		int resultFee = 0;
		
		Calendar presentTime = Calendar.getInstance(); //CalendarŬ������ �̿��ؼ� ���� �ð��� �⵵,��,���� �˾Ƴ���.
		
		Date sDay = new Date(presentTime.YEAR + 115, presentTime.MONTH - 1, presentTime.DATE-3); //sDay�� ������ �⵵, ��, ���� ����ִ´�. ex)2016�� 1�� 1�� 00:00:00 ���� ����ȴ�.
		System.out.println("������ ����! : " + sDay);
		Date eDay = new Date(presentTime.YEAR + 115, presentTime.MONTH - 1, presentTime.DAY_OF_MONTH-3, 23, 59, 59); //eDay�� ������ �⵵, ��, ��, 23��59��59�ʸ� �־���´�.
		System.out.println("������ ��! : " + eDay);
		
		for (ParkFeeInfo fee : ParkingCarOut.feeList) //���� ������ ���� ��������� �����ð��� ���� feeList�� feeInfo��ü���� �ϳ��� ������ ���� ���Ѵ�.
		{
			System.out.println("���� ���� �ð� : " + fee.getTime());
			//sDay�� ���� ��(��)�� ù ���̴�. eDay�� ���� ��(��)�� �� ���̴�. ����� ���� �ð����� sDay�����̰� eDay�����̸� �Ѵ��̶�� �ð��� ���̿� �ð����� �ȴ�. �̸� ���� �Ѵ�ġ�� ����� ����� �� �ִ�.
			if ((fee.getTime().after(sDay) && fee.getTime().before(eDay))  && fee.getTime().before(eDay)) //1���̶�� ġ�� 1�� 1�� ~ 1��31�� ���̸� ����� �� ���ϴ°Ŵ�!
			{
				resultFee += fee.getFee();
			}
		}
		return resultFee;
	} //dayFee() End
	
	public int searchFee(String date1, String date2) //�Ⱓ�� ����, �˻��ؼ� ������� ����� ��� �޼ҵ�
	{
		int resultFee = 0;

		//ù��° �ؽ�Ʈ �ʵ忡 �ִ� ��,��,�� ���� �������� �ڵ�
		int fYear = Integer.parseInt(firstDateText.getText().substring(0, 4));
		int fMonth = Integer.parseInt(firstDateText.getText().substring(5, 7));
		int fDay = Integer.parseInt(firstDateText.getText().substring(8));
		
		//�ι�° �ؽ�Ʈ �ʵ忡 �ִ� ��,��,�� ���� �������� �ڵ�
		int tYear = Integer.parseInt(secondDateText.getText().substring(0, 4));
		int tMonth = Integer.parseInt(secondDateText.getText().substring(5, 7));
		int tDay = Integer.parseInt(secondDateText.getText().substring(8));
		
		Date sDay = new Date(fYear - 1900, fMonth -1, fDay); //sDay�� ������ �⵵, ��, ���� ����ִ´�. ex)2016�� 1�� 1�� 00:00:00 ���� ����ȴ�.
		System.out.println("������ ����! : " + sDay);
		Date eDay = new Date(tYear - 1900, tMonth -1, tDay, 23, 59, 59); //eDay�� ������ �⵵, ��, ��, 23��59��59�ʸ� �־���´�.
		System.out.println("������ ��! : " + eDay);
		
		for (ParkFeeInfo fee : ParkingCarOut.feeList) //���� ������ ���� ��������� �����ð��� ���� feeList�� feeInfo��ü���� �ϳ��� ������ ���� ���Ѵ�.
		{
			System.out.println("���� ���� �ð� : " + fee.getTime());
			//sDay�� ���� ��(��)�� ù ���̴�. eDay�� ���� ��(��)�� �� ���̴�. ����� ���� �ð����� sDay�����̰� eDay�����̸� �Ѵ��̶�� �ð��� ���̿� �ð����� �ȴ�. �̸� ���� �Ѵ�ġ�� ����� ����� �� �ִ�.
			if ((fee.getTime().after(sDay) && fee.getTime().before(eDay))  && fee.getTime().before(eDay)) //1���̶�� ġ�� 1�� 1�� ~ 1��31�� ���̸� ����� �� ���ϴ°Ŵ�!
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
			feeResultDialog.setVisible(true); //'����Ȯ��'�� ������ ���̾�αװ� ����ȴ�.
			feeResultDialog.dispose();
		}
	} //feeOkListener class End
	
	class termOkListener implements ActionListener //�Ⱓ �˻� ��� Ȯ���� ������ �� �����ϰ� �Ǵ� Ŭ����
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int resultFee = 0;
			termSearchDig.setVisible(false);
			termSearchDig.dispose();
			
			titleFeeLabel.setText("�˻� ��� ���� ����Դϴ�.");
			String date1 = firstDateText.getText();
			String date2 = secondDateText.getText();
			resultFee = searchFee(date1, date2);
			System.out.println("�˻� ���: " + resultFee);
			titleFeeLabel.setText("�˻� ��� ���� ����Դϴ�.");
			resultFeeLabel.setText("�� " + resultFee + "���Դϴ�.");
			makeResultDialog();
		}
	} //termOkListener class End
}
