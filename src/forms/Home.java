package forms;

import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Employee;
import model.Person;
import model.Leave;
//import model.PaySlip;
//import model.Payroll;
import model.Print;
import model.SingletonConnection;
import java.awt.*;
import java.awt.image.*;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.table.TableRowSorter;
import model.SQLRun;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

 
        
public class Home extends javax.swing.JFrame {
//Creating Objects
    private DateChooser chDate = new DateChooser();
    private DateChooser chDate2 = new DateChooser();
    private DateChooser chDate3 = new DateChooser();
    private DateChooser chDate4 = new DateChooser();
    private DateChooser chDate5 = new DateChooser();
    private DateChooser chDate6 = new DateChooser();
    private DateChooser chDate7 = new DateChooser();
    private DateChooser chDate8 = new DateChooser();
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private DefaultTableModel model3;
    Employee objEmployee = new Employee();
    //Payroll objPayroll = new Payroll();
    //PaySlip objPaySlip = new PaySlip();
    Leave objLeave = new Leave();
    Person objPerson = new Person();
    JFrame frame = new JFrame("Confirmation Dialog Example");
    
    PrinterJob printJob = PrinterJob.getPrinterJob();
    
    BufferedImage image1;
    BufferedImage image2;

    /**
     * Creates new form Home
     */
    public Home() {
        
        initComponents();
        addButtonGroup();
        changeIcon();
        
        chDate.setTextField(txtfld_datefiled);
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        
        chDate8.setTextField(txtfld_inclusivedate);
        chDate8.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        chDate8.setLabelCurrentDayVisible(false);
        chDate8.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        
        chDate7.setTextField(txt_pydt);
        chDate7.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate7.setLabelCurrentDayVisible(false);
        chDate7.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        
        
      
        //chDate3.setTextField(fromcutofftxt);
        chDate3.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate3.setLabelCurrentDayVisible(false);
        chDate3.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        
        //chDate4.setTextField(tocutofftxt);
        chDate4.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate4.setLabelCurrentDayVisible(false);
        chDate4.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        
        //chDate5.setTextField(deletesalarydeets_date);
        chDate5.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate5.setLabelCurrentDayVisible(false);
        chDate5.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        
        //chDate6.setTextField(print_date1);
        chDate6.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        chDate6.setLabelCurrentDayVisible(false);
        chDate6.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        
        
        
        
        
        //chDate2.setTextField(txtDate2);
        chDate2.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        chDate2.setLabelCurrentDayVisible(false);
        chDate2.setDateFormat(new SimpleDateFormat("dd-MMMM-yyyy"));
        model = (DefaultTableModel) jTable1.getModel();  
        chDate2.addActionDateChooserListener(new DateChooserAdapter(){
               @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action){  
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String dateFrom =  df.format(date.getFromDate());
                String toDate =  df.format(date.getToDate());
                //loadData("SELECT * FROM `salary_details` WHERE date BETWEEN '" + dateFrom + "' and '" +toDate+"'");
                loadData("SELECT * FROM leavelog WHERE e.empId=s.empId AND fromcutoffdate = '" + dateFrom + "' AND tocutoffdate = '" +toDate+"'");
                txt_pydt.setText(null);  
                
            }
            
        });
        try {
            SingletonConnection.getInstance().connectDatabase();
            
        }catch(Exception e){
            System.err.println(e);
        }
        chDate2.setSelectedDateBetween(new DateBetween(getLast28Day(), new Date()),true);

    }
    public boolean insertLeavelog() {
        SQLRun objSQLRun = new SQLRun();
        /*String sql = "INSERT INTO employee (empId,nic,fname,lname,dob,address,city,tel_home,tel_mobile,designation,"
                + "department,date_of_joining,gender,salType) VALUES ('" + empId + "','" + nic + "','" + fName + "','" + lName + "','" + dob + "',"
                + "'" + address + "','" + city + "','" + telHome + "','" + telMobile + "','" + designation + "',"
                + "'" + department + "','" + dateOfJoining + "','" + gender + "','" + salType + "')";*/
        String sql = "INSERT INTO leavelog (`No`, `Employee_Name`, `Position`, `Date_Filed`, `Type_Of_Loa`, `Inclusive_Date`, `no_of_days`, `Approved_days`, `Approval_By`, `HRD`) "
                + "VALUES ('" + objEmployee.getEmpId() + "','" + objEmployee.getFname() + " " + objEmployee.getLname() + "','"
                + objEmployee.getDesignation() + "','"
                + txtfld_datefiled.getText() +  "', '" + txtfld_loatype.getText() + "', '"+ txtfld_inclusivedate.getText() + "', '"+ txtfld_numofdays.getText() + "', '"+ txtfld_approveddays.getText() + "', '"+ txtfld_approvalby.getText() + "', '"+ txtfld_hrd.getText()+ "')";
        
        int inserted = objSQLRun.sqlUpdate(sql);

        if (inserted > 0) {
            JOptionPane.showMessageDialog(null, "Employee " + objEmployee.getFname() + objEmployee.getLname() + " has been added "
                    + "to the system successfully", "Success", 1);
            return true;

        } else {
            JOptionPane.showMessageDialog(null, "Error occurred while trying to add Employee "
                    + "" + objEmployee.getFname() + objEmployee.getLname() + " to the system", "ERROR", 0);
            return false;

        }

    }
    private void loadData(String sql){
        try{
            
            model.setRowCount(0);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            PreparedStatement p = SingletonConnection.getInstance().openConnection().prepareStatement(sql);
            ResultSet r = p.executeQuery();
            while(r.next()){
                String empId = String.valueOf(r.getString("No"));
                //String fname = r.getString("fname");
                //String lname = r.getString("lname");
                String empname = r.getString("Employee_Name");
                String position = r.getString("Position");
                String datefiled = r.getString("Date_Filed");
                String loatype = r.getString("Type_of_Loa");
                String incdate = r.getString("Inclusive_Date");
                String numofdays = String.valueOf(r.getInt("no_of_days"));
                String approveddays = String.valueOf(r.getInt("Approved_days"));
                String approvalby = r.getString("Approval_By");
                String hrd = r.getString("HRD");
                //String totaldays = String.valueOf(r.getInt("totalDays"));
                //String salratedaily = String.valueOf(r.getDouble("SalRateDaily"));
                //String totalDays = String.valueOf(r.getDouble("totalDays"));//
                //String salratedaily = String.valueOf(r.getDouble("SalRateDaily"));//
                //String basicSalary = String.valueOf(r.getDouble("basicSalary"));//
                //String allowanceAmount = String.valueOf(r.getDouble("Allowance"));
                //String nightDiffHours = String.valueOf(r.getDouble("nightDiffhours")); //
                //String nightDiffAmount = String.valueOf(r.getDouble("nightDiffamount"));
                //String specialHolidaydays = String.valueOf(r.getDouble("specialHolidaydays"));
                //String specialHolidayAmount = String.valueOf(r.getDouble("specialHolidayamount"));
                //String SHNDhours = String.valueOf(r.getDouble("SHNDhours"));//
                //String SHNDAmount = String.valueOf(r.getDouble("SHNDamount"));
                //String LegalHolidayDays = String.valueOf(r.getDouble("legalHolidaydays"));//
                //String LegalHolidayAmount = String.valueOf(r.getDouble("legalHolidayamount"));
                //String LHNDhours = String.valueOf(r.getDouble("LHNDhours"));//
                //String LHNDamount = String.valueOf(r.getDouble("LHNDamount"));
                //String OThours = String.valueOf(r.getDouble("Overtimehours"));//
                //String OTamount = String.valueOf(r.getDouble("Overtimeamount"));
               // String OTNDhours = String.valueOf(r.getDouble("OTnightdiffhours"));//
                //String OTNDamount = String.valueOf(r.getDouble("OTnightdiffamount"));
                //String leaveothersamount = String.valueOf(r.getDouble("LeaveOthers"));
                //String adjustmentamount = String.valueOf(r.getDouble("Adjustment"));
                //String lateundertimemins = String.valueOf(r.getDouble("lateundertimemins"));//
                //String lateundertimerate = String.valueOf(r.getDouble("lateundertimerate"));//
                //String lateundertimeamount = String.valueOf(r.getDouble("lateundertimeamount"));
                //String SSSamount = String.valueOf(r.getDouble("SSSamount"));
                //String philhealthamount = String.valueOf(r.getDouble("PhilHealthamount"));
                //String hdmfamount = String.valueOf(r.getDouble("HDMFamount"));
                //String otherdeductionamount = String.valueOf(r.getDouble("OtherDeduction"));
                //String datef = df.format(r.getDate("date"));
                //String totaldeductions = String.valueOf(r.getDouble("total_deductions"));
                //String netpay = String.valueOf(r.getBigDecimal("net_pay"));
                model.addRow(new Object[]{empId, empname, position, datefiled, loatype, incdate, numofdays, approveddays, approvalby, hrd});
            }
            r.close();
            p.close();
        } catch (Exception e){
            System.err.println(e);
        }        
    }
    
    private Date getLast28Day(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -28);
        return cal.getTime();
    }
    
     public void openFile(String file){
        try{
            File path = new File(file);
            Desktop.getDesktop().open(path);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }
     
    public void exportarExcel(JTable jt){
        try{
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(jt);
            File saveFile= jFileChooser.getSelectedFile();
            if(saveFile != null){
                saveFile = new File(saveFile.toString()+".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("payroll");
                Row rowCol = sheet.createRow(0); 
                
                for(int i=0;i<jt.getColumnCount();i++){
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(jt.getColumnName(i));
                }
                
                for(int j=0;j<jt.getRowCount();j++){
                    Row row = sheet.createRow(j+1);
                    for(int k=0;k<jt.getColumnCount();k++){
                        Cell cell = row.createCell(k);
                        if(jt.getValueAt(j, k) != null){
                            cell.setCellValue(jt.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                openFile(saveFile.toString());
                
            }else{
                JOptionPane.showMessageDialog(null, "Error generating file");
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
        catch(IOException io){
            System.out.println(io);
        }
    }
    public int findNetPayColumnIndex(JTable jt, String netPayColumnName) {
    int columnIndex = -1; // Default value if column is not found

    // Iterate over column headers to find the net pay column
    for (int i = 0; i < jt.getColumnCount(); i++) {
        String columnName = jt.getColumnName(i);
        if (columnName.equals(netPayColumnName)) {
            columnIndex = i;
            break; // Column found, exit loop
        }
    }

    return columnIndex;
}
    public int findTotalDeductionsColumnIndex(JTable jt, String totalDeductionsColumnName) {
    int columnIndex = -1; // Default value if column is not found

    // Iterate over column headers to find the total deductions column
    for (int i = 0; i < jt.getColumnCount(); i++) {
        String columnName = jt.getColumnName(i);
        if (columnName.equals(totalDeductionsColumnName)) {
            columnIndex = i;
            break; // Column found, exit loop
        }
    }
    
    

    return columnIndex;
}
    
    
public int findColumnIndexByName(JTable jt, String columnName) {
    int columnIndex = -1; // Default value if column is not found

    // Iterate over column headers to find the column by name
    for (int i = 0; i < jt.getColumnCount(); i++) {
        String columnHeaderText = jt.getColumnName(i);
        if (columnHeaderText.equals(columnName)) {
            columnIndex = i;
            break; // Column found, exit loop
        }
    }

    return columnIndex;
}

public double calculateTotalColumnSum(JTable jt, int columnIndex) {
    double totalSum = 0.0;

    // Iterate over rows to calculate the total sum of the column
    for (int j = 0; j < jt.getRowCount(); j++) {
        Object value = jt.getValueAt(j, columnIndex);
        if (value != null && value instanceof Number) {
            totalSum += ((Number) value).doubleValue();
        }
    }

    return totalSum;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroup_rd = new javax.swing.ButtonGroup();
        jDialog3 = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        leave_empfname = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        leave_emplname = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jDialog4 = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        employee_empfname1 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        employee_emplname1 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jDialog5 = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        employee_desig = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jDialog6 = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        employee_dept = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jDialog7 = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        log_empfname2 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        log_emplname2 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        intFrame_cutoff = new javax.swing.JInternalFrame();
        btn_exit_payroll1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel65 = new javax.swing.JLabel();
        txt_pydt = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        intFrame_employee_new = new javax.swing.JInternalFrame();
        btn_exit = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        panel_empDetails = new javax.swing.JPanel();
        lbl_empId = new javax.swing.JLabel();
        lbl_fname = new javax.swing.JLabel();
        lbl_lname = new javax.swing.JLabel();
        txt_empID = new javax.swing.JTextField();
        txt_fname = new javax.swing.JTextField();
        txt_lname = new javax.swing.JTextField();
        lbl_designation = new javax.swing.JLabel();
        txt_designation = new javax.swing.JTextField();
        lbl_department = new javax.swing.JLabel();
        txt_deparment = new javax.swing.JTextField();
        lbl_department2 = new javax.swing.JLabel();
        lbl_department3 = new javax.swing.JLabel();
        lbl_department4 = new javax.swing.JLabel();
        txt_vacleave = new javax.swing.JTextField();
        txt_sicleave = new javax.swing.JTextField();
        intFrame_employee_update = new javax.swing.JInternalFrame();
        btn_update = new javax.swing.JButton();
        btn_exit_update = new javax.swing.JButton();
        btn_search_update = new javax.swing.JButton();
        panel_empUpdate = new javax.swing.JPanel();
        lbl_empId1 = new javax.swing.JLabel();
        lbl_fname1 = new javax.swing.JLabel();
        lbl_lname1 = new javax.swing.JLabel();
        txt_empID_update = new javax.swing.JTextField();
        txt_fname_update = new javax.swing.JTextField();
        txt_lname_update = new javax.swing.JTextField();
        lbl_designation1 = new javax.swing.JLabel();
        txt_designation_update = new javax.swing.JTextField();
        lbl_department1 = new javax.swing.JLabel();
        txt_deparment_update = new javax.swing.JTextField();
        intFrame_employee_search = new javax.swing.JInternalFrame();
        jScrollPane_tableContainer = new javax.swing.JScrollPane();
        btn_searchEmp = new javax.swing.JButton();
        btn_searchEmp1 = new javax.swing.JButton();
        btn_searchEmp2 = new javax.swing.JButton();
        btn_searchEmp3 = new javax.swing.JButton();
        intFrame_leave = new javax.swing.JInternalFrame();
        panel_empDetails_payroll1 = new javax.swing.JPanel();
        lbl_empId_allowance1 = new javax.swing.JLabel();
        txt_empId_leave = new javax.swing.JTextField();
        lbl_fname_allowance1 = new javax.swing.JLabel();
        lbl_lname_allowance1 = new javax.swing.JLabel();
        lbl_desig_allowance1 = new javax.swing.JLabel();
        lbl_depart_allowance1 = new javax.swing.JLabel();
        txt_fname_leave = new javax.swing.JTextField();
        txt_lname_leave = new javax.swing.JTextField();
        txt_desig_leave = new javax.swing.JTextField();
        txt_depart_leave = new javax.swing.JTextField();
        panel_salAllow_payroll1 = new javax.swing.JPanel();
        lbl_travel1 = new javax.swing.JLabel();
        lbl_food1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_vl_count = new javax.swing.JTextField();
        txt_sl_count = new javax.swing.JTextField();
        txt_vl_apply = new javax.swing.JTextField();
        txt_sl_apply = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        btn_update_leave = new javax.swing.JButton();
        btn_apply_leave = new javax.swing.JButton();
        txtfld_datefiled = new javax.swing.JTextField();
        txtfld_loatype = new javax.swing.JTextField();
        txtfld_inclusivedate = new javax.swing.JTextField();
        txtfld_numofdays = new javax.swing.JTextField();
        txtfld_approveddays = new javax.swing.JTextField();
        txtfld_approvalby = new javax.swing.JTextField();
        txtfld_hrd = new javax.swing.JTextField();
        btn_exit_leave = new javax.swing.JButton();
        btn_search_leave = new javax.swing.JButton();
        btn_searchvianame_leave = new javax.swing.JButton();
        lbl_pms = new javax.swing.JLabel();
        lbl_background = new javax.swing.JLabel();
        menu_menuBar = new javax.swing.JMenuBar();
        menuBar_file = new javax.swing.JMenu();
        menuBar_file_logout = new javax.swing.JMenuItem();
        menuBar_file_exit = new javax.swing.JMenuItem();
        menuBar_employee = new javax.swing.JMenu();
        menuBar_employee_new = new javax.swing.JMenuItem();
        menuBar_employee_update = new javax.swing.JMenuItem();
        menuBar_employee_delete = new javax.swing.JMenuItem();
        menuBar_employee_search = new javax.swing.JMenuItem();
        menuBar_leave = new javax.swing.JMenu();
        menuBar_leave_apply = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        jDialog3.setLocation(new java.awt.Point(500, 250));
        jDialog3.setResizable(false);
        jDialog3.setSize(new java.awt.Dimension(500, 250));

        jLabel62.setText("Employee First Name:");

        leave_empfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leave_empfnameActionPerformed(evt);
            }
        });

        jButton6.setText("Search");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        leave_emplname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leave_emplnameActionPerformed(evt);
            }
        });

        jLabel64.setText("Employee Last Name:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel62)
                            .addComponent(jLabel64))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(leave_empfname, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(leave_emplname, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(leave_empfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(leave_emplname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap(263, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog4.setLocation(new java.awt.Point(500, 250));
        jDialog4.setResizable(false);
        jDialog4.setSize(new java.awt.Dimension(500, 250));

        jLabel63.setText("Employee First Name:");

        employee_empfname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_empfname1ActionPerformed(evt);
            }
        });

        jButton7.setText("Search");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        employee_emplname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_emplname1ActionPerformed(evt);
            }
        });

        jLabel70.setText("Employee Last Name:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63)
                            .addComponent(jLabel70))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(employee_empfname1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(employee_emplname1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(employee_empfname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(employee_emplname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addContainerGap(263, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog4Layout = new javax.swing.GroupLayout(jDialog4.getContentPane());
        jDialog4.getContentPane().setLayout(jDialog4Layout);
        jDialog4Layout.setHorizontalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog4Layout.setVerticalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog5.setLocation(new java.awt.Point(500, 250));
        jDialog5.setResizable(false);
        jDialog5.setSize(new java.awt.Dimension(500, 250));

        jLabel71.setText("Employee Designation:");

        employee_desig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_desigActionPerformed(evt);
            }
        });

        jButton8.setText("Search");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employee_desig, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(employee_desig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8)
                .addContainerGap(303, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog5Layout = new javax.swing.GroupLayout(jDialog5.getContentPane());
        jDialog5.getContentPane().setLayout(jDialog5Layout);
        jDialog5Layout.setHorizontalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog5Layout.setVerticalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog6.setLocation(new java.awt.Point(500, 250));
        jDialog6.setResizable(false);
        jDialog6.setSize(new java.awt.Dimension(500, 250));

        jLabel72.setText("Employee Department:");

        employee_dept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_deptActionPerformed(evt);
            }
        });

        jButton9.setText("Search");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employee_dept, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(employee_dept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9)
                .addContainerGap(303, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog6Layout = new javax.swing.GroupLayout(jDialog6.getContentPane());
        jDialog6.getContentPane().setLayout(jDialog6Layout);
        jDialog6Layout.setHorizontalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog6Layout.setVerticalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog7.setLocation(new java.awt.Point(500, 250));
        jDialog7.setResizable(false);
        jDialog7.setSize(new java.awt.Dimension(500, 250));

        jLabel73.setText("Employee First Name:");

        log_empfname2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                log_empfname2ActionPerformed(evt);
            }
        });

        jButton12.setText("Search");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        log_emplname2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                log_emplname2ActionPerformed(evt);
            }
        });

        jLabel74.setText("Employee Last Name:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton12)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel73)
                            .addComponent(jLabel74))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(log_empfname2, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(log_emplname2, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(log_empfname2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(log_emplname2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton12)
                .addContainerGap(263, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog7Layout = new javax.swing.GroupLayout(jDialog7.getContentPane());
        jDialog7.getContentPane().setLayout(jDialog7Layout);
        jDialog7Layout.setHorizontalGroup(
            jDialog7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog7Layout.setVerticalGroup(
            jDialog7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Payroll Management System | Home");
        setLocation(new java.awt.Point(100, 0));
        setMinimumSize(new java.awt.Dimension(1200, 700));
        setName("Home"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(null);

        intFrame_cutoff.setClosable(true);
        intFrame_cutoff.setVisible(false);

        btn_exit_payroll1.setText("Exit");
        btn_exit_payroll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exit_payroll1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Employee Name", "Position", "Date Filed", "Type of Loa", "Inclusive Date", "No. of Days", "Approved Days", "Approval By", "HRD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(0);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(130);
        }

        jButton3.setText("Search by Employee ID");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Display All");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel65.setText("Search by Date Filed:");

        jButton10.setText("Export");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Search");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton5.setText("Search by Employee Name");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout intFrame_cutoffLayout = new javax.swing.GroupLayout(intFrame_cutoff.getContentPane());
        intFrame_cutoff.getContentPane().setLayout(intFrame_cutoffLayout);
        intFrame_cutoffLayout.setHorizontalGroup(
            intFrame_cutoffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                .addGroup(intFrame_cutoffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                        .addGroup(intFrame_cutoffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(intFrame_cutoffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jLabel65)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_pydt, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton11))
                                    .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4))))
                            .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btn_exit_payroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(42, 42, 42))
        );
        intFrame_cutoffLayout.setVerticalGroup(
            intFrame_cutoffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(intFrame_cutoffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_pydt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(jButton11))
                .addGap(17, 17, 17)
                .addGroup(intFrame_cutoffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton5)
                    .addComponent(jButton4))
                .addGroup(intFrame_cutoffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(intFrame_cutoffLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(127, 127, 127)
                .addComponent(btn_exit_payroll1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(intFrame_cutoff);
        intFrame_cutoff.setBounds(0, 0, 1190, 680);

        intFrame_employee_new.setClosable(true);
        intFrame_employee_new.setTitle("Enter Employee Details");
        intFrame_employee_new.setToolTipText("");
        intFrame_employee_new.setMaximumSize(new java.awt.Dimension(800, 500));
        intFrame_employee_new.setMinimumSize(new java.awt.Dimension(800, 500));
        intFrame_employee_new.setPreferredSize(new java.awt.Dimension(800, 500));
        intFrame_employee_new.setVisible(false);
        intFrame_employee_new.getContentPane().setLayout(null);

        btn_exit.setText("Exit");
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });
        intFrame_employee_new.getContentPane().add(btn_exit);
        btn_exit.setBounds(400, 350, 120, 23);

        btn_add.setText("Add Employee");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        intFrame_employee_new.getContentPane().add(btn_add);
        btn_add.setBounds(270, 350, 120, 20);

        panel_empDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Employee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(255, 0, 0))); // NOI18N

        lbl_empId.setText("Employee ID*");

        lbl_fname.setText("First Name*");

        lbl_lname.setText("Last Name*");

        txt_empID.setToolTipText("Eg: 0001");

        lbl_designation.setText("Position*");

        lbl_department.setText("Department*");

        lbl_department2.setText("Balance");

        lbl_department3.setText("Vacation Leave");

        lbl_department4.setText("Sick Leave");

        javax.swing.GroupLayout panel_empDetailsLayout = new javax.swing.GroupLayout(panel_empDetails);
        panel_empDetails.setLayout(panel_empDetailsLayout);
        panel_empDetailsLayout.setHorizontalGroup(
            panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_empDetailsLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_empId)
                    .addComponent(lbl_fname)
                    .addComponent(lbl_lname)
                    .addComponent(lbl_designation)
                    .addComponent(lbl_department)
                    .addComponent(lbl_department2)
                    .addGroup(panel_empDetailsLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_department4)
                            .addComponent(lbl_department3))))
                .addGap(83, 83, 83)
                .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_empDetailsLayout.createSequentialGroup()
                        .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_lname, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_fname, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_empID, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_designation, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(28, Short.MAX_VALUE))
                    .addGroup(panel_empDetailsLayout.createSequentialGroup()
                        .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_deparment, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_vacleave, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sicleave, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_empDetailsLayout.setVerticalGroup(
            panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_empDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_empId)
                    .addComponent(txt_empID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_fname)
                    .addComponent(txt_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_lname)
                    .addComponent(txt_lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_designation)
                    .addComponent(txt_designation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_empDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_empDetailsLayout.createSequentialGroup()
                        .addComponent(lbl_department)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_department2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_department3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_department4))
                    .addGroup(panel_empDetailsLayout.createSequentialGroup()
                        .addComponent(txt_deparment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_vacleave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_sicleave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        intFrame_employee_new.getContentPane().add(panel_empDetails);
        panel_empDetails.setBounds(60, 40, 470, 300);

        getContentPane().add(intFrame_employee_new);
        intFrame_employee_new.setBounds(0, 0, 1200, 680);
        try {
            intFrame_employee_new.setMaximum(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        intFrame_employee_update.setClosable(true);
        intFrame_employee_update.setTitle("Update Employee Details");
        intFrame_employee_update.setMaximumSize(new java.awt.Dimension(800, 500));
        intFrame_employee_update.setMinimumSize(new java.awt.Dimension(800, 500));
        intFrame_employee_update.setPreferredSize(new java.awt.Dimension(800, 500));
        intFrame_employee_update.setVisible(false);
        intFrame_employee_update.getContentPane().setLayout(null);

        btn_update.setText("Update Employee");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });
        intFrame_employee_update.getContentPane().add(btn_update);
        btn_update.setBounds(200, 420, 150, 23);

        btn_exit_update.setText("Exit");
        btn_exit_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exit_updateActionPerformed(evt);
            }
        });
        intFrame_employee_update.getContentPane().add(btn_exit_update);
        btn_exit_update.setBounds(360, 420, 150, 23);

        btn_search_update.setText("Search");
        btn_search_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_updateActionPerformed(evt);
            }
        });
        intFrame_employee_update.getContentPane().add(btn_search_update);
        btn_search_update.setBounds(40, 420, 150, 23);

        panel_empUpdate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Update Employee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(255, 0, 0))); // NOI18N

        lbl_empId1.setText("Employee ID");

        lbl_fname1.setText("First Name*");

        lbl_lname1.setText("Last Name*");

        txt_empID_update.setFocusable(false);

        lbl_designation1.setText("Designation*");

        lbl_department1.setText("Department*");

        javax.swing.GroupLayout panel_empUpdateLayout = new javax.swing.GroupLayout(panel_empUpdate);
        panel_empUpdate.setLayout(panel_empUpdateLayout);
        panel_empUpdateLayout.setHorizontalGroup(
            panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_empUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_fname1)
                    .addComponent(lbl_lname1)
                    .addComponent(lbl_empId1)
                    .addComponent(lbl_designation1)
                    .addComponent(lbl_department1))
                .addGap(104, 104, 104)
                .addGroup(panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_empUpdateLayout.createSequentialGroup()
                        .addGroup(panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_lname_update, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_fname_update, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_empID_update, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(panel_empUpdateLayout.createSequentialGroup()
                        .addGroup(panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_deparment_update, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_designation_update, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_empUpdateLayout.setVerticalGroup(
            panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_empUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_empID_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_empId1))
                .addGap(28, 28, 28)
                .addGroup(panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_fname_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_fname1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_lname_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_lname1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_designation_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_designation1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_empUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_deparment_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_department1))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        intFrame_employee_update.getContentPane().add(panel_empUpdate);
        panel_empUpdate.setBounds(30, 10, 470, 290);

        getContentPane().add(intFrame_employee_update);
        intFrame_employee_update.setBounds(0, 0, 1200, 680);
        try {
            intFrame_employee_update.setMaximum(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        intFrame_employee_search.setClosable(true);
        intFrame_employee_search.setTitle("Search Employee Details");
        intFrame_employee_search.setMaximumSize(new java.awt.Dimension(800, 500));
        intFrame_employee_search.setMinimumSize(new java.awt.Dimension(800, 500));
        intFrame_employee_search.setPreferredSize(new java.awt.Dimension(800, 500));
        intFrame_employee_search.setVisible(false);

        jScrollPane_tableContainer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(255, 0, 20))); // NOI18N

        btn_searchEmp.setText("Search Employee");
        btn_searchEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchEmpActionPerformed(evt);
            }
        });

        btn_searchEmp1.setText("Search Employee Name");
        btn_searchEmp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchEmp1ActionPerformed(evt);
            }
        });

        btn_searchEmp2.setText("Search Designation");
        btn_searchEmp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchEmp2ActionPerformed(evt);
            }
        });

        btn_searchEmp3.setText("Search Department");
        btn_searchEmp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchEmp3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout intFrame_employee_searchLayout = new javax.swing.GroupLayout(intFrame_employee_search.getContentPane());
        intFrame_employee_search.getContentPane().setLayout(intFrame_employee_searchLayout);
        intFrame_employee_searchLayout.setHorizontalGroup(
            intFrame_employee_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(intFrame_employee_searchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(intFrame_employee_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane_tableContainer)
                    .addGroup(intFrame_employee_searchLayout.createSequentialGroup()
                        .addComponent(btn_searchEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_searchEmp1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_searchEmp2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_searchEmp3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        intFrame_employee_searchLayout.setVerticalGroup(
            intFrame_employee_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, intFrame_employee_searchLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(intFrame_employee_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_searchEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_searchEmp1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_searchEmp2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_searchEmp3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane_tableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(198, 198, 198))
        );

        getContentPane().add(intFrame_employee_search);
        intFrame_employee_search.setBounds(0, 0, 1200, 680);
        try {
            intFrame_employee_search.setMaximum(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        intFrame_leave.setClosable(true);
        intFrame_leave.setTitle("Leave Status | Apply Leave");
        intFrame_leave.setMaximumSize(new java.awt.Dimension(800, 500));
        intFrame_leave.setMinimumSize(new java.awt.Dimension(800, 500));
        intFrame_leave.setPreferredSize(new java.awt.Dimension(800, 500));
        intFrame_leave.setVisible(false);

        panel_empDetails_payroll1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(255, 0, 0))); // NOI18N

        lbl_empId_allowance1.setText("Employee No.");

        txt_empId_leave.setFocusable(false);

        lbl_fname_allowance1.setText("First Name");

        lbl_lname_allowance1.setText("Last Name");

        lbl_desig_allowance1.setText("Position");

        lbl_depart_allowance1.setText("Department");

        txt_fname_leave.setFocusable(false);

        txt_lname_leave.setFocusable(false);

        txt_desig_leave.setFocusable(false);

        txt_depart_leave.setFocusable(false);

        javax.swing.GroupLayout panel_empDetails_payroll1Layout = new javax.swing.GroupLayout(panel_empDetails_payroll1);
        panel_empDetails_payroll1.setLayout(panel_empDetails_payroll1Layout);
        panel_empDetails_payroll1Layout.setHorizontalGroup(
            panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_empDetails_payroll1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_empDetails_payroll1Layout.createSequentialGroup()
                        .addComponent(lbl_fname_allowance1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_fname_leave, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_empDetails_payroll1Layout.createSequentialGroup()
                        .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_empId_allowance1)
                            .addComponent(lbl_lname_allowance1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_empId_leave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_lname_leave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_depart_allowance1)
                    .addComponent(lbl_desig_allowance1))
                .addGap(60, 60, 60)
                .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_desig_leave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_depart_leave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );
        panel_empDetails_payroll1Layout.setVerticalGroup(
            panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_empDetails_payroll1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_empDetails_payroll1Layout.createSequentialGroup()
                        .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_empId_leave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_empId_allowance1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_fname_leave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_fname_allowance1)))
                    .addGroup(panel_empDetails_payroll1Layout.createSequentialGroup()
                        .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_desig_leave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_desig_allowance1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_depart_leave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_depart_allowance1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_empDetails_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_lname_leave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_lname_allowance1))
                .addGap(33, 33, 33))
        );

        panel_salAllow_payroll1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Leave Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18), new java.awt.Color(255, 0, 0))); // NOI18N

        lbl_travel1.setText("Vacation Leave");

        lbl_food1.setText("Sick Leave");

        jLabel6.setText("Available Leave Count");

        jLabel8.setText("Apply Leave");

        txt_vl_count.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_vl_countActionPerformed(evt);
            }
        });
        txt_vl_count.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_vl_countKeyTyped(evt);
            }
        });

        txt_sl_count.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sl_countActionPerformed(evt);
            }
        });

        txt_vl_apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_vl_applyActionPerformed(evt);
            }
        });
        txt_vl_apply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_vl_applyKeyTyped(evt);
            }
        });

        txt_sl_apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sl_applyActionPerformed(evt);
            }
        });

        jLabel12.setText("Date Filed");

        jLabel21.setText("Type of Loa");

        jLabel46.setText("Inclusive Date");

        jLabel66.setText("No. of Days");

        jLabel67.setText("Approved Days");

        jLabel68.setText("Approval By:");

        jLabel69.setText("HRD");

        btn_update_leave.setText("Update Leave");
        btn_update_leave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_leaveActionPerformed(evt);
            }
        });

        btn_apply_leave.setText("Apply Leave");
        btn_apply_leave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_apply_leaveActionPerformed(evt);
            }
        });

        txtfld_datefiled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfld_datefiledActionPerformed(evt);
            }
        });

        txtfld_loatype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfld_loatypeActionPerformed(evt);
            }
        });

        txtfld_inclusivedate.setText(" ");

        javax.swing.GroupLayout panel_salAllow_payroll1Layout = new javax.swing.GroupLayout(panel_salAllow_payroll1);
        panel_salAllow_payroll1.setLayout(panel_salAllow_payroll1Layout);
        panel_salAllow_payroll1Layout.setHorizontalGroup(
            panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_salAllow_payroll1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel69)
                    .addComponent(jLabel68)
                    .addComponent(jLabel67)
                    .addGroup(panel_salAllow_payroll1Layout.createSequentialGroup()
                        .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_salAllow_payroll1Layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(panel_salAllow_payroll1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(btn_update_leave))))
                            .addGroup(panel_salAllow_payroll1Layout.createSequentialGroup()
                                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_travel1)
                                    .addComponent(lbl_food1))
                                .addGap(54, 54, 54)
                                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_sl_count, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_vl_count, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59)
                                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(panel_salAllow_payroll1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txt_sl_apply, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_vl_apply, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(58, 58, 58)
                        .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel21)
                            .addComponent(jLabel46)
                            .addComponent(jLabel66))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtfld_hrd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                        .addComponent(txtfld_approvalby, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(panel_salAllow_payroll1Layout.createSequentialGroup()
                        .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtfld_datefiled, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtfld_loatype, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfld_approveddays, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfld_numofdays, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfld_inclusivedate, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(40, 40, 40)
                        .addComponent(btn_apply_leave, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panel_salAllow_payroll1Layout.setVerticalGroup(
            panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_salAllow_payroll1Layout.createSequentialGroup()
                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12)
                    .addComponent(txtfld_datefiled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_salAllow_payroll1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_travel1)
                            .addComponent(txt_vl_count, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_vl_apply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_food1)
                            .addComponent(txt_sl_count, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sl_apply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46)
                            .addComponent(txtfld_inclusivedate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_apply_leave, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_update_leave)
                            .addComponent(jLabel66)
                            .addComponent(txtfld_numofdays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_salAllow_payroll1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtfld_loatype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(txtfld_approveddays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(txtfld_approvalby, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_salAllow_payroll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(txtfld_hrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        btn_exit_leave.setText("Exit");
        btn_exit_leave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exit_leaveActionPerformed(evt);
            }
        });

        btn_search_leave.setText("Search Employee ID");
        btn_search_leave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search_leaveActionPerformed(evt);
            }
        });

        btn_searchvianame_leave.setText("Search Employee Name");
        btn_searchvianame_leave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchvianame_leaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout intFrame_leaveLayout = new javax.swing.GroupLayout(intFrame_leave.getContentPane());
        intFrame_leave.getContentPane().setLayout(intFrame_leaveLayout);
        intFrame_leaveLayout.setHorizontalGroup(
            intFrame_leaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(intFrame_leaveLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(intFrame_leaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_salAllow_payroll1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_empDetails_payroll1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(intFrame_leaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_searchvianame_leave, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(btn_search_leave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_exit_leave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        intFrame_leaveLayout.setVerticalGroup(
            intFrame_leaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(intFrame_leaveLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(intFrame_leaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(intFrame_leaveLayout.createSequentialGroup()
                        .addComponent(btn_search_leave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_searchvianame_leave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_exit_leave))
                    .addComponent(panel_empDetails_payroll1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(panel_salAllow_payroll1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(intFrame_leave);
        intFrame_leave.setBounds(0, 0, 1200, 680);

        lbl_pms.setFont(new java.awt.Font("URW Palladio L", 1, 48)); // NOI18N
        lbl_pms.setForeground(new java.awt.Color(36, 121, 158));
        lbl_pms.setText("Leave Management System");
        getContentPane().add(lbl_pms);
        lbl_pms.setBounds(280, 80, 710, 60);

        lbl_background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forms/images/dotaero1.jpg"))); // NOI18N
        getContentPane().add(lbl_background);
        lbl_background.setBounds(0, 10, 1200, 700);

        menu_menuBar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        menuBar_file.setText("   File   ");
        menuBar_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBar_fileActionPerformed(evt);
            }
        });

        menuBar_file_logout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuBar_file_logout.setText("Log Out");
        menuBar_file_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBar_file_logoutActionPerformed(evt);
            }
        });
        menuBar_file.add(menuBar_file_logout);

        menuBar_file_exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuBar_file_exit.setText("Exit");
        menuBar_file_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBar_file_exitActionPerformed(evt);
            }
        });
        menuBar_file.add(menuBar_file_exit);

        menu_menuBar.add(menuBar_file);

        menuBar_employee.setText("   Employee   ");
        menuBar_employee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBar_employeeActionPerformed(evt);
            }
        });

        menuBar_employee_new.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuBar_employee_new.setText("New Employee");
        menuBar_employee_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBar_employee_newActionPerformed(evt);
            }
        });
        menuBar_employee.add(menuBar_employee_new);

        menuBar_employee_update.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuBar_employee_update.setText("Update Employee");
        menuBar_employee_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBar_employee_updateActionPerformed(evt);
            }
        });
        menuBar_employee.add(menuBar_employee_update);

        menuBar_employee_delete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuBar_employee_delete.setText("Delete Employee");
        menuBar_employee_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBar_employee_deleteActionPerformed(evt);
            }
        });
        menuBar_employee.add(menuBar_employee_delete);

        menuBar_employee_search.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuBar_employee_search.setText("Search Employee");
        menuBar_employee_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBar_employee_searchActionPerformed(evt);
            }
        });
        menuBar_employee.add(menuBar_employee_search);

        menu_menuBar.add(menuBar_employee);

        menuBar_leave.setText("Leave");

        menuBar_leave_apply.setText("Apply Leave");
        menuBar_leave_apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBar_leave_applyActionPerformed(evt);
            }
        });
        menuBar_leave.add(menuBar_leave_apply);

        jMenuItem1.setText("Leave Log");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuBar_leave.add(jMenuItem1);

        menu_menuBar.add(menuBar_leave);

        setJMenuBar(menu_menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents
//Change Title bar Icon of the Form

    public void changeIcon() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
    }

