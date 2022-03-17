package com.otopark.presentation.controller;

import com.otopark.business.service.UserService;
import com.otopark.presentation.controller.dto.ResponseDTO;
import com.otopark.presentation.controller.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/user")
@CrossOrigin(origins = "http://localhost:3005")
public class UserController {
    @Autowired
    UserService userService;

    private static Logger log = LoggerFactory.getLogger(UserController.class);


    @GetMapping(path = "")
    @PreAuthorize("hasAuthority('PER_USERS')")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> userList() {
        List<UserDTO> userDTOS = userService.findAll().stream()
                .map(user -> {
                    return new UserDTO(user);
                }).collect(Collectors.toList());

        ResponseDTO<List<UserDTO>> responseDTO = new ResponseDTO<List<UserDTO>>("SUCCESS", userDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


/*    @RequestMapping(value = "", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> listFullAccounts()*/


}
