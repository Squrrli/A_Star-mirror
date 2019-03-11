import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class A_StarTest {

    @Test
    @DisplayName("Validator.parse(): Correct input format")
    void correctInputFormat(){
        assertTrue(A_Star.Validator.parse("1 2 3 4 5 6 7 8 0"));
    }

    @Test
    @DisplayName("Validator.parse(): length of input string too sort")
    void tooFewInputArg(){
        assertFalse(A_Star.Validator.parse("1 2 3 4 5 6 7 8"));
    }

    @Test
    @DisplayName("Validator.parse(): Input string too long")
    void tooManyInputArgs(){
        assertFalse(A_Star.Validator.parse("1 1 2 3 4 5 6 7 8 0"));
    }

    @Test
    @DisplayName("Validator.parse(): Input value > 8(max value in 9 puzzle)")
    void inputValueOutOfBounds1(){
        assertFalse(A_Star.Validator.parse("1 2 3 4 5 6 7 8 9"));
        assertFalse(A_Star.Validator.parse("1 0 3 4 5 6 7 8 22"));
    }

    @Test
    @DisplayName("Validator.parse(): Input vlaue < 0(minimum value)")
    void inputValueOutOfBounds2(){
        assertFalse(A_Star.Validator.parse("1 -2 3 4 5 6 7 8 0"));
    }
}