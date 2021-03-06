package org.sjsu.cmpe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
    @RequestMapping("/index")
    public String getIndex() {
        
        return "zillow_home";
    }
    
    @RequestMapping("/rating")
    public String getRating() {
        
        return "zillow_rate";
    }

}