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
        int guessCount = 1; //AI guess count


        //input/output initial setup
        System.out.println("Hello there. How high would you like me to guess up to?.");
        int bound = scan.nextInt();

        System.out.println("How many guesses would you like for me to have until I give up? [Type 0 for infinite]");
        int guessLimit = scan.nextInt();
        if(guessLimit<=0){
            guessLimit = 5000; //idc enough to do this the "proper" way rn
        }

        System.out.println("Type a number between 1-"+bound+" for me to guess.");
        int target = scan.nextInt();

        //error handler for if target is out of bounds
        while(target>bound||target<1&&!scan.hasNextInt()){
            System.out.println("Invalid output. Try again!");
            target = scan.nextInt();
        }

        bound++; //bound must be increased by 1 or else AI will never guess the bound, so you could set bound to 10 and set ur target num to 10 and never lose

        int guess = rand.nextInt(bound);
        int previousGuess; //this var exists bc AI needs to have a previous guess to compare itself to; there's def a better way to do this but IDk

        //begin AI's output
        System.out.println("Hmmm... I guess "+guess+". Was I correct?");
        System.out.println("Type + for a higher guess, - for lower, ! for a correct guess.");
        String input = scan.next();

        //gameplay loop

        while(!forceExit&&guessCount<guessLimit){
            guessCount++;
            int guessLocal = 0;
            previousGuess = guess;
            //cpu will not guess already guessed nums
            if(previousGuess!=target){
                failedGuesses.add(previousGuess);
            }

            //cpu guesses higher
            if(input.equals("+")) {
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
            }else if(input.equals("-")) { //cpu guesses lower
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
            }

            //cpu output
            System.out.println("Hmmm... I guess "+guess+". Was I correct?");
            input = scan.next();
        }
        if(guessCount>=guessLimit){
            System.out.println("Looks like you won! I ran out of guesses.");
        }


        System.out.println("Would you like to play again?");
        char replayInput = scan.next().charAt(0);
        if(replayInput=='Y'){
            runGame();
        }
    }
}
