package MainFunctionView;

import StartProgram.*;
import StartView.*;
import MainFunctionView.*;


import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import StartView.ParkingStartView;

public class ParkingCarNumList {
	ParkingStartView frame; //ParkingStartView��ü�� JFrame�� �������� ���� ����
	//�⺻������ JTable�� ����ϴ� ���
	Vector<String> rowData; //���� ����� ��� ������ ������ ��� ���� ���� ����
	Vector<String> colName = new Vector<String>(); //���̺��� �� ������ �����ϱ� ���� ���ͺ���

	DefaultTableModel parkTableModel = new DefaultTableModel(); //DefaultTabelModel (vector, vector)���·� ��뿹��
	JTable parkJTable; //DefaultTableModel�� ��� ���� JTable����, JTable�� ���� �� �ϳ��� TableModel�̴�
	public static JScrollPane parkScroll; //JTable�� Scroll����� �ޱ� ���� JScroll����, '������Ȳ'Ŭ�� �� '��������'���̺��� ������ �ʾƾ��ϹǷ� ���� ��ȣ�ۿ��ϱ� ���� static�� ����
	
	
}
