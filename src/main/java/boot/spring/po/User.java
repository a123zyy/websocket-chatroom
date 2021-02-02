package boot.spring.po;

public class User {
    int uid;
	
	String name;
	
	public User() {
		super();
	}

	public User(int uid, String name) {
		super();
		this.uid = uid;
		this.name = name;
	}

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
