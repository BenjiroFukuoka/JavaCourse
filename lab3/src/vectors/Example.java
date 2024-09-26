package vectors;
public class Example {
    public static void main(String[] args) throws VectorIndexOutOfBoundsException, IncompatibleVectorSizesException {
        //Для работы с ArrayVector и Vectors
        ArrayVector v1 = new ArrayVector(2.0, 3.5, 2.2);
        ArrayVector v2 = new ArrayVector(3.4, 1.1, 3.2);
        System.out.print("a:"); v1.print();
        System.out.print("b:"); v2.print();
        System.out.println("\nv1(по индексу 2): " + v1.get(2) + ",\nv2(по индексу 1): " + v2.get(1));
        System.out.println("\n(a.size): " + v1.size() + ", (b.size): " + v2.size());
        System.out.println("\nЕвклидова норма для v1: " + v1.norm());
        System.out.print("\nВектор (v1) на число (3): "); Vectors.mul(v1, 3).print();;
        System.out.print("\nСложение векторов(v1, v2): "); Vectors.sum(v1, v2).print();
        System.out.print("\nСкалярное произведение векторов (v1, v2): " + Vectors.mul(v1, v2));

        LinkedListArray l1 = new LinkedListArray(3);
        LinkedListArray l2 = new LinkedListArray(3);
        System.out.print("\n\nl1:"); l1.print();
        System.out.print("l2:"); l2.print();
        l1.addFirst(100);
        l1.addLast(1000);
        l1.addIndex(2, 50); 
        System.out.print("\nl1:"); l1.print();
        l1.removeFirst();
        l1.removeLast();
        l1.removeIndex(1);
        System.out.print("\nl1:"); l1.print();
        System.out.println("\nl1(по индексу 1): " + l1.get(1));
        System.out.print("\nl1(по индексу 1, значение 20): "); l1.set(1, 20); l1.print();
        System.out.println("\nЕвклидова норма для v1: " + l1.norm()); 
        System.out.print("\nСписок (l1) на число (3): "); Vectors.mul(l1, 3).print();
        System.out.print("\nСложение списков(l1, l2): "); Vectors.sum(l1, l2).print();
        System.out.print("\nСкалярное произведение списков (v1, v2): " + Vectors.mul(v1, v2));
    }
}