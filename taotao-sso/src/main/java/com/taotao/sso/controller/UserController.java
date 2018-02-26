package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user")
public class UserController {

    /**
     * 注册 
     * 
     * @return
     */
    @RequestMapping(value = "register" , method = RequestMethod.GET)
    public String register() {
        return "register";
    }
    
    
    
    
    
}
