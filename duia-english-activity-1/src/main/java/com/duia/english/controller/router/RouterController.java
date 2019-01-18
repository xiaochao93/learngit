package com.duia.english.controller.router;

import com.duia.english.common.annotation.IgnoreValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by liuhao on 2018/4/12.
 */
@Controller
public class RouterController {
    @ResponseBody
    @IgnoreValidation
    @GetMapping("/test")
    public String index1() {
        return "ok";
    }

    /**
     * app 页面
     * @param directory
     * @param name
     * @return
     */
    @IgnoreValidation
    @GetMapping("/page/app/{directory}/{name}")
    public String appDirectoryName(@PathVariable String directory, @PathVariable String name, Model model, HttpServletRequest req) {
        return webDirectoryName("app",directory,name,model,req);
    }


    @IgnoreValidation
    @GetMapping("/page/wap/{directory}/{name}")
    public String wapDirectoryName(@PathVariable String directory, @PathVariable String name,  Model model, HttpServletRequest req) {
        return webDirectoryName("wap",directory,name,model,req);
    }

    @IgnoreValidation
    @GetMapping("/page/web/{directory}/{name}")
    public String webDirectoryName(String client,@PathVariable String directory,@PathVariable String name,Model model, HttpServletRequest req) {
        if(client==null){
            client="web";
        }
        Enumeration<String> en = req.getParameterNames();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            model.addAttribute(key, req.getParameter(key));
        }
        return client+"/"+directory+"/"+name;
    }





}
