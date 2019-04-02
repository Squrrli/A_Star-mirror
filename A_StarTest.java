import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class A_StarTest {

    /* Validator.parse()
     *  Check possible user input states
     */
    @Test
    @DisplayName("Validator.parse(): Correct input format")
    void correctInputFormat(){

        if(A_Star.State.BOARD_SIZE == 9)
            assertTrue(A_Star.Validator.parse("1 2 3 4 5 6 7 8 0"));
        else
            assertTrue(A_Star.Validator.parse("1 2 3 4 5 6 7 8 9 0 A B C D E F"));
    }

    @Test
    @DisplayName("Validator.parse(): length of input string too sort")
    void tooFewInputArg(){
        if(A_Star.State.BOARD_SIZE == 9)
            assertFalse(A_Star.Validator.parse("1 2 3 4 5 6 7 8"));
        else
            assertFalse(A_Star.Validator.parse("1 2 3 4 5 6 7 8 0 A B C"));
    }

    @Test
    @DisplayName("Validator.parse(): Input string too long")
    void tooManyInputArgs(){
      if(A_Star.State.BOARD_SIZE == 9)
          assertFalse(A_Star.Validator.parse("1 2 3 4 5 6 7 8 9 0"));
      else
          assertFalse(A_Star.Validator.parse("1 2 3 4 5 6 7 A 8 9 0 A B C D E F"));
    }

    @Test
    @DisplayName("Validator.parse(): Input value > max value")
    void inputValueOutOfBounds1(){
//        A_Star.State.BOARD_SIZE = 9;
        assertFalse(A_Star.Validator.parse("1 2 3 4 5 6 7 8 9"));
        assertFalse(A_Star.Validator.parse("1 0 3 4 5 6 7 8 22"));

//        A_Star.State.BOARD_SIZE = 16;
        assertFalse(A_Star.Validator.parse("1 2 3 4 5 6 7 8 9 0 A B C D E X"));
        assertFalse(A_Star.Validator.parse("1 0 3 4 5 6 7 8 9 0 A BB C D E F"));
    }

    @Test
    @DisplayName("Validator.parse(): Input value < 0(minimum value)")
    void inputValueOutOfBounds2(){
//        A_Star.State.BOARD_SIZE = 9;
        assertFalse(A_Star.Validator.parse("1 -2 3 4 5 6 7 8 0"));
        assertFalse(A_Star.Validator.parse("1 -2 3 4 5 6 7 8 9 0 A B C D E F"));
    }

    /* Validator.containsDuplicates()
     * Check the input short[] contains no duplicate values
     */
    @Test
    @DisplayName("Validator.containsDuplicates(): valid array state")
    void validInputArray(){
        if(A_Star.State.BOARD_SIZE == 9)
            assertFalse(A_Star.Validator.containsDuplicates(new short[]{1, 2, 3, 4, 5, 6, 7, 8, 0}));
        else
            assertFalse(A_Star.Validator.containsDuplicates(new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}));
    }

    @Test
    @DisplayName("Validator.containsDuplicates(): valid array state")
    void inputContains2orMoreDups(){
        if(A_Star.State.BOARD_SIZE == 9) {
            assertTrue(A_Star.Validator.containsDuplicates(new short[]{1, 1, 3, 4, 5, 6, 7, 8, 0}));
            assertTrue(A_Star.Validator.containsDuplicates(new short[]{1, 1, 1, 1, 1, 1, 1, 1, 1}));
        } else
            assertTrue(A_Star.Validator.containsDuplicates(new short[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 15}));
    }
}