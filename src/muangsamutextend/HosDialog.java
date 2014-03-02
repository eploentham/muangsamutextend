package muangsamutextend;

/*
 * HosDialog.java
 *
 * Created on 18 เธ?เธคเธฉเธ เธฒเธ?เธก 2548, 10:32 เธ?.
 *
 */ 



//import com.hosv3.utility.Constant;
import object.BBranch;
import object.Config11;
import javax.swing.*;
//import javax.swing.text.*;
import java.util.*;
import com.hosv3.utility.connection.UpdateStatus;
import com.hosv3.control.*;
import com.hosv3.utility.connection.*;

import com.hospital_os.object.*; 
import com.hospital_os.usecase.connection.ConnectionInf;
import com.hospital_os.utility.Constant;
import com.hospital_os.utility.Gutil;
import com.hospital_os.utility.IOStream;
import com.hospital_os.utility.TaBleModel;
import com.hosv3.gui.dialog.ConfigPathPrinting;
import com.hosv3.object.printobject.PrintFileName;
import com.hosv3.utility.DateUtil;
import java.io.File;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import object.BDocNumberDB;
import object.BangnaTContact;
import object.TVisit;
import object.TVisitDB;
import object.VTBillingInvoiceSubgroup;
//import com.hosv3.control.DialogCause;
/**
 *
 * @author  administrator
 */
public final class HosDialog {
    
    HosControl theHC;
    UpdateStatus theUS;    
    private ConnectionInf theBCconn;
    DialogSearchPatient theDialogSearchPatient;
    DialogHistoryPatient theDialogHistoryPatient;
//    DialogContactAdjust theDialogContactAdjust;
    DialogBillingReceive theDialogBillingReceive;
    DialogDoctorXrayDx theDialogXrayDx;
    DialogSurveillanceCase theSurveillance;
    JFrame theJFrame;
    private object.Config11 config1 ;
    public BBranch branch;
    com.hospital_os.object.Visit visit;
    private HosManage theHosManage;

    TVisit t_visit = new TVisit();
    TVisitDB t_visitdb = new TVisitDB();
    public OrderItem dbObj = new OrderItem();
    boolean choosePrinter = true;
    muangsamut muang = new muangsamut();
    public CashierTBillingInvoicePrintDB cbi_pdb = new CashierTBillingInvoicePrintDB();
     
