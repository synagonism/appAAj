package pk_XKBEditor;

import pk_XKBManager.*;
import pk_Logo.*;
import pk_Util.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.UIManager;

import javax.swing.event.UndoableEditListener;
import javax.swing.event.UndoableEditEvent;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import javax.swing.text.TextAction;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.text.Collator;


public class TestInsertAttributeInFile
{
	private static boolean inserted=false;
	private static String		ln = null;
	private static Collator	collator = Collator.getInstance();

	//************************************************************************
	public static void main(String[] args)
	{
		insertAttAttributeInFile("entity@hknu.symb-7",
													"structure@hknu.symb-8",
													null,
													"HoKoNoUmo");
	}

	//************************************************************************
	/**
	 * Here Copy the method you use as public static
	 * and handwrite the files: "G:\\FILE1\\AAjWORKING\\AAjProgram\\AAjKB\\HoKoNoUmo\\Symban\\"
	 */
	public static void insertAttAttributeInFile(String uniqueNm,
																		String attUnNm,
																		String attGenInh,
																		String attAut)
	{
		String fileOld = "G:\\FILE1\\AAjWORKING\\AAjProgram\\AAjKB\\HoKoNoUmo\\Symban\\Entity@hknu.symb-7@.xml";
		String fileNew = "G:\\FILE1\\AAjWORKING\\AAjProgram\\AAjKB\\HoKoNoUmo\\Symban\\"
										+"tmpInsertAttribute";
		inserted=false;
		attUnNm= "Entity's-structure@hknu.symb-8@";//Util.getXCpt_sFormalName(attUnNm);

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);

			while ((ln = br.readLine()) != null)
			{
				// insert all lines before att.
				if (!ln.startsWith("<REFINO_ATTRIBUTE"))
				{
					//set integrated=no.
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && ln.startsWith("</XCONCEPT"))//r_att is last element
					{
						bw.newLine();
						bw.write("<REFINO_ATTRIBUTE "
											+"CREATED=\"" +Util.getCurrentDate() +"\" "
											+"AUTHOR=\"" +attAut +"\">");
						bw.newLine();
						if (attGenInh==null){
							bw.write("<XCPT FRnAME=\"" +attUnNm +"\" "
												+"CREATED=\"" +Util.getCurrentDate() +"\" "
												+"AUTHOR=\"" +attAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +attUnNm +"\" "
												+"GENERICiNHERITOR=\"" +attGenInh +"\" "
												+"CREATED=\"" +Util.getCurrentDate() +"\" "
												+"AUTHOR=\"" +attAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_ATTRIBUTE>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}
				else //line STARTS with <ratt.
				{
					//write <ratt
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!ln.startsWith("</REFINO_ATTRIBUTE")
							&& !ln.startsWith("<REFINO")) {
						String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
						if (!(collator.compare(attUnNm,existingFrNm)>0)){
							//write the new-att before existing
							if (attGenInh==null){
								bw.write("<XCPT FRnAME=\"" +attUnNm +"\" "
													+"CREATED=\"" +Util.getCurrentDate() +"\" "
													+"AUTHOR=\"" +attAut +"\"/>");
							} else {
								bw.write("<XCPT FRnAME=\"" +attUnNm +"\" "
													+"GENERICiNHERITOR=\"" +attGenInh +"\" "
													+"CREATED=\"" +Util.getCurrentDate() +"\" "
													+"AUTHOR=\"" +attAut +"\"/>");
							}
							bw.newLine();

							//then we write the existing xcpt line.
							bw.write(ln);
							bw.newLine();
							inserted=true;
							break;
						}
						else //write the existing xcpt
						{
							//2 cases: xcpt and xcpt_int
							if (ln.startsWith("<INTxCPT"))
							{
								bw.newLine();
								//and all lines of internal
								while (!(ln=br.readLine()).startsWith("</INTxCPT")){
									bw.write(ln);
									bw.newLine();
									ln=br.readLine();
								}
								bw.write(ln); //the </xcpt
								bw.newLine();
								ln=br.readLine();
							} else {
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}
					}

					if (inserted) {
						continue;
					} else {
						if (attGenInh==null){
							bw.write("<XCPT FRnAME=\"" +attUnNm +"\" "
												+"CREATED=\"" +Util.getCurrentDate() +"\" "
												+"AUTHOR=\"" +attAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +attUnNm +"\" "
												+"GENERICiNHERITOR=\"" +attGenInh +"\" "
												+"CREATED=\"" +Util.getCurrentDate() +"\" "
												+"AUTHOR=\"" +attAut +"\"/>");
						}
						bw.newLine();

						// write </att and continue
						bw.write(ln);
						bw.newLine();
						inserted=true;
					}
				}
			}
			br.close();
			bw.close();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Insert.insertAttAttributeInFile: IOException",
														"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}
}
