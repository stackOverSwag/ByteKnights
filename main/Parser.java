import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math; // for abs in JUMP,...
import java.nio.file.Files;

public class Parser {
    /*
     * This object implements methods for each command of
     * "exa-assembly" (I will call it xcode). In the Game class,
     * when the user types out code, the object splits it firstly
     * line by line (by '\n') into an array A, such that each element
     * in A is a line written by the user, and secondly splits it
     * "word" by "word" (by ' ') such that A contains sub-arrays
     * [A1, A2, A3...] where An is a line of code split in a format
     * ["Command", "Argument1", "Argument2"...].
     * 
     * For example, the LC-3 code:
     * 
     * ADD R1 R1 5
     * AND R1 R1 R0
     * 
     * Will be first split by '\n' into:
     * 
     * A = [
     * "ADD R1 R1 5",
     * "AND R1 R1 R0"
     * ]
     * 
     * And then secondly split by ' ' into:
     * 
     * A = [
     * ["ADD", "R1", "R1", "5"],
     * ["AND", "R1", "R1", "R0"]
     * ]
     * 
     * Where EVERYTHING is a string (typechecking done individually
     * in each command). The important thing is that Game will
     * send A, the Exa, and the Level structure over to Parser,
     * who will iterate over elements of A and make changes
     * to the Level structure in accordance to what the commands
     * passed tell it to.
     * 
     * The problems that arise with this line-by-line implementation are
     * labels. How do we treat labels? In xcode, we are saved my the
     * MAKE label(L) command - we know exactly when labels are created.
     * Thus we can save them in a map (explained why later) with
     * the key being the name of the label itself, and the value being
     * the line that the label appears at, with -1 as the value if the
     * label wasn't used, for example:
     * 
     * (1) MAKE myL
     * (2) myL ADDI...
     * 
     * results in:
     * 
     * (0) labels = []
     * (1) labels = ["myL":-1]
     * (2) labels = ["myL":2]
     * 
     * In reality the label shouldn't be -1, since that would mean it's unused,
     * as well as the fact that label searching should be done in MARK itself.
     * 
     * So the reasons to use a map to store this data are:
     * 
     * 1) Encounter the label at assignment:
     * when checking if the first word in the
     * subarray of A is a known command, we can
     * also check if the first word appears in
     * the map of labels before throwing
     * an error to the player.
     * 
     * 2) JUMP label after label is assigned:
     * we can save the line at which the label is
     * first mentionned, so that if the player
     * wants to make a loop in his code, we can
     * know in the future if something like
     * "JUMP myL" will just makes us check our
     * map to see where to go back to,
     * or to throw an error if the label "myL"
     * doesn't exist, and if it does, we just
     * set our curline variable to the value
     * in the labels map stored at the
     * key "myL" (Turing's Halting problem
     * is watering at the mouth here).
     * 
     * 3) JUMP label before label is assigned:
     * we can look through A (the array sent
     * over by Game) and search for the label,
     * adding it to the map and then
     * jumping to the line (changing the variable
     * curline).
     * 
     * 4) Duplicate label assignment:
     * if the label has already been assigned (ie
     * in the map it does not have a value of
     * -1), we can throw an error to the player saying
     * that the label was already assigned at line
     * enter line stored in the map.
     * 
     * As mentionned in 2), we encounter the Halting problem with
     * labels; it has no solution. However, we don't care, since
     * Java will throw a Runtime Exception which we can just catch
     * whenever the JUMP or similar commands (TJMP, FJMP) are involved.
     * 
     * With the functionality out of the way, we can proceed with the
     * attributes:
     * 
     */

    private enum Commands {
        // Arithmetic
        ADDI, // done
        SUBI, // done
        MULI, // done
        DIVI, // done
        SWIZ, // unneeded?

        // Control Flow
        MARK, // done
        JUMP, // done
        TJMP, // done
        FJMP, // done

        // Conditions
        TEST, // done

        // Multiprocessing
        REPL,
        HALT,
        KILL,

        // Movement
        LINK,
        HOST,

        // Communication
        MODE,
        VOID,
        // another TEST // done

        // File Manipulation
        MAKE, // done
        GRAB,
        FILE,
        SEEK,
        // another VOID
        DROP,
        WIPE,
        // another TEST // done

        // Miscellaneous
        NOTE,
        NOOP,
        RAND
    }

