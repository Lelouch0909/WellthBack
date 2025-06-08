package com.lontsi.wellthappback.controllers;

import com.lontsi.wellthappback.config.JwtProvider;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.Regime;
import com.lontsi.wellthappback.models.Utilisateur;
import com.lontsi.wellthappback.repository.RegimeRepository;
import com.lontsi.wellthappback.repository.UtilisateurRepository;
import com.lontsi.wellthappback.response.AuthResponse;
import com.lontsi.wellthappback.services.UtilisateurService;
import com.lontsi.wellthappback.services.impl.UtilisateurDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/wellthapp/v1/auth")
public class AuthController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Autowired
    private UtilisateurDetailsServiceImpl customUserDetails;

    @Autowired
    private RegimeRepository regimeRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUtilisateurHandler(@RequestBody Utilisateur user) throws UtilisateurException {

        String nom = user.getNom();
        String email = user.getEmail();
        String password = user.getMotDePasse();
        String sexe = user.getSexe();
        Integer age = user.getAge();

        if (utilisateurRepository.findByEmail(email) != null) {
            throw new UtilisateurException("Email is already used with another account");

        }

        Utilisateur createdUSer = new Utilisateur();

        createdUSer.setAge(age);
        createdUSer.setNom(nom);
        createdUSer.setEmail(email);
        createdUSer.setMotDePasse(passwordEncoder.encode(password));
        createdUSer.setSexe(sexe);


        UtilisateurDto savedUtilisateur = utilisateurService.createUtilisateur(createdUSer);


        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(jwt, true);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody Utilisateur utilisateur) throws UtilisateurException {

        String password = utilisateur.getMotDePasse();
        String email = utilisateur.getEmail();

        Authentication authentication = authenticate(email, password);
        String token = jwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse(token, true);


        return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String email, String password) {


        UserDetails userDetails = customUserDetails.loadUserByUsername(email);

        if (userDetails == null) {
            throw new BadCredentialsException("invalid username...");
        }
         if (!passwordEncoder.matches(password,userDetails.getPassword())) {

            throw new BadCredentialsException("invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
