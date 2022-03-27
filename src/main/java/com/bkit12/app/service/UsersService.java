package com.bkit12.app.service;

import com.bkit12.app.domain.Roles;
import com.bkit12.app.domain.Users;
import com.bkit12.app.domain.enumeration.UserStatus;
import com.bkit12.app.repository.RolesRepository;
import com.bkit12.app.repository.UsersRepository;
import com.bkit12.app.security.AuthoritiesConstants;
import com.bkit12.app.security.SecurityUtils;
import com.bkit12.app.service.dto.UsersDTO;
import com.bkit12.app.service.mapper.UsersMapper;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
@Transactional
public class UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersService.class);

    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    private final RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;


    public UsersService(UsersRepository usersRepository, UsersMapper usersMapper,
         RolesRepository rolesRepository,  PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Optional<Users> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(usersRepository::findOneByUsername);
    }

    /**
     * Save a users.
     *
     * @param usersDTO the entity to save.
     * @return the persisted entity.
     */
    public UsersDTO save(UsersDTO usersDTO) {
        log.debug("Request to save Users : {}", usersDTO);
        Users users = usersMapper.toEntity(usersDTO);
        users = usersRepository.save(users);
        return usersMapper.toDto(users);
    }

    /**
     * Partially update a users.
     *
     * @param usersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsersDTO> partialUpdate(UsersDTO usersDTO) {
        log.debug("Request to partially update Users : {}", usersDTO);

        return usersRepository
            .findById(usersDTO.getId())
            .map(existingUsers -> {
                usersMapper.partialUpdate(existingUsers, usersDTO);

                return existingUsers;
            })
            .map(usersRepository::save)
            .map(usersMapper::toDto);
    }

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return usersRepository.findAll(pageable).map(usersMapper::toDto);
    }

    /**
     * Get all the users with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UsersDTO> findAllWithEagerRelationships(Pageable pageable) {
        return usersRepository.findAllWithEagerRelationships(pageable).map(usersMapper::toDto);
    }

    /**
     *  Get all the users where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsersDTO> findAllWhereCustomerIsNull() {
        log.debug("Request to get all users where Customer is null");
        return StreamSupport
            .stream(usersRepository.findAll().spliterator(), false)
            .filter(users -> users.getCustomer() == null)
            .map(usersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one users by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsersDTO> findOne(Long id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findOneWithEagerRelationships(id).map(usersMapper::toDto);
    }

    /**
     * Delete the users by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }





    public Users registerUser(UsersDTO userDTO, String password) {
        usersRepository
            .findOneByUsername(userDTO.getUsername().toLowerCase())
            .ifPresent(existingUser -> {
                if (existingUser != null) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        usersRepository
            .findOneByEmail(userDTO.getEmail())
            .ifPresent(existingUser -> {
                 if (existingUser != null) {
                    throw new EmailAlreadyUsedException();
                }
            });
        Users newUser = new Users();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setUsername(userDTO.getUsername().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        // new user is not active
        newUser.setStatus(UserStatus.CURRENT);
        // new user gets registration key
        Set<Roles> authorities = new HashSet<>();
        rolesRepository.findById(6L).ifPresent(authorities::add);
        newUser.setRoles(authorities);
        usersRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }









}
