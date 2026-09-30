package model;
public class Student{
    private int studentId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String department;
    private double cgpa;
    private int backlogs;
    private String skills;
    // Empty constructor
    public Student(){
    }
    // Constructor with all fields
    public Student(int studentId, String name, String email, String password,
                   String phone, String department, double cgpa,
                   int backlogs, String skills){
        this.studentId=studentId;
        this.name=name;
        this.email=email;
        this.password=password;
        this.phone=phone;
        this.department=department;
        this.cgpa=cgpa;
        this.backlogs=backlogs;
        this.skills=skills;
    }
    public int getStudentId(){ 
    	return studentId; 
    }
    public void setStudentId(int studentId){
    	this.studentId=studentId;
    }
    public String getName(){
    	return name;
    }
    public void setName(String name){
    	this.name=name; 
    }
    public String getEmail(){
    	return email; 
    }
    public void setEmail(String email){ 
    	this.email=email; 
    }
    public String getPassword(){ 
    	return password; 
    }
    public void setPassword(String password){ 
    	this.password=password;
    }
    public String getPhone(){ 
    	return phone;
    }
    public void setPhone(String phone){
    	this.phone=phone; 
    }
    public String getDepartment(){
    	return department; 
    }
    public void setDepartment(String department){
    	this.department=department; 
    }
    public double getCgpa(){
    	return cgpa; 
    }
    public void setCgpa(double cgpa){
    	this.cgpa=cgpa;
    }
    public int getBacklogs(){
    	return backlogs; 
    }
    public void setBacklogs(int backlogs){
    	this.backlogs=backlogs; 
    }
    public String getSkills(){
    	return skills; 
    }
    public void setSkills(String skills){
    	this.skills=skills; 
    }
    @Override
    public String toString() {
        return studentId+" | "+name+" | "+department+" | CGPA: "+ cgpa+" | Backlogs: "+backlogs+" | Skills: "+ skills;
    }
}