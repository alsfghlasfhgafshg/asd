package sales.salesmen.service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sales.salesmen.service.RedisService;
import sales.salesmen.service.UserMemberService;

import java.util.Random;

@Service
public class UserMemberServiceImpl implements UserMemberService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String PREFIX_AUTHCODE;
    @Value("${redis.key.expire.authCode}")
    private Long EXPIRE_SECONDS;

    @Override
    public String generateAuthCode(String phonenum) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        redisService.set(PREFIX_AUTHCODE+phonenum,sb.toString());
        redisService.expire(PREFIX_AUTHCODE+phonenum,EXPIRE_SECONDS);
        return "验证码为"+sb.toString();
    }

    @Override
    public boolean verifyAuthCode(String phonenum, String authCode) {
        if (StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = redisService.get(PREFIX_AUTHCODE+phonenum);
        return authCode.equals(realAuthCode);
    }
}
