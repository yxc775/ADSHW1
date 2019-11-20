public class RBNode {
    /**a pointer to the unit in MinHeap
     * */
    BuildingInfo keystruct;
    int buildingnum;
    int exetime;
    int totaltime;
    boolean isRed;
    RBNode left;
    RBNode right;
    RBNode parent;

    /** initialize a basic unit that is managed and manipulated by a Red black tree,
     * it connects to the BuildingInfo, a MinHeap unit, through
     * pointer
     * @param newone is the BuildingInfo Unit to connect to
     */
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

    /**get the parallel sibling of parent node, if not exist return null
     * */
    public RBNode getUnc(){
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

    /**get sibling of this node
     * */
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

    /** This will insert the new node called
     * @param inserted to replace current RBNode object position in RBtree, a
     * Further operation will push the current RBNode object down recursively.
     */
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

    /**Check whether there are any Red child below current RBNode
     * */
    public boolean hasRedbelow(){
        return (left != null && left.isRed) || (right != null && right.isRed);
    }

}
