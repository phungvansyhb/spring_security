package lg.frontend.spring_security_section1.services;

import lg.frontend.spring_security_section1.DTOs.request.CreateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.request.UpdateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleDetailResponseDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleResponseDTO;
import lg.frontend.spring_security_section1.entities.Role;
import lg.frontend.spring_security_section1.models.CustomResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    CustomResponse<RoleResponseDTO> createRole(CreateRoleDTO createRoleDTO);

    CustomResponse<List<Role>> getRoles();

    CustomResponse<RoleDetailResponseDTO> getDetailRole(Long id);

    CustomResponse<RoleDetailResponseDTO> updateRole(Long id, UpdateRoleDTO updateRoleDTO);

    CustomResponse<Role> deleteRole(Long id);

}
