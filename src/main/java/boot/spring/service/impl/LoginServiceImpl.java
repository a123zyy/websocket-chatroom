package boot.spring.service.impl;



import boot.spring.service.LoginRedisTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import boot.spring.mapper.LoginMapper;
import boot.spring.po.Staff;
import boot.spring.service.LoginService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service("loginservice")
public class LoginServiceImpl implements LoginService{
	@Autowired
	LoginMapper loginmapper;

    @Autowired
	LoginRedisTemplateService loginRedisTemplateService;

    @Override
	public Staff getpwdbyname(String name) {
		Staff s=loginmapper.getpwdbyname(name);
        return s!=null?s:null;
	}

    @Override
	public Integer getUidbyname(String name) {
		Staff s=loginmapper.getpwdbyname(name);
        return s!=null? s.getStaff_id():null;
	}
	@Override
	public String getnamebyid(int id) {
		Staff s=loginmapper.getnamebyid(id);
		return s!=null?s.getUsername():null;
	}

    @Override
    public void delRedisUid(String username) {
        loginRedisTemplateService.delUserid(loginmapper.getpwdbyname(username).getStaff_id());
        System.out.println("当前参数已经删除"+username);
    }


}
