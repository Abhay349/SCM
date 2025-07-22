package com.project1.shriganeshaynamah.Controllers;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project1.shriganeshaynamah.Dao.contacc;
import com.project1.shriganeshaynamah.Dao.userDao;
import com.project1.shriganeshaynamah.helper.Msg;
import com.project1.shriganeshaynamah.user.Contact;
import com.project1.shriganeshaynamah.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class cont1 {
    @Autowired
    private PasswordEncoder PasswordEncoder;
    @Autowired
    private userDao sms;
    @Autowired
    private contacc ck;
   
    
    @RequestMapping("/some")
    public String getMethodName() {
        return "base";
    }
    @RequestMapping("/")
    public String requestMethodName() {
        return "home";
    }
    @GetMapping("/signin")
    public String Sts(){
        return "login";
    }
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    @RequestMapping("/signup")
    public String requestName(Model ms) {
       
        ms.addAttribute("user", new User());

        return "signup";
    }
  
@GetMapping("/user/index")
   public String getmethodName(Model md,Principal pc) {
       String ss=pc.getName();
       System.out.println(ss);
       User us=sms.findByName(ss);
       md.addAttribute("user",us);
       return "user/index";
   }
   @GetMapping("/addcontact")
   public String getMethName() {
       return "user/add-contact";
   }
   
    @PostMapping("/regis")
    public String postMethodName(@ModelAttribute User user,@RequestParam(value="agreement",defaultValue="false") boolean agreement,Model md){

       try {
         if(!agreement){
            System.out.println("Please agree to terms!!");
            throw new Exception("Please agree to terms!!");
        }
        user.setRole("Majdoor");
        user.setEnable("true");
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        System.out.println("User:-"+user);
        
        User usa=sms.save(user);
        md.addAttribute("user", usa);
        md.addAttribute("message", new Msg("successfully reistered!", "alert-success"));
        return "signup";
       } catch (Exception e) {

        md.addAttribute("user",user);
        md.addAttribute("message", new Msg("Something Wrong!!", "alert-danger"));
        return "signup";

       }
      
    }
    @PostMapping("user/a-contact")
    public String postMethodName(@ModelAttribute Contact contact,Principal pc) {
        String smo=pc.getName();
      
        User us=sms.findByName(smo);
        
        //   us.getCn().add(contact);
        //   sms.save(us);
            contact.setUs(us);
            ck.save(contact);
        //TODO: process POST request
        System.out.println("data is"+contact);
        System.out.println("Added to database");
        return "user/add-contact";
    }
    
   @GetMapping("/user/show/{page}")
   public String postMethod(@PathVariable("page") Integer page, Model md,Principal pc) {
      String na = pc.getName();
      User us=sms.findByName(na);
     Pageable st= PageRequest.of(page,2);
      Page<Contact> ss=ck.findByUs(us,st);
      md.addAttribute("contacts", ss);
      md.addAttribute("nopage", ss.getTotalPages());
      md.addAttribute("currpage", page);
       
       return "user/scontact";
   }
   @GetMapping("/user/{cid}/ind")
   public String getMethodNa(@PathVariable("cid") Integer cid,Model md,Principal pc) {
    String nm=pc.getName();
    User us=sms.findByName(nm);
    Optional<Contact> ss=ck.findById(cid);
    Contact cn=ss.get();
    System.out.println(cn.getDescription());
    if(us.getId()==cn.getUs().getId()){
    md.addAttribute("con",cn);
    }
       return "user/ind";
   }
   @GetMapping("/user/delete/{cid}")
   public String getName(@PathVariable("cid") Integer cid,Principal pc,HttpSession hs) {
    //    String nm=pc.getName();
    //    User us=sms.findByName(nm);
    //   Optional<Contact> cs= ck.findById(cid);
    // //    Page<Contact> ss=ck.findByUs(us);
    //    Contact cn=cs.get();
    //    cn.setUs(null);
    //    if(us.getId()==cn.getUs().getId()){
    //     ck.delete(cn);
    //    }


    //    return "Redirect:/user/show/0";
      String username = pc.getName();
    User us = sms.findByName(username);

    Contact cn = ck.findById(cid).get();
    // cn.setUs(null);
    us.getCn().remove(cn);
     sms.save(us);
     hs.setAttribute("message", new Msg("Deleted Succesfully","success"));
  

    return "redirect:/user/show/0";
   }
   @PostMapping("/user/upd/{cid}")
   public String postMethodName(@PathVariable("cid") Integer cid,Model md) {
    Contact cs=ck.findById(cid).get();
    md.addAttribute("con", cs);
     md.addAttribute("cp", cs);
       
       return "user/update-page";
   }

   @PostMapping("/user/upt")
   public String postMethodName(@ModelAttribute("cp") Contact cp,HttpSession hs,Principal pc) {
      User us = sms.findByName(pc.getName());

    Optional<Contact> optionalContact = ck.findById(cp.getCid());
    if (optionalContact.isEmpty()) {
        hs.setAttribute("message", new Msg("Contact not found", "danger"));
        return "redirect:/user/show/0";
    }

    Contact old = optionalContact.get();
   

    // Update fields
    old.setName(cp.getName());
    old.setEmail(cp.getEmail());
    old.setPhone(cp.getPhone());
    old.setWork(cp.getWork());
    old.setDescription(cp.getDescription());
    old.setImage(cp.getImage()); // Handle image only if updated via form
    old.setUs(us);

    ck.save(old);

    hs.setAttribute("message", new Msg("Updated successfully", "success"));
    return "redirect:/user/" + cp.getCid() + "/ind";
       

     
   }
   
   
   
   
}

    
    
    
    
    

