public class BuildingInfo {
    private int buildingNum;
    private int executed_time;
    private int total_time;
    /**a pointer to the unit in RBTree
     * */
    public RBNode point;

    /**
     * created as basic data unit which will be managed with input files and MinHeap Tree
     * @param b indicates building number
     * @param e indicates execution time
     * @param t indicates total time
     * This unit will also has a pointer pointed to its connected component called RBNode, which is a mirror unit inserted int Red Black Tree.
     * The pointer will be keep tracked and updated correspondingly during every tree operation in both trees.
     */
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
