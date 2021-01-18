package me.ryan.chatwebapp.controller;

import me.ryan.chatwebapp.util.ActiveUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
public class WebSocketConnectionRestController {

    @Autowired
    private ActiveUserManager activeSessionManager;

    @PostMapping("/rest/user-connect")
    public String userConnect(HttpServletRequest request, @ModelAttribute("username") String username) {
        String remoteAddress = "";
        if (request != null) {
            remoteAddress = request.getHeader("Remote_Addr");
            if (StringUtils.isEmpty(remoteAddress)) {
                remoteAddress = request.getHeader("X-FORWARDED-FOR");
                if (remoteAddress == null || "".equals(remoteAddress)) {
                    remoteAddress = request.getRemoteAddr();
                }
            }
        }

        activeSessionManager.add(username, remoteAddress);
        return remoteAddress;
    }

    @PostMapping("/rest/user-disconnect")
    public String userDisconnect(@ModelAttribute("username") String username) {
        activeSessionManager.remove(username);
        return "disconnected";
    }

    @GetMapping("/rest/active-users-except/{userName}")
    public Set<String> getActiveUsersExceptCurrentUser(@PathVariable String userName) {
        return activeSessionManager.getActiveUsersExceptCurrentUser(userName);
    }
}
