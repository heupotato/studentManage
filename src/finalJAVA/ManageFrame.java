package finalJAVA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/*
 * ỨNG DỤNG QUẢN LÝ HỌC SINH
 * THÁI THỊ THU LOAN - 18TCLC_NHAT
 */
public class ManageFrame extends JFrame{
	public static String id = "GV10A";
	public static String dbo =null; 
	public static String pass; 
	JComboBox<String> cbbSort, cbbSearch; 
	JButton btnAdd, btnSearch, btnSort, btnDelete, btnShowAll;
	JTextField tfSearch; 
	public static JTable jtDataTable = new JTable() {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	String lbtext = null; 
	public static Connection conn; 
	public int selectedRowInd; 
	static String datacbb[] = {"Mã học sinh", "Tên học sinh", "Giới tính", "Ngày sinh", "Quê quán", "Điểm trung bình", "Hạnh kiểm"};
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		new ManageFrame(); 
	}
	public static Boolean DbConn() throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
		}
		catch (ClassNotFoundException e) {
			// TODO: handle exception
			return false;
		}
		
		String dburl = "jdbc:sqlserver://localhost:1433; databaseName=SinhVienKhoa10"; 
		String user = "test"; 
		String password = "test";
		conn = DriverManager.getConnection(dburl,user,password); 
		if (conn==null) return false;
		return true;  
	}
	
	public ManageFrame() throws SQLException 
	{
		this.setTitle("Manage Students");
		this.setSize(1000,800);
		this.setDefaultCloseOperation(3);
		this.setResizable(false); 
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout(10,10));
		if (DbConn()==true) JOptionPane.showMessageDialog(null, "Connected!");
		else JOptionPane.showMessageDialog(null, "Failed to connect! Please re-run the program");
		
		
		if (id == "GV10A") {
			dbo = "class10A"; 
			lbtext = "DANH SÁCH HỌC SINH LỚP 10A";
		}
		else if (id == "GV10B") {
			dbo = "class10B"; 
			lbtext = "DANH SÁCH HỌC SINH LỚP 10B";
		}
		else if (id == "GV10C") {
			dbo = "class10C"; 
			lbtext = "DANH SÁCH HỌC SINH LỚP 10C";
		}
		
		/*
		 * Make a label
		 */
		JLabel lbTitle = new JLabel(lbtext, JLabel.CENTER);
		lbTitle.setFont(new Font("Arial", Font.BOLD, 30));
		lbTitle.setForeground(Color.red);
		lbTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		this.add(lbTitle, BorderLayout.NORTH); 
		
		/*
		 * Make panel buttons 
		 */
		JPanel pnButtons = new JPanel(); 
		pnButtons.setLayout(new GridLayout(4,1,0,0));
		
		/*
		 * Panel search
		 */
		JPanel pnSearch = new JPanel(); 
		tfSearch = new JTextField(30); 
		btnSearch = new JButton("Search"); 
		btnShowAll = new JButton("Show all"); 
		cbbSearch = new JComboBox<String>(datacbb); 
		JLabel lbSearch = new JLabel(String.format("%50s", "Search for :")); 
		
		pnSearch.add(lbSearch); 
		pnSearch.add(tfSearch); 
		pnSearch.add(cbbSearch); 
		pnSearch.add(btnSearch); 
		pnSearch.add(btnShowAll);
		
		pnButtons.add(pnSearch);
		
		/*
		 * Panel Add
		 */
		JPanel pnAdd = new JPanel(); 
		JLabel lbAdd = new JLabel(String.format("%10s", "Add new student here :")); 
		btnAdd = new JButton("Add"); 
		
		pnAdd.add(lbAdd); 
		pnAdd.add(btnAdd); 
		
		pnButtons.add(pnAdd);
		
		/*
		 * Panel Sort
		 */
		
		JPanel pnSort = new JPanel(); 
		JLabel lbSort = new JLabel(String.format("%50s", "Sort :")); 
		
		cbbSort = new JComboBox<String>(datacbb); 
		cbbSort.setPreferredSize(new Dimension(224, 25));
		btnSort = new JButton("Sort"); 
		
		pnSort.add(lbSort);
		pnSort.add(cbbSort); 
		pnSort.add(btnSort);
		
		pnButtons.add(pnSort);
		
		/*
		 * Panel Delete
		 */
		JPanel pnDelete = new JPanel(); 
		JLabel lbDelete = new JLabel(String.format("%35s", "Delete selected student :"));
		btnDelete = new JButton("Delete"); 
		pnDelete.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		pnDelete.add(lbDelete); 
		pnDelete.add(btnDelete); 
		
		pnButtons.add(pnDelete); 
				
		this.add(pnButtons,BorderLayout.SOUTH); 
		/*
		 * Table 
		 */
		jtDataTable.setBounds(30, 40, 200, 300);
		JScrollPane sp = new JScrollPane(jtDataTable);
		add(sp, BorderLayout.CENTER);
		jtDataTable.setAutoCreateRowSorter(true);
		
		setDataTable();
		
		addListener(); 
		
		this.setVisible(true);
	}
	
	public void addListener()
	{
		//button add
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					new StudentFrame("add");
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.toString());
				}
			}
		});
		
		/*
		 * edit function 
		 * if the row is double-clicked, invoke edit function
		 */
		jtDataTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try{
					DefaultTableModel model = (DefaultTableModel)jtDataTable.getModel(); 
					selectedRowInd = jtDataTable.getSelectedRow(); 
					selectedRowInd = jtDataTable.convertRowIndexToModel(selectedRowInd); 
					
					if (e.getClickCount() == 2 && jtDataTable.getSelectedRow()!=-1)
					{
						StudentFrame.ID = "" +model.getValueAt(selectedRowInd, 0);
						StudentFrame.Name = "" + model.getValueAt(selectedRowInd, 1); 
						StudentFrame.Gender = "" + model.getValueAt(selectedRowInd, 2); 
						StudentFrame.DoB = "" + model.getValueAt(selectedRowInd, 3); 
						StudentFrame.Addr = "" + model.getValueAt(selectedRowInd, 4); 
						StudentFrame.Score = "" + model.getValueAt(selectedRowInd, 5).toString(); 
						StudentFrame.Conduct = "" + model.getValueAt(selectedRowInd, 6); 
						new StudentFrame("edit"); 
					}
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.toString());
				}
				
			}
		});
		/*
		 * button delete
		 */
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					DefaultTableModel model = (DefaultTableModel)jtDataTable.getModel(); 
					selectedRowInd = jtDataTable.getSelectedRow(); 
					selectedRowInd = jtDataTable.convertRowIndexToModel(selectedRowInd); 
					
					if (jtDataTable.getSelectedRowCount()!=1)
						throw new Exception("Pick one row to delete");
					String delID = "" + model.getValueAt(selectedRowInd, 0);
					String query = "delete from " + dbo + " where MaHS = ?"; 
					PreparedStatement prestm = conn.prepareStatement(query);
					prestm.setString(1, delID);
					prestm.execute(); 
					setDataTable();
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.toString());
				}
				
			}
		});
		/*
		 * button sort
		 */
		btnSort.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel model = new DefaultTableModel(datacbb,0); 
				jtDataTable.setModel(model);
				try {
					String temp, prop = null; 
					temp = cbbSort.getSelectedItem().toString(); 
					switch (temp) {
					case "Mã học sinh" :
						prop = "MaHS"; 
						break;
					case "Tên học sinh": 
						prop = "TenHS";
						break;
					case "Giới tính": 
						prop = "GioiTinh";
						break;
					case  "Ngày sinh": 
						prop = "DoB";
						break; 
					case "Quê quán": 
						prop = "QueQuan";
						break; 
					case "Điểm trung bình":
						prop = "DiemTB";
						break; 
					case "Hạnh kiểm":
						prop = "HanhKiem";
						break; 
					}
					String sql = "SELECT * FROM " + dbo + " order by " + prop; 
					PreparedStatement stm = conn.prepareStatement(sql);
					ResultSet rs = stm.executeQuery();
					while (rs.next())
					{
						model.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), Double.parseDouble(rs.getString(6)),rs.getString(7)});
					}
					editTable();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.toString());
				}
			}
		});
		/*
		 * button search 
		 */
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel model = new DefaultTableModel(datacbb,0); 
				jtDataTable.setModel(model);
				try {
					String temp, prop = null ; 
					temp = cbbSearch.getSelectedItem().toString(); 
					switch (temp) {
					case "Mã học sinh" :
						prop = "MaHS"; 
						break;
					case "Tên học sinh": 
						prop = "TenHS";
						break;
					case "Giới tính": 
						prop = "GioiTinh";
						break;
					case  "Ngày sinh": 
						prop = "DoB";
						break; 
					case "Quê quán": 
						prop = "QueQuan";
						break; 
					case "Điểm trung bình":
						prop = "DiemTB";
						break; 
					case "Hạnh kiểm":
						prop = "HanhKiem";
						break; 
					}
					String text = tfSearch.getText(); 
					String query= "select * from " + dbo + " where " + prop + " = ?"; 
					PreparedStatement stm = conn.prepareStatement(query); 
					stm.setString(1, text);
					ResultSet rs = stm.executeQuery();
					while (rs.next())
					{
						model.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), Double.parseDouble(rs.getString(6)),rs.getString(7)});
					}
					editTable();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex.toString());
				}
			}
		});
		
		/*
		 * Button Show all
		 * After searching for a prop, press button Show all to see the original table
		 */
		btnShowAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setDataTable();
			}
		});
	}
	public static void editTable() {
		// Chỉnh độ dài, rộng cột và header
		jtDataTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		jtDataTable.getTableHeader().setPreferredSize(new Dimension(100, 30));
		jtDataTable.setRowHeight(30);
		jtDataTable.getColumnModel().getColumn(0).setMaxWidth(120);
		jtDataTable.getColumnModel().getColumn(1).setMaxWidth(300);
		jtDataTable.getColumnModel().getColumn(2).setMaxWidth(120);
		jtDataTable.getColumnModel().getColumn(3).setMaxWidth(120);
		jtDataTable.getColumnModel().getColumn(4).setMaxWidth(120);
		jtDataTable.getColumnModel().getColumn(5).setMaxWidth(120);
		jtDataTable.getColumnModel().getColumn(6).setMaxWidth(120);;
	}
	
	public static void setDataTable()
	{
		DefaultTableModel model = new DefaultTableModel(datacbb,0); 
		jtDataTable.setModel(model);
		try {
			String sql = "SELECT * FROM " + dbo; 
			PreparedStatement stm = conn.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while (rs.next())
			{
				model.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), Double.parseDouble(rs.getString(6)),rs.getString(7)});
			}
			editTable();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}
	
	
}
/*
 * ỨNG DỤNG QUẢN LÝ HỌC SINH
 * THÁI THỊ THU LOAN - 18TCLC_NHAT
 */
