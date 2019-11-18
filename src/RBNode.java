public class RBNode {
    BuildingInfo keystruct;
    int buildingnum;
    int exetime;
    int totaltime;
    boolean isRed;
    RBNode left;
    RBNode right;
    RBNode parent;
    public RBNode(BuildingInfo newone){
        keystruct = newone;
        buildingnum = newone.getB();
        exetime = newone.getE();
        totaltime = newone.getT();
        newone.point = this;
        isRed = true;
        left = null;
        right = null;
        parent = null;
    }

    public int getB(){
        return buildingnum;
    }

    public void setB(int newB){
        buildingnum = newB;
    }

    public int getE(){
        return exetime;
    }

    public void setE(int newE){
        exetime =  newE;
    }

    public int getT(){
        return totaltime;
    }

    public void setT(int newT){
        totaltime = newT;
    }

    public RBNode getParentBroth(){
        if(parent == null || parent.parent == null){
            return null;
        }

        if(parent.isLeftchild()){
            return parent.parent.right;
        }
        else{
            return parent.parent.left;
        }
    }

    public boolean isLeftchild(){
        return this == parent.left;
    }

    public RBNode getSib(){
        if(parent == null){
            return null;
        }

        if(isLeftchild()){
            return parent.right;
        }
        else{
            return parent.left;
        }
    }

    public void replaceWiththis(RBNode inserted){
        BuildingInfo keystruct = inserted.keystruct;
        if(parent != null){
            if(isLeftchild()){
                parent.left = inserted;
            }
            else{
                parent.right = inserted;
            }
        }
        inserted.parent = this.parent;
        parent = inserted;
    }

    public boolean hasRedbelow(){
        return (left != null && left.isRed) || (right != null && right.isRed);
    }

}
