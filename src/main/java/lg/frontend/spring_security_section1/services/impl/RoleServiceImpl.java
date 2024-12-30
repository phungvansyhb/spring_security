package lg.frontend.spring_security_section1.services.impl;

import jakarta.transaction.Transactional;
import lg.frontend.spring_security_section1.DTOs.request.CreateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.request.UpdateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleDetailResponseDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleResponseDTO;
import lg.frontend.spring_security_section1.entities.*;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.repositories.PermissionRepository;
import lg.frontend.spring_security_section1.repositories.RolePermissionRepository;
import lg.frontend.spring_security_section1.repositories.RoleRepository;
import lg.frontend.spring_security_section1.repositories.RoleUserRepository;
import lg.frontend.spring_security_section1.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleUserRepository roleUserRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;


    public RoleServiceImpl(RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository,
                           PermissionRepository permissionRepository, RoleUserRepository roleUserRepository) {
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.roleUserRepository = roleUserRepository;
    }

    @Override
    @Transactional
    public CustomResponse<RoleResponseDTO> createRole(CreateRoleDTO createRoleDTO) {
        try {
            Role role = new Role();
            role.setRoleName(createRoleDTO.getRoleName());
            role.setRoleDescription(createRoleDTO.getRoleDescription());
            Role createdRole = this.roleRepository.save(role);

            List<RolePermission> rolePermissions = new ArrayList<>();
            createRoleDTO.getPermissionIds().forEach(permissionId -> {
                Permission permission = permissionRepository.findById(permissionId).orElse(null);
                if (permission != null) {
                    RolePermission tmpRolePermission = new RolePermission();
                    tmpRolePermission.setPermission(permission);
                    tmpRolePermission.setRole(createdRole);
                    rolePermissions.add(tmpRolePermission);
                }
            });
            if (!rolePermissions.isEmpty()) {
                rolePermissionRepository.saveAll(rolePermissions);
            }
            RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
            roleResponseDTO.setId(createdRole.getId());
            roleResponseDTO.setRoleName(createdRole.getRoleName());
            roleResponseDTO.setRoleDescription(createdRole.getRoleDescription());
            roleResponseDTO.setCreatedDate(createdRole.getCreatedDate());
            roleResponseDTO.setUpdatedDate(createdRole.getUpdatedDate());
            roleResponseDTO.setPermissionId(createRoleDTO.getPermissionIds());
            return new CustomResponse<>(true, "ok", roleResponseDTO);
        } catch (Exception e) {
            return new CustomResponse<>(false, "Error creating role " + e.getMessage(), null);
        }

    }

    @Override
    public CustomResponse<List<RoleResponseDTO>> getRoles() {
        return null;
    }

    @Override
    public CustomResponse<RoleDetailResponseDTO> getDetailRole(Long id) {
        try {
            RoleDetailResponseDTO roleDetailResponseDTO = new RoleDetailResponseDTO();
            Role role = roleRepository.findById(id).orElse(null);
            if (role != null) {
                roleDetailResponseDTO.setRoleName(role.getRoleName());
                roleDetailResponseDTO.setRoleDescription(role.getRoleDescription());
                roleDetailResponseDTO.setCreatedDate(role.getCreatedDate());
                roleDetailResponseDTO.setUpdatedDate(role.getUpdatedDate());

                List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(role.getId());
                List<Permission> permissions = rolePermissions.stream().map(RolePermission::getPermission).toList();
                roleDetailResponseDTO.setPermissions(permissions);

                List<RoleUser> roleUsers = roleUserRepository.findAllByRoleId(role.getId());
                List<User> users = roleUsers.stream().map(RoleUser::getUser).toList();
                roleDetailResponseDTO.setUsers(users);

                return new CustomResponse<>(false, "ok", roleDetailResponseDTO);

            } else {
                return new CustomResponse<>(false, "Role not found", null);
            }
        } catch (Exception e) {
            return new CustomResponse<>(false, "Role get detail error " + e.getMessage(), null);

        }

    }

    @Override
    @Transactional
    public CustomResponse<RoleDetailResponseDTO> updateRole(Long id, UpdateRoleDTO updateRoleDTO) {
        try{
            Role role = roleRepository.findById(id).orElse(null);
            if(role != null){
                List<RolePermission> rolePermissions = new ArrayList<>();
                updateRoleDTO.getPermissions().forEach(item -> {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setPermission(item);
                    rolePermission.setRole(role);
                    rolePermissions.add(rolePermission);
                });
                if(!rolePermissions.isEmpty()){
                    rolePermissionRepository.deleteAllByRoleId(role.getId());
                    rolePermissionRepository.saveAll(rolePermissions);
                }
                List<RoleUser> roleUsers = new ArrayList<>();
                updateRoleDTO.getUsers().forEach(item -> {
                    RoleUser roleUser = new RoleUser();
                    roleUser.setUser(item);
                    roleUser.setRole(role);
                    roleUsers.add(roleUser);
                });
                if(!roleUsers.isEmpty()){
                    roleUserRepository.deleteAllByRoleId(role.getId());
                    roleUserRepository.saveAll(roleUsers);
                }
                return new CustomResponse<>(true, "ok", null);
            }else{
                return new CustomResponse<>(false, "Role not found", null);
            }
        }catch (Exception e){
            return new CustomResponse<>(false, "Role update error " + e.getMessage(), null);
        }

    }

    @Override
    public CustomResponse<List<RoleResponseDTO>> deleteRole(Long[] ids) {
        return null;
    }
}