//Disable menu on form initialization    
    public void disableMenu() {

        menuBar_employee.setEnabled(false);
        //menuBar_payroll.setEnabled(false);
        //menuBar_paySlip.setEnabled(false);

    }

//Add radio buttons to a group
    public void addButtonGroup() {

       //btnGroup_rd.add(rd_male);
       //btnGroup_rd.add(rd_female);

    }

//clear new employee form    
    public void clearEmployeeNew() {
        txt_empID.setText(null);
        //txt_nic.setText(null);
        txt_fname.setText(null);
        txt_lname.setText(null);
        //txt_address.setText(null);
        //txt_city.setText(null);
        //txt_dob.setText(null);
        //txt_dateJoin.setText(null);
        txt_deparment.setText(null);
        txt_designation.setText(null);
        //txt_telHome.setText(null);
        //txt_telMobile.setText(null);
    }

//clear update employee form    
    public void clearEmployeeUpdate() {
        txt_empID_update.setText(null);
        //txt_nic_update.setText(null);
        txt_fname_update.setText(null);
        txt_lname_update.setText(null);
        //txt_address_update.setText(null);
        //txt_city_update.setText(null);
        //txt_dob_update.setText(null);
        //txt_dateJoin_update.setText(null);
        txt_deparment_update.setText(null);
        txt_designation_update.setText(null);
        //txt_telHome_update.setText(null);
        //txt_telMobile_update.setText(null);

    }



