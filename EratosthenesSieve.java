package main;

import java.util.ArrayList;
import java.util.Collections;

public class EratosthenesSieve {
	
	public static enum QUERY_TYPE { BOOL , LIST };
	
	private ArrayList<Boolean> isPrime;
	private ArrayList<Integer> primeList;
	int ub;
	
	private void generate() {
		
		// generate the sieve
		for(int i = 2; i*i < ub; i++)
		{
			if(isPrime.get(i)) {
				for(int j = i*i; j <= ub; j+=i) {
					isPrime.set(j, false);
				}
			}
		}
		
		
		// make a list
		for(int p = 2; p<=ub ;p++) {
			if(isPrime.get(p)) {
				primeList.add(p);
			}
		}
	}
	
	
	public ArrayList<Integer> getPrimes(){
		return primeList;
	}
	
	public boolean checkPrime(int p) {
		if(p>=2) {
			return isPrime.get(p);
		}
		return false;
	}

	
	public EratosthenesSieve(int upperBound) {
		ub = upperBound;
		isPrime = new ArrayList<>(Collections.nCopies(ub+1, true));
		primeList = new ArrayList<>();
		this.generate();		
	}
	
	
	
}
