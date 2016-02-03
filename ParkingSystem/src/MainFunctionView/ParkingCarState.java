package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;
import Information.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
	
public class ParkingCarState implements ActionListener {
	public ParkingStartView frame; //ParkingStartView��ü�� JFrame�� �������� ���� ����
	String[][] rowName = new String[3][3]; //�࿡ ����� ������ ���̱� ���� 3X3�� ���̺����¸� ���� 2�����迭 ����
	String[] colName = {"����?", "������Ȳ", "����� ��"}; //���� ��Ÿ�� ��� �����!
	
	DefaultTableModel parkTableModel; //DefaultTabelModel (vector, vector)���·� ��뿹��
	JTable parkJTable; //DefaultTableModel�� ��� ���� JTable����
	public static JScrollPane parkScroll; //JTable�� Scroll����� �ޱ� ���� JScroll����, '��������'Ŭ�� �� '������Ȳ'���̺��� ��������ϴ� ��ȣ�ۿ��� �ϱ� ���� static���� �����Ѵ�.
	
	//�����ڿ� ȸ�� ����
	int memCheck = 0;
	
	public ParkingCarState (ParkingStartView frame, int memCheck)
	{
		this.memCheck = memCheck;
		this.frame = frame; //ParkingStartView���� ������ frame�� �����Ѵ�.
		
		rowName[0][0] = "1��"; //�� ó����  �࿡ �� ����� ������̴�
		rowName[1][0] = "2��";
		rowName[2][0] = "3��";
		parkTableModel = new DefaultTableModel(rowName, colName); //DefaultTableModel�� ���� ���� null���� ���Ͱ� �� �����̴�.
		parkJTable = new JTable(parkTableModel); //JTable�� ������ ���ڿ� TableModel�� �־�����ؼ� DefaultTableModel�� ����Ѵ�
		parkJTable.setFont(new Font("����", Font.PLAIN, 14));
		parkScroll = new JScrollPane(parkJTable); //JTable�� Scroll����� �ִ´�.
		parkScroll.setBounds(880, 200, 310, 72); //��� ���̺� ��ġ�� ũ�� ����
		
		parkScroll.setVisible(true);
		frame.add(parkScroll); //ParkingStartView�� frame�� JTable�� ��ġ�Ѵ�!
		thread.start(); //��ü ������ ���ÿ�'������Ȳ'�� �ֽ�ȭ�ϴ� �����带 �����Ų��.
	} //ParkingCarState ������ End

	//'ParkingMainView���� '������Ȳ' Ŭ�� �� ����Ǵ� �̺�Ʈ ������
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
		ParkingCarList.parkScroll.setVisible(false); //���� ������ ��µǴ� '��������'�� ������ �ʰ� �����Ѵ�.
		parkScroll.setVisible(true); //'������Ȳ'���̺��� ��µǰ� �����Ѵ�.
	} 
	
	Thread thread = new Thread(new Runnable() { //'������Ȳ'���̺� ��µ� ������� 1�ʸ��� �ڵ����� �����ǰԲ� �����Ѵ�.
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true)
			{
				//�� ���� ���� �� ���������� ���ڵ��� ���� �������̴�
				int firstIn = 0;
				int firstRemain = 0;
				int secondIn = 0;
				int secondRemain = 0;
				int thirdIn = 0;
				int thirdRemain = 0;
				for (ParkCarInfo car : ParkingCarIn.parkCarList) //������ ������ �����Ͱ� ����� ArrayList���� �� ������ �������� ��ȣ�� �����´�.
				{
					int parkPlaceNum = car.getparkPlaceNum();
					if (parkPlaceNum <= 20)
					{
						++firstIn; //������ϵ� ���� �ε��� ��ȣ�� 20���� �۰ų� ���� ��� 1���̴�
					}
					else if (parkPlaceNum >= 21 && parkPlaceNum <= 40)
					{
						++secondIn; //������ϵ� ���� �ε��� ��ȣ�� 20���� ũ�ų� ���� 40���� �۰ų� ���� ���� ��� 1���̴�
					}
					else
					{
						++thirdIn; //�������� 3��!
					}
				} //for�� End
				
				firstRemain = 20 - firstIn; //1���� ���� ������ ��
				secondRemain = 20 - secondIn; //2���� ���� ������ ��
				thirdRemain = 20 - thirdIn; //3���� ���� ������ ��
				
				//2���� �迭�� ���� DefaultTable�� �̷��� ��� ������ �ʱ�ȭ �Ǿ����� ������ ��Ÿ�� �� ����. ��ư�� Ŭ�� �� ������ ���� �����͸� �ʱ�ȭ ��Ų��.
				rowName[0][0] = "1��";
				rowName[1][0] = "2��";
				rowName[2][0] = "3��";
				rowName[0][1] = String.valueOf(firstIn);
				rowName[0][2] = String.valueOf(firstRemain);
				rowName[1][1] = String.valueOf(secondIn);
				rowName[1][2] = String.valueOf(secondRemain);
				rowName[2][1] = String.valueOf(thirdIn);
				rowName[2][2] = String.valueOf(thirdRemain); 
				parkTableModel = new DefaultTableModel(rowName, colName); //�ʱ�ȭ ��Ų �����͸� DefaultTableModel�� �־ ��ü�� �����ϰ�
				parkJTable.setModel(parkTableModel); //���� ������ TableModel�� JTable�� set��Ű�� ���ο� �����͸� ���� TableModel�� ȭ�鿡 ���̰� �ȴ�.
				try {
					thread.sleep(1000);
				} catch (InterruptedException e) {}
			} //while End
		} //run() End
	}); //Thread End
} //ParkingCarState class End
