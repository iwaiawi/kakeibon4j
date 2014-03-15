package kakeibon4j.entity;

import java.util.Date;

/**
 * A class of representive duration.
 * 
 * @author ero3
 */
public class Duration implements Comparable<Duration>, java.io.Serializable {
	
	public static final long MILLISECONDS_OF_MINUTE = 60L * 1000L;
	
	public static final long MILLISECONDS_OF_HOUR = MILLISECONDS_OF_MINUTE * 60L;
	
	public static final long MILLISECONDS_OF_DAY = MILLISECONDS_OF_HOUR * 24;
	
	private final Date startAt;
	private final Date endAt;
	
	/**
	 * Creates a Duration from start date to end date.
	 * 
	 * @param startAt start date
	 * @param endAt end date
	 */
	public Duration(final Date startAt, final Date endAt) {
		this.startAt = new Date(startAt.getTime());
		this.endAt = new Date(endAt.getTime());
	}
	
	public Duration(final Date startAt, final int days) {
		this(startAt, new Date(startAt.getTime() + MILLISECONDS_OF_DAY * days));
	}
	
	/**
	 * Creates a Duration from some days before end date to end date.
	 * 
	 * @param days duration days
	 * @param endAt end date
	 */
	public Duration(final int days, final Date endAt) {
		this(new Date(endAt.getTime() - MILLISECONDS_OF_DAY * days), endAt);
	}
	
	/**
	 * Returns a start date.
	 * 
	 * @return start date
	 */
	public Date getStartAt() {
		return new Date(startAt.getTime());
	}
	
	/**
	 * Returns a end date.
	 * 
	 * @return end date
	 */
	public Date getEndAt() {
		return new Date(endAt.getTime());
	}
	
	/**
	 * Returns milliseconds of duration.
	 * 
	 * @return milliseconds of duration
	 */
	public long getMilliseconds() {
		return (endAt.getTime() - startAt.getTime());
	}
	
	/**
	 * Returns floored days of duration.
	 * 
	 * @return days of duration
	 */
	public int getDays() {
		return (int)((endAt.getTime() - startAt.getTime()) / MILLISECONDS_OF_DAY);
	}
	
	/**
	 * Returns a previous duration.
	 * 
	 * @return previous duration
	 */
	public Duration previous() {
		return new Duration(new Date(startAt.getTime() - getMilliseconds()), startAt);
	}
	
	/**
	 * Returns a next duration.
	 * 
	 * @return next duration
	 */
	public Duration next() {
		return new Duration(endAt, new Date(endAt.getTime() + getMilliseconds()));
	}
	
	@Override
	public int compareTo(final Duration another) {
		final long thisStartAt = getStartAt().getTime();
		final long anotherStartAt = another.getStartAt().getTime();
		
		if (thisStartAt != anotherStartAt) {
			return (thisStartAt < anotherStartAt ? -1 : (thisStartAt == anotherStartAt ? 0 : 1));
		} else {
			final long thisEndAt = getEndAt().getTime();
			final long anotherEndAt = another.getEndAt().getTime();
			
			return (thisEndAt < anotherEndAt ? -1 : (thisEndAt == anotherEndAt ? 0 : 1));
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endAt == null) ? 0 : endAt.hashCode());
		result = prime * result + ((startAt == null) ? 0 : startAt.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Duration other = (Duration)obj;
		if (endAt == null) {
			if (other.endAt != null) {
				return false;
			}
		} else if (!endAt.equals(other.endAt)) {
			return false;
		}
		if (startAt == null) {
			if (other.startAt != null) {
				return false;
			}
		} else if (!startAt.equals(other.startAt)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Duration[startAt=" + startAt + ", endAt=" + endAt + "]";
	}
}
