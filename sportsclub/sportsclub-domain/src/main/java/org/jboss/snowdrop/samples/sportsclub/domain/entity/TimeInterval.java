package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Embeddable;

import org.jboss.snowdrop.samples.sportsclub.utils.DateUtils;

/**
 * @author Marius Bogoevici
 */
@Embeddable
public class TimeInterval implements Serializable
{
   public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("EST");

   public static final long TWO_WEEKS = (14 * 24 * 3600 * 1000);
   public static final long DAY = 24 * 3600 * 1000;

   private Date startDate;

   private Date endDate;

   public Date getEndDate()
   {
      return endDate;
   }

   public void setEndDate(Date endDate)
   {
      this.endDate = endDate;
   }

   public Date getStartDate()
   {
      return startDate;
   }

   public void setStartDate(Date startDate)
   {
      this.startDate = startDate;
   }

   public boolean contains(Date someDate)
   {
      return someDate.compareTo(startDate) >= 0 && someDate.compareTo(endDate)<0;
   }
}
