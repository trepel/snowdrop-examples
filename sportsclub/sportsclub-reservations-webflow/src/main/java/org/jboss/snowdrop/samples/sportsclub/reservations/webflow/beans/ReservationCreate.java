package org.jboss.snowdrop.samples.sportsclub.reservations.webflow.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.service.AccountService;
import org.jboss.snowdrop.samples.sportsclub.service.ReservationService;
import org.richfaces.model.selection.Selection;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:lvlcek@redhat.com">Lukas Vlcek</a>
 */
public class ReservationCreate
{

   @Autowired
   private ReservationService reservationService;

   private Locale locale;

   public Reservation init(Long reservationId)
   {
      this.locale = Locale.getDefault();


      if (reservationId == null)
      {
         Date from;
         Date to;

         Calendar cal = Calendar.getInstance(Locale.US);
         //cal.clear();
         from = cal.getTime();

         cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
         to = cal.getTime();
         Reservation reservation = new Reservation();
         reservation.setAccount(null);
         reservation.setEquipment(null);
         reservation.setFrom(from);
         reservation.setTo(to);


         return reservation;
      }
      else
      {
         return reservationService.loadReservation(reservationId);
      }
   }

   public Selection selectAccount(Reservation r)
   {
      if (r == null || r.getAccount() == null)
      {
         return null;
      }
      SimpleSelection selection = new SimpleSelection();
      selection.addKey(r.getAccount().getId());
      return selection;
   }

   public Selection selectEquipment(Reservation r)
   {
      if (r == null || r.getEquipment() == null)
      {
         return null;
      }
      SimpleSelection selection = new SimpleSelection();
      selection.addKey(r.getEquipment().getId());
      return selection;
   }


   public Map<String, FacesMessage> validate(Reservation reservation)
   {

      Map<String, FacesMessage> errors = new HashMap<String, FacesMessage>();

      if (reservation.getAccount() == null ||
            reservation.getEquipment() == null ||
            reservation.getFrom() == null ||
            reservation.getTo() == null)
      {

         if (reservation.getAccount() == null)
         {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Account not selected.");
            message.setDetail("Please select account!");
            errors.put("AccountSelectForm", message);
         }

         if (reservation.getEquipment() == null)
         {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Equipment not selected.");
            message.setDetail("Please select equipment!");
            errors.put("EquipmentSelectForm", message);
         }

         if (reservation.getFrom() == null)
         {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Date not selected.");
            message.setDetail("Please select date!");
            errors.put("ReservationDetailForm:from", message);
         }

         if (reservation.getTo() == null)
         {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Date not selected.");
            message.setDetail("Please select date!");
            errors.put("ReservationDetailForm:to", message);
         }
      }

      return errors;
   }

   public Locale getLocale()
   {
      return locale;
   }
}
