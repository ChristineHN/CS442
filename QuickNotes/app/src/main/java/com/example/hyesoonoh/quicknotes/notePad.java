package com.example.hyesoonoh.quicknotes;
/**
 * Created by HyeSooNoh on 1/28/18.
 */

public class notePad{

    private String timestamp;
    private String inputText;


    public String getInputText(){
        return inputText;
    }

    public void setInputText(String inputText){
        this.inputText = inputText;
    }

    public String getTimeStamp(){
        return timestamp;
    }

    public void setTimeStamp(String timestamp){
        this.timestamp = timestamp;
    }

    public String toString(){
        return inputText + "@" + timestamp;
    }


}