//clear leave form    
    public void clearLeave() {
        txt_empId_leave.setText(null);
        txt_fname_leave.setText(null);
        txt_lname_leave.setText(null);
        txt_desig_leave.setText(null);
        txt_depart_leave.setText(null);
        txt_vl_count.setText(null);
        txt_vl_apply.setText(null);
        txt_sl_apply.setText(null);
        txt_sl_count.setText(null);

    }

//validate new employee fields    
    public boolean validateEmployeeNew() {

        if (txt_empID.getText().isEmpty()
                //|| txt_nic.getText().isEmpty()
                || txt_fname.getText().isEmpty()
                || txt_lname.getText().isEmpty()
           //     || txt_dob.getText().isEmpty()
                || txt_designation.getText().isEmpty()
                || txt_deparment.getText().isEmpty())
         //       || txt_dateJoin.getText().isEmpty()) 
        {
            return false;
        } else {
            return true;
        }

    }

//validate update employee form fields    
    public boolean validateEmployeeUpdate() {

        if (txt_empID_update.getText().isEmpty()
              //  || txt_nic_update.getText().isEmpty()
                || txt_fname_update.getText().isEmpty()
                || txt_lname_update.getText().isEmpty()
              //  || txt_dob_update.getText().isEmpty()
                || txt_designation_update.getText().isEmpty()
                || txt_deparment_update.getText().isEmpty()
              //  || txt_dateJoin_update.getText().isEmpty()
                ) {
            return false;
        } else {
            return true;
        }

    }

