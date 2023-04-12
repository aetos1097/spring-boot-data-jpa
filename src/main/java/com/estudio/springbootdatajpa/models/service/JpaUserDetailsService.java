package com.estudio.springbootdatajpa.models.service;

import com.estudio.springbootdatajpa.models.dao.IUsuarioDao;
import com.estudio.springbootdatajpa.models.entity.Role;
import com.estudio.springbootdatajpa.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {//spring provee esta interfaz para seguridad con el usuario

    @Autowired
    private IUsuarioDao usuarioDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly =true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //obtenemos el usuario
        Usuario usuario = usuarioDao.findByUsername(username);
        //validamos que exista el usuario
        if(usuario == null){
            logger.error("Error login: No existe el usuario '"+username+" '");
            throw new UsernameNotFoundException("No existe el usuario: " + username + " en el sistema");
        }
        //obtenemos los roles
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Role role: usuario.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));//Asignamos los roles dentro de la lista authorities
        }
        //validamos que exista el Rol
        if(authorities.isEmpty()){
            logger.error("Error login: No existe el usuario '"+ username + " ' no tiene roles asignaos");
            throw new UsernameNotFoundException("Error login: No existe el usuario '"+ username + " ' no tiene roles asignaos");
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(),true,true,true,authorities);
    }
}
