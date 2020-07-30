package com.maitech.controllers;

import com.maitech.dtos.RoleDto;
import com.maitech.models.RoleModel;
import com.maitech.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/authentification/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<RoleDto>> findAllRoles() {
        RoleDto roleDto = new RoleDto();
        List<RoleDto> roleDtos = new ArrayList<>();

        roleService.findAllRoles().forEach(role -> roleDtos.add(roleDto.fromRoleModel(role)));

        return ResponseEntity.status(HttpStatus.OK).body(roleDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findRoleById(@PathVariable() String id) {
        RoleDto roleDto = new RoleDto();
        RoleModel roleModel = roleService.findRoleById(id);

        return ResponseEntity.status( (roleModel != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND).body( (roleModel != null) ? roleDto.fromRoleModel(roleModel) : null);
    }

    @GetMapping("/name")
    public ResponseEntity<RoleDto> findRoleByName(@RequestParam("query") String name) {
        RoleDto roleDto = new RoleDto();
        RoleModel roleModel = roleService.findRoleByName(name);

        return ResponseEntity.status( (roleModel != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND).body( (roleModel != null) ? roleDto.fromRoleModel(roleModel) : null);
    }

    @RequestMapping("/code")
    public ResponseEntity<RoleDto> findRoleByCode(@RequestParam("query") String code) {
        RoleDto roleDto = new RoleDto();
        RoleModel roleModel = roleService.findRoleByCode(code);

        return ResponseEntity.status( (roleModel != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND).body( (roleModel != null) ? roleDto.fromRoleModel(roleModel) : null);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveRole(@RequestBody() RoleDto roleDto) {
        RoleDto role = new RoleDto();

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(role.fromRoleModel(roleService.saveRole(roleDto.toRoleModel())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoleById(@PathVariable() String id) {
        return (roleService.deleteRoleById(id)) ? ResponseEntity.status(HttpStatus.OK).body(null) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/name")
    public ResponseEntity<?> deleteRoleByName(@RequestParam() String name) {
        return (roleService.deleteRoleByName(name)) ? ResponseEntity.status(HttpStatus.OK).body(null) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/code")
    public ResponseEntity<?> deleteRoleByCode(@RequestParam() String code) {
        return (roleService.deleteRoleByCode(code)) ? ResponseEntity.status(HttpStatus.OK).body(null) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
