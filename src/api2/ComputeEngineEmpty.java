package api2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;

public class ComputeEngineEmpty implements ComputeEngine {
    private boolean[] primeSieve;

    // Constructor to initialize the sieve
    public ComputeEngineEmpty() {
        // Fast Version
        int maxNumber = 10_000; // Can adjust this limit
        this.primeSieve = generatePrimeSieve(maxNumber);

    }

    // Method to generate a sieve of primes up to a given max
    private boolean[] generatePrimeSieve(int max) {
        boolean[] isPrime = new boolean[max + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false; 

        for (int i = 2; i * i <= max; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= max; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }

    // Check if a number is prime using the precomputed sieve
    private boolean isPrime(int number) {
        if (number < 0 || number >= primeSieve.length) return false;
        return primeSieve[number];
    }

    // Compute method to filter primes from the input list and return them as string
    @Override
    public String compute(List<Integer> input) {
        if (input == null || input.isEmpty()) {
            System.err.println("Input list is null or empty.");
            return "";
        }

        // Use streams to filter primes and format the output
        List<Integer> primes = input.stream()
                                    .filter(this::isPrime)
                                    .collect(Collectors.toList());

        return primes.stream()
                     .map(String::valueOf)
                     .collect(Collectors.joining(", "));
    }

    //Slow code v
    @Override
    public String computeNaive(List<Integer> input) {
        if(input == null || input.isEmpty()){
            System.err.println("Input list is null or empty.");
            return "";
        }

        List<Integer> primes = new ArrayList<>();

        // Replacing the for-each loop with a regular for loop
        for (int number : input) {
            if (number > 1) {
                boolean isPrime = true;
                for (int j = 2; j <= Math.sqrt(number); j++) {
                    if (number % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) {
                    primes.add(number);
                }
            }
        }

        return primes.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }
}

        //As it stands, this will return ANY number non divisble by 2. 5 and 7 are prime, but 9 and 15 get through and are not.
	    //I've tried to implement something that may work better if a little late, but what works works.
	    //I have been racking my brain as to how to get this to work, and using nested loops MAY be the solution, but how is lost on me.
	    //I've been looking over this the whole night and frankly staying up any longer is a detriment to my health.
	    //I'm leaving the old code as a comment for reference and readability. -Ivan
	
		//find the prime under the given
		/*for(int i = 0; i < input.size(); i++) {
			if ((input.get(i) % 2) != 0) {
				b.add(input.get(i));*/
