import java.io.*;

public class risingCity {
    MinHeap minheap = new MinHeap();
    RBTree rbTree = new RBTree();
    /**@param item is the new item which will be inserted into Minheap
     * Beside this, another red black tree node will be created, and it will consistently connect with it*/
    public void insert(BuildingInfo item) throws Exception{
        RBNode rbn = new RBNode(item);
        rbTree.insert(rbn);
        minheap.insert(item);
    }

    /** @param item is the building which is already be constructed but not finished yet. This building gets increment
     * of its executation time, reupdate the connected executation time in the RBtree also, and
     * it get reinserted*/
    public void update(BuildingInfo item){
        minheap.insert(item);
    }

    /**@param buildingnum is the target of the searching algorithm to be run in red black tree.
     * @return a String snippet which will be stored in correct format output*/
    public String print(int buildingnum){
            RBNode found = rbTree.search(rbTree.getRoot(),buildingnum);
            if(found != null){
                return "(" + found.getB() +"," +found.getE() + "," + found.getT() + ")";
            }
            else{
                return "(0,0,0)";
            }
    }

    /**
     * @param buildingnum1 is the min building number, inclusively, targeting the building which will be selected
     * @param buildingnum2 is the max building number, inclusively, targeting the building which will be selected
     * @return a string snippet which will be stored in correct format output
     */
    public String print(int buildingnum1,int buildingnum2){
            String content = rbTree.searchBetween(rbTree.getRoot(),buildingnum1,buildingnum2);
            if(!content.isEmpty()){
                return content.substring(0,content.length()-1);
            }
            else{
                return "(0,0,0)";
            }
    }


    /** @param command is the command after read file, reformat, and check the execution time is valid
     * @param res is the output stringbuilder, which will store the corresponding output depend on the command.
     */
    public void executecommand(String[] command, StringBuilder res) throws Exception{
        String[] inputs = command[1].trim().replaceAll("[()]", " ").split(" ");
        switch(inputs[0]) {
            case "Insert":
                String[] parameters = inputs[1].split(",");
                BuildingInfo item = new BuildingInfo(Integer.parseInt(parameters[0]),0, Integer.parseInt(parameters[1]));
                insert(item);
                break;
            case "PrintBuilding":
                String[] buildingnum = inputs[1].split(",");
                if (buildingnum.length == 1) {
                    res.append(print(Integer.parseInt(buildingnum[0])) + "\n");
                } else {
                    res.append(print(Integer.parseInt(buildingnum[0]), Integer.parseInt(buildingnum[1])) + "\n");
                }
                break;
            default:
                break;
        }
    }

    /**
     * @param args arg[0] take in a file name as input file
     */
    public static void main(String[] args){
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(args[0]));
        }
        catch(Exception e){
            System.out.println("No Such File " + args[0]);
        }

        /** after setting up reading pipe,
         * */
        try{
            int globalCounter = 0;
            int cumulativeincrement = 0;
            String line = reader.readLine();
            risingCity interpretor = new risingCity();
            StringBuilder res = new StringBuilder();
            BuildingInfo curB = null;
            /** keep reading or wait and construct the house until, either, input file, heap, and current
             * working building are empty.
             * */
            while(curB != null || line != null || interpretor.minheap.peek() != null){
                /**Execuate command first if global counter match the command line*/
                if(line != null) {
                    String[] command = line.split(":");
                    if(Integer.parseInt(command[0]) == globalCounter){
                        interpretor.executecommand(command,res);
                        line = reader.readLine();
                    }
                }

                /**If have building working on or Heap is not empty yet, we need to work on it*/
                if(curB != null || interpretor.minheap.peek() != null){
                    /**If execution time = total time of current building we are working on, remove it and also
                     * remove it from heap*/
                    if(curB != null && curB.getE() == curB.getT()){
                        interpretor.rbTree.delete(curB.point);
                        res.append("(" + curB.getB() +"," +globalCounter+ ")" + "\n");
                        curB = null;
                        cumulativeincrement = 0;
                    }
                    /**not yet finished, but we already did 5 days, reinsert it back into the heap(here I call update to
                     * avoid confusion)*/
                    else if(cumulativeincrement == 5){
                        interpretor.update(curB);
                        cumulativeincrement = 0;
                        curB = null;
                    }
                    /**else, we need to recheck the min element in the heap and pull it out of the heap to work on it*/
                    if(curB != null || interpretor.minheap.peek() != null){
                        if(cumulativeincrement == 0){
                            curB = interpretor.minheap.pop();
                        }
                        curB.setE(curB.getE() + 1);
                        curB.point.setE(curB.getE());
                        cumulativeincrement++;
                    }
                }
                globalCounter++;
            }

            /**all contents are stored in StringBuilder res, we then write all these strings to the output file*/

            File outfile = new File("output_file.txt");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile)));
            writer.write(res.toString());
            reader.close();
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
