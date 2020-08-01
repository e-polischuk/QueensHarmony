import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DemoTask {

    public static void main(String[] args) {
        List<Integer> primeCheck = List.of(1, 2, 3, 5, 7, 11, 13, 17, 19);
        testOf("isPrime", getFilteredRangeAsList(20, DemoTask::isPrime), primeCheck);
        testOf("isPrimeRecursive", getFilteredRangeAsList(20, DemoTask::isPrimeRecursive), primeCheck);

        List<String> subCase = List.of("", "a", "ab", "aaabc", "abccccccszx", "abcabcbb", "abcfhabcde");
        List<String> subCheck = List.of("", "a", "ab", "abc", "cszx", "abc", "abcfh");
        testOf("longestUniqSubStrInitial", getStringsMap(subCase, DemoTask::longestUniqSubStrInitial), subCheck);
        testOf("longestUniqSubStrRefactored", getStringsMap(subCase, DemoTask::longestUniqSubStrRefactored), subCheck);
    }

    public static boolean isPrime(int num) {
        if (num > 3) {
            if (num % 2 == 0) return false;
            for (int i = 3; i < Math.sqrt(num) + 1; i = i + 2) {
                if (num % i == 0) return false;
            }
        }
        return true;
    }

    public static boolean isPrimeRecursive(int num) {
        return num < 4 || (num % 2 != 0 && isNextPrime(num, 3));
    }

    private static boolean isNextPrime(int n, int i) {
        return n % i != 0 && (i > Math.sqrt(n) || isNextPrime(n, i + 2));
    }

//    public static boolean isPrimeRec(int... n) {
//        int i = n.length > 1 ? n[1] : 3;
//        return n[0] < 4 || (n[0] % 2 != 0 && n[0] % i != 0 && (i > Math.sqrt(n[0]) || isPrimeRec(n[0], i + 2)));
//    }

    public static String longestUniqSubStrInitial(String str) {
        if (str == null || str.length() < 2) return str;
        String res = "";
        int start = 0;
        for (int j = 1; j < str.length(); j++) {
            String sub = str.substring(start, j);
            if (sub.contains(str.substring(j, j + 1))) { //String.valueOf(str.charAt(j))
                if (sub.length() > res.length()) res = sub;
                start = j;
            } else if (j == str.length() - 1 && str.length() - start > res.length()) {
                res = str.substring(start);
            }
        }
        return res;
    }

    public static String longestUniqSubStrRefactored(String str) {
        if (str == null || str.length() < 2) return str;
        int start = 0;
        int end = 1;
        char[] chars = str.toCharArray();
        for (int i = 0, j = 1; j <= chars.length; j++) {
            boolean isFound = j == chars.length;
            if (!isFound) {
                int k = i;
                while (!isFound && k < j) isFound = chars[j] == chars[k++];
            }
            if (isFound && end - start < j - i) {
                start = i;
                end = j;
            }
            i = isFound ? j : i;
        }
        return str.substring(start, end);
    }

    private static <T> void testOf(String meth, List<T> testCase, List<T> testCheck) {
        System.out.print(meth + " test: ");
        String check = "OK";
        for (int i = 0; i < testCase.size(); i++) {
            if(!testCase.get(i).equals(testCheck.get(i))) {
                check = "FAIL -> " + testCase.get(i) + " != " + testCheck.get(i);
                break;
            }
        }
        System.out.println(check);
    }

    private static List<Integer> getFilteredRangeAsList(int range, IntPredicate filter) {
        return IntStream.range(1, range).filter(filter).boxed().collect(Collectors.toList());
    }

    private static List<String> getStringsMap(List<String> list, Function<String, String> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }

}
