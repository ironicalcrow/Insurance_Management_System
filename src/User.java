public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private int age;
    private String role;

    User(String name, String email, String phoneNumber, int age, String role) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getAge() {
        return age;
    }
    public String getRole() {
        return role;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setRole(String role) {
        this.role = role;
    }


}