//hide frames on opening a new form    
    public void hideFrames() {

        intFrame_employee_new.setVisible(false);
        intFrame_employee_update.setVisible(false);
        //intFrame_payroll.setVisible(false);
        //intFrame_print.setVisible(false);
        intFrame_employee_search.setVisible(false);
        intFrame_leave.setVisible(false);

    }

//dialog box to get employee id    
    public String getEmpId() {

        return JOptionPane.showInputDialog("Enter Employee ID");

    }

    private void menuBar_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBar_fileActionPerformed

    }//GEN-LAST:event_menuBar_fileActionPerformed

    private void menuBar_file_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBar_file_exitActionPerformed

        System.exit(0);
    }//GEN-LAST:event_menuBar_file_exitActionPerformed

    private void menuBar_employee_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBar_employee_newActionPerformed

        hideFrames();
        intFrame_employee_new.setVisible(true);
        txt_empID.setText(objEmployee.setEmpIdField());
    }//GEN-LAST:event_menuBar_employee_newActionPerformed

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed

        intFrame_employee_new.setVisible(false);
    }//GEN-LAST:event_btn_exitActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed

        if (!validateEmployeeNew()) {

            JOptionPane.showMessageDialog(null, "Fields Marked with * are Mandatory", "ERROR", 0);

        } else {

            objEmployee.setEmpId(txt_empID.getText());
            //objEmployee.setNic(txt_nic.getText());
            objEmployee.setFname(txt_fname.getText());
            objEmployee.setLname(txt_lname.getText());
            //objEmployee.setDob(txt_dob.getText());
            //objEmployee.setAddress(txt_address.getText());
           // objEmployee.setCity(txt_city.getText());
           // objEmployee.setTel_home(txt_telHome.getText());
           // objEmployee.setTel_mobile(txt_telMobile.getText());
            objEmployee.setDesignation(txt_designation.getText());
            objEmployee.setDepartment(txt_deparment.getText());
            objEmployee.setVacationleave(Double.parseDouble(txt_vacleave.getText()));
            objEmployee.setSickleave(Double.parseDouble(txt_sicleave.getText()));
           // objEmployee.setDateOfJoining(txt_dateJoin.getText());
           // objEmployee.setSalType(combo_salType.getSelectedItem().toString());

          /* if (rd_male.isSelected()) {

                objEmployee.setGender(rd_male.getText());

            } else if (rd_female.isSelected()) {

                objEmployee.setGender(rd_female.getText());

            }
            */
            if (objEmployee.insertEmployee()) {
                clearEmployeeNew();
            }

        }


    }//GEN-LAST:event_btn_addActionPerformed

    private void menuBar_file_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBar_file_logoutActionPerformed

        Login loginForm = new Login();
        loginForm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuBar_file_logoutActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed

        if (!validateEmployeeUpdate()) {

            JOptionPane.showMessageDialog(null, "Fields Marked with * are Mandatory", "ERROR", 0);

        } else {

            objEmployee.setEmpId(txt_empID_update.getText());
            //objEmployee.setNic(txt_nic_update.getText());
            objEmployee.setFname(txt_fname_update.getText());
            objEmployee.setLname(txt_lname_update.getText());
            //objEmployee.setDob(txt_dob_update.getText());
           // objEmployee.setAddress(txt_address_update.getText());
           // objEmployee.setCity(txt_city_update.getText());
           // objEmployee.setTel_home(txt_telHome_update.getText());
          //  objEmployee.setTel_mobile(txt_telMobile_update.getText());
            objEmployee.setDesignation(txt_designation_update.getText());
            objEmployee.setDepartment(txt_deparment_update.getText());
           // objEmployee.setDateOfJoining(txt_dateJoin_update.getText());
           // objEmployee.setSalType(combo_salType_update.getSelectedItem().toString());
           
           
           //objLeave.setVacationleave(Double.parseDouble(txt_vl_count.getText()));
           //sobjLeave.setSickleave(Double.parseDouble(txt_sl_count.getText()));

            if (objEmployee.updateEmployee()) {
                clearEmployeeUpdate();
            }
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_exit_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exit_updateActionPerformed

        intFrame_employee_update.setVisible(false);
    }//GEN-LAST:event_btn_exit_updateActionPerformed

    private void menuBar_employee_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBar_employee_updateActionPerformed

        hideFrames();
        intFrame_employee_update.setVisible(true);
    }//GEN-LAST:event_menuBar_employee_updateActionPerformed

    private void menuBar_employeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBar_employeeActionPerformed

    }//GEN-LAST:event_menuBar_employeeActionPerformed

    private void menuBar_employee_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBar_employee_deleteActionPerformed

        hideFrames();
        objEmployee.deleteEmployee(getEmpId());
    }//GEN-LAST:event_menuBar_employee_deleteActionPerformed

    private void btn_search_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_updateActionPerformed

        if (objEmployee.getEmployeeDetails(getEmpId())) {

            txt_empID_update.setText(String.valueOf(objEmployee.getEmpId()));
            //txt_nic_update.setText(objEmployee.getNic());
            txt_fname_update.setText(objEmployee.getFname());
            txt_lname_update.setText(objEmployee.getLname());
            //txt_dob_update.setText(objEmployee.getDob());
            //txt_address_update.setText(objEmployee.getAddress());
            //txt_city_update.setText(objEmployee.getCity());
            txt_designation_update.setText(objEmployee.getDesignation());
            txt_deparment_update.setText(objEmployee.getDepartment());
            //txt_telHome_update.setText(objEmployee.getTelHome());
           // txt_telMobile_update.setText(objEmployee.getTelMobile());
            //txt_dateJoin_update.setText(objEmployee.getDateOfJoining());
            //combo_salType_update.setSelectedItem(objEmployee.getSalType());
            //txt_vl_count1.setText(String.valueOf(objLeave.getVacationleave()));
            //txt_sl_count1.setText(String.valueOf(objLeave.getSickleave()));
            

        }
    }//GEN-LAST:event_btn_search_updateActionPerformed

    private void menuBar_employee_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBar_employee_searchActionPerformed
