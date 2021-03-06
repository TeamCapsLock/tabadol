package com.example.tabadol.contoller;

import com.example.tabadol.JsonClasses.LoginForm;
import com.example.tabadol.JsonClasses.ResponseJson;
import com.example.tabadol.model.Feedback;
import com.example.tabadol.repository.FeedbackRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class FeedbackController {

    @Autowired
    FeedbackRepository feedbackRepository;


    @PostMapping("/sendFeedback")
    public RedirectView saveFeedback(String name, String email, String subject, String message, Model model){
        System.out.println("inside feedback");
        Feedback feedback = new Feedback(name, email, subject, message);
        try{
            feedbackRepository.save(feedback);
            System.out.println("feedback added");
            // TODO: add a message to tell the user that feed back sent
            model.addAttribute("confirmSend",true);

        }catch (Exception e){
            System.out.println(e.getMessage());
            model.addAttribute("confirmSend",false);
        }
        return new RedirectView("/#contact");
    }


    @GetMapping("/testgetjson")
    @ResponseBody
    public ResponseJson testGet(@RequestBody LoginForm login){

        return new ResponseJson("Get: You've entered: username: "+login.getUsername()+"  password:"+login.getPassword());

    }

    @PostMapping("/testpostjson")
    @ResponseBody
    public ResponseJson testPost(@RequestBody LoginForm login){

        return new ResponseJson("Post: You've entered: username: "+login.getUsername()+"  password:"+login.getPassword());

    }









}
