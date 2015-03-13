package pl.edu.agh.jtp.zad6;

import java.math.BigInteger;

public class Entry {
	private BigInteger value;
	private boolean prime;
	private boolean checked;
	
	public Entry(BigInteger number) {
		this.value = number;
		prime = false;
		checked = false;
	}
	public BigInteger getValue () {
		return value;
	}
	public boolean getPrime () {
		return prime;
	}
	public void setPrime (boolean prime) {
		this.prime = prime;
	}
	public boolean getChecked () {
		return checked;
	}
	public void check () {
		checked = true;
	}
}
