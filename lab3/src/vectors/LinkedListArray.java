package vectors;
class LinkedListArray implements Vector{//реализация двунаправленного списка
    //поле класса
    private Node head; //начало списка
    private Node tail; //конец списка
    private int size; //размерность списка
    LinkedListArray(){//конструктор по умполчанию, изначально пустой
        head = null;
        tail = null;
        size = 0;
    }
    LinkedListArray(int size){//конструктор с размером и рандомными значениями
        for(int i = 0; i < size; ++i){
            addLast(Randomizer());
        }
    }
    private double Randomizer(){
        double value = Math.random()*50+1;
        value = ((int)(value*100))/100.0;
        return value;
    }
    private boolean isEmpty(){//проверка на заполненость
        return head == null;
    }
    void addFirst(double data){//Добавить значение в начало списка
        Node tmp = new Node(data);
        if(isEmpty()) //если список пуст - head = tail = data
            tail = tmp;
        else
            head.prev = tmp;
        tmp.next = head;
        head = tmp;
        size++;
    }
    void addLast(double data){
        Node tmp = new Node(data);
        if(isEmpty())
            head = tmp;
        else //аналогично addFirst только начинам с конца, а следовательно начинаем делать ссылку с предхвоста на новый хвост и тд.
            tail.next = tmp;
        tmp.prev = tail;
        tail = tmp;
        size++;
    }
    void addIndex(int index, double data) throws ArrayIndexOutOfBoundsException{
        Node place = head;
        int count = 0;
        if(checkIndex(index)){
            while(place != null && count != index){
                place = place.next;
                count++; }
            Node tmp = new Node(data);
            tmp.next = place;
            place.prev.next = tmp;
            tmp.prev = place.prev;
            place.prev = tmp;
            size++;
        }
        else
            throw new ArrayIndexOutOfBoundsException(index);
    }
    void removeFirst(){
        if(head.next == null)
            tail = null;
        else
            head.next.prev = null;
        head = head.next;
        size--;
    }
    void removeLast(){
        if(tail.prev == null)
            head = null;
        else
            tail.prev.next = null;
        tail = tail.prev;
        size--;
    }
    void removeIndex(int index) throws ArrayIndexOutOfBoundsException{
        Node place = head;
        int count = 0;
        if(checkIndex(index)){
            while(place != null && count != index){
                place = place.next;
                ++count;
            }
            if(count == 0)
                removeFirst();
            else if(count == size-1)
                removeLast();
            else{
                place.prev.next = place.next;
                place.next.prev = place.prev;
                size--;
            }
        }
        else
            throw new ArrayIndexOutOfBoundsException(index);
    }
    private boolean checkIndex(int index){
        return !(index > this.size || index < 0);
    }
    public double get(int index){
        if(checkIndex(index)){
            Node tmp = head;
            for(int i = 0; i < i; ++i)
                tmp = tmp.next;
            return tmp.data;
        }else
            return 0;
    }
    public void set(int index, double data){
        if(checkIndex(index)){
            Node tmp = head;
            for(int i = 0; i < i; ++i)
                tmp = tmp.next;
            tmp.data = data;
        }
    }
    public double norm(){//норма Евклида
        double z = 0;
        for(Node tmp = head; tmp != null; tmp = tmp.next)
            z += Math.pow(tmp.data, 2);
        return Math.sqrt(z);
    }
    public int size(){
        return size;
    }
    public void print(){
        Node tmp = head;
        System.out.print("{");
        while(tmp != null){
            System.out.print(" " + tmp.data);
            tmp = tmp.next;
        }
        System.out.println(" }, size: " + size);
    }
    private static class Node{ //Node(Узел) - так называется элемент списка, характеризуется значением и связан с педыдущим и последующим элементом списка
    //поле класса
    double data;
    Node next; //ссылка на следующий элемент
    Node prev; //ссылка на предыдущий элемент
    Node(double data){ //конструктор
        this.data = data;}
    }
}