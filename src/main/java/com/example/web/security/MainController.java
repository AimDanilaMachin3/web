package com.example.web.security;

import com.example.web.conf.ent.Car;
import com.example.web.conf.ent.User;
import com.example.web.services.CarService;
import com.example.web.services.UserService;
import com.example.web.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final CarService carService;

    private final UserValidator userValidator;

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String reg(@ModelAttribute("userForm") User user,
                      BindingResult bindingResult, HttpServletRequest request) throws ServletException {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return "registration";
        } else {
            String password = user.getPassword();
            userService.save(user);
            request.login(user.getUsername(), password);
            return "redirect:/catalog";
        }
    }

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = "Ошибка в логине/пароле";
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @RequestMapping(value = {"/login", "/"})
    public String login() {
        return "login";
    }

    @GetMapping("/catalog")
    public String catalog(Model model, @PageableDefault(value = 8) Pageable pageable) {
        Page<Car> carList = carService.getAllCars(pageable);
        model.addAttribute("carList", carList);
        return "catalog";
    }

}
