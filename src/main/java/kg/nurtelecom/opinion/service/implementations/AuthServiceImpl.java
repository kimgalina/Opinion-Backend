package kg.nurtelecom.opinion.service.implementations;

import kg.nurtelecom.opinion.entity.User;
import kg.nurtelecom.opinion.entity.UserPrivacySettings;
import kg.nurtelecom.opinion.enums.Role;
import kg.nurtelecom.opinion.enums.Status;
import kg.nurtelecom.opinion.exception.NotFoundException;
import kg.nurtelecom.opinion.exception.NotValidException;
import kg.nurtelecom.opinion.mapper.UserMapper;
import kg.nurtelecom.opinion.payload.user.UserSignInRequest;
import kg.nurtelecom.opinion.payload.user.UserSignInResponse;
import kg.nurtelecom.opinion.payload.user.UserSignUpRequest;
import kg.nurtelecom.opinion.payload.user.UserSignUpResponse;
import kg.nurtelecom.opinion.repository.UserPrivacyRepository;
import kg.nurtelecom.opinion.repository.UserRepository;
import kg.nurtelecom.opinion.service.AuthService;
import kg.nurtelecom.opinion.service.JwtService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserPrivacyRepository userPrivacyRepository;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserPrivacyRepository userPrivacyRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userPrivacyRepository = userPrivacyRepository;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<UserSignUpResponse> signUp(UserSignUpRequest user) {
        if (!user.password().equals(user.confirmPassword())) {
            throw new NotValidException("Пароли не совпадают");
        }

        if (LocalDate.now().minusYears(user.birthDate().getYear()).getYear() > 120) {
            throw new NotValidException("Возраст не может быть больше 120");
        }

        User userEntity = userMapper.toEntity(user);
        userEntity.setStatus(Status.NOT_VERIFIED);
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserPrivacySettings userPrivacySettings = new UserPrivacySettings();
        userEntity.setPrivacySettings(userPrivacySettings);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException("Пользователь с такой почтой или никнеймом уже существует");
        }

        return ResponseEntity.ok(userMapper.toModel(userEntity));
    }

    @Override
    public ResponseEntity<UserSignInResponse> signIn(UserSignInRequest user) {
        User userEntity = userRepository.findByEmail(user.email())
                .orElseThrow(() -> new NotFoundException("Пользователь с такой почтой не найден"));

        if (!passwordEncoder.matches(user.password(), userEntity.getPassword())) {
            throw new NotValidException("Вы ввели неверный пароль");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.email(),
                        user.password()
                )
        );

        String jwtToken = jwtService.generateToken(userEntity);
        UserSignInResponse response = new UserSignInResponse(jwtToken);

        return ResponseEntity.ok(response);
    }
}
