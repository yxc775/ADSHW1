import java.io.*;
public class risingCity {
    MinHeap minheap = new MinHeap();
    RBTree rbTree = new RBTree();
    public void insert(int buildingnum,int totaltime){
        BuildingInfo item = new BuildingInfo(buildingnum,0,totaltime);
        RBNode rbn = new RBNode(item);
        rbTree.insert(rbn);
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
            while(line != null){
                String[] command = line.split(":");
                //do something here
                while(Integer.parseInt(command[0]) > globalCounter){
                    globalCounter += 1;
                    if(interpretor.minheap.peek()!= null){
                        cumulativeincrement += 1;
                        BuildingInfo mayUpdate = interpretor.minheap.peek();
                        if (mayUpdate.getE() + cumulativeincrement >= mayUpdate.getT()) {
                            interpretor.minheap.pop();
                            interpretor.rbTree.delete(mayUpdate.point);
                            res.append("(" + mayUpdate.getB() +"," +globalCounter + ")" + "\n");
                            cumulativeincrement = 0;
                        }
                        if(cumulativeincrement >= 5){
                            interpretor.minheap.update(5);
                            cumulativeincrement = 0;
                        }
                    }
                }
                String[] inputs = command[1].trim().replaceAll("[()]", " ").split(" ");
                switch(inputs[0]){
                    case "Insert":
                        String[] parameters = inputs[1].split(",");
                        interpretor.insert(Integer.parseInt(parameters[0]),Integer.parseInt(parameters[1]));
                        break;
                    case "PrintBuilding":
                        String[] buildingnum = inputs[1].split(",");
                        if(buildingnum.length == 1){
                            res.append(interpretor.print(Integer.parseInt(buildingnum[0])) + "\n");
                        }
                        else{
                            res.append(interpretor.print(Integer.parseInt(buildingnum[0]),Integer.parseInt(buildingnum[1]))+"\n");
                        }
                        break;
                    default:
                        break;
                }

                line = reader.readLine();
            }

            while(interpretor.minheap.peek() != null){
                BuildingInfo mayUpdate = interpretor.minheap.peek();
                cumulativeincrement += 1;
                globalCounter += 1;
                if (mayUpdate.getE() + cumulativeincrement >= mayUpdate.getT()) {
                    mayUpdate.point.getB();
                    interpretor.rbTree.delete(mayUpdate.point);
                    res.append("(" + mayUpdate.getB() +"," +globalCounter+ ")" + "\n");
                    interpretor.minheap.pop();
                    cumulativeincrement = 0;
                }
                else if(cumulativeincrement >= 5){
                    interpretor.minheap.update(5);
                    cumulativeincrement = 0;
                }
                else{

                }
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
