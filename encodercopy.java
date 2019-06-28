

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class encodercopy {
	
	 //Inputs 
	static String[] bonds = { "B04", "55", "B5","A04","BB" }; // Bonds possible
	static int[] bondper = { 50, 10, 10, 20, 10};  // Ratio of the bonds

	static String[] mono = { "G", "H", "S" }; // Monomer units
	static int[] monoPer = {90, 0, 10 }; // Ratio of the units
	
	static int dp =4; // No of monomer units
	public static void main(String s[])
	{
		 try {
			generateEncodedFile(dp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Function to encode the monolist, bondlist to datafile
	 * @param dp
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public static List<Lignin>  generateEncodedFile(int dp) throws FileNotFoundException, UnsupportedEncodingException
	{
		
		encodercopy en = new encodercopy();
		String[] monoArr = getList(mono, monoPer, dp);
		
		GeneratePolymer gp = new GeneratePolymer();
		List<String[]> monoListStr = new ArrayList<String[]>();
		int n = monoArr.length;
		int noOfPer = 10000;
		gp.permute(monoArr, 0, n - 1, monoListStr, noOfPer);
		
		//sop(monoListStr.size());
		
		for(int i=0;i<monoListStr.size();i++)
		{
			sop(Arrays.toString(monoListStr.get(i)));
		}
		
		ArrayList<Integer> getSeq = Combination.printCombination(dp);	
		//sop(getSeq);
		
		String[] bondArr = getList(bonds, bondper, getSeq.size()/2);
				
		List<String[]> bondListStr = new ArrayList<String[]>();

		n = bondArr.length;
		gp.permute(bondArr, 0, n - 1, bondListStr, monoListStr.size());

		//sop(bondListStr.size());
		
		for(int i=0;i<bondListStr.size();i++)
		{
			sop(Arrays.toString(bondListStr.get(i)));
		}
		
		PrintWriter writer;		
		writer = new PrintWriter("DataSet.txt", "UTF-8");		
		List<String> data = new ArrayList<String>();
		List<Lignin> ligninList = new ArrayList<Lignin>();

		for(int lp=0;lp<monoListStr.size();lp++)
		{
			monoArr = monoListStr.get(lp);
			bondArr = bondListStr.get(lp);
			List<childNode> cNodeList = new ArrayList<childNode>();
			ArrayList<monolignol> monoList = new ArrayList<monolignol>();
			ArrayList<String> bondList = new ArrayList<String>();
			String dataStr="";
			Lignin lignin=new Lignin();
			Edges edg = new Edges();
			
			for(int i=0;i<dp;i++)
			{
				monolignol m1 = new monolignol(monoArr[i], i+1);			
				monoList.add(m1);     
			}
			for(int i=0;i<bondArr.length;i++)
			{
				bondList.add(bondArr[i]);
			}
			edg.setEdWgt(bondList);
			for (int i=0;i<getSeq.size();i+=2)
			{
				edg = (Edges) en.encode(edg, monoList.get(getSeq.get(i)-1),monoList.get(getSeq.get(i+1)-1));
				monoList.set(getSeq.get(i)-1,(monolignol) edg.getMonoL());						
			}			
			ArrayList<Integer> edgWtIn = new ArrayList<Integer>();
			for(monolignol mono: monoList)
			{			
				String[] ed= mono.getEdgeType();
				boolean setVal = false;
				for(int c=0;c<ed.length;c++)
				{
					setVal = false;
					for(int k=0;k<bonds.length;k++)
					{
						if (!setVal && ed[c]!=null && ed[c].equals(bonds[k]))
						{
							edgWtIn.add(k+1);
							setVal = true;
						}
					}
				}
			}
			edg.setEdgWgtIn(edgWtIn);
			//sop(edg);	
			//sop(monoList);
			int oldNode=0;
					
			for(int i=0;i<edg.getpNode().size();i++)
			{
				int nodeId = edg.getpNode().get(i);				
				monolignol monomer = monoList.get(nodeId-1);
				monolignol child1 = monomer.getBondl();
				monolignol child2 = monomer.getBond2();
				monolignol child3 = monomer.getBond3();				
				String nodeType = monomer.objId+monomer.getType();
				String connection1 = (child1 == null)?"":child1.objId+child1.getType();
				String connection2 = (child2 == null)?"":child2.objId+child2.getType();
				String connection3 = (child3 == null)?"":child3.objId+child3.getType();
				String[] connections = {connection1,connection2,connection3};
				String[] links = monomer.getEdgeType();
				
				String bondtype="";			
				int lbl = monomer.lbl.getIndex();
					
				
				for (int b=0;b<monomer.getEdgeType().length;b++)
					if (monomer.getEdgeType()!=null && monomer.getEdgeType()[b] != null)
					{
						bondtype = bondtype.concat(" "+monomer.getEdgeType()[b]).concat(" ");
					}				
				
				if (nodeId!= oldNode)
				{
					//writer.println(nodeId+"#\t\t"+nodeType+"\t\t"+connection1+"\t"+connection2+"\t"+connection3+"\t"+bondtype+"\t\t"+lbl);
					dataStr = dataStr.concat(nodeType+"\t("+bondtype+")"+"\t"+connection1+"\t"+connection2+"\t"+connection3+"\t|\t");								
					oldNode = nodeId;		
					
					for (int cnt=0;cnt<connections.length;cnt++)
					{
						if (connections[cnt]!=null && connections[cnt]!="" && links[cnt]!=null && links[cnt] !="" )
						{
						childNode cN = new childNode();						
						cN.setpType(nodeType);
						cN.setcType(connections[cnt]);
						cN.setBondType(links[cnt]);
						cNodeList.add(cN);
						sop(cN);
						}
					}	
					
				}
			}
			oldNode=0;
			for(int i=0;i<edg.getcNode().size();i++)			
			{
				int nodeId = edg.getcNode().get(i);
				monolignol monomer = monoList.get(nodeId-1);
				monolignol child1 = monomer.getBondl();
				monolignol child2 = monomer.getBond2();
				monolignol child3 = monomer.getBond3();
				String nodeType =monomer.objId+monomer.getType();
				String connection1 = (child1 == null)?"":child1.objId+child1.getType();
				String connection2 = (child2 == null)?"":child2.objId+child2.getType();
				String connection3 = (child3 == null)?"":child3.objId+child3.getType();
				String bondtype ="";
								
				String[] connections = {connection1,connection2,connection3};
				String[] links = monomer.getEdgeType();
				
				for (int b=0;b<monomer.getEdgeType().length;b++)
					if (monomer.getEdgeType()!=null && monomer.getEdgeType()[b] != null)
					{
						List<String> bndL = Arrays.asList(bonds);
						bondtype = bondtype.concat(" "+(bndL.indexOf(monomer.getEdgeType()[b])+1)).concat(" ");											
					}				
				
				int lbl = monomer.lbl.getIndex();
				ArrayList<Integer> pNodeList = edg.getpNode();
				
				if (!pNodeList.contains(nodeId))
				{
					if (nodeId!= oldNode)
					{
						//writer.println(nodeId+"#\t\t"+nodeType+"\t\t"+connection1+"\t"+connection2+"\t"+connection3+"\t"+bondtype+"\t\t"+lbl);
						dataStr = dataStr.concat(nodeType+"\t("+bondtype+")"+"\t\t"+connection1+"\t"+connection2+"\t"+connection3);
						oldNode = nodeId;						
						for (int cnt=0;cnt<connections.length;cnt++)
						{
							
							if (connections[cnt]!=null && connections[cnt]!="" && links[cnt]!=null && links[cnt] !="" )
							{
							childNode cN = new childNode();
							cN.setpType(nodeType);
							cN.setcType(connections[cnt]);
							cN.setBondType(links[cnt]);
							cNodeList.add(cN);
							sop(cN);
							}
						}	
					}
				}		
			}
			if (!data.contains(dataStr)) 
			{
				writer.println(dataStr);
				data.add(dataStr);
				lignin.setPType("G1");
				lignin.setcNode(cNodeList);
				ligninList.add(lignin);
				lignin=new Lignin();
			}
			
		}
		sop(ligninList);
		writer.close();		
		return ligninList;
	}
	
	
	public Object encode(Edges ed,monolignol l1, monolignol l2)
	{		
		if (checkID(l1.getBondl(),l2.objId) && checkID(l1.getBond2(),l2.objId) && checkID(l1.getBond3(),l2.objId))
		{	
			Random rn = new Random();
			int random = rn.nextInt(3);
			//sop(random+1);
			switch (random+1)
			{
			case 3:				
					if (l1.getBondl() == null)
					{
						l1.setBondl(l2);
					}
					else
						if (l1.getBond2() == null)
						{
							l1.setBond2(l2);
						}
					else if (l1.getBond3() == null) 
					{
						l1.setBond3(l2);
					}
					break;
			case 2: 
				if (l1.getBondl() == null)
				{
					l1.setBondl(l2);
				}
				else if (l1.getBond2() == null)
				{
					l1.setBond2(l2);
				}
				break;
			case 1:
				if (l1.getBondl() == null)
				{
					l1.setBondl(l2);
				}
				break;
			}			
			if (!isFilled (l1.objId, ed))
			{
				l1 = getEdgeType(ed,l1);		
				if (l1.isEdgUpdated)
				{
					ed.getpNode().add(l1.objId);
					ed.getcNode().add(l2.objId);				
					ed.setEdWgt(l1.getEdWgt());	
					ed.setMonoL(l1);		
				}
			}		
		}
		return ed;			
	}
	
	static boolean checkID(monolignol obj, int id2)
	{	
		if (obj==null || obj.objId != id2)
		{
			return true;
		}
		return false;
	}
	
	static boolean isFilled(int id,Edges ed)
	{
		boolean isfill=false;
		int cnt=0;
		for(int pVer:ed.getpNode())
		{
			if (id == pVer)
			{
				cnt++;
			}
		}		
		if (cnt >= 3) isfill = true;
		return isfill;
	}
	
	static void sop(Object s)
	{
		System.out.println(s);
	}
	
	
	
	static String[] getList(String[] str, int[] bondper, int dp) {
		
		String[] strBonds = new String[dp];
		int[] totcnt = new int[str.length];
		// initializing the totcount[] for each bond with 1  
		for(int inc=0; inc < totcnt.length; inc++)
		{
			totcnt[inc]+=1;
		}
		// Loop to create the possible bond names (or) monomer names with the dp count
		int cnt = 0;
		boolean brkLoop = false;
		while (!brkLoop) {
			for (int j = 0; j < str.length; j++) {
				double cal = calPer(bondper[j], dp);		
				if (cal > 0 && totcnt[j] <= cal ) {						
					strBonds[cnt++] = str[j];	
					totcnt[j] = totcnt[j]+1;	
				}
			}
			brkLoop = true;
			for (int j = 0; j < str.length; j++) {
				double cal = calPer(bondper[j], dp);		
				if (cal > 0 && totcnt[j] <= cal) {	
					brkLoop = false;
				}				
			}
		}
		//Loop to add bonds at the end for chain length
		while (cnt < dp)
		{
			for (int k=0;k<str.length;k++)
	      	 {
	            if (cnt >= dp)
	            {
	              break;
	            }
	            strBonds[cnt++]=str[k];
	         }
		}				
		return strBonds;
	}
	
	static double calPer(int val, int dp) {
		if (val == 0)
			return val;
		double flrval = dp * (val / 100f);		
		return ((flrval > 0.5) ? Math.floor(flrval) : 0);
	}
	
	static monolignol getEdgeType(Edges ed, monolignol m1)
	{	
		m1.isEdgUpdated = false;
		ArrayList<String> edgArr = ed.getEdWgt();
		String c[]=new String[3];
	    List<String> newEdg = Arrays.asList(m1.getEdgeType());
	    int cnt=0;
	    for(String s: newEdg)
	    {
	    	if (s!=null)
	    	c[cnt++] = ""+s.charAt(0);
	    }	    
		for(int k=0;k<m1.getEdgeType().length;k++)
		{	 
			  for (int j=0;j<edgArr.size();j++) 
			  {
				  String oldegd = edgArr.get(j);
				  String oldC = ""+oldegd.charAt(0);
				  if (!Arrays.asList(m1.getEdgeType()).contains(edgArr.get(j)) && !Arrays.asList(c).contains(oldC))
				  {			
					  if (m1.getEdgeType()[k]==null || m1.getEdgeType()[k].isEmpty())
					  {						
						  m1.getEdgeType()[k]= edgArr.get(j);
						  edgArr.remove(edgArr.get(j));						  
						  m1.setEdWgt(edgArr);		
						  m1.isEdgUpdated = true;					 				 
						  return m1;
					  }
				  }			  
			  }			 
		  }
		return m1;				
	}
	
}
