package com.example.web.validator;

import com.example.web.conf.ent.User;
import com.example.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(userService.userByName(user.getUsername())!=null){
            errors.rejectValue("username","validation","такой логин уже есть");
        }

        if(user.getPassword().length() < 5){
            errors.rejectValue("username","validation","слишком короткий пароль");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("username","validation","пароли не совпадают");
        }
    }
}
