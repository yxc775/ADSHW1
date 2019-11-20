public class MinHeap {
    private BuildingInfo[] heap = new BuildingInfo[2000];
    private int size = 0;
    private int maxsize = 2000;

    public MinHeap(){
        BuildingInfo dummy = new BuildingInfo(-5,-5,-5);
        heap[0] = dummy;
    }

    /** insert the element into MinHeap tree
     * @param element
     */
    public void insert(BuildingInfo element) {
        if (size >= maxsize) {
            System.out.println("full!");
            return;
        }

        size += 1;
        heap[size] = element;
        int cur = size;
        /**recursively push newly inserted value into top if condition sufficed to keep MinHeap balance*/
        if(size > 1) {
            while (heap[cur].getE() <= heap[parent(cur)].getE()) {
                if (heap[cur].getE() < heap[parent(cur)].getE()) {
                    swap(cur, parent(cur));
                    cur = parent(cur);
                } else {
                    if (heap[cur].getB() < heap[parent(cur)].getB()) {
                        swap(cur, parent(cur));
                        cur = parent(cur);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    /**
     * return the corresponding Min execution time buildinginfo and remove it.
     */
    public BuildingInfo pop(){
        if(size > 1) {
            BuildingInfo poped = heap[1];
            heap[1] = heap[size];
            heap[size] = null;
            size--;
            minheapify(1);
            return poped;
        }
        else if(size == 1){
            BuildingInfo poped = heap[1];
            heap[1] = null;
            size--;
            return poped;
        }
        else{
            return null;
        }
    }

    public BuildingInfo peek(){
        if(size >= 1) {
            return heap[1];
        }
        else{
            return null;
        }
    }

    private int parent(int pos){
        return pos/2;
    }

    private int leftsub(int pos){
        return 2 * pos;
    }

    private int rightsub(int pos){
        return 2 * pos + 1;
    }


    private boolean isLeaf(int pos){
        return (heap[leftsub(pos)] == null && heap[rightsub(pos)] == null);
    }

    /** swap the position of selected two BuildingInfo unit.
     * @param first , the index of first BuildingInfo
     * @param second, the index of second BuildingInfo
     */
    private void swap(int first, int second){
        /**Save the pointers first*/
        RBNode pointtofirst = heap[first].point;
        RBNode pointtoSecond = heap[second].point;

        BuildingInfo tmp1 = new BuildingInfo(0,0,0);
        tmp1.setB(heap[first].getB());
        tmp1.setE(heap[first].getE());
        tmp1.setT(heap[first].getT());
        /**reestablish pointers*/
        tmp1.point = pointtofirst;
        pointtofirst.keystruct = tmp1;

        BuildingInfo tmp2 = new BuildingInfo(0,0,0);
        tmp2.setB(heap[second].getB());
        tmp2.setE(heap[second].getE());
        tmp2.setT(heap[second].getT());
        /**reestablish pointers*/
        tmp2.point = pointtoSecond;
        pointtoSecond.keystruct = tmp2;

        /**finally switch these two nodes.*/
        heap[first] = tmp2;
        heap[second] = tmp1;
    }


    /** rebalance the tree by recursively pushing current selected node down if conditions sufficed.
     * @param pos the index of the selected element which will be minheapified
     */
    private void minheapify(int pos){
        if(!isLeaf(pos)){
            /**
            *  if both leaves exists, swap with the minimum child
            * */
            if(heap[leftsub(pos)] != null && heap[rightsub(pos)] != null) {
                if ((heap[pos].getE() > heap[leftsub(pos)].getE()) || (heap[pos].getE() > heap[rightsub(pos)].getE())) {
                    if (heap[leftsub(pos)].getE() < heap[rightsub(pos)].getE()) {
                        swap(pos, leftsub(pos));
                        minheapify(leftsub(pos));
                    } else {
                        swap(pos, rightsub(pos));
                        minheapify(rightsub(pos));
                    }
                } else {
                    /**
                     * if parent is greater or equal to both, need to check and swap with the child with minimum BuildingNumber,
                    * */
                    if ((heap[pos].getE() == heap[leftsub(pos)].getE()) && (heap[pos].getE() == heap[rightsub(pos)].getE())) {
                            if (heap[leftsub(pos)].getB() < heap[rightsub(pos)].getB()) {
                                swap(pos, leftsub(pos));
                                minheapify(leftsub(pos));
                            } else {
                                swap(pos, rightsub(pos));
                                minheapify(rightsub(pos));
                            }
                    } else if (heap[pos].getE() == heap[leftsub(pos)].getE()) {
                        if (heap[pos].getB() > heap[leftsub(pos)].getB()) {
                            swap(pos, leftsub(pos));
                            minheapify(leftsub(pos));
                        }
                    } else if (heap[pos].getE() == heap[rightsub(pos)].getE()) {
                        if (heap[pos].getB() > heap[rightsub(pos)].getB()) {
                            swap(pos, rightsub(pos));
                            minheapify(rightsub(pos));
                        }
                    }
                    /**
                     * Else, we do nothing
                    * */
                }
            }
            /**if only a left child,
            * */
            else if(heap[leftsub(pos)] != null){
                if ((heap[pos].getE() > heap[leftsub(pos)].getE())){
                        swap(pos, leftsub(pos));
                        minheapify(leftsub(pos));
                } else {
                    if (heap[pos].getE() == heap[leftsub(pos)].getE()) {
                        if (heap[pos].getB() > heap[leftsub(pos)].getB()) {
                            swap(pos, leftsub(pos));
                            minheapify(leftsub(pos));
                        }
                    }
                }
            }
            /**if only a right child,
             * */
            else{
                if ((heap[pos].getE() > heap[rightsub(pos)].getE())){
                    swap(pos, rightsub(pos));
                    minheapify(rightsub(pos));
                } else {
                    if (heap[pos].getE() == heap[rightsub(pos)].getE()) {
                        if (heap[pos].getB() > heap[rightsub(pos)].getB()) {
                            swap(pos, rightsub(pos));
                            minheapify(rightsub(pos));
                        }
                    }
                }
            }
        }
    }
}
