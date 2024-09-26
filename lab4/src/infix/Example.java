package infix;
import java.io.IOException;
class Example {
    public static void main(String[] args) throws IOException {
//Пример работы стека
        MStack<String> data = new MStack<>();
        data.push("Hello");
        data.push("World");
        data.push("Ah-ah");
        data.print();
        System.out.println("\n" + data.peek() + "- ТОП");
        data.pop();
        System.out.println("\n" + data.pop() + "- Новый ТОП");
        data.print();
        System.out.println();
//Работа с логическим выражением
        // String str = Convert.input();
        String str = "ABC&!|";
        System.out.println("Example: " + str);
        System.out.println("Infix+Scobe: " + Convert.toConvert(str));
        System.out.println("Infix+Scobe: " + Convert.toConvertTwo(str));
    }
}