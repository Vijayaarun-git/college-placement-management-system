package util;
public class Validator{
    public static boolean notEmpty(String s){
        return s!=null&&!s.trim().isEmpty();
    }
    public static boolean isValidEmail(String email){
        return email!=null&&email.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$");
    }
    public static boolean isValidPhone(String phone){
        return phone!=null&&phone.matches("\\d{10}");
    }
    public static boolean isValidCgpa(double cgpa){
        return cgpa>=0&&cgpa<=10;
    }
}