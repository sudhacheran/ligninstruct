import javax.swing.JOptionPane;
public class GUI {
	public static void main(String[] args) {
		String dp;
		int number1;
		try {
			dp = JOptionPane.showInputDialog("Enter the dp (2-150)");		
			number1 = Integer.parseInt(dp);
			encodercopy.generateEncodedFile(number1);
			JOptionPane.showMessageDialog(null, "Datafile generated! FileName: DataSet.txt" , "Results", JOptionPane.PLAIN_MESSAGE );
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Enter value between 2 to 150 for DP \nPlease execute again" , "Error", JOptionPane.PLAIN_MESSAGE );
		}
		System.exit(0);
		
	}
}