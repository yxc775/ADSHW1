import java.io.*;

public class risingCity {
    MinHeap minheap = new MinHeap();
    RBTree rbTree = new RBTree();
    public void insert(BuildingInfo item){
        RBNode rbn = new RBNode(item);
        rbTree.insert(rbn);
        minheap.insert(item);
    }

    public void update(BuildingInfo item){
        minheap.insert(item);
    }

    public String print(int buildingnum){
            RBNode found = rbTree.search(rbTree.getRoot(),buildingnum);
            if(found != null){
                return "(" + found.getB() +"," +found.getE() + "," + found.getT() + ")";
            }
            else{
                return "(0,0,0)";
            }
    }

    public String print(int buildingnum1,int buildingnum2){
            String content = rbTree.searchBetween(rbTree.getRoot(),buildingnum1,buildingnum2);
            if(!content.isEmpty()){
                return content.substring(0,content.length()-1);
            }
            else{
                return "(0,0,0)";
            }
    }

    public void executecommand(String[] command, StringBuilder res){
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

    public static void main(String[] args){
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(args[0]));
        }
        catch(Exception e){
            System.out.println("No Such File " + args[0]);
        }

        try{
            int globalCounter = 0;
            int cumulativeincrement = 0;
            String line = reader.readLine();
            risingCity interpretor = new risingCity();
            StringBuilder res = new StringBuilder();
            BuildingInfo curB = null;
            while(curB != null || line != null || interpretor.minheap.peek() != null){
                if(line != null) {
                    String[] command = line.split(":");
                    if(Integer.parseInt(command[0]) == globalCounter){
                        interpretor.executecommand(command,res);
                        line = reader.readLine();
                    }
                }

                if(curB != null || interpretor.minheap.peek() != null){
                    if(curB != null && curB.getE() == curB.getT()){
                        interpretor.rbTree.delete(curB.point);
                        res.append("(" + curB.getB() +"," +globalCounter+ ")" + "\n");
                        curB = null;
                        cumulativeincrement = 0;
                    }
                    else if(cumulativeincrement == 5){
                        interpretor.update(curB);
                        cumulativeincrement = 0;
                        curB = null;
                    }
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
