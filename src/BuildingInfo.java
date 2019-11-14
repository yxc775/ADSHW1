public class BuildingInfo {
    private int buildingNum;
    private int executed_time;
    private int total_time;
    public RBNode point;

    public BuildingInfo(int b, int e, int t){
        buildingNum = b;
        executed_time = e;
        total_time = t;
    }

    public int getB(){
        return buildingNum;
    }

    public void setB(int b){
        buildingNum = b;
    }

    public int getE(){
        return executed_time;
    }

    public void setE(int e){
        executed_time = e;
    }

    public int getT(){
        return total_time;
    }

    public void setT(int t){
        total_time = t;
    }

    @Override
    public String toString(){
        return "Building Num->"+ buildingNum + " ExecTime->" + executed_time + " TotalTime->" + total_time + " ";
    }
}
