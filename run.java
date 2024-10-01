import java.util.*;

public class run {
    public static void main(String[] args) {
        runGame(); //this must be its own method or else the replay section at the end of the game does not work as intended
    }
    public static void runGame(){
        //init var
        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> failedGuesses = new ArrayList<Integer>();
        boolean forceExit = false; // if true, game will not roll nums or ask for new ones
        int guessCount = 1; //CPU guess count
        final int MIN_VALUE = 1;
        final int MAX_VALUE = 100;


        //input/output initial setup
        String message = "Hello there. How high would you like me to guess up to?.";
        int bound = validityCheck(MIN_VALUE, MAX_VALUE,message);

        message = "How many guesses would you like for me to have until I give up? [Type 0 for infinite]";
        int guessLimit = validityCheck(0,MAX_VALUE, message);
        if(guessLimit<=0){
            guessLimit = 5000; //idc enough to do this the "proper" way rn | 10/1 EDIT: wtf is the "proper way???"
        }

        message = "Type a number between 1-"+bound+" for me to guess.";
        int target = validityCheck(MIN_VALUE, bound, message);


        bound++; //bound must be increased by 1 or else CPU will never guess the bound, so you could set bound to 10 and set ur target num to 10 and never lose

        int guess = rand.nextInt(bound);
        int previousGuess; //this var exists bc CPU needs to have a previous guess to compare itself to; there's def a better way to do this but IDk

        //begin CPU's output
        System.out.println("Hmmm... I guess "+guess+". Was I correct?\nType + for a higher guess, - for lower, ! for a correct guess.");
        String input = scan.next(); //i could proly turn this into a do/while and get rid of these 2 lines bnut uhhh im tired

        //gameplay loop

        do{
            if(guess!=target){
                failedGuesses.add(guess);
            }
            int guessLocal = 0;
            previousGuess = guess;
            //cpu will not guess already guessed nums

            //cpu guesses higher
            if(input.equals("+")) {

                if(guess==target){ //if cpu guesses correct and player doesn't input '!'
                    System.out.println("Hey wait, wasn't "+target+"the number you wanted me to guess? Looks like I win!");
                    forceExit = true;
                    break;
                }

                while ((guess < previousGuess || failedGuesses.contains(guess)) && !forceExit) {
                    guess = rand.nextInt(bound);
                    guessLocal++;
                    //if all guesses are used: guesses lower instead (as to prevent an infinite loop)
                    if (guessLocal >= 1000) {
                        while (failedGuesses.contains(guess) && !forceExit) {
                            System.out.println("I don't think there's any higher numbers i can guess that I haven't used already... I'll have to guess something else");
                            guess = rand.nextInt(bound);
                            forceExit = true;
                        }
                    }
                }

            }else if(input.equals("-")) {//cpu guesses lower

                if(guess==target){ //if cpu guesses correct and player doesn't input '!'
                    System.out.println("Hey wait, wasn't "+target+"the number you wanted me to guess? Looks like I win!");
                    forceExit = true;
                    break;
                }

                while ((guess > previousGuess || failedGuesses.contains(guess)) && !forceExit) {
                    guess = rand.nextInt(bound);
                    guessLocal++;
                    //if all guesses are used: guesses higher instead (as to prevent an infinite loop)
                    if (guessLocal >= 1000) {
                        while (failedGuesses.contains(guess) && !forceExit) {
                            System.out.println("I don't think there's any lower numbers i can guess that I haven't used already... I'll have to guess something else");
                            guess = rand.nextInt(bound);
                            forceExit = true;
                        }
                    }
                }

            }else if(input.equals("!")) { //correct guess

                    if (target == guess) {
                        System.out.println("I got it right! Thanks for playing!");
                        forceExit = true;
                        break;
                    } else {
                        //dawg.. don't be lyin like that...................
                        System.out.println("Hey wait, did you lie?");
                        guess = rand.nextInt(bound);
                        failedGuesses.add(guess);
                        while (failedGuesses.contains(guess)) {
                            guess = rand.nextInt(bound);
                        }
                    }

            }else{
                System.out.println("Invalid input. Try again.");
            }

            guessCount++;
            //cpu output
            System.out.println("Hmmm... I guess "+guess+". Was I correct?");
            input = scan.next();

        }while(!forceExit&&guessCount<guessLimit);

        if(guessCount>=guessLimit){
            System.out.println("Looks like you won! I ran out of guesses.");
        }


        System.out.println("Would you like to play again?");
        char replayInput = scan.next().charAt(0);
        if(replayInput=='Y'){
            runGame();
        }
    }
    //validity check for all values
    public static int validityCheck(final int MIN_BOUNDS, final int MAX_BOUNDS, String MESSAGE){
        //init var
        Scanner scan = new Scanner(System.in);
        boolean intValidity = false; //is input valid? (if in range and an int, true)
        int input = 0;

        System.out.println(MESSAGE);

        do {
            if(scan.hasNextInt()){ //check if input has correct datatype
                input = scan.nextInt();
                
                if(input >= MIN_BOUNDS && input <= MAX_BOUNDS) { //check if input falls within range
                    intValidity = true; //exits doWhile
                    
                }else{ System.out.println("Your input doesn't fall within the range. Try Again. [Minimum: "+MIN_BOUNDS+ "Maximum: "+MAX_BOUNDS+"]"); } //if input is correct type but isn't in range
            
            }else{ System.out.println("You've entered an invalid datatype. Try again."); } //if datatype is wrong
            
            scan.nextLine(); //clears cache; if this wasnt here, inputs would be quirked up
        } while (!intValidity);
        
        return input;
    }
}
