package cn.edu.fudan.bclab.hackathon.util;

/**
 * This is used to return a error to the client
 */

public class ErrorHolder
{
    public String errorMessage;

    public ErrorHolder(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString()
    {
        return "ErrorHolder{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }


}

