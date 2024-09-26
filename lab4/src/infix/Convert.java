package infix;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Convert {
    private static boolean isOperand(char c){ // проверка на букву
        return ((c >= 'a' && c <= 'z')||(c >= 'A' && c <= 'Z'));
    }
    private static boolean isOperator(char c){ // проверка на символ оператора
        return ((c == '&') || (c == '|'));
    }
    private static boolean isNot(char c){ //проверка на "инверсию"
        return (c == '!');
    }
    static boolean symbol(char c){ //объединение проверок
        return (isOperand(c) || isOperator(c) || isNot(c));
    }
    static String input() throws IOException{ //Ввод строки с клавы
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while(true){
            System.out.print("Введите логическое выражение: ");
            str = read.readLine();
            if(checkLine(str))
                break;
        }
        return str;
    }
    static boolean checkLine(String str){
        for(int i = 0; i < str.length(); ++i){
            char c = str.charAt(i);
            if(!symbol(c)){
                System.out.println("Некорректная строка!");
                return false;
            }
        }
        return true;
    }
    static Object toConvert(String str){ // конвертация используя только скобки - для создание приоритета
        checkLine(str);
        MStack<Character> s = new MStack<Character>(); // создание стека
        for(int i = 0; i < str.length(); ++i){ // пройдемся по элементно по строке
            char c = str.charAt(i); //сохраним символ строки в отдельную переменную
            if(isOperand(c)) //букву добавим в стек
                s.push(c);
            else if(isOperator(c)){ //если оператор, кроме "!", то запишим возьмем две буквы из стека и доабвим к ним знак закрыв скобками
                String op1 = "", op2 = "";
                if(!s.isEmpty()){
                    op1 = s.peek() + "";
                    s.pop();
                }
                if(!s.isEmpty()){
                    op2 = s.peek() + "";
                    s.pop();
                }
                s.push('(' + op2 + op1 + c + ')');
            }
            else{
                String t = ""; //случай если это "!" - должен быть только с одной буквой
                if(!s.isEmpty()){
                    t = s.peek() + "";
                    s.pop();
                }
                s.push('(' + t + c + ')');
            }
        }
        return s.peek();
    }
    static Object toConvertTwo(String str){ //полная конвертация из постфикса в инфикс, с перестановкой операндов
        MStack<Character> s = new MStack<Character>(); // создание стека
        for(int i = 0; i < str.length(); ++i){ // пройдемся по элементно по строке
            char c = str.charAt(i); //сохраним символ строки в отдельную переменную
            if(isOperand(c))
                s.push(c);
            else if(isOperator(c)){
                String op1 = "", op2 = "";
                if(!s.isEmpty()){
                    op1 = s.peek() + "";
                    s.pop();
                }
                if(!s.isEmpty()){
                    op2 = s.peek() + "";
                    s.pop();
                }
                s.push('(' + op2 + c + op1 + ')');
            }
            else {
                String t = "";
                if(!s.isEmpty()){
                    t = s.peek() + "";
                    s.pop();
                }
                s.push(t + c);
            }
        }
        return s.peek();
    }
}