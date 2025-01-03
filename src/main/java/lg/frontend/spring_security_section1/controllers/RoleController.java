package lg.frontend.spring_security_section1.controllers;

import lg.frontend.spring_security_section1.DTOs.request.CreateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.request.UpdateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleDetailResponseDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleResponseDTO;
import lg.frontend.spring_security_section1.entities.Role;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CustomResponse<List<Role>> getAllRoles() {
        return roleService.getRoles();

    }

    @GetMapping("/{id}")
    public CustomResponse<RoleDetailResponseDTO> getRoleDetail(@PathVariable Long id) {
        return roleService.getDetailRole(id);
    }

    @PutMapping("/{id}")
    public CustomResponse<RoleDetailResponseDTO> updateRole(@PathVariable Long id ,  @RequestBody UpdateRoleDTO updateRoleDTO){
        return roleService.updateRole( id, updateRoleDTO);
    }

    @DeleteMapping("/{id}")
    public CustomResponse<Role> deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }

}
