package service;

import java.util.List;

/**
 * Created by CharlesGao on 15-08-10.
 */
public interface UserService {

    public void userLogin(String loginName, String loginPassword) throws Exception;
    public void userRegister(String loginName, List<String> stringList) throws Exception;

}