    public HosDialog(HosControl hc,UpdateStatus us, HosManage hm) {
        setControl(hc,us,hm);
        config1 = new Config11();
        branch = new BBranch();
        visit = new com.hospital_os.object.Visit();
        try {
            branch = config1.getBranchActive();
        } catch (Exception ex) {
            Logger.getLogger(HosDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    public HosDialog(HosControl hc,UpdateStatus us){
        setControl(hc,us,null);
    }
    
    public void setControl(HosControl hc,UpdateStatus us, HosManage hm) {
        theHC = hc;
        theUS = us;
        theHosManage = hm;
    }
    public void setJFrame(JFrame jf){
        theJFrame = jf;
    }
    public void showDialogSearchPatient(String fname,String lname,String hn,String pid){
        if(theDialogSearchPatient==null)
            theDialogSearchPatient = new DialogSearchPatient(theHC,theUS,1,theHosManage);
        if(!hn.equals("")|| !pid.equals("")){
            theDialogSearchPatient.setHNPID(hn,pid);
            theDialogSearchPatient.setFnameLname(fname,lname);
        }
        else if(!fname.equals("")||!lname.equals("")) {
            theDialogSearchPatient.setFnameLname(fname,lname);
            theDialogSearchPatient.setHNPID(hn,pid);
        }
        
        System.out.println(fname + ": " + lname);
        theDialogSearchPatient.showDialog();
    }
    public void printDrugSummaryVnOPD(){
//        theBCconn.open();
        try{
            //theHC.theLO.theOption.print_xraycard_con
            Constant.println("printDrugSummaryVn" );
//                checkPathPrint(frm, true);
            Map o = new HashMap();
//            JOptionPane.showMessageDialog(null, "visit_id "+theHC.theHO.theVisit.getObjectId());
            o.put("visit_id",theHC.theHO.theVisit.getObjectId());
            o.put("patient_fullname",theHC.theLookupControl.readPrefixString(theHC.theHO.thePatient.prefix_id)+" "+ theHC.theHO.thePatient.fname +" "+theHC.theHO.thePatient.lname);
            o.put("visit_hn",theHC.theHO.theVisit.hn);
            o.put("visit_vn",theHC.theHO.theVisit.vn);
            o.put("date_current",theHC.theHO.date_time);
            Constant.println("printDrugSummaryVn " + theHC.theHO.theVisit.getObjectId());
            boolean retp = initPrintDrug("drug_summary_vn_stricker_opd",1,o,null,false);
        }catch(Exception ex){
            theUS.setStatus("printDrugSummaryVnOPD "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
        }finally{
//            theConnectionInf.close();
        }
    }
    public void printDrugSummaryVnIPD(){
//        theBCconn.open();
        try{
            //theHC.theLO.theOption.print_xraycard_con
            Constant.println("printDrugSummaryVn" );
//                checkPathPrint(frm, true);
            Map o = new HashMap();
//            JOptionPane.showMessageDialog(null, "visit_id "+theHC.theHO.theVisit.getObjectId());
            o.put("visit_id",theHC.theHO.theVisit.getObjectId());
            o.put("patient_fullname",theHC.theLookupControl.readPrefixString(theHC.theHO.thePatient.prefix_id)+" "+ theHC.theHO.thePatient.fname +" "+theHC.theHO.thePatient.lname);
            o.put("visit_hn",theHC.theHO.theVisit.hn);
            o.put("visit_vn",theHC.theHO.theVisit.vn);
            o.put("date_current",theHC.theHO.date_time.substring(0, 10));
            Constant.println("printDrugSummaryVn " + theHC.theHO.theVisit.getObjectId());
            Constant.println("date_current= " + theHC.theHO.date_time.substring(0, 10));
            boolean retp = initPrintDrug("drug_summary_vn_stricker_ipd",1,o,null,false);
            //theHC.theHosDB.theOrderItemDB
        }catch(Exception ex){
            theUS.setStatus("printDrugSummaryVnIPD "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
        }finally{
//            theConnectionInf.close();
        }
    }
    protected boolean initPrintDrug(String filename,int valuePrint,Map o,Connection ds, boolean mode_con) throws Exception {
       JasperReport jp=null;
       String file_name = "";
       if(theHC.theLO.path_print.indexOf("C")>0) {
           file_name = theHC.theLO.path_print + "/"+filename+".xml";
       } else {
           file_name = theHC.theLO.path_print + "/"+filename+".xml";
       }
       File file = new File(file_name);
//       JOptionPane.showMessageDialog(null, "file_name "+file_name);
       if(!file.isFile()) {
           theUS.setStatus("ไม่พบไฟล์ที่ทำการสั่งพิมพ์ "+file_name,UpdateStatus.ERROR);
           return false;
       }
       try {
//           JOptionPane.showMessageDialog(null, "compile "+file_name);
           jp = JasperCompileManager.compileReport(file_name);
//           JOptionPane.showMessageDialog(null, "222222222222222 ");
       } catch(Exception e) {
           e.printStackTrace(Constant.getPrintStream());
           theUS.setStatus(e.getMessage()+" "+file_name,UpdateStatus.ERROR);
           return false;
       }
        JasperPrint jprint = null;
        if(mode_con) {

        }
//            jprint = JasperFillManager.fillReport(jp,o, theConnectionInf.getConnection());
        else {
//            JOptionPane.showMessageDialog(null, "ds "+ds.getCatalog());
            Connection conn;
            branch = config1.getBranchActive();
            conn = config1.getConnectionHospital(branch.getBranchId());
            jprint = JasperFillManager.fillReport(jp,o, conn);
            conn.close();
        }
        if(valuePrint==1){
            JasperViewer.viewReport(jprint,false);
            return true;
        }
        JasperPrintManager.printReport(jprint, choosePrinter);
        return true;
   }
    public void showDialogSurveillanceCase(HosControl hc,UpdateStatus us,Plan p, Boolean chk){
        if(theSurveillance == null){
            theSurveillance =new DialogSurveillanceCase();
        }
        theSurveillance.showDialog(hc, us, p, chk);
    }
    public void showDialogHistoryPatient(Patient thePatient){
        if(theDialogHistoryPatient == null){
            theDialogHistoryPatient =new DialogHistoryPatient(theHC, theUS);
        }
        theDialogHistoryPatient.showDialog(thePatient);
    }
    public void showDialogContactAdjust(String patient_hn, String patient_fullnamet){
//        JOptionPane.showMessageDialog(null, "showDialogContactAdjust");
//        if(theDialogContactAdjust != null){
//            theDialogContactAdjust.dispose();
//        }
//        theDialogContactAdjust = new DialogContactAdjust(patient_hn, patient_fullnamet, theHC);
//        theDialogContactAdjust.showDialog(patient_hn, patient_fullnamet);
    }
    public void showDialogDoctorXrayDx() {
        String sql="", xray_number="";
        try {
            if(theDialogXrayDx != null){
                theDialogXrayDx.dispose();
            }
//            JOptionPane.showMessageDialog(null, "showDialogDoctorXrayDx theDialogXrayDx = new DialogDoctorXrayDx()");
//            if(theHC.theResultControl.!=null){
//                xray_number=theHC.theHO.thePatientXN.patient_xray_number;
//            }else{
//                xray_number ="";
//            }
            theDialogXrayDx = new DialogDoctorXrayDx();
//            JOptionPane.showMessageDialog(null, "showDialogDoctorXrayDx theDialogXrayDx.showDialog(theHC, theUS,this,xray_number);");
            theDialogXrayDx.showDialog(theHC, theUS,this,xray_number);
//            JOptionPane.showMessageDialog(null, "showDialogDoctorXrayDx1");
            Constant.println("theLO.theOption.print_cashier_invoice" );
        } catch(Exception ex) {
            theUS.setStatus("การสั่งพิมพ์รายการผิดพลาด "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
        }
        finally {
//            theConnectionInf.close();
        }
    }
    
    public OrderItem initConfig()
    {
        dbObj.table="t_order";
        dbObj.pk_field="t_order_id";
        dbObj.visit_id   ="t_visit_id";
        dbObj.item_code   ="b_item_id";
        dbObj.vertifier   ="order_staff_verify";
        dbObj.vertify_time   ="order_verify_date_time";
        dbObj.executer   ="order_staff_execute";
        dbObj.executed_time   ="order_executed_date_time";
        dbObj.discontinue   ="order_staff_discontinue";
        dbObj.discontinue_time   ="order_discontinue_date_time";
        dbObj.dispense   ="order_staff_dispense";
        dbObj.dispense_time   ="order_dispense_date_time";
        dbObj.clinic_code   ="order_service_point";
        dbObj.item_group_code_category   ="b_item_subgroup_id";
        dbObj.charge_complete   ="order_charge_complete";
        dbObj.status   ="f_order_status_id";
        dbObj.secret   ="order_secret";
        dbObj.continue_order   ="order_continue";
        dbObj.price   ="order_price";
        dbObj.qty   ="order_qty";
        dbObj.item_group_code_billing   ="b_item_billing_subgroup_id";
        dbObj.hn   ="t_patient_id";
        dbObj.category_group   ="f_item_group_id";
        dbObj.common_name   ="order_common_name";
        dbObj.refer_out   ="order_refer_out";
        dbObj.request   ="order_request";
        dbObj.order_user   ="order_staff_order";
        dbObj.order_time   ="order_date_time";
        dbObj.order_complete ="order_complete";
        dbObj.order_cost = "order_cost";
        dbObj.note = "order_notice";

        dbObj.reporter = "order_staff_report";
        dbObj.reported_time = "order_report_date_time";

        //เก็บสาเหตุการยกเลิกผลแลป --sumo-- 13/3/2549
        dbObj.cause_cancel_resultlab = "order_cause_cancel_resultlab";

        ///amp:30/03/2549 เก็บข้อมูลการสั่งยาที่แพ้ และสั่งยาที่มีปฏิกิริยาด้วย
        dbObj.drug_allergy = "order_drug_allergy";
        //tong:01/06/49 รายการกลับบ้าน
        dbObj.order_home = "order_home";
        dbObj.item_16_group = "b_item_16_group_id";
        return dbObj;
    }
    public Vector eQuery(String sql) throws Exception
    {
        OrderItem p;
        Vector list = new Vector();
        p = initConfig();
//        ConnectionInf bcconn = new ConnectionInf();private ConnectionInf theBCconn;
        theBCconn = theHC.theConnectionInf;
        theBCconn.open();
        ResultSet rs = theBCconn.eQuery(sql.toString());
        int i =0;
        while(rs.next())
        {
            p = new OrderItem();
            p.setObjectId(rs.getString(dbObj.pk_field));
            p.visit_id = rs.getString(dbObj.visit_id);
            p.item_code = rs.getString(dbObj.item_code);
            p.vertifier = rs.getString(dbObj.vertifier);
            p.vertify_time = rs.getString(dbObj.vertify_time);
            p.executer = rs.getString(dbObj.executer);
            p.executed_time = rs.getString(dbObj.executed_time);
            p.discontinue = rs.getString(dbObj.discontinue);
            p.discontinue_time = rs.getString(dbObj.discontinue_time);
            p.dispense = rs.getString(dbObj.dispense);
            p.dispense_time = rs.getString(dbObj.dispense_time);
            p.clinic_code = rs.getString(dbObj.clinic_code);
            p.category_group = rs.getString(dbObj.category_group);
            p.charge_complete = rs.getString(dbObj.charge_complete);
            p.status = rs.getString(dbObj.status);
            p.secret = rs.getString(dbObj.secret);
            p.continue_order = rs.getString(dbObj.continue_order);
            p.price = rs.getString(dbObj.price);
            p.qty = rs.getString(dbObj.qty);
            p.item_group_code_billing = rs.getString(dbObj.item_group_code_billing);
            p.common_name = rs.getString(dbObj.common_name);
            p.refer_out = rs.getString(dbObj.refer_out);
            p.hn = rs.getString(dbObj.hn);
            p.request = rs.getString(dbObj.request);
            p.order_user = rs.getString(dbObj.order_user);
            p.order_time = rs.getString(dbObj.order_time);
            p.order_complete = rs.getString(dbObj.order_complete);
            p.order_cost = rs.getString(dbObj.order_cost);
            p.note = rs.getString(dbObj.note);
            p.reporter = rs.getString(dbObj.reporter);
            p.reported_time = rs.getString(dbObj.reported_time);
            p.cause_cancel_resultlab = rs.getString(dbObj.cause_cancel_resultlab);
            p.drug_allergy = rs.getString(dbObj.drug_allergy);
            p.order_home = rs.getString(dbObj.order_home);
            p.item_16_group = rs.getString(dbObj.item_16_group);
            p.item_group_code_category = rs.getString(dbObj.item_group_code_category);
            list.add(p);
//            if(false && with_drug)
//                p.theOrderItemDrug = theOrderItemDrugDB.rs2Object(rs);
            i = i+1;
        }
        rs.close();
        theBCconn.close();
        return list;
    }
    public Vector selectOrderItemByVNCGForOption(String visit_id ,String category_group, boolean showreport, boolean useshowexecute) throws Exception
    {
        String sql="select * " +
                "from t_order " +
                "where t_visit_id = '"+visit_id+"' and f_item_group_id = '3' " +
                "Order by order_date_time";

        //sql = "";
        return eQuery(sql.toString());
    }
    public String selectClaimNumber(String branch_id,String t_visit_id) throws Exception {
        String sql="select bcp.contract_plans_number " +
                "from t_visit tv " +
                "left join t_visit_payment tvp on tv.t_visit_id = tvp.t_visit_id " +
                "left join b_contract_plans bcp on tvp.b_contract_plans_id = bcp.b_contract_plans_id " +
                "where tv.t_visit_id = '"+t_visit_id+"' and visit_payment_active = '1'";

        String claim_namet="";
        Connection conn;
        conn = config1.getConnectionHospital(branch_id);
        Statement st;
        st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery(sql);
        while(rs.next()){
            claim_namet = rs.getString("contract_plans_number");
        }
        rs.close();
        conn.close();
        //sql = "";
        return claim_namet;
    }
    public Double getDeduct(String branch_id,String tbi_id) throws Exception {
        String sql="select billing_deduct " +
                "from t_billing tb "
                + "left join t_billing_invoice tbi on tb.t_billing_id = tbi.t_billing_id " +
                "where tbi.t_billing_invoice_id = '"+tbi_id+"' and tb.billing_active = '1'";
        Double discount=0.0;
        Connection conn;
        conn = config1.getConnectionHospital(branch_id);
        Statement st;
        st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery(sql);
        while(rs.next()){
            discount = rs.getDouble("billing_deduct");
        }
        rs.close();
        conn.close();
        //sql = "";
        return discount;
    }
    public String selectContract(String branch_id,String t_visit_id, String t_billing_invoice_id) throws Exception {
        String sql="select bc.contract_number " +
                "from t_visit tv " +
                "left join t_visit_payment tvp on tv.t_visit_id = tvp.t_visit_id " +
                "left join b_contract bc on tvp.b_contract_id = bc.b_contract_id " +
                "left join t_billing_invoice tbi on tv.t_visit_id = tbi.t_visit_id " +
                "where tv.t_visit_id = '"+t_visit_id+"' and tbi.t_billing_invoice_id ='"+t_billing_invoice_id+"' and visit_payment_active = '1'";

        String claim_namet="";
        Connection conn;
        conn = config1.getConnectionHospital(branch_id);
        Statement st;
        st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery(sql);
        while(rs.next()){
            claim_namet = rs.getString("contract_number");
        }
        rs.close();
        conn.close();
        //sql = "";
        return claim_namet;
    }
    public Vector selectXRayDescription(String t_order_id) throws Exception
    {
        String sql="select * " +
                "from t_order to " +
                "left join t_result_xray_position " +
                "where t_visit_id = '"+t_order_id+"' and f_item_group_id = '3' " +
                "Order by order_date_time";

        //sql = "";
        return eQuery(sql.toString());
    }
    private void updateStatusOrderPrint(String tOrderId){
        String sql = "";
        sql = "Update t_order_drug Set order_status_print = '1' Where t_order_id = '"+tOrderId+"'";
        try {
            eQuery(sql);
        } catch (Exception ex) {
            Logger.getLogger(HosDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    public Vector listOrderXrayByVN(String vn) {
        /*theOrderItemDB = new OrderItemDB(theConnectionInf);*/
        Vector vc;
//        if(open_con)
//            theConnectionInf.open();
        try {
            //comment by tong ได้เข้าไปเปลี่ยน function การทำงานใหม่
            vc = selectOrderItemByVNCGForOption(vn,CategoryGroup.isXray(),false,false);
            return    vc;
        }
        catch(Exception ex) {
            theUS.setStatus("listOrderXrayByVN "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
//            if(open_con)
//                theConnectionInf.rollback();
        }
        finally {
//            if(open_con)
//                theConnectionInf.close();
        }
        //         theConnectionInf.commit();
        /*theOrderItemDB = null;*/
        return null;
    }
    public void printXRayPatientHn(){
//        theBCconn.open();
        try{
            //theHC.theLO.theOption.print_xraycard_con
            Constant.println("theLO.theOption.print_xraycard_con" + theHC.theLO.theOption.print_xraycard_con);
//                checkPathPrint(frm, true);
            Map o = new HashMap();
            o.put("hn",theHC.theHO.theVisit.hn);
            Constant.println("theHO.theVisit.getObjectId()" + theHC.theHO.theVisit.getObjectId());
            boolean retp = initPrint("x_ray_card_hn",0,o,null,false);
        }
        catch(Exception ex){
            theUS.setStatus("printXRayPatientHn "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
        }
        finally{
//            theConnectionInf.close();
        }
    }
    public void printXRayPatientXn(){
//        theBCconn.open();
        String sex="", prefix_namet="";
        try{
            //theHC.theLO.theOption.print_xraycard_con
            Constant.println("theLO.theOption.print_xraycard_con" + theHC.theLO.theOption.print_xraycard_con);
//                checkPathPrint(frm, true);
            Map o = new HashMap();
            Vector orderxrayreportedV = listOrderXrayByVN(theHC.theHO.theVisit.getObjectId());
            for(int i=0,size=orderxrayreportedV.size();i<size; i++){
                OrderItem order = (OrderItem)orderxrayreportedV.get(i);
                ResultXRay rx = theHC.theOrderControl.readOrderXrayByVNItemId(order.getObjectId(),order.visit_id);
                if(theHC.theHO.thePatient.sex.equals("1")){
                    sex="ชาย";
                }else{
                    sex="หญิง";
                }
                if(theHC.theHO.thePatient.prefix_id.equals("001")){
                    prefix_namet="ด.ช.";
                }else if(theHC.theHO.thePatient.prefix_id.equals("002")){
                    prefix_namet="ด.ญ.";
                }else if(theHC.theHO.thePatient.prefix_id.equals("003")){
                    prefix_namet="นาย";
                }else if(theHC.theHO.thePatient.prefix_id.equals("004")){
                    prefix_namet="นางสาว";
                }else if(theHC.theHO.thePatient.prefix_id.equals("005")){
                    prefix_namet="นาง";
                }else if(theHC.theHO.thePatient.prefix_id.equals("845")){
                    prefix_namet="Mr.";
                }else if(theHC.theHO.thePatient.prefix_id.equals("846")){
                    prefix_namet="Miss";
                }else if(theHC.theHO.thePatient.prefix_id.equals("847")){
                    prefix_namet="Mrs.";
                }
                o.put("hn",theHC.theHO.theVisit.hn);
                o.put("name",prefix_namet+theHC.theHO.thePatient.fname+" "+theHC.theHO.thePatient.lname);
                o.put("xn",rx.xn);
                o.put("xray_date",rx.record_time.substring(8, 10)+"-"+ rx.record_time.substring(5, 7)+"-"+ rx.record_time.substring(0, 4));
                o.put("age","เพศ "+sex+" อายุ "+theHC.theHO.thePatient.age);
            }
            Constant.println("theHO.theVisit.getObjectId()" + theHC.theHO.theVisit.getObjectId());
            boolean retp = initPrint("x_ray_card_xn",0,o,null,false);
        }
        catch(Exception ex) {
            theUS.setStatus("การสั่งพิมพ์รายการผิดพลาด "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
        }
        finally{
//            theConnectionInf.close();
        }
    }
    public void printXRayPatientDescription(){
//        theBCconn.open();
        try{
            //theHC.theLO.theOption.print_xraycard_con
            String xray_date = config1.getDateDBHospital("ddMMyyyy");
            xray_date = config1.DateFormatDB2Show(xray_date, "ddMMyyyy");
            String xray_time = config1.getTimeDB("hh:mm");
            Constant.println("theLO.theOption.print_xraycard_con" + theHC.theLO.theOption.print_xraycard_con);
//                checkPathPrint(frm, true);
//            theHC.theHO.theVisit.
            Vector orderxrayV = listOrderXrayByVN(theHC.theHO.theVisit.getObjectId());//,open_con);
            Map o = new HashMap();
            Map oo = new HashMap();
            int i=0;
//            o.put("visit_id",theHC.theHO.theVisit.getObjectId());
            for(i=0;i<=orderxrayV.size()-1; i++) {
                OrderItem order = (OrderItem)orderxrayV.get(i);
                if(i<=4) {
                    o.put("description"+i,order.common_name);
                }
//                if(i>=5){
//                    exit;
//                }
            }
            o.put("xray_date", xray_date+" "+xray_time);
            boolean retp = initPrint("x_ray_card_description",0,o,null,false);

            if(orderxrayV.size()>5) {
                for(int ii=5;ii<=orderxrayV.size()-1; ii++) {
                    OrderItem order = (OrderItem)orderxrayV.get(ii);
                    if(ii<=9) {
                        oo.put("description"+(ii-5),order.common_name);
                    }
                }
                oo.put("xray_date", xray_date+" "+xray_time);
                boolean retp1 = initPrint("x_ray_card_description",0,oo,null,false);

            }
            //o.put("xray_date", xray_date+" "+xray_time);


//            o.put("name",theHC.theHO.thePatient.fname+" "+theHC.theHO.thePatient.lname);
////            o.put("xn",theHC.theHO.);
//            o.put("xn",theHC.theHO.thePatientXN.patient_xn_year);
//            o.put("age",theHC.theHO.thePatient.sex+" "+theHC.theHO.thePatient.age);
//            Constant.println("theHO.theVisit.getObjectId()" + theHC.theHO.theVisit.);

//            Connection conn;
//            conn = config1.getConnectionHospital("103003");
//            conn = config1.
            
            theUS.setStatus("theHO.theVisit.getObjectId() "+theHC.theHO.theVisit.getObjectId(),UpdateStatus.NORMAL);
        }
        catch(Exception ex) {
            theUS.setStatus("การสั่งพิมพ์รายการผิดพลาด "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
        }
        finally{
//            theConnectionInf.close();
        }
    }
    public boolean intPrintCon(String filename,int valuePrint,Map o) throws Exception {
        return initPrint(filename,valuePrint,o,null,true);
   }
    public void printOPDCard(){
//        Constant.println(UseCase.UCID_printOPDCard);
        String objectid =   null;
//        theBCconn.open();
        try{
//            JOptionPane.showMessageDialog(null, "theHC.theLO.theOption.print_opdcard_con1111");
//            if(theHC.theLO.theOption.print_opdcard_con.equals("1")){
//                JOptionPane.showMessageDialog(null, "theHC.theHO.thePatient.getObjectId() "+theHC.theHO.thePatient.getObjectId());
//                checkPathPrint(frm);
                Map o = new HashMap();
                o.put("patient_id",theHC.theHO.thePatient.getObjectId());
                o.put("prefix_surname",muang.getPrefixSurname(theHC.theHO.thePatient.lname));
                o.put("username",theHC.theHO.theEmployee.fname + "  " + theHC.theHO.theEmployee.lname);
                boolean ret = intPrintCon("OPD_Card_con",1,o);
                if(!ret) return ;
//            }
//            else
//                intPrintOPDCard(printPreview,vVisitPayment);
            if(theHC.theHO.theVisit != null)
                objectid = theHC.theHO.theVisit.getObjectId();
//            theSystemControl.setStatus(UseCase.TH_printOPDCard,UpdateStatus.COMPLETE,null);
//            theSystemControl.saveLog(UseCase.UCID_printOPDCard,objectid,null,UpdateStatus.COMPLETE);
        }
        catch(Exception ex){
//            theSystemControl.setStatus(UseCase.TH_printOPDCard,UpdateStatus.ERROR,ex);
//            theSystemControl.saveLog(UseCase.UCID_printOPDCard,objectid,ex,UpdateStatus.ERROR);
        }
        finally{
//            theBCconn.close();
        }
    }
    public void printXRayPatientResult() {
        try{
            Constant.println("theLO.theOption.print_xraycard_result" + theHC.theLO.theOption.print_xraycard_con);
            Map o = new HashMap();
            branch = config1.getBranchActive();
            o.put("visit_id",theHC.theHO.theVisit.getObjectId());
            Connection conn;
            conn = config1.getConnectionHospital(branch.getBranchId());
//            conn = config1.
            boolean retp = initPrint("x_ray_card_result",0,o,conn,false);
            conn.close();
            //theUS.setStatus("theHO.theVisit.getObjectId() "+theHC.theHO.theVisit.getObjectId(),UpdateStatus.NORMAL);
        }
        catch(Exception ex){
            theUS.setStatus("การสั่งพิมพ์รายการผิดพลาด "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
        }
        finally{
//            theConnectionInf.close();
        }
    }
    public String getInvoicePrint(String visit_vn, String visit_date_time, String tbiId, String flag) {
        String sql="", invoice_print_id="", current_date="", print_page="0", doc_number="",primary="";
        Integer print_preview=0;
        BBranch branch = new BBranch();
        BangnaTContact t_contact = new BangnaTContact();
        BangnaTContactDB t_contactdb = new BangnaTContactDB();
        BDocNumberDB doc_numberdb = new BDocNumberDB();
        t_visit = new TVisit();
        Double nettotal=0.0, amount=0.0, discount=0.0;
        Vector v_vv_bis = new Vector();
//        VitalSign theVitalSign = new VitalSign();
        PrimarySymptom thePrimarySymtom = new PrimarySymptom();
//        Vector v_vitalSign = new Vector();
        Vector v_thePrimarySymtom = new Vector();
        
        CashierTBillingInvoicePrint cbi_p = new CashierTBillingInvoicePrint();
        CashierTBillingInvoicePrintDetail cbi_pd = new CashierTBillingInvoicePrintDetail();
        VTBillingInvoiceSubgroup vv_bis = new VTBillingInvoiceSubgroup();
        try {
//            config1.getSiteNamet(print_page)
            branch = config1.getBranchActive();
            if(flag.equals("invoice")) {
                print_page = branch.getPrintInvoice();
                print_preview = Integer.parseInt(branch.getPrintInvoicePreview());
            }else{
                print_page = branch.getPrintReceive();
                print_preview = Integer.parseInt(branch.getPrintReceivePreview());
            }
            if(print_page.equals("") || print_page == null) {
                print_page = "0";
            }
            //JOptionPane.showMessageDialog(null, "111111111111111111111 ");
        } catch (Exception ex) {
            Logger.getLogger(HosDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        v_vv_bis = cbi_pdb.getVTBillingInvoiceSubgroup(branch.getBranchId(),tbiId, visit_vn,flag);

        if(v_vv_bis.size()>0) {
            try {
                Connection conn;
                conn = config1.getConnectionBangna();

                Boolean chk=false;

                if(flag.equals("receive")) {
//                    muangsamutextend.HosDB theHosDB = new muangsamutextend.HosDB(theHC.theConnectionInf);
//                    object.Visit theVisit = new object.Visit();
//                    muangsamutextend.VisitControl theVisitControl = new muangsamutextend.VisitControl(theHC.theConnectionInf,theHC.theHO,theHosDB,null);
//    //                bangnaextend.VisitControl theVisitControl = new bangnaextend.VisitControl(theHC.theConnectionInf,theHC.theHO,theHosDB,null);
//                    Vector v = theVisitControl.listVisitByVn(visit_vn);
//                    theVisit = (object.Visit)v.get(0);
//
//                    String[] receive_privilege;
//                    receive_privilege = branch.getReceivePrivilege().split("\\,");
////                    String claim_namet = selectClaimNumber(branch.getBranchId(), theVisit.getObjectId());//selectContract
//                    String contract_number = selectContract(branch.getBranchId(), theVisit.getObjectId(),t_billing_invoice_id);//selectContract
////                    String contract_number="";
//                    for(int iii=0;iii<=receive_privilege.length-1;iii++) {
//                        if(contract_number.equals(receive_privilege[iii])) {
//                            chk=true;
//                        }
//                    }
                }
                if(!chk && flag.equals("receive")) {
//                    theUS.setStatus("ประเภทการรับชำระ ไม่สามารถพิมพ์ได้",UpdateStatus.WARNING);
//                    return "";
                }
//                t_contact = t_contactdb.getTContactByPK(branch.getBranchId(), t_billing_invoice_print.getTVisitHn(),"patient_hn");
                t_visit = t_visitdb.getTVisitByPK(branch.getBranchId(), "", visit_vn, "visit_vn");
                current_date = config1.getDateDBHospital("ddMMyyyy");
                if(current_date.length()>4) {
                    Integer year = Integer.parseInt(current_date.substring(0, 4));
                    if(year>3000) {
                        current_date = String.valueOf(year-543) + current_date.substring(4);
                    }
                }
                primary="";
//                theVitalSign = new VitalSign();
                thePrimarySymtom = new PrimarySymptom();
                theHC.theHosDB.thePrimarySymptomDB.theConnectionInf.open();
                v_thePrimarySymtom = theHC.theHosDB.thePrimarySymptomDB.selectByVisitId(t_visit.getTVisitId());
                for(int iii=0;iii<=v_thePrimarySymtom.size()-1;iii++) {
                    thePrimarySymtom = (PrimarySymptom)v_thePrimarySymtom.get(iii);
                    primary += thePrimarySymtom.main_symptom+" ";
//                    if(visit.getObjectId().equals("255117676798366898")) {
////                            note="";
//                    }
                }
                theHC.theHosDB.thePrimarySymptomDB.theConnectionInf.close();
//                v_vitalSign = theHC.theVitalControl.listVitalSign(theHC.theHO.theVisit.getObjectId());

//                for(int ii=0;ii<=v_vitalSign.size()-1;ii++) {
//                    theVitalSign = (VitalSign)v_vitalSign.get(ii);
//                }
                cbi_pdb.setCashierTBillingInvoicePrintActiveVoid(visit_vn);
                for(Integer i=0;i<=v_vv_bis.size()-1;i++) {
                    vv_bis = (VTBillingInvoiceSubgroup)v_vv_bis.get(i);
                    if(i==0) {
                        if(t_visit.getVisitDischargeDate().equals("")) {
                            t_visit.setVisitDischargeDate(current_date);
                        }
                        if(t_visit.getContactNamet().equals("")){
                            t_contact = t_contactdb.getTContactByPK(branch.getBranchId(), vv_bis.getHn(),"patient_hn");
                            cbi_p.setPatientCompanyName(t_contact.getContactNamet());
                        }else{
                            cbi_p.setPatientCompanyName(t_visit.getContactNamet());
                        }
                        cbi_p.setPatientAddress(vv_bis.getAmphur());
                        cbi_p.setPatientAge(vv_bis.getPatientAge());
                        cbi_p.setPatientFullnamet(vv_bis.getPrefixPatient()+" "+vv_bis.getFirstname()+" "+vv_bis.getLastname());
                        cbi_p.setPatientPid(vv_bis.getPid());
                        cbi_p.setTBillingInvoiceId(vv_bis.getInvoiceNumber());

                        cbi_p.setTBillingInvoicePrintActive("1");
                        cbi_p.setTBillingInvoicePrintDate(current_date);
                        cbi_p.setTBillingInvoicePrintId("");
                        cbi_p.setTVisitHn(vv_bis.getHn());
                        cbi_p.setTVisitId(visit_vn);

                        cbi_p.setTVisitVn(vv_bis.getVn());
                        cbi_p.setVisitTypeDescription(vv_bis.getPlan());
                        cbi_p.setVisitDate(config1.DateFormatDB2Show(t_visit.getVisitDate(),"ddMMyyyy"));
                        cbi_p.setVisitTime(t_visit.getVisitTime());
                        cbi_p.setDischargeDate(config1.DateFormatDB2Show(t_visit.getVisitDischargeDate(),"ddMMyyyy"));
                        cbi_p.setSickness(config1.StringNull(primary));
                        doc_number = doc_numberdb.getRunningDocNumber(branch.getBranchId(), conn,t_visit.getFVisitTypeId(), flag);
                        cbi_p.setInvoiceNumber(doc_number);
                        cbi_p.setTBillingInvoiceId(tbiId);
                        if(flag.equals("receive")) {
                            CashierTBillingReceivePrint cbr_p = CashInvoiceToReceipt(cbi_p);
                            invoice_print_id = cbi_pdb.setSaveCashierTBillingReceivePrint(branch.getBranchId(), cbr_p);
                        }else {
                            invoice_print_id = cbi_pdb.setSaveCashierTBillingInvoicePrint(branch.getBranchId(), cbi_p);
                        }
                    }
                    if(vv_bis.getInvoiceNumber().equals("OPD")) {
                        //continue;
                    }
                    cbi_pd.setCode(vv_bis.getSpace1()+" "+vv_bis.getInvoiceNumber());
                    if(vv_bis.getItemCommonName().equals("")){
                        vv_bis.setItemCommonName(vv_bis.getBillingSubgroup());
                    }
                    if(vv_bis.getItemCommonName().equals("ยาผู้ป่วยนอก") && vv_bis.getVn().subSequence(0, 1).equals("1")&&vv_bis.getPatientShare()<=0.0){
//                        sql="";
                        continue;
                    }
                    cbi_pd.setName1(vv_bis.getItemCommonName());
                    cbi_pd.setPrice(vv_bis.getPatientShare());
                    cbi_pd.setNode(vv_bis.getNode());
                    if(!vv_bis.getInvoiceNumber().equals("")) {
                        String[] sort1;
                        sort1 = vv_bis.getInvoiceNumber().split("\\.");
                        cbi_pd.setSortLine1(0.0);
                        cbi_pd.setSortLine2(0.0);
                        cbi_pd.setSortLine3(0.0);
                        cbi_pd.setSortLine4(0.0);
                        cbi_pd.setSortLine5(0.0);
                        for(int j=0;j<=sort1.length-1;j++) {
                           if(j==0) {
                               if(sort1[j]!=null) {
                                   cbi_pd.setSortLine1(Double.parseDouble(sort1[j]));
                               } else{
                                   cbi_pd.setSortLine1(0.0);
                               }
                           } else if(j==1) {
                               if(sort1[j]!=null) {
                                   cbi_pd.setSortLine2(Double.parseDouble(sort1[j]));
                               }else{
                                   cbi_pd.setSortLine2(0.0);
                               }
                           } if(j==2) {
                               if(sort1[j]!=null) {
                                   cbi_pd.setSortLine3(Double.parseDouble(sort1[j]));
                               } else{
                                   cbi_pd.setSortLine3(0.0);
                               }
                           } if(j==3){
                               if(sort1[j]!=null) {
                                   cbi_pd.setSortLine4(Double.parseDouble(sort1[j]));
                               }else{
                                   cbi_pd.setSortLine4(0.0);
                               }
                           } if(j==4) {
                               if(sort1[j]!=null) {
                                   cbi_pd.setSortLine5(Double.parseDouble(sort1[j]));
                               } else{
                                   cbi_pd.setSortLine5(0.0);
                               }
                           }
                       }
                    }
//                        if(vv_t_billing_invoice_subgroup.getInvoiceNumber().equals("9")||vv_t_billing_invoice_subgroup.getInvoiceNumber().equals("999")) {
//                            discount+=t_billing_invoice_print_detail.getPrice();
//                        }
//                    } else {
//                        t_billing_invoice_print_detail.setRow1(999.0);
                    if(vv_bis.getInvoiceNumber().equals("9")||vv_bis.getInvoiceNumber().equals("999")) {
                        discount+=cbi_pd.getPrice();
                    }
////                    }
    //                t_billing_invoice_print_detail.setRow1(Double.parseDouble(i.toString()));
                    cbi_pd.setTBillingInvoicePrintDetailId("");
                    cbi_pd.setTBillingInvoicePrintId(invoice_print_id);

                    if(flag.equals("receive")) {
                        CashierTBillingReceivePrintDetail t_billing_receive_print_Detail = CashInvoiceDetailToReceiveDetail(cbi_pd);
                        cbi_pdb.setSaveCashierTBillingReceivePrintDetail(conn,branch.getBranchId(), t_billing_receive_print_Detail);
                    }else {
                        cbi_pdb.setSaveCashierTBillingInvoicePrintDetail(branch.getBranchId(), cbi_pd);
                    }
                    if(vv_bis.getPatientShare()!=null) {
                        amount+=vv_bis.getPatientShare();
                    }
                }
                discount = getDeduct(branch.getBranchId(),tbiId);
                if(flag.equals("receive")) {
                    cbi_pdb.setCashierTBillingReceivePrintUpdateNettotal(invoice_print_id,amount - discount,amount,discount);
                }else{
                    cbi_pdb.setCashierTBillingInvoicePrintUpdateNettotal(invoice_print_id,amount - discount,amount,discount);
                }

                for(Integer i=0;i<=Integer.parseInt(print_page)-1;i++) {
                    Map o = new HashMap();
                    o.put("p_invoice_print_id",invoice_print_id);
                    o.put("rheader3",branch.getTaxId());
                    if(flag.equals("invoice")) {
                        o.put("header4", branch.getInvoiceNamet());
                    }else{
                        o.put("header4", branch.getReceiveNamet());
                    }
                    if(i==0) {
                        o.put("rheader1", "ต้นฉบับ");
                    } else {
                        o.put("rheader1", "สำเนา");
                    }
                    String plan="";
                    Vector v_payment = theHC.theHosDB.thePaymentDB.selectByVisitId(theHC.theHO.theVisit.getObjectId());
                    for(int k=0,size=v_payment.size();k<size;k++){
                        Payment pm = (Payment)v_payment.get(k);
                        if(pm.visit_payment_active.equals("1")) {
                            if(k==0)
                                plan = theHC.theLookupControl.readPlanString(pm.plan_kid);
                            break;
                        }
                    }
                    o.put("patient_work_office",plan);
                    //JOptionPane.showMessageDialog(null, "print_preview "+print_preview.toString());
                    if(flag.equals("receive")) {
                        boolean retp = initPrint("billing_receive",print_preview,o,conn,false);
                        cbi_pdb.setUpdateTBillingReceipt(branch.getBranchId(), doc_number, tbiId);//pop update ค่าเลขที่ใบเสร็จ ให้ hospital osv3
                    }else {
                        boolean retp = initPrint("billing_invoice",print_preview,o,conn,false);
                    }
                }
            } catch (Exception ex) {
                theUS.setStatus("การสั่งพิมพ์รายการผิดพลาด "+ex.getMessage(),UpdateStatus.ERROR);
            }
        } else{
            theUS.setStatus("ไม่พบข้อมูล",UpdateStatus.WARNING);
        }
        return "";
    }

    public void printCashierInvoice(){
        String sql="";
        CashierTBillingInvoicePrintDB invoice_printdb = new CashierTBillingInvoicePrintDB();
        try{
            Constant.println("theLO.theOption.print_cashier_invoice" );
//            getInvoicePrint("103003", "052000454",theHC.theHO.theVisit.begin_visit_time,theHC.theHO.theVisit.financial_discharge_time);
            getInvoicePrint(theHC.theHO.theVisit.vn,theHC.theHO.theVisit.begin_visit_time,"","invoice");

        } catch(Exception ex) {
            theUS.setStatus("การสั่งพิมพ์รายการผิดพลาด "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
        }
        finally {
//            theConnectionInf.close();
        }
    }
    public void printCashierReceive() {
        String sql="";
        CashierTBillingInvoicePrintDB invoice_printdb = new CashierTBillingInvoicePrintDB();
        try {

            if(theDialogBillingReceive != null){
                theDialogBillingReceive.dispose();
            }
            theDialogBillingReceive = new DialogBillingReceive();
            theDialogBillingReceive.showDialog(theHC, theUS,this);

            Constant.println("theLO.theOption.print_cashier_invoice" );
//            getInvoicePrint("103003", "052000454",theHC.theHO.theVisit.begin_visit_time,theHC.theHO.theVisit.financial_discharge_time);
//            getInvoicePrint(theHC.theHO.theVisit.vn,theHC.theHO.theVisit.begin_visit_time,theHC.theHO.theVisit.financial_discharge_time, "receive");

        } catch(Exception ex) {
            theUS.setStatus("การสั่งพิมพ์รายการผิดพลาด "+ex.getMessage(),UpdateStatus.ERROR);
            ex.printStackTrace(Constant.getPrintStream());
        }
        finally {
//            theConnectionInf.close();
        }
    }
    
    protected boolean initPrint(String filename,int valuePrint,Map o,Connection ds, boolean mode_con) throws Exception {
       JasperReport jp=null;
       String file_name = "";
       if(theHC.theLO.path_print.indexOf("C")>0) {
           file_name = theHC.theLO.path_print + "/"+filename+".xml";
       } else {
           file_name = theHC.theLO.path_print + "/"+filename+".xml";
       }
       File file = new File(file_name);
//       JOptionPane.showMessageDialog(null, "file_name "+file_name);
       if(!file.isFile()) {
           theUS.setStatus("ไม่พบไฟล์ที่ทำการสั่งพิมพ์ "+file_name,UpdateStatus.ERROR);
           return false;
       }
       try {
//           JOptionPane.showMessageDialog(null, "compile "+file_name);
           jp = JasperCompileManager.compileReport(file_name);
//           JOptionPane.showMessageDialog(null, "222222222222222 ");
       } catch(Exception e) {
           e.printStackTrace(Constant.getPrintStream());
           theUS.setStatus(e.getMessage()+" "+file_name,UpdateStatus.ERROR);
           return false;
       }
        JasperPrint jprint = null;
        if(mode_con) {
            jprint = JasperFillManager.fillReport(jp,o, theHC.theHosDB.thePrimarySymptomDB.theConnectionInf.getConnection());
        } else {
//            JOptionPane.showMessageDialog(null, "ds "+ds.getCatalog());
            if(ds!=null)
                jprint = JasperFillManager.fillReport(jp,o, ds);
            else
                jprint = JasperFillManager.fillReport(jp,o, theBCconn.getConnection());
        }
        //เธ?เธนเธ เธฒเธ?เธ?เน?เธญเธ?เธ?เธดเธกเธ?เน?
        if(valuePrint==1){
            JasperViewer.viewReport(jprint,false);
            return true;
        }
        //ยังมีปัญหาเรื่องการพิมพ์ sticker ยามีการฟีด กระดาษผิดพลาด100%
//        JOptionPane.showMessageDialog(null, "choosePrinter "+choosePrinter);
        JasperPrintManager.printReport(jprint, choosePrinter);
//        //ค้นเครื่องพิมพ์จากไฟล์
//        PrintService service = DialogChoosePrinter.readFilePrint(filename);
//        //พิมพ์เครื่องพิมพ์ default
//        Constant.println("PrintService service = DialogChoosePrinter.readFilePrint(filename);");
//        if(!choosePrinter && service==null){
//            Constant.println("if(!choosePrinter && service==null){");
//            JasperPrintManager.printReport(jprint, choosePrinter);
//            return true;
//        }
//        //เลือกเครื่องพิมพ์เอง
//        if(service==null || choosePrinter){
//            Constant.println("if(service==null || choosePrinter){");
//            service = theDialogChoosePrinter.showDialog(theUS.getJFrame(),filename);
//            if(service==null)
//                return false;
//        }
//        JasperPrintManager.printReport(jprint, choosePrinter);
	// Export the report using the JasperPrint instance
//	JRExporter exporter = new JRPrintServiceExporter();
//	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
//	exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, service.getAttributes());
//	exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
//	exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
//	exporter.exportReport();
        return true;
   }
    public boolean checkPathPrint(javax.swing.JFrame frm,boolean auto)
    {
        if(theHC.theLO.path_print != null && !theHC.theLO.path_print.equals("") && auto) {
            return true;
        }
        Constant.println("if(theLO.path_print == null){");
        ConfigPathPrinting.showDialog(frm);
        theHC.theLO.path_print = IOStream.readInputDefault(".printpath.cfg");
        Constant.println("theLO.path_print" + theHC.theLO.path_print);
        if(theHC.theLO.path_print==null)
            return false;

        String[] data = theHC.theLO.path_print.split(";");
        //เน?เธฅเธทเธญเธ?เน?เธ?เธฃเธทเน?เธญเธ?เธ?เธดเธกเธ?เน?เธกเธฑเน?เธข  เธ?เน?เธฒ false เธ?เธทเธญ เน?เธกเน?
        if(!data[0].equals(Active.isEnable()))
//            choosePrinter = false;
//        else
//            choosePrinter = true;

        if(!data[1].equals(""))
            theHC.theLO.path_print = data[1];

        return true;
    }
    private CashierTBillingReceivePrint CashInvoiceToReceipt(CashierTBillingInvoicePrint invoice){
        CashierTBillingReceivePrint receipt = new CashierTBillingReceivePrint();
        receipt.setBillingInvoiceDate(invoice.getBillingInvoiceDate());
        receipt.setAmount(invoice.getAmount());
        receipt.setBillingInvoiceNo(invoice.getBillingInvoiceNo());
        receipt.setDischargeDate(invoice.getDischargeDate());
        receipt.setDischargeTime(invoice.getDischargeTime());
        receipt.setDiscount(invoice.getDiscount());
        receipt.setInjuryNo(invoice.getInjuryNo());
        receipt.setNettotal(invoice.getNettotal());
        receipt.setPatientAddress(invoice.getPatientAddress());
        receipt.setPatientAge(invoice.getPatientAge());
        receipt.setPatientCompanyName(invoice.getPatientCompanyName());
        receipt.setPatientFullnamet(invoice.getPatientFullnamet());
        receipt.setPatientPid(invoice.getPatientPid());
        receipt.setSickness(invoice.getSickness());
        receipt.setTBillingInvoiceId(invoice.getTBillingInvoiceId());
        receipt.setTBillingReceivePrintActive(invoice.getTBillingInvoicePrintActive());
        receipt.setTBillingReceivePrintDate(invoice.getTBillingInvoicePrintDate());
        receipt.setTBillingReceivePrintId(invoice.getTBillingInvoicePrintId());
        receipt.setTVisitHn(invoice.getTVisitHn());
        receipt.setTVisitId(invoice.getTVisitId());
        receipt.setTVisitVn(invoice.getTVisitId());
        receipt.setVisitDate(invoice.getVisitDate());
        receipt.setVisitTime(invoice.getVisitTime());
        receipt.setVisitTypeDescription(invoice.getVisitTypeDescription());
        receipt.setReceiveNumber(invoice.getInvoiceNumber());
        return receipt;
    }
    private CashierTBillingReceivePrintDetail CashInvoiceDetailToReceiveDetail(CashierTBillingInvoicePrintDetail item){
        CashierTBillingReceivePrintDetail receive_detail = new CashierTBillingReceivePrintDetail();
        receive_detail.setCode(item.getCode());
        receive_detail.setName1(item.getName1());
        receive_detail.setNode(item.getNode());
        receive_detail.setPrice(item.getPrice());
        receive_detail.setRow1(item.getRow1());
        receive_detail.setSortLine1(item.getSortLine1());
        receive_detail.setSortLine2(item.getSortLine2());
        receive_detail.setSortLine3(item.getSortLine3());
        receive_detail.setSortLine4(item.getSortLine4());
        receive_detail.setSortLine5(item.getSortLine5());
        receive_detail.setTBillingReceivePrintDetailId(item.getTBillingInvoicePrintDetailId());
        receive_detail.setTBillingReceivePrintId(item.getTBillingInvoicePrintId());
        return receive_detail;
    }
    public void setVisitContact(String t_visit_id, String contact_id, String contact_namet){
        String sql="Update t_visit Set contact_id = '"+contact_id+"', contact_namet = '"+contact_namet+"' Where t_visit_id = '"+t_visit_id+"'";
        try {
            theHC.theConnectionInf.open();
            theHC.theConnectionInf.eUpdate(sql);
            theHC.theConnectionInf.close();
        } catch (Exception ex) {
            Logger.getLogger(HosDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
    }
}

    