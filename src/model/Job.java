package model;
public class Job{
    private int jobId;
    private int companyId;
    private String role;
    private double packageLpa;
    private double minCgpa;
    private int maxBacklogs;
    private String allowedDepartments;
    private String requiredSkills;
    public Job(){
    }
    public Job(int jobId,int companyId,String role,double packageLpa,double minCgpa,int maxBacklogs,String allowedDepartments,String requiredSkills){
        this.jobId=jobId;
        this.companyId=companyId;
        this.role=role;
        this.packageLpa=packageLpa;
        this.minCgpa=minCgpa;
        this.maxBacklogs=maxBacklogs;
        this.allowedDepartments=allowedDepartments;
        this.requiredSkills=requiredSkills;
    }
    public int getJobId(){return jobId;}
    public void setJobId(int jobId){this.jobId=jobId;}
    public int getCompanyId(){return companyId;}
    public void setCompanyId(int companyId){this.companyId=companyId;}
    public String getRole(){return role;}
    public void setRole(String role){this.role=role;}
    public double getPackageLpa(){return packageLpa;}
    public void setPackageLpa(double packageLpa){this.packageLpa=packageLpa;}
    public double getMinCgpa(){return minCgpa;}
    public void setMinCgpa(double minCgpa){this.minCgpa=minCgpa;}
    public int getMaxBacklogs(){return maxBacklogs;}
    public void setMaxBacklogs(int maxBacklogs){this.maxBacklogs=maxBacklogs;}
    public String getAllowedDepartments(){return allowedDepartments;}
    public void setAllowedDepartments(String allowedDepartments){this.allowedDepartments=allowedDepartments;}
    public String getRequiredSkills(){return requiredSkills;}
    public void setRequiredSkills(String requiredSkills){this.requiredSkills=requiredSkills;}
    @Override
    public String toString(){
        return jobId+" | Company: "+companyId+" | "+role+" | "+packageLpa+" LPA | Min CGPA: "+minCgpa+" | Max backlogs: "+maxBacklogs+" | Depts: "+allowedDepartments+" | Skills: "+requiredSkills;
    }
}