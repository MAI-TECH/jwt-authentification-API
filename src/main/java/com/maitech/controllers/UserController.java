package com.maitech.controllers;

import com.maitech.dtos.UserDto;
import com.maitech.models.UserModel;
import com.maitech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/authentification/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        UserDto userDto = new UserDto();
        List<UserDto> userDtos = new ArrayList<>();

        userService.findAllUsers().forEach(user -> userDtos.add(userDto.fromUserModel(user)));

        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable() String id) {
        UserDto userDto = new UserDto();
        UserModel userModel = userService.findUserById(id);

        return ResponseEntity.status( (userModel != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND).body( (userModel != null) ? userDto.fromUserModel(userModel) : null);
    }

    @RequestMapping("/phone")
    public ResponseEntity<UserDto> findUserByPhone(@RequestParam("query") int phone) {
        UserDto userDto = new UserDto();
        UserModel userModel = userService.findUserByPhone(phone);

        return ResponseEntity.status( (userModel != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND).body( (userModel != null) ? userDto.fromUserModel(userModel) : null);
    }

    @RequestMapping("/email")
    public ResponseEntity<UserDto> findUserByEmail(@RequestParam("query") String email) {
        UserDto userDto = new UserDto();
        UserModel userModel = userService.findUserByEmail(email);

        return ResponseEntity.status( (userModel != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND).body( (userModel != null) ? userDto.fromUserModel(userModel) : null);
    }

    @RequestMapping("/username")
    public ResponseEntity<UserDto> findUserByUsername(@RequestParam("query") String username) {
        UserDto userDto = new UserDto();
        UserModel userModel = userService.findUserByUsername(username);

        return ResponseEntity.status( (userModel != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND).body( (userModel != null) ? userDto.fromUserModel(userModel) : null);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadAvatar(@PathVariable() String id) {
        UserDto userDto = new UserDto();
        userDto.fromUserModel(userService.findUserById(id));

        if (userDto != null) {
            if (userDto.getFormat()!= null)
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(userDto.getFormat())).body(new ByteArrayResource(userDto.getAvatar()));
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody() UserDto userDto) {
        UserDto user = new UserDto();

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(user.fromUserModel(userService.saveUser(userDto.toUserModel())));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadAvatar(@PathVariable() String id, @RequestParam("file") MultipartFile file) {
        try {
            boolean isSave = userService.uploadAvatar(id, file);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable() String id) {
        return (userService.deleteUserById(id)) ? ResponseEntity.status(HttpStatus.OK).body(null) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/phone")
    public ResponseEntity<?> deleteUserByPhone(@RequestParam() int phone) {
        return (userService.deleteUserByPhone(phone)) ? ResponseEntity.status(HttpStatus.OK).body(null) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/email")
    public ResponseEntity<?> deleteUserByEmail(@RequestParam() String email) {
        return (userService.deleteUserByEmail(email)) ? ResponseEntity.status(HttpStatus.OK).body(null) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/username")
    public ResponseEntity<?> deleteUserByUsername(@RequestParam() String username) {
        return (userService.deleteUserByUsername(username)) ? ResponseEntity.status(HttpStatus.OK).body(null) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
