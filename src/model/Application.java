package model;
public class Application{
    private int applicationId;
    private int studentId;
    private int jobId;
    private String status;
    private String appliedDate;
    public Application(){
    }
    public Application(int applicationId,int studentId,int jobId,String status,String appliedDate){
        this.applicationId=applicationId;
        this.studentId=studentId;
        this.jobId=jobId;
        this.status=status;
        this.appliedDate=appliedDate;
    }
    public int getApplicationId(){return applicationId;}
    public void setApplicationId(int applicationId){this.applicationId=applicationId;}
    public int getStudentId(){return studentId;}
    public void setStudentId(int studentId){this.studentId=studentId;}
    public int getJobId(){return jobId;}
    public void setJobId(int jobId){this.jobId=jobId;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
    public String getAppliedDate(){return appliedDate;}
    public void setAppliedDate(String appliedDate){this.appliedDate=appliedDate;}
    @Override
    public String toString(){
        return applicationId+" | Student: "+studentId+" | Job: "+jobId+" | "+status+" | "+appliedDate;
    }
}