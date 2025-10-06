package iv.synapseuser.api.controllers;

import iv.synapseuser.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        User response = userService.updateUser(user);
        return ResponseEntity.ok(response);
    }
}