    private Map<String, Integer> labels; // label map
    private ArrayList<ArrayList<String>> code; // player's code
    private int curline; // current line index (starts at 1)
    private Niveau level;
    private Exa exa;
    /*
     * Note: all the errors to think about:
     * Invalid Instruction (not in Commands)
     * Invalid Register (not X, T, F, M)
     * Number Too Large (>9999)
     * Number Too Small (<-9999)
     * RAND Not Allowed Here (probably unused)
     * Unknown Label (not in labels) // done
     */

    /*
     * Receives "String code", splits it by line, then by spaces,
     * stores it in this.code,
     * Stores 1 into this.curline, e into exa
     * Creates empty this.labels Map.
     */
    public Parser(String code, Niveau level, Exa e) {
        if (null == code)
            throw new NullPointerException("code cannot be null");
        if (null == level)
            throw new NullPointerException("level cannot be null");
        if (null == e)
            throw new NullPointerException("exa cannot be null");

        this.level = level;
        this.labels = new HashMap<>();
        this.code = new ArrayList<>();
        this.curline = 1;
        this.exa = e;

        String[] temp = code.split("\n");
        for (String line : temp) {
            String[] words = line.split(" ");
            ArrayList<String> lineWords = new ArrayList<>();
            for (String word : words) {
                lineWords.add(word);
            }
            this.code.add(lineWords);
        }
    }

    public int getCurLine() {
        return this.curline;
    }

    /*
     * START!!!!
     * takes the code and tries to execute it!
     * 
     * Two steps:
     * 
     * 1. check ALL appearences of MARK (even "myL1 MARK myL2") and execute MARKs
     * (to not have problems with labels later on)
     * 
     * 2. skipping all MARK instructions, execute the rest of the code, while
     * verifying any unknown command's existence in labels (the ArrayList
     * which contains all the labels).
     */

    public void start() {
        String firstElem;
        // Step 1: start by going through searching all the "MARK myL" and "myL1 MARK
        // myL2" and add labels to the list of labels
        for (ArrayList<String> mline : this.code) { // markline

            firstElem = mline.get(0);

            if (firstElem == "MARK") {
                if (!(mline.size() == 2)) {
                    throw new IllegalArgumentException("Mark got more than 2 arguments");
                }

                addLabel(mline.get(1));

            } else if (!Commands.contains(firstElem) && mline.get(1) == "MARK") {
                if (!(mline.size() == 3)) {
                    throw new IllegalArgumentException("Mark got more than 2 arguments");
                }
                addLabel(mline.get(2));
            }

            this.curline += 1;
        }

        this.curline = 1;

        for (ArrayList<String> lline : this.code) { // labelline
            firstElem = mline.get(0);

            if (Commands.contains(firstElem)) {
                this.curline++;
                break;
            }
            // everything that is left is a label

            if (!this.labels.containsKey(firstElem)) {
                throw new IllegalArgumentException("Label " + firstElem + " is not in the list");
            }
            // everything that is left is MARKed

            if (this.labels.get(label) != -1) {
                throw new IllegalArgumentException("Duplicate label " + firstElem);
            }
            // everything that is left is not initialised yet (line = -1)

            this.labels.remove(firstElem);
            this.labels.put(firstElem, this.curline);

            this.curline += 1;
        }

        // now ALL the MARK commands have been processed. the only way to
        // get to one would be with a label, which will lead to the line being skipped.

        this.curline = 1; // reset code pointer

        // all labels now exist, are MARKed, and are unique.

        ArrayList<String> arithmeticOperations = new ArrayList<String>("ADDI", "SUBI", "DIVI", "MULI");
        for (ArrayList<String> line : this.code) {
            firstElem = line.get(0);

            // if MARK is here, skip.
            if (checkCommand(line, "MARK")) {
                this.curline += 1;
                break;
            }

            // ADDI, SUBI, DIVI, MULI
            else if (arithmeticOperations.contains(firstElem)) {
                line.remove(0);
                /*
                 * This function will throw an IllegalArgumentException
                 * which needs to be caught by Game class, which should also
                 */
                resolveArithmetic(line, firstElem);
                this.curline += 1;
            }

            // TEST
            else if (checkCommand(line, "TEST")) {
                line = line.remove(0);
                // will throw errors by herself
                TEST(line);
            }

            else if (checkCommand(line, "JUMP")) {
                line = line.remove(0);
                if (line.size() != 1) {
                    throw new IllegalArgumentException("JUMP" + line + " needs to only have 1 argument");
                }
                JUMP(line);
            }

            else if (checkCommand(line, "TJMP")) {
                line = line.remove(0);
                if (line.size() != 1) {
                    throw new IllegalArgumentException("TJMP" + line + " has to only have 1 argument");
                }
                if (this.exa.getT() > 0) {
                    JUMP(line);
                }
            }

            else if (checkCommand(line, "FJMP")) {
                line = line.remove(0);
                if (line.size() != 1) {
                    throw new IllegalArgumentException("FJMP" + line + " has to only have 1 argument");
                }
                if (this.exa.getT() == 0) {
                    JUMP(line);
                }
            }

        }
    }

