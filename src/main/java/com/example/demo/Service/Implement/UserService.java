package com.example.demo.Service.Implement;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.InterfaceUserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService extends BaseService<User, UserRepository> implements InterfaceUserService {
    public Set<Order> getOrders(UUID idUser) {
        Optional<User> userOptional = repository.findById(idUser);
        if(userOptional.isPresent()){
            return userOptional.get().getOrders();
        } else {
            throw new NoSuchElementException("can't found user have id: " + idUser);
        }
    }
}
