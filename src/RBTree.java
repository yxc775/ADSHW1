import java.util.LinkedList;
import java.util.Queue;

public class RBTree {
    RBNode root;

    public RBTree(){
        root = null;
    }

    public void insert(RBNode x){
        if(root == null){
            x.isRed = false;
            root = x;
        }
        else{
            RBNode parent = root;
            while(parent != null){
                if(x.keystruct.getB() < parent.keystruct.getB()){
                    if(parent.left == null){
                        break;
                    }
                    else{
                        parent = parent.left;
                    }
                }
                else if(x.keystruct.getB() == parent.keystruct.getB()){
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
            if(x.keystruct.getB() < parent.keystruct.getB()){
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

    public void printFromTopDown(){
        System.out.println("top to down: ");
        if(root == null){
            System.out.println("Empty！");
        }
        else{
            Queue<RBNode> q = new LinkedList<>();
            RBNode cur;
            q.offer(root);

            while(!q.isEmpty()){
                cur = q.poll();
                System.out.print(cur.keystruct.getB() + " ");
                if(cur.left != null){
                    q.offer(cur.left);
                }
                if(cur.right != null){
                    q.offer(cur.right);
                }
            }
        }
    }

    public void printInorder(){
        System.out.println("Inorder: ");
        if(root !=  null){
            recursivePrintInorder(root);
        }
        else{
            System.out.println("Empty!");
        }
    }

    public void recursivePrintInorder(RBNode x){
        if(x != null){
            recursivePrintInorder(x.left);
            System.out.print(x.keystruct.getB() + " ");
            recursivePrintInorder(x.right);
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
    private void swapRBNode(RBNode a, RBNode b){
        BuildingInfo tempa = new BuildingInfo(a.keystruct.getB(),a.keystruct.getE(),a.keystruct.getT());
        RBNode temppoint = new RBNode(new BuildingInfo(a.keystruct.getB(),a.keystruct.getE(),a.keystruct.getT()));
        temppoint.parent = a.parent;
        temppoint.left = a.left;
        temppoint.right = a.right;
        temppoint.isRed = a.isRed;
        a.keystruct.point = b.keystruct.point;
        b.keystruct.point = temppoint;
        a.keystruct = b.keystruct;
        b.keystruct = tempa;
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
                toDelete.keystruct = replaceWith.keystruct;
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
            swapRBNode(replaceWith,toDelete);
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
    }

}
