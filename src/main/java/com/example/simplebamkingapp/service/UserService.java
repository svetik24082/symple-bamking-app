package com.example.simplebamkingapp.service;

import com.example.simplebamkingapp.exception.InvalidPasswordException;
import com.example.simplebamkingapp.exception.UserAlreadyExistsException;
import com.example.simplebamkingapp.exception.UserNotFoundException;
import com.example.simplebamkingapp.model.Account;
import com.example.simplebamkingapp.model.Currency;
import com.example.simplebamkingapp.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
   private final Map<String, User> users = new HashMap<>();
   public  User addUser(User user) {//доб польз.
      if (users.containsKey(user.getUsername())){
         throw new UserAlreadyExistsException();
      }
      users.put(user.getUsername(),user);
      return  createNewUserAccounts(user);


   }
   public User updateUser(String username, String firstName,
                          String lastName){  //обновить польз.
      if (users.containsKey(username)){
         throw new UserNotFoundException();
      }
      User user = users.get(username);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      return user;

   }
   public  void updatePassword(String username,String password, String newPassword){
// обновить логин и пароль
      if (users.containsKey(username)){
         throw  new UserNotFoundException();
      }
      User user = users.get(username);
      if (!user.getPassword().equals(password)){
         throw new InvalidPasswordException();
      }
      user.setPassword(newPassword);
   }
   public User removeUser(String username){ // удалить польз
      if (users.containsKey(username)){
         throw new UserNotFoundException();
      }
   return  users.remove(username);
   }
   public User getUser (String username){ //получить пользователя по логину
      if (!users.containsKey(username)){
         throw new UserNotFoundException();
      }
      return users.get(username);


   }
   public Collection<User>getAllUsers(){ // получить всех пользователей
return users.values();
   }
   private User createNewUserAccounts(User user){ //  Создать новые учетные записи пользователей
      user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.RUB));
      user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.EUR));
      user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.USD));
      return  user;
   }
}
