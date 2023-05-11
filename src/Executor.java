public class Executor {

    private IRider process;

    public Executor(){

    }
    public void setProcess(IRider process){
        this.process= process;
    }

    public void StartRide(){
        process.Ride();
    }
}
