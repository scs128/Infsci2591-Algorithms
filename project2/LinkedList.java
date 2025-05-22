public class LinkedList<T> {
    Node head;
    Node last;
    int size;
    
    public LinkedList(){
        size = 0;
        head = null;
        last = null;
    }

    public void add(T n){
        if(head == null){
            head = new Node(n, null);
            last = head;
        }else{
            last.setNext(new Node(n, null));
            last = last.getNext();
        }
        size = size + 1;
    }

    public void set(int index, T n){
        if(index >= size){
            throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size);
        }
        Node pointer = head;
        for(int i = 0; i < index; i++){
            pointer = pointer.getNext();
        }
        pointer.setValue(n);
    }

    public T get(int index){
        if(index >= size){
            throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size);
        }
        Node pointer = head;
        for(int i = 0; i < index; i++){
            pointer = pointer.getNext();
        }
        return pointer.getValue();
    }

    public int size(){
        return size;
    }

    private class Node{
        private T value;
        private Node next;

        private Node(T value, Node next){
            this.value = value;
            this.next = next;
        }

        private T getValue(){
            return value;
        }

        private Node getNext(){
            return next;
        }

        private void setNext(Node next){
            this.next = next;
        }

        private void setValue(T value){
            this.value = value;
        }
    }
}
