package com.example.devmagrocrud.cli;


import com.example.devmagrocrud.dtos.CreateUserDto;
import com.example.devmagrocrud.models.User;
import com.example.devmagrocrud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Menu {
    private static final Scanner SCANNER = new Scanner(System.in);

    @Autowired
    private UserService userService;

    public void showMenu() {

        while (true) {

            System.out.println("""
                    1 - Cadastrar o usuário
                    2 - Listar todos usuários cadastrados
                    3 - Deletar usuário
                    4 - Pesquisar usuário por nome
                    0 - Sair
                    """);

            int option;

            try {

                option = Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção Inválida! Escolha um número");
                continue;
            }

            switch (option) {
                case 1 -> cadasterUser();
                case 2 -> listUser();
                case 3 -> deleteUser();
                case 4 -> searchUser();
                case 0 -> {
                    System.out.println("Finalizando programa...");
                    System.exit(0);
                }
                default -> System.out.println("Digite um número entre 1 e 5: ");
            }
        }
    }

    private void deleteUser() {
        listUser();
        System.out.print("Escolha o id de um usuário para deletar: ");
        long choicedId = Integer.parseInt(SCANNER.nextLine());
        userService.deleteUserById(choicedId);
        System.out.println("Usuário removido com sucesso.");
    }

    private void cadasterUser() {
        System.out.println("-=-=-=-=-=-=-=-=-=-");
        System.out.print("Nome: ");
        String nome = SCANNER.nextLine();
        System.out.print("Email: ");
        String email = SCANNER.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(SCANNER.nextLine());
        System.out.print("Height: ");
        double height = Double.parseDouble(SCANNER.nextLine());

        CreateUserDto newUser = new CreateUserDto(nome, email, age, height);
        userService.saveUser(newUser);
    }

    private void listUser() {
        System.out.println("-=-==-=-=-=-=-=-=-=-=-");
        List<User> allUser = userService.findAllUser();
        for (User user : allUser){
            System.out.println(user.getId() + " - " + user.getName() + ".");
        }
    }

    private void searchUser(){
        List<User> users = userService.findAllUser();

        System.out.println("-=-==-=-=-=-=-=-=-=-=-");
        System.out.println("Digite um trecho do nome, email ou idade.");
        var choice = SCANNER.nextLine().trim().toUpperCase();

        users.stream().filter(user -> user.getName().toUpperCase().contains(choice)
                                         || user.getEmail().toUpperCase().contains(choice)
                                         || String.valueOf(user.getAge()).contains(choice))
                .forEach(System.out::println);
    }

}
