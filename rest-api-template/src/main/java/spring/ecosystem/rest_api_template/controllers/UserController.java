package spring.ecosystem.rest_api_template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import spring.ecosystem.rest_api_template.dto.RegisterUserDTO;
import spring.ecosystem.rest_api_template.dto.UserDTO;
import spring.ecosystem.rest_api_template.entities.User;
import spring.ecosystem.rest_api_template.services.UserService;
import spring.ecosystem.rest_api_template.services.auth.AuthenticateService;

import java.util.UUID;

public class UserController {


    @Autowired
    private UserService usuarioServicio;
    @Autowired
    private AuthenticateService autheticateService;
// ACA ME PARECE MUCHO DEVOLVER EL userDTO ya que estan las password etc, abria que usar RegisterUserDTO pero hay que cambia el metodo para setear,o hacer el construtuir con RegisterUserDTO::new User
    @GetMapping("/user")
    public ResponseEntity<Page<RegisterUserDTO>> listAllUsers(Pageable pegeable) {
        Page<RegisterUserDTO> listaUsuarios = usuarioServicio.listAllUsers(pegeable);
        return ResponseEntity.ok(listaUsuarios);
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterUserDTO> register(@RequestBody UserDTO newUser) throws Exception {

        RegisterUserDTO registerUser = usuarioServicio.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
        /* return ResponseEntity.ok(RegisterUser registerUser= autheticateService.regusterOneCustumer(newUser);); */
    }



    @GetMapping("/upgrateProfile")
    public void  upgrateProfile(UserDTO userDTO, UUID id){
      try {
          usuarioServicio.updateUser(userDTO,id);
      } catch (RuntimeException e) {
          throw new RuntimeException(e);
      }

    }

    @GetMapping("/deleteUser")
    public void deleteProfile(UUID id){
        try {
            usuarioServicio.deleteUser(id);
        }catch (Exception e){
            new RuntimeException(e.getMessage());
        }
    }
}
