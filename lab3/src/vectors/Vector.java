package vectors;
interface Vector {
    double get(int index) throws VectorIndexOutOfBoundsException;
    void set(int index, double value) throws VectorIndexOutOfBoundsException;
    int size();
    double norm();
    void print();
}