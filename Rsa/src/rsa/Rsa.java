package rsa;
import java.util.concurrent.ThreadLocalRandom;

public class Rsa {
    int p, q, n, phiOfN, e, d;

    public Rsa() {
        // Generate two random primes
        p = getRandomPrime(1, 100);

        q = getRandomPrime(1, 100);


        // n = pq
        n = p * q;


        // compute the totient of n
        phiOfN = getPhi(n);


        // Compute coprime e, where 1 < e < phiOfN
        e = getRandomPrime(1, phiOfN);


        // Compute the modular multiplicative inverse of e
        d = modInverse(e, phiOfN);

    }

    public int encrypt(int plaintext) {
        return modPow(plaintext, e, n);
    }

    public int decrypt(int ciphertext) {
        return modPow(ciphertext, d, n);
    }

    private int getRandomPrime(int min, int max) {
        int randomInt = ThreadLocalRandom.current().nextInt(min, max + 1);

        while (!isPrime(randomInt)) {
            randomInt = ThreadLocalRandom.current().nextInt(min, max + 1);
        }
        return randomInt;
    }

    private boolean isPrime(int n) {
        int i;
        for (i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int getPhi(int number) {
        int counter = 0;

        for (int i = 1; i < number; i++) {
            if (getGCD(number, i) == 1) {
                counter++;
            }
        }

        return counter;
    }

    private int getGCD(int a, int b) {
        if (b == 0) {
            return a;
        }

        return getGCD(b, a % b);
    }

    public int modInverse(int a, int m) {
        int m0 = m, t, z;
        int x0 = 0, x1 = 1;

        if (m == 1) {
            return 0;
        }

        while (a > 1) {
            // q is quotient
            z = a / m;

            t = m;

            // m is remainder now, process same as
            // Euclid's algo
            m = a % m;
            a = t;

            t = x0;

            x0 = x1 - z * x0;

            x1 = t;
        }

        // Make x1 positive
        if (x1 < 0) {
            x1 += m0;
        }

        return x1;
    }

    public int modPow(int base, int exponent, int modulus) {
        if (exponent < 0)
            throw new IllegalArgumentException("exponent < 0");
        int result = 1;
        while (exponent > 0) {
            if ((exponent & 1) != 0) {
                result = (result * base) % modulus;
            }
            exponent >>>= 1;
            base = (base * base) % modulus;
        }
        return result;

    }
}
