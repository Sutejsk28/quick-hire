package com.sutej.QuickHire.Services;

import com.sutej.QuickHire.Dto.WorkerRequest;
import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Entities.UserEntity;
import com.sutej.QuickHire.Entities.WorkerEntity;
import com.sutej.QuickHire.Enums.Roles;
import com.sutej.QuickHire.Repository.UserRepository;
import com.sutej.QuickHire.Repository.WorkerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class WorkerServices {

    private final WorkerRepository workerRepository;
    private final UserRepository userRepository;

    public WorkerEntity addWorker(WorkerRequest workerRequest){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority(Roles.WORKER.getName()));

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        UserEntity user = (UserEntity) newAuth.getPrincipal();
        log.info("Username: "+user.getUsername());

        WorkerEntity worker = new WorkerEntity();
        worker.setSkillSet(workerRequest.getSkillsSet());
        worker.setExperience(workerRequest.getExperience());
        worker.setTasksList(new ArrayList<>());
        worker.setUser(user);

        workerRepository.save(worker);

        return worker;

    }


    public List<TaskEntity> getAllTasksByWorker() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        WorkerEntity worker = workerRepository.findByUser(user).orElseThrow();
        return worker.getTasksList();
    }
}
