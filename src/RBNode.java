public class RBNode {
        public BuildingInfo keystruct;
        boolean isRed;
        RBNode left;
        RBNode right;
        RBNode parent;
        public RBNode(BuildingInfo newone){
            keystruct = newone;
            isRed = true;
            left = null;
            right = null;
            parent = null;
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
