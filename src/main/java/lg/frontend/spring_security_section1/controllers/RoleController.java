package lg.frontend.spring_security_section1.controllers;

import lg.frontend.spring_security_section1.DTOs.request.CreateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleDetailResponseDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleResponseDTO;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public CustomResponse<RoleResponseDTO> addRole(@RequestBody CreateRoleDTO createRoleDTO) {
        return roleService.createRole(createRoleDTO);
    }

    @GetMapping
    public CustomResponse<RoleDetailResponseDTO> getRoleDetail(@RequestParam Long roleId) {
        return roleService.getDetailRole(roleId);
    }


}
