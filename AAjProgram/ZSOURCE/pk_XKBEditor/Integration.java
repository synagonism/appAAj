/**
 * Integration.java - Integration related methods.
 * Copyright (C) 2000-2001 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 */
package pk_XKBEditor;

import pk_XKBManager.AAj;
import pk_Util.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @modified 2000.06.11
 * @since 2000.02.23
 * @author HoKoNoUmo
 */
 public class Integration
{

	public int confirm; //for confirm dialogs
	public String passedAtt="yes";
	public String passedWho="yes";
	public String passedGen="yes";
	public String passedSub="yes";
	public String passedEnv="yes";

	/**
	 * ENVIRONMENT-INTEGRATION:
	 * IF current is an OBJECT AND the env is a corelaton THEN
	 * a) the objects of the env must be >= 2 and
	 * b) the env must be an env on its objects. [1999.02.22]
	 * eg: current=HardDisk has as env 'HardDisk&CDROM' THEN
	 * a) 'HardDisk&CDROM' has as env the 'HardDisk' and 'CDROM', ie. >=2 objects.
	 * b) 'HardDisk&CDROM' is and env of lg_object 'CDROM'.
	 *
	 * IF current is an OBJECT and the env is an lg_object AND IF current is a corelaton (hknu.symb-1)
	 * THEN the objects of its environment must have the current in their environment.
	 * [1999.02.28]
	 * eg: current=HardDisk&CDROM
	 * THEN the 'HardDisk' and 'CDROM' cpts have current in their environment.
	 * @modified 2000.06.02
	 * @since 1999.02.23
	 * @author HoKoNoUmo
	 */
	public void environmentIntegration (String sbcpt)
	{
		boolean isRelation = Util.isSpecificOf(sbcpt,"hknu.meta-1");

		Extract ex = new Extract();
		if (AAj.vectorEnvID.isEmpty() && AAj.intEnv>0)
		{
			ex.extractEnvironments(sbcpt);
			AAj.vectorEnvID = ex.vectorEnvID;
		}
		if (AAj.vectorGenID.isEmpty() && AAj.intGen>0)
		{
			ex.extractGenerics(sbcpt);
			AAj.vectorGenID = ex.vectorGenID;
		}

		//check ALL ENVIRONMENT-cpts of the current xConcept
		for (int j=0; j<AAj.vectorEnvID.size(); j++)
		{
			String cptEnvID =  AAj.vectorEnvID.get(j);
			String cptEnv = Util.getXCpt_sLastFileName(cptEnvID);

			//extract the envs of the cptEnv.
			Vector vectorEnvEnvID = new Vector(); //holds the objects of each env-xcpt
			try {
				//Extract the ENVIRONMENT-concepts (id-values) of the file
				ex.extractEnvironments(cptEnv);
				vectorEnvEnvID = ex.vectorEnvID;
			}
			catch (Exception elist)
			{
				confirm = JOptionPane.showConfirmDialog(null,
										"INTEGRATION-ENVIRONMENT FAILED: "
									+ "\nxml-file for the ENVIRONMENT ("	+cptEnv +")"
									+ " of the current-sbcpt, there is NOT exist."
									+ "\nDO YOU WANT TO CONTINUE?", "Choose One", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_OPTION)
				{
					passedEnv="no";
					continue;
				}
				else {
					passedEnv="no";
					break;
				}
			}


			if (!isRelation && Util.isSpecificOf(cptEnvID,"hknu.meta-1")) // if CURRENT is an OBJECT AND env a corelaton.
			{
				// IF the env-xcpt is a corelaton, the number of objects must be >=2.
				if (vectorEnvEnvID.size()<2)
				{
					confirm = JOptionPane.showConfirmDialog(null,
											"INTEGRATION-ENVIRONMENT FAILED: "
										+ "\nThe 'objects' of the ENVIRONMENT-sbcpt ("	+cptEnv +") are NOT >= 2."
										+ "\nDO YOU WANT TO CONTINUE?", "Choose One", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.YES_OPTION)
					{
						passedEnv="no";
						continue;
					}
					else {
						passedEnv="no";
						break;
					}
				}

				// CHECK ALL the objects of cptEnv If have the cptEnv as ENVIRONMENT-sbcpt.
				for (int k=0; k<vectorEnvEnvID.size(); k++)
				{
					String objEnvID = (String)vectorEnvEnvID.get(k);
					if (objEnvID.equals(AAj.xcpt_sFormalID)) continue;//don't check current.

					//Find the file with name sbcpt.
					String fileName2 = null;
					Vector vectorEnv2 = new Vector(); //holds the env-xcpt of each lg_object
					try {
						fileName2 = Util.getXCpt_sLastFileName(objEnvID);
						//Extract the ENVIRONMENT-concepts (id-values) of the file
						ex.extractEnvironments(fileName2);
						vectorEnv2 = ex.vectorEnvID;
					}
					catch (Exception elist)
					{
						confirm = JOptionPane.showConfirmDialog(null,
												"INTEGRATION-ENVIRONMENT FAILED: "
											+ "\nxml-file for the OBJECT (" +fileName2 +") of ("	+cptEnv
											+ ") of the current-sbcpt, there is NOT exist."
											+ "\nDO YOU WANT TO CONTINUE?", "Choose One", JOptionPane.YES_NO_OPTION);
						if(confirm == JOptionPane.YES_OPTION)
						{
							passedEnv="no";
							continue;
						}
						else {
							passedEnv="no";
							break;
						}
					}

					if (!vectorEnv2.contains(cptEnvID))
					{
						confirm = JOptionPane.showConfirmDialog(null,
											 "INTEGRATION-ENVIRONMENT FAILED:"
											+"\nOBJECT (" +fileName2 +") does NOT "
											+"\ncontain (" +cptEnv +")."
											+"\nDO YOU WANT TO FIX IT?", "Choose One", JOptionPane.YES_NO_OPTION);
						if(confirm == JOptionPane.YES_OPTION)
						{
							Insert ins = new Insert();
							ins.insertAttEnvironmentInFile(fileName2,
																					cptEnv,									//FILENAME
																					null,									//Generic
																					AAj.kb_sAuthor);					//Author
							passedEnv="yes";
							continue;
						}
						else {
							passedEnv="no";
							continue;
						}
					}
				}// end of contains for2 check env of env

				if (passedEnv.equals("no"))
				{
					confirm = JOptionPane.showConfirmDialog(null,
											"INTEGRATION-ENVIRONMENT FAILED: "
										+ "\nENVIRONMENT-sbcpt " +cptEnv +" have NOT been integrated into CURRENT-xcpt."
										+ "\nDO YOU WANT TO CONTINUE?", "Choose One", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.YES_OPTION)
					{
						continue;
					}
					else {
						break;
					}
				}
			}//end current is an lg_object


			else	//IF current is a corelaton and current is an lg_object and env is lg_object,
						//THEN its env-objects must have the current in its env
			{
				if (!vectorEnvEnvID.contains(AAj.xcpt_sFormalID))
				{
					confirm = JOptionPane.showConfirmDialog(null,
											 "INTEGRATION-ENVIRONMENT FAILED:"
											+"\nOBJECT (" +cptEnv +") does NOT "
											+"\ncontain the CURRENT in its environment."
											+"\nDO YOU WANT TO FIX IT?", "Choose One", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.YES_OPTION)
					{
						Insert ins = new Insert();
						ins.insertAttEnvironmentInFile(cptEnv,
																				sbcpt,										//FILENAME
																				"Relation@hknu.symb-16.xml",	//Generic
																				AAj.kb_sAuthor);					//Author
						passedEnv="yes";
						continue;
					}
					else {
						passedEnv="no";
						continue;
					}
				}
				if (passedEnv.equals("no"))
				{
					confirm = JOptionPane.showConfirmDialog(null,
											"INTEGRATION-ENVIRONMENT FAILED: "
										+ "\nENVIRONMENT-sbcpt " +cptEnv +" have NOT been integrated into CURRENT-xcpt."
										+ "\nDO YOU WANT TO CONTINUE?", "Choose One", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.YES_OPTION)
					{
						continue;
					}
					else {
						break;
					}
				}
			}

		}

		if (passedEnv.equals("yes"))
		{
			if (!isRelation)
			{
				JOptionPane.showMessageDialog(null,
										"ENVIRONMENT-INTEGRATION PASSED:"
									+ "\nALL the ENVIRONMENT-RELATIONS have at least 2 objects AND they are envs in their OBJECTS."
									+ "\nAND ALL the ENVIRONMENT-OBJECTS have the current in their environment.");
			}
			else//Current IS a corelaton.
			{
				JOptionPane.showMessageDialog(null,
										"ENVIRONMENT-INTEGRATION PASSED:"
									+ "\nALL ENVIRONMENT OBJECTS have "
									+ "\nthe CURRENT in its environment.");
			}
		}
	}

