package ua.eva.wims.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ua.eva.wims.dto.UserDto;
import ua.eva.wims.entity.UserEntity;
import ua.eva.wims.entity.UserInfoDetails;
import ua.eva.wims.repository.UserEntityRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {

    private UserEntityRepository repository;
    private PasswordEncoder encoder;
    private ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userDetail = repository.findByName(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public UserDto addEmployee(UserDto userDto) {

        if (repository.findByName(userDto.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + userDto.getName() + " already exists");
        }
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        return toDto(repository.save(toEntity(userDto)));
    }

    public UserEntity toEntity(UserDto userDto) {
        return mapper.map(userDto, UserEntity.class);
    }

    public UserDto toDto(UserEntity userEntity) {
        return mapper.map(userEntity, UserDto.class);
    }
}
