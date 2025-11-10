package com.quantafic.JWTSecurity.Service;

import com.quantafic.JWTSecurity.DTO.UserLoginDTO;
import com.quantafic.JWTSecurity.DTO.UserResponseDTO;
import com.quantafic.JWTSecurity.Model.Application;
import com.quantafic.JWTSecurity.Model.CustomerPrincipal;
import com.quantafic.JWTSecurity.Model.UserPrincipal;
import com.quantafic.JWTSecurity.Model.Users;
import com.quantafic.JWTSecurity.Repo.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserResponseDTO registerUser(Users user){
        if (repo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return mapToDTO(user);
    }

    public String verify(UserLoginDTO loginDTO) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getStaffId(), loginDTO.getPassword())
        );
        if (authentication.isAuthenticated()) {
            Users dbUser = repo.findById(loginDTO.getStaffId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtService.generateToken(
                    dbUser.getStaffId(),
                    dbUser.getRole(),
                    dbUser.getIsActive()
            );
            return token;
        }
        return "Fail";
    }

    public UserResponseDTO getUser() {
        String userId = getIdFromToken();
        Users user = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    public String getIdFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUsername();
        } else if (principal instanceof CustomerPrincipal) {
            return ((CustomerPrincipal) principal).getUsername();
        }
        return null;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<Users> users = repo.findAll();
        return users.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void editUser(UserResponseDTO userResponseDTO) {
        Users user = repo.findById(userResponseDTO.getStaffId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStaff_name(userResponseDTO.getUsername());
        user.setDepartment(userResponseDTO.getDepartment());
        user.setEmail(userResponseDTO.getEmail());
        user.setRole(userResponseDTO.getRole());
        user.setBranch_code(userResponseDTO.getBranchCode());
        user.setIsActive(userResponseDTO.getIsActive());
         repo.save(user);
    }

    private UserResponseDTO mapToDTO(Users user) {
        return UserResponseDTO.builder()
                .staffId(user.getStaffId())
                .username(user.getStaff_name())
                .email(user.getEmail())
                .department(user.getDepartment())
                .role(user.getRole())
                .branchCode(user.getBranch_code())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


}
