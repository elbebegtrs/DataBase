package andrescamargo.info.database1;

/**
 * Created by Dre on 6/3/2016.
 */
public class Users {
    private String fname,lname,userName,password;
    private int age;
    private String message;

    public Users(String fname,String lname, int age, String userName, String password){
        this.fname=fname;
        this.lname=lname;
        this.age=age;
        this.userName=userName;
        this.password=password;
        message=fname;
    }

    public String getFname() {
        return fname;
    }

   // public String getLname() {
    //    return lname;
   // }

    //public String getUserName() {
    //    return userName;
    //}

   // public String getPassword() {
    //    return password;
   // }

    //public int getAge() {
   //     return age;
   // }

    //public void setFname(String fname) {

    //    this.fname = fname;
    //}

    //public void setLname(String lname) {
    //    this.lname = lname;
    //}

    //public void setUserName(String userName) {
   //     this.userName = userName;
  //  }

    //public void setPassword(String password) {
   //     this.password = password;
  //  }

    //public void setAge(int age) {
   //     this.age = age;
  //  }

    //public void setMessage(String message){
     //   this.message=message;
    //}
    //public String message(){
    //    return message;
   // }
}
