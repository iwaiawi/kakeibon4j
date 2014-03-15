package kakeibon4j.internal.scraping.client.query;

import java.util.Calendar;

import kakeibon4j.entity.Duration;

public class DurationQuery implements BatchQuery {
	
	private final Duration duration;
	
	public DurationQuery(final Duration duration) {
		if (duration == null) {
			throw new IllegalStateException("Argument duration is null. Argument duration is required.");
		}
		this.duration = duration;
	}
	
	@Override
	public String toQueryString() {
		final Calendar startAt = Calendar.getInstance();
		final Calendar endAt = Calendar.getInstance();
		startAt.setTime(duration.getStartAt());
		endAt.setTime(duration.getEndAt());
		
		return String.format(
				"{type:\"term\",start_date:\"%04d%02d%02d\",end_date:\"%04d%02d%02d\"}",
				startAt.get(Calendar.YEAR),
				startAt.get(Calendar.MONTH) + 1,
				startAt.get(Calendar.DATE),
				endAt.get(Calendar.YEAR),
				endAt.get(Calendar.MONTH) + 1,
				endAt.get(Calendar.DATE)
				);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[duration=" + duration + "]";
	}
}