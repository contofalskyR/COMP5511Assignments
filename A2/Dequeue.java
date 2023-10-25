public class Dequeue{
    int[] dequeue;
    int front;
    int rear;
    int size;

    public Dequeue(int size){
        front = -1;
        rear = 0;
        dequeue = new int[size];
        this.size = size;
    }

    
    public boolean isEmpty(){
        return front == -1;
    }
    public boolean isFull(){
        return ((front==0 && rear==size-1) || (front == rear+1));
    }

    public int popFront(){
        if (isEmpty()){
            System.out.println("The Dequeue is empty");
            return -1;
        }
        else{
            int toReturn=dequeue[front];
            if(front==rear){//the dequeue contains one element
                front=-1;
                rear=0;
            }else{
                front++;
                front%=size;
            }
            return toReturn;
        }
    }

    public int popRear(){
        if (isEmpty()){
            System.out.println("The Dequeue is empty");
            return -1;
        }
        else{
            int toReturn = dequeue[rear];
            if(rear==front){ //popping the last item
                front=-1;
                rear=0;

            }else{
                rear=(rear-1+size)%size;
            }
            return toReturn;
        }
    }

    public void insertFront(int insert){
        if((isFull())){
            System.out.println("Dequeue is full");
        }
        else{
            if (front<0){
                front = 0;
            }else{
                front=(front-1+size)%size;
            }
            dequeue[front]=insert;
            } 
    }

    public void insertRear(int insert){
        if(isFull()){
            System.out.println("Dequeue is full");
        }else{
            if(front<0){
                front=0;
            }else{
                rear++;
                rear%=size;
            }

            
            dequeue[rear]=insert;
        }
    }




    public static void main(String[] args){
        Dequeue d = new Dequeue(3);

        //A million tests

        
        d.insertRear(0);
        d.insertRear(1);
        d.insertRear(2);
        d.insertRear(3);

        System.out.println("rear: "+d.popRear());
        System.out.println("rear: "+d.popRear());
        System.out.println("rear: "+d.popRear());
        System.out.println("rear: "+d.popRear());

        d.insertFront(0);
        d.insertFront(1);
        d.insertFront(2);
        d.insertFront(3);

        System.out.println("front: "+d.popFront());
        System.out.println("front: "+d.popFront());
        System.out.println("front: "+d.popFront());
        System.out.println("front: "+d.popFront());

        d.insertFront(0);
        d.insertRear(3);
        d.insertFront(1);
        d.insertRear(2);

        System.out.println("front: "+d.popFront());
        System.out.println("rear: "+d.popRear());
        System.out.println("front: "+d.popFront());
        System.out.println("rear: "+d.popRear());

        d.insertRear(3);
        d.insertFront(0);
        d.insertRear(2);
        d.insertFront(1);

        System.out.println("rear: "+d.popRear());
        System.out.println("front: "+d.popFront());
        System.out.println("rear: "+d.popRear());
        System.out.println("front: "+d.popFront());

        d.insertFront(0);
        d.insertFront(1);

        System.out.println("rear: "+d.popRear());
        System.out.println("rear: "+d.popRear());

        d.insertRear(2);
        d.insertFront(1);

        System.out.println("rear: "+d.popRear());
        System.out.println("rear: "+d.popRear());
        System.out.println("rear: "+d.popRear());







        

        
    }

}