package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;


import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Information.ParkCarInfo;
import StartView.ParkingStartView;

public class ParkingCarNumList {
	ParkingStartView frame; //ParkingStartView��ü�� JFrame�� �������� ���� ����
	//�⺻������ JTable�� ����ϴ� ���
	Vector<String> rowData; //���� ����� ��� ������ ������ ��� ���� ���� ����
	Vector<String> colName = new Vector<String>(); //���̺��� �� ������ �����ϱ� ���� ���ͺ���

	DefaultTableModel parkTableModel = new DefaultTableModel(); //DefaultTabelModel (vector, vector)���·� ��뿹��
	JTable parkJTable; //DefaultTableModel�� ��� ���� JTable����, JTable�� ���� �� �ϳ��� TableModel�̴�
	public static JScrollPane parkScroll; //JTable�� Scroll����� �ޱ� ���� JScroll����, '������Ȳ'Ŭ�� �� '��������'���̺��� ������ �ʾƾ��ϹǷ� ���� ��ȣ�ۿ��ϱ� ���� static�� ����
	
	public ParkingCarNumList (ParkingStartView frame)
	{
		this.frame = frame;
		
		//JTable ���, ũ�� ���� �� ��ġ ����
		colName.addElement("���� ���� ���� ��ȣ ���"); //colName���Ϳ� �������� (���̺��� �����̶�� ����ȴ�)
		parkTableModel = new DefaultTableModel(null, colName); //DefaultTableModel�� row(��)������ null���� ���Ͱ� �� �����̴�.
		parkJTable = new JTable(parkTableModel); //JTable�� ������ ���ڿ� TableModel�� �־�����ؼ� DefaultTableModel�� ����Ѵ�
		parkScroll = new JScrollPane(parkJTable); //JTable �����ʿ� ��ũ�ѹٸ� ���δ�!
		parkScroll.setBounds(880, 280, 310, 165); //��� ���̺� ��ġ�� ũ�� ����
		
		parkScroll.setVisible(true);
		frame.add(parkScroll); //ParkingStartView�� frame�� JTable�� ��ġ�Ѵ�!
		thread.start(); //��ü ������ ���ÿ� '��������'�� �ֽ�ȭ�ϴ� �����带 �����Ų��.
	}
	
	Thread thread = new Thread(new Runnable() { //'��������'���̺� ��µ� ������� �ڵ����� �����ǰԲ� �����Ѵ�.
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true)
			{
				//�߰��� ������� ���ļ� �ٽ� ����ϱ� ���� ���� Table�� ���ִ� ������ ���� �����.
				int rowCount = parkTableModel.getRowCount();
				for (int i = rowCount - 1; i > -1; i--)
				{
					parkTableModel.removeRow(i);
				}
				
				for (ParkCarInfo car : ParkingCarIn.parkCarList)
				{
					String carNum = "";
					if (car.getparkPlaceNum() > 0 && car.getparkPlaceNum() <= 20)
					{
						carNum = "1�� " + car.getparkPlaceNum() + "�� ���������� " + car.getcarNum() +"�� ���� ������";
					}
					else if (car.getparkPlaceNum() > 20 && car.getparkPlaceNum() <= 40)
					{
						carNum = "2���� " + car.getparkPlaceNum() + "�� ���������� " + car.getcarNum() +"�� ���� ������";
					}
					else
					{
						carNum = "3���� " + car.getparkPlaceNum() + "�� ���������� " + car.getcarNum() +"�� ���� ������";
					}
					rowData = new Vector<String>(); //���� ����
					rowData.addElement(carNum); //������ ���Ϳ� ��Ʈ�� �� ����
					parkTableModel.addRow(rowData); //���̺��� �࿡ ��Ʈ�� ��(�������)�� �ϳ��� �߰��Ѵ�.
				}
				
				try {
					thread.sleep(1000);
				} catch (InterruptedException e) {}
			} //while End
		} //run() End
	}); //Thread End
	
	
}
