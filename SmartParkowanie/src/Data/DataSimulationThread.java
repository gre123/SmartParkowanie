
package Data;

public class DataSimulationThread implements Runnable{

    Simulator simulation;
    private boolean moveOn = true;
    
    public DataSimulationThread(Simulator simulation)
    {
        this.simulation = simulation;
    }
    public void stopIt()
    {
        moveOn = false;
    }
    
    @Override
    public void run() {
        for(int i=0;i<1000;i++)
        {
            simulation.doIteration();
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    
}
