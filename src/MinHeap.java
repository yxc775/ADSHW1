
public class MinHeap {
    private BuildingInfo[] heap = new BuildingInfo[2000];
    private int size = 0;
    private int maxsize = 2000;

    public MinHeap(){
        BuildingInfo dummy = new BuildingInfo(0,Integer.MIN_VALUE,0);
        heap[0] = dummy;
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
        if((pos >= size/2) && (pos <= size)){
            return true;
        }
        else{
            return false;
        }
    }

    private void swap(int first, int second){
        BuildingInfo tmp1 = new BuildingInfo(0,0,0);
        tmp1.setB(heap[first].getB());
        tmp1.setE(heap[first].getE());
        tmp1.setT(heap[first].getT());

        BuildingInfo tmp2 = new BuildingInfo(0,0,0);
        tmp2.setB(heap[second].getB());
        tmp2.setE(heap[second].getE());
        tmp2.setT(heap[second].getT());

        heap[first] = tmp2;
        heap[second] = tmp1;
    }

    private void minheapify(int pos){
        if(!isLeaf(pos)){
            if((heap[pos].getE() > heap[leftsub(pos)].getE())||(heap[pos].getE() > heap[rightsub(pos)].getE())){
                if(heap[leftsub(pos)].getE() < heap[rightsub(pos)].getE()){
                    swap(pos,leftsub(pos));
                    minheapify(leftsub(pos));
                }
                else{
                    swap(pos,rightsub(pos));
                    minheapify(rightsub(pos));
                }
            }
            else{
                if((heap[pos].getE() == heap[leftsub(pos)].getE())&&(heap[pos].getE() == heap[rightsub(pos)].getE())) {
                    if ((heap[pos].getB() > heap[leftsub(pos)].getB()) || (heap[pos].getB() > heap[rightsub(pos)].getB())) {
                        if (heap[leftsub(pos)].getB() < heap[rightsub(pos)].getB()) {
                            swap(pos, leftsub(pos));
                            minheapify(leftsub(pos));
                        } else {
                            swap(pos, rightsub(pos));
                            minheapify(rightsub(pos));
                        }
                    }
                }
                else if(heap[pos].getE() == heap[leftsub(pos)].getE()){
                   if(heap[pos].getB() > heap[leftsub(pos)].getB()){
                       swap(pos, leftsub(pos));
                       minheapify(leftsub(pos));
                   }
                }
                else if(heap[pos].getE() == heap[rightsub(pos)].getE()){
                    if(heap[pos].getB() > heap[rightsub(pos)].getB()){
                        swap(pos, rightsub(pos));
                        minheapify(rightsub(pos));
                    }
                }
                else{

                }
            }
        }
    }

    private void insert(BuildingInfo element) {
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

    public void print()
    {
        for (int i = 1; i <= size / 2; i++) {
            System.out.println("p: " + heap[i]
                    + "l： " + heap[2 * i]
                    + "r: " + heap[2 * i + 1]);
        }
    }

    public void update(int execute){
        BuildingInfo top = heap[1];
        top.setE(top.getE() + execute);
    }

    public BuildingInfo pop(){
        BuildingInfo poped = heap[1];
        heap[1] = heap[size];
        size--;
        minheapify(1);
        return poped;
    }

}
