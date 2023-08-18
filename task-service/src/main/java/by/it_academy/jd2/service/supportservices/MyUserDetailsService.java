package by.it_academy.jd2.service.supportservices;

import by.it_academy.jd2.service.api.feign.IUserClientService;
import org.example.mylib.tm.itacademy.dto.UserDTO;
import org.example.mylib.tm.itacademy.enums.EStatusUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class MyUserDetailsService implements UserDetailsService {
    private static final String USER_NOT_FOUND = "User is not found";

    private final IUserClientService userClientService;

    public MyUserDetailsService(IUserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        UserDTO dto = Optional
                .ofNullable(
                        this.userClientService
                                .meDetails(token)
                                .getBody())
                .orElseThrow(()
                        -> new IllegalStateException(USER_NOT_FOUND));

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List
                        .of(new SimpleGrantedAuthority("ROLE_" + dto.getRole()));
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                assert dto != null;
                return dto.getMail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                assert dto != null;
                return (!dto.getStatus().equals(EStatusUser.DEACTIVATED));
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                assert dto != null;
                return (dto.getStatus().equals(EStatusUser.ACTIVATED));
            }
        };

        return userDetails;
    }
}