    public boolean checkCommand(ArrayList<String> line, String objective) {
        return objective == line.get(0) || (this.labels.containsKey(line.get(0)) && objective == line.get(1));
    }

    public void addLabel(String label) {

        if (this.Commands.contains(label)) {
            throw new IllegalArgumentException("label " + label + " cannot be a command");
        }

        if (this.labels.containsKey(mline.get(1))) {
            // do we even need to throw an error here?
            throw new IllegalArgumentException("label " + label + " already created");
        }

        // we are sure that the label isn't already in labels.

        this.labels.put(mline.get(1), -1);
    }

    /*
     * Takes 1 int, 1 String and tries to put the int
     * into the register String.
     * eliminates code duplication in Arithmetic methods.
     */

    public boolean setRegister(String S, int n) {
        if (S.equals("X")) {
            this.exa.setX(n);
        } else if (S.equals("T")) {
            this.exa.setT(n);
        } else if (S.equals("M")) {
            this.exa.setM(n);
        } else {
            return false;
        }
        return true;
    }

    /*
     * Takes 3 int and checks if they are in [-9999, 9999].
     * eliminates code duplication in Arithmetic methods.
     */

    public boolean checkArithmeticArguments(int val1, int val2, int res) {
        if (val1 > 9999 ||
                val2 > 9999 ||
                res > 9999 ||
                val1 < -9999 ||
                val2 < -9999 ||
                res < -9999) {
            return false;
        }
        return true;

        /*
         * I KNOW I CAN DO IT IN ONE LINE
         * return !(val1>9999||val2>9999||res>9999||val1<-9999||val2<-9999||res<-9999);
         * it's simply more readable this way!
         */
    }

    /*
     * Takes an object and tries to convert it to an int.
     * can throw errors, possible to debug using the "useful"
     * messages. eliminates code duplication in Arithmetic methods.
     */

    public int convToInt(Object o) {
        int n;

        if (o instanceof int) {
            n = (int) o;
        }

        else if (o instanceof Files) {
            Files f = (Files) o;
            if (f.iter.hasNext()) {
                n = Integer.parseInt(f.iter.next());
            } else {
                throw new IllegalArgumentException("File " + f.getName() + " does not have next value");
            }
        }

        else if (o instanceof String) {
            String S = (String) o;
            if (S.equals("X")) {
                n = this.exa.getX();
            } else if (S.equals("T")) {
                n = this.exa.getT();
            } else if (S.equals("M")) {
                n = this.exa.getM();
            } else {
                n = String.valueOf(S);
            }
        }

        else {
            throw new IllegalArgumentException("Expected type Files/int/String");
        }
        return n;
    }

    /*
     * Takes arguments, a command, and tries executing them.
     * Throws IllegalArgumentException if something the user
     * did breaks.
     */

