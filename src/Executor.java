public class Executor {

    private IRider _IRider;


    public Executor(IRider rider){
        _IRider = rider;
    }

    public void StartRide(){
        _IRider.Ride();
    }
}
