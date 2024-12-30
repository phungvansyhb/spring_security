package lg.frontend.spring_security_section1.services;

import lg.frontend.spring_security_section1.DTOs.request.CreateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleDetailResponseDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleResponseDTO;
import lg.frontend.spring_security_section1.models.CustomResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    CustomResponse<RoleResponseDTO> createRole(CreateRoleDTO createRoleDTO);

    CustomResponse<List<RoleResponseDTO>> getRoles();

    CustomResponse<RoleDetailResponseDTO> getDetailRole(Long id);

    CustomResponse<RoleResponseDTO> updateRole(Long id, CreateRoleDTO createRoleDTO);

    CustomResponse<List<RoleResponseDTO>> deleteRole(Long[] ids);

}
