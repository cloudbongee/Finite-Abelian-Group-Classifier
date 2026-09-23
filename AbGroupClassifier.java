package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



/**
 * 
 * if your abelian group is finite.
 * 
 * Then you can classify it up to isomorphism by running
 * the main in this class.
 * 
 * 
 * 
 */
public class AbGroupClassifier {
	
		// Prime factors of the order
	private ArrayList<Integer> factorization;
	
		// Valid array sequences of cyclic group modulos
	private ArrayList<ArrayList<Integer>> classification;
	
		// Prime siever
	private EratosthenesSieve sieve;
		
		// Order of finitely generated finite group
	int ord;
		
		// Maximum allowed order
	private final static int MAX_N = 2000;
	
		// Constructor
	public AbGroupClassifier(int ord) {
		this.ord = ord;
		this.sieve = new EratosthenesSieve(ord);
		this.factorization = new ArrayList<>();
		this.classification = new ArrayList<>();
	}
	
		// Access the prime siever to get the factorization list
	public void factorize() {
		ArrayList<Integer> primes = this.sieve.getPrimes();
		
		
		for(int p : primes) {
			int copyOrd = ord;
			while(copyOrd%p==0 && copyOrd!=0) {
				factorization.add(p);
				copyOrd/=p;
			}
		}
		
		
	}
	
		// Get the factorization list
	public ArrayList<Integer> getFactorization(){
		if(factorization.isEmpty()) {
			this.factorize();
		}
		
		return this.factorization;
	}
	
	
	


	
	private void classify() {
		
			// make an exponent count per prime
		HashMap<Integer,Integer> primeToExponent = new HashMap<>();
		
		for(int p : factorization) {
			if(primeToExponent.containsKey(p)) {
				primeToExponent.put(p, primeToExponent.get(p)+1);
			}else {
				primeToExponent.put(p, 1);
			}
		}
		
			// make all of the possible increasing sum partitions for the exponents
		HashMap<Integer,ArrayList<ArrayList<Integer>>> primeToPartition = new HashMap<>();
		for(int p : primeToExponent.keySet()) {
			ArrayList<ArrayList<Integer>> partition = exponentPart(primeToExponent.get(p));
			primeToPartition.put(p, partition);
		}
		// making this null helps compiler know this is unused memory.
		primeToExponent = null;
		
		ArrayList<Integer> primes = new ArrayList<>(primeToPartition.keySet());
		
		// combine all of the sum partition of exponents generated.
		combinePartitions(
				primes,
				primeToPartition,
				0,
				new ArrayList<ArrayList<Integer>>()
		);

	}
		
		
	private void combinePartitions(ArrayList<Integer> primes,
			
			/*
			 * All of this backtracking stub is 
			 * programmatically just doing the work
			 * of combinations.
			 * 
			 * Everybody say thank you combinatorics. 
			 *  
			 */
			
		HashMap<Integer, ArrayList<ArrayList<Integer>>> primeToPartition, int i, ArrayList<ArrayList<Integer>> curr) {
		if(i==primes.size()) {
			makeFactors(primes, curr);
			return;
		}
		int prime = primes.get(i);
		for(ArrayList<Integer> partition : primeToPartition.get(prime)) {
			curr.add(partition);
			combinePartitions(primes, primeToPartition, i+1, curr);
			curr.remove(curr.size()-1);
		}
		
	}


	private void makeFactors(ArrayList<Integer> primes, ArrayList<ArrayList<Integer>> curr) {
		int M = 0;
		for(ArrayList<Integer> sumPartition: curr) {
			if(M < sumPartition.size()) M = sumPartition.size();
		}
		
		ArrayList<Integer> ords = new ArrayList<>();
		
		for(int column = 0 ; column < M ; column++) {
			int order = 1;
			for(int i = 0 ; i < primes.size(); i ++) {
				int prime = primes.get(i);
				ArrayList<Integer> partition = curr.get(i);
				
				// ensure correct padding for multiplication.
				int offset = M-partition.size();
				if(column>= offset) {
					int exponent = partition.get(column-offset);
					order *= intPow(prime, exponent);
				}
			}
			
			ords.add(order);
		}
		
		classification.add(ords);
	}
	
	

	private int intPow(int prime, int exponent) {
		int r = 1;
		for(int i = 0; i < exponent; ++i) {
			r*=prime;
		}
		return r;
	}

	private ArrayList<ArrayList<Integer>> exponentPart(Integer exponent) {
		ArrayList<ArrayList<Integer>> partitions = new ArrayList<>();
		exponentPartitionBacktrack(
				exponent,
				1,
				new ArrayList<>(),
				partitions
			);
		return partitions;
	}
	
	
	private void exponentPartitionBacktrack(
			int remaining, 
			int minimum, 
			ArrayList<Integer> current, 
			ArrayList<ArrayList<Integer>> partitions) {
		if(remaining==0) {
			partitions.add(new ArrayList<>(current));
			return;
		}
		for(int i = minimum; i <= remaining; ++i) {
			current.add(i);
			
			exponentPartitionBacktrack(
					remaining-i, i, current, partitions );
			
			current.remove(current.size() - 1);
		}
	}

		// Get the classification of group orders
	public ArrayList<ArrayList<Integer>> getClassification(){
		if(classification.isEmpty()) {
			this.classify();
		}
		return this.classification;
	}
	
	

	public static void main(String[] args) {
		
		Scanner scanIn = new Scanner(System.in);
		
		System.out.println("Warning: This is a very rudamentary prime factorization algorithm."
				+ " Factorization is an extremely expensive computation. Integers greater than "
				+   MAX_N 
				+ " will be ignored.");
		
		
		
		String response = "";
		while(!response.equals("-1")) {

			System.out.print("Enter order of finitely generated abelian G: ");
			response = scanIn.nextLine();
			
			int n = 1;
			
			try {
				
				n = Integer.valueOf(response);
				
			}catch(Exception e) {
				
				System.out.println("Couldn't parse response as int.");
				continue;
				
			}
			
			if(n>MAX_N || n==1) {
				continue;
			}
			
			AbGroupClassifier G = new AbGroupClassifier(n);
			
			
			System.out.println("~~~~~~ Prime factorization ~~~~~~~\n");
			System.out.println(G.getFactorization());
			System.out.println();
			System.out.println("~~~~~~ Direct sum classification ~~~~\n");
			System.out.println();
			
			ArrayList<ArrayList<Integer>> classes = G.getClassification();
			for(ArrayList<Integer> l : classes) {
				System.out.println(l);
			}
			
			
		}
		
		
		scanIn.close();
	}
}
