import java.util.List;

public class Lignin {

	
	String PType;
	List<childNode> cNode;
	public String getPType() {
		return PType;
	}
	public void setPType(String pType) {
		PType = pType;
	}
	public List<childNode> getcNode() {
		return cNode;
	}
	public void setcNode(List<childNode> cNode) {
		this.cNode = cNode;
	}
	@Override
	public String toString() {
		return "Lignin [PType=" + PType + ", cNode=" + cNode + "]";
	}
}

class childNode
{
	String pType;
	String cType;
	String bondType;
	public String getpType() {
		return pType;
	}
	public void setpType(String pType) {
		this.pType = pType;
	}
	public String getcType() {
		return cType;
	}
	public void setcType(String cType) {
		this.cType = cType;
	}
	public String getBondType() {
		return bondType;
	}
	public void setBondType(String bondType) {
		this.bondType = bondType;
	}
	@Override
	public String toString() {
		return "childNode [pType=" + pType + ", cType=" + cType + ", bondType=" + bondType + "]";
	}
	
	
	
}