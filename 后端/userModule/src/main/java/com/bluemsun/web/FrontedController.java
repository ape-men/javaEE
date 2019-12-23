package com.bluemsun.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FrontedController {
    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    private String login() {
        return "dist/login";
    }
    @RequestMapping(value ="/PersonPage",method = RequestMethod.GET)
    private String register(){
        return "dist/personPage";
    }
    @RequestMapping(value = "/Vaildate",method = RequestMethod.GET)
    private String vaildate(){
        return "dist/vaildate";
    }
}
