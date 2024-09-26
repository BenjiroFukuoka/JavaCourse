import java.util.Scanner;
public class Lab2 {
    public static void main(String[] args) {
        vector v1 = new vector(2.0, 3.5, 2.2);
        vector v2 = new vector(3.4, 1.1);
        System.out.print("a:"); v1.print();
        System.out.print("b:"); v2.print();
        System.out.println("\nv1(по индексу 2): " + v1.get(2) + ",\nv2(по индексу 1): " + v2.get(1));
        System.out.println("\n(a.size): " + v1.size() + ", (b.size): " + v2.size());
        System.out.println("\nmin(a): " + v1.min());
        System.out.println("max(a): " + v1.max());
        System.out.print("\nСортировка массива v1: "); v1.sort(0, v1.size()); v1.print();
        System.out.print("Сортировка массива v2: "); v2.sort(0, v2.size()); v2.print();
        System.out.println("\nЕвклидова норма для v1: " + v1.norm());
        System.out.print("\nВектор (v1) на число (3): "); v1.mul(3).print();;
        System.out.print("\nСложение векторов(v1, v2): "); v1.sum(v2).print();
        System.out.print("\nСкалярное произведение векторов (v1, v2): " + v1.mul(v2));
    }
}
class vector{
    private double[] vec; // создание поля класса
    public vector() {
        int size = (int)(Math.random()*5)+1;
        vec = new double[size];
        System.out.println("Размера массива: " + size);
        vec = inputArr(vec);
    }
    private static double[] inputArr(double[] v){
        System.out.println("Введите массив данных: ");
        Scanner in = new Scanner (System.in);
        for (int i = 0; i < v.length; ++i){
            v[i] = in.nextDouble();
        }
        in.close();
        return v;
    }
    public vector(int size) { // создание экземпляра класса с заданным размером
        vec = new double[size]; //массив размером size и заполненый 0
    }
    public vector(double... array){
        vec = new double[array.length];
        for (int i = 0; i < array.length; ++i)
            vec[i] = array[i];
    }
    //реализация, методов класса
    public double get(int index){ // получение значения по индексу массива
        return vec[index];
    }
    public void set(int index, double value){ // присвоение значения по индексу массива
        vec[index] = value;
    }
    public int size(){ // получение размера массива
        return vec.length;
    }
    public double min(){ // получение минимального значения массива
        double min = vec[0]; // присвоим переменой min значение нулевого индекса и проверим с другими элементами
        for(int i = 1; i < size(); ++i)
            if(min > vec[i])
                min = vec[i]; // присваиваем переменой min значение, если по индексу оно меньше
        return min;
    }
    public double max(){ // также реализация что и в min, но по большему значению
        double max = vec[0];
        for(int i = 1; i < size(); ++i)
            if(max < vec[i])
                max = vec[i];
        return max;
    }
    public void sort(int low, int high) { //реализация быстрой сортировки (quciksort)
        vector r = new vector(vec.length);
        --high;
        if (size() == 0)
            return;
        if (low >= high)
            return;
        int i = low, j = high;
        int mid = low+(high-low) / 2;
        double temp = r.vec[mid];
        while (i <= j) {
            while (r.vec[i] < temp) {
                ++i;
            }
            while (r.vec[j] > temp) {
                --j;
            }
            if (i <= j) {
                double t = r.vec[i];
                r.vec[i] = r.vec[j];
                r.vec[j] = t;
                ++i;
                --j;
            }
        }
        if (low < j) { // рекурсия метода по условию
            sort(low, j);
        }
        if (high > i) // рекурсия метода по условию
            sort(i, high);
    }
    public double norm(){ // реализация нормы Евклида
        double z = 0;
        for (double x:vec) //for-each пройтись от 0-го индекса массива до последнего размером double
            z += x*x;
        return Math.sqrt(z);
    }
    public vector mul(double h){ // произведение вектора на число, берем элемент массива и умножаем на h
        vector v = new vector(size());
        for(int i = 0; i < size(); i++)
            v.vec[i] = vec[i]*h;
        return v;
    }
    public vector sum(vector v) { // сумма двух векторов
        double[] min = vec, max = v.vec;
        if (vec.length > v.vec.length){
            double[] t = min;
            min = max;
            max = t;
        }
        vector r = new vector(max.length);
        for (int i = 0; i < max.length; ++i)
            r.vec[i] = max[i];
        for (int i = 0; i < min.length; ++i)
            r.vec[i] += min[i]; 
        return r;
    }
    public double mul(vector v){
        int len;
        if (vec.length < v.vec.length)
            len = vec.length;
        else
            len = v.vec.length;
        double r = 0;
        for (int i = 0; i < len; ++i)
            r += vec[i] * v.vec[i];
        return r;
    }
    public void print(){ //вывод массива
        if(vec.length == 0) //скажем что массив пуст, если он null
            System.out.println("empty");
        else
            System.out.print("{" + vec[0]);
        for(int i = 1; i < size(); ++i)
            System.out.print(" " + vec[i]);
        System.out.println("} размер: " + size());
    }
}