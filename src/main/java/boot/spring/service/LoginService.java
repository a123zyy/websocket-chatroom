package boot.spring.service;


import boot.spring.po.Staff;

public interface LoginService {
    Staff getpwdbyname(String name);
	Integer getUidbyname(String name);
	String getnamebyid(int id);
}
