import java.util.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        while (true)
        {
            Scanner in = new Scanner(System.in);
            System.out.print("Input a mathematical equation : \n");
            String input = in.nextLine();
            String ans =  calc( input ) ;
            System.out.print(ans);
            System.out.print("\n");
        }

        /*String[] ask = {"1","2","3","4","5","6","7","8","9","10","II","III","IV","V","VI","VII","VIII","IX","X"};
        String[] ops = {" + ", " / "," - "," * "};
        for (int i = 0 ; i < ask.length; i++)
        {
            for (int j = 0 ; j < ask.length; j++)
            {
                for (int k = 0 ; k < ops.length; k++)
                {
                    String input = ask[i] + ops[k] + ask[j];
                    System.out.println(input);
                    System.out.println(calc(input));
                }
            }
            }

         */
    }

    public static String calc(String input)
    {
        input = input.replaceAll(" ", "");
        if (!IsCorrectInput(input)) {return "throws Exception // Некорректный ввод";}
        if (!IsCorrectOperation(input)) {return ("throws Exception // Некорректная операция  ");}
        if (!IsCorrectCalculusSystem(input)) {return("throws Exception // Разные системы счисления "); }
        String operation = FindOperation(input);
        String[] nums = input.split(operation);
        if (IsCorrectArabicSystem(nums[0]) && IsCorrectArabicSystem(nums[1]))
        {
            int ans = DoOperation( Integer.parseInt(nums[0]), Integer.parseInt(nums[1]),operation);

             return Integer.toString(ans);
        }
        else if (IsCorrectRomeSystem(nums[0]) && IsCorrectRomeSystem(nums[1]))
        {
            int ans = DoOperation ( RomeToInt(nums[0]), RomeToInt(nums[1]), operation) ;
            if (ans < 1)
            {
                return("throws Exception //  Римские цифры могут быть только положительными");
            }
            return (IntegerConverter.intToRoman(ans));
        }
        else
        {
            return("throws Exception // Некоректные числа");
        }

    }
     static boolean IsInArray(char [] line, char  chr)
    {
        for (int i = 0 ; i < line.length ; i++)
        {
            if (line[i] == chr)
            {
                return true;
            }

        }
        return false;
    }
     static boolean IsInArray(String [] line, String  chr)
    {
        for (int i = 0 ; i < line.length ; i++)
        {
            if (Objects.equals(line[i] , chr))
            {
                return true;
            }

        }
        return false;
    }
     static boolean IsCorrectCalculusSystem(String args)
    {
        boolean arabic_flag = false;
        boolean roman_flag = false;
        char[] operations = {'+','-','/','*'};
        char[] arabic_numbers = {'1','2','3','4','5','6','7','8','9','0'};
        char[] roman_numbers = {'I','V','X'};

        for (int i = 0 ; i < args.length() ; i++)
        {
            char chr = args.charAt(i);
            boolean is_arabic = IsInArray(arabic_numbers,chr);
            boolean is_roman  = IsInArray(roman_numbers,chr);
            if (is_arabic)
            {
                arabic_flag = true;
            }
            if (is_roman)
            {
                roman_flag = true;
            }
            if ( arabic_flag && roman_flag)
            {
                return false;
            }


        }
        return true;
    }
     static int RomeToInt (String rome_num)
    {
        int arabic_num = 0;
        int count = 0;
        for (int i = 0 ; i < rome_num.length(); i++)
        {
            char chr = rome_num.charAt(i);
            if (chr == 'I')
            {
                count += 1 ;
            }
            if (chr == 'V')
            {
                arabic_num += 5 - count;
                count = 0;
            }
            if (chr == 'X')
            {
                arabic_num += 10 - count;
                count = 0 ;
            }
        }
        arabic_num += count;

        return arabic_num;
    }
     static boolean IsCorrectRomeSystem(String rome_num)
    {
        String[] nums = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};

        return IsInArray(nums,rome_num);
    }
     static boolean IsCorrectArabicSystem(String arabic_num)
    {
        try {
            return Integer.parseInt(arabic_num) < 11 && Integer.parseInt(arabic_num) > 0 ;
        }
        catch ( Exception e)
        {
            if (e instanceof RuntimeException)
            {

                return false;
            }
        }
        return false;

    }
     static boolean IsCorrectInput(String args)
    {
        if (args.length() < 3 ) return false;
        char[] allowed_chars = {'+','-','/','*','1','2','3','4','5','6','7','8','9','0','I','V','X'};
        for (int i = 0 ; i < args.length() ; i++)
        {
            if (!IsInArray(allowed_chars,args.charAt(i))) return false;
        }
        return  true;
    }
     static boolean IsCorrectOperation(String args)
    {
        char[] operations = {'+','-','/','*'};
        boolean is_find = false;
        for (int i = 0 ; i < args.length() ; i++)
        {
            if (IsInArray(operations,args.charAt(i)))
            {
                if (!is_find) is_find = true;
                else return false;
            }
        }
        return is_find;
    }
     static String FindOperation(String args)
    {
        char[] operations = {'+','-','/','*'};
        for (int i = 0 ; i < args.length() ; i++)
        {
            if (IsInArray(operations,args.charAt(i)))
            {
                if (args.charAt(i) == '+') return "\\+";
                else if (args.charAt(i) == '*') return "\\*";
                return String.valueOf(args.charAt(i));
            }
        }
        return "\\+";

    }

     static int DoOperation(int num_1, int num_2, String operation)
    {
        int result = 0;
        if (Objects.equals(operation , "/")) result = num_1 / num_2;
        if (Objects.equals(operation , "\\+")) result = num_1 + num_2;
        if (Objects.equals(operation , "-")) result = num_1 - num_2;
        if (Objects.equals(operation , "\\*")) result = num_1 * num_2;
        return  result;
    }

     class IntegerConverter {

         static String intToRoman(int number) {
            if (number >= 4000 || number <= 0)
                return "";
            StringBuilder result = new StringBuilder();
            for(Integer key : units.descendingKeySet()) {
                while (number >= key) {
                    number -= key;
                    result.append(units.get(key));
                }
            }
            return result.toString();
        }

         static final NavigableMap<Integer, String> units;
        static {
            NavigableMap<Integer, String> initMap = new TreeMap<>();
            initMap.put(1000, "M");
            initMap.put(900, "CM");
            initMap.put(500, "D");
            initMap.put(400, "CD");
            initMap.put(100, "C");
            initMap.put(90, "XC");
            initMap.put(50, "L");
            initMap.put(40, "XL");
            initMap.put(10, "X");
            initMap.put(9, "IX");
            initMap.put(5, "V");
            initMap.put(4, "IV");
            initMap.put(1, "I");
            units = Collections.unmodifiableNavigableMap(initMap);
        }
    }
}