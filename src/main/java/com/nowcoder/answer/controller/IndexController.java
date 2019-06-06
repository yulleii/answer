package com.nowcoder.answer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import java.util.*;

@Controller
public class IndexController {
//    @RequestMapping(path={"/","index"})
//    @ResponseBody
//    public String index(HttpSession httpSession){
//        System.out.println("hello index");
//        return "hello NowCoder"+ httpSession.getAttribute("msg");
//    }
//
//    @RequestMapping(path={"/profile/{groupName}/{userId}"})
//    @ResponseBody
//    public String profile(@PathVariable("userId")int userId,
//                          @PathVariable("groupName")String groupName,
//                          @RequestParam(value="type",required = false)Integer type,
//                          @RequestParam(value = "key",required =false)String key){
//        return String.format("Profile page of %d ,%s,%d,%s",userId,groupName,type,key);
//    }

    @RequestMapping(path={"/vm"},method ={RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1","vvvv1");
        List<String> colors= Arrays.asList(new String[]{"RED","GREEN","BLUE"});
        model.addAttribute("colors",colors);
        boolean flag=true;
        model.addAttribute("flag",flag);
        Map<String,String> map=new HashMap<>();
        for (int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);
        return "vm";
    }

    @RequestMapping(path={"/request"},method ={RequestMethod.GET})
    @ResponseBody
    public String template(Model model, HttpServletResponse response,
                           HttpServletRequest request,
                           HttpSession httpSeesion){
        StringBuilder sb=new StringBuilder();
        Enumeration<String>headerNames=request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        if(request.getCookies()!=null){
            for(Cookie cookie :request.getCookies()){
                sb.append("Cookie:"+cookie.getName()+"value:"+cookie.getValue());
            }
        }
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getRequestURI()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRemoteHost()+"<br>");
        sb.append("tostring:"+request.toString());

        response.addHeader("nowcoderId","hello");
        response.addCookie(new Cookie("userName","nowcoder"));
        String host=request.getRemoteHost();
        if(host.equals("127.0.0.1")||host.equals("0:0:0:0:0:0:0:1")){
            sb.append("true");
        }else{
            sb.append("false");
        }
        return sb.toString();
    }

    @RequestMapping (path={"/redirect/{id}"},method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("id")int id,
                                 HttpSession httpSession){
        httpSession.setAttribute("msg","hello session1");
        RedirectView red=new RedirectView("/",true);
        if(id==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }
    @RequestMapping (path={"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key")String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数错误");

    }
    @RequestMapping(path={"/login"},method={RequestMethod.GET})
    public String login(){
        return "login";
    }

    @RequestMapping(path={"/letter"},method={RequestMethod.GET})
    public String letter(){
        return "letter";
    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error:"+e.getMessage();
    }

}
