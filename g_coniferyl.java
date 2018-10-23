

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.atom.struct.Atom;
import com.atom.struct.AtomArray;
import com.atom.struct.Bond;
import com.atom.struct.BondArray;
import com.atom.struct.Cml;
import com.atom.struct.Molecule;
import com.atom.struct.ObjectFactory;


public class g_coniferyl {
	
	public static void main (String s[])
	{	  
	  ObjectFactory	objF = new ObjectFactory();
	  Cml cml = objF.createCml();
	  cml.setId("g");
	  cml.setFileId("fieldID");
	  Molecule mol = objF.createMolecule();
	  
	  AtomArray atmArr = createAtomList();	
	  	  	  
	  BondArray bndArr = createBondList(atmArr);
	 
	  mol.getAnyCmlOrAnyOrAny().add(atmArr);
	  mol.getAnyCmlOrAnyOrAny().add(bndArr);  	  
	  cml.getAnyCmlOrAnyOrAny().add(mol);
	
	  
	  JAXBContext jaxbContext;
	  try 
	  {
		jaxbContext = JAXBContext.newInstance(Cml.class);	
		Marshaller marshaller = jaxbContext.createMarshaller();      
		JAXBElement<Cml> cmlout = objF.createCml(cml);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);     
		marshaller.marshal(cmlout, System.out);	  
	  
	  } catch (JAXBException e) {		
		e.printStackTrace();
	  }		
	}
	
	
	public static AtomArray createAtomList()
	{
		  double[][] xyz = coordinates();
		  AtomArray atmArr = new AtomArray();		  
		  for (int i=0;i<9;i++)
		  {
			  Atom atmC = new Atom();	  
			  atmC.setId("C"+(i+1));
			  atmC.setX3(xyz[i][0]);
			  atmC.setY3(xyz[i][1]);
			  atmC.setZ3(xyz[i][2]);	
			  atmC.setElementType("C");
			  atmArr.getAnyCmlOrAnyOrAny().add(atmC);
		  }		  
		  int o1 =1;
		  int cnt=9; 	
		  while (o1<=2)
		  {
			  Atom atmO = new Atom();	  
			  atmO.setId("O"+o1++);
			  atmO.setX3(xyz[cnt][0]);
			  atmO.setY3(xyz[cnt][1]);
			  atmO.setZ3(xyz[cnt++][2]);	
			  atmO.setElementType("O");
			  atmArr.getAnyCmlOrAnyOrAny().add(atmO);		
		  }	 		  
		  
		  int h1 =1;		 
		  while (h1<=2)
		  {			  
			  Atom atmH = new Atom();	  
			  atmH.setId("H"+h1++);
			  atmH.setX3(xyz[cnt][0]);
			  atmH.setY3(xyz[cnt][1]);
			  atmH.setZ3(xyz[cnt++][2]);	
			  atmH.setElementType("H");
			  atmArr.getAnyCmlOrAnyOrAny().add(atmH);		  
		  }
		  
		  Atom atmO = new Atom();	  
		  atmO.setId("O"+o1++);
		  atmO.setX3(xyz[cnt][0]);
		  atmO.setY3(xyz[cnt][1]);
		  atmO.setZ3(xyz[cnt++][2]);	
		  atmO.setElementType("O");
		  atmArr.getAnyCmlOrAnyOrAny().add(atmO);	
		  
		  Atom atmC = new Atom();	  
		  atmC.setId("C10");
		  atmC.setX3(xyz[cnt][0]);
		  atmC.setY3(xyz[cnt][1]);
		  atmC.setZ3(xyz[cnt++][2]);	
		  atmC.setElementType("C");
		  atmArr.getAnyCmlOrAnyOrAny().add(atmC);	
		  
		  while (h1<=5)
		  {			  
			  Atom atmH = new Atom();	  
			  atmH.setId("H"+h1++);
			  atmH.setX3(xyz[cnt][0]);
			  atmH.setY3(xyz[cnt][1]);
			  atmH.setZ3(xyz[cnt++][2]);	
			  atmH.setElementType("H");
			  atmArr.getAnyCmlOrAnyOrAny().add(atmH);		  
		  }
		  
		  return atmArr;
	}
	
	
	public static BondArray createBondList(AtomArray atmArr)
	{
		BondArray bondArr = new BondArray();
		
		 int len =0;
		 while(len < 5)
		 {
			Atom atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(len++);
			Atom atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(len);
			Bond bnd = new Bond();
			if (len%2 == 0)
				bnd.setOrder("2");
			bnd.getAtomRefs2().add(atm.getId());
			bnd.getAtomRefs2().add(atm2.getId());			
			bondArr.getAnyCmlOrAnyOrAny().add(bnd);			
		 }
		 
	 	Atom atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(0);
		Atom atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(5);
		Bond bnd = new Bond();	
		bnd.setOrder("2");
		bnd.getAtomRefs2().add(atm.getId());
		bnd.getAtomRefs2().add(atm2.getId());			
		bondArr.getAnyCmlOrAnyOrAny().add(bnd);	
		
		atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(0);
		atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(6);
		bnd = new Bond();			
		bnd.getAtomRefs2().add(atm.getId());
		bnd.getAtomRefs2().add(atm2.getId());			
		bondArr.getAnyCmlOrAnyOrAny().add(bnd);	
		
		len++;
		
		while(len < 9)
		 {
			atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(len++);
			atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(len);
			bnd = new Bond();
			
			bnd.getAtomRefs2().add(atm.getId());
			bnd.getAtomRefs2().add(atm2.getId());			
			bondArr.getAnyCmlOrAnyOrAny().add(bnd);			
		 }
		
		atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(9);
		atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(11);
		bnd = new Bond();		
		bnd.getAtomRefs2().add(atm.getId());
		bnd.getAtomRefs2().add(atm2.getId());			
		bondArr.getAnyCmlOrAnyOrAny().add(bnd);	
		
		atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(3);
		atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(10);
		bnd = new Bond();		
		bnd.getAtomRefs2().add(atm.getId());
		bnd.getAtomRefs2().add(atm2.getId());			
		bondArr.getAnyCmlOrAnyOrAny().add(bnd);	
		
		atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(10);
		atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(12);
		bnd = new Bond();		
		bnd.getAtomRefs2().add(atm.getId());
		bnd.getAtomRefs2().add(atm2.getId());			
		bondArr.getAnyCmlOrAnyOrAny().add(bnd);	
		
		atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(2);
		atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(13);
		bnd = new Bond();		
		bnd.getAtomRefs2().add(atm.getId());
		bnd.getAtomRefs2().add(atm2.getId());			
		bondArr.getAnyCmlOrAnyOrAny().add(bnd);			
		
		atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(13);
		atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(14);
		bnd = new Bond();		
		bnd.getAtomRefs2().add(atm.getId());
		bnd.getAtomRefs2().add(atm2.getId());			
		bondArr.getAnyCmlOrAnyOrAny().add(bnd);	
		
		int inc=15;		
		while (inc<=17)
		{
			atm = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(14);
			atm2 = (Atom) atmArr.getAnyCmlOrAnyOrAny().get(inc++);
			bnd = new Bond();		
			bnd.getAtomRefs2().add(atm.getId());
			bnd.getAtomRefs2().add(atm2.getId());			
			bondArr.getAnyCmlOrAnyOrAny().add(bnd);	
		}
		
		return bondArr;		  
	}
	
	static double[][] coordinates()
	{
		double[][] xyz = new double[18][3];
		
		xyz[0][0]= 1.016203;
		xyz[0][1]= -0.942736;
		xyz[0][2]= 2.589872;
		
		xyz[1][0]= 2.287593;
		xyz[1][1]= -1.357486;
		xyz[1][2]= 1.184009;
		
		xyz[2][0]= 1.566159;
		xyz[2][1]= -1.619278;
		xyz[2][2]= -0.923952;
		
		xyz[3][0]= 0.568195;
		xyz[3][1]= -0.986324;
		xyz[3][2]= -1.802449;
		
		xyz[4][0]= -1.030254;
		xyz[4][1]= -0.541577;
		xyz[4][2]= -0.344812;
		
		xyz[5][0]= -0.376015;
		xyz[5][1]= -0.268576;
		xyz[5][2]= 1.547096;
		
		xyz[6][0]= 0.838514;
		xyz[6][1]= -1.050713;
		xyz[6][2]= 4.714071;
		
		xyz[7][0]= 1.060330;
		xyz[7][1]= 0.549432;
		xyz[7][2]= 5.995093;
		
		xyz[8][0]= -0.039287;
		xyz[8][1]= 1.387881;
		xyz[8][2]= 7.388143;
		
		xyz[9][0]= -0.808327;
		xyz[9][1]= 2.633214;
		xyz[9][2]= 6.541554;
		
		xyz[10][0]= 0.530981;
		xyz[10][1]= -0.857794;
		xyz[10][2]= -3.091453;
		
		xyz[11][0]= -1.509841;
		xyz[11][1]= 3.371831;
		xyz[11][2]= 5.646766;
		
		xyz[12][0]= 0.276263;
		xyz[12][1]= -0.915634;
		xyz[12][2]= -4.592073;	
		
		xyz[13][0]= 2.406466;
		xyz[13][1]= -3.093759;
		xyz[13][2]= -1.782328;	
		
		xyz[14][0]= 3.643669;
		xyz[14][1]= -3.458269;
		xyz[14][2]= -2.420801;	
		
		xyz[15][0]= 4.048370;
		xyz[15][1]= -3.281994;
		xyz[15][2]= -4.408340;
		
		xyz[16][0]= 4.819216;
		xyz[16][1]= -4.339068;
		xyz[16][2]= -2.099352;
		
		xyz[17][0]= 4.129023;
		xyz[17][1]= -4.308411;
		xyz[17][2]= -3.086534;
		
		return  xyz;
		
	}
	
	
	
	
	

}
