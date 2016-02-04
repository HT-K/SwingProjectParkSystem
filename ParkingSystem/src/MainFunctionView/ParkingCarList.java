package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

public class ParkingCarList implements ActionListener {
	ParkingStartView frame; //ParkingStartView��ü�� JFrame�� �������� ���� ����
	//�⺻������ JTable�� ����ϴ� ���
	Vector<String> rowData; //���� ����� ��� ������ ������ ��� ���� ���� ����
	Vector<String> colName = new Vector<String>(); //���̺��� �� ������ �����ϱ� ���� ���ͺ���

	DefaultTableModel parkTableModel = new DefaultTableModel(); //DefaultTabelModel (vector, vector)���·� ��뿹��
	JTable parkJTable; //DefaultTableModel�� ��� ���� JTable����, JTable�� ���� �� �ϳ��� TableModel�̴�
	public static JScrollPane parkScroll; //JTable�� Scroll����� �ޱ� ���� JScroll����, '������Ȳ'Ŭ�� �� '��������'���̺��� ������ �ʾƾ��ϹǷ� ���� ��ȣ�ۿ��ϱ� ���� static�� ����
	
	public ParkingCarList (ParkingStartView frame)
	{
		this.frame = frame; //ParkingStartView���� ������ frame�� �����Ѵ�.
		
		//JTable ���, ũ�� ���� �� ��ġ ����
		colName.addElement("���� ���� ���"); //colName���Ϳ� �������� (���̺��� �����̶�� ����ȴ�)
		parkTableModel = new DefaultTableModel(null, colName); //DefaultTableModel�� row(��)������ null���� ���Ͱ� �� �����̴�.
		parkJTable = new JTable(parkTableModel); //JTable�� ������ ���ڿ� TableModel�� �־�����ؼ� DefaultTableModel�� ����Ѵ�
		parkScroll = new JScrollPane(parkJTable); //JTable �����ʿ� ��ũ�ѹٸ� ���δ�!
		parkScroll.setBounds(880, 200, 310, 240); //��� ���̺� ��ġ�� ũ�� ����
		
		parkScroll.setVisible(false);
		frame.add(parkScroll); //ParkingStartView�� frame�� JTable�� ��ġ�Ѵ�!
		thread.start(); //��ü ������ ���ÿ� '��������'�� �ֽ�ȭ�ϴ� �����带 �����Ų��.
	} //ParkingCarList ������ End
	
	//'��������' Ŭ�� �� �߻��� �̺�Ʈ ó�� �ڵ鷯
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ParkingCarState.parkScroll.setVisible(false); //���� ������ ��µǴ� '������Ȳ'�� ������ �ʰ� �����Ѵ�.
		ParkingCarNumList.parkScroll.setVisible(false); //���� ������ ��µǴ� '����������ȣ'�� ������ �ʰ� �����Ѵ�.
		parkScroll.setVisible(true); //'��������' ���
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
				
				for (String parkData : ParkingCarIn.parkPrintList)
				{
					rowData = new Vector<String>(); //���� ����
					rowData.addElement(parkData); //������ ���Ϳ� ��Ʈ�� �� ����
					parkTableModel.addRow(rowData); //���̺��� �࿡ ��Ʈ�� ��(�������)�� �ϳ��� �߰��Ѵ�.
				}
				
				try {
					thread.sleep(1000);
				} catch (InterruptedException e) {}
			} //while End
		} //run() End
	}); //Thread End
}
