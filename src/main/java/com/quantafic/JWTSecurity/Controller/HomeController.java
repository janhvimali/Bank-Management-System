package com.quantafic.JWTSecurity.Controller;



import com.quantafic.JWTSecurity.Repo.UserRepo;
import com.quantafic.JWTSecurity.Service.BureauServices;
import com.quantafic.JWTSecurity.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    UserRepo repo;

    @Autowired
    UserService userService;

    @Autowired
    BureauServices bureauServices;


}
