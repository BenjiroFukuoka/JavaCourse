package vectors;
class ArrayVector implements Vector{
    //поле класса
    private double[] vec;
    private int size;
    //конструкторы класса
    ArrayVector(){//конструктор по умолчанию. случайные значения дабл с размерностью 3 массива
        size = 3;
        vec = new double[size];
        vec = Randomazer();
    }
    ArrayVector(int size){//конструктор с указанием размера и случайными значениями
        this.size = size;
        if(this.size >= 0){//усключаем отрицательные значения для массива
            vec = new double[this.size];
            vec = Randomazer();
        }
        else
            System.out.println("Было введено недопустимое значения размера массива");
        }
    ArrayVector(double...vec){//конструктор и инициализацией любым кол-во аргументов или массивом типа дабл
        this.size = vec.length;
        this.vec = new double[this.size];
        for (int i = 0; i < this.size; ++i)
            this.vec[i] = vec[i];
    }
    public double get(int index) throws VectorIndexOutOfBoundsException{//геттер - получение значения по индексу
        if(index > this.size || index < 0)
            throw new VectorIndexOutOfBoundsException(index);
        else
            return vec[index];
    }
    public void set(int index, double value) throws VectorIndexOutOfBoundsException{//сеттер - присвоение значения по индексу
        if(index > size() || index < 0)
            throw new VectorIndexOutOfBoundsException(index);
        else
            vec[index] = value;
    }
    public int size(){//размера массива
        return size;
    }
    public double norm(){//норма Евклида
        double z = 0;
        for (double x : vec)//пройтись по vec с шагом - double
            z += Math.pow(x, 2);
        return Math.sqrt(z);
    }
    private double[] Randomazer(){//закрытый метод для рандомизации значений
        int size = vec.length;
        for (int i = 0; i < size; ++i){
            double tmp = Math.random()*50+1;
            vec[i] = ((int)(tmp*100))/100.0;}
        return vec;
    }
    public void print(){//вывод массива в консоле
        int size = vec.length;
        if (size == 0)
            System.out.println("Array is empty");
        else{
            System.out.print(vec[0]);
            for (int i = 1; i < size; ++i)
                System.out.print(" " + vec[i]);
            System.out.println();}
    }
}