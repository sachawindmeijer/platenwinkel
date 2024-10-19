package com.example.platenwinkel.service;
import com.example.platenwinkel.dtos.output.UserOutputDto;
import com.example.platenwinkel.models.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Service
public class MyUserDetailService implements UserDetailsService {

        private final UserService userService;

    public MyUserDetailService(UserService userService) {
        this.userService = userService;
    }


//    @Autowired
//    private AuthorityService authorityService;

        public UserDetails loadUserByUsername(Long userid, String username) {
            UserOutputDto userDto = userService.getUser(userid);
            if (userDto == null) {
                throw new UsernameNotFoundException("User not found");
            }

            String password = userDto.getPassword();

            Set<Authority> authorities = userDto.getAuthorities();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Authority authority: authorities) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }

            return new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);
        }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