    public void resolveArithmetic(ArrayList<String> args, String firstElement) {
        if (args.size() != 3) {
            throw new IllegalArgumentException(
                    "Arithmetic command didn't get 3 arguments, instead got " + args.size() + " arguments");
        }
        /*
         * This will throw an IllegalArgumentException that needs to be caught by game.
         */
        int val1 = convToInt(args.get(0)), val2 = convToInt(args.get(1)), res;
        String resReg;

        ArrayList<String> numbers = new ArrayList<>("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

        if (numbers.contains(args.get(2).substring(0, 1))) {
            throw new IllegalArgumentException(
                    "Arithmetic command destination argument isn't a String (cannot be resolved to exa Register)");
        }
        switch (firstElem) {
            case "ADDI":
                res = val1 + val2;
                break;
            case "SUBI":
                res = val1 - val2;
                break;
            case "MULI":
                res = val1 * val2;
                break;
            case "DIVI":
                if (val2 == 0) {
                    throw new IllegalArgumentException("You cannot divide by 0 :(");
                }
                res = val1 / val2;
                break;
            default:
                throw new IllegalArgumentException("Command " + firstElem + " not found/label not MARKed");
        }
        if (!checkArithmeticArguments(val1, val2, res)) {
            throw new IllegalArgumentException(
                    "Arithmetic command argument or result is out of bounds, [-9999,9999]");
        }
        resReg = (String) args.get(2);
        this.curline += 1;
        if (!setRegister(resReg, res)) {
            if (!setFiles(resReg, res)) {
                throw new IllegalArgumentException("Register not found");
            }
        }
    }

    /*
     * COMMAND archetype
     * always returns a boolean, true if the command was executed successfully,
     * false if not.
     * don't forget to check the arguments! every command may take different types
     * as arguments!
     * because of the JUMP commands, every command will advance curline by itself
     */

    /*
     * Takes a label, casts it to either String or int depending on what it is,
     * and sends the curline there.
     * 
     */

    public void JUMP(Object label) {
        if (label instanceof int) {
            int num = (int) label;

            if (0 == num) {
                throw new IllegalArgumentException("you can't jump 0 lines");
            }
            if ((num < 0 && abs(num) > this.curline) || (num + this.curline > this.code.size())) { // curline = 5, JUMP
                                                                                                   // -10 not possible
                throw new IllegalArgumentException("you cannot jump " + num + " lines, out of bounds");
            }

            this.curline += num;

        }

        else if (label instanceof String) {
            String str = (String) label;

            if (!(this.labels.containsKey(str))) {
                throw new IllegalArgumentException("label " + str + " does not exist");
            }
            this.curline = labels.get(str);

        }

        throw new IllegalArgumentException("label type not supported (not String or int)");
    }

    /*
     * TEST has 5 implementations:
     * 
     * TEST a > b
     * TEST a = b
     * TEST a < b
     * TEST EOF
     * TEST MRD // not needed
     * 
     * Where a, b can be int or letters, if their types are different comparison is
     * false.
     * Thus TEST takes an ArrayList<String> and then check each case
     * 
     */

    public void TEST(ArrayList<String> args) {
        if (args.size() == 3) {

            /*
             * Possibilities:
             * TEST a < b
             * TEST a = b
             * TEST a > b
             * 
             */

            Object a = args.get(0);
            String symbol = args.get(1);
            Object b = args.get(2);

            if (a instanceof int && b instanceof int) {
                int A = (int) a;
                int B = (int) b;
                switch (symbol) {
                    case "<":
                        this.exa.setT((A < B) ? 1 : 0);
                        break;
                    case "=":
                        this.exa.setT((A == B) ? 1 : 0);
                        break;
                    case ">":
                        this.exa.setT((A > B) ? 1 : 0);
                        break;
                    default:
                        throw new IllegalArgumentException("Symbol " + symbol + " not recognised (not <, >, or =)");
                }
                return;

            } else if (a instanceof String && b instanceof String) {
                String A = (String) a;
                String B = (String) b;
                int comp = A.compareTo(B);
                switch (symbol) {
                    case "<":
                        this.exa.setT((comp == -1) ? 1 : 0);
                        break;
                    case "=":
                        this.exa.setT((comp == 0) ? 1 : 0);
                        break;
                    case ">":
                        this.exa.setT((comp == 1) ? 1 : 0);
                        break;
                    default:
                        throw new IllegalArgumentException("Symbol " + symbol + " not recognised");
                }
                return;
            }

        } else if (args.size() == 1) {

            /*
             * Possibilities:
             * TEST EOF
             * TEST MRD //not needed
             * 
             */

            Object s = args.get(0);
            if (!(s instanceof String)) {
                throw new IllegalArgumentException("Cannot have single non-String argument");
            }

            String S = (String) s;

            if (S.equals("EOF")) {
                if (null == this.exa.getF()) {
                    throw new IllegalArgumentException("No file");
                }
                this.exa.setT(!this.exa.getF().getIter().hasNext() ? 1 : 0);
                return;
            }

            else if (S.equals("MRD")) {
                // this was not supposed to happen...
                throw new IllegalArgumentException("MRD not supported");
            }
        }

        throw new IllegalArgumentException("Argument(s) not recognised");

    }

}
