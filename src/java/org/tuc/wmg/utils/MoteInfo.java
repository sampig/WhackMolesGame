package org.tuc.wmg.utils;

/**
 * Mote Information.
 * 
 * @author Chenfeng Zhu
 *
 */
public class MoteInfo implements Comparable<MoteInfo> {

	private int mid;

	public MoteInfo() {
	}

	public MoteInfo(int mid) {
		this.mid = mid;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	@Override
	public int compareTo(MoteInfo other) {
		if (this.mid == other.getMid()) {
			return 0;
		} else if (this.mid > other.getMid()) {
			return 1;
		} else if (this.mid < other.getMid()) {
			return -1;
		}
		return 1;
	}

	public String toString() {
		return "<Mode." + mid + ">";
	}

}
