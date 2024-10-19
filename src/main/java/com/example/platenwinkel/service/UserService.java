package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.UserInputDto;
import com.example.platenwinkel.dtos.output.OrderOutputDto;
import com.example.platenwinkel.dtos.output.UserOutputDto;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.Authority;

import com.example.platenwinkel.models.User;
import com.example.platenwinkel.repositories.UserRepository;

import com.example.platenwinkel.untils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserOutputDto getUser(Long userid) {
        UserOutputDto dto = new UserOutputDto();
        Optional<User> user = userRepository.findById(userid);
        if (user.isPresent()) {
            dto = fromUser(user.get());
        } else {
            throw new UsernameNotFoundException("User not found with ID: " + userid);
        }
        return dto;
    }

    public boolean userExists(Long userid) {
        return userRepository.existsById(userid);
    }

    public Long createUser(UserInputDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        User newUser = userRepository.save(toUser(userDto));

        if (newUser == null) {
            throw new RuntimeException("Failed to create a new user");
        }
        return newUser.getId(); // Return the Long ID of the new user
    }
    public void deleteUser(Long userid) {
        userRepository.deleteById(userid);
    }

    public void updateUser(Long userid, UserOutputDto newUser) {
        if (!userRepository.existsById(userid)) throw new RecordNotFoundException("User not found: " + userid);
        User user = userRepository.findById(userid).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(Long userid) {
        if (!userRepository.existsById(userid)) {
            throw new UsernameNotFoundException("User not found with ID: " + userid);
        }
        User user = userRepository.findById(userid).get();
        return user.getAuthorities();
    }

    // dit is ook gewijzigt 19-10
    public void addAuthority(Long userid, String authority) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userid));

        user.addAuthority(new Authority(user.getUsername(), authority));
        userRepository.save(user);
    }

    public void removeAuthority(Long userid, String authority) {
        if (!userRepository.existsById(userid)) {
            throw new UsernameNotFoundException("User not found with ID: " + userid);
        }
        User user = userRepository.findById(userid).get();
        Authority authorityToRemove = user.getAuthorities().stream()
                .filter(a -> a.getAuthority().equalsIgnoreCase(authority))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Authority not found: " + authority));
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }
    public static UserOutputDto fromUser(User user){

        var dto = new UserOutputDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setEnabled(user.isEnabled());

        // Orders omzetten naar OrderOutputDto
        dto.setOrders(user.getOrders()
                .stream()
                .map(order -> {
                    OrderOutputDto orderDto = new OrderOutputDto();
                    orderDto.setId(order.getId());
                    orderDto.setOrderDate(order.getOrderDate());
                    orderDto.setShippingCost(order.getShippingCost());
                    orderDto.setPaymentStatus(order.getPaymentStatus());
                    orderDto.setDeliveryStatus(order.getDeliveryStatus());
                    orderDto.setShippingAdress(order.getShippingAdress());

                    Map<Long, Integer> itemDtoMap = order.getItems().entrySet()
                            .stream()
                            .collect(Collectors.toMap(
                                    entry -> entry.getKey().getId(), // Haal de ID van LpProduct op
                                    Map.Entry::getValue // Houd de hoeveelheid hetzelfde
                            ));

                    orderDto.setItems(itemDtoMap);
                    return orderDto;
                })
                .collect(Collectors.toList()));
        return dto;
    }

    public User toUser(UserInputDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setDateOfBirth(userDto.getDateOfBirth());

        userDto.getAuthorities().forEach(auth -> {
            user.addAuthority(new Authority(user.getUsername(), auth.getAuthority()));
        });

        return user;
    }

    public void save(User newUser) {
    }
}