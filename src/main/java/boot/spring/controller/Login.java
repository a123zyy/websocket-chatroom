package boot.spring.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import boot.spring.po.Staff;
import boot.spring.service.LoginRedisTemplateService;
import com.mysql.cj.log.Log;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import boot.spring.po.MSG;
import boot.spring.po.User;
import boot.spring.service.LoginService;

import java.io.Console;
import java.util.Objects;


@Slf4j
@Controller
public class Login {
	@Autowired
	LoginService loginservice;

    @Autowired
    private LoginRedisTemplateService loginRedisTemplateService;

	//登录前校验是否重复登录
	@RequestMapping("/loginvalidate")
	public String loginvalidate(@RequestParam("username") String username,@RequestParam("password") String pwd,HttpSession httpSession){
	    if(username == null || pwd == null){
            return "login";
        }
        //第一次登陆redisUid sessionid null
        Staff realpwd=loginservice.getpwdbyname(username);
         String redisUid =  loginRedisTemplateService.getUserid(realpwd.getStaff_id());
         System.out.println("我的Uid "+realpwd.getStaff_id());
        if((realpwd!=null&&pwd.equals(realpwd.getPassword())) && redisUid == null) {
               httpSession.setAttribute("uid",realpwd.getStaff_id());
               loginRedisTemplateService.setUserid(realpwd.getStaff_id(),httpSession.getId());
            return "chatroom";
         } else if ((realpwd!=null&&pwd.equals(realpwd.getPassword())) && redisUid.equals(realpwd.getStaff_id()+"_"+httpSession.getId())){
             httpSession.setAttribute("uid",realpwd.getStaff_id());
             loginRedisTemplateService.setUserid(realpwd.getStaff_id(),httpSession.getId());
             return "chatroom";
         }
         return "fail";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession httpSession){
	    if (Objects.nonNull(httpSession.getAttribute("uid"))){
            Integer uid = (Integer) httpSession.getAttribute("uid");
            log.info("这里是uid", uid);
            loginRedisTemplateService.delUserid(uid);
        }
	    return "login";
	}
	
	@RequestMapping(value="/currentuser",method = RequestMethod.GET)
	@ResponseBody
	public User currentuser(HttpSession httpSession){
        Integer uid = (Integer)httpSession.getAttribute("uid");
		String name = loginservice.getnamebyid(uid);
		return new User(uid, name);
	}


  }
