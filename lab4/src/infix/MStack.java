package infix;
class MStack <T>{
    Object[] array;
    private int capacity; // размерность
    private int top; // указатель верхнего элемента
    MStack(){ // конструктор по умолчанию = 0
        this.capacity = 0;
        this.array = new Object[0];
        this.top = -1;
    }
    MStack(int capacity){ // конструктор с укзанием размера
        this.capacity = capacity;
        this.array = new Object[this.capacity];
        this.top = -1;
    }
    int size(){ // возращает размер стека
        return (top++);
    }
    boolean isEmpty(){ // проверка на пустоту
        return (top == -1);
    }
    private boolean isFull(){ // проверка на перезаполнение
        return (top == capacity - 1);
    }
    void push(Object value){ // добавление элемента
        if(isFull()){
            ++capacity;
            resize();
        }
        array[++top] = value;
    }
    Object pop(){ // удаление элемента
        if(isEmpty()){
            System.out.println("Пустота!");
        }
        else
            --capacity;
        return array[top--];
    }
    Object peek(){ // показывает Топ
        if(isEmpty()){
            System.out.println("Пустота!");
        }
        return array[top];
    }
    void print(){ // вывод элементов стека
        System.out.print("{ ");
        for(int i = 0; i < capacity; ++i){
            if (i != capacity -1)
                System.out.print(array[i] + ", ");
            else System.out.print(array[i] + " ");
            }
        System.out.print("}");
    }
    void resize(){ // увеличение размерности
        Object[] tmp = new Object[capacity];
        if(!isEmpty())
            for(int i = 0; i < capacity-1; ++i)
                tmp[i] = array[i];
        this.array = new Object[capacity];
        array = tmp;
    }
}