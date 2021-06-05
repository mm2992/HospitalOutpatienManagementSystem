package com.graduate.hospital.web;
import com.graduate.hospital.model.Doctor;
import com.graduate.hospital.model.DrugCategory;
import com.graduate.hospital.model.Manufacturer;
import com.graduate.hospital.model.User;
import com.graduate.hospital.service.DoctorService;
import com.graduate.hospital.service.DrugCategoryService;
import com.graduate.hospital.service.ManufacturerService;
import com.graduate.hospital.service.UserService;
import com.graduate.hospital.vo.DoctorVo;
import com.graduate.hospital.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller

@RequestMapping("/")
public class JumpController {
    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    DrugCategoryService drugCategoryService;
    @Autowired
    ManufacturerService manufacturerService;

//    跳转到首页
    public ModelAndView toIndex(){

        return new ModelAndView("index");
    }

//    跳转到自助页面
    @RequestMapping("self")
    public ModelAndView toSelfIndex(){
        return new ModelAndView("selfService/guaHao/index");
    }

//    跳转到在线挂号挂号页面
    @RequestMapping("guahao")
    public ModelAndView toGuaHaoIndex(){
        return new ModelAndView("selfService/guaHao/register");
    }

//    跳转到查看挂号信息页面
    @RequestMapping("watchGuaHaoInfo")
    public ModelAndView toRegisterInfo(){
        return new ModelAndView("selfService/guaHao/registerInfo");
    }

//    跳转到登录页面
    @ResponseBody
    @RequestMapping("login")
    public Object toLogin(HttpServletRequest request, User user){
        HttpSession session=request.getSession();
//        首先到user中查找
        User user1=userService.queryUser(user);
            if (user1==null){
                Doctor doctor=new Doctor();
                String doctorId=user.getUserId();
                String password=user.getPassword();
                doctor.setDoctorId(doctorId);
                doctor.setPassword(password);
                Doctor doctor1=doctorService.queryDoctor(doctor);
                if (doctor1==null){
                    return 0;
                }else {
                    session.setAttribute("user",doctor1);
                    return 4;
                }
            }else {
                session.setAttribute("user",user1);
                    return user1.getUserCategory();
            }

    }

//    跳转到登录首页
    @RequestMapping("loginIndex")
    public ModelAndView toLoginIndex(HttpSession session){
        return new ModelAndView("login/index");
    }

//    跳转到管理员页面
    @RequestMapping("managerIndex")
    public ModelAndView toManagerIndex(){
        return new ModelAndView("login/manager/managerFirst");
    }

//    跳转到添加用户
    @RequestMapping("addUser")
    public ModelAndView toAddUser(){
        return new ModelAndView("login/manager/addUser");
    }

    //    跳转到编辑详细信息页面
    @RequestMapping("editUser")
    public ModelAndView toEditUser(HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
//        System.out.println(user.getUserId());
        ModelAndView model=new ModelAndView();
        UserVo userVo=userService.seltctUserInfo(user.getUserId());
//        System.out.println(userVo);
        model.addObject("userVo",userVo);
        model.setViewName("login/manager/editUser");
        return model;
    }

    //跳转回登录进来的页面
    @RequestMapping("warningPage")
    public ModelAndView toWarningPage(){
        return new ModelAndView("login/manager/warningPage");
    }

    //跳转到查看用户信息模块
    @RequestMapping("viewUser")
    public ModelAndView toViewUser(){
        return new ModelAndView("login/manager/viewUser");
    }

    //跳转到添加医生的页面
    @RequestMapping("addDoctor")
    public String toAddDoctor(){
        return "login/manager/addDoctor";
    }

    //跳转到查看医生信息的页面
    @RequestMapping("viewDoctor")
    public String toViewDoctor(){
        return "login/manager/viewDoctor";
    }

    //跳转到库房管理员操作页面
    @RequestMapping("wareHouseIndex")
    public String toWareHouse(){
        return "login/wareHouse/index";
    }

    //跳转到录入药品信息界面
    @RequestMapping("enterDrug")
    public String toEnterDrug(){
        return "login/wareHouse/enterDrug";
    }

    //跳转到订购药品信息界面
    @RequestMapping("orderDrug")
    public String toOrderDrug(Model model){
        List<DrugCategory> drugCategoryList=drugCategoryService.getAllCategory();
        model.addAttribute("drugCategories",drugCategoryList);
        return "login/wareHouse/orderDrug";
    }

    //跳转到退货 界面
    @RequestMapping("returnDrug")
    public String toReturnDrug(Model model){
        List<DrugCategory> drugCategoryList=drugCategoryService.getAllCategory();
        List<Manufacturer> allManufacturer = manufacturerService.getAllManufacturer();
        model.addAttribute("drugCategories",drugCategoryList);
        model.addAttribute("allManufacturer",allManufacturer);
        return "login/wareHouse/viewDrug";
    }

    /**
     * 跳转到监测药品的页面
     * @return
     */
    @RequestMapping("monitorDrug")
    public String toMonitorDrug(Model model){
        List<Manufacturer> allManufacturer = manufacturerService.getAllManufacturer();
        model.addAttribute("allManufacturer",allManufacturer);
        return "login/wareHouse/monitorDrug";
    }

    /**
     * 跳转到医生操作的首页
     * @return
     */
    @RequestMapping("doctorIndex")
    public String toDoctorIndex(){
        return "login/doctor/doctorFirst";
    }

/**
 * 跳转到编辑医生资料的页面
 */
    @RequestMapping("editDoctor")
    public String toEditDoctor(HttpSession session){
        Doctor doctor=(Doctor) session.getAttribute("user");
        DoctorVo doctorVo=doctorService.getDoctorVoById(doctor.getDoctorId());
        session.setAttribute("doctor",doctorVo);
        return "login/doctor/editDoctor";
    }

//    跳转到医生看病的页面
    @RequestMapping("treat")
    public String toTreat(Model model){
        List<DrugCategory> allCategory = drugCategoryService.getAllCategory();
        model.addAttribute("categories",allCategory);
        return "login/doctor/treat";
    }

    //跳转到支付页面
    @RequestMapping("pay")
    public String toPay(){
        return "/selfService/guaHao/pay";
    }

    /**
     * 跳转到退费的页面
     * @return
     */
    @RequestMapping("refund")
    public String toRefund(){
        return "/selfService/guaHao/refund";
    }

    /**
     * 跳转到充值页面
     * @return
     */
    @RequestMapping("reCharge")
    public String toReCharge(){
        return "/selfService/guaHao/recharge";
    }

    /**
     * 跳转到药方护士的页面
     */
    @RequestMapping("nurseIndex")
    public String toNurseIndex(){
        return "/login/salesman/index";
    }

    /**
     * 跳转到取药的页面
     * @return
     */
    @RequestMapping("takeMedicine")
    public String toTakeMedicine(){
        return "/login/salesman/quyao";
    }

    /**
     * 跳转到打印就诊信息的页面
     * @return
     */
    @RequestMapping("printMsg")
    public String printMsg(){
        return "/login/salesman/printMsg";
    }

    /**
     * 跳转到查询出库记录的页面
     * @return
     */
    @RequestMapping("outDrug")
    public String toOuDrug(){
        return "/login/wareHouse/outRecord";
    }
}
