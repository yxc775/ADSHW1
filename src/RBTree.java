public class RBTree {
    private RBNode root;


    public RBTree(){
        root = null;
    }
    public RBNode getRoot(){
        return  root;
    }
    public void insert(RBNode x){
        if(root == null){
            x.isRed = false;
            root = x;
        }
        else{
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
                    System.out.println("Duplicate Building Number Encountered!");
                    return;
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
    }

    public void delete(RBNode toDelete){
        deleteRBNode(toDelete);
    }

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

    public String searchBetween(RBNode cur,int left, int right){
        if(cur == null){
            return "";
        }
        else if(cur.getB()> right || cur.getT() < left){
            if(cur.getB() > right){
                return searchBetween(cur.left,left,right);
            }
            else {
                return searchBetween(cur.right,left,right);
            }
        }
        else{
            StringBuilder content = new StringBuilder();
            String leftbuild = searchBetween(cur.left,left,right);
            if(!leftbuild.isEmpty()) {
                content.append(leftbuild);
            }
            content.append("("+cur.getB() + "," + cur.getE() + "," + cur.getT()+")" +",");
            String rightbuild = searchBetween(cur.right,left,right);
            if(!rightbuild.isEmpty()){
                content.append(rightbuild);
            }

            return content.toString();
        }
    }
    private void rotateto(RBNode node, boolean toLeft){
        RBNode newparent = null;
        if(toLeft){
            newparent = node.right;
        }
        else{
            newparent = node.left;
        }
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
    private void swapBuildingInfo(RBNode a, RBNode b){
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
        //You have to re assign the pointers otherwise, the pointer will not follow with the updated value
        builda.point = b;
        buildb.point = a;
    }

    private void swapColor(RBNode a, RBNode b){
        boolean temp = a.isRed;
        a.isRed = b.isRed;
        b.isRed = temp;
    }

    private void balanceDoubleRedAt(RBNode dr){
        if(root == dr){
            dr.isRed = false;
            return;
        }
        else{
            RBNode parent = dr.parent;
            RBNode parentsparent = parent.parent;
            RBNode parentBroth = dr.getParentBroth();

            if(parent.isRed){
                if(parentBroth != null && parentBroth.isRed){
                    parent.isRed = false;
                    parentBroth.isRed = false;
                    parentsparent.isRed = true;
                    balanceDoubleRedAt(parentsparent);
                }
                else{
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
    }

    private RBNode leftbranchingUntilNoChild(RBNode item){
        RBNode temp = item;
        while(temp.left != null){
            temp = temp.left;
        }
        return temp;
    }

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

    private void deleteRBNode(RBNode toDelete){
        RBNode replaceWith = getFillInAfterDelete(toDelete);
        RBNode parent = toDelete.parent;
        if(replaceWith == null){
            if(root == toDelete){
                root = null;
            }
            else{
                if((replaceWith == null || !replaceWith.isRed) && !toDelete.isRed){
                    balanceDoubleBlackAt(toDelete);
                }

                if(toDelete.isLeftchild()){
                    parent.left = null;
                }
                else{
                    parent.right = null;
                }
            }
        }
        else if(toDelete.left == null || toDelete.right == null) {
            if (root == toDelete) {
                toDelete.setB(replaceWith.getB());
                toDelete.setE(replaceWith.getE());
                toDelete.setT(replaceWith.getT());
                toDelete.left = null;
                toDelete.right = null;
            } else {
                if (toDelete.isLeftchild()) {
                    parent.left = replaceWith;
                } else {
                    parent.right = replaceWith;
                }

                replaceWith.parent = parent;
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

    private void balanceDoubleBlackAt(RBNode db){
        if(root != db){
            RBNode sib = db.getSib();
            RBNode parent = db.parent;
            if(sib == null){
                balanceDoubleBlackAt(parent);
            }
            else{
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
                    if(sib.hasRedbelow()){
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
