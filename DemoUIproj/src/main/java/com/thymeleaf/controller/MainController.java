package com.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.thymeleaf.entities.EmpEntity;
import com.thymeleaf.services.EmpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class MainController {

    @Autowired
    private EmpService empService;


    @GetMapping("/home")
    public String homeHandler() {
        return "home";
    }

    @GetMapping("/home/add")
    public String addDataHandler( Model model) {
        EmpEntity empObj=new EmpEntity();
        model.addAttribute("empObj",empObj);
        return "addDataForm";
    }


    @PostMapping("/emp")
    public String emp(@Valid @ModelAttribute("empObj")  EmpEntity empObj, RedirectAttributes redirectAttributes) {

        EmpEntity empEmail=this.empService.findEmpByEmail(empObj.getEmail());
        System.out.println(empEmail);
        if (this.empService.validName(empObj.getName())){
            redirectAttributes.addFlashAttribute("nameError","This isn't valid. It accepts only alphabets");
            return "redirect:/home/add";
        }
        else if(empEmail != null){
            redirectAttributes.addFlashAttribute("emailError","You cannot use this email!");
            return "redirect:/home/add";
        }
        else if (this.empService.validPass1(empObj.getPassword())){
            redirectAttributes.addFlashAttribute("passError","password must be accepts 8 characters.");
            return "redirect:/home/add";
        }
        else if (this.empService.validPass2(empObj.getPassword())){
            redirectAttributes.addFlashAttribute("passError","It Contains at least one upper case letter,one lower case letter,one digit,one special character!");
            return "redirect:/home/add";
        }
        else {
            this.empService.addEmp(empObj);
            redirectAttributes.addFlashAttribute("msg","data added successfully");
            return "redirect:/home/list";
        }
    }

    @GetMapping("/home/list")
    public String listDataHandler(Model model) {
        model.addAttribute("empList",this.empService.findAllEmp());
        return "listData";
    }

    @GetMapping("/update/{id}")
    public String updateDataHandler(@PathVariable("id") int id, Model model) {
        model.addAttribute("empObj",this.empService.findEmpById(id));
        return "updateDataForm";
    }

    @PostMapping("/emp/{id}")
    public String saveDataHandler(@Valid @PathVariable("id") int id, @ModelAttribute("empObj") EmpEntity empObj, RedirectAttributes redirectAttributes) {
        EmpEntity empUpdate=this.empService.findEmpById(id);
        empUpdate.setId(id);
        empUpdate.setName(empObj.getName());
        empUpdate.setEmail(empObj.getEmail());
        empUpdate.setPassword(empObj.getPassword());

//        System.out.println("getEmail(): ========================="+empObj.getEmail()+"=========================");
//        System.out.println("getPassword(): ========================="+empObj.getPassword()+"=========================");

        EmpEntity empEmail=this.empService.findEmpByEmail(empObj.getEmail());
        if (this.empService.validName(empObj.getName())) {
            redirectAttributes.addFlashAttribute("nameError", "This isn't valid. It accepts only alphabets");
            return "redirect:/update/{id}";
        }
        else if(empEmail != null && empEmail!=empUpdate){
            redirectAttributes.addFlashAttribute("emailError","You cannot use this email!");
            return "redirect:/update/{id}";
        }
        else if (this.empService.validPass1(empObj.getPassword())){
            redirectAttributes.addFlashAttribute("passError","password must be accepts 8 characters.");
            return "redirect:/update/{id}";
        }
        else if (this.empService.validPass2(empObj.getPassword())){
            redirectAttributes.addFlashAttribute("passError","It Contains at least one upper case letter,one lower case letter,one digit,one special character!");
            return "redirect:/update/{id}";
        }
        else {
            this.empService.addEmp(empObj);
            redirectAttributes.addFlashAttribute("msg","data updated successfully");
            return "redirect:/home/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDataHandler(@PathVariable("id") int id,RedirectAttributes redirectAttributes) {
        this.empService.deleteEmp(id);
        redirectAttributes.addFlashAttribute("msg","data deleted");
        return "redirect:/home/list";
    }
}
