package com.example.devmagrocrud.services;

import com.example.devmagrocrud.dtos.CreateUserDto;
import com.example.devmagrocrud.exceptions.InvalidAgeException;
import com.example.devmagrocrud.exceptions.InvalidEmailException;
import com.example.devmagrocrud.exceptions.InvalidNameException;
import com.example.devmagrocrud.models.User;
import com.example.devmagrocrud.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        openMocks(this);  // Inicializa os mocks
    }

    @Test
    void testSaveUser_InvalidEmail() {
        CreateUserDto userDto = new CreateUserDto("John Doe", "invalid-email", 25, 1.75);

        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            userService.saveUser(userDto);
        });

        assertEquals("Email inválido!", exception.getMessage());
    }

    @Test
    void testSaveUser_InvalidName() {
        CreateUserDto userDto = new CreateUserDto("John", "john@example.com", 25, 1.75);

        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> {
            userService.saveUser(userDto);
        });

        assertEquals("Nome inválido!", exception.getMessage());
    }

    @Test
    void testSaveUser_InvalidAge() {
        CreateUserDto userDto = new CreateUserDto("Johnathan Doe", "john@example.com", 17, 1.75);

        InvalidAgeException exception = assertThrows(InvalidAgeException.class, () -> {
            userService.saveUser(userDto);
        });
        assertEquals("Idade inválida!", exception.getMessage());
    }

    @Test
    void testSaveUser_EmailAlreadyExists() {
        // Nome com mais de 10 caracteres
        CreateUserDto userDto = new CreateUserDto("Johnathan Doe", "john@example.com", 25, 1.75);

        // Simulando que o email já está no banco de dados
        when(userRepository.findByEmail(userDto.email())).thenReturn(Optional.of(new User()));

        // Agora, espera-se que o código lance uma InvalidEmailException
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            userService.saveUser(userDto);
        });

        assertEquals("Email ja cadastrado!", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(userDto.email()); // Verifica que a consulta foi chamada uma vez
    }


    @Test
    void testSaveUser_Success() {
        CreateUserDto userDto = new CreateUserDto("John Doeee", "john@example.com", 25, 1.75);

        // Simulando que o email não existe no banco
        when(userRepository.findByEmail(userDto.email())).thenReturn(Optional.empty());

        User userToSave = new User();
        userToSave.setName(userDto.name());
        userToSave.setEmail(userDto.email());
        userToSave.setAge(userDto.age());
        userToSave.setHeight(userDto.height());

        // Quando o save for chamado, não precisa fazer nada (simular comportamento)
        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        // Chama o método
        userService.saveUser(userDto);

        // Verifica se o repositório save foi chamado
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUserById() {
        // Definir o ID do usuário a ser deletado
        long userId = 1L;

        // Chamar o método de deletação
        userService.deleteUserById(userId);

        // Verificar se o método deleteById foi chamado no repositório com o ID correto
        verify(userRepository, times(1)).deleteById(userId);
    }

}