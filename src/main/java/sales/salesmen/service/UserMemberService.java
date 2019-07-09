package sales.salesmen.service;

public interface UserMemberService {
    //生成
    String generateAuthCode(String phonenum);
    //检验
    boolean verifyAuthCode(String phonenum,String authCoe);
}
