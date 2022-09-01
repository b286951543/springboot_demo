package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.config.AppConfig;
import com.example.demo.service.HelloService;
import com.example.demo.service.HelloService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class HelloController {

    // yml 属性注入
    @Value("${app.name}")
    private String name;

    @Value("${app.version}")
    private String version;

    // yml 属性注入
    @Autowired
    private AppConfig appConfig;

    @Autowired // 根据类型注入
    private HelloService helloService;

    @Autowired
    @Qualifier("helloServiceImpl2") // 根据名称注入
    private HelloService2 helloService2;

    // http://localhost:8080/api/v1/hello
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!" + name + version;
    }

    @RequestMapping("/hello1")
    public String hello1(){
        return "Hello World!" + appConfig.getName() + appConfig.getVersion();
    }

    @RequestMapping("/hello2")
    public String hello2(){
        helloService.hello();
        helloService2.hello2();
        return "Hello World!";
    }

    // 只能get请求
    @RequestMapping(value = "/hello3", headers = {}, method = RequestMethod.GET)
    public String hello3(){
        helloService.hello();
        return "Hello World!";
    }

    // http://localhost:8080/api/v1/hello4/10
    @GetMapping("/hello4/{id}")
    public String hello4(@PathVariable String id){
        return "Hello World!" + id;
    }

    // http://localhost:8080/api/v1/hello5/user?name=10
    @GetMapping("/hello5/user")
    public String hello5(@RequestParam String name){
        return "Hello World!" + name;
    }

    /**
     * 使用postman 时，需要使用body的raw的json来传参数
     * {
     *     "id": "10",
     *     "cover": "封面",
     *     "title": "标题",
     *     "content": "内容"
     * }
     *
     * 请求头：Content-Type=application/json
     *
     * @param req
     * @return
     */
    @PostMapping("/post/create")
    public String create(@RequestBody Post req){
        return "Hello World!" + req.getId() + "," + req.getCover() + "," + req.getTitle() + "," + req.getContent();
    }

    @PostMapping("/post/update")
    public String update(@RequestBody Post req){
        return "Hello World!" + req.getId() + "," + req.getCover() + "," + req.getTitle() + "," + req.getContent();
    }

    /**
     * 使用body的raw的json来传递参数
     * {"name":"1234"}
     * 请求头：Content-Type=application/json
     * @param jsonStr
     * @return
     */
    @PostMapping("/hello6/create")
    public String hello6(@RequestBody String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String name = jsonObject.optString("name"); // 如果使用getString(name)，那么name不存在的话，会报错
        System.out.println(name);
        return "Hello World!" + jsonStr;
    }

    /**
     * 使用body的raw的json来传递参数
     * {"name":"1234"}
     * 请求头：Content-Type=application/json
     * @param map
     * @return
     */
    @PostMapping("/hello6_2/create")
    public String hello6_2(@RequestBody Map<String, String> map) { // 不能使用 JSONArray 来接收参数，因为接收不到
        String name = map.get("name");
        System.out.println(name);
        return "Hello World!" + name;
    }

    // 获取请求头的内容
    @GetMapping("/hello7")
    public String hello7(HttpServletRequest req){
        String str = null;
        System.out.println(str.length());
        return "Hello World!" + req.getHeader("Cookie");
    }

    @GetMapping("/hello/error")
    public String errorHello(HttpServletRequest req) throws Exception {
        try{
            String str = null;
            System.out.println(str.length());
        }catch (Exception e){
            // 由 ExceptionController 类统一处理
            Exception e2 = new Exception("errorHello 出错了", e);
            throw e2;
        }

        return "errorHello";
    }
}
