package rs.ac.uns.ftn.medDataShare.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.medDataShare.model.user.Admin;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.repository.AdminRepository;
import rs.ac.uns.ftn.medDataShare.repository.CommonUserRepository;
import rs.ac.uns.ftn.medDataShare.repository.MedWorkerRepository;
import rs.ac.uns.ftn.medDataShare.security.dto.UserPrinciple;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MedWorkerRepository medWorkerRepository;

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = getUser(username);
        return UserPrinciple.build(user);
    }

    public User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        User user;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            user = getUser(username);
            return user;
        } else {
            return null;
        }

    }

    public User getUser(String email){
        MedWorker medWorker = medWorkerRepository.findByUsername(email);
        if(medWorker != null){
            return medWorker;
        }
        CommonUser commonUser = commonUserRepository.findByUsername(email);
        if(commonUser != null){
            return commonUser;
        }

        Admin admin = adminRepository.findByUsername(email);
        return admin;
    }


}
