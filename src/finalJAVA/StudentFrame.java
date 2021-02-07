package finalJAVA;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/*
 * ỨNG DỤNG QUẢN LÝ HỌC SINH
 * THÁI THỊ THU LOAN - 18TCLC_NHAT
 */

public class StudentFrame extends JFrame{
	String type; 
	JButton btnSave; 
	JTextField tfID, tfName, tfGender, tfDoB, tfAddr, tfScore, tfConduct, tfDate; 
	public static String ID ="", Name="", Gender="", DoB="YYYY-MM-DD", Addr="", Score="", Conduct=""; 


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new StudentFrame("null"); 
	}
	
	public StudentFrame(String type) throws ParseException
	{
		this.setTitle("Student Information");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout(10,10));
		this.type = type; 
		
		JLabel label = new JLabel("Thông tin học sinh", JLabel.CENTER); 
		label.setFont(new Font("Arial", Font.BOLD, 25));
		label.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		this.add(label, BorderLayout.NORTH); 
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("",Font.PLAIN, 20));
		JPanel pnSave = new JPanel(); 
		pnSave.add(btnSave); 
		pnSave.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		this.add(pnSave,BorderLayout.SOUTH); 
		
		JPanel pninfo = new JPanel(new GridLayout(7,1,5,5)); 
		//MSSV
		JPanel row1 = new JPanel(); 
		row1.add(new JLabel("Mã số học sinh: ")); 
		row1.add(tfID = new JTextField(20)); 
		pninfo.add(row1);
		
		//Tên 
		JPanel row2 = new JPanel(); 
		row2.add(new JLabel("Họ tên học sinh: ")); 
		row2.add(tfName = new JTextField(20)); 
		pninfo.add(row2);
		
		//Giới tính
		JPanel row3 = new JPanel(); 
		row3.add(new JLabel(String.format("%24s", "Giới tính : "))); 
		row3.add(tfGender = new JTextField(20)); 
		pninfo.add(row3);
		
		//Ngày sinh
		JPanel row4 = new JPanel(); 
		row4.add(new JLabel(String.format("%22s", "Ngày sinh : ")));
		row4.add(tfDate = new JTextField(20)); 
		pninfo.add(row4);
		
		//Quê quán
		JPanel row5 = new JPanel(); 
		row5.add(new JLabel(String.format("%22s", "Quê quán : ")));
		row5.add(tfAddr = new JTextField(20));
		pninfo.add(row5); 
		
		//Điểm trung bình
		JPanel row6 = new JPanel(); 
		row6.add(new JLabel(String.format("%16s", "Điểm trung bình : ")));
		row6.add(tfScore = new JTextField(20));
		pninfo.add(row6);
		
		//Hạnh Kiểm
		JPanel row7 = new JPanel(); 
		row7.add(new JLabel(String.format("%22s", "Hạnh kiểm : ")));
		row7.add(tfConduct = new JTextField(20));
		pninfo.add(row7);
		
		
		this.add(pninfo, BorderLayout.CENTER); 
		
		setTF(); 
		
		addListener(); 
		this.setVisible(true);
	}
	public void setTF()
	{
		tfID.setText(ID);
		tfName.setText(Name);
		tfGender.setText(Gender);
		tfDate.setText(DoB);
		tfAddr.setText(Addr);
		tfScore.setText(Score);
		tfConduct.setText(Conduct);
		
	}
	public void clearData()
	{
		tfID.setText("");
		tfName.setText("");
		tfGender.setText("");
		tfDate.setText("");
		tfAddr.setText("");
		tfScore.setText("");
		tfConduct.setText("");
		
		ID = Name = Gender = Addr = Score = Conduct = ""; 
		DoB = "YYYY-MM-DD"; 
	}
	
	public void addListener()
	{
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String id = tfID.getText().trim(); 
					String name = tfName.getText().trim(); 
					String gender = tfGender.getText().trim(); 
					String date = tfDate.getText().trim(); 
					String addr = tfAddr.getText().trim(); 
					Double score = Double.parseDouble(tfScore.getText().trim()); 
					String cond = tfConduct.getText().trim();
					switch (type) {
					case "add":
						String query = "insert into " + ManageFrame.dbo +
								" values (?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement preparedStmt = ManageFrame.conn.prepareStatement(query);
						preparedStmt.setString(1, id);
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, gender);
						preparedStmt.setString(4, date);
						preparedStmt.setString(5, addr);
						preparedStmt.setDouble(6, score);
						preparedStmt.setString(7, cond);
						
						preparedStmt.execute(); 
						clearData();
						ManageFrame.setDataTable();
						dispose(); 
						break;
					
					case "edit":
						query = "update " + ManageFrame.dbo + 
							" set MaHS = ? , TenHS = ?, GioiTinh = ?, DoB = ?, QueQuan = ?, DiemTB = ?, HanhKiem = ? where MaHS = ?"; 
						preparedStmt = ManageFrame.conn.prepareStatement(query);
						preparedStmt.setString(1, id);
						preparedStmt.setString(2, name);
						preparedStmt.setString(3, gender);
						preparedStmt.setString(4, date);
						preparedStmt.setString(5, addr);
						preparedStmt.setDouble(6, score);
						preparedStmt.setString(7, cond);
						preparedStmt.setString(8, ID);
						
						preparedStmt.execute(); 
						clearData();
						ManageFrame.setDataTable();
						dispose(); 
						break; 
					}
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.toString());
				}
			}
		});
	}
}

/*
 * ỨNG DỤNG QUẢN LÝ HỌC SINH
 * THÁI THỊ THU LOAN - 18TCLC_NHAT
 */
