package model;
public class Company{
    private int companyId;
    private String name;
    private String location;
    private String contact;
    public Company(){
    }
    public Company(int companyId,String name,String location,String contact){
        this.companyId=companyId;
        this.name=name;
        this.location=location;
        this.contact=contact;
    }
    public int getCompanyId(){return companyId;}
    public void setCompanyId(int companyId){this.companyId=companyId;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getLocation(){return location;}
    public void setLocation(String location){this.location=location;}
    public String getContact(){return contact;}
    public void setContact(String contact){this.contact=contact;}
    @Override
    public String toString(){
        return companyId+" | "+name+" | "+location+" | "+contact;
    }
}