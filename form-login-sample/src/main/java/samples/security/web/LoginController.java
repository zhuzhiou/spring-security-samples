package samples.security.web;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @GetMapping(path = "/login")
    public String doGet(HttpServletRequest request, Model model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("error")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                String errorMsg = ex != null ? ex.getMessage() : "none";
                model.addAttribute("errorMsg", errorMsg);
            }
        }
        return "login";
    }
}
