package org.sjsu.cmpe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping("/home")
    public String greeting() {
        return "zillow_home";
    }
    
//    @RequestMapping("/index")
//    public String index() {
//        return "index";
//    }
    
    @RequestMapping("left-sidebar")
    public String leftSidebar() {
        return "left-sidebar";
    }

    
}