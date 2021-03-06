

import java.util.ArrayList;
import java.util.List;


public class GeneratePolymer {
    
    
    
    public static void main(String[] args) {
		
		int dp =20; // Chain lenght (No. of Monomers)
		int noOfPer = 10000;  // No. of permutations considered (from N!)

         //Inputs 
		String[] bonds = { "B04", "BB", "55", "B5", "B1", "405" }; // Bonds possbile
		int[] bondper = { 45, 15, 10, 10, 10, 10 };  // Ratio of the bonds

		String[] mono = { "G", "H", "S" }; // Monomer units
		int[] monoPer = { 45, 15, 40 }; // Ratio of the units
		
		GeneratePolymer gp = new GeneratePolymer();
		List<String> polymentString = gp.getPossiblePolymer(bonds, bondper, mono, monoPer, dp,noOfPer);
		
		String temp = "";
		for(int i=0;i<polymentString.size();i++)
		{
		    if(!temp.equals(polymentString.get(i)))
		    {
		        System.out.println(polymentString.get(i));
	    	    temp = polymentString.get(i);
		    }
		}
}
	
	
/**
 * Method returns the possible polymer chain permutations (within the specified limit parameter)
 * @param str - Bond array
 * @param bondper - Bond percentage
 * @param mono - Monomer unit array
 * @param monoPer - Monomer percentage
 * @param dp  - Chain length (no. of monomer units)
 * @param noOfPer - Limiting the permutation
 * @return
 */
public List<String> getPossiblePolymer(String[] str, int[] bondper, String[] mono, int[] monoPer, int dp, int noOfPer)
{

		String[] strBonds = getList(str, bondper, dp);
		String[] monoBonds = getList(mono, monoPer, dp);

		sop("No of Bonds=" + strBonds.length);
		sop("No of Monomers=" + monoBonds.length);

		List<String[]> bondList = new ArrayList<String[]>();

		int n = strBonds.length;
		permute(strBonds, 0, n - 1, bondList, noOfPer);

		List<String[]> monoList = new ArrayList<String[]>();
		n = monoBonds.length;
		permute(monoBonds, 0, n - 1, monoList, noOfPer);

		sop("Bond Permutations" + bondList.size());
	    sop("Monomer Permutations" + monoList.size());

		List<String> polymer = new ArrayList<String>();

		int d=0;
		//for (String[] monoArr : monoList) {
			for (String[] bondArr : bondList) {
				String[] monoArr = monoList.get(d++);
				String polymerStr = "";
				int e=0;
				for (String bndStr : bondArr) {
				    if (!(monoArr[e].equals("S") && bndStr.equals("55")) && !((e<monoList.size()-1) && monoArr[e+1].equals("S") && (bndStr.equals("B5") || bndStr.equals("55"))))
					polymerStr += monoArr[e++] + "-" + bndStr + "-";
				}
				if (!polymer.contains(polymerStr))
				polymer.add(polymerStr);
			}
		//}
		sop("No of chains created="+polymer.size());
		return polymer;
	}

	/**
	 * Method returns the possible bond names and monomer names with in the dp 
	 * @param str - Bond or monomer array
	 * @param bondper - Bond or monomer percentile list
	 * @param dp  - length of the polymer chain
	 * @return
	 */
	private String[] getList(String[] str, int[] bondper, int dp) {
		
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
					//sop(totcnt[j]+"<="+cal+"="+str[j]+" ="+cnt);
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

	/**
	 * This is a recursive method to create the permutations of bond (or) monomer array
	 * @param str -  Bond array / Monomer array
	 * @param l   - Initial index of the array to permute
	 * @param r   - end index of the array to permute
	 * @param bondList  - List to store the possible combinations of Bond / Monomers
	 * @param noOfPermutation
	 */
	private void permute(String[] str, int l, int r, List<String[]> bondList, int noOfPermutation) {
		if (bondList.size() >= noOfPermutation) {
			return;
		}

		if (l == r) {
			String[] st3 = str.clone(); // cloning the string array to add it to ArrayList
			if (!bondList.contains(st3)) {
				bondList.add(st3);
			}
		} else
			for (int i = l; i <= r; i++) {

				str = swap(str, l, i);
			//	sop ("swap1");
			//	sop (str);
				permute(str, l + 1, r, bondList, noOfPermutation); // Recursive call
				str = swap(str, l, i);
			//	sop("swap2");
			//	sop (str);
			}

	}

	/**
	 * Method to calculate the ratio
	 * @param val - Percentage value
	 * @param dp   - polymer chain length
	 * @return
	 */
	private double calPer(int val, int dp) {
		if (val == 0)
			return val;
		double flrval = dp * (val / 100f);
		//sop(""+flrval +"-"+Math.floor(flrval));
		return ((flrval > 0.5) ? Math.floor(flrval) : 0);
	}

	/**
	 * Method to swap tow values in a string array
	 * @param a
	 * @param i
	 * @param j
	 * @return
	 */
	private String[] swap(String[] a, int i, int j) {
		String temp;
		temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		return a;
	}

	private void sop(String s) {
		System.out.println(s);
	}
	private void sop(String s[]) {
	    for (String a: s)
	    {
		    System.out.print(a);
	    }
	}
}