//populate table with employee details        
        hideFrames();
        intFrame_employee_search.setVisible(true);
        String sql = "SELECT * FROM employee";
        JTable empDetails = new JTable(objEmployee.getAllEmployeeDetails(objEmployee.getAllEmployeeDetails(sql)), objEmployee.getColumnNames(objEmployee.getAllEmployeeDetails(sql)));
        jScrollPane_tableContainer.setViewportView(empDetails);
    }//GEN-LAST:event_menuBar_employee_searchActionPerformed

    private void btn_searchEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchEmpActionPerformed

        String empId = getEmpId();
        String sql = "SELECT * FROM employee WHERE empId='" + empId + "'";
        JTable empDetails = new JTable(objEmployee.getAllEmployeeDetails(objEmployee.getAllEmployeeDetails(sql)), objEmployee.getColumnNames(objEmployee.getAllEmployeeDetails(sql)));
        
        jScrollPane_tableContainer.setViewportView(empDetails);
    }//GEN-LAST:event_btn_searchEmpActionPerformed

    private void txt_vl_countActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_vl_countActionPerformed

    }//GEN-LAST:event_txt_vl_countActionPerformed

    private void txt_vl_countKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_vl_countKeyTyped

    }//GEN-LAST:event_txt_vl_countKeyTyped

    private void txt_sl_countActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sl_countActionPerformed

    }//GEN-LAST:event_txt_sl_countActionPerformed

    private void btn_search_leaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search_leaveActionPerformed

        if (objLeave.getLeaveDetails(getEmpId())) {
            
            txt_empId_leave.setText(objLeave.objEmployee.getEmpId());
            txt_fname_leave.setText(objLeave.objEmployee.getFname());
            txt_lname_leave.setText(objLeave.objEmployee.getLname());
            txt_desig_leave.setText(objLeave.objEmployee.getDesignation());
            txt_depart_leave.setText(objLeave.objEmployee.getDepartment());
            txt_vl_count.setText(String.valueOf(objLeave.getVacationleave()));
            txt_sl_count.setText(String.valueOf(objLeave.getSickleave()));
            objEmployee.setEmpId(txt_empId_leave.getText());
            objEmployee.setFname(txt_fname_leave.getText());
            objEmployee.setLname(txt_lname_leave.getText());
            objEmployee.setDesignation(txt_desig_leave.getText());
        }
    }//GEN-LAST:event_btn_search_leaveActionPerformed

    private void btn_apply_leaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_apply_leaveActionPerformed

        objLeave.objEmployee.setEmpId(txt_empId_leave.getText());
        objLeave.setVacationleave(Double.parseDouble(txt_vl_count.getText()) - Double.parseDouble(txt_vl_apply.getText()));
        objLeave.setSickleave(Double.parseDouble(txt_sl_count.getText()) - Double.parseDouble(txt_sl_apply.getText()));
        if (objLeave.applyLeave() && insertLeavelog()) {
            clearLeave();
        }
    }//GEN-LAST:event_btn_apply_leaveActionPerformed

    private void btn_exit_leaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exit_leaveActionPerformed

        intFrame_leave.setVisible(false);
    }//GEN-LAST:event_btn_exit_leaveActionPerformed

    private void menuBar_leave_applyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBar_leave_applyActionPerformed

        hideFrames();
        intFrame_leave.setVisible(true);
    }//GEN-LAST:event_menuBar_leave_applyActionPerformed

    private void txt_vl_applyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_vl_applyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_vl_applyActionPerformed

    private void txt_vl_applyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_vl_applyKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_vl_applyKeyTyped

    private void txt_sl_applyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sl_applyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sl_applyActionPerformed
    
    
    
    private void btn_exit_payroll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exit_payroll1ActionPerformed
        intFrame_cutoff.setVisible(false);
    }//GEN-LAST:event_btn_exit_payroll1ActionPerformed

    private void btn_update_leaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update_leaveActionPerformed
        objLeave.objEmployee.setEmpId(txt_empId_leave.getText());
        objLeave.setVacationleave(Double.parseDouble(txt_vl_count.getText()));
        objLeave.setSickleave(Double.parseDouble(txt_sl_count.getText()));
        if (objLeave.applyLeave()) {
            clearLeave();
        }
    }//GEN-LAST:event_btn_update_leaveActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String empId = getEmpId();
        //txtDate2.setText(null);
        model = (DefaultTableModel) jTable1.getModel();
        String sql = "SELECT * FROM leavelog WHERE No=" + empId ;
        loadData(sql);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        model = (DefaultTableModel) jTable1.getModel();
        String sql = "SELECT * FROM leavelog";
        loadData(sql);
        //txtDate2.setText(null);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        exportarExcel(jTable1);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        model2 = (DefaultTableModel) jTable1.getModel();
        String pydtsort = txt_pydt.getText();
        loadData("SELECT * FROM leavelog WHERE Date_Filed = '" + pydtsort + "'");
        //txtDate2.setText(null);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void txtfld_datefiledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfld_datefiledActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfld_datefiledActionPerformed

    private void txtfld_loatypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfld_loatypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfld_loatypeActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        intFrame_cutoff.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btn_searchvianame_leaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchvianame_leaveActionPerformed
        jDialog3.setVisible(true);
    }//GEN-LAST:event_btn_searchvianame_leaveActionPerformed

    private void leave_empfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leave_empfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_leave_empfnameActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String empfname = leave_empfname.getText();
        String emplname = leave_emplname.getText();
        if (objLeave.getLeaveDetailsvianame(empfname,emplname)) {        
            txt_empId_leave.setText(objLeave.objEmployee.getEmpId());
            txt_fname_leave.setText(objLeave.objEmployee.getFname());
            txt_lname_leave.setText(objLeave.objEmployee.getLname());
            txt_desig_leave.setText(objLeave.objEmployee.getDesignation());
            txt_depart_leave.setText(objLeave.objEmployee.getDepartment());
            txt_vl_count.setText(String.valueOf(objLeave.getVacationleave()));
            txt_sl_count.setText(String.valueOf(objLeave.getSickleave()));
            objEmployee.setEmpId(txt_empId_leave.getText());
            objEmployee.setFname(txt_fname_leave.getText());
            objEmployee.setLname(txt_lname_leave.getText());
            objEmployee.setDesignation(txt_desig_leave.getText());
        }
        
        jDialog3.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void leave_emplnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leave_emplnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_leave_emplnameActionPerformed

    private void btn_searchEmp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchEmp1ActionPerformed
        jDialog4.setVisible(true);
        
    }//GEN-LAST:event_btn_searchEmp1ActionPerformed

    private void employee_empfname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_empfname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employee_empfname1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String empfname = employee_empfname1.getText();
        String emplname = employee_emplname1.getText();
        String sql = "SELECT * FROM employee WHERE fname='" + empfname + "' AND lname ='" + emplname + "' ";
        JTable empDetails = new JTable(objEmployee.getAllEmployeeDetails(objEmployee.getAllEmployeeDetails(sql)), objEmployee.getColumnNames(objEmployee.getAllEmployeeDetails(sql)));
        jScrollPane_tableContainer.setViewportView(empDetails);
        jDialog4.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void employee_emplname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_emplname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employee_emplname1ActionPerformed

    private void btn_searchEmp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchEmp2ActionPerformed
        jDialog5.setVisible(true);
    }//GEN-LAST:event_btn_searchEmp2ActionPerformed

    private void employee_desigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_desigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employee_desigActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String desig = employee_desig.getText();
        String sql = "SELECT * FROM employee WHERE designation='" + desig + "'";
        JTable empDetails = new JTable(objEmployee.getAllEmployeeDetails(objEmployee.getAllEmployeeDetails(sql)), objEmployee.getColumnNames(objEmployee.getAllEmployeeDetails(sql)));
        jScrollPane_tableContainer.setViewportView(empDetails);
        jDialog5.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btn_searchEmp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchEmp3ActionPerformed
        jDialog6.setVisible(true);
    }//GEN-LAST:event_btn_searchEmp3ActionPerformed

    private void employee_deptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_deptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employee_deptActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String dept = employee_dept.getText();
        String sql = "SELECT * FROM employee WHERE department='" + dept + "'";
        JTable empDetails = new JTable(objEmployee.getAllEmployeeDetails(objEmployee.getAllEmployeeDetails(sql)), objEmployee.getColumnNames(objEmployee.getAllEmployeeDetails(sql)));
        jScrollPane_tableContainer.setViewportView(empDetails);
        jDialog6.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jDialog7.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void log_empfname2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_log_empfname2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_log_empfname2ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        model3 = (DefaultTableModel) jTable1.getModel();
        String logfname = log_empfname2.getText();
        String loglname = log_emplname2.getText();
        loadData("SELECT * FROM leavelog WHERE Employee_Name = '" + logfname + " " + loglname + "'");
        jDialog7.setVisible(false);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void log_emplname2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_log_emplname2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_log_emplname2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }
    class PrintableImage implements Printable {
        private BufferedImage image;

        PrintableImage(BufferedImage image) {
            this.image = image;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            double scaleX = pageFormat.getImageableWidth() / image.getWidth();
            double scaleY = pageFormat.getImageableHeight() / image.getHeight();
            double scaleFactor = Math.min(scaleX, scaleY);
            int scaledWidth = (int) (image.getWidth() * scaleFactor);
            int scaledHeight = (int) (image.getHeight() * scaleFactor);

            // Center the image on the page
            int x = (int) ((pageFormat.getImageableWidth() - scaledWidth) / 2);
            int y = (int) ((pageFormat.getImageableHeight() - scaledHeight) / 2);

            g2d.drawImage(image, x, y, scaledWidth, scaledHeight, null);
            return PAGE_EXISTS;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroup_rd;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_apply_leave;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_exit_leave;
    private javax.swing.JButton btn_exit_payroll1;
    private javax.swing.JButton btn_exit_update;
    private javax.swing.JButton btn_searchEmp;
    private javax.swing.JButton btn_searchEmp1;
    private javax.swing.JButton btn_searchEmp2;
    private javax.swing.JButton btn_searchEmp3;
    private javax.swing.JButton btn_search_leave;
    private javax.swing.JButton btn_search_update;
    private javax.swing.JButton btn_searchvianame_leave;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton btn_update_leave;
    private javax.swing.JTextField employee_dept;
    private javax.swing.JTextField employee_desig;
    private javax.swing.JTextField employee_empfname1;
    private javax.swing.JTextField employee_emplname1;
    private javax.swing.JInternalFrame intFrame_cutoff;
    private javax.swing.JInternalFrame intFrame_employee_new;
    private javax.swing.JInternalFrame intFrame_employee_search;
    private javax.swing.JInternalFrame intFrame_employee_update;
    private javax.swing.JInternalFrame intFrame_leave;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JDialog jDialog4;
    private javax.swing.JDialog jDialog5;
    private javax.swing.JDialog jDialog6;
    private javax.swing.JDialog jDialog7;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane_tableContainer;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_background;
    private javax.swing.JLabel lbl_depart_allowance1;
    private javax.swing.JLabel lbl_department;
    private javax.swing.JLabel lbl_department1;
    private javax.swing.JLabel lbl_department2;
    private javax.swing.JLabel lbl_department3;
    private javax.swing.JLabel lbl_department4;
    private javax.swing.JLabel lbl_desig_allowance1;
    private javax.swing.JLabel lbl_designation;
    private javax.swing.JLabel lbl_designation1;
    private javax.swing.JLabel lbl_empId;
    private javax.swing.JLabel lbl_empId1;
    private javax.swing.JLabel lbl_empId_allowance1;
    private javax.swing.JLabel lbl_fname;
    private javax.swing.JLabel lbl_fname1;
    private javax.swing.JLabel lbl_fname_allowance1;
    private javax.swing.JLabel lbl_food1;
    private javax.swing.JLabel lbl_lname;
    private javax.swing.JLabel lbl_lname1;
    private javax.swing.JLabel lbl_lname_allowance1;
    private javax.swing.JLabel lbl_pms;
    private javax.swing.JLabel lbl_travel1;
    private javax.swing.JTextField leave_empfname;
    private javax.swing.JTextField leave_emplname;
    private javax.swing.JTextField log_empfname2;
    private javax.swing.JTextField log_emplname2;
    private javax.swing.JMenu menuBar_employee;
    private javax.swing.JMenuItem menuBar_employee_delete;
    private javax.swing.JMenuItem menuBar_employee_new;
    private javax.swing.JMenuItem menuBar_employee_search;
    private javax.swing.JMenuItem menuBar_employee_update;
    private javax.swing.JMenu menuBar_file;
    private javax.swing.JMenuItem menuBar_file_exit;
    private javax.swing.JMenuItem menuBar_file_logout;
    private javax.swing.JMenu menuBar_leave;
    private javax.swing.JMenuItem menuBar_leave_apply;
    private javax.swing.JMenuBar menu_menuBar;
    private javax.swing.JPanel panel_empDetails;
    private javax.swing.JPanel panel_empDetails_payroll1;
    private javax.swing.JPanel panel_empUpdate;
    private javax.swing.JPanel panel_salAllow_payroll1;
    private javax.swing.JTextField txt_deparment;
    private javax.swing.JTextField txt_deparment_update;
    private javax.swing.JTextField txt_depart_leave;
    private javax.swing.JTextField txt_desig_leave;
    private javax.swing.JTextField txt_designation;
    private javax.swing.JTextField txt_designation_update;
    private javax.swing.JTextField txt_empID;
    private javax.swing.JTextField txt_empID_update;
    private javax.swing.JTextField txt_empId_leave;
    private javax.swing.JTextField txt_fname;
    private javax.swing.JTextField txt_fname_leave;
    private javax.swing.JTextField txt_fname_update;
    private javax.swing.JTextField txt_lname;
    private javax.swing.JTextField txt_lname_leave;
    private javax.swing.JTextField txt_lname_update;
    private javax.swing.JTextField txt_pydt;
    private javax.swing.JTextField txt_sicleave;
    private javax.swing.JTextField txt_sl_apply;
    private javax.swing.JTextField txt_sl_count;
    private javax.swing.JTextField txt_vacleave;
    private javax.swing.JTextField txt_vl_apply;
    private javax.swing.JTextField txt_vl_count;
    private javax.swing.JTextField txtfld_approvalby;
    private javax.swing.JTextField txtfld_approveddays;
    private javax.swing.JTextField txtfld_datefiled;
    private javax.swing.JTextField txtfld_hrd;
    private javax.swing.JTextField txtfld_inclusivedate;
    private javax.swing.JTextField txtfld_loatype;
    private javax.swing.JTextField txtfld_numofdays;
    // End of variables declaration//GEN-END:variables
}
