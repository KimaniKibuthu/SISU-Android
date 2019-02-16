package kimani.com.sisu.utils;


public class NetworkResponse {
    public boolean is_loading;
    public String message;
    public int response_code;

    public NetworkResponse(){
        is_loading=false;
        this.response_code=0;
    }

    public NetworkResponse(boolean is_loading,String message, int response_code){
        this.is_loading=is_loading;
        this.message=message;
        this.response_code=response_code;
    }

    public NetworkResponse(boolean is_loading){
        this.is_loading=is_loading;
        this.response_code=0;
    }
}
