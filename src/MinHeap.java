
public class MinHeap {
    private BuildingInfo[] heap = new BuildingInfo[2000];
    private int size = 0;
    private int maxsize = 2000;

    public MinHeap(){
        BuildingInfo dummy = new BuildingInfo(-5,-5,-5);
        heap[0] = dummy;
    }

    public void insert(BuildingInfo element) {
        if (size >= maxsize) {
            System.out.println("full!");
            return;
        }

        size += 1;
        heap[size] = element;
        int cur = size;
        while (heap[cur].getE() <= heap[parent(cur)].getE()) {
            if(heap[cur].getE() < heap[parent(cur)].getE()) {
                swap(cur, parent(cur));
                cur = parent(cur);
            }
            else{
                if(heap[cur].getB() < heap[parent(cur)].getB()){
                    swap(cur, parent(cur));
                    cur = parent(cur);
                }
                else{
                    break;
                }
            }
        }
    }

    public void update(int execute){
        BuildingInfo top = heap[1];
        if(top.getE() >= 0) {
            top.setE(top.getE() + execute);
            top.point.setE(top.point.getE() + execute);
            minheapify(1);
        }
    }

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
        if(pos * 2 < heap.length){
            if(heap[pos * 2] != null){
                return false;
            }
        }

        if(pos * 2+ 1 < heap.length){
            if(heap[pos * 2+1] != null){
                return false;
            }
        }
        return true;
    }

    private void swap(int first, int second){
        RBNode pointtofirst = heap[first].point;
        RBNode pointtoSecond = heap[second].point;

        BuildingInfo tmp1 = new BuildingInfo(0,0,0);
        tmp1.setB(heap[first].getB());
        tmp1.setE(heap[first].getE());
        tmp1.setT(heap[first].getT());
        tmp1.point = pointtofirst;

        BuildingInfo tmp2 = new BuildingInfo(0,0,0);
        tmp2.setB(heap[second].getB());
        tmp2.setE(heap[second].getE());
        tmp2.setT(heap[second].getT());
        tmp2.point = pointtoSecond;

        heap[first] = tmp2;
        heap[second] = tmp1;
    }



    private void minheapify(int pos){
        if(!isLeaf(pos)){
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
                    if ((heap[pos].getE() == heap[leftsub(pos)].getE()) && (heap[pos].getE() == heap[rightsub(pos)].getE())) {
                        if ((heap[pos].getB() > heap[leftsub(pos)].getB()) || (heap[pos].getB() > heap[rightsub(pos)].getB())) {
                            if (heap[leftsub(pos)].getB() < heap[rightsub(pos)].getB()) {
                                swap(pos, leftsub(pos));
                                minheapify(leftsub(pos));
                            } else {
                                swap(pos, rightsub(pos));
                                minheapify(rightsub(pos));
                            }
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
                }
            }
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
