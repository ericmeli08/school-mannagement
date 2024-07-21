package com.mlschool.schoolmannagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mlschool.schoolmannagement.model.user.User;
import com.mlschool.schoolmannagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

   public User saveUser(User user){
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
   }

   public User loginUser(User user) {
    String msg = "";
    User User1 = userRepository.findByEmail(user.getEmail());
    if (User1 != null) {
        String password = user.getPassword();
        String encodedPassword = User1.getPassword();
        Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
        if (isPwdRight) {
            User User = userRepository.findByEmailAndPassword(user.getEmail(), encodedPassword);
            if (User != null) {
                return User;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }else {
        return null;
    }
}

// public LoginMesage  loginEmployee(LoginDTO loginDTO) {
//     String msg = "";
//     Employee employee1 = employeeRepo.findByEmail(loginDTO.getEmail());
//     if (employee1 != null) {
//         String password = loginDTO.getPassword();
//         String encodedPassword = employee1.getPassword();
//         Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//         if (isPwdRight) {
//             Optional<Employee> employee = employeeRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
//             if (employee.isPresent()) {
//                 return new LoginMesage("Login Success", true);
//             } else {
//                 return new LoginMesage("Login Failed", false);
//             }
//         } else {
//             return new LoginMesage("password Not Match", false);
//         }
//     }else {
//         return new LoginMesage("Email not exits", false);
//     }
// }
}

