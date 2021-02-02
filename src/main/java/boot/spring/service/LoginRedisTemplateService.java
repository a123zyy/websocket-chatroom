package boot.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("LoginRedisTemplateService")
public class LoginRedisTemplateService {

    private String LOGIN_USER_UId = "LOGIN:USER:UId:";

    @Autowired
    private RedisTemplate redisTemplate;

    public Boolean setUserid(int userid){
     redisTemplate.opsForValue().set(LOGIN_USER_UId+userid,String.valueOf(userid));
     return true;
    }

    public Boolean getUserid(int userid){
        if (Objects.nonNull(redisTemplate.opsForValue().get(LOGIN_USER_UId+userid))){
            String uid = (String) redisTemplate.opsForValue().get(LOGIN_USER_UId+userid);
            return Integer.parseInt(uid) == userid?false:true;
        }
        return true;
    }

    public void delUserid(int userid){
        redisTemplate.delete(LOGIN_USER_UId+userid);
    }

}
