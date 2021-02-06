package boot.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service("LoginRedisTemplateService")
@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout=5)
public class LoginRedisTemplateService {

    private static String LOGIN_USER_UId = "LOGIN:USER:UId:";

    @Autowired
    private RedisTemplate redisTemplate;

    public Boolean setUserid(int userid,String SessionId){
     redisTemplate.opsForValue().set(LOGIN_USER_UId+userid,userid+"_"+SessionId);
     return true;
    }

    public String getUserid(int userid){
        String uid = null;
        if (Objects.nonNull(redisTemplate.opsForValue().get(LOGIN_USER_UId+userid))){
            uid = (String) redisTemplate.opsForValue().get(LOGIN_USER_UId+userid);
        }
        return uid;
    }

    public void delUserid(int userid){
        redisTemplate.delete(LOGIN_USER_UId+userid);
    }

}
