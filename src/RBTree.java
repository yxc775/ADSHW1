public class RBTree {
    private RBNode root;
    public RBTree(){
        root = null;
    }
    public RBNode getRoot(){
        return  root;
    }


    /** insert a RBNode
     * @param x
     * into this Red Black tree, x should already be assigned a pointer to corresponding unit in MinHeap
     */
    public void insert(RBNode x) throws Exception{
        if(root != null){
            RBNode parent = root;
            while(parent != null){
                if(x.getB() < parent.getB()){
                    if(parent.left == null){
                        break;
                    }
                    else{
                        parent = parent.left;
                    }
                }
                else if(x.getB() == parent.getB()){
                    throw new ArrayIndexOutOfBoundsException("Duplicate Building Number Encountered!");
                }
                else{
                    if(parent.right == null){
                        break;
                    }
                    else{
                        parent = parent.right;
                    }
                }
            }
            x.parent = parent;
            if(x.getB() < parent.getB()){
                parent.left = x;
            }
            else{
                parent.right = x;
            }
            balanceDoubleRedAt(x);
        }
        else{
            x.isRed = false;
            root = x;
        }
    }

    /** a helper function which can handle the situation if Red black tree is empty
     * @param b indicates the RBNode to delete. Usually get from the unit pointer from MinHeap to skip search step.
     */
    public void delete(RBNode b){
        //System.out.println("node from point " + b.getB());
        if(root == null){
            return;
        }
        RBNode todelete = b;

        deleteRBNode(todelete);
    }

    /**search a corresponding unit with target BuildingNumber,
     * @param cur  indicates the first node to begin the search, to use in recursive
     * @param buildingNumber indicates the building number
     * @return
     */
    public RBNode search(RBNode cur, int buildingNumber){
        if(cur == null){
            return null;
        }
        else if(cur.getB() == buildingNumber){
            return cur;
        }
        else{
            if(cur.getB() > buildingNumber){
                return search(cur.left,buildingNumber);
            }
            else{
                return search(cur.right,buildingNumber);
            }
        }
    }

    /**similar with search, but doing a binary search traversal to output a range of existed building
     *
     * @param cur indicates the first node to begin the search, to use in recursive
     * @param left Inclusive minimum Building number to include
     * @param right Inclusive maximum Building number to include
     * @return  is handle in String because this function to specifically designed to output a string including required BuildngInfo content*/
    public String searchBetween(RBNode cur,int left, int right){
        if(cur == null){
            return "";
        }
        StringBuilder content = new StringBuilder();

        if(left < cur.getB()){
            content.append(searchBetween(cur.left,left,right));
        }

        if(left <= cur.getB() && right >= cur.getB()){
            content.append("(" +cur.getB() + "," + cur.getE() + "," + cur.getT() + ")" + ",");
        }

        if(right > cur.getB()){
            content.append(searchBetween(cur.right,left,right));
        }

        return content.toString();
    }

    /** Rotate current node to a certain direction, if
     * @param node  the node to rotate
     * @param toLeft if true, rotate to left, if false rotate to right
     * E.g, rotate X to left
     *               X                                    b
     *             /   \                                 /    \
     *           a      b      ------> TO left         X        f
     *         /  \    /  \                           /   \    / \
     *       c     d   e   f                        a     e
     */
    private void rotateto(RBNode node, boolean toLeft){
        RBNode newparent = null;


        newparent = toLeft ? node.right : node.left;

        if(root == node){
            root = newparent;
        }
        node.replaceWiththis(newparent);
        if(toLeft) {
            node.right = newparent.left;

            if(newparent.left != null){
                newparent.left.parent = node;
            }
            newparent.left = node;
        }
        else{
            node.left = newparent.right;
            if(newparent.right != null){
                newparent.right.parent = node;
            }
            newparent.right = node;
        }
    }

    /** helper function, swap the position of two RBNOde with in the tree
     * @param a   first RBNode
     * @param b   second RBNode
     */
    private void swapBuildingInfo(RBNode a, RBNode b){
        /** Save the original pointers here
         */
        BuildingInfo builda = a.keystruct;
        BuildingInfo buildb = b.keystruct;


        int tempbuildingnum = a.getB();
        int tempexe = a.getE();
        int temptotal = a.getT();
        a.setB(b.getB());
        a.setE(b.getE());
        a.setT(b.getT());
        b.setB(tempbuildingnum);
        b.setE(tempexe);
        b.setT(temptotal);
        /**Reassign the saved pointers, otherwise,
         * the pointer will not follow with the updated value.
         * This is to ensure unit in MinHeap is connected to correct one in the red black tree*/
        builda.point = b;
        buildb.point = a;
        b.keystruct = builda;
        a.keystruct = buildb;
    }

    private void swapColor(RBNode a, RBNode b){
        boolean temp = a.isRed;
        a.isRed = b.isRed;
        b.isRed = temp;
    }

    /**Do the corresponding LLr,LRr,RLr,RRr Color flipping
     * or corresponding LLb, LRb, RLb, and RRb rotation/
     * @param dr
     */
    private void balanceDoubleRedAt(RBNode dr){
        if(root != dr){
            RBNode parent = dr.parent;
            RBNode parentsparent = parent.parent;
            RBNode parentBroth = dr.getUnc();

            if(parent.isRed){
                if(parentBroth != null && parentBroth.isRed){
                    /**Flip color and recursively balance two red conditions in topper layer.
                     * */
                    parent.isRed = false;
                    parentBroth.isRed = false;
                    parentsparent.isRed = true;
                    balanceDoubleRedAt(parentsparent);
                }
                else{
                    /**Handle LL or LR situations, flip color or rotate
                     */
                    if(parent.isLeftchild()){
                        if(dr.isLeftchild()){
                            swapColor(parent,parentsparent);
                        }
                        else{
                            rotateto(parent,true);
                            swapColor(dr,parentsparent);
                        }
                        rotateto(parentsparent,false);
                    }
                    /**Handle RR or RL situations, flip color or rotate
                     */
                    else{
                        if(dr.isLeftchild()){
                            rotateto(parent,false);
                            swapColor(dr,parentsparent);
                        }
                        else{
                            swapColor(parent,parentsparent);
                        }
                        rotateto(parentsparent,true);
                    }
                }
            }
        }
        else{
            dr.isRed = false;
            return;
        }
    }

    /**Seasing the left most nodes starting from
     * @param item, which is the starting node to search
     * @return this node*/
    private RBNode leftbranchingUntilNoChild(RBNode item){
        RBNode temp = item;
        while(temp.left != null){
            temp = temp.left;
        }
        return temp;
    }


    /** This will get a node below this
     * @param toDelete  node to replace this position after deletion.
     * @return the new node.
     */
    private RBNode getFillInAfterDelete(RBNode toDelete){
        if(toDelete.left != null && toDelete.right != null) {
            return leftbranchingUntilNoChild(toDelete.right);
        }
        else if(toDelete.left != null){
            return toDelete.left;
        }
        else if(toDelete.right != null){
            return toDelete.right;
        }
        else{
            return null;
        }
    }


    /** Given the node pointer
     * @param toDelete , which is the node to delete, delete it and replace it with a suitable node
     * and perform balancing depend on situations.
     */
    private void deleteRBNode(RBNode toDelete){
        RBNode replaceWith = getFillInAfterDelete(toDelete);
        RBNode curparent = toDelete.parent;
        if(replaceWith == null){
            if(root == toDelete){
                root = null;
            }
            else{
                if((replaceWith == null || !replaceWith.isRed) && !toDelete.isRed){
                    balanceDoubleBlackAt(toDelete);
                }

                if(toDelete.isLeftchild()){
                    curparent.left = null;
                }
                else{
                    curparent.right = null;
                }
            }
        }
        else if(toDelete.left == null || toDelete.right == null) {
            if (root == toDelete) {
                /**If it only has one child, we are sure that the child node cannot be black node.
                 * Thus we can simply swith these two, and delete the child.
                 * Also keep updating the pointers connecting to the MinHeap
                 * */
                BuildingInfo replpoint = replaceWith.keystruct;
                toDelete.setB(replaceWith.getB());
                toDelete.setE(replaceWith.getE());
                toDelete.setT(replaceWith.getT());
                toDelete.left = null;
                toDelete.right = null;
                replpoint.point = toDelete;
                toDelete.keystruct = replpoint;
            } else {
                if (toDelete.isLeftchild()) {
                    curparent.left = replaceWith;
                } else {
                    curparent.right = replaceWith;
                }

                replaceWith.parent = curparent;

                if ((replaceWith == null || !replaceWith.isRed)  && !toDelete.isRed) {
                    balanceDoubleBlackAt(replaceWith);
                } else {
                    replaceWith.isRed = false;
                }
            }
        }
        else{
            swapBuildingInfo(replaceWith,toDelete);
            deleteRBNode(replaceWith);
        }
    }


    /**Do the corresponding Rb0,Rb1,Rb2, or Rr0, Rr1, and Rr2.
     * Recursively balance it depend on the situations.
     * @param db is the node which firstly causing the double black inbalance right after operation.
     */
    private void balanceDoubleBlackAt(RBNode db){
        if(root != db){
            RBNode sib = db.getSib();
            RBNode parent = db.parent;
            if(sib == null){
                balanceDoubleBlackAt(parent);
            }
            else{
                /**If sibiling is red, rotate and rebalance again. Rr(n) case.
                 */
                if(sib.isRed){
                    parent.isRed = true;
                    sib.isRed = false;
                    if(sib.isLeftchild()){
                        rotateto(parent,false);
                    }
                    else{
                        rotateto(parent,true);
                    }
                    balanceDoubleBlackAt(db);
                }
                else{
                    /** if sibiling does not have red childrien, we are good and keep balancing it from parent node
                     *  Else, we may have Rb(n) condition
                     */
                    if(sib.hasRedbelow()){
                        /**Need balance here if have red child between sibiling.
                         * */
                        if(sib.left != null && sib.left.isRed){
                            if(sib.isLeftchild()){
                                sib.left.isRed = sib.isRed;
                                sib.isRed = parent.isRed;
                                rotateto(parent,false);
                            }
                            else{
                                sib.left.isRed = parent.isRed;
                                rotateto(sib,false);
                                rotateto(parent,true);
                            }
                        }
                        else{
                            if(sib.isLeftchild()){
                                sib.right.isRed = parent.isRed;
                                rotateto(sib,true);
                                rotateto(parent,false);
                            }
                            else{
                                sib.right.isRed = sib.isRed;
                                sib.isRed = parent.isRed;
                                rotateto(parent,true);
                            }
                        }
                        parent.isRed = false;
                    }
                    else{
                        /**Flip color and handle inbalance in the top layer
                         * */
                        sib.isRed = true;
                        if(!parent.isRed){
                            balanceDoubleBlackAt(parent);
                        }
                    }
                }
            }
        }
        else{
            return;
        }
    }
}
