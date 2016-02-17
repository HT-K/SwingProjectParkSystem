package MainFunctionView;

import StartProgram.*;
import StartView.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
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
	
	JButton startTermBtn = new JButton("�˻� ����");
	JButton endTermBtn = new JButton("�˻� ��");
	JButton termOkBtn = new JButton("�˻�Ȯ��");
	
	UtilDateModel model1 = new UtilDateModel(); //�Ⱓ �˻����� �޷��� ����ϱ� ���� �ʿ��� �͵�, JDatePicker �̿�
	JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
	JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
	
	UtilDateModel model2 = new UtilDateModel(); //�Ⱓ �˻����� �޷��� ����ϱ� ���� �ʿ��� �͵�
	JDatePanelImpl datePanel2 = new JDatePanelImpl(model2);
	JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
	
	ImageIcon imgIcon = new ImageIcon("�α׾ƿ�.png");
	
	public ParkingFeeList (ParkingStartView frame) //ParkingSystem���� '�������'��ư Ŭ�� �̺�Ʈ �߻� �� ������� ���̾�α� ��� ��ġ�� �Ѿ���� �ȴ�.
	{
		this.frame = frame; //ParkingStartView��ü�� frame�� �����ͼ� ����		
		
		feeOkButton.addActionListener(new feeOkListener()); //'����Ȯ��' ��ư Ŭ�� ��, �̺�Ʈ ������
		startTermBtn.addActionListener(new startTermListener());
		endTermBtn.addActionListener(new endTermListener());
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
		termSearchDig.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 270, 150);
		termSearchDig.add(termDigPanel);
		termDigPanel.setBounds(0, 0, 200, 300);
		
		tetmTitleLabel.setBounds(65, 0, 150, 20);
		startTermBtn.setBounds(10, 30, 100, 30);
		tildeLabel.setBounds(120, 30, 20, 30);
		tildeLabel.setFont(new Font("���", Font.PLAIN, 15));
		endTermBtn.setBounds(140, 30, 100, 30);
		termOkBtn.setBounds(75, 70, 100, 30);
		
		termDigPanel.add(tetmTitleLabel);
		termDigPanel.add(startTermBtn);
		termDigPanel.add(tildeLabel);
		termDigPanel.add(endTermBtn);
		termDigPanel.add(termOkBtn);
		
		//firstDateText.requestFocus();
		termSearchDig.setVisible(true);
	} //makeTermSearchDig() End
	
	public int monthFee()
	{
		int resultFee = 0; //�̹��� �� ����� �����ϱ� ���� ����
		
		Calendar presentTime = Calendar.getInstance(); //CalendarŬ������ �̿��ؼ� ���� �ð��� �⵵,��,���� �˾Ƴ���.
		Date sDay = new Date(presentTime.get(Calendar.YEAR) - 1900, presentTime.get(Calendar.MONTH), 1); //sDay�� ���� �ð��� �⵵, ��, 1��(���۳�)�� ����ִ´�. ex)2016�� 1�� 1�� 00:00:00 ���� ����ȴ�.
		System.out.println("���� ���� ���� : " + sDay);
		Date eDay = new Date(presentTime.get(Calendar.YEAR) - 1900, presentTime.get(Calendar.MONTH), presentTime.getActualMaximum(Calendar.DATE), 23, 59, 59); //eDay�� ���� �ð��� �⵵, ��, ��������(30,31�� 2���� 28��29��) �� ����ִ´�. ex)2016�� 1�� 31�� 23:59:59 ���� ����ȴ�.
		//3916�⵵�� �����Ǿ��־ -1900�� �ؾ� ���� �ð��� �⵵�� ���´�. ����?
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
		
		Date sDay = new Date(presentTime.get(Calendar.YEAR) - 1900, presentTime.get(Calendar.MONTH), presentTime.get(Calendar.DATE)); //sDay�� ������ �⵵, ��, ���� ����ִ´�. ex)2016�� 1�� 1�� 00:00:00 ���� ����ȴ�.
		System.out.println("������ ����! : " + sDay);
		Date eDay = new Date(presentTime.get(Calendar.YEAR) - 1900, presentTime.get(Calendar.MONTH), presentTime.get(Calendar.DATE), 23, 59, 59); //eDay�� ������ �⵵, ��, ��, 23��59��59�ʸ� �־���´�.
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
	
	public int searchFee(String date1, String date2) //�Ⱓ�� ����, �˻��ؼ� ������� ����� ��� �޼ҵ�, �� ��Ʈ���������� YYYY-MM-DD ������ ���� ����ִ� (JDatePicker�� �̿��ؼ� �޾ƿ� ��¥ ���̴�)
	{
		int resultFee = 0;

		//ù��° JFormattedTextField() �ʵ忡 �ִ� ��,��,�� ���� �������� �ڵ�
		int fYear = Integer.parseInt(date1.substring(0, 4)); 
		int fMonth = Integer.parseInt(date1.substring(5, 7));
		int fDay = Integer.parseInt(date1.substring(8));
		
		//�ι�° JFormattedTextField() �ʵ忡 �ִ� ��,��,�� ���� �������� �ڵ�
		int tYear = Integer.parseInt(date2.substring(0, 4));
		int tMonth = Integer.parseInt(date2.substring(5, 7));
		int tDay = Integer.parseInt(date2.substring(8));
		
		Date sDay = new Date(fYear - 1900, fMonth -1, fDay); //sDay�� ������ �⵵, ��, ���� ����ִ´�. ex)2016�� 1�� 1�� 00:00:00 ���� ����ȴ�.
		System.out.println("�Ⱓ ����! : " + sDay);
		Date eDay = new Date(tYear - 1900, tMonth -1, tDay, 23, 59, 59); //eDay�� ������ �⵵, ��, ��, 23��59��59�ʸ� �־���´�.
		System.out.println("�Ⱓ ��! : " + eDay);
		
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
	
	class feeOkListener implements ActionListener //����Ȯ�� ��ư Ŭ�� �� �̺�Ʈ �ڵ鷯
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			feeResultDialog.setVisible(true); //'����Ȯ��'�� ������ ���̾�αװ� ����ȴ�.
			feeResultDialog.dispose();
		}
	} //feeOkListener class End
	
	class startTermListener implements ActionListener //�˻� ���� ��¥�� �˱� ���� ����
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			startTermBtn.setVisible(false);
			
			model1 = new UtilDateModel(); //�޷��̶�� �����ϸ� �ȴ�.
			datePanel1 = new JDatePanelImpl(model1); //�޷������� model2�� �гο� ��Ƴ��´�.
			datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter()); //������ JDatePickerImpl Ŭ������ �̿��� �޷� ��°� ��¥ Ŭ�� �� DateLabelFormatter�ؽ�Ʈ �ʵ忡 �ڵ� �߰��ǰ��Ѵ�.
			datePicker1.setBounds(5, 30, 110, 30);

			termDigPanel.add(datePicker1); //���̾�α׿� JDatePicker���
		}
	}
	
	class endTermListener implements ActionListener //�˻� �� ��¥�� �˱� ���� ��ư Ŭ�� ��
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			endTermBtn.setVisible(false);
			
			model2 = new UtilDateModel(); //�޷��̶�� �����ϸ� �ȴ�.
			datePanel2 = new JDatePanelImpl(model2); //�޷������� model2�� �гο� ��Ƴ��´�.
			datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter()); //������ JDatePickerImpl Ŭ������ �̿��� �޷� ��°� ��¥ Ŭ�� �� DateLabelFormatter�ؽ�Ʈ �ʵ忡 �ڵ� �߰��ǰ��Ѵ�.
			datePicker2.setBounds(140, 30, 110, 30);

			termDigPanel.add(datePicker2); //���̾�α׿� JDatePicker���
		}
	}
	
	//���� ���� �����غ��� �ʹ�...
