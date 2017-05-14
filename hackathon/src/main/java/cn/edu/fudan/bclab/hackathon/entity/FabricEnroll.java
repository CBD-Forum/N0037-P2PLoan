package cn.edu.fudan.bclab.hackathon.entity;


public class FabricEnroll {
    private String enrollId;
    private String enrollSecret;

    public String getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(String enrollId) {
        this.enrollId = enrollId;
    }

    public String getEnrollSecret() {
        return enrollSecret;
    }

    public void setEnrollSecret(String enrollSecret) {
        this.enrollSecret = enrollSecret;
    }

    @Override
    public String toString(){
        return "enroll :"+"{enrollId : "+enrollId+","+" enrollSecret :"+enrollSecret+"}";
    }
}
