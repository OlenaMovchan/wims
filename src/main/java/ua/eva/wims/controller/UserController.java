package ua.eva.wims.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.eva.wims.dto.UserDto;
import ua.eva.wims.service.UserInfoService;

@CrossOrigin
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "basicAuth")
@RequestMapping()
public class UserController {

    private UserInfoService service;

    @PostMapping("/addEmployee")

    public UserDto addEmployee(@RequestBody UserDto userInfo) {
        return service.addEmployee(userInfo);
    }

}
