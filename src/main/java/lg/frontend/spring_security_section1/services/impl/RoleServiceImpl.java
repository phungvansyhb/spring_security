package lg.frontend.spring_security_section1.services.impl;

import jakarta.transaction.Transactional;
import lg.frontend.spring_security_section1.DTOs.request.CreateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.request.UpdateRoleDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleDetailResponseDTO;
import lg.frontend.spring_security_section1.DTOs.response.RoleResponseDTO;
import lg.frontend.spring_security_section1.entities.*;
import lg.frontend.spring_security_section1.models.CustomResponse;
import lg.frontend.spring_security_section1.repositories.*;
import lg.frontend.spring_security_section1.services.RoleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleUserRepository roleUserRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository,
                           PermissionRepository permissionRepository, RoleUserRepository roleUserRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.roleUserRepository = roleUserRepository;
        this.userRepository = userRepository;
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

    public RoleDetailResponseDTO getRoleDetailResponseDTOById(Long id) {
        RoleDetailResponseDTO roleDetailResponseDTO = new RoleDetailResponseDTO();
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            roleDetailResponseDTO.setRoleName(role.getRoleName());
            roleDetailResponseDTO.setRoleDescription(role.getRoleDescription());
            roleDetailResponseDTO.setCreatedDate(role.getCreatedDate());
            roleDetailResponseDTO.setUpdatedDate(role.getUpdatedDate());

            List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleId(role.getId());
            List<Permission> permissions = rolePermissions.stream().map(RolePermission::getPermission).toList();
            roleDetailResponseDTO.setPermissions(permissions);

            List<RoleUser> roleUsers = roleUserRepository.findAllByRoleId(role.getId());
            List<User> users = roleUsers.stream().map(RoleUser::getUser).toList();
            List<RoleDetailResponseDTO.User> finalUsers = users.stream().map(u -> new RoleDetailResponseDTO.User(u.getId(), u.getUsername(), u.getEmail(), u.getAvatar())).toList();
            roleDetailResponseDTO.setUsers(finalUsers);
            return roleDetailResponseDTO;
        }
        return null;
    }

    @Override
    public CustomResponse<List<Role>> getRoles() {
        List<Role> roles = roleRepository.findAllByDeletedDateIsNull();
        return new CustomResponse<>(true, "Ok", roles);
    }

    @Override
    public CustomResponse<RoleDetailResponseDTO> getDetailRole(Long id) {
        try {
            RoleDetailResponseDTO roleDetailResponseDTO = getRoleDetailResponseDTOById(id);
            if (roleDetailResponseDTO != null) {
                return new CustomResponse<>(true, "ok", roleDetailResponseDTO);
            } else {
                return new CustomResponse<>(false, "role not found", null);
            }
        } catch (Exception e) {
            return new CustomResponse<>(false, "Role get detail error " + e.getMessage(), null);
        }
    }

    @Override
    @Transactional
    public CustomResponse<RoleDetailResponseDTO> updateRole(Long id, UpdateRoleDTO updateRoleDTO) {
        try {
            Role role = roleRepository.findById(id).orElse(null);
            if (role != null) {
                List<RolePermission> rolePermissions = new ArrayList<>();
                updateRoleDTO.getPermissionIds().forEach(permissionId -> {
                    Permission permission = permissionRepository.findById(permissionId).orElse(null);
                    if (permission != null) {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setPermission(permission);
                        rolePermission.setRole(role);
                        rolePermissions.add(rolePermission);
                    }
                });
                if (!rolePermissions.isEmpty()) {
                    rolePermissionRepository.deleteAllByRoleId(role.getId());
                    rolePermissionRepository.saveAll(rolePermissions);
                }
                List<RoleUser> roleUsers = new ArrayList<>();
                updateRoleDTO.getUserIds().forEach(userId -> {
                    User user = userRepository.findById(userId).orElse(null);
                    if (user != null) {
                        RoleUser roleUser = new RoleUser();
                        roleUser.setUser(user);
                        roleUser.setRole(role);
                        roleUsers.add(roleUser);
                    }
                });
                if (!roleUsers.isEmpty()) {
                    roleUserRepository.deleteAllByRoleId(role.getId());
                    roleUserRepository.saveAll(roleUsers);
                }
                return new CustomResponse<>(true, "ok", null);
            } else {
                return new CustomResponse<>(false, "Role not found", null);
            }
        } catch (Exception e) {
            return new CustomResponse<>(false, "Role update error " + e.getMessage(), null);
        }

    }

    @Override
    @Transactional
    public CustomResponse<Role> deleteRole(Long id) {
        try {
            Role role = roleRepository.findByIdAndDeletedDateIsNull(id).orElse(null);
            if (role != null) {
                roleRepository.deleteById(id);
                rolePermissionRepository.deleteAllByRoleId(id);
                roleUserRepository.deleteAllByRoleId(id);
                role.setDeletedDate(LocalDate.now());
                return new CustomResponse<>(true, "ok", role);
            } else {
                return new CustomResponse<>(false, "role not found", null);
            }
        } catch (Exception e) {
            return new CustomResponse<>(false, "delete fail: " + e.getMessage(), null);
        }

    }
}
