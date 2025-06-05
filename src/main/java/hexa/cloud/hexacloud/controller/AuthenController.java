// package hexa.cloud.hexacloud.controller;
// import hexa.cloud.hexacloud.model.User;
// import hexa.cloud.hexacloud.service.UserService;

// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthenController {

//     @Autowired
//     private UserService userService;

//     @PostMapping("/register")
//     public ResponseEntity<?> register(@RequestBody User user) {
//         User created = userService.registerUser(user);
//         return ResponseEntity.ok(created);
//     }

//     @PostMapping("/login")
//     public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
//         String username = loginData.get("username");
//         String password = loginData.get("password");
//         User user = userService.login(username, password);
//         return ResponseEntity.ok(user);
//     }
// }