	/**
	 * DO GENERIC-INTEGRATION:
	 * a) IF current has as GENERIC sbcpt-y
	 * THEN the PARTS of sbcpt-y are attributes of current.
	 * [nikos, 1999.01.17]
	 *
	 * b) IF current has as GENERIC sbcpt-y
	 * THEN sbcpt-y has as SPECIFIC current.
	 * [1999.02.28]
	 *
	 * c) Check if there are inherited-parts that does not belong to any of its generics. 2000.06.05
	 *
	 * d) Check if there are same inherited-overridden and inherited-nonOverridden parts.
	 * (the same genvalue comes 2 times). 2000.06.05
	 * @modified 2000.06.05
	 * @author HoKoNoUmo
	 */
	public void genericIntegration(String sbcpt)
	{
		Vector vectorParInhGenID = new Vector();
		Extract ex = new Extract();
		//we do an extraction only once per sbcpt.
		if (AAj.vectorGenID.isEmpty() && AAj.intGen>0)
		{
			ex.extractGenerics(sbcpt);
			AAj.vectorGenID = ex.vectorGenID;
		}
		if (AAj.vectorParID.isEmpty() && AAj.intPar>0)
		{
			ex.extractParts(sbcpt);
			AAj.vectorParID = ex.vectorParID;
			AAj.vectorParFileID = ex.vectorParFileID;
			AAj.vectorParGenID = ex.vectorParGenID;
			vectorParInhGenID=ex.vectorParInhGenID;
		}
		//will hold ALL parts of the generic-cpts.
		Vector<String> vectorAllGenParID = new Vector<String>();

		//a) check ALL generic-cpts of current xConcept
		for (int j=0; j<AAj.vectorGenID.size(); j++)
		{
			String cptGenID =  AAj.vectorGenID.get(j);
			String cptGen = Util.getXCpt_sLastFileName(cptGenID);
			Vector<String> vectorGenPar = new Vector<String>();
			Vector<String> vectorGenParID = new Vector<String>();
			Vector<String> vectorGenSub = new Vector<String>(); //will hold the subs of one gen.
			Vector<String> vectorGenSubID = new Vector<String>();

			try {
				//Extract the Part/Specific-concepts (id-values) of the generic-sbcpt
				ex.extractParts(cptGen);
				ex.extractSpecifics(cptGen);
				vectorGenPar = ex.vectorPar;
				vectorGenParID = ex.vectorParID;
				vectorGenSub = ex.vectorSpe;
				vectorGenSubID = ex.vectorSpeID;
			}
			catch (Exception elist)
			{
				confirm = JOptionPane.showConfirmDialog(null,
									"GENERIC-INTEGRATION for (" +sbcpt +") FAILED"
									+"\nxml-file for the GENERIC (" +cptGen
									+") of the CURRENT-xcpt, there is NOT exist."
									+"\nDO YOU WANT TO CONTINUE?", "Choose One", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.NO_OPTION)
				{
					passedGen="no";
					break;
				}
				else if(confirm == JOptionPane.YES_OPTION)
				{
					passedGen="no";
					continue;
				}
			}

			vectorAllGenParID.addAll(vectorGenParID);

			// If the attributes of the generic-sbcpt are contained by vectorParID fine,
			// if not throw filedialog
			for (int k=0; k<vectorGenPar.size(); k++)
			{

				String genPar = vectorGenPar.get(k);
				String genParID = Util.getXCpt_sFormalID(genPar);
				// The inherited overriden or not, are on 'generic=' xml-attribute.
				if (!AAj.vectorParGenID.contains(genParID))
				{
					confirm = JOptionPane.showConfirmDialog(null,
										"GENERIC-INTEGRATION for (" +sbcpt +") FAILED"
										+"\nATTRIBUTE (" +genPar + ") of the GENERIC (" +cptGen
										+") is NOT ATTRIBUTE of the current-sbcpt."
										+"\nDO YOU WANT TO FIX IT?", "Choose One", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.NO_OPTION)
					{
						passedGen="no";
						continue;
					}
					else if(confirm == JOptionPane.YES_OPTION)
					{
						//INSERT a generic(INHERITED)-attribute(no file) in crt-sbcpt
						Insert ins = new Insert();
						ins.insertAttPartInFile(sbcpt,
																			genPar,									//FILENAME
																			genPar,									//Generic
																			AAj.kb_sAuthor);					//Author

						passedGen="yes";
						continue;
					}
				}
			}

			//b) current must be a specific of cptGen
			if (!vectorGenSubID.contains(AAj.xcpt_sFormalID))
			{
				confirm = JOptionPane.showConfirmDialog(null,
										 "GENERIC-INTEGRATION for (" +sbcpt +") FAILED"
										+"\nGENERIC (" +cptGen + ") does NOT have "
										+"\nas SPECIFIC the CURRENT-xcpt."
										+"\nDO YOU WANT TO FIX IT?", "Choose One", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_OPTION)
				{
					//INSERT current as specific in cptGen
					Insert ins = new Insert();
					ins.insertAttSpecificInFile(cptGen,
																			sbcpt,										//FILENAME
																			cptGen,									//Generic
																			AAj.kb_sAuthor);					//Author
					passedGen="yes";
					continue;
				}
				else {
					passedGen="no";
					continue;
				}
			}


			//c) check if the current's-inherited-cpts belong to current's-generics.
			for (int k=0; k<vectorParInhGenID.size(); k++)
			{
				String piID = (String)vectorParInhGenID.get(k);
				String parInhID=piID.substring(0,piID.indexOf(","));
				String parInhGenID=piID.substring(piID.indexOf(",")+1,piID.length());
				if (!vectorAllGenParID.contains(parInhGenID))
				{
					confirm = JOptionPane.showConfirmDialog(null,
										"GENERIC-INTEGRATION for (" +sbcpt +") FAILED"
										+"\nIts Inherited-Part-Cpt with gen=" +Util.getXCpt_sLastFileName(parInhID)
										+" does NOT belong to any of its generics."
										+"\nDO YOU WANT TO REMOVE IT from the current-sbcpt?", "Choose One", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.NO_OPTION)
					{
						passedGen="no";
					}
					else if(confirm == JOptionPane.YES_OPTION)
					{
						Remove rm = new Remove();
						rm.removeCPTEXT(sbcpt, parInhID);
						passedGen="yes";
					}
				}
			}


			//d) Check for double gens.
			Set<String> s = new HashSet<String>(AAj.vectorParGenID);
			for (Iterator<String> i=s.iterator(); i.hasNext();)
			{
				String pgid=i.next();
				if (AAj.vectorParGenID.indexOf(pgid)!=AAj.vectorParGenID.lastIndexOf(pgid))
				{
					JOptionPane.showMessageDialog(null, "BE CAREFUL!!	 There are more than ONE"
						+" inherited-parts with the SAME generic (" +Util.getXCpt_sLastFileName(pgid) +").");
					passedGen="no";
				}
			}

			if (passedGen.equals("yes"))
			{
				JOptionPane.showMessageDialog(null,
							 "GENERIC-INTEGRATION for (" +sbcpt +") PASSED"
							+"\na) The attributes of its GENERICS are also PARTS of it"
							+"\nb) It is a specific of its generics"
							+"\nc) There are no 'dangling' inherited-parts"
							+"\nd) There are no SAME inherited overridden and nonOverridden parts.");
			}
			else {
				JOptionPane.showMessageDialog(null,"GENERIC-INTEGRATION for (" +sbcpt +") FAILED");
			}
		}

		if (AAj.vectorGenID.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"GENERIC-INTEGRATION for (" +sbcpt +") PASSED"
																					+"\nThere is no generics to check.");
		}
	}

	/**
	 * Performs *** PART INTEGRATION *** of the current xConcept.
	 * IF the current has as ATTRIBUTE the xcpt-x
	 * THEN the xcpt-x has as WHOLE the current.
	 * @modified 2000.02.23
	 * @author HoKoNoUmo
	 */
	public void partIntegration (String sbcpt)
	{
		Extract ex = new Extract();
		//we do an extraction only once per sbcpt.
		if (AAj.vectorParID.isEmpty() && AAj.intPar>0)
		{
			ex.extractParts(sbcpt);
			AAj.vectorParID = ex.vectorParID;
			AAj.vectorParFileID = ex.vectorParFileID;
			AAj.vectorParGenID = ex.vectorParGenID;
		}

		// We must check ALL the attribute-cpts we have an xml-file for them.
		// (these include not-inherited and overriden-inherited) 2000.02.25
		// >>>> We CAN NOT show that NOT-OVERRIDEN-INHERITED cpts have as whole the current sbcpt.
		// INTERNAL-cpts by default have the current as whole.
		for (int j=0; j<AAj.vectorParFileID.size(); j++) //editor & viewer MUST display same file.
		{
			String cptAttID =  AAj.vectorParFileID.get(j);
			String cptAtt = Util.getXCpt_sLastFileName(cptAttID);
			Vector vectorParWhoID = new Vector();

			try {
				//Extract the whole-concepts of the file
				ex.extractWholes(cptAtt);
				vectorParWhoID = ex.vectorWhoID;//some fileNames maybe have changed, so uses IDs.
			}
			catch (Exception elist)
			{
				confirm = JOptionPane.showConfirmDialog(null,
									 "PART-INTEGRATION of (" +sbcpt +") FAILED"
									+ "\nxml-file for the ATTRIBUTE " +cptAtt
									+ " of the current-sbcpt, there is NOT exist."
									+ "\nDO YOU WANT TO CONTINUE?", "Choose One", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.NO_OPTION)
				{
					passedAtt="no";
					break;
				}
				else if(confirm == JOptionPane.YES_OPTION)
				{
					passedAtt="no";
					continue;
				}
			}

			//If attID belongs to new vector fine, if not throw filedialog
			if (!vectorParWhoID.contains(AAj.xcpt_sFormalID))
			{
				confirm = JOptionPane.showConfirmDialog(null,
										 "PART-INTEGRATION of (" +sbcpt +") FAILED"
										+"\nATTRIBUTE (" +cptAtt +") has NOT "
										+"\nas WHOLE the current-sbcpt."
										+"\nDO YOU WANT TO FIX IT?", "Choose One", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.NO_OPTION)
				{
					passedAtt="no";
					continue;
				}
				else if(confirm == JOptionPane.YES_OPTION)
				{
						Insert ins = new Insert();
						ins.insertAttWholeInFile(cptAtt,
																	sbcpt,										//whoID
																	null,									//whoGen
																	AAj.kb_sAuthor);
					passedAtt="yes";
					continue;
				}
				//I COULD find ALL the concepts that fail to integrate
				// and I'll throw one message with all these.
			}

		}
		if (AAj.vectorParID.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"PART-INTEGRATION for (" +sbcpt +") PASSED"
																					+"\nThere is no PARTS to check.");
		}
		else if (passedAtt.equals("yes"))
		{
			JOptionPane.showMessageDialog(null, "PART-INTEGRATION for (" +sbcpt +") PASSED"
																			 +"\nALL its PARTS have as WHOLE it");
		}
	}

	/**
	 * SPECIFIC-INTEGRATION:
	 * IF current has as SPECIFIC xcpt-y
	 * THEN the attributes of current are attributes of sbcpt-y.
	 * [nikos, 1999.01.17]
	 *
	 * IF current has as SPECIFIC xcpt-y
	 * THEN sbcpt-y has as GENERIC current
	 * [nikos, 1999.02.22]
	 *
	 * toDo:
	 * IF current has whole sbcpt-x
	 * THEN and spe has as whole sbcpt-x [nikkas 2000.03.22]
	 * @modified 2004.04.09
	 * @author HoKoNoUmo
	 */
	public void specificIntegration(String sbcpt)
	{
		Extract ex = new Extract();
		//we do an extraction only once per sbcpt.
		if (AAj.vectorSpeID.isEmpty() && AAj.intSpe>0)
		{
			ex.extractSpecifics(sbcpt);
			AAj.vectorSpeID = ex.vectorSpeID;
		}
		if (AAj.vectorParID.isEmpty() && AAj.intPar>0)
		{
			ex.extractParts(sbcpt);
			AAj.vectorParID = ex.vectorParID;
			AAj.vectorParFileID = ex.vectorParFileID;
			AAj.vectorParGenID = ex.vectorParGenID;
		}

		Vector vectorSubGenID = new Vector(); //Contains the gen-ids of a spe-sbcpt
		Vector vectorSubParGenID = new Vector(); //Contains the attr of a spe-sbcpt

		//for1: check ALL SPECIFIC concepts of the current xConcept.
		for (int j=0; j<AAj.vectorSpeID.size(); j++)
		{
			String cptSubID =  AAj.vectorSpeID.get(j);
			String cptSub = Util.getXCpt_sLastFileName(cptSubID);

			if (cptSubID.indexOf("#")==-1) //internal subs have the att by definition.
			{

				try {
					//Extract the INHERITED-Attribute-cpts (id-values) of the specific-sbcpt
					//extract the generic xml-attribute.
					ex.extractParts(cptSub);
					ex.extractGenerics(cptSub);
					vectorSubParGenID = ex.vectorParGenID;
					vectorSubGenID = ex.vectorGenID;
				}
				catch (Exception elist)
				{
					confirm = JOptionPane.showConfirmDialog(null,
											"INTEGRATION-SPECIFIC FAILED: "
										+ "\nxml-file for the SPECIFIC (" +cptSub +")"
										+ " of the current-sbcpt, there is NOT exist."
										+ "\nDO YOU WANT TO CONTINUE?", "Choose One", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.YES_OPTION)
					{
						passedSub="no";
						continue;
					}
					else {
						passedSub="no";
						break;
					}
				}

				// for2: If ALL attributes of the CURRENT-concept are contained by vectorSubGen fine,
				// if not throw filedialog
				for (int k=0; k<AAj.vectorParID.size(); k++)
				{
					String attID = AAj.vectorParID.get(k);
					String att = Util.getXCpt_sLastFileName(attID);
					if (!vectorSubParGenID.contains(attID))
					{
						confirm = JOptionPane.showConfirmDialog(null,
												 "INTEGRATION-SPECIFIC FAILED:"
												+"\nSUBGENERIC (" +cptSub +") has NOT "
												+"\nas INHERITED-ATTRIBUTE the " +att +"."
												+"\nDO YOU WANT TO FIX IT?", "Choose One", JOptionPane.YES_NO_OPTION);
						if(confirm == JOptionPane.YES_OPTION)
						{
							Insert ins = new Insert();
							ins.insertAttPartInFile(cptSub,
																				att,										//FILENAME
																				att,										//Generic
																				AAj.kb_sAuthor);					//Author
							passedSub="yes";
							continue;
						}
						else {
							passedSub="no";
							continue;
						}
					}
				}// end of for2

				// IF: CHECK if the SPECIFIC-sbcpt has as GENERIC the CURRENT-xcpt.
				if (!vectorSubGenID.contains(AAj.xcpt_sFormalID))
				{
					confirm = JOptionPane.showConfirmDialog(null,
											 "INTEGRATION-SPECIFIC FAILED:"
											+"\nSUBGENERIC (" +cptSub +") has NOT "
											+"\nas GENERIC the CURRENT."
											+"\nDO YOU WANT TO FIX IT?", "Choose One", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.YES_OPTION)
					{
						Insert ins = new Insert();
						ins.insertAttGenericInFile(cptSub,
																		sbcpt,											//FILENAME
																		null,										//Generic
																		AAj.kb_sAuthor);						//Author
						passedSub="yes";
						continue;
					}
					else {
						passedSub="no";
						continue;
					}
				}
			}
		}

		if (AAj.vectorSpeID.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"SPECIFIC-INTEGRATION for (" +sbcpt +") PASSED"
																					+"\nThere is no SPECIFICS to check.");
		}
		else if (passedSub.equals("yes"))
		{
			JOptionPane.showMessageDialog(null,
							 "SPECIFIC-INTEGRATION PASSED:"
							+"\nALL current's SPECIFICS "
							+"\nhave PARTS the attributes of current"
							+"\n										AND"
							+"\nALL SPECIFICS have as GENERIC the CURRENT");
		}
	}

	/**
	 * Performs ALL kinds of integration in CURRENT-xcpt.
	 * IF all these integrations pass, then put the value of the 'integrated' attribute 'yes'.
	 * @modified 2000.03.01
	 * @author HoKoNoUmo
	 */
	public void totalIntegration(String sbcpt)
	{
		partIntegration(sbcpt);
		wholeIntegration(sbcpt);
		genericIntegration(sbcpt);
		specificIntegration(sbcpt);
		environmentIntegration(sbcpt);

		if(		 passedAtt.equals("yes")
				&& passedWho.equals("yes")
				&& passedGen.equals("yes")
				&& passedSub.equals("yes")
				&& passedEnv.equals("yes") )
		{
			String fileIn = Util.getXCpt_sLastFullFileName(sbcpt);
			String fileOut = Util.getXCpt_sFullDir(sbcpt) +File.separator +"tmp";

			try {
				FileReader reader =new FileReader(fileIn);
				BufferedReader br =new BufferedReader(reader);
				FileWriter writer = new FileWriter(fileOut);
				BufferedWriter bw =new BufferedWriter(writer);
				String ln = null;

				while ((ln = br.readLine()) != null)
				{
					//set integrated=yes.
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"yes\" " +part2);
						bw.newLine();
						break;
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
				}

				while ((ln = br.readLine()) != null)
				{
					bw.write(ln);
					bw.newLine();
				}

				br.close();
				bw.close();
			}
			catch (IOException eio)
			{
				JOptionPane.showMessageDialog(null, "Integration.totalIntegration: problem in making INTEGRATED=yes",
																"WARNING", JOptionPane.WARNING_MESSAGE);
			}

			//rename files
			File f1 = new File(fileIn);
			f1.delete();
			File f2 = new File(fileOut);
			f2.renameTo(new File(fileIn));

			AAj.display(sbcpt);//redisplay sbcpt.
		}
	}

	/**
	 * Performs WHOLE integration of the current sbcpt.
	 * IF current has as WHOLE sbcpt-y
	 * THEN sbcpt-y has as ATTRIBUTE current.
	 *
	 * TODO:
	 * IF current has subs
	 * THEN current's whole is whole of subs.
	 *
	 * IF current has not whole BUT its gen has
	 * THEN the gen's whole is and current's whole. 2000.03.18
	 * @modified 2000.02.26
	 * @author HoKoNoUmo
	 */
	public void wholeIntegration (String sbcpt)
	{
		//if int.att we don't need whole integration. check other cases.

		Extract ex = new Extract();
		//we do an extraction only once per sbcpt.
		if (AAj.vectorWhoID.isEmpty() && AAj.intWho>0)
		{
			ex.extractWholes(sbcpt);
			AAj.vectorWhoID = ex.vectorWhoID;
		}

		for (int j=0; j<AAj.vectorWhoID.size(); j++)
		{
			String cptWhoID =  AAj.vectorWhoID.get(j);
			String cptWho = Util.getXCpt_sLastFileName(cptWhoID);
			Vector vectorWhoAttID = new Vector();

			try {
				//Extract the Attributes of the whole sbcpt.
				ex.extractParts(cptWho);
				vectorWhoAttID = ex.vectorParID;
			}
			catch (Exception elist)
			{
				confirm = JOptionPane.showConfirmDialog(null,
										"WHOLE-INTEGRATION for (" +sbcpt +") FAILED"
									+ "\nxml-file for the WHOLE-XCPT ("	+cptWho +")	 there is NOT exist."
									+ "\nDO YOU WANT TO CONTINUE?", "Choose One", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.NO_OPTION)
				{
					passedWho="no";
					break;
				}
				else if(confirm == JOptionPane.YES_OPTION)
				{
					passedWho="no";
					continue;
				}
			}

			//If current-sbcpt ID belongs to new vector fine, if not throw dialog
			if (!vectorWhoAttID.contains(AAj.xcpt_sFormalID))
			{
				confirm = JOptionPane.showConfirmDialog(null,
										"WHOLE-INTEGRATION for (" +sbcpt +") FAILED"
										+"\nWHOLE (" +cptWho +")	has NOT "
										+"\nas ATTRIBUTE the CURRENT-xcpt."
										+"\nDO YOU WANT TO FIX IT?", "Choose One", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.NO_OPTION)
				{
					passedWho="no";
					continue;
				}
				else if(confirm == JOptionPane.YES_OPTION)
				{
					Insert ins = new Insert();
					ins.insertAttPartInFile(cptWho,
																		sbcpt,										//FILENAME
																		null,									//Generic
																		AAj.kb_sAuthor);					//Author
					passedWho="yes";
					continue;
				}
			}
		}
		if (AAj.vectorWhoID.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"WHOLE-INTEGRATION for (" +sbcpt +") PASSED"
																					+"\nThere is no WHOLES to check.");
		}
		else if (passedWho.equals("yes") )
		{
			JOptionPane.showMessageDialog(null,
							 "WHOLE-INTEGRATION of (" +sbcpt +")	PASSED"
							+"\nALL its WHOLE have as ATTRIBUTE it") ;
		}
	}

}
