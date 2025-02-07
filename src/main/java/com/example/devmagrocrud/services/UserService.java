package com.example.devmagrocrud.services;

import com.example.devmagrocrud.dtos.CreateUserDto;
import com.example.devmagrocrud.exceptions.InvalidAgeException;
import com.example.devmagrocrud.exceptions.InvalidEmailException;
import com.example.devmagrocrud.exceptions.InvalidNameException;
import com.example.devmagrocrud.models.User;
import com.example.devmagrocrud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(CreateUserDto userDto){
        if (!userDto.email().contains("@")){
            throw new InvalidEmailException("Email inválido!");
        }

        if(userDto.name().length() < 10){
            throw new InvalidNameException("Nome inválido!");
        }

        if(userDto.age() < 18){
            throw new InvalidAgeException("Idade inválida!");
        }

        if (userRepository.findByEmail(userDto.email()).isPresent()){
            throw new InvalidEmailException("Email ja cadastrado!");
        }
        var newUser = new User();
        newUser.setName(userDto.name());
        newUser.setEmail(userDto.email());
        newUser.setAge(userDto.age());
        newUser.setHeight(userDto.height());

        userRepository.save(newUser);
    }

    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    public void deleteUserById(long id){
        userRepository.deleteById(id);
    }
}
