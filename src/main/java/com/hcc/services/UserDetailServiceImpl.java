package com.hcc.services;

import com.hcc.entities.Authority;
import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.CustomPasswordEncoder;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        try {
//            String hql = "SELECT * FROM Users WHERE username = :username";
//            Query<User> query = session.createQuery(hql, User.class);
//            query.setParameter("username",username );
//            List<User> users = query.getResultList();
//            System.out.println(users.size() + "users size");
//            // Process the retrieved users as needed
//
//        } finally {
//            session.close();
//        }
//
//        System.out.println("Authority-size");
        Optional<User> userOpt = userRepository.findByUsername(username);
//        if (userOpt.isPresent()) {
//            System.out.println("got it!");
//        } else {
//            System.out.println("user not found");
//        }
//        String hql = "SELECT u FROM User u WHERE u.username = :username";
//        Query<User> query = session.createQuery(hql, User.class);
//        query.setParameter("username", "john");
//        List<User> users = query.getResultList();
        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        System.out.println(user.getAuthorities().size() + " authority-size");
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    //return null;
    }
}
