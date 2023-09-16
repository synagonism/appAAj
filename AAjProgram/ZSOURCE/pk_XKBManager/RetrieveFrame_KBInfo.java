/*
 * RetrieveFrame_KBInfo.java - Info on program's XKnowledgeBase.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2005 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Generic Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package pk_XKBManager;

import pk_XKBManager.AAj;
import pk_Util.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * XKnowledgeBase Statistics.
 *
 * @modified
 * @since 2008.04.26
 * @author HoKoNoUmo
 */
public class RetrieveFrame_KBInfo extends JFrame
{
	static final long serialVersionUID = 21L;

	JLabel				jlblChooseVS;
	JLabel				jlblDescription;
	JLabel				jlblFullName;
	JLabel				jlblStatistics;

	JTextField		jtfDescription;
	JTextField		jtfFullName;

	JComboBox		jcbSBWV;//SensorialBrainWorldview
	JTable				jtbVD = new JTable();

	MyTableModel mtm;


	/**
	 *
	 * @modified
	 * @since 2008.04.26
	 * @author HoKoNoUmo
	 */
	public RetrieveFrame_KBInfo()
	{
		super(AAj.rbLabels.getString("fkbiTitle"));//KB statistics

		jlblChooseVS = new JLabel(AAj.rbLabels.getString("MsgChooseSrWV")+":",JLabel.RIGHT);
		jlblDescription = new JLabel(AAj.rbLabels.getString("Description")+":",JLabel.RIGHT);
		jtfDescription = new JTextField();
		jlblFullName = new JLabel(AAj.rbLabels.getString("FullName")+":",JLabel.RIGHT);
		jtfFullName = new JTextField();
		jlblStatistics = new JLabel(AAj.rbLabels.getString("Statistics"),JLabel.RIGHT);

		jcbSBWV = new JComboBox();
		//put info
		Iterator it = AAj.trmapSrBrWorldview.entrySet().iterator();
		for (int i=0; i<AAj.trmapSrBrWorldview.size(); i++)
		{
			Map.Entry et = (Map.Entry) it.next();
			String k = (String)et.getKey();
			jcbSBWV.addItem(k);
		}
		jcbSBWV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mtm = new MyTableModel();
				jtbVD.setModel(mtm);
				setColumnsWidth(jtbVD);
				jtbVD.setPreferredScrollableViewportSize(new Dimension(749,	196));
			}
		});

		//Create the Layouts.
		JPanel jpNLeft = new JPanel(new GridLayout(3,1,1,0));
		jpNLeft.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		jpNLeft.add(jlblChooseVS);
		jpNLeft.add(jlblDescription);
		jpNLeft.add(jlblFullName);
		JPanel jpNCenter = new JPanel(new GridLayout(3,1,1,0));
		jpNCenter.add(jcbSBWV);
		jpNCenter.add(jtfDescription);
		jpNCenter.add(jtfFullName);
		JPanel jpNorth = new JPanel(new BorderLayout());
		jpNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		jpNorth.add(jpNLeft, BorderLayout.WEST);
		jpNorth.add(jpNCenter, BorderLayout.CENTER);

		JPanel jpCenter = new JPanel(new BorderLayout());
		JPanel jpCNorth = new JPanel();
		jlblStatistics.setFont(new Font("sansserif", Font.BOLD, 20));
		jpCNorth.add(jlblStatistics);
		JScrollPane jspTable = new JScrollPane(jtbVD);
		jpCenter.add(jpCNorth, BorderLayout.NORTH);
		jpCenter.add(jspTable, BorderLayout.CENTER);

		JPanel jpMain = new JPanel(new BorderLayout());
		jpMain.setBorder(BorderFactory.createEtchedBorder());
		jpMain.add(jpNorth, BorderLayout.NORTH);
		jpMain.add(jpCenter, BorderLayout.CENTER);

		getContentPane().add(jpMain, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		pack();
		Util.setFrameIcon(this);
		setLocation(12,126);
		setSize(new Dimension(759,	359));
		setVisible(true);
	}


	public void setColumnsWidth(JTable t){
		//set columns width.
		TableColumn column = null;
		column = t.getColumnModel().getColumn(0);
		column.setPreferredWidth(19);
		column = t.getColumnModel().getColumn(1);
		column.setPreferredWidth(133);
		column = t.getColumnModel().getColumn(2);
		column.setPreferredWidth(200);
		column = t.getColumnModel().getColumn(3);
		column.setPreferredWidth(150);
		column = t.getColumnModel().getColumn(4);
		column.setPreferredWidth(70);
		column = t.getColumnModel().getColumn(5);
		column.setPreferredWidth(70);
		column = t.getColumnModel().getColumn(6);
		column.setPreferredWidth(70);
		column = t.getColumnModel().getColumn(7);
		column.setPreferredWidth(70);
		column = t.getColumnModel().getColumn(8);
		column.setPreferredWidth(70);
		t.setCellSelectionEnabled(false);
		t.setColumnSelectionAllowed(false);
		t.setRowSelectionAllowed(false);
	}

	/**
	 *
	 * @modified 2008.04.30
	 * @since 2008.04.30 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class MyTableModel extends AbstractTableModel
	{
		static final long serialVersionUID = 21L;
		String[] columnNames = {"#", "SubWorldview","Description","Directory",
				"Last-ID","Empty-IDs","File-Cpts","Internals","Total-Cpts"};

		Object[][] data;

		public MyTableModel(){
			String vs = (String)jcbSBWV.getSelectedItem();
			String[] v = AAj.trmapSrBrWorldview.get(vs);
			jtfDescription.setText(v[0]);
			jtfFullName.setText(v[1]);

			int lastRow=0;
			Iterator itr = AAj.trmapSrBrSubWorldview.entrySet().iterator();
			for (int j=0; j<AAj.trmapSrBrSubWorldview.size(); j++)
			{
				Map.Entry et = (Map.Entry) itr.next();
				String key = (String)et.getKey();
				if (key.startsWith(vs))
					lastRow++;
			}
			lastRow++;//put an extra row for sum

			data = new Object[lastRow][9];
			int sumFiles=0;		 	//counter for all file-xcpt.
			int sumInternals=0;	//counter for all internal-xcpt.
			int sumTotals=0;			//counter for all sbcpt.

			Iterator it = AAj.trmapSrBrSubWorldview.entrySet().iterator();
			for (int i=0; i<AAj.trmapSrBrSubWorldview.size(); i++)
			{
				Map.Entry et = (Map.Entry) it.next();
				String key = (String)et.getKey();
				if (key.startsWith(vs)){
					String[] a = (String[]) et.getValue();
					//lust id number.
					int lastFormalNumber = Integer.parseInt(a[2]);
					//number of empty ids.
					TreeSet<String> set = AAj.tmapEmptyID.get(key);
					int ids;
					if (set!=null) ids=set.size();
					else ids=0;
					String stids = String.valueOf(ids);
					//number of file-concepts.
					int n = lastFormalNumber-ids;
					String stn = String.valueOf(n);
					//internals
					int n2 = Integer.parseInt(a[3]);
					//total
					int n3 = n+n2;
					String stn3 = String.valueOf(n3);
					//sum
					sumFiles=sumFiles+n;
					sumInternals=sumInternals+n2;
					sumTotals=sumTotals+n3;
					data[i][0]=String.valueOf(i+1);	//number of vd
					data[i][1]=key;								//name
					data[i][2]=a[0];								//description
					data[i][3]=a[1];								//directory
					data[i][4]=a[2];								//last-id
					data[i][5]=stids;							//empty-ids
					data[i][6]=stn;								//file-xcpts
					data[i][7]=a[3];								//internals
					data[i][8]=stn3;								//totals
				}
			}
			data[lastRow-1][0]="";
			data[lastRow-1][1]="";
			data[lastRow-1][2]="";
			data[lastRow-1][3]="";
			data[lastRow-1][4]="";
			data[lastRow-1][5]="SUM";
			data[lastRow-1][6]=String.valueOf(sumFiles);
			data[lastRow-1][7]=String.valueOf(sumInternals);
			data[lastRow-1][8]=String.valueOf(sumTotals);

		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		/*
		 * Don't need to implement this method unless your table's
		 * data can change.
		 */
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

	}


}

