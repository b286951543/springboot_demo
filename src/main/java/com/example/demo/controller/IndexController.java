package com.example.demo.controller;

import com.example.demo.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

//    @Autowired
//    private SessionDAO sessionDAO;

    // 没有权限，则报异常，在 ExceptionController 捕获
//    @RequiresUser // 需要登录
//    @RequiresGuest // 需要游客身份
//    @RequiresRoles(value={"admin", "user"}, logical= Logical.OR) // 当前需要的角色
    @RequiresPermissions("p:user:index") // 表示当前需要的权限，但如果当前拥有 p:user 权限，也能访问该url
    @RequestMapping("/index")
    public String index(Model model) {
        // 登录成后，即可通过Subject获取登录的用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", user);
        return "index";
    }

//    @RequiresPermissions("p:admin")
//    @RequestMapping("/userOnline/list")
//    @ResponseBody
//    public List<UserOnline> list() {
//        List<UserOnline> list = new ArrayList<>();
//        Collection<Session> sessions = sessionDAO.getActiveSessions();
//        for (Session session : sessions) {
//            UserOnline userOnline = new UserOnline();
//            User user = new User();
//            SimplePrincipalCollection principalCollection;
//            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
//                continue;
//            } else {
//                principalCollection = (SimplePrincipalCollection) session
//                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//                user = (User) principalCollection.getPrimaryPrincipal();
//                userOnline.setUsername(user.getUsername());
//                userOnline.setUserId(user.getId().toString());
//            }
//            userOnline.setSessionId((String) session.getId());
//            userOnline.setHost(session.getHost());
//            userOnline.setStartTime(session.getStartTimestamp());
//            userOnline.setLastTime(session.getLastAccessTime());
//            Long timeout = session.getTimeout();
//            if (timeout == 0l) {
//                userOnline.setStatus("离线");
//            } else {
//                userOnline.setStatus("在线");
//            }
//            userOnline.setTimeout(timeout);
//            list.add(userOnline);
//        }
//        return list;
//    }
//
//    @RequiresPermissions("p:admin")
//    @RequestMapping("/forceLogout")
//    @ResponseBody
//    public boolean forceLogout(String sessionId) {
//        Session session = sessionDAO.readSession(sessionId);
//        session.setTimeout(0);
//        return true;
//    }
}
