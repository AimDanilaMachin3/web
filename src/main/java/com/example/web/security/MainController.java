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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

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

    @GetMapping("/car/{id}")
    public String catalog(@PathVariable Long id, Model model) {
        model.addAttribute("car", carService.findByCarById(id));
        return "car";
    }

    @GetMapping("/addCar")
    public String addCar(Model model) {
        model.addAttribute("newCar",new Car());
        return "addCar";
    }

    @PostMapping("/addCar")
    public String addCar(@ModelAttribute("newCar") Car car) {
        carService.saveCar(car);
        return "redirect:/catalog";
    }

    @GetMapping("/car/{id}/delete")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCarById(id);
        return "redirect:/catalog";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        User user = userService.userByName(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping("/exit")
    public String logout() {
        return "exitPanel";
    }
    @GetMapping("/profile/edit")
    public String editProfilePage(Model model, Principal principal) {
        User user = userService.userByName(principal.getName());
        model.addAttribute("user", user);
        return "editProfile";
    }

    @PostMapping("/profile/edit")
    public String editProfilePage(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            bindingResult.addError(
                    new FieldError("user", "confirmPassword", "Passwords don't matches. Enter password again!"));
            return "editProfile";
        } else {
            userService.updatePassword(user);
            return "profile";
        }
    }

    @RequestMapping("/catalog/search")
    public String findBuQuery(@RequestParam("query") String query, @PageableDefault(value = 5) Pageable pageable, Model model) {
        query = query.toLowerCase();
        List<Car> cars = carService.findAllByModel(query);
        model.addAttribute("cars", cars);
        return "result";
    }

}