/*	public class DatePicker extends JDatePickerImpl 
	{
		JDatePickerImpl pre;
		JDatePanelImpl datePanel;
		JFormattedTextField text = new JFormattedTextField();
		JButton btn = new JButton("�׽�Ʈ");
		
		public DatePicker(JDatePanelImpl datePanel, AbstractFormatter formatter, JPanel termDigPanel) {
			super(datePanel, formatter);
			pre = this;
			
			this.datePanel = datePanel;
			
			termDigPanel.setPreferredSize(new Dimension(100, 30));
			termDigPanel.add(text);
			text.setBounds(0, 0, 80, 30);
			termDigPanel.add(btn);
			btn.setBounds(80, 0, 100, 30);
			// TODO Auto-generated constructor stub
			btn.addActionListener(new btnActionListener());
		}
		
		class btnActionListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pre.setVisible(true);
				datePanel.setVisible(true);
			}
			
		}
	}*/
	
	public class DateLabelFormatter extends AbstractFormatter { //JDatePicker���� ��¥ ���� �� ���õ� ��¥���� ��Ʈ�� ������ ��ȯ��Ű�� Ŭ����
		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		
		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			Calendar today = Calendar.getInstance();
			return dateFormatter.format(today.getTime());
		}
	} //DateLabelFormatter class End
	
	class termOkListener implements ActionListener //�Ⱓ �˻� ��� Ȯ���� ������ �� �����ϰ� �Ǵ� Ŭ����
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int resultFee = 0;
			termSearchDig.setVisible(false);
			termSearchDig.dispose();
			
			//JDatePicker���� ���õ� ��¥�� DateLabelFormatter ��ü���� SimpleDateFormat�� ���� String������ ��ȯ�ȴ�.
			String date1 = datePicker1.getJFormattedTextField().getText(); 
			System.out.println(date1);
			String date2 = datePicker2.getJFormattedTextField().getText();
			System.out.println(date2);
			
			resultFee = searchFee(date1, date2);
			System.out.println("�˻� ���: " + resultFee);
			titleFeeLabel.setText("�˻� ��� ���� ����Դϴ�.");
			resultFeeLabel.setText("�� " + resultFee + "���Դϴ�.");
			makeResultDialog();
		}
	} //termOkListener class End
}
