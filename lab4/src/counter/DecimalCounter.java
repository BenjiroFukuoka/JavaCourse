package counter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class DecimalCounter {
    private static int MAX, MIN; //диапозон значений
    private int current; //значение счетчика
    DecimalCounter() throws IOException{ //конструктор с указанием начального значения и диапозона
        inValue();
        print();
    }
    int process() throws IOException{
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите '+'- для сложения, '-' - для уменьшения, '=' - для вывода результата, 'f' - для выхода");
        while(true){
            char choice = (char)read.readLine().charAt(0);
            switch(choice){
                case '+':
                    increaseCount();
                    break;
                case '-':
                    decreaseCount();
                    break;
                case '=': 
                    print();
                    break;
                case 'f': 
                    return 0;
                default:
                    System.out.println("Введено неправильное значение!");
            }
        }
    }
    int value(){
        return current;
    }
    void increaseCount(){
        if(current != MAX)
            current++;
    }
    void decreaseCount(){
        if(current != MIN)
            current--;
    }
    void print(){
        System.out.flush();
        System.out.println("count : " + value() + " [" + MIN + ";" + MAX + "]");
    }
    void inValue() throws IOException{ //ввод current
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        do{
            try {
                System.out.print("Введите минимальное значения диапозона: ");
                str = read.readLine();
                MIN = Integer.parseInt(str);
                System.out.print("Введите максимальное значения диапозона: ");
                str = read.readLine();
                MAX = Integer.parseInt(str);
                do{
                    System.out.print("Введите начальное значение счетчика: ");
                    str = read.readLine();
                    int tmp = Integer.parseInt(str);
                    if(!setCountValue(tmp)){
                        this.current = tmp;
                        break;
                    }
                }while(true);
                break;
            } catch (Exception e) {
                System.out.println("Недопустимое значение!");
            }
        }while(true);
}
    public boolean setCountValue(int value){
        if(value > MAX || value < MIN){
            System.out.println("Недопустимое значение");
            return true;
        }
        else
            return false;
    }
}