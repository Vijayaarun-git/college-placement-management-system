package service;
import java.sql.*;
import dao.*;
import model.Application;
public class PlacementService{
    private ApplicationDAO applicationDAO=new ApplicationDAO();
    private InterviewDAO interviewDAO=new InterviewDAO();
    private PlacementDAO placementDAO=new PlacementDAO();
    public String scheduleInterview(int applicationId,String date,String time,String venue){
        if(time.length()==5) time=time+":00";
        try{
            Date.valueOf(date);
            Time.valueOf(time);
        }catch(IllegalArgumentException e){
            return "Invalid date or time. Use yyyy-mm-dd and HH:mm";
        }
        if(applicationDAO.getApplicationById(applicationId)==null) return "Application not found";
        if(!interviewDAO.scheduleInterview(applicationId,date,time,venue)) return "Could not schedule (an interview may already exist for this application)";
        applicationDAO.updateStatus(applicationId,"Interview Scheduled");
        return "Interview scheduled";
    }
    public String recordResult(int interviewId,String result){
        if(!result.equals("Passed")&&!result.equals("Failed")) return "Result must be Passed or Failed";
        int applicationId=interviewDAO.getApplicationIdByInterview(interviewId);
        if(applicationId==-1) return "Interview not found";
        interviewDAO.updateResult(interviewId,result);
        if(result.equals("Failed")){
            applicationDAO.updateStatus(applicationId,"Rejected");
            return "Result saved. Application marked Rejected";
        }
        return "Result saved. You can now select the student";
    }
    public String selectStudent(int applicationId){
        Application a=applicationDAO.getApplicationById(applicationId);
        if(a==null) return "Application not found";
        if(placementDAO.isPlaced(a.getStudentId())) return "Student is already placed";
        if(!placementDAO.addPlacement(a.getStudentId(),a.getJobId())) return "Could not create the placement record";
        applicationDAO.updateStatus(applicationId,"Selected");
        return "Student selected and placement recorded";
    }
}