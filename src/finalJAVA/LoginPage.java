package finalJAVA;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * ỨNG DỤNG QUẢN LÝ HỌC SINH
 * THÁI THỊ THU LOAN - 18TCLC_NHAT
 */

public class LoginPage extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginPage(); 
	}
	
	public LoginPage()
	{
		this.setTitle("Login");
		this.setSize(400,200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.getContentPane().setLayout(new BorderLayout(10, 10));
		JLabel lbLog = new JLabel("LOGIN",JLabel.CENTER); 
		lbLog.setFont(new Font("Arial", Font.BOLD, 16));
		lbLog.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		this.add(BorderLayout.NORTH, lbLog); 
		
		JPanel pnLogin = new JPanel(); 
		pnLogin.setLayout(new GridLayout(3,1,10,10));
		
		JPanel pnUser = new JPanel(); 
		pnUser.setLayout(new GridLayout(1,2));
		JLabel lbUser = new JLabel("Username: ", JLabel.CENTER);
		JTextField tfUser = new JTextField(); 
		pnUser.add(lbUser); 
		pnUser.add(tfUser); 
		
		JPanel pnPassword = new JPanel(); 
		JLabel lbPassword = new JLabel("Password: ", JLabel.CENTER); 
		JPasswordField pfPass = new JPasswordField(); 
		pfPass.setEchoChar('*');
		pnPassword.setLayout(new GridLayout(1,2));
		pnPassword.add(lbPassword); 
		pnPassword.add(pfPass);
		pnLogin.add(pnUser); 
		pnLogin.add(pnPassword); 
		
		JPanel pnButtons = new JPanel(); 
		JButton btnLogin = new JButton("Log in"); 
		JButton btnHelp = new JButton("Help"); 
		pnButtons.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(); 
		gbc.fill = GridBagConstraints.BOTH; 
		gbc.gridx = 0; 
		gbc.gridy = 0; 
		gbc.insets = new Insets(0, 0, 0, 10); 
//		pnButtons.setLayout(new GridLayout(1, 2));
		pnButtons.add(btnLogin,gbc); 
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1; 
		gbc.gridy = 0; 
		gbc.insets = new Insets(0,10,0,0); 
		pnButtons.add(btnHelp, gbc);
		pnLogin.add(pnButtons); 
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				try{
					String strPass = new String(pfPass.getPassword()); 
					if (tfUser.getText().equals("GV10A")==true && (strPass.equals("PWGV10A")==true)) {
						ManageFrame.id = "GV10A"; 
						ManageFrame.pass = "PWGV10A"; 
						new ManageFrame(); 
					}
					else if (tfUser.getText().equals("GV10B")==true && (strPass.equals("PWGV10B")==true)) {
						ManageFrame.id = "GV10B"; 
						ManageFrame.pass = "PWGV10B"; 
						new ManageFrame();
					}
					else if (tfUser.getText().equals("GV10C")==true && (strPass.equals("PWGV10C")==true)) {
						ManageFrame.id = "GV10C"; 
						ManageFrame.pass = "PWGV10C"; 
						new ManageFrame();
					}
					else {
						JOptionPane.showMessageDialog(null, "Wrong username or password.\n Please try again");
						
					}
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.toString());
				}
				
			}
		});
		
		btnHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Class 10A - Username: GV10A - Password: PWGV10A\n"
						                           +"Class 10B - Username: GV10B - Password: PWGV10B\n"
						                           +"Class 10C - Username: GV10C - Password: PWGV10C");
			}
		});
		this.add(BorderLayout.CENTER, pnLogin);
		this.setVisible(true);
		
		
	}

}
/*
 * ỨNG DỤNG QUẢN LÝ HỌC SINH
 * THÁI THỊ THU LOAN - 18TCLC_NHAT
 */
