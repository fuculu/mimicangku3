package com.jk.controller.PageController;

import com.jk.model.Pl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("page")
public class ControllerPage {
    @RequestMapping("toindex")
    public String toindex(){
        return "index";
    }
    @RequestMapping("toshow")
    public String toshow(){
        return "show";
    }
    @RequestMapping("tohx")
    public String tohx(String gid, Model model){
        model.addAttribute("aa",gid);
        return "huxian";
    }
}
